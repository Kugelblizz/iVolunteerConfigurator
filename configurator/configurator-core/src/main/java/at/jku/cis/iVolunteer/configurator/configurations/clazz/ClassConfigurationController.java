package at.jku.cis.iVolunteer.configurator.configurations.clazz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.configurator._mapper.property.PropertyDefinitionToClassPropertyMapper;
import at.jku.cis.iVolunteer.configurator.configurations.matching.collector.MatchingEntityMappingConfigurationRepository;
import at.jku.cis.iVolunteer.configurator.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.configurator.meta.core.class_.CollectionService;
import at.jku.cis.iVolunteer.configurator.meta.core.property.definition.flatProperty.FlatPropertyDefinitionRepository;
import at.jku.cis.iVolunteer.configurator.meta.core.relationship.RelationshipRepository;
import at.jku.cis.iVolunteer.configurator.model.SaveClassConfigurationRequest;
import at.jku.cis.iVolunteer.configurator.model.configurations.clazz.ClassConfiguration;
import at.jku.cis.iVolunteer.configurator.model.configurations.clazz.ClassConfigurationDTO;
import at.jku.cis.iVolunteer.configurator.model.configurations.matching.collector.MatchingEntityMappingConfiguration;
import at.jku.cis.iVolunteer.configurator.model.matching.MatchingEntityMappings;
import at.jku.cis.iVolunteer.configurator.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.configurator.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.flatProperty.FlatPropertyDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.relationship.Inheritance;
import at.jku.cis.iVolunteer.configurator.model.meta.core.relationship.Relationship;
import at.jku.cis.iVolunteer.configurator.model.meta.core.relationship.RelationshipType;

@RestController
public class ClassConfigurationController {

	@Autowired private ClassConfigurationRepository classConfigurationRepository;
	@Autowired private CollectionService collectionService;

	@Autowired private MatchingEntityMappingConfigurationRepository matchingCollectorConfigurationRepository;
	@Autowired private ClassDefinitionRepository classDefinitionRepository;
	@Autowired private RelationshipRepository relationshipRepository;
	@Autowired private FlatPropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired private PropertyDefinitionToClassPropertyMapper propertyDefinitionToClassPropertyMapper;

	@GetMapping("class-configuration/all")
	List<ClassConfiguration> getAllClassConfigurations() {
		return classConfigurationRepository.findAll();
	}

	@GetMapping("class-configuration/all/tenant/{tenantId}")
	List<ClassConfiguration> getAllClassConfigurations(@PathVariable("tenantId") String tenantId) {
		return classConfigurationRepository.findByTenantId(tenantId);
	}

	@GetMapping("class-configuration/{id}")
	public ClassConfiguration getClassConfigurationById(@PathVariable("id") String id) {
		return classConfigurationRepository.findOne(id);
	}

	@GetMapping("class-configuration/by-name/{name}")
	public List<ClassConfiguration> getClassConfigurationByName(@PathVariable("name") String name) {
		return classConfigurationRepository.findByName(name);
	}

	@GetMapping("class-configuration/all-in-one/{id}")
	public ClassConfigurationDTO getAllForClassConfigurationInOne(@PathVariable("id") String id) {
		ClassConfiguration classConfiguration = classConfigurationRepository.findOne(id);

		List<ClassDefinition> classDefinitions = new ArrayList<>();
		classDefinitionRepository.findAll(classConfiguration.getClassDefinitionIds()).forEach(classDefinitions::add);
		;

		List<Relationship> relationships = new ArrayList<>();
		relationshipRepository.findAll(classConfiguration.getRelationshipIds()).forEach(relationships::add);

		ClassConfigurationDTO dto = new ClassConfigurationDTO(classConfiguration, classDefinitions, relationships);
		return dto;
	}

	@PostMapping("class-configuration/new-empty")
	public ClassConfiguration createNewEmptyClassConfiguration(@RequestBody String[] params) {
		if (params.length != 2) {
			return null;
		}

		ClassConfiguration classConfiguration = new ClassConfiguration();
		classConfiguration.setName(params[0]);
		classConfiguration.setDescription(params[1]);
		classConfiguration.setTimestamp(new Date());

		return saveClassConfiguration(classConfiguration);
	}

