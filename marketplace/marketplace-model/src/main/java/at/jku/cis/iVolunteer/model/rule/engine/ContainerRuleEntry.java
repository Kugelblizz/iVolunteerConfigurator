package at.jku.cis.iVolunteer.model.rule.engine;

import java.sql.Timestamp;
import java.util.Date;

import org.kie.api.io.ResourceType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.IVolunteerObject;

@Document(collection="containerRule")
public class ContainerRuleEntry extends IVolunteerObject {
	
	private String name;
	private String container;
	private String content;
	private ResourceType type;
	
	public ContainerRuleEntry() {
	}

	public ContainerRuleEntry(String tenantId, String marketplaceId, String container) {
		this.tenantId = tenantId;
		this.marketplaceId = marketplaceId;
		this.container = container;
		Timestamp stamp = new Timestamp(System.currentTimeMillis());
	    this.timestamp = new Date(stamp.getTime()); 
	}

	public ContainerRuleEntry(String tenantId, String marketplaceId, String container, String name, String content) {
		this(tenantId, marketplaceId, container);
		this.name = name;
		this.container = container;
		this.content = content;
		setType(ResourceType.DRL);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getContainer() {
		return container;
	}
	
	public void setContainer(String container) {
		this.container = container;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}
	
	public String toString() {
		return "Tenant: " + tenantId + ", Container: + " + container + ", Rule: " + content + ", Type: " + type.toString();
	}

	
}
