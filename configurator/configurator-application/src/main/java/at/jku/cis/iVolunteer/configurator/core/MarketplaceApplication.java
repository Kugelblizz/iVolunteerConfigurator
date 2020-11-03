package at.jku.cis.iVolunteer.configurator.core;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.iVolunteer.configurator.configurations.clazz.ClassConfigurationRepository;
import at.jku.cis.iVolunteer.configurator.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.configurator.meta.core.property.definition.flatProperty.FlatPropertyDefinitionRepository;
import at.jku.cis.iVolunteer.configurator.meta.core.relationship.RelationshipRepository;

@SpringBootApplication
@EnableScheduling
public class MarketplaceApplication {

	@Autowired
	private ClassConfigurationRepository configuratorRepository;
	@Autowired
	private ClassDefinitionRepository classDefinitionRepository;
	@Autowired
	private RelationshipRepository relationshipRepository;
	@Autowired
	private FlatPropertyDefinitionRepository propertyDefinitionRepository;

	public static void main(String[] args) {
		SpringApplication.run(MarketplaceApplication.class, args);
	}

	@Bean
	@Primary
	public RestTemplate produceRestTemplate() {
		return new RestTemplate();
	}

	@PreDestroy
	public void onExit() {
		FinalizationService finalizationService = new FinalizationService();
		finalizationService.destroy(configuratorRepository, classDefinitionRepository,
				relationshipRepository, propertyDefinitionRepository);
	}

}
