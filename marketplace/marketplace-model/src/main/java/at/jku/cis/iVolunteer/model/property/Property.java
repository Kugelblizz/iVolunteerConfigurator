package at.jku.cis.iVolunteer.model.property;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class Property {
	@Id
	String id;
	String name;
	
//	List<Rule> rules;
	PropertyKind kind;
	
	int order;
	
	boolean custom;
	
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


//	public List<Rule> getRules() {
//		return rules;
//	}
//	
//	public void setRules(List<Rule> rules) {
//		this.rules = rules;
//	}

	public PropertyKind getKind() {
		return kind;
	}
	
	public void setKind(PropertyKind kind) {
		this.kind = kind;
	}
	
	public int getOrder() {
		return order;
	}
	
	public void setOrder(int order) {
		this.order = order;
	}
	
	public boolean isCustom() {
		return custom;
	}
	
	public void setCustom(boolean custom) {
		this.custom = custom;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Property)) {
			return false;
		}
		return ((Property) obj).id.equals(id);
	
	}
	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

	@Override
	public String toString() {
		return "\nProperty [id=" + id + ", name=" + name + /*", rules=" + rules +*/ ", kind=" + kind + ", order=" + order
				+ ", custom=" + custom + "]\n";
	}
	
	

	
	

	
}