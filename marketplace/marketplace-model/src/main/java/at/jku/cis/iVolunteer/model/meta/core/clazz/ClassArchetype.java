package at.jku.cis.iVolunteer.model.meta.core.clazz;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ClassArchetype {

	COMPETENCE("COMPETENCE"), TASK("TASK"), FUNCTION("FUNCTION"), ACHIEVEMENT("ACHIEVEMENT");

	private final String archetype;

	private ClassArchetype(String archetype) {
		this.archetype = archetype;
	}

	public String getArchetype() {
		return this.archetype;
	}

	@Override
	public String toString() {
		return archetype;
	}

	@JsonCreator
	public static ClassArchetype getFromClassArchetype(String type) {
		for (ClassArchetype t : ClassArchetype.values()) {
			if (t.getArchetype().equals(type)) {
				return t;
			}
		}
		throw new IllegalArgumentException();

	}
}