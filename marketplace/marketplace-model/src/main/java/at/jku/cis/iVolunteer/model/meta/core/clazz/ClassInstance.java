package at.jku.cis.iVolunteer.model.meta.core.clazz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.google.gson.JsonObject;

import at.jku.cis.iVolunteer.model.IVolunteerObject;
import at.jku.cis.iVolunteer.model.hash.IHashObject;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;

@Document
public class ClassInstance extends IVolunteerObject implements IHashObject {

	private String classDefinitionId;
	private String name;
	private List<PropertyInstance<Object>> properties = new ArrayList<>();

	private String userId;
	private String issuerId;

	private String imagePath;

	private ClassArchetype classArchetype;

	private List<ClassInstance> childClassInstances = new ArrayList<>();

	private boolean visible;
	private int tabId;
	
	private boolean issued;
	private boolean expired;
	private boolean subscribed;
	
	private Date blockchainDate;


	private String derivationRuleId;
	
	private int level;
	
	public ClassInstance() {
	}

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

	public List<PropertyInstance<Object>> getProperties() {
		return properties;
	}

	public void setProperties(List<PropertyInstance<Object>> properties) {
		this.properties = properties;
	}
	
	public Boolean containsProperty(String propertyId) {
		if (properties == null || properties.size() == 0)
			return false;
		return properties.stream().filter(p -> p.getId().equals(propertyId)).findAny().isPresent();
	}
	
	public Boolean containsPropertyByName(String name){
		if (properties == null || properties.size() == 0)
			return false;
		for (PropertyInstance<Object> pi: properties) {
			if (pi.getName().equals(name))
				return true;
		}
		return false;
	}
	
	public PropertyInstance<Object> getProperty(String propertyId){
		return properties.stream().filter(pi -> pi.getId().equals(propertyId)).findFirst().orElse(null);
	}
	
	public String getClassDefinitionId() {
		return classDefinitionId;
	}

	public void setClassDefinitionId(String classDefinitionId) {
		this.classDefinitionId = classDefinitionId;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ClassInstance))
			return false;
		return ((ClassInstance) obj).id.equals(id);
	}

	public String getMarketplaceId() {
		return marketplaceId;
	}

	public void setMarketplaceId(String marketplaceId) {
		this.marketplaceId = marketplaceId;
	}

	public ClassArchetype getClassArchetype() {
		return classArchetype;
	}

	public void setClassArchetype(ClassArchetype classArchetype) {
		this.classArchetype = classArchetype;
	}

	public String getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public List<ClassInstance> getChildClassInstances() {
		return childClassInstances;
	}

	public void setChildClassInstances(List<ClassInstance> childClassInstances) {
		this.childClassInstances = childClassInstances;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public int getTabId() {
		return tabId;
	}

	public void setTabId(int tabId) {
		this.tabId = tabId;
	}
	
	public boolean isIssued() {
		return issued;
	}

	public void setIssued(boolean issued) {
		this.issued = issued;
	}
	
	
	
	public boolean isSubscribed() {
		return subscribed;
	}

	public void setSubscribed(boolean subscribed) {
		this.subscribed = subscribed;
	}

	public void setDerivationRuleId(String derivationRuleId) {
		this.derivationRuleId = derivationRuleId;
	}
	
	public String getDerivationRuleId() {
		return derivationRuleId;
	}

	public Date getBlockchainDate() {
		return blockchainDate;
	}
	
	public void setBlockchainDate(Date blockchainDate) {
		this.blockchainDate = blockchainDate;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	@Override
	public String toHashObject() {
		JsonObject json = new JsonObject();
		json.addProperty("id", id);
		json.addProperty("name", name);
		json.addProperty("marketplaceId", marketplaceId);
		json.addProperty("properties", this.properties.hashCode());
		return json.toString();
	}

}
