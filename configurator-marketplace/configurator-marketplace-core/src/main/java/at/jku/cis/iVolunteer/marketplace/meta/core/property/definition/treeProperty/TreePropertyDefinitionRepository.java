package at.jku.cis.iVolunteer.marketplace.meta.core.property.definition.treeProperty;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.meta.core.property.definition.treeProperty.TreePropertyDefinition;

public interface TreePropertyDefinitionRepository extends MongoRepository<TreePropertyDefinition, String> {

	public TreePropertyDefinition findByName(String name);

	public List<TreePropertyDefinition> getByTenantId(String tenantId);
}