	@PostMapping("class-configuration/new")
	public ClassConfiguration createNewClassConfiguration(@RequestBody String[] params) {
		if (params.length != 3) {
			return null;
		}

		ClassConfiguration newClassConfiguration = createAndSaveNewClassConfiguration(params[0], params[1], params[2],
				null);
		return newClassConfiguration;
	}

	@PutMapping("class-configuration/save-everything")
	public ClassConfiguration saveEverything(@RequestBody SaveClassConfigurationRequest req) {
		System.out.println("tessst");
		return null;
	}

	@PutMapping("class-configuration/save")
	public ClassConfiguration saveClassConfiguration(@RequestBody ClassConfiguration updatedClassConfiguration) {

		updatedClassConfiguration.setTimestamp(new Date());

		ClassConfiguration classConfiguration = classConfigurationRepository.save(updatedClassConfiguration);

		List<ClassDefinition> classDefinitions = new ArrayList<>();
		classDefinitionRepository.findAll(updatedClassConfiguration.getClassDefinitionIds())
				.forEach(classDefinitions::add);

		if (classDefinitions != null) {
			classDefinitions = updateClassDefinitions(classDefinitions, classConfiguration);
		}

		classDefinitionRepository.save(classDefinitions);

		// Build MatchingCollector
		MatchingEntityMappings mappings = collectionService
				.collectAllClassDefinitionsWithPropertiesAsMatchingEntityMappings(classConfiguration.getId());

		MatchingEntityMappingConfiguration matchingCollectorConfiguration = new MatchingEntityMappingConfiguration();
		matchingCollectorConfiguration.setId(classConfiguration.getId());
		matchingCollectorConfiguration.setClassConfigurationId(classConfiguration.getId());
		matchingCollectorConfiguration.setMappings(mappings);

		matchingCollectorConfigurationRepository.save(matchingCollectorConfiguration);
		return classConfiguration;
	}

	private List<ClassDefinition> updateClassDefinitions(List<ClassDefinition> classDefinitions,
			ClassConfiguration classConfiguration) {
		for (ClassDefinition cd : classDefinitions) {
			cd.setConfigurationId(classConfiguration.getId());
		}

		List<Relationship> relationships = new ArrayList<>();
		relationshipRepository.findAll(classConfiguration.getRelationshipIds()).forEach(relationships::add);

		collectionService.assignLevelsToClassDefinitions(classDefinitions, relationships);

		return classDefinitions;
	}

	@PutMapping("class-configuration/{id}/save-meta")
	public ClassConfiguration saveClassConfigurationMeta(@RequestBody String[] params, @PathVariable String id) {
		ClassConfiguration classConfiguration = classConfigurationRepository.findOne(id);

		if (params.length != 2) {
			return null;
		}

		classConfiguration.setName(params[0]);
		classConfiguration.setDescription(params[1]);

		return classConfigurationRepository.save(classConfiguration);
	}

	@DeleteMapping("class-configuration/{id}/delete")
	public void deleteClassConfiguration(@PathVariable("id") String id) {
		ClassConfiguration classConfiguration = classConfigurationRepository.findOne(id);

		classConfiguration.getClassDefinitionIds().forEach(classDefinitionRepository::delete);
		classConfiguration.getRelationshipIds().forEach(relationshipRepository::delete);

		classConfigurationRepository.delete(id);
	}

