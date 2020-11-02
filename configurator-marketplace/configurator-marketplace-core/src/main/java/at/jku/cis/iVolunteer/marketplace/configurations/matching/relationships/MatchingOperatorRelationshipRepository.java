package at.jku.cis.iVolunteer.marketplace.configurations.matching.relationships;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.configurations.matching.MatchingOperatorRelationship;

public interface MatchingOperatorRelationshipRepository extends MongoRepository<MatchingOperatorRelationship, String> {

	List<MatchingOperatorRelationship> findByMatchingConfigurationId(String matchingConfigurationId);

}
