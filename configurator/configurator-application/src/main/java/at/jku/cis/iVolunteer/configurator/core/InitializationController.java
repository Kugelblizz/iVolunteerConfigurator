package at.jku.cis.iVolunteer.configurator.core;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitializationController {

	@Autowired private InitializationService initializationService;

	@PutMapping("/init/configurator/all")
	public void addTestData(@RequestBody List<String> tenantIds) {
//		addAllProperties();
		System.out.println("test put mapping");
		initializationService.init(tenantIds);
	}
	

	@GetMapping("/init/test-url-encoding")
	public void initTest() {

		List<String> urls = new LinkedList<String>();
		urls.add("http://localhost:8080/response/class-instance-configurator");
		urls.add("http://localhost:8080/response/class-configurator");
		urls.add("http://localhost:8080/response/matching-configurator");

		try {
			for (String url : urls) {
				String encoded = URLEncoder.encode(url, "UTF-8");
				System.out.println(encoded);
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
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
		initializationService.deleteProperties();
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

	@PutMapping("init/delete-matching-configurations")
	public void deleteMatchingConfigurations() {
		initializationService.deleteMatchingConfigurations();
	}
	
	@PutMapping("/init/delete-matching-collector-configurations")
	public void deleteMatchingCollectorConfigurations() {
		initializationService.matchingCollectorConfigurationRepository.deleteAll();
	}

	@PutMapping("/init/wipe-configurator")
	public void wipeMarketplace() {
		deleteClassConfigurations();
		deleteClassDefinitions();
		deleteRelationships();
		deleteProperties();
		deleteMatchingCollectorConfigurations();
		deleteMatchingConfigurations();
		deleteEnumDefinitions();
	}

}
