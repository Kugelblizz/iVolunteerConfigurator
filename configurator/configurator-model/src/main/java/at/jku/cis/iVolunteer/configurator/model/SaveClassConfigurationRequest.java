package at.jku.cis.iVolunteer.configurator.model;

import java.util.List;

import at.jku.cis.iVolunteer.configurator.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.configurator.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.relationship.Relationship;

public class SaveClassConfigurationRequest {
	ClassConfiguration classConfiguration;
	List<ClassDefinition> classDefinitions;
	List<Relationship> relationships;
	List<String> deletedClassDefinitionIds;
	List<String> deletedRelationshipIds;
	String tenantId;
	
	public ClassConfiguration getClassConfiguration() {
		return classConfiguration;
	}
	public void setClassConfiguration(ClassConfiguration classConfiguration) {
		this.classConfiguration = classConfiguration;
	}
	public List<ClassDefinition> getClassDefinitions() {
		return classDefinitions;
	}
	public void setClassDefinitions(List<ClassDefinition> classDefinitions) {
		this.classDefinitions = classDefinitions;
	}
	public List<Relationship> getRelationships() {
		return relationships;
	}
	public void setRelationships(List<Relationship> relationships) {
		this.relationships = relationships;
	}
	public List<String> getDeletedClassDefinitionIds() {
		return deletedClassDefinitionIds;
	}
	public void setDeletedClassDefinitionIds(List<String> deletedClassDefinitionIds) {
		this.deletedClassDefinitionIds = deletedClassDefinitionIds;
	}
	public List<String> getDeletedRelationshipIds() {
		return deletedRelationshipIds;
	}
	public void setDeletedRelationshipIds(List<String> deletedRelationshipIds) {
		this.deletedRelationshipIds = deletedRelationshipIds;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
}
