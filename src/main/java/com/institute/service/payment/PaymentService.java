package com.institute.service.payment;

import java.util.List;

import com.institute.dto.WrapperDto;
import com.institute.dto.payment.DashboardPaymentDto;
import com.institute.dto.payment.PaymentDto;
import com.institute.dto.payment.PaymentHistoryDto;
import com.institute.dto.payment.SearchPaymentDto;

public interface PaymentService {

	Long savePayment(PaymentDto paymentDto, String username);

	PaymentDto getPaymentById(Long id, String username);

	WrapperDto<SearchPaymentDto> searchPayment(SearchPaymentDto searchPaymentDto, String username);

	List<PaymentHistoryDto> getPaymentHistoryBypaymentId(Long id, String username);

	WrapperDto<DashboardPaymentDto> getDashboardPayment(DashboardPaymentDto dashboardPaymentDto, String username);

}
