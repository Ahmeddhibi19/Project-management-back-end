package com.business;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import com.entity.Task;
@Local
public interface TaskDAO {
	List<Task> getAlltasks();
	List<Task> getAlltasksPerProject(int code_project);
	void addTask(int code,Task task)throws Exception;
	void findByCodeAndDelete(int code_task,int code_project)throws Exception;
	void updateTask(int taskCode, String newDescription, Date newStartDate, Date newEndDate) throws Exception;
}
