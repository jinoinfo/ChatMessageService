package com.java.web.chatmessageservice;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;




@CrossOrigin 
@ServletComponentScan
@SpringBootApplication
@RestController
public class ChatMessageServiceApplication extends SpringBootServletInitializer{

	@Autowired
	ResourceLoader resourceLoader;
	
	private static Class<ChatMessageServiceApplication> applicationClass = ChatMessageServiceApplication.class;

	Map<String, Object> chatObjCache =null;
	public static void main(String[] args) {
		SpringApplication.run(ChatMessageServiceApplication.class, args);
	}
	
	
	 @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	        return application.sources(applicationClass);
	    }
	 

	
	@RequestMapping(path = "/chatservice", method = {RequestMethod.POST,RequestMethod.GET}, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getChatservice(@RequestBody Map<String, String> chatMsgMap) {
		
		String issueType = chatMsgMap.get("type");
		
		//String message= chatServiceMap.get("message");
		String chatMsgKey =chatMsgMap.get("messageKey");
		System.out.println("chat message key "+chatMsgKey);
		
		chatObjCache = new ConcurrentHashMap<String, Object>();
		
		//loading the device json file 
		loadDeviceJson(issueType,chatMsgKey);
		
		JSONObject chatConfigObj = (JSONObject)chatObjCache.get(issueType);
		
		//System.out.println ("deviceObj is  "+deviceObj);
		if (chatConfigObj != null ) {
			JSONObject chatMsgObj = (JSONObject) chatConfigObj.get(chatMsgKey);
			System.out.println("Chat Config Message Object is "+ chatConfigObj.get(chatMsgKey));
			
			
			if (chatMsgObj != null ) {
		 		
				return chatMsgObj.toString();
			}	else {
				
				return chatConfigObj.get("default").toString();
			}
			
		}else {
			
			JsonObject errorObj = new JsonObject();

			errorObj.addProperty("error", "-1");
			errorObj.addProperty("statusMessage", "Backend system is down at this point of time.  Please retry the transaction after some time. If issue still presists, please raise a helpdesk ticket.");
			
			return errorObj.toString();
		}
		
		
	
		
		
		
	}

	private void loadDeviceJson(String chatType, String messageKey) {
		

		System.out.println ("Chat type request is  "+chatType);
		JSONParser jsonParser = new JSONParser();
		
		try {
			
		
			Resource resource=resourceLoader.getResource("classpath:./chatServiceConfig.json");
			
			System.out.println("resource .."+resource);
			
			/*Random random = new Random();
			int answer = random.nextInt(3) + 1;
			
			if (answer == 1) {
				
			}*/
			
			
		      //System.out.println("JsonObject .."+obj);
		      InputStream stream= resource.getInputStream();
		   
		      BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(stream));

			    
		      Object obj=  jsonParser.parse(bufferedReader);
		      
		      System.out.println("obj.."+obj);
		      
		    
		      JSONObject jsonObj = (org.json.simple.JSONObject)obj;
		      
		  
		      JSONObject chatObj =  (JSONObject) jsonObj.get(chatType);
		  				
			  System.out.println("Chat Object retrieved for the chat type ."+chatType+ " is..  "+chatObj);
				
			  chatObjCache.put(chatType, chatObj);
				
							
			 /* JSONObject simObj = (JSONObject) jsonObj.get("iccid");
				
			  System.out.println("Sim Obj is .."+simObj);
			  if (null !=simObj) {
						deviceCache.put("sim", simObj);
				}*/
				
				
				
		}catch(Exception ex) {
			
			ex.printStackTrace();
		}
		
	    
		
	}


	}

