package at.jku.cis.iVolunteer.configurator.meta.core.class_;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.configurator.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.configurator.model.meta.core.clazz.ClassDefinition;

@Repository
public interface ClassDefinitionRepository extends MongoRepository<ClassDefinition, String> {

//	List<ClassDefinition> getByClassArchetypeAndTenantId(ClassArchetype classArchetype, String tenantId);
	
	List<ClassDefinition> getByClassArchetype(ClassArchetype classArchetype);
	
	List<ClassDefinition> getByTenantId(String tenantId);

	ClassDefinition getByIdAndTenantId(String id, String tenantId);
	
	ClassDefinition findByNameAndTenantId(String name, String tenantId);



}
