package at.jku.csi.marketplace.task;

import java.util.List;

import javax.ws.rs.NotAcceptableException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.jku.csi.marketplace.task.transaction.TaskTransactionRepository;

@RestController
public class TaskController {

	private static final int PAGE_SIZE = 10;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private TaskTransactionRepository taskTransactionRepository;

	@GetMapping("/task")
	public List<Task> findAll() {
		return taskRepository.findAll();
	}

	@GetMapping("/task/{id}")
	public Task findById(@PathVariable("id") String id) {
		return taskRepository.findOne(id);
	}

	@GetMapping("/task/status/{status}")
	public List<Task> findByStatus(@PathVariable("status") TaskStatus taskStatus) {
		return taskRepository.findByTaskStatus(taskStatus, new PageRequest(0, PAGE_SIZE));
	}

	@PostMapping("/task")
	public Task createTask(@RequestBody Task task) {
		task.setTaskStatus(TaskStatus.CREATED);
		return taskRepository.insert(task);
	}

	@PutMapping("/task/{id}")
	public Task updateTask(@PathVariable("id") String id, @RequestBody Task task) {
		if (taskRepository.exists(id)) {
			throw new NotAcceptableException();
		}
		return taskRepository.save(task);
	}

	@DeleteMapping("/task/{id}")
	public void deleteTask(@PathVariable("id") String id) {
		taskRepository.delete(id);
	}

}
