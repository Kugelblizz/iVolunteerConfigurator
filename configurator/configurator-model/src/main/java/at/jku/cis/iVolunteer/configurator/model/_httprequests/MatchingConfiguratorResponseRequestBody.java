package at.jku.cis.iVolunteer.configurator.model._httprequests;

import java.util.List;

import at.jku.cis.iVolunteer.configurator.model.configurations.matching.MatchingConfiguration;
import at.jku.cis.iVolunteer.configurator.model.configurations.matching.MatchingOperatorRelationship;

public class MatchingConfiguratorResponseRequestBody {
//	--body: matching-configuration + matching-relationships
	
	MatchingConfiguration matchingConfiguration;
	List<MatchingOperatorRelationship> matchingRelationships;
	
	
	public MatchingConfiguration getMatchingConfiguration() {
		return matchingConfiguration;
	}
	public void setMatchingConfiguration(MatchingConfiguration matchingConfiguration) {
		this.matchingConfiguration = matchingConfiguration;
	}
	public List<MatchingOperatorRelationship> getMatchingRelationships() {
		return matchingRelationships;
	}
	public void setMatchingRelationships(List<MatchingOperatorRelationship> matchingRelationships) {
		this.matchingRelationships = matchingRelationships;
	}
	
	
}
