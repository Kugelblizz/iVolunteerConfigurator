package at.jku.cis.iVolunteer.core;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.configurations.clazz.ClassConfigurationController;
import at.jku.cis.iVolunteer.marketplace.configurations.clazz.ClassConfigurationRepository;
import at.jku.cis.iVolunteer.marketplace.configurations.matching.collector.MatchingEntityMappingConfigurationRepository;
import at.jku.cis.iVolunteer.marketplace.configurations.matching.configuration.MatchingConfigurationRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.definition.flatProperty.FlatPropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.definition.treeProperty.TreePropertyDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.flatProperty.FlatPropertyDefinition;

@Service
public class InitializationService {

	@Autowired protected ClassDefinitionRepository classDefinitionRepository;
	@Autowired protected RelationshipRepository relationshipRepository;
	@Autowired protected FlatPropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired protected ClassConfigurationRepository classConfigurationRepository;
	@Autowired protected MatchingConfigurationRepository matchingConfigurationRepository;
	@Autowired protected MatchingEntityMappingConfigurationRepository matchingCollectorConfigurationRepository;
	@Autowired protected TreePropertyDefinitionRepository treePropertyDefinitionRepository;


	@Autowired public StandardPropertyDefinitions standardPropertyDefinitions;



	@PostConstruct
	public void init() {
		
		//TODO
	}

	public void addiVolunteerPropertyDefinitions() {
//		List<Tenant> tenants = getTenants();
//		tenants.forEach(tenant -> {
			for (FlatPropertyDefinition<Object> pd : standardPropertyDefinitions.getAlliVolunteer(null)) {
				if (propertyDefinitionRepository.getByNameAndTenantId(pd.getName(), pd.getTenantId()).size() == 0) {
					propertyDefinitionRepository.save(pd);
				}
			}
//		});
	}

	public void addFlexProdPropertyDefinitions() {
//		List<Tenant> tenants = getTenants();
//		tenants.forEach(tenant -> {
			for (FlatPropertyDefinition<Object> pd : standardPropertyDefinitions
					.getAllFlexProdProperties(null)) {
				if (propertyDefinitionRepository.getByNameAndTenantId(pd.getName(), pd.getTenantId()).size() == 0) {
					propertyDefinitionRepository.save(pd);
				}
			}
//		});
	}

	public void addGenericPropertyDefintions() {
//		List<Tenant> tenants = getTenants();
//		tenants.forEach(tenant -> {
			for (FlatPropertyDefinition<Object> pd : standardPropertyDefinitions.getAllGeneric(null)) {
				if (propertyDefinitionRepository.getByNameAndTenantId(pd.getName(), pd.getTenantId()).size() == 0) {
					propertyDefinitionRepository.save(pd);
				}
			}
//		});
	}

	public void addHeaderPropertyDefintions() {
//		List<Tenant> tenants = getTenants();
//		tenants.forEach(tenant -> {
			for (FlatPropertyDefinition<Object> pd : standardPropertyDefinitions.getAllHeader(null)) {
				if (propertyDefinitionRepository.getByNameAndTenantId(pd.getName(), pd.getTenantId()).size() == 0) {
					propertyDefinitionRepository.save(pd);
				}
			}
//		});
	}

	public void addClassConfigurations() {
//		List<Tenant> tenants = getTenants();
//
//		for (Tenant t : tenants) {
//			for (int i = 1; i <= 5; i++) {
//				this.classConfigurationController
//						.createNewClassConfiguration(new String[] { t.getId(), "slot" + i, "" });
//			}
//		}
	}


	public void deleteClassDefinitions() {
		classDefinitionRepository.deleteAll();
	}

	public void deleteRelationships() {
		relationshipRepository.deleteAll();
	}

	public void deleteClassConfigurations() {
		classConfigurationRepository.deleteAll();
	}

	public void deleteMatchingConfigurations() {
		matchingConfigurationRepository.deleteAll();
	}

}
