package at.jku.cis.iVolunteer.configurator.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.configurator.configurations.clazz.ClassConfigurationController;
import at.jku.cis.iVolunteer.configurator.configurations.clazz.ClassConfigurationRepository;
import at.jku.cis.iVolunteer.configurator.configurations.matching.collector.MatchingEntityMappingConfigurationRepository;
import at.jku.cis.iVolunteer.configurator.configurations.matching.configuration.MatchingConfigurationRepository;
import at.jku.cis.iVolunteer.configurator.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.configurator.meta.core.property.definition.flatProperty.FlatPropertyDefinitionRepository;
import at.jku.cis.iVolunteer.configurator.meta.core.property.definition.treeProperty.TreePropertyDefinitionRepository;
import at.jku.cis.iVolunteer.configurator.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.flatProperty.FlatPropertyDefinition;

@Service
public class InitializationService {

	@Autowired protected ClassDefinitionRepository classDefinitionRepository;
	@Autowired protected RelationshipRepository relationshipRepository;
	@Autowired protected FlatPropertyDefinitionRepository flatPropertyDefinitionRepository;
	@Autowired protected ClassConfigurationRepository classConfigurationRepository;
	@Autowired protected MatchingConfigurationRepository matchingConfigurationRepository;
	@Autowired protected MatchingEntityMappingConfigurationRepository matchingCollectorConfigurationRepository;
	@Autowired protected TreePropertyDefinitionRepository treePropertyDefinitionRepository;
	@Autowired protected ClassConfigurationController classConfigurationController;


	@Autowired public StandardPropertyDefinitions standardPropertyDefinitions;


	private List<String> tenantIds;
	
	
	@PostConstruct
	public void initOnConstruct() {
		List<String> tenants = new ArrayList<>();
		tenants.add("5f92c841eada0c0d9dfa877b");
		tenants.add("5f92c841eada0c0d9dfa877a");
		
		init(tenants);
	}

	public void init(List<String> tenantIds) {
		this.tenantIds = tenantIds;
		
		if (tenantIds != null) {
			addiVolunteerPropertyDefinitions();
			addGenericPropertyDefintions();
			addHeaderPropertyDefintions();
			addClassConfigurations();
		}
	}

	public void addiVolunteerPropertyDefinitions() {

		tenantIds.forEach(tenant -> {
			for (FlatPropertyDefinition<Object> pd : standardPropertyDefinitions.getAlliVolunteer()) {
				if (flatPropertyDefinitionRepository.getByNameAndTenantId(pd.getName(), tenant).size() == 0) {
					pd.setTenantId(tenant);
					flatPropertyDefinitionRepository.save(pd);
				}
			}
		});
	}

	public void addFlexProdPropertyDefinitions() {
		tenantIds.forEach(tenant -> {
			for (FlatPropertyDefinition<Object> pd : standardPropertyDefinitions
					.getAllFlexProdProperties()) {
				if (flatPropertyDefinitionRepository.getByNameAndTenantId(pd.getName(), tenant).size() == 0) {
					pd.setTenantId(tenant);
					flatPropertyDefinitionRepository.save(pd);
				}
			}
		});
	}

	public void addGenericPropertyDefintions() {
		tenantIds.forEach(tenant -> {
			for (FlatPropertyDefinition<Object> pd : standardPropertyDefinitions.getAllGeneric()) {
				if (flatPropertyDefinitionRepository.getByNameAndTenantId(pd.getName(), tenant).size() == 0) {
					pd.setTenantId(tenant);
					flatPropertyDefinitionRepository.save(pd);
				}
			}
		});
	}

	public void addHeaderPropertyDefintions() {
		tenantIds.forEach(tenant -> {
			for (FlatPropertyDefinition<Object> pd : standardPropertyDefinitions.getAllHeader()) {
				if (flatPropertyDefinitionRepository.getByNameAndTenantId(pd.getName(), tenant).size() == 0) {
					pd.setTenantId(tenant);
					flatPropertyDefinitionRepository.save(pd);
				}
			}
		});
	}

	public void addClassConfigurations() {

		for (String tenantId : tenantIds) {
			for (int i = 1; i <= 5; i++) {
				this.classConfigurationController
						.createNewClassConfiguration(new String[] {"slot" + i, "",  tenantId });
			}
		}
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
	
	public void deleteProperties() {
		flatPropertyDefinitionRepository.deleteAll();
	}

}
