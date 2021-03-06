package at.jku.cis.iVolunteer.marketplace.task;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import at.jku.cis.iVolunteer.model.meta.core.clazz.TaskInstance;
import at.jku.cis.iVolunteer.model.task.XTask;

@Service
public class XTaskInstanceService {

	@Autowired XTaskInstanceRepository xTaskInstanceRepository;

	public TaskInstance getTaskInstance(String id) {
		return xTaskInstanceRepository.findOne(id);
	}
	
	public List<TaskInstance> getAll() {
		return xTaskInstanceRepository.findAll();
	}
	
	public List<TaskInstance> getTaskInstance(List<String> ids) {
		if (ids == null) {return null;}
		List<TaskInstance> instances = new LinkedList<>();
		xTaskInstanceRepository.findAll(ids).forEach(instances::add);
		return instances;
	}
	
	public List<TaskInstance> getTaskInstanceByTenantId(String tenantId) {
		if (tenantId == null) {
			return null;
		}
		List<TaskInstance> instances = xTaskInstanceRepository.findByIssuerId(tenantId);
		return instances;
	}
	
	public TaskInstance addOrOverwriteTaskInstance(TaskInstance taskInstance) {
		if (taskInstance == null) {
			return null;
		}
		return xTaskInstanceRepository.save(taskInstance);
	}
	
	
	public TaskInstance updateTaskInstance(TaskInstance newTaskInstance) {
		TaskInstance existingTaskInstance = xTaskInstanceRepository.findOne(newTaskInstance.getId());
		return updateTaskInstance(newTaskInstance, existingTaskInstance);
	}

	
	public TaskInstance updateTaskInstance(TaskInstance newTaskInstance, TaskInstance existingTaskInstance) {
		if (newTaskInstance == null || existingTaskInstance == null) {
			return null;
		}
		TaskInstance updateInstance = existingTaskInstance.updateTaskInstance(newTaskInstance);
		
		return xTaskInstanceRepository.save(updateInstance);
	}

	

	
	
	public void deleteTaskInstance(String id) {
		xTaskInstanceRepository.delete(id);
	}
}
