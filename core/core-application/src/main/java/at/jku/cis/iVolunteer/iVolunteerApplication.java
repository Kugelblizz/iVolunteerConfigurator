package at.jku.cis.iVolunteer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import at.jku.cis.iVolunteer.core.employee.CoreEmployeeRepository;
import at.jku.cis.iVolunteer.core.marketplace.CoreMarketplaceRestClient;
import at.jku.cis.iVolunteer.core.marketplace.MarketplaceRepository;
import at.jku.cis.iVolunteer.core.volunteer.CoreVolunteerRepository;
import at.jku.cis.iVolunteer.model.core.participant.CoreEmployee;
import at.jku.cis.iVolunteer.model.core.participant.CoreVolunteer;
import at.jku.cis.iVolunteer.model.marketplace.Marketplace;
import at.jku.cis.iVolunteer.model.participant.dto.VolunteerDTO;

@SpringBootApplication
public class iVolunteerApplication {

	private static final String MARKETPLACE_ID = "0eaf3a6281df11e8adc0fa7ae01bbebc";
	private static final String MARKETPLACE_NAME = "Marketplace 1";
	private static final String MARKETPLACE_SHORTNAME = "MP1";
	private static final String MARKETPLACE_URL = "http://localhost:8080";

	private static final String MMUSTERMANN = "mmustermann";
	private static final String MWEISSENBEK = "mweissenbek";
	private static final String PSTARZER = "pstarzer";
	private static final String BROISER = "broiser";

	private static final String RAW_PASSWORD = "passme";

	@Autowired
	private MarketplaceRepository marketplaceRepository;

	@Autowired
	private CoreMarketplaceRestClient coreMarketplaceRestClient;

	@Autowired
	private CoreEmployeeRepository coreEmployeeRepository;
	@Autowired
	private CoreVolunteerRepository coreVolunteerRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(iVolunteerApplication.class, args);
	}

	@PostConstruct
	private void init() {
		Marketplace marketplace = createMarketplace();
		createEmployee(marketplace);
		createVolunteer(BROISER, RAW_PASSWORD, marketplace);
		createVolunteer(PSTARZER, RAW_PASSWORD, marketplace);
		createVolunteer(MWEISSENBEK, RAW_PASSWORD, marketplace);

	}

	private Marketplace createMarketplace() {
		Marketplace marketplace = marketplaceRepository.findOne(MARKETPLACE_ID);
		if (marketplace == null) {
			marketplace = new Marketplace();
			marketplace.setId(MARKETPLACE_ID);
			marketplace.setUrl(MARKETPLACE_URL);
			marketplace.setName(MARKETPLACE_NAME);
			marketplace.setShortName(MARKETPLACE_SHORTNAME);
			marketplaceRepository.insert(marketplace);
		}
		return marketplace;
	}

	private void createEmployee(Marketplace marketplace) {
		CoreEmployee employee = coreEmployeeRepository.findByUsername(MMUSTERMANN);
		if (employee == null) {
			employee = new CoreEmployee();
			employee.setUsername(MMUSTERMANN);
			employee.setPassword(bCryptPasswordEncoder.encode(RAW_PASSWORD));
			employee.getRegisteredMarketplaces().clear();
			employee.getRegisteredMarketplaces().add(marketplace);
			employee = coreEmployeeRepository.insert(employee);

		}
	}

	private void createVolunteer(String username, String password, Marketplace marketplace) {
		CoreVolunteer volunteer = coreVolunteerRepository.findByUsername(username);
		if (volunteer == null) {
			volunteer = new CoreVolunteer();
			volunteer.setUsername(username);
			volunteer.setPassword(bCryptPasswordEncoder.encode(password));
			volunteer.getRegisteredMarketplaces().clear();
			volunteer.getRegisteredMarketplaces().add(marketplace);
			volunteer = coreVolunteerRepository.insert(volunteer);

			VolunteerDTO volunteerDto = new VolunteerDTO();
			volunteerDto.setId(volunteer.getId());
			volunteerDto.setUsername(volunteer.getUsername());
			volunteerDto.setPassword(volunteer.getPassword());

			coreMarketplaceRestClient.registerVolunteer(MARKETPLACE_URL,
					"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtbXVzdGVybWFubiIsInVzZXJuYW1lIjoibW11c3Rlcm1hbm4iLCJhdXRob3JpdGllcyI6WyJFTVBMT1lFRSJdLCJleHAiOjE1MzE3Mzg1Nzd9.YxIp9xY0sJ56rz9Q_ZzV-LT12wiw4UbXnIiaebTBaRv6AFgiDdWO-Yj-UWbf8qJyewkp3nAYtqzjbzvTiToc-Q",

					volunteerDto);
		}
	}
}
