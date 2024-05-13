package com.duaz.microservices.admin.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.duaz.microservices.admin.model.Movie;
import com.duaz.microservices.admin.model.ReturnMessege;
import com.duaz.microservices.admin.services.MovieServices;

@RestController
@CrossOrigin(origins = "*")
public class MovieController {
	
	@Autowired
	private MovieServices movieServices;
	
	@RequestMapping(
			value="/Movies",
			method=RequestMethod.GET)
	public @ResponseBody List<Movie> getListMovie() {
		List<Movie> list = null;
		
		try {			
			list = this.movieServices.getListMovie();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	@RequestMapping(
			value="/listMovies",
			method=RequestMethod.GET)
	public @ResponseBody ResponseEntity getListMovies() {
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.set("Baeldung-Example-Header", 
	      "Value-ResponseEntityBuilderWithHttpHeaders");
	    
		List<Movie> list = null;
		
		try {			
			list = this.movieServices.getListMovie();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		return list;
	    return ResponseEntity.ok()
	    	      .headers(responseHeaders)
	    	      .body("Response with header using ResponseEntity");
	}
	
	@RequestMapping(
			value="/Movies/{id}",
			method=RequestMethod.GET)
	public @ResponseBody Movie getMovieById(@PathVariable Integer id) {
		Movie movie = null;
		
		try {			
			movie = this.movieServices.getMovieById(id);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return movie;
	}
	
	@RequestMapping(value="/Movies",method=RequestMethod.POST)
	private ReturnMessege insertMovie(@RequestBody Movie movie) {
		ReturnMessege rMessege = new ReturnMessege();
		
		try {
			rMessege = movieServices.insertMovie(movie);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rMessege;
	}
	
	@RequestMapping(value="/Movies/{id}",method=RequestMethod.PATCH)
	private ReturnMessege updateMovie(@PathVariable Integer id, @RequestBody Movie movie) {
		ReturnMessege rMessege = new ReturnMessege();
		
		try {
			rMessege = this.movieServices.updateMovie(id, movie);
		} catch (Exception e) {
			rMessege.setIsSuccess(false);
			rMessege.setMessege(e.getMessage());
			e.printStackTrace();
		}
		
		
		return rMessege;
	}

	@RequestMapping(value="/Movies/{id}",method=RequestMethod.DELETE)
	private ReturnMessege removeMovie(@PathVariable Integer id) {
		ReturnMessege rMessege = new ReturnMessege();
		
		try {
			rMessege= this.movieServices.removeMovie(id);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		
		return rMessege;
	}
	
}
