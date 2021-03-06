package at.jku.cis.iVolunteer.marketplace.chart;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import at.jku.cis.iVolunteer.model.chart.StoredChart;

@Repository
public interface StoredChartRepository extends MongoRepository<StoredChart, String> {
	
	StoredChart findByTitle(String title);
}


