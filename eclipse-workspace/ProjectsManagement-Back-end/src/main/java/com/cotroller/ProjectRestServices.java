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
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless 
@Path("/ProjectRest")
public class ProjectRestServices {
	@EJB
	ProjectDAO projectDAO;
	@GET
	@Path("Projects")
	@Produces(MediaType.APPLICATION_JSON) 
	public List<Project> getAllProjectsWEB(){
		 return projectDAO.getAllproject();
	}
	
	@POST
	@Path("add")
    @Produces(MediaType.APPLICATION_JSON) 
	public void addProjectWEB(@WebParam Project project ) {
		  projectDAO.addProject(project);
		
	}
	
	@DELETE
	@Path("/delete/{code}")
	@Produces(MediaType.APPLICATION_JSON) 
	public void findByCodeAndDeleteWEB(@PathParam(value="code") int code)throws Exception {
		  projectDAO.findByCodeAndDelete(code);
	}
	@PATCH
	@Path("/update/{code}/{newStartDate}/{newDescription}")
	@Produces(MediaType.APPLICATION_JSON)
	public void updateProjectWEB(
	    @PathParam(value="code") int code,
	    @PathParam(value="newStartDate") String newStartDateString, 
	    @PathParam(value="newDescription") String newDescription) throws Exception {
	    
	    
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	    Date newStartDate = dateFormat.parse(newStartDateString);

	   
	    projectDAO.updateProject(code, newStartDate, newDescription);
	}

}
