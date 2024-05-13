package com.duaz.microservices.admin.control;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

import com.duaz.microservices.admin.constant.RIConstants;
import com.duaz.microservices.admin.model.LoginAPI;
import com.duaz.microservices.admin.model.user.User;
import com.duaz.microservices.admin.model.user.UserRest;
import com.duaz.microservices.admin.response.CommonAPIResponse;
import com.duaz.microservices.admin.response.LoginAPIResponse;
import com.duaz.microservices.admin.response.RequestTokenAPIResponse;
import com.duaz.microservices.admin.services.LoginAPIServices;
import com.duaz.microservices.admin.services.UserServices;
import com.duaz.microservices.admin.utility.FormatUtility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@RestController
@RequestMapping("/rest/api/v1")
public class RestAPIController {
	static Logger LOG = Logger.getLogger(RestAPIController.class); 
	
	final static int TIMEOUT = 9999999;
	final static int EXPIRY_JWT_TOKEN = 600000;
	final static String JWT_ID = "DuazJwt";
	final static String JWT_SECRET_KEYS = "KS43sk((s";
	
	@Autowired
	private LoginAPIServices loginAPIServices;
	
	@Autowired
	private UserServices userServices;
	
	/**
	 * 
	 * @param api
	 * @return
	 */
	@RequestMapping(
			value = "/login", 
			method = RequestMethod.POST)
	public WebAsyncTask<LoginAPIResponse> login(@RequestBody LoginAPI api) {
		
		WebAsyncTask<LoginAPIResponse> task = new WebAsyncTask<LoginAPIResponse>(TIMEOUT, () -> {
			LoginAPIResponse resp = null;

			String token = "";
			int loginRespond = -1;
			
			SimpleDateFormat sdf = null;
			
			try {	
				LOG.info("login() - start");
			
				sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				
				resp = new LoginAPIResponse();
				resp.setUsername(api.getUsername());
				
				String username = api.getUsername();
				String pass = api.getPassword();
				
				loginRespond = loginAPIServices.doLogin(username,pass);
		        
				if (loginRespond == RIConstants.REST_API_LOGIN_SUCCESS) {
					token = getJWTToken(api.getUsername());
					
					resp.setExpiryDate(sdf.format(new Date(System.currentTimeMillis() + EXPIRY_JWT_TOKEN)));					
					resp.setToken(token);
		        	resp.setMessage("Login successful.");
		        	resp.setStatus(RIConstants.REST_SUCCESS_STATUS);
				}
				else {
					resp.setMessage("Problem in logging-in");
		        	resp.setStatus(RIConstants.REST_SUCCESS_STATUS);
				}
				
			}
			catch (final Exception e) {			
				resp = new LoginAPIResponse();
				
				resp.setMessage("Failed to start the workflow. Error = " + e.getMessage());
				resp.setStatus(RIConstants.REST_FAIL_STATUS);
	        	
	            LOG.error("Exception at login()");
	        	LOG.error(this, e);
	        }
	        finally {
	        	// clean up
	        	sdf = null;
	        }
			
			return resp;
		});
		
		task.onTimeout(new Callable<LoginAPIResponse>() {
    		@Override
    	    public LoginAPIResponse call() throws Exception {
    			LoginAPIResponse respFail = new LoginAPIResponse();
    			
    			respFail.setStatus(RIConstants.REST_FAIL_STATUS);
				respFail.setMessage("Failed to login. Error = request timeout.");
				
				LOG.error("Exception at login() - timeout");
	        	
    	        return respFail;
    	    }
    	});
    	
        task.onCompletion(()->{
        	LOG.info("login() - completed");
        });
    	
    	return task;
		
	}
	
	@RequestMapping(
			value = "/getToken", 
			method = RequestMethod.GET)
	public LoginAPIResponse getToken(@ModelAttribute LoginAPI api) {
		
			LoginAPIResponse resp = null;
			String token = "";
			SimpleDateFormat sdf = null;
			
			try {	
				sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				
				resp = new LoginAPIResponse();
				resp.setUsername(api.getUsername());
		        
				token = getJWTToken(api.getUsername());
				
				resp.setExpiryDate(sdf.format(new Date(System.currentTimeMillis() + EXPIRY_JWT_TOKEN)));					
				resp.setToken(token);
	        	resp.setMessage("Get Token successful.");
	        	resp.setStatus(RIConstants.REST_SUCCESS_STATUS);
				
			}
			catch (final Exception e) {			
				resp = new LoginAPIResponse();
				
				resp.setMessage("Failed at GetToken. Error = " + e.getMessage());
				resp.setStatus(RIConstants.REST_FAIL_STATUS);
	        	
	            LOG.error("Exception at login()");
	        	LOG.error(this, e);
	        }
	        finally {
	        	// clean up
	        	sdf = null;
	        }
			
			return resp;
	}
	
