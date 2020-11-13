package at.jku.cis.iVolunteer.configurator.core;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.configurator.configurations.clazz.ClassConfigurationController;
import at.jku.cis.iVolunteer.configurator.configurations.clazz.ClassConfigurationRepository;
import at.jku.cis.iVolunteer.configurator.configurations.matching.collector.MatchingEntityMappingConfigurationRepository;
import at.jku.cis.iVolunteer.configurator.configurations.matching.configuration.MatchingConfigurationRepository;
import at.jku.cis.iVolunteer.configurator.configurations.matching.relationships.MatchingOperatorRelationshipRepository;
import at.jku.cis.iVolunteer.configurator.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.configurator.meta.core.property.definition.flatProperty.FlatPropertyDefinitionRepository;
import at.jku.cis.iVolunteer.configurator.meta.core.property.definition.treeProperty.TreePropertyDefinitionRepository;
import at.jku.cis.iVolunteer.configurator.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.configurator.model._httprequests.ClassConfiguratorResponseRequestBody;
import at.jku.cis.iVolunteer.configurator.model._httprequests.InitConfiguratorRequest;
import at.jku.cis.iVolunteer.configurator.model._httprequests.UrlIdRequestBody;
import at.jku.cis.iVolunteer.configurator.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.Tuple;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.flatProperty.FlatPropertyDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.treeProperty.TreePropertyDefinition;
import at.jku.cis.iVolunteer.configurator.response.SendResponseController;

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
	@Autowired protected MatchingOperatorRelationshipRepository matchingOperatorRelationshipRepository;
	@Autowired protected SendResponseController sendResponseRestClient;
	@Autowired protected StandardPropertyDefinitions standardPropertyDefinitions;

	private List<Tuple<String,String>> tenantIds;
	private String marketplaceUrl;

//	@PostConstruct
//	public void initOnConstruct() {
////		List<String> tenants = new ArrayList<>();
////		tenants.add("5f92c841eada0c0d9dfa877b");
////		tenants.add("5f92c841eada0c0d9dfa877a");
////		
////		init(tenants);
//	}

	public void init(InitConfiguratorRequest initRequestBody) {
		this.tenantIds = initRequestBody.getTenantIds();
		this.marketplaceUrl = initRequestBody.getMpUrl();

		if (tenantIds != null) {
			addiVolunteerPropertyDefinitions();
			addGenericPropertyDefintions();
			addHeaderPropertyDefintions();
			
			
			addMichaTestProperties();

			
			addClassConfigurations(1);
			addFlexProdPropertyDefinitions();
			addFlexProdClassDefinitionsAndConfigurations();
			
		}

	}

	public void addiVolunteerPropertyDefinitions() {

		for (int i = 0; i < tenantIds.size(); i++) {
			for (FlatPropertyDefinition<Object> pd : standardPropertyDefinitions.getAlliVolunteer()) {
				if (flatPropertyDefinitionRepository.getByNameAndTenantId(pd.getName(), tenantIds.get(i).getId()).size() == 0) {
					pd.setTenantId(tenantIds.get(i).getId());
					flatPropertyDefinitionRepository.save(pd);
				}
			}
//			String key = (tenantIds.get(i).getLabel().equals("FF Eidenberg")) ? "FF" : "MV";
			String key = "FF";
			for (TreePropertyDefinition tpd : standardPropertyDefinitions.getAllTreeProperties(key)) {
				if (flatPropertyDefinitionRepository.getByNameAndTenantId(tpd.getName(), tenantIds.get(i).getId())
						.size() == 0) {
					tpd.setTenantId(tenantIds.get(i).getId());
					treePropertyDefinitionRepository.save(tpd);
				}
			}
		}
	}

	public void addFlexProdPropertyDefinitions() {
		tenantIds.forEach(tenant -> {
			for (FlatPropertyDefinition<Object> pd : standardPropertyDefinitions.getAllFlexProdProperties()) {
				if (flatPropertyDefinitionRepository.getByNameAndTenantId(pd.getName(), tenant.getId()).size() == 0) {
					pd.setTenantId(tenant.getId());
					flatPropertyDefinitionRepository.save(pd);
				}
			}
		});
	}
	
	public void addMichaTestProperties() {
				
		tenantIds.forEach(tenant -> {
			for (FlatPropertyDefinition<Object> pd : standardPropertyDefinitions.getAllTest()) {
				if (flatPropertyDefinitionRepository.getByNameAndTenantId(pd.getName(), tenant.getId()).size() == 0) {
					pd.setTenantId(tenant.getId());
					flatPropertyDefinitionRepository.save(pd);
				}
			}
		});
	}
	

	public void addGenericPropertyDefintions() {
		tenantIds.forEach(tenant -> {
			for (FlatPropertyDefinition<Object> pd : standardPropertyDefinitions.getAllGeneric()) {
				if (flatPropertyDefinitionRepository.getByNameAndTenantId(pd.getName(), tenant.getId()).size() == 0) {
					pd.setTenantId(tenant.getId());
					flatPropertyDefinitionRepository.save(pd);
				}
			}
		});
	}

	public void addHeaderPropertyDefintions() {
		tenantIds.forEach(tenant -> {
			for (FlatPropertyDefinition<Object> pd : standardPropertyDefinitions.getAllHeader()) {
				if (flatPropertyDefinitionRepository.getByNameAndTenantId(pd.getName(), tenant.getId()).size() == 0) {
					pd.setTenantId(tenant.getId());
					flatPropertyDefinitionRepository.save(pd);
				}
			}
		});
	}

	public void addClassConfigurations(int noOfConfigurations) {

		for (Tuple<String,String> tenant : tenantIds) {
			for (int i = 1; i <= noOfConfigurations; i++) {
				ClassConfiguration cc = this.classConfigurationController
						.createNewClassConfiguration(new String[] { tenant.getId(), "Standardkonfiguration" + i, "" });

				// TODO send new stuff to mp
				UrlIdRequestBody body = new UrlIdRequestBody();
				body.setAction("save");
				body.setIdToSave(cc.getId());
				

				body.setUrl(marketplaceUrl + "/response/class-configurator");
								
				sendResponseRestClient.sendClassConfiguratorResponse(body);
				
				
			}
		}
	}

	public void addFlexProdClassDefinitionsAndConfigurations() {
		for (Tuple<String,String> tenant : tenantIds) {
			this.classConfigurationController.createAndSaveFlexProdClassConfigurations(tenant.getId());
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
		treePropertyDefinitionRepository.deleteAll();
	}

	public void wipeConfigurator() {
		deleteClassDefinitions();
		deleteRelationships();
		deleteClassConfigurations();
		deleteMatchingConfigurations();
		deleteProperties();
		matchingConfigurationRepository.deleteAll();
	}

}
