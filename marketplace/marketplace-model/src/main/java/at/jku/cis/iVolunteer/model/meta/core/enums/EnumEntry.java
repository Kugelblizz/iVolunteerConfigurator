package at.jku.cis.iVolunteer.model.meta.core.enums;

public class EnumEntry {
	String id;
	String value;
	boolean selectable;
	
	public EnumEntry() {
	
	}
	
	public EnumEntry(String value) {
		this.value = value;
		this.selectable = true;
	}

	public EnumEntry(String value, boolean selectable) {
		this.value = value;
		this.selectable = selectable;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public boolean isSelectable() {
		return selectable;
	}
	
	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}

	
	
	
	
	
}
