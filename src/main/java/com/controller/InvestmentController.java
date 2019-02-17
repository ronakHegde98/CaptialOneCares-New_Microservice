package com.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.domain.Investment;
import com.repository.InvestmentsRepository;

@CrossOrigin(origins = "*")
@RestController
@ExposesResourceFor(Investment.class)
@RequestMapping(value = "/investments", produces = "application/json")
//@Controller    // This means that this class is a Controller
//@RequestMapping(path="/investments") // This means URL's start with /addInvestments (after Application path)


public class InvestmentController {
	@Autowired // This means to get the bean called InvestmentsRepository
	           // Which is auto-generated by Spring, we will use it to handle the data
	private InvestmentsRepository investmentRepository;
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Investment> addQuestion (@RequestBody Investment investment
			) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		Investment createdInvestment = investmentRepository.save(investment);
		return new ResponseEntity(createdInvestment,HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Iterable<Investment>> getAllQuestions() {
		// This returns a JSON or XML with the questions
		return new ResponseEntity<>(investmentRepository.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Investment> findInvestmentById(@PathVariable Integer id) {
		Optional<Investment> investment = investmentRepository.findById(id);

		if (investment.isPresent()) {
			return new ResponseEntity<>(investment.get(), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<Investment> updateInvestment(@PathVariable Integer id, @RequestBody Investment investment) {
		Investment updatedQuestionText  = investmentRepository.save(investment);
		System.out.println("ID:"+id+":");
		if (investmentRepository.existsById(id)) {
			return findInvestmentById(id);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}