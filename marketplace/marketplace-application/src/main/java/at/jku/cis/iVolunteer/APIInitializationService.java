package at.jku.cis.iVolunteer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.cis.iVolunteer.marketplace.MarketplaceService;
import at.jku.cis.iVolunteer.marketplace._mapper.property.PropertyDefinitionToClassPropertyMapper;
import at.jku.cis.iVolunteer.marketplace.core.CoreTenantRestClient;
import at.jku.cis.iVolunteer.marketplace.meta.core.class_.ClassDefinitionRepository;
import at.jku.cis.iVolunteer.marketplace.meta.core.property.definition.flatProperty.FlatPropertyDefinitionRepository;
import at.jku.cis.iVolunteer.model.core.tenant.Tenant;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.achievement.AchievementClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.function.FunctionClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.clazz.task.TaskClassDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.flatProperty.FlatPropertyDefinition;

@Service
public class APIInitializationService {

	@Autowired private CoreTenantRestClient coreTenantRestClient;
	@Autowired private ClassDefinitionRepository classDefinitionRepository;
	@Autowired private MarketplaceService marketplaceService;
	@Autowired private FlatPropertyDefinitionRepository propertyDefinitionRepository;
	@Autowired private PropertyDefinitionToClassPropertyMapper propertyDefinitionToClassPropertyMapper;

	private List<Tenant> getTenants() {
		List<Tenant> tenants = new ArrayList<>();
		tenants = coreTenantRestClient.getAllTenants();
		return tenants;
	}

	public void addiVolunteerAPIClassDefinition() {
		List<Tenant> tenants = getTenants();

		tenants.forEach(tenant -> {
			ClassDefinition cdPersonRole = classDefinitionRepository.findByNameAndTenantId("PersonRole",
					tenant.getId());
			if (cdPersonRole == null) {
				createiVolunteerAPIPersonRoleClassDefinition(tenant.getId());
				createiVolunteerAPIPersonBadgeClassDefinition(tenant.getId());
				createiVolunteerAPIPersonCertificateClassDefinition(tenant.getId());
				createiVolunteerAPIPersonTaskClassDefinition(tenant.getId());
			}
		});
	}

	private void createiVolunteerAPIPersonRoleClassDefinition(String tenantId) {
		ClassDefinition functionDefinition = new ClassDefinition();
		functionDefinition.setClassArchetype(ClassArchetype.FUNCTION);
		functionDefinition.setMarketplaceId(marketplaceService.getMarketplaceId());
		functionDefinition.setRoot(true);
		functionDefinition.setName("PersonRole");
		functionDefinition.setTimestamp(new Date());
		functionDefinition.setTenantId(tenantId);
		List<FlatPropertyDefinition<Object>> properties = propertyDefinitionRepository.getAllByTenantId(tenantId);
		functionDefinition.setProperties(
				propertyDefinitionToClassPropertyMapper.toTargets(filterPersonRoleProperties(properties)));
		classDefinitionRepository.save(functionDefinition);
	}

	private void createiVolunteerAPIPersonBadgeClassDefinition(String tenantId) {
		ClassDefinition achievementDefinition = new ClassDefinition();
		achievementDefinition.setClassArchetype(ClassArchetype.ACHIEVEMENT);
		achievementDefinition.setMarketplaceId(marketplaceService.getMarketplaceId());
		achievementDefinition.setRoot(true);
		achievementDefinition.setName("PersonBadge");
		achievementDefinition.setTimestamp(new Date());
		achievementDefinition.setTenantId(tenantId);
		List<FlatPropertyDefinition<Object>> properties = propertyDefinitionRepository.getAllByTenantId(tenantId);
		achievementDefinition.setProperties(
				propertyDefinitionToClassPropertyMapper.toTargets(filterPersonBadgeProperties(properties)));
		classDefinitionRepository.save(achievementDefinition);
	}

