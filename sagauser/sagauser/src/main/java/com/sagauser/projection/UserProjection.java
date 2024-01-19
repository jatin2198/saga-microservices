package com.sagauser.projection;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import com.commonservice.models.CardDetails;
import com.commonservice.models.User;
import com.commonservice.queries.GetUserDetailsQuery;

@Component
public class UserProjection {
	
	@QueryHandler
	public User getUserPaymentDetails(GetUserDetailsQuery getUserDetailsQuery) {
		//Dummy details ,in rela-time these should come frm DB 
		CardDetails cardDetails=CardDetails.builder()
				.cardNumber("12344678")
				.cvv(234)
				.validUntilMonth(07)
				.validUntilYear(2027)
				.name("Jatin Arya")
				.build();
		
		return User.builder().cardDetails(cardDetails)
				.firstName("Jatin")
				.lastName("Arya")
				.userId(getUserDetailsQuery.getUserId()).build();
		
		
	}

}
