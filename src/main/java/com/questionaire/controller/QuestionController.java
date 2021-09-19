package com.questionaire.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.questionaire.entity.Question;
import com.questionaire.exception.QuestionNotFoundException;
import com.questionaire.exception.QuizIdNotFoundException;
import com.questionaire.exception.ServiceException;
import com.questionaire.service.QuestionService;

@RestController
@RequestMapping("/api/question")
@CrossOrigin("http://localhost:4200")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@PostMapping("/{id}")
	public ResponseEntity<Response> addQuestion(@PathVariable("id") Long id, @RequestBody Question question) {
		ResponseEntity<Response> responseBody = null;
		Response response = new Response();
		try {
			Question ques = questionService.addQuestion(id, question);
			response.setData(ques);
			response.setStatusText("OK");
			response.setStatusCode(200);
			responseBody = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.OK);

		} catch (ServiceException e) {
			String name = e.getClass().getName();
			System.out.println(name);
			if (name.equals("com.school.exception.ServiceException")) {
				response.setStatusCode(500);
				response.setStatusText("Internal Server Error");
				responseBody = new ResponseEntity<Response>(response, new HttpHeaders(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return responseBody;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response> getQuestion(@PathVariable("id") Long id) {
		ResponseEntity<Response> responseBody = null;
		Response response = new Response();
		List<Question>ques;
		try {
			 ques = questionService.getQuestion(id);
			response.setData(ques);
			response.setStatusText("Fetched Questions..");
			response.setStatusCode(200);
			responseBody = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.OK);
		} catch (ServiceException e) {

			response.setStatusCode(404);
			response.setStatusText(e.getMessage());
			responseBody = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);

		}
		return responseBody;
	}

	@PutMapping("/{id}/{quesNo}")
	public ResponseEntity<Response> updateQuestion(@PathVariable("id") Long id, @PathVariable("quesNo") Integer quesNo,
			@RequestBody Question question) throws QuizIdNotFoundException {
		ResponseEntity<Response> responseBody = null;
		Response response = new Response();
		try {
			Question ques = questionService.updateQuestion(id, quesNo, question);
			response.setData(ques);
			response.setStatusText("Question is updated");
			response.setStatusCode(200);
			responseBody = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.OK);
		} catch (ServiceException e) {
			response.setData(e);
			response.setStatusText(e.getMessage());
			response.setStatusCode(404);
			responseBody = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
		return responseBody;
	}

	@DeleteMapping("/{quesNo}")
	public ResponseEntity<Response> deleteQuestion(@PathVariable("quesNo") Integer quesNo)
			throws QuestionNotFoundException {
		ResponseEntity<Response> responseBody = null;
		Response response = new Response();
		try {
			String ques = questionService.deleteQuestion(quesNo);
			response.setData(ques);
			response.setStatusText("OK");
			response.setStatusCode(200);
			responseBody = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.OK);
		} catch (ServiceException e) {

			response.setStatusText(e.getMessage());
			response.setStatusCode(404);
			responseBody = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseBody;
	}
}