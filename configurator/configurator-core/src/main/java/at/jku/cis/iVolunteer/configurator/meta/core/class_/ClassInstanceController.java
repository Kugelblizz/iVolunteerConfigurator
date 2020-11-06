package at.jku.cis.iVolunteer.configurator.meta.core.class_;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.configurator._mapper.clazz.ClassDefinitionToInstanceMapper;
import at.jku.cis.iVolunteer.configurator._mapper.clazz.ClassInstanceMapper;
import at.jku.cis.iVolunteer.configurator.commons.DateTimeService;
import at.jku.cis.iVolunteer.configurator.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.configurator.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.configurator.model.meta.core.clazz.ClassInstance;
import at.jku.cis.iVolunteer.configurator.model.meta.core.clazz.ClassInstanceDTO;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.PropertyType;

@RestController
public class ClassInstanceController {

	@Autowired
	private ClassDefinitionService classDefinitionService;
	@Autowired
	private ClassInstanceMapper classInstanceMapper;
	@Autowired
	private ClassDefinitionToInstanceMapper classDefinitionToInstanceMapper;
	@Autowired
	private DateTimeService dateTimeService;

	

	//TODO
	@PostMapping("/meta/core/class/instance/new")
	public List<ClassInstance> createNewClassInstances(@RequestBody List<ClassInstance> classInstances) {
		System.out.println("create new class instance TODO");
		return null;
//		return classInstanceRepository.save(classInstances);
	}



}
