package org.renaldo.testbot.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class BotFilesUtility {
	
	String dirForMainFiles = "";
	ArrayList<String> filenameslist;
	BotREST_API bot_restapi = new BotREST_API();
	
	public static void main(String[] args)
	{
		System.out.println("Hey");
	}
	
	public String setAllBotFiles(Boolean uploadFile)
	{
		File currentDirFile = null;
		String dir = "";
	    filenameslist = new ArrayList<String>();
		String json = "No files loaded";
	
		try
		{
			 currentDirFile = new File("");
			 dir = currentDirFile.getAbsolutePath() + "/webapps/rosie/";
			 ArrayList<File> files = new ArrayList<File>();
			 String x = files.toString();
			 String status = "";
			 
			 getFilesInDirectory(dir, files);
			 
			 for (File file : files) {
				    if (file.isFile()) {
				    	String ext = getFileExtension(file);
		    	
					    	if(uploadFile)
					    	{
					    		status = sendFilesToServer(file, ext);
					    	}
					    	else
					    	{
					    	    status = deleteFilesToServer(file, ext);
					    	}

				    }
				}
			
			 JsonArrayBuilder jBuilder = Json.createArrayBuilder();
			 
		      for (int i=0;i<filenameslist.size();i++)
		      {
		    	  		jBuilder.add(Json.createObjectBuilder().add("file", filenameslist.get(i)));   
		      }
		      
		      JsonArray jArray = jBuilder.build();
		      		
		      json = jArray.toString();
		//File folder = new File("/");
		}
		catch(Exception ex)
		{
			json = "Exception: No files loaded: " + ex.toString() + ": dir = " + dir;
		}
	
		return json;
	}
	
	private String getFileExtension(File file) {
	    String name = file.getName();
	    try {
	        return name.substring(name.lastIndexOf(".") + 1);
	    } catch (Exception e) {
	        return "";
	    }
	}
	
	public void getFilesInDirectory(String directoryName, ArrayList<File> files) {
	    File directory = new File(directoryName);

	    // get all the files from a directory
	    File[] fList = directory.listFiles();
	    for (File file : fList) {
	        if (file.isFile()) {
	            files.add(file);
	        } else if (file.isDirectory()) {
	        	getFilesInDirectory(file.getAbsolutePath(), files);
	        }
	    }
	}
	
	String sendFilesToServer(File _file, String _extension) throws Exception
	{
		String content = "";
		String status = "ok";
		
		switch(_extension.toLowerCase())
		{
			case "aiml": 
				filenameslist.add(_file.getName());
				System.out.println(_file.getAbsolutePath());
				System.out.println(_file.getName());
				
			try {
				String filepath = _file.getAbsolutePath();
				content = new Scanner(new File(filepath)).useDelimiter("\\Z").next();
				status = bot_restapi.uploadBotFile(_file.getName(), content, "file", false);
				
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					status = "Problem with sending file to pandora bots";
					e.printStackTrace();
				}
			
				//System.out.println(content);
				
				System.out.println("*--------------------------------------------------------------------*");
				
				break;
				
			case "map":
			case "set":
			case "substitution":
				
				filenameslist.add(_file.getName());
				System.out.println(_file.getAbsolutePath());
				System.out.println(_file.getName());
				
			try {
				String filepath = _file.getAbsolutePath();
				content = new Scanner(new File(filepath)).useDelimiter("\\Z").next();
				String filenameWithoutExtension = _file.getName().substring(0, _file.getName().lastIndexOf('.'));
				status = bot_restapi.uploadBotFile(filenameWithoutExtension, content, _extension.toLowerCase(), false);
				
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					status = "Problem with sending file to pandora bots";
					e.printStackTrace();
				}
			
				//System.out.println(content);
				
				System.out.println("*--------------------------------------------------------------------*");
				
				break;
				
			case "properties":
			case "pdefaults":
				filenameslist.add(_file.getName());
				System.out.println(_file.getAbsolutePath());
				System.out.println(_file.getName());
				
				try {
					String filepath = _file.getAbsolutePath();
					content = new Scanner(new File(filepath)).useDelimiter("\\Z").next();
					String filenameWithoutExtension = _file.getName().substring(0, _file.getName().lastIndexOf('.'));			
					status = bot_restapi.uploadBotFile(filenameWithoutExtension, content, _extension.toLowerCase(), true);
					
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						status = "Problem with sending file to pandora bots";
						e.printStackTrace();
					}
				
					//System.out.println(content);
					
					System.out.println("*--------------------------------------------------------------------*");
					
				break;
				
		}
		
		
			if(!status.equals("ok"))
				status = "Problem with sending file to pandora bots";
			
		return status;
		
	}
	
	String deleteFilesToServer(File _file, String _extension) throws Exception
	{
		String content = "";
		String status = "ok";
		
		switch(_extension.toLowerCase())
		{
			case "aiml": 
				
				filenameslist.add(_file.getName());
				System.out.println(_file.getAbsolutePath());
				System.out.println(_file.getName());
				
			try {
				String filepath = _file.getAbsolutePath();
				content = new Scanner(new File(filepath)).useDelimiter("\\Z").next();
				
				status = bot_restapi.deleteBotFile(_file.getName(), content, "file", false); 
				
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					status = "Problem with sending file to pandora bots";
					e.printStackTrace();
				}
			
				//System.out.println(content);
				
				System.out.println("*--------------------------------------------------------------------*");
				
				break;
				
			case "map":
			case "set":
			case "substitution":
				
				filenameslist.add(_file.getName());
				System.out.println(_file.getAbsolutePath());
				System.out.println(_file.getName());
				
			try {
				String filepath = _file.getAbsolutePath();
				content = new Scanner(new File(filepath)).useDelimiter("\\Z").next();
				
				status = bot_restapi.deleteBotFile(_file.getName(), content, _extension.toLowerCase(), false); 
				
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					status = "Problem with sending file to pandora bots";
					e.printStackTrace();
				}
			
				//System.out.println(content);
				
				System.out.println("*--------------------------------------------------------------------*");
				
				break;
				
			case "properties":
			case "pdefaults":
				filenameslist.add(_file.getName());
				System.out.println(_file.getAbsolutePath());
				System.out.println(_file.getName());
				
				try {
					String filepath = _file.getAbsolutePath();
					content = new Scanner(new File(filepath)).useDelimiter("\\Z").next();
					
					status = bot_restapi.deleteBotFile(_file.getName(), content, _extension.toLowerCase(), true);
					
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						status = "Problem with sending file to pandora bots";
						e.printStackTrace();
					}
				
					//System.out.println(content);
					
					System.out.println("*--------------------------------------------------------------------*");
					
				break;
				
		}
		
		
			if(!status.equals("ok"))
				status = "Problem with sending file to pandora bots";
			
		return status;
		
	}
	
}
