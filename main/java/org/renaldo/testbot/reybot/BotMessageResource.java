package org.renaldo.testbot.reybot;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.renaldo.testbot.utilities.BotFilesUtility;

import service.BotMessageService;


/**
 * Root resource (exposed at "/messages" path)
 */
@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BotMessageResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
	
	BotMessageService bService = new BotMessageService();
	
    @GET
    public Response getMessageFromBot(@QueryParam("messagetobot") String messagetobot) {
    	
    		String botReply = "";
    	
	    	if(messagetobot == null || messagetobot.equals(""))
	    	{
	    		botReply = bService.sendMessageToBot_REST("Hi");
	    	}
	    	else
	    	{
	    		botReply = bService.sendMessageToBot_REST(messagetobot);
	    	}
	    	
			return Response.status(Status.OK)
					.entity(botReply)
					.header("Access-Control-Allow-Origin", "*")
					.build();
    }  
}