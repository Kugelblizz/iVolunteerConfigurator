package at.jku.cis.iVolunteer.configurator.meta.core.class_;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.configurator._mapper.relationship.RelationshipMapper;
import at.jku.cis.iVolunteer.configurator.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.configurator.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.clazz.ClassDefinitionDTO;
import at.jku.cis.iVolunteer.configurator.model.meta.core.relationship.Relationship;
//import at.jku.cis.iVolunteer.model.meta.form.EnumEntry;
import at.jku.cis.iVolunteer.configurator.model.meta.form.FormConfiguration;
import at.jku.cis.iVolunteer.configurator.model.meta.form.FormConfigurationPreviewRequest;
import at.jku.cis.iVolunteer.configurator.model.meta.form.FormEntry;

@RestController
public class ClassDefinitionController {

	@Autowired private ClassDefinitionRepository classDefinitionRepository;
	@Autowired private RelationshipMapper relationshipMapper;
	@Autowired private ClassDefinitionService classDefinitionService;
	@Autowired private ClassDefinitionMapper classDefinitionMapper;

//	@PreAuthorize("hasAnyRole('TENANT_ADMIN', 'HELP_SEEKER')")
	@GetMapping("/meta/core/class/definition/all/")
	private List<ClassDefinition> getAllClassDefinitions(String tenantId) {
		return classDefinitionRepository.findAll();
	}

//	@GetMapping("meta/core/class/definition/all/no-enum/tenant/{tenantId}")
//	public List<ClassDefinition> getAllClassDefinitionsWithoutEnums(@PathVariable("tenantId") String tenantId) {
//		return classDefinitionService.getAllClassDefinitionsWithoutEnums(tenantId);
//	}
//
//	@GetMapping("meta/core/class/definition/all/no-enum-no-head/tenant/{tenantId}")
//	public List<ClassDefinition> getAllClassDefinitionsWithoutEnumsAndHeads(@PathVariable("tenantId") String tenantId) {
//		return classDefinitionService.getAllClassDefinitionsWithoutEnumsAndHeads(tenantId);
//	}

	@GetMapping("/meta/core/class/definition/{id}")
	private ClassDefinition getClassDefinitionById(@PathVariable("id") String id) {
		return classDefinitionService.getClassDefinitionById(id);
	}

	@GetMapping("meta/core/class/definition/{slotId}/with-properties")
	private List<ClassDefinition> getClassDefinitionsWithProperties(@PathVariable("slotId") String slotId) {
		return classDefinitionService.getAllClassDefinitionsWithProperties(slotId);
	}

//	@GetMapping("/meta/core/class/definition/{id}")
//	private ClassDefinition getClassDefinitionById(@PathVariable("id") String id) {
//		return classDefinitionService.getClassDefinitionById(id);
//	}

	@GetMapping("/meta/core/class/definition/archetype/{archetype}")
	public List<ClassDefinitionDTO> getClassDefinitionByArchetype(@PathVariable("archetype") ClassArchetype archetype) {
		return classDefinitionMapper
				.mapToDTO(classDefinitionService.getClassDefinitionsByArchetype(archetype));
	}

	@PutMapping("/meta/core/class/definition/multiple")
	private List<ClassDefinition> getClassDefinitonsById(@RequestBody List<String> ids) {
		return classDefinitionService.getClassDefinitonsById(ids);
	}

	@PostMapping("/meta/core/class/definition/new")
	private ClassDefinition newClassDefinition(@RequestBody ClassDefinition classDefinition) {
		return classDefinitionService.newClassDefinition(classDefinition);
	}

	@PutMapping("/meta/core/class/definition/{id}/change-name")
	private ClassDefinition changeClassDefinitionName(@PathVariable("id") String id, @RequestBody String newName) {
		return classDefinitionService.changeClassDefinitionName(id, newName);
	}

	@PutMapping("/meta/core/class/definition/delete")
	private List<ClassDefinition> deleteClassDefinition(@RequestBody List<String> idsToRemove) {
		return classDefinitionService.deleteClassDefinition(idsToRemove);
	}

	@PutMapping("/meta/core/class/definition/add-or-update")
	private List<ClassDefinition> addOrUpdateClassDefinitions(@RequestBody List<ClassDefinition> classDefinitions) {
		return classDefinitionService.addOrUpdateClassDefinitions(classDefinitions);
	}

//	@PutMapping("meta/core/class/definition/get-children/tenant/{tenantId}")
//	private List<FormConfiguration> getChildrenById(@RequestBody List<String> rootIds, @PathVariable("tenantId") String tenantId) {
//		return classDefinitionService.aggregateChildrenById(rootIds);
//	}
//
//	@PutMapping("meta/core/class/definition/get-parents/tenant/{tenantId}")
//	private List<FormConfiguration> getParentsById(@RequestBody List<String> childIds,
//			@PathVariable("tenantId") String tenantId) {
//		return classDefinitionService.getParentsById(childIds);
//	}

//	@GetMapping("meta/core/class/definition/enum-values/{classDefinitionId}/tenant/{tenantId}")
//	public List<EnumEntry> getEnumValues(@PathVariable("classDefinitionId") String classDefinitionId,
//			@PathVariable("tenantId") String tenantId) {
//		return collectionService.aggregateEnums(classDefinitionId);
//
//	}

	@PutMapping("meta/core/class/definition/form-configuration")
	private List<FormConfiguration> getFormConfigurations(@RequestBody List<String> ids) {

		return classDefinitionService.getClassDefinitionsById(ids);
	}

	@PutMapping("meta/core/class/definition/form-configuration-preview")
	private List<FormConfiguration> getFormConfigurationPreview(@RequestBody FormConfigurationPreviewRequest request) {
		List<Relationship> relationships = relationshipMapper.toSources(request.getRelationships());
		List<FormConfiguration> ret = classDefinitionService.getClassDefinitions(request.getClassDefinitions(),
				relationships, request.getRootClassDefinition());
		return ret;
	}

	@PutMapping("meta/core/class/definition/form-configuration-chunk")
	private FormEntry getFormConfigurationChunk(@RequestBody String[] params) {
		final String pathPrefix = params[0];
		final String choiceId = params[1];
		String[] split = pathPrefix.split("\\.");

		assert (split.length >= 1);
		final String startClassDefinitionId = split[split.length - 1];

		return classDefinitionService.getClassDefinitionChunk(pathPrefix, startClassDefinitionId, choiceId);
	}

//	@GetMapping("meta/core/class/definition/enum-values/{classDefinitionId}")
//	public List<EnumEntry> getEnumValues(@PathVariable("classDefinitionId") String classDefinitionId) {
//		return collectionService.aggregateEnums(classDefinitionId);
//	}

}