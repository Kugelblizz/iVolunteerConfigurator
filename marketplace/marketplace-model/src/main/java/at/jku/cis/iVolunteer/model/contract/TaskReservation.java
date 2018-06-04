package at.jku.cis.iVolunteer.model.contract;

import at.jku.cis.iVolunteer.model.source.dto.SourceDTO;
import at.jku.cis.iVolunteer.model.task.dto.TaskDTO;

public class TaskReservation {

	private SourceDTO source;
	private TaskDTO task;

	public TaskReservation() {
	}

	public TaskReservation(SourceDTO source, TaskDTO task) {
		this.source = source;
		this.task = task;
	}

	public SourceDTO getSource() {
		return source;
	}

	public void setSource(SourceDTO source) {
		this.source = source;
	}

	public TaskDTO getTask() {
		return task;
	}

	public void setTask(TaskDTO task) {
		this.task = task;
	}
}