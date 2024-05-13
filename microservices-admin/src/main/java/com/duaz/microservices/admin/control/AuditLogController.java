package com.duaz.microservices.admin.control;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.duaz.microservices.admin.model.ReturnMessage;
import com.duaz.microservices.admin.model.auditlog.AuditLog;
import com.duaz.microservices.admin.model.auditlog.ListAuditLogFilter;
import com.duaz.microservices.admin.model.auditlog.ListAuditLogResponse;
import com.duaz.microservices.admin.services.AuditLogServices;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/auditLog/")
public class AuditLogController {
	@Autowired
	private AuditLogServices auditLogServices;
	
	static Logger LOG = Logger.getLogger(AuditLogController.class); 

	@RequestMapping(
			value="getListOfAuditLog",
			method=RequestMethod.GET)
	public @ResponseBody ListAuditLogResponse getListOfAuditLog(@RequestBody ListAuditLogFilter filter) {		
		ListAuditLogResponse lar = null;
		
		try {			
			lar = this.auditLogServices.getListAuditLog(filter);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lar;
	}
	
	@RequestMapping(
			value="addAuditLog",
			method=RequestMethod.POST)
	public ReturnMessage addNewUserPOST(@RequestBody AuditLog auditLog){
		ReturnMessage rMessage = new ReturnMessage();
		
		try {
			LOG.info("Start Process Rest API Insert Data AuditLog");
			
			rMessage = this.auditLogServices.addAuditLog(auditLog);
			
		} catch (Exception e) {
			rMessage.setMessage(e.getMessage());
			e.printStackTrace();
			LOG.error("Error Insert Data AuditLog : " + e.getMessage());
		}
		
		LOG.info("End Process Rest API Insert Data AuditLog");
		
		return rMessage;
	}
}