	@RequestMapping(
			value = "/performTest.json", 
			method = RequestMethod.POST)
	public WebAsyncTask<ResponseEntity<CommonAPIResponse>> performTest(@RequestParam String test, @RequestHeader("Authorization") String authHeader) {
		
		WebAsyncTask<ResponseEntity<CommonAPIResponse>> task = new WebAsyncTask<ResponseEntity<CommonAPIResponse>>(TIMEOUT, () -> {

			CommonAPIResponse resp = null;

			String authToken = "";
			Claims claims = null;
			
			try {	
				resp = new CommonAPIResponse();
				
				LOG.info("performTest() - start");
			
				if (authHeader == null || !authHeader.startsWith("Bearer ")) {
					resp.setMessage("No token in header");
					resp.setStatus(RIConstants.REST_FAIL_STATUS);
					
					return new ResponseEntity<>(resp, HttpStatus.FORBIDDEN);
		        }

		        authToken = authHeader.substring(7);
		        
		        // validate token
		        try {
		        	claims = validateToken(authToken);
		        	
		        	if (claims != null && claims.get("authorities") != null) {
						// safe - can continue
		        		System.out.println(claims.getExpiration().toString());
		        		
		        		resp.setMessage("Token validation success");
						resp.setStatus(RIConstants.REST_SUCCESS_STATUS);
			        	
					} 
		        	else {
						// has to login again?
		        		resp.setMessage("Token validation failed");
						resp.setStatus(RIConstants.REST_FAIL_STATUS);
					}
		        	
		        	return new ResponseEntity<>(resp, HttpStatus.OK);
		        }
		        catch (ExpiredJwtException e) {
		        	System.out.println("expried");
		        	resp.setMessage("Token validation expired");
					resp.setStatus(RIConstants.REST_FAIL_STATUS);
					
					return new ResponseEntity<>(resp, HttpStatus.FORBIDDEN);
		        }
		        catch (UnsupportedJwtException | MalformedJwtException e) {
		        	System.out.println("asdasaff");
		        	
		        	resp.setMessage("Token validation malformed / unsupported");
		        	resp.setStatus(RIConstants.REST_FAIL_STATUS);
		        	
		        	return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		        
			}
			catch (final Exception e) {			
				resp = new CommonAPIResponse();
				
				resp.setMessage("Failed to performTest. Error = " + e.getMessage());
				resp.setStatus(RIConstants.REST_FAIL_STATUS);
	        									
	            LOG.error("Exception at performTest()");
	        	LOG.error(this, e);
	        	
	        	return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	        finally {
	        	// clean up
	        }
			
			
			
		});
		
		task.onTimeout(new Callable<ResponseEntity<CommonAPIResponse>>() {
    		@Override
    	    public ResponseEntity<CommonAPIResponse> call() throws Exception {
    			LoginAPIResponse respFail = new LoginAPIResponse();
    			
    			respFail.setStatus(RIConstants.REST_FAIL_STATUS);
				respFail.setMessage("Failed to performTest. Error = request timeout.");
				
				LOG.error("Exception at performTest() - timeout");
	        	
				return new ResponseEntity<>(respFail, HttpStatus.OK);
    	    }
    	});
    	
        task.onCompletion(()->{
        	LOG.info("performTest() - completed");
        });
    	
    	return task;
		
	}
	
	@RequestMapping(
			value = "/getFile.json", 
			method = RequestMethod.POST)	
	public WebAsyncTask<ResponseEntity<Object>> getFile(@RequestParam String test, @RequestHeader("Authorization") String authHeader) {
		
		WebAsyncTask<ResponseEntity<Object>> task = new WebAsyncTask<ResponseEntity<Object>>(TIMEOUT, () -> {

			CommonAPIResponse resp = null;
			Resource resource = null;
			
			String authToken = "";
			Claims claims = null;
			
			try {	
				LOG.info("getFile() - start");
				resp = new CommonAPIResponse();
				
				if (authHeader == null || !authHeader.startsWith("Bearer ")) {
					resp.setMessage("No token in header");
					resp.setStatus(RIConstants.REST_FAIL_STATUS);
					
					return new ResponseEntity<>(resp, HttpStatus.FORBIDDEN);
		        }

		        authToken = authHeader.substring(7);
		        
		        // validate token
		        try {
		        	claims = validateToken(authToken);
		        	
		        	if (claims != null && claims.get("authorities") != null) {
						// safe - can continue
		        		System.out.println(claims.getExpiration().toString());
		        		
		        		resp.setMessage("Token validation success");
						resp.setStatus(RIConstants.REST_SUCCESS_STATUS);
			        	
					} 
		        	else {
						// has to login again?
		        		resp.setMessage("Token validation failed");
						resp.setStatus(RIConstants.REST_FAIL_STATUS);
						
						return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
					}
		        	
		        }
		        catch (ExpiredJwtException e) {
		        	System.out.println("expried");
		        	resp.setMessage("Token validation expired");
					resp.setStatus(RIConstants.REST_FAIL_STATUS);
					
					return new ResponseEntity<>(resp, HttpStatus.FORBIDDEN);
		        }
		        catch (UnsupportedJwtException | MalformedJwtException e) {
		        	System.out.println("asdasaff");
		        	
		        	resp.setMessage("Token validation malformed / unsupported");
		        	resp.setStatus(RIConstants.REST_FAIL_STATUS);
		        	
		        	return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		        
		        // get the file now
		        resource = null;
		        resource = this.loadFileAsResource();
		        
		        String contentType = "application/octet-stream";

		        return ResponseEntity.ok()
		        		.contentType(MediaType.parseMediaType(contentType))
		        		.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
		        		.body(resource);
			}
			catch (final Exception e) {			
				resp = new CommonAPIResponse();
				
				resp.setMessage("Failed to getFile. Error = " + e.getMessage());
				resp.setStatus(RIConstants.REST_FAIL_STATUS);
	        									
	            LOG.error("Exception at getFile()");
	        	LOG.error(this, e);
	        	
	        	return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	        finally {
	        	// clean up
	        }
			
			
			
		});
		
		task.onTimeout(new Callable<ResponseEntity<Object>>() {
    		@Override
    	    public ResponseEntity<Object> call() throws Exception {
    			LoginAPIResponse respFail = new LoginAPIResponse();
    			
    			respFail.setStatus(RIConstants.REST_FAIL_STATUS);
				respFail.setMessage("Failed to performTest. Error = request timeout.");
				
				LOG.error("Exception at performTest() - timeout");
	        	
				return new ResponseEntity<>(respFail, HttpStatus.OK);
    	    }
    	});
    	
        task.onCompletion(()->{
        	LOG.info("performTest() - completed");
        });
    	
    	return task;
		
	}
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	private String getJWTToken(String username) {
		
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = Jwts
				.builder()
				.setId(JWT_ID)
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRY_JWT_TOKEN))
				.signWith(SignatureAlgorithm.HS512, JWT_SECRET_KEYS.getBytes()).compact();

		return "Bearer " + token;
	}
	
	/**
	 * 
	 * @param token
	 * @return
	 */
	private Claims validateToken(String token) {
		return Jwts.parser().setSigningKey(JWT_SECRET_KEYS.getBytes()).parseClaimsJws(token).getBody();
	}
	
	/**
	 * This is to build into spring config but we don't need this
	 * 
	 * @param claims
	 */
	private void setUpSpringAuthentication(Claims claims) {
		@SuppressWarnings("unchecked")
		List<String> authorities = (List) claims.get("authorities");

		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
				authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		SecurityContextHolder.getContext().setAuthentication(auth);

	}
	
	/**
	 * 
	 * @param token
	 * @return
	 */
	public String getUsernameFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(JWT_SECRET_KEYS).parseClaimsJws(token).getBody();
		return claims.getSubject();

	}

	public Resource loadFileAsResource() throws Exception {		
        try {
            Path filePath = Paths.get("C:\\Users\\WIN 10\\Desktop\\SuratKuasa.pdf").normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if(resource.exists()) {
                return resource;
            } 
            else {
                throw new FileNotFoundException("File not found ");
            }
        } 
        catch (MalformedURLException ex) {

            throw new FileNotFoundException("File not found ");
        }
    }
    
	@RequestMapping(value = {"/requestSecurityToken"}, method = {RequestMethod.POST})
	  public RequestTokenAPIResponse requestSecurityToken(@RequestBody UserRest api) {
	    RequestTokenAPIResponse resp = null;
	    FormatUtility fu = null;
	    User u = null;
	    String securityToken = "";
	    int source = 2;
	    String err = "";
	    try {
	      resp = new RequestTokenAPIResponse();
	      fu = new FormatUtility();
	      
	      err = "";
	      err = loginAPIServices.validateClientIdApiKey(api.getClientId(), api.getApiKey());
	      if (err != null && err.trim().length() > 0)
	        throw new Exception(err); 
	      if (api == null)
	        throw new Exception("Data is null"); 
	      if (api.getUserId() == null || api.getUserId().intValue() <= 0)
	        throw new Exception("User details is null"); 
	      securityToken = "";
	      securityToken = fu.generateAlphanumericRandomString(20);
	      u = null;
	      u = this.userServices.getUserById(api.getUserId());
	      
	      if (u == null)
	        throw new Exception("User is null"); 
	      if (u != null && (u.getClientNo() == null || u.getClientNo().intValue() <= 0))
	        throw new Exception("Client of the user is null"); 
	      this.loginAPIServices.insertSecurityToken(securityToken, source, u.getClientNo().intValue(), 1, 0);
	      
	      resp.setSecurityToken(securityToken);
	      resp.setMessage("Token request for user " + api.getUserId() + " has been successful.");
	      resp.setStatus(Integer.valueOf(1));
	    } catch (Exception e) {
	      resp = new RequestTokenAPIResponse();
	      resp.setMessage("Unable to request token. Error = " + e.getMessage());
	      resp.setStatus(Integer.valueOf(0));
	      LOG.error("Exception at requestSecurityToken()");
	      LOG.error(this, e);
	    } finally {
	      fu = null;
	    } 
	    return resp;
	  }
	
}

