package com.business;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import com.entity.Project;

@Local
public interface ProjectDAO {
	List<Project> getAllproject();
	void addProject(Project project);
	void findByCodeAndDelete(int code)throws Exception;
	void updateProject(int code, Date newStartDate, String newDescription)throws Exception;
}
