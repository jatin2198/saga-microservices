package com.sagauser.controller;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commonservice.models.CardDetails;
import com.commonservice.models.User;
import com.commonservice.queries.GetUserDetailsQuery;

@RestController
@RequestMapping("/users")
public class UserController {
	
	 private transient QueryGateway queryGateway;

	    public UserController(QueryGateway queryGateway) {
	        this.queryGateway = queryGateway;
	    }

	    @GetMapping("/{userId}")
	    public User getUserPaymentDetails(@PathVariable String userId){
	    	GetUserDetailsQuery getUserPaymentDetailsQuery
	                = new GetUserDetailsQuery(userId);
	    	
	        User user =
	                queryGateway.query(getUserPaymentDetailsQuery,
	                        ResponseTypes.instanceOf(User.class)).join();

	        return user;
	    }

}
