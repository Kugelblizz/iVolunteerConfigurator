package at.jku.cis.marketplace.trustifier;

import static java.text.MessageFormat.format;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import at.jku.cis.marketplace.participant.profile.CompetenceEntry;
import at.jku.cis.marketplace.participant.profile.TaskEntry;
import at.jku.cis.marketplace.task.Task;

@Service
public class VerifierRestClient {

	private static final String TRUSTIFIER_CONTRACTOR_TASK = "{0}/trustifier/verifier/{1}";

	@Value("${marketplace.trustifier.uri}")
	private URI trustifierURI;

	@Autowired
	private RestTemplate restTemplate;

	public boolean verifyTask(Task task) {
		String requestURI = buildContractorRequestURI("task");
		return restTemplate.postForObject(requestURI, task, Boolean.class).booleanValue();
	}

	public boolean verifyTaskEntry(TaskEntry taskEntry) {
		String requestURI = buildContractorRequestURI("taskEntry");
		return restTemplate.postForObject(requestURI, taskEntry, Boolean.class).booleanValue();
	}

	public boolean verifyCompetenceEntry(CompetenceEntry competenceEntry) {
		String requestURI = buildContractorRequestURI("competenceEntry");
		return restTemplate.postForObject(requestURI, competenceEntry, Boolean.class).booleanValue();
	}

	private String buildContractorRequestURI(String requestPath) {
		return format(TRUSTIFIER_CONTRACTOR_TASK, trustifierURI, requestPath);
	}

}
