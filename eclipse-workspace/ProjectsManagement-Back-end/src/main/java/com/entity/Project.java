package com.entity;

import java.io.Serializable;
import java.lang.String;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Entity implementation class for Entity: Project
 *
 */
@Entity
@Table(name="project")
public class Project implements Serializable {

	   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="codeProject")
	private int codeProject;
	@Column(name="StartDate")
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
	private Date startDate;
	@Column(name="Description")
	private String Description;
	private static final long serialVersionUID = 1L;
	
	@JsonManagedReference
	@OneToMany(mappedBy="project",cascade=CascadeType.ALL,orphanRemoval= true,fetch = FetchType.EAGER)
	private List<Task> tasks =new ArrayList<Task>();

	public Project() {
		super();
		
	}   
	public int getCode() {
		return this.codeProject;
	}

	public void setCode(int Code) {
		this.codeProject = Code;
	}   
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}   
	public String getDescription() {
		return this.Description;
	}

	public void setDescription(String Description) {
		this.Description = Description;
	}
	
	
	public List<Task> getTasks(){
		return this.tasks;
	}
	public void setTasks(List<Task> t) {
		this.tasks=t;
	}
	public void addTask(Task task) throws Exception {
	    try {
	        if (task.getStartDate().toInstant().isAfter(this.startDate.toInstant())) {
	            this.tasks.add(task);
	        }
	        else {
	        	 throw new Exception("dtart date of the tsk must be after the project");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	public void removeTask(int code_task) throws Exception {
	    try {
	        boolean taskFound = false; 
	        Iterator<Task> iterator = this.tasks.iterator();

	        while (iterator.hasNext()) {
	            Task t = iterator.next();
	            if (t.getCode() == code_task) {
	                iterator.remove(); 
	                taskFound = true; 
	                break; 
	            }
	        }

	        
	        if (!taskFound) {
	            throw new Exception("Task not found");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        
	        throw new Exception("Error in removeTask method", e);
	    }
	}



}
