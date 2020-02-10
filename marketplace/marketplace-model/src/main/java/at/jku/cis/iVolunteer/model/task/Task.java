package at.jku.cis.iVolunteer.model.task;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.google.gson.JsonObject;

import at.jku.cis.iVolunteer.model.IVolunteerObject;
import at.jku.cis.iVolunteer.model.hash.IHashObject;
import at.jku.cis.iVolunteer.model.meta.core.clazz.competence.CompetenceClassDefinition;
import at.jku.cis.iVolunteer.model.project.Project;

@Document
public class Task implements IHashObject {

	@Id private String id;
	private String name;

	private String description;
	private String workflowKey;
	private String marketplaceId;
	private TaskStatus status;
	private Date startDate;
	private Date endDate;

	private List<CompetenceClassDefinition> acquirableCompetences;
	private List<CompetenceClassDefinition> requiredCompetences;

	@DBRef private Project project;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getWorkflowKey() {
		return workflowKey;
	}

	public void setWorkflowKey(String workflowKey) {
		this.workflowKey = workflowKey;
	}

	public String getMarketplaceId() {
		return marketplaceId;
	}

	public void setMarketplaceId(String marketplaceId) {
		this.marketplaceId = marketplaceId;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<CompetenceClassDefinition> getAcquirableCompetences() {
		return acquirableCompetences;
	}

	public void setAcquirableCompetences(List<CompetenceClassDefinition> acquirableCompetences) {
		this.acquirableCompetences = acquirableCompetences;
	}

	public List<CompetenceClassDefinition> getRequiredCompetences() {
		return requiredCompetences;
	}

	public void setRequiredCompetences(List<CompetenceClassDefinition> requiredCompetences) {
		this.requiredCompetences = requiredCompetences;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Task)) {
			return false;
		}
		return ((Task) obj).id.equals(id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toHashObject() {
		JsonObject json = new JsonObject();
		json.addProperty("id", id);
		json.addProperty("name", name);
		json.addProperty("description", description);
		json.addProperty("marketplaceId", marketplaceId);
		// json.addProperty("parent", parent.getId());
		json.addProperty("startDate", startDate.toString());
		// json.addProperty("endDate", endDate.toString());
		// json.addProperty("acquirableCompetences", acquirableCompetences.toString());
		// json.addProperty("requiredCompetences", requiredCompetences.toString());
		return json.toString();
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", name=" + name + ", description=" + description + ", workflowKey=" + workflowKey
				+ ", marketplaceId=" + marketplaceId + ", status=" + status + ", startDate=" + startDate + ", endDate="
				+ endDate + ", acquirableCompetences=" + getAcquirableCompetences() + ", requiredCompetences="
				+ getRequiredCompetences() + ", project=" + project + "]";
	}

}
