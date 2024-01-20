package com.entity;


import java.io.Serializable;
import java.lang.String;
import java.util.Date;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Entity implementation class for Entity: Task
 *
 */
@Entity
@Table(name="task")
public class Task implements Serializable {

	   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="taskCoce")
	private int Code;
	@Column(name="Description")
	private String Description;
	@Column(name="StartDate")
	@JsonSerialize(using = CustomDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
	private Date startDate;
	@Column(name="EndDate")
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
	private Date endDate;
	private static final long serialVersionUID = 1L;
	 
	@ManyToOne
	@JoinColumn(name="code_Project",referencedColumnName = "codeProject",nullable=false)
	 @JsonBackReference
	private Project project;

	public Task() {
		super();
	}   
	public int getCode() {
		return this.Code;
	}

	public void setCode(int Code) {
		this.Code = Code;
	}   
	public String getDescription() {
		return this.Description;
	}

	public void setDescription(String Description) {
		this.Description = Description;
	}   
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}   
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date End_Date)throws Exception {
		try {
			if(End_Date.toInstant().isAfter(this.startDate.toInstant())) {
				this.endDate = End_Date;
			}
			else {
				 throw new Exception("endDate must be after start date");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	
	public Project getProject() {
		return this.project;
	}
	public void setProject(Project p) {
		this.project=p;
	}
	@JsonProperty("code_project")
    public int getCodeProject() {
        if (this.project != null) {
            return this.project.getCode();
        }
        return 0; // Or handle the default case as needed
    }
   
}
