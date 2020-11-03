package at.jku.cis.iVolunteer.configurator.initialize;


import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;


@Service
public class CoreInitializationService {
	
	@Autowired private Environment environment;
	
	@PostConstruct
	public void init() {
		
		
	}

	

}
