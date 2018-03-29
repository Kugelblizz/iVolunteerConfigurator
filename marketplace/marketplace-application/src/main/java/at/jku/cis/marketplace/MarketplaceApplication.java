package at.jku.cis.marketplace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import at.jku.cis.marketplace.participant.Employee;
import at.jku.cis.marketplace.participant.EmployeeRepository;
import at.jku.cis.marketplace.participant.Volunteer;
import at.jku.cis.marketplace.participant.VolunteerRepository;
import at.jku.cis.marketplace.participant.profile.VolunteerProfile;
import at.jku.cis.marketplace.participant.profile.VolunteerProfileRepository;

@SpringBootApplication
public class MarketplaceApplication implements CommandLineRunner {

	private static final String MMUSTERMANN = "mmustermann";
	private static final String MWEISSENBEK = "mweissenbek";
	private static final String PSTARZER = "pstarzer";
	private static final String BROISER = "broiser";

	private static final String RAW_PASSWORD = "passme";

	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private VolunteerRepository volunteerRepository;
	@Autowired
	private VolunteerProfileRepository volunteerProfileRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(MarketplaceApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		Employee employee = employeeRepository.findByUsername(MMUSTERMANN);
		if (employee == null) {
			employee = new Employee();
			employee.setUsername(MMUSTERMANN);
			employee.setPassword(bCryptPasswordEncoder.encode(RAW_PASSWORD));
			employee = employeeRepository.insert(employee);
		}

		createVolunteer(BROISER, RAW_PASSWORD);
		createVolunteer(PSTARZER, RAW_PASSWORD);
		createVolunteer(MWEISSENBEK, RAW_PASSWORD);
	}

	private Volunteer createVolunteer(String username, String password) {
		Volunteer volunteer = volunteerRepository.findByUsername(username);
		if (volunteer == null) {
			volunteer = new Volunteer();
			volunteer.setUsername(username);
			volunteer.setPassword(bCryptPasswordEncoder.encode(password));
			volunteer = volunteerRepository.insert(volunteer);
		}

		VolunteerProfile volunteerProfile = volunteerProfileRepository.findByVolunteer(volunteer);
		if (volunteerProfile == null) {
			volunteerProfile = new VolunteerProfile();
			volunteerProfile.setVolunteer(volunteer);
			volunteerProfile = volunteerProfileRepository.insert(volunteerProfile);
		}
		return volunteer;
	}

}