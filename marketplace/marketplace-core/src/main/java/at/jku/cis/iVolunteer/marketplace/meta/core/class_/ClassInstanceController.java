package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.mapper.meta.core.class_.ClassDefinitionToInstanceMapper;
import at.jku.cis.iVolunteer.marketplace.security.LoginService;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;

@RestController
public class ClassInstanceController {

	@Autowired private ClassInstanceRepository classInstanceRepository;
	@Autowired private ClassDefinitionToInstanceMapper classDefinition2InstanceMapper;
	@Autowired private ClassDefinitionService classDefinitionService;
	@Autowired private LoginService loginService;

	@Autowired private ClassInstanceMapper classInstanceMapper;

	@GetMapping("/meta/core/class/instance/all")
	private List<ClassInstance> getAllClassInstances() {
		return classInstanceRepository.findAll();
	}

	@GetMapping("/meta/core/class/instance/{id}")
	private ClassInstance getClassInstanceById(@PathVariable("id") String id) {
		return classInstanceRepository.findOne(id);
	}

	@GetMapping("/meta/core/class/instance/all/by-archetype/{archetype}")
	private List<ClassInstanceDTO> getClassInstancesByArchetype(@PathVariable("archetype") ClassArchetype archeType,
			@RequestParam(value = "org", required = false) String organisation) {
		List<ClassInstance> classInstances = new ArrayList<>();
		List<ClassDefinition> classDefinitions = classDefinitionService.getClassDefinitionsByArchetype(archeType,
				organisation == null ? "FF" : organisation);
		if (!organisation.equals("MV")) {
			for (ClassDefinition cd : classDefinitions) {
				classInstances.addAll(classInstanceRepository.getByClassDefinitionId(cd.getId()));
			}
		} else {
			for (ClassDefinition cd : classDefinitions) {
				classInstances.addAll(classInstanceRepository.getByClassDefinitionId(cd.getId()).stream()
						.filter(ci -> ci.isMV()).collect(Collectors.toList()));
			}
		}

		return classInstanceMapper.mapToDTO(classInstances);
	}

	@GetMapping("/meta/core/class/instance/all/by-archetype/{archetype}/user")
	private List<ClassInstanceDTO> getClassInstancesByClassDefinitionIdFake(
			@PathVariable("archetype") ClassArchetype archeType) {
		List<ClassInstance> classInstances = new ArrayList<>();
		List<ClassDefinition> classDefinitions = classDefinitionService.getClassDefinitionsByArchetype(archeType, "FF");

		for (ClassDefinition cd : classDefinitions) {
			classInstances.addAll(classInstanceRepository
					.getByUserIdAndClassDefinitionId(loginService.getLoggedInParticipant().getId(), cd.getId()));
		}

		return classInstanceMapper.mapToDTO(classInstances);
	}

	@GetMapping("/meta/core/class/instance/by-userid/{userId}")
	private List<ClassInstance> getClassInstanceByUserId(@PathVariable("userId") String userId) {
		return classInstanceRepository.getByUserId(userId);
	}

	@GetMapping("/meta/core/class/instance/in-user-inbox/{userId}")
	private List<ClassInstance> getClassInstanceInUserInbox(@PathVariable("userId") String userId) {
		return classInstanceRepository.getByUserIdAndInUserRepositoryAndInIssuerInbox(userId, false, false);
	}

	@GetMapping("/meta/core/class/instance/in-user-repository/{userId}")
	private List<ClassInstanceDTO> getClassInstanceInUserRepostory(@PathVariable("userId") String userId) {
		return classInstanceMapper
				.mapToDTO(classInstanceRepository.getByUserIdAndInUserRepositoryAndInIssuerInbox(userId, true, false));
	}

	@GetMapping("/meta/core/class/instance/in-issuer-inbox/{issuerId}")
	private List<ClassInstance> getClassInstanceInIssuerInbox(@PathVariable("issuerId") String issuerId) {
		List<ClassInstance> instances = classInstanceRepository
				.getByIssuerIdAndInIssuerInboxAndInUserRepository(issuerId, true, false);
		return instances;
	}

	@PutMapping("/meta/core/class/instance/set-in-user-repository/{inUserRepository}")
	private List<ClassInstance> setClassInstancesInUserRepository(
			@PathVariable("inUserRepository") boolean inUserRepository, @RequestBody List<String> classInstanceIds) {
		List<ClassInstance> classInstances = new ArrayList<>();
		classInstanceRepository.findAll(classInstanceIds).forEach(classInstances::add);

		for (ClassInstance classInstance : classInstances) {
			classInstance.setInUserRepository(inUserRepository);
		}

		return classInstanceRepository.save(classInstances);
	}

	@PutMapping("/meta/core/class/instance/set-in-issuer-inbox/{inIssuerInbox}")
	private List<ClassInstanceDTO> setClassInstancesInIssuerInbox(@PathVariable("inIssuerInbox") boolean inIssuerInbox,
			@RequestBody List<String> classInstanceIds) {
		List<ClassInstance> classInstances = new ArrayList<>();
		classInstanceRepository.findAll(classInstanceIds).forEach(classInstances::add);

		for (ClassInstance classInstance : classInstances) {
			classInstance.setInIssuerInbox(inIssuerInbox);
			classInstance.setInUserRepository(false);
		}

		return classInstanceMapper.mapToDTO(classInstanceRepository.save(classInstances));
	}

	@PostMapping("/meta/core/class/instance/new")
	public List<ClassInstance> createNewClassInstances(@RequestBody List<ClassInstance> classInstances) {

		for (ClassInstance classInstance : classInstances) {
			classInstance.setInIssuerInbox(true);
			classInstance.setInUserRepository(false);
		}
		return classInstanceRepository.save(classInstances);

	}

	@PostMapping("/meta/core/class/instance/{id}/new")
	private ClassInstance createNewClassInstanceById() {
		// TODO
		return null;
	}

	@PutMapping("/meta/core/class/instance/{id}/update")
	private ClassInstance updateClassInstance() {
		// TODO
		return null;
	}

	@DeleteMapping("/meta/core/class/instance/delete")
	private void deleteClassInstance() {
		// TODO
	}

}
