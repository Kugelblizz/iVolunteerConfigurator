package at.jku.cis.iVolunteer.model.user;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Volunteer extends User {
	private String[] subscribedTenants;

	public String[] getSubscribedTenants() {
		return subscribedTenants;
	}

	public void setSubscribedTenants(String[] subscribedTenants) {
		this.subscribedTenants = subscribedTenants;
	}

	



}