	@PutMapping("class-configuration/delete-multiple")
	public List<ClassConfiguration> deleteMultipleClassConfigurations(@RequestBody List<String> ids) {
		ids.forEach(this::deleteClassConfiguration);
		return this.classConfigurationRepository.findAll();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ClassConfiguration createAndSaveNewClassConfiguration(String configuratorName, String description,
			String tenantId, String configuratorId) {

		List<ClassDefinition> classDefinitions = new ArrayList<>();
		List<Relationship> relationships = new ArrayList<>();

		List<FlatPropertyDefinition<Object>> properties = this.propertyDefinitionRepository.findAll();

		ClassDefinition fwPassEintrag = new ClassDefinition();
		fwPassEintrag.setId(new ObjectId().toHexString());
		fwPassEintrag.setTenantId(tenantId);
		fwPassEintrag.setName("Freiwilligenpass-\nEintrag");
		fwPassEintrag.setRoot(true);
		fwPassEintrag.setClassArchetype(ClassArchetype.ROOT);
		fwPassEintrag.setWriteProtected(true);
		fwPassEintrag.setCollector(true);
		fwPassEintrag.setProperties(new ArrayList<ClassProperty<Object>>());
		fwPassEintrag.setLevel(0);

		FlatPropertyDefinition idProperty = properties.stream()
				.filter(p -> p.getName().equals("ID") && p.getTenantId().equals(tenantId)).findFirst().get();
		fwPassEintrag.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(idProperty));

		FlatPropertyDefinition nameProperty = properties.stream()
				.filter(p -> p.getName().equals("Name") && p.getTenantId().equals(tenantId)).findFirst().get();
		fwPassEintrag.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(nameProperty));

//		FlatPropertyDefinition evidenzProperty = properties.stream().filter(p -> p.getName().equals("evidenz")).findFirst()
//				.get();
//		fwPassEintrag.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(evidenzProperty));

		FlatPropertyDefinition imageLinkProperty = properties.stream()
				.filter(p -> p.getName().equals("Description") && p.getTenantId().equals(tenantId)).findFirst().get();
		fwPassEintrag.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(imageLinkProperty));
