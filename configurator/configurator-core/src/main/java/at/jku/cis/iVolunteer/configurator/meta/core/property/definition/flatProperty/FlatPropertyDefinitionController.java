package at.jku.cis.iVolunteer.configurator.meta.core.property.definition.flatProperty;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.configurator.model.meta.core.property.definition.flatProperty.FlatPropertyDefinition;

@RestController
public class FlatPropertyDefinitionController {

	@Autowired FlatPropertyDefinitionRepository propertyDefinitionRepository;

	@GetMapping("/property-definition/flat/all")
	private List<FlatPropertyDefinition<Object>> getAllPropertyDefinitions() {
		return propertyDefinitionRepository.findAll();
	}

	@GetMapping("/property-definition/flat/{id}")
	private FlatPropertyDefinition<Object> getPropertyDefinitionById(@PathVariable("id") String id) {
	
		FlatPropertyDefinition<Object> findOne = propertyDefinitionRepository.findOne(id);
		return findOne;
		
	}

	@PostMapping("/property-definition/flat/new")
	private List<FlatPropertyDefinition<Object>> createNewPropertyDefintion(
			@RequestBody List<FlatPropertyDefinition<Object>> propertyDefinitions) {
		
//		for (PropertyDefinition<Object> pd : propertyDefinitions) {
//			pd.setCustom(true);
//		}

		return propertyDefinitionRepository.save(propertyDefinitions);
	}

	@PutMapping("/property-definition/flat/{id}/update")
	private List<FlatPropertyDefinition<Object>> updatePropertyDefinition(
			@RequestBody List<FlatPropertyDefinition<Object>> propertyDefinitions) {
		return this.createNewPropertyDefintion(propertyDefinitions);
	}

	@DeleteMapping("/property-definition/flat/{id}/delete")
	private void deletePropertyDefinition(@PathVariable("id") String id) {
		propertyDefinitionRepository.delete(id);
	}

}
