package com.cotroller;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Path;
import com.business.*;
import com.business.implementation.*;
import com.entity.*;
import javax.jws.WebParam;
import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless 
@Path("/TaskRest")
public class TaskRestServices {
	@EJB
	TaskDAO taskDAO;
	@GET
	@Path("tasks")
	@Produces(MediaType.APPLICATION_JSON) 
	public List<Task> getAllTasksWEB(){
		 return taskDAO. getAlltasks();
	}
	
	@GET
	@Path("/{code_project}/tasks")
	@Produces(MediaType.APPLICATION_JSON) 
	public List<Task> getAllTasksPerprojectWEB(@PathParam(value="code_project")int code_projec){
		 return taskDAO. getAlltasksPerProject(code_projec);
	}
	
	@POST
	@Path("add/{code_project}")
    @Produces(MediaType.APPLICATION_JSON) 
	public void addTaskWeb(@PathParam(value="code_project")int code_project, @WebParam Task task )throws Exception {
		  taskDAO.addTask(code_project,task);
		
	}
	@DELETE
	@Path("/delete/{code_project}/{code_task}")
	@Produces(MediaType.APPLICATION_JSON) 
	@Transactional
	public void findByCodeAndDeleteWEB(@PathParam(value="code_project") int code_project,@PathParam(value="code_task") int code_task)throws Exception {
		  taskDAO.findByCodeAndDelete(code_task,code_project);
	}
	@PATCH
	@Path("/update/{code}/{newStartDate}/{newEndDate}/{newDescription}")
	@Produces(MediaType.APPLICATION_JSON)
	public void updateProjectWEB(
	    @PathParam(value = "code") int code,
	    @PathParam(value = "newDescription") String newDescription,
	    @PathParam(value = "newStartDate") String newStartDatestring,
	    @PathParam(value = "newEndDate") String newEndDatestring) throws Exception {
	    
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	    Date newStartDate = dateFormat.parse(newStartDatestring);
	    Date newEndDate = dateFormat.parse(newEndDatestring);
	    
	    taskDAO.updateTask(code, newDescription, newStartDate, newEndDate);
	}

}
