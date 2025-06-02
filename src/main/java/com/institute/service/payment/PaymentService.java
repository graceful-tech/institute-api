package com.institute.service.payment;

import com.institute.dto.payment.PaymentDto;

public interface PaymentService {

	Long savePayment(PaymentDto paymentDto, String username);

	PaymentDto getPaymentById(Long id, String username);

}
