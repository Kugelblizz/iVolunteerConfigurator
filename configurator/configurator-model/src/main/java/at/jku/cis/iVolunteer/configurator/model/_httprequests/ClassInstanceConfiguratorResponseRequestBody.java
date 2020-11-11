package at.jku.cis.iVolunteer.configurator.model._httprequests;

import at.jku.cis.iVolunteer.configurator.model.meta.core.clazz.ClassInstance;

public class ClassInstanceConfiguratorResponseRequestBody {
//	--body: instance
	
	private ClassInstance classInstance;

	public ClassInstance getClassInstance() {
		return classInstance;
	}

	public void setClassInstance(ClassInstance classInstance) {
		this.classInstance = classInstance;
	}
	
	
}
