package com.institute.repository.payment;

import org.springframework.data.domain.Pageable;

import com.institute.dto.WrapperDto;
import com.institute.dto.payment.DashboardPaymentDto;
import com.institute.dto.payment.SearchPaymentDto;

public interface PaymentCustomRepository {

	WrapperDto<SearchPaymentDto> searchPayments(SearchPaymentDto searchPaymentDto, Pageable pageable);

	WrapperDto<DashboardPaymentDto> getDashboardPayments(DashboardPaymentDto dashboardPaymentDto, Pageable pageable);

}
