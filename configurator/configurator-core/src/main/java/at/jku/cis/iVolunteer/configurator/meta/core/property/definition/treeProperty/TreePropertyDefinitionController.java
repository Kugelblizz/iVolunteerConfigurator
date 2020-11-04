package at.jku.cis.iVolunteer.configurator.meta.core.property.definition.treeProperty;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.treeProperty.TreePropertyDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.treeProperty.TreePropertyRelationship;

@RestController
public class TreePropertyDefinitionController {

	@Autowired TreePropertyDefinitionRepository treePropertyDefinitionRepository;

	@GetMapping("/property-definition/tree/all")
	private List<TreePropertyDefinition> getAllTreePropertyDefinitions() {
		return treePropertyDefinitionRepository.findAll();
	}

	@GetMapping("/property-definition/tree/{id}")
	private TreePropertyDefinition getTreePropertyDefinitionById(@PathVariable("id") String id) {
		return treePropertyDefinitionRepository.findOne(id);
	}

	@GetMapping("/property-definition/tree/by-name/{name}")
	private TreePropertyDefinition getTreePropertyDefinitionByName(@PathVariable("name") String name) {
		return treePropertyDefinitionRepository.findByName(name);
	}

	@PostMapping("/property-definition/tree/new")
	private TreePropertyDefinition newTreePropertyDefinition(@RequestBody TreePropertyDefinition treePropertyDefinition) {
		return treePropertyDefinitionRepository.save(treePropertyDefinition);
	}

	@PutMapping("/property-definition/tree/save")
	private TreePropertyDefinition replaceTreePropertyDefinition(@RequestBody TreePropertyDefinition treePropertyDefinition) {
		
		return treePropertyDefinitionRepository.save(treePropertyDefinition);
	}

	@DeleteMapping("/property-definition/tree/{id}/delete")
	private void deleteTreePropertyDefinition(@PathVariable("id") String id) {
		treePropertyDefinitionRepository.delete(id);
	}

	@PutMapping("/enum-definition/tree/delete-multiple")
	private List<TreePropertyDefinition> deleteMultipleMatchingConfigurations(@RequestBody List<String> ids) {
		ids.forEach(this.treePropertyDefinitionRepository::delete);
		return this.treePropertyDefinitionRepository.findAll();
	}

}
