package at.jku.cis.iVolunteer.configurator.response;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.configurator.configurations.clazz.ClassConfigurationController;
import at.jku.cis.iVolunteer.configurator.configurations.matching.configuration.MatchingConfigurationService;
import at.jku.cis.iVolunteer.configurator.configurations.matching.relationships.MatchingOperatorRelationshipController;
import at.jku.cis.iVolunteer.configurator.meta.core.class_.ClassDefinitionService;
import at.jku.cis.iVolunteer.configurator.meta.core.property.definition.flatProperty.FlatPropertyDefinitionRepository;
import at.jku.cis.iVolunteer.configurator.meta.core.property.definition.treeProperty.TreePropertyDefinitionRepository;
import at.jku.cis.iVolunteer.configurator.meta.core.relationship.RelationshipController;
import at.jku.cis.iVolunteer.configurator.model._httprequests.ClassConfiguratorResponseRequestBody;
import at.jku.cis.iVolunteer.configurator.model._httprequests.ClassInstanceConfiguratorResponseRequestBody;
import at.jku.cis.iVolunteer.configurator.model._httprequests.MatchingConfiguratorResponseRequestBody;
import at.jku.cis.iVolunteer.configurator.model._httprequests.PropertyConfiguratorResponseRequestBody;
import at.jku.cis.iVolunteer.configurator.model._httprequests.FrontendClassInstanceConfiguratorRequestBody;
import at.jku.cis.iVolunteer.configurator.model._httprequests.FrontendPropertyConfiguratorRequestBody;
import at.jku.cis.iVolunteer.configurator.model._httprequests.FrontendClassAndMatchingConfiguratorRequestBody;
import at.jku.cis.iVolunteer.configurator.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.configurator.model.configurations.matching.MatchingConfiguration;
import at.jku.cis.iVolunteer.configurator.model.configurations.matching.MatchingOperatorRelationship;
import at.jku.cis.iVolunteer.configurator.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.flatProperty.FlatPropertyDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.treeProperty.TreePropertyDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.relationship.RelationshipDTO;

@RestController
public class SendResponseController {

	@Autowired private ClassDefinitionService classDefinitionService;
	@Autowired private RelationshipController relationshipController;
	@Autowired private ClassConfigurationController ClassConfigurationController;
	@Autowired private MatchingConfigurationService matchingConfigurationService;
	@Autowired private ResponseRestClient responseRestClient;
	@Autowired private MatchingOperatorRelationshipController matchingOperatorRelationshipController;
	@Autowired private FlatPropertyDefinitionRepository flatPropertyDefinitionRepository;
	@Autowired private TreePropertyDefinitionRepository treePropertyDefinitionRepository;


	@PostMapping("/send-response/class-configurator")
	public void sendClassConfiguratorResponse(@RequestBody FrontendClassAndMatchingConfiguratorRequestBody body) {
		ClassConfiguratorResponseRequestBody responseRequestBody = new ClassConfiguratorResponseRequestBody();

		ClassConfiguration classConfiguration = ClassConfigurationController
				.getClassConfigurationById(body.getIdToSave());
		responseRequestBody.setClassConfiguration(classConfiguration);

		if (classConfiguration != null) {
			List<ClassDefinition> classDefinitions = classDefinitionService
					.getClassDefinitonsById(classConfiguration.getClassDefinitionIds());
			responseRequestBody.setClassDefinitions(classDefinitions);

			List<RelationshipDTO> relationships = relationshipController
					.getRelationshipsByIdAsDTO(classConfiguration.getRelationshipIds());
			responseRequestBody.setRelationships(relationships);
		}
		
		
		responseRequestBody.setIdsToDelete(body.getIdsToDelete());
		responseRequestBody.setAction(body.getAction());

		HttpStatus status = responseRestClient.sendClassConfiguratorResponse(body.getUrl(), responseRequestBody);
	}

	@PostMapping("/send-response/class-instance-configurator")
	public void sendClassInstanceConfiguratorResponse(@RequestBody FrontendClassInstanceConfiguratorRequestBody body) {
		ClassInstanceConfiguratorResponseRequestBody responseRequestBody = new ClassInstanceConfiguratorResponseRequestBody();
		responseRequestBody.setClassInstance(body.getClassInstance());
		
		HttpStatus status = responseRestClient.sendClassInstanceConfiguratorResponse(body.getUrl(), responseRequestBody);
	}

	@PostMapping("/send-response/matching-configurator")
	public void sendMatchingConfiguratorResponse(@RequestBody FrontendClassAndMatchingConfiguratorRequestBody body) {
		MatchingConfiguratorResponseRequestBody responseRequestBody = new MatchingConfiguratorResponseRequestBody();
		MatchingConfiguration matchingConfiguration = matchingConfigurationService.getMatchingConfigurationById(body.getIdToSave());
		responseRequestBody.setMatchingConfiguration(matchingConfiguration);
		
		if (matchingConfiguration != null) {
			List<MatchingOperatorRelationship> relationships = matchingOperatorRelationshipController.getMatchingOperatorRelationshipByMatchingConfiguration(matchingConfiguration.getId());
			responseRequestBody.setMatchingRelationships(relationships);
		}
		
		responseRequestBody.setIdsToDelete(body.getIdsToDelete());
		responseRequestBody.setAction(body.getAction());

		HttpStatus status = responseRestClient.sendMatchingConfiguratorResponse(body.getUrl(),
				responseRequestBody);
	}
	
	@PostMapping("/send-response/property-configurator")
	public void sendPropertyConfiguratorResponse(@RequestBody FrontendPropertyConfiguratorRequestBody body) {
		PropertyConfiguratorResponseRequestBody responseRequestBody = new PropertyConfiguratorResponseRequestBody();
		
		responseRequestBody.setAction(body.getAction());
		
		if (body.getFlatPropertyDefinitionIds() != null) {
			List<FlatPropertyDefinition<Object>> flatPropertyDefinitions = new LinkedList<>();
			flatPropertyDefinitionRepository.findAll(body.getFlatPropertyDefinitionIds()).forEach(flatPropertyDefinitions::add);;
			responseRequestBody.setFlatPropertyDefinitions(flatPropertyDefinitions);

		}
		
		if (body.getTreePropertyDefinitionIds() != null) {
			List<TreePropertyDefinition> treePropertyDefinitions = new LinkedList<>();
			treePropertyDefinitionRepository.findAll(body.getTreePropertyDefinitionIds()).forEach(treePropertyDefinitions::add);;
			responseRequestBody.setTreePropertyDefinitions(treePropertyDefinitions);

		}
				
		HttpStatus status = responseRestClient.sendPropertyConfiguratorResponse(body.getUrl(),
				responseRequestBody);
		
	}
	
}