	private void createiVolunteerAPIPersonCertificateClassDefinition(String tenantId) {
		ClassDefinition achievementDefinition = new ClassDefinition();
		achievementDefinition.setClassArchetype(ClassArchetype.ACHIEVEMENT);
		achievementDefinition.setMarketplaceId(marketplaceService.getMarketplaceId());
		achievementDefinition.setRoot(true);
		achievementDefinition.setName("PersonCertificate");
		achievementDefinition.setTimestamp(new Date());
		achievementDefinition.setTenantId(tenantId);
		List<FlatPropertyDefinition<Object>> properties = propertyDefinitionRepository.getAllByTenantId(tenantId);
		achievementDefinition.setProperties(
				propertyDefinitionToClassPropertyMapper.toTargets(filterPersonCertificateProperties(properties)));
		classDefinitionRepository.save(achievementDefinition);
	}

	private void createiVolunteerAPIPersonTaskClassDefinition(String tenantId) {
		ClassDefinition taskClassDefinition = new ClassDefinition();
		taskClassDefinition.setClassArchetype(ClassArchetype.TASK);
		taskClassDefinition.setMarketplaceId(marketplaceService.getMarketplaceId());
		taskClassDefinition.setRoot(true);
		taskClassDefinition.setName("PersonTask");
		taskClassDefinition.setTimestamp(new Date());
		taskClassDefinition.setTenantId(tenantId);
		List<FlatPropertyDefinition<Object>> properties = propertyDefinitionRepository.getAllByTenantId(tenantId);
		taskClassDefinition.setProperties(
				propertyDefinitionToClassPropertyMapper.toTargets(filterPersonTaskProperties(properties)));
		classDefinitionRepository.save(taskClassDefinition);
	}

	private List<FlatPropertyDefinition<Object>> filterPersonRoleProperties(List<FlatPropertyDefinition<Object>> properties) {
		// @formatter:off
		return properties.stream()
				.filter(p -> p.getName().equals("RoleID") || p.getName().equals("Purpose") || p.getName().equals("Name")
						|| p.getName().equals("Description") || p.getName().equals("organisationID")
						|| p.getName().equals("OrganisationName") || p.getName().equals("OrganisationType")
						|| p.getName().equals("Starting Date") || p.getName().equals("End Date")
						|| p.getName().equals("IVolunteerSource"))
				.collect(Collectors.toList());
		// @formatter:on
	}

	private List<FlatPropertyDefinition<Object>> filterPersonBadgeProperties(List<FlatPropertyDefinition<Object>> properties) {
		// @formatter:off
		return properties.stream()
				.filter(p -> p.getName().equals("BadgeID") || p.getName().equals("Name")
						|| p.getName().equals("Description") || p.getName().equals("IssuedOn")
						|| p.getName().equals("Icon") || p.getName().equals("IVolunteerUUID")
						|| p.getName().equals("IVolunteerSource"))
				.collect(Collectors.toList());
		// @formatter:on
	}

	private List<FlatPropertyDefinition<Object>> filterPersonCertificateProperties(
			List<FlatPropertyDefinition<Object>> properties) {
		// @formatter:off
		return properties.stream()
				.filter(p -> p.getName().equals("CertificateID") || p.getName().equals("Name")
						|| p.getName().equals("Description") || p.getName().equals("IssuedOn")
						|| p.getName().equals("End Date") || p.getName().equals("Icon")
						|| p.getName().equals("IVolunteerUUID") || p.getName().equals("IVolunteerSource"))
				.collect(Collectors.toList());
		// @formatter:on
	}

	private List<FlatPropertyDefinition<Object>> filterPersonTaskProperties(List<FlatPropertyDefinition<Object>> properties) {
		// @formatter:off
		return properties.stream().filter(p -> p.getName().equals("TaskID") || p.getName().equals("Name")
				|| p.getName().equals("TaskType1") || p.getName().equals("TaskType2") || p.getName().equals("TaskType3")
				|| p.getName().equals("TaskType4") || p.getName().equals("Description") || p.getName().equals("Purpose")
				|| p.getName().equals("Role") || p.getName().equals("Rank") || p.getName().equals("Phase")
				|| p.getName().equals("Unit") || p.getName().equals("Level") || p.getName().equals("TaskCountAll")
				|| p.getName().equals("Starting Date") || p.getName().equals("End Date")
				|| p.getName().equals("Duration") || p.getName().equals("Location")
				|| p.getName().equals("GeoInformation") || p.getName().equals("IVolunteerUUID")
				|| p.getName().equals("IVolunteerSource")).collect(Collectors.toList());
		// @formatter:on
	}



}