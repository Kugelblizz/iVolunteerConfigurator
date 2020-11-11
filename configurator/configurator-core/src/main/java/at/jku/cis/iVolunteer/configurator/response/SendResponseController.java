package at.jku.cis.iVolunteer.configurator.response;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.configurator.configurations.clazz.ClassConfigurationController;
import at.jku.cis.iVolunteer.configurator.configurations.matching.configuration.MatchingConfigurationService;
import at.jku.cis.iVolunteer.configurator.configurations.matching.relationships.MatchingOperatorRelationshipController;
import at.jku.cis.iVolunteer.configurator.meta.core.class_.ClassDefinitionService;
import at.jku.cis.iVolunteer.configurator.meta.core.relationship.RelationshipController;
import at.jku.cis.iVolunteer.configurator.model._httprequests.ClassConfiguratorResponseRequestBody;
import at.jku.cis.iVolunteer.configurator.model._httprequests.ClassInstanceConfiguratorResponseRequestBody;
import at.jku.cis.iVolunteer.configurator.model._httprequests.MatchingConfiguratorResponseRequestBody;
import at.jku.cis.iVolunteer.configurator.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.configurator.model.configurations.matching.MatchingConfiguration;
import at.jku.cis.iVolunteer.configurator.model.configurations.matching.MatchingOperatorRelationship;
import at.jku.cis.iVolunteer.configurator.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.configurator.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.configurator.model.meta.core.relationship.RelationshipDTO;

@RestController
public class SendResponseController {
	
	@Autowired private ClassDefinitionService classDefinitionService;
	@Autowired private RelationshipController relationshipController;
	@Autowired private ClassConfigurationController ClassConfigurationController;
	@Autowired private MatchingConfigurationService matchingConfigurationService;
	@Autowired private ResponseRestClient responseRestClient;
	@Autowired private MatchingOperatorRelationshipController matchingOperatorRelationshipController;
	
	@PostMapping("/send-response/class-configurator")
	public void sendClassConfiguratorResponse(@RequestBody Map<String, Object> body) {
		ClassConfiguratorResponseRequestBody responseRequestBody = new ClassConfiguratorResponseRequestBody();
		
		ClassConfiguration classConfiguration = ClassConfigurationController.getClassConfigurationById((String)body.get("id"));
		responseRequestBody.setClassConfiguration(classConfiguration);
		
		List<ClassDefinition> classDefinitions = classDefinitionService.getClassDefinitonsById(classConfiguration.getClassDefinitionIds());
		responseRequestBody.setClassDefinitions(classDefinitions);
		
		List<RelationshipDTO> relationships = relationshipController.getRelationshipsByIdAsDTO(classConfiguration.getRelationshipIds());
		responseRequestBody.setRelationships(relationships);
		
		HttpStatus status = responseRestClient.sendClassConfiguratorResponse((String)body.get("url"), responseRequestBody);
		
		for (String s : body.keySet()) {
			System.out.println(s);
			System.out.println(body.get(s));
		}
		
	}
	
	@PostMapping("/send-response/class-instance-configurator")
	public void sendClassInstanceConfiguratorResponse(@RequestBody Map<String, ?> body) {
		ClassInstanceConfiguratorResponseRequestBody responseRequestBody = new ClassInstanceConfiguratorResponseRequestBody();
		responseRequestBody.setClassInstance((ClassInstance)body.get("classInstance"));
		
		HttpStatus status = responseRestClient.sendClassInstanceConfiguratorResponse((String) body.get("url"), responseRequestBody);

		for (String s : body.keySet()) {
			System.out.println(s);			
			System.out.println(body.get(s));
		}
		
	}
	
	@PostMapping("/send-response/matching-configurator")
	public void sendMatchingConfiguratorResponse(@RequestBody Map<String, ?> body) {
		MatchingConfiguratorResponseRequestBody responseRequestBody = new MatchingConfiguratorResponseRequestBody();
		
		MatchingConfiguration matchingConfiguration = matchingConfigurationService.getAllMatchingConfigurationsById((String)body.get("id"));
		responseRequestBody.setMatchingConfiguration(matchingConfiguration);
		
		List<MatchingOperatorRelationship> matchingRelationships = matchingOperatorRelationshipController.getMatchingOperatorRelationshipByMatchingConfiguration(matchingConfiguration.getId());
		responseRequestBody.setMatchingRelationships(matchingRelationships);
		
		HttpStatus status = responseRestClient.sendMatchingConfiguratorResponse((String)body.get("url"), responseRequestBody);

		for (String s : body.keySet()) {
			System.out.println(s);
			System.out.println(body.get(s));
		}
		
	}

}
