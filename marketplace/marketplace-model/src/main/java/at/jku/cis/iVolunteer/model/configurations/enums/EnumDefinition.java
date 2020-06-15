package at.jku.cis.iVolunteer.model.configurations.enums;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.IVolunteerObject;
import at.jku.cis.iVolunteer.model.meta.core.enums.EnumEntry;
import at.jku.cis.iVolunteer.model.meta.core.enums.EnumRelationship;

@Document
public class EnumDefinition extends IVolunteerObject {

	private String name;
	private String description;

	private List<EnumEntry> enumEntries;
	private List<EnumRelationship> enumRelationships;


	public EnumDefinition() {
		this.enumEntries = new ArrayList<EnumEntry>();
		this.enumRelationships = new ArrayList<EnumRelationship>();
		this.setTimestamp(new Date());
	}
	
	public EnumDefinition(String name, String tenantId) {
		this.name = name;
		this.enumEntries = new ArrayList<EnumEntry>();
		this.enumRelationships = new ArrayList<EnumRelationship>();
		this.setTenantId(tenantId);
		this.setTimestamp(new Date());
	}
	
	public EnumDefinition(String name, String description, String tenantId) {
		this.name = name;
		this.description = description;
		this.enumEntries = new ArrayList<EnumEntry>();
		this.enumRelationships = new ArrayList<EnumRelationship>();
		this.setTenantId(tenantId);
		this.setTimestamp(new Date());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<EnumEntry> getEnumEntries() {
		return enumEntries;
	}

	public void setEnumEntries(List<EnumEntry> enumEntries) {
		this.enumEntries = enumEntries;
	}

	public List<EnumRelationship> getEnumRelationships() {
		return enumRelationships;
	}

	public void setEnumRelationships(List<EnumRelationship> enumRelationships) {
		this.enumRelationships = enumRelationships;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EnumDefinition)) {
			return false;
		}
		return ((EnumDefinition) obj).id.equals(id);
	}

}