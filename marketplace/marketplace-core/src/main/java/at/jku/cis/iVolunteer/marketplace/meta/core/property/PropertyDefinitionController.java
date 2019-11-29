package at.jku.cis.iVolunteer.marketplace.meta.core.property;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;

@RestController
public class PropertyDefinitionController {

	@Autowired PropertyDefinitionRepository propertyDefinitionRepository;

	@GetMapping("/meta/core/property/definition/all")
	private List<PropertyDefinition<Object>> getAllPropertyDefinitions() {
		return propertyDefinitionRepository.findAll();
	}

	@GetMapping("/meta/core/property/definition/{id}")
	private PropertyDefinition<Object> getPropertyDefinitionById(@PathVariable("id") String id) {
		return propertyDefinitionRepository.findOne(id);
	}

	@PostMapping("/meta/core/property/definition/new")
	private List<PropertyDefinition<Object>> createNewPropertyDefintion(
			@RequestBody List<PropertyDefinition<Object>> propertyDefinitionDTOs) {
		List<PropertyDefinition<Object>> propertyDefinitions = propertyDefinitionDTOs;

		for (PropertyDefinition<Object> pd : propertyDefinitions) {
			pd.setCustom(true);
		}

		return propertyDefinitionRepository.save(propertyDefinitions);
	}

	@PutMapping("/meta/core/property/definition/{id}/update")
	private List<PropertyDefinition<Object>> updatePropertyDefinition(
			@RequestBody List<PropertyDefinition<Object>> propertyDefinitionDTOs) {
		return this.createNewPropertyDefintion(propertyDefinitionDTOs);
	}

	@DeleteMapping("/meta/core/property/definition/{id}/delete")
	private void deletePropertyDefinition(@PathVariable("id") String id) {
		propertyDefinitionRepository.delete(id);
	}

}