package at.jku.cis.iVolunteer.configurator.core;

import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.configurator.configurations.clazz.ClassConfigurationRepository;
import at.jku.cis.iVolunteer.configurator.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.configurator.meta.core.property.definition.flatProperty.FlatPropertyDefinitionRepository;
import at.jku.cis.iVolunteer.configurator.meta.core.relationship.RelationshipRepository;


@Service
public class FinalizationService {

	FinalizationService() {

	}

	public void destroy(ClassConfigurationRepository configuratorRepository,
			ClassDefinitionRepository classDefinitionRepository,
			RelationshipRepository relationshipRepository, FlatPropertyDefinitionRepository propertyDefinitionRepository
			) {
//		 classDefinitionRepository.deleteAll();
//		 relationshipRepository.deleteAll();
//		 classInstanceRepository.deleteAll();
//		 configuratorRepository.deleteAll();
//		 propertyDefinitionRepository.deleteAll();
//		 derivationRuleRepository.deleteAll();

	}
}
