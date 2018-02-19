package at.jku.csi.marketplace.participant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public abstract class Participant {

	@Id
	private String id;

	private String username;
	private ParticipantProfile profile;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ParticipantProfile getProfile() {
		return profile;
	}

	public void setProfile(ParticipantProfile profile) {
		this.profile = profile;
	}

}
