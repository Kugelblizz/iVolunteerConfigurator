package at.jku.cis.iVolunteer.model.meta.core.property.dtos;

public class PropertyItemDTO {
	private String id;
	private String name;

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

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PropertyItemDTO)) {
			return false;
		}
		return ((PropertyItemDTO) obj).id.equals(id);

	}

	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

}
