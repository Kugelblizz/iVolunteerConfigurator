package at.jku.cis.iVolunteer.configurator.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitializationController {

	@Autowired private InitializationService initializationService;
	
	@PutMapping("/init/add-test-data")
	public void addTestData() {
		addAllProperties();
	}

	
	/**
	 * Properties
	 */
	
	@PutMapping("/init/add-properties/iVolunteer")
	public void addAllProperties() {
		initializationService.addiVolunteerPropertyDefinitions();
	}
	
	@PutMapping("/init/delete-properties")
	public void deleteProperties() {
		initializationService.propertyDefinitionRepository.deleteAll();
	}
	
	@PutMapping("/init/add-properties/header")
	public void addHeaderProperties() {
		initializationService.addHeaderPropertyDefintions();
	}	
	
	@PutMapping("/init/add-properties/generic")
	public void addGenericProperties() {
		initializationService.addGenericPropertyDefintions();
	}
	
	@PutMapping("/init/add-properties/flexprod")
	public void addFlexProdProperties() {
		initializationService.addFlexProdPropertyDefinitions();
	}
	
	/**
	 * Enum-Definitions
	 */
	
	@PutMapping("/init/delete-enumdefinitions")
	public void deleteEnumDefinitions() {
		initializationService.treePropertyDefinitionRepository.deleteAll();
	}
	
	/**
	 * Class-Definitions and Configurations
	 */
	
	
	@PutMapping("/init/delete-class-definitions")
	public void deleteClassDefinitions() {
		initializationService.classDefinitionRepository.deleteAll();
	}
	
	@PutMapping("/init/delete-relationships")
	public void deleteRelationships() {
		initializationService.relationshipRepository.deleteAll();
	}
	
	@PutMapping("/init/delete-class-configurations")
	public void deleteClassConfigurations() {
		initializationService.classConfigurationRepository.deleteAll();
	}
	
	/**
	 * Matching
	 */
	
	@PutMapping("/init/delete-matching-collector-configurations")
	public void deleteMatchingCollectorConfigurations() {
		initializationService.matchingCollectorConfigurationRepository.deleteAll();
	}
	
	

	
	@PutMapping("/init/wipe-marketplace")
	public void wipeMarketplace() {
		deleteClassConfigurations();
		deleteClassDefinitions();
		deleteRelationships();
		deleteProperties();
		deleteMatchingCollectorConfigurations();
		deleteEnumDefinitions();
	}
	
	
}
