package com.institute.controller.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.institute.dto.payment.PaymentDto;
import com.institute.service.payment.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

	@Autowired
	PaymentService paymentService;

	@PostMapping("/save")
	public ResponseEntity<?> savePayment(@RequestBody PaymentDto paymentDto,
			@RequestHeader("username") String username) {
		logger.debug("Controller :: savePayment :: Entered");

		Long savePayment = paymentService.savePayment(paymentDto, username);

		logger.debug("Controller :: savePayment :: Exited");
		return new ResponseEntity<>(savePayment, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getPayment(@PathVariable Long id, @RequestHeader("username") String username) {
		logger.debug("Controller :: getPayment :: Entered");

		PaymentDto paymentDto = paymentService.getPaymentById(id, username);

		logger.debug("Controller :: getPayment :: Exited");

		return new ResponseEntity<>(paymentDto, HttpStatus.OK);
	}

}
