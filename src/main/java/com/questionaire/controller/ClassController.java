package com.questionaire.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.questionaire.entity.ClassRoom;
import com.questionaire.exception.ServiceException;
import com.questionaire.service.ClassService;

@RestController
@RequestMapping("/api/class")
@CrossOrigin("http://localhost:4200")
public class ClassController {

	@Autowired
	private ClassService classServiceImp;

	@PostMapping
	public ResponseEntity<Response> addClass(@RequestBody ClassRoom classDetails) {
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		try {
		ClassRoom classRoom=classServiceImp.addClass(classDetails);
		 response.setStatusText("OK");
		 response.setStatusCode(200);
		 response.setData(classRoom);
		 responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
		}
		catch(ServiceException e)
		{
			 String name = e.getClass().getName();
			  if(name.equals("com.questionaire.exception.ServiceException"))
			  {
			     response.setStatusCode(500);
			     response.setStatusText("Internal Server Error");
			     responseEntity = new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
			  } 
		}
		return responseEntity;
	}
	
	@GetMapping
	public ResponseEntity<Response> getClassDetails()
	{
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		List<ClassRoom> classDetails;
		try {
			classDetails = classServiceImp.getClassDetails();
			response.setStatusText("OK");
			 response.setStatusCode(200);
			 response.setData(classDetails);
			 responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
			
		} catch(ServiceException e)
		{
			 String name = e.getClass().getName();
			  if(name.equals("com.questionaire.exception.ServiceException"))
			  {
			     response.setStatusCode(500);
			     response.setStatusText("Internal Server Error");
			     responseEntity = new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
			  } 
		}
		return responseEntity;
	}
	
	@PutMapping("/{roomNo}")
	public ResponseEntity<Response> updateClass(@PathVariable("roomNo")Long roomNo,@RequestBody ClassRoom classDetails)
	{
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		ClassRoom updatedClass;
		try {
			updatedClass= classServiceImp.updateClass(roomNo,classDetails);
			response.setStatusText("OK");
			 response.setStatusCode(200);
			 response.setData(classDetails);
			 responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
			
		} catch (ServiceException e) {
			 String name = e.getClass().getName();
			  if(name.equals("com.questionaire.exception.ServiceException"))
			  {
			     response.setStatusCode(500);
			     response.setStatusText("Internal Server Error");
			     responseEntity = new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
			  } 
		
		}
		return responseEntity;
	}
	
	@GetMapping("/{standard}/{section}")
	public ResponseEntity<Response> getClass(@PathVariable("standard") String standard,@PathVariable("section") String section)
	{
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		ClassRoom cls;
		try {
			cls = classServiceImp.getClass(standard,section);
			response.setStatusText("OK");
			 response.setStatusCode(200);
			 response.setData(cls);
			 responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
		} catch (ServiceException e) {
			String name = e.getClass().getName();
			  if(name.equals("com.questionaire.exception.ServiceException"))
			  {
			     response.setStatusCode(500);
			     response.setStatusText("Internal Server Error");
			     responseEntity = new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
			  } 
		
		}
		return responseEntity;
	}
		
	
}