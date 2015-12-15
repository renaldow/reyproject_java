package org.renaldo.testbot.reybot;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.renaldo.testbot.utilities.BotFilesUtility;

@Path("/botfiles")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BotUploadFilesResource {
	
	BotFilesUtility bFiles = new BotFilesUtility();
	
	@GET
    public Response botFiles() {
    	
    		String response = "This is the botFiles Resource.";
    	
    		return Response.status(Status.OK)
    					.entity(response)
					.header("Access-Control-Allow-Origin", "*")
					.build();
    } 
	
	@GET
	@Path("/uploadfiles")
    public Response uploadAllBotFiles() {
    	
    		String response = bFiles.setAllBotFiles(true);
    	
    		return Response.status(Status.OK)
    					.entity(response)
					.header("Access-Control-Allow-Origin", "*")
					.build();
    } 
	
	@GET
	@Path("/deletefiles")
    public Response deleteAllBotFiles() {
    	
    		String response = bFiles.setAllBotFiles(false);
    	
    		return Response.status(Status.OK)
    					.entity(response)
					.header("Access-Control-Allow-Origin", "*")
					.build();
    } 
}
