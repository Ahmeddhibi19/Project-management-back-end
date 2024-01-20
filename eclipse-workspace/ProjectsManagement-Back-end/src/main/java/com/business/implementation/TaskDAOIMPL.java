package com.business.implementation;
import com.entity.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.business.*;

@Stateless
public class TaskDAOIMPL implements TaskDAO {
	@PersistenceContext(unitName = "ProjectsManagement-Back-end")
	EntityManager em;
	@Override
	public List<Task> getAlltasks() {
	    try {
	        TypedQuery<Task> query = em.createQuery("SELECT t FROM Task t", Task.class);
	        return query.getResultList();
	    } catch (Exception e) {
	       
	        e.printStackTrace();
	        return Collections.emptyList(); 
	    }
	}

	@Override
	public void addTask(int code, Task task) throws Exception {
	    try {
	        Project project = em.find(Project.class, code);

	        if (project != null) {
	            Date projectStartDate = project.getStartDate();
	            Date taskStartDate = task.getStartDate();
	            Date taskEndDate = task.getEndDate();

	            if (taskEndDate.after(taskStartDate) && taskStartDate.after(projectStartDate) ) {
	                project.addTask(task);
	                task.setProject(project);

	                em.persist(task);
	                em.merge(project);

	                em.flush();
	            } else {
	                throw new Exception("Task dates should be within the project timeframe");
	            }
	        } else {
	            throw new Exception("Project not found");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void findByCodeAndDelete(int code_task ,int code_project)throws Exception{
		try {
			 Project project = em.find(Project.class, code_project);
			 if (project != null) {
				 project.removeTask(code_task);
				 Task task=em.find(Task.class, code_task);
				 em.remove(task);
				 em.merge(project);  
		         em.flush();
			 }
			 else {
				// throw new Exception("project not found");
			 }	
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	@Override
	public void updateTask(int taskCode, String newDescription, Date newStartDate, Date newEndDate) throws Exception {
	    try {
	        Task task = em.find(Task.class, taskCode);

	        if (task != null) {
	            Project project = task.getProject();
	            if (project != null && newStartDate != null && newStartDate.after(project.getStartDate())) {
	                if (newDescription != null) {
	                    task.setDescription(newDescription);
	                }
	                task.setStartDate(newStartDate);
	                if (newEndDate != null) {
	                    task.setEndDate(newEndDate);
	                }
	                em.merge(task);
	                em.flush();
	            } else {
	            	
	            	 throw new Exception("task start date must be after the start date of the project");
	            }
	        } else {
	        	 throw new Exception("Project not found");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	 @Override
	    public List<Task> getAlltasksPerProject(int code_project) {
	        String requeteJPQL = "SELECT t FROM Task t WHERE t.project.codeProject = :code_project";
	        Query query = em.createQuery(requeteJPQL, Task.class);
	        query.setParameter("code_project", code_project);

	        List<Task> tasks = query.getResultList();
	        return tasks;
	    }
	



	
}
