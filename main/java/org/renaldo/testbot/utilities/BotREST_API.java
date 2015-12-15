package org.renaldo.testbot.utilities;

import java.io.StringReader;
import java.util.Scanner;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.StatusType;

////custom exception class
//class FileSendException extends Exception
//{	//Prints statement if user calls toString method on exception
//	public String toString()
//	{
//		return "A problem occurred while sending the file to Pandora Bots.";
//	}
//}
public class BotREST_API {
	
	private final String baseURL = "https://aiaas.pandorabots.com/";
	private String api_key = "ff69e877c2e0f96ef67f5af62a9102f9";
	private String app_id = "1409612317974";
	private String botname = "/reybot";
	private String domain = "talk/" + app_id + botname;
	private String domainUploadFile = "bot/" + app_id + botname;
	private StringReader myResponse;
	
    public String sendMessageToBot(String message)
    {
    		MultivaluedMap<String, String> form = new MultivaluedHashMap<String, String>();
 		
    	    form.add("input",message); 
    	    form.add("user_key",api_key); 
  
    	    return retrieveDataFromURL(form);    		
    }
    
    private String retrieveDataFromURL(MultivaluedMap<String, String> _form) {
    	
		String eventsReturned = "";
		String sessionID = "";
		String command = "";
		String reply = "";
		//Scanner myScanner = new Scanner(System.in);
		
	    Client client = ClientBuilder.newClient();

	    WebTarget webTarget = client.target(baseURL + domain);
    	
			System.out.println("You: " + _form.getFirst("input"));
					
		     eventsReturned = webTarget.request(MediaType.APPLICATION_JSON_TYPE)
		    		    .post(Entity.entity(_form,MediaType.APPLICATION_FORM_URLENCODED_TYPE),
		    		        String.class);
		    	    	
		    myResponse = new StringReader(eventsReturned);
		    
		 	JsonReader jsonReader = Json.createReader(myResponse);
		    JsonObject myObj = jsonReader.readObject();
		     
		    boolean itemsEmpty = myObj.getJsonArray("responses").isEmpty();
		    
		    JsonArray jArray = myObj.getJsonArray("responses");
		    
		    reply = jArray.getString(0);

		    return reply;
		    
		}
    
    		String uploadBotFile(String _filename, String _fileContents, String _type, Boolean properties)
    		{
    			
    			String eventsReturned = "";
    			String reply = "ok";
    			WebTarget webTarget;
    			Response response = null;
    			//Scanner myScanner = new Scanner(System.in);
    			
    			try
    			{
    		    Client client = ClientBuilder.newClient();

    		    if(properties)
    		    {	    	
    		    		webTarget = client.target(baseURL + domainUploadFile + "/" + _type + "?user_key={user_key}");
    		    	}
    		    else
    		    {
    		    		webTarget = client.target(baseURL + domainUploadFile + "/" + _type + "/" + _filename + "?user_key={user_key}");   	    		   
    		    }
  						
    		    response = webTarget
    			    		 	.resolveTemplate("user_key", api_key)
    			    		 	.request(MediaType.APPLICATION_XML_TYPE)
    			    		    .put(Entity.entity(_fileContents,MediaType.APPLICATION_FORM_URLENCODED_TYPE));
    			   
    		    MultivaluedMap<String, Object> m = response.getHeaders();
    		    StatusType statusInfo = response.getStatusInfo();
    		    int code = response.getStatus();
    		    String reason = statusInfo.getReasonPhrase();
    		    
    		    if(code != 200)
    		    {
    		    		System.out.println("Non 200 status returned when deleting file method. Probably because file already deleted. Resuming deletion of next file.");
    		    		return "ok";
    		    }
    			
    		    eventsReturned = response.readEntity(String.class)	;  
    		    
    			    myResponse = new StringReader(eventsReturned);
    			    
    			 	JsonReader jsonReader = Json.createReader(myResponse);
    			    JsonObject myObj = jsonReader.readObject();
    			    
    			    Boolean itemsEmpty =  myObj.getString("status").isEmpty();

    			    reply = myObj.getString("status");
    			}
    			catch(Exception ex)
    			{
    				
    			}

    			    return reply;
    		}
    		
    		String deleteBotFile(String _filename, String _fileContents, String _type, Boolean properties) 
    		{
    			
    			String eventsReturned = "";
    			String reply = "ok";
    			WebTarget webTarget;
    			Response response = null;
    			//Scanner myScanner = new Scanner(System.in);
    			try
    			{
    				
    		    Client client = ClientBuilder.newClient();

    		    if(properties)
    		    {	    	
    		    		webTarget = client.target(baseURL + domainUploadFile + "/" + _type + "?user_key={user_key}");
    		    	}
    		    else
    		    {
    		    		webTarget = client.target(baseURL + domainUploadFile + "/" + _type + "/" + _filename + "?user_key={user_key}");   	    		   
    		    }
  					
    		    
    			     response = webTarget
    			    		 	.resolveTemplate("user_key", api_key)
    			    		 	.request(MediaType.APPLICATION_XML_TYPE)
    			    		    .delete();
    			
    			
    		    
    		    MultivaluedMap<String, Object> m = response.getHeaders();
    		    StatusType statusInfo = response.getStatusInfo();
    		    int code = response.getStatus();
    		    String reason = statusInfo.getReasonPhrase();
    		    
    		    if(code != 200)
    		    {
    		    		System.out.println("Non 200 status returned when deleting file method. Probably because file already deleted. Resuming deletion of next file.");
    		    		return "ok";
    		    }
    			
    		    eventsReturned = response.readEntity(String.class)	;    	
    			    myResponse = new StringReader(eventsReturned);
    			    
    			 	JsonReader jsonReader = Json.createReader(myResponse);
    			    JsonObject myObj = jsonReader.readObject();
    			    
    			    Boolean itemsEmpty =  myObj.getString("status").isEmpty();
    			 
    		  			 
    			    
    			    reply = myObj.getString("status");
    			}
		    catch(Exception ex)
			{
				
			}
    				// }
    			    
    				// myScanner.close();
    			    return reply;
    		}

}