//		
//		FlatPropertyDefinition descriptionProperty = properties.stream().filter(p -> p.getName().equals("Image Link")).findFirst().get();
//		fwPassEintrag.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(descriptionProperty));
//		
//		FlatPropertyDefinition expiredProperty = properties.stream().filter(p -> p.getName().equals("Expired")).findFirst().get();
//		fwPassEintrag.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(expiredProperty));

		classDefinitions.add(fwPassEintrag);

		ClassDefinition task = new ClassDefinition();
		task.setId(new ObjectId().toHexString());
		task.setTenantId(tenantId);
		task.setName("Tätigkeit");
		task.setClassArchetype(ClassArchetype.TASK);
		task.setWriteProtected(true);
		task.setProperties(new ArrayList<>());
		task.setLevel(0);

		FlatPropertyDefinition dateFromProperty = properties.stream()
				.filter(p -> p.getName().equals("Starting Date") && p.getTenantId().equals(tenantId)).findFirst().get();
		task.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(dateFromProperty));

		FlatPropertyDefinition dateToProperty = properties.stream()
				.filter(p -> p.getName().equals("End Date") && p.getTenantId().equals(tenantId)).findFirst().get();
		task.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(dateToProperty));

		FlatPropertyDefinition locationProperty = properties.stream()
				.filter(p -> p.getName().equals("Location") && p.getTenantId().equals(tenantId)).findFirst().get();
		task.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(locationProperty));

		classDefinitions.add(task);

		Inheritance r1 = new Inheritance();
		r1.setRelationshipType(RelationshipType.INHERITANCE);
		r1.setTarget(task.getId());
		r1.setSource(fwPassEintrag.getId());

		relationships.add(r1);

		ClassDefinition competence = new ClassDefinition();
		competence.setId(new ObjectId().toHexString());
		competence.setTenantId(tenantId);
		competence.setName("Kompetenz");
		competence.setClassArchetype(ClassArchetype.COMPETENCE);
		competence.setWriteProtected(true);
		competence.setProperties(new ArrayList<>());
		competence.setLevel(0);

		classDefinitions.add(competence);

		Inheritance r2 = new Inheritance();
		r2.setRelationshipType(RelationshipType.INHERITANCE);
		r2.setTarget(competence.getId());
		r2.setSource(fwPassEintrag.getId());

		relationships.add(r2);

		ClassDefinition achievement = new ClassDefinition();
		achievement.setId(new ObjectId().toHexString());
		achievement.setTenantId(tenantId);
		achievement.setName("Verdienst");
		achievement.setClassArchetype(ClassArchetype.ACHIEVEMENT);
		achievement.setWriteProtected(true);
		achievement.setProperties(new ArrayList<>());
		achievement.setLevel(0);

		classDefinitions.add(achievement);

		Inheritance r3 = new Inheritance();
		r3.setRelationshipType(RelationshipType.INHERITANCE);
		r3.setTarget(achievement.getId());
		r3.setSource(fwPassEintrag.getId());

		relationships.add(r3);

		ClassDefinition function = new ClassDefinition();
		function.setId(new ObjectId().toHexString());
		function.setTenantId(tenantId);
		function.setName("Funktion");
		function.setClassArchetype(ClassArchetype.FUNCTION);
		function.setWriteProtected(true);
		function.setProperties(new ArrayList<>());
		function.setLevel(0);

		classDefinitions.add(function);

		Inheritance r4 = new Inheritance();
		r4.setRelationshipType(RelationshipType.INHERITANCE);
		r4.setTarget(function.getId());
		r4.setSource(fwPassEintrag.getId());

		relationships.add(r4);

		///////////////// Philipp Zeug//////////////////////////
		ClassDefinition myTask = new ClassDefinition();
		myTask.setId(new ObjectId().toHexString());
		myTask.setTenantId(tenantId);
		myTask.setName("myTask");
		myTask.setClassArchetype(ClassArchetype.TASK);
		myTask.setProperties(new ArrayList<>());

		FlatPropertyDefinition tt1 = properties.stream()
				.filter(p -> p.getName().equals("TaskType1") && p.getTenantId().equals(tenantId)).findFirst().get();
		myTask.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(tt1));

		FlatPropertyDefinition tt2 = properties.stream()
				.filter(p -> p.getName().equals("TaskType2") && p.getTenantId().equals(tenantId)).findFirst().get();
		myTask.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(tt2));

		FlatPropertyDefinition tt3 = properties.stream()
				.filter(p -> p.getName().equals("TaskType3") && p.getTenantId().equals(tenantId)).findFirst().get();
		myTask.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(tt3));

//		FlatPropertyDefinition location = properties.stream().filter(p -> p.getName().equals("Location")).findFirst().get();
//		myTask.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(location));

		FlatPropertyDefinition rank = properties.stream()
				.filter(p -> p.getName().equals("Rank") && p.getTenantId().equals(tenantId)).findFirst().get();
		myTask.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(rank));

		FlatPropertyDefinition duration = properties.stream()
				.filter(p -> p.getName().equals("Duration") && p.getTenantId().equals(tenantId)).findFirst().get();
		myTask.getProperties().add(propertyDefinitionToClassPropertyMapper.toTarget(duration));

		classDefinitions.add(myTask);

		Inheritance r5 = new Inheritance();
		r5.setRelationshipType(RelationshipType.INHERITANCE);
		r5.setTarget(myTask.getId());
		r5.setSource(task.getId());

		relationships.add(r5);

		ClassConfiguration configurator = new ClassConfiguration();
		configurator.setTimestamp(new Date());
		configurator.setTenantId(tenantId);
		configurator.setId(configuratorId);
		configurator.setName(configuratorName);
		configurator.setDescription(description);
		configurator.setClassDefinitionIds(new ArrayList<>());
		configurator.setRelationshipIds(new ArrayList<>());

		for (ClassDefinition cd : classDefinitions) {
			cd.setConfigurationId(configurator.getId());
			this.classDefinitionRepository.save(cd);
			configurator.getClassDefinitionIds().add(cd.getId());
		}

		for (Relationship r : relationships) {
			this.relationshipRepository.save(r);
			configurator.getRelationshipIds().add(r.getId());
		}

		return saveClassConfiguration(configurator);

	}

}
