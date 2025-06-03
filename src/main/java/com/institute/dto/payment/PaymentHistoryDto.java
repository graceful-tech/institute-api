package com.institute.dto.payment;

import java.time.LocalDate;

import com.institute.dto.BaseDto;

public class PaymentHistoryDto extends BaseDto {

	private Long id;

	private double historyCourseFees;

	private double historyDiscount;

	private LocalDate historyPaidDate;

	private double historyAmountPaid;

	private String historyPaymentMode;

	private double historyBalanceAmount;

	private Long PaymentId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getHistoryCourseFees() {
		return historyCourseFees;
	}

	public void setHistoryCourseFees(double historyCourseFees) {
		this.historyCourseFees = historyCourseFees;
	}

	public double getHistoryDiscount() {
		return historyDiscount;
	}

	public void setHistoryDiscount(double historyDiscount) {
		this.historyDiscount = historyDiscount;
	}

	public LocalDate getHistoryPaidDate() {
		return historyPaidDate;
	}

	public void setHistoryPaidDate(LocalDate historyPaidDate) {
		this.historyPaidDate = historyPaidDate;
	}

	public double getHistoryAmountPaid() {
		return historyAmountPaid;
	}

	public void setHistoryAmountPaid(double historyAmountPaid) {
		this.historyAmountPaid = historyAmountPaid;
	}

	public String getHistoryPaymentMode() {
		return historyPaymentMode;
	}

	public void setHistoryPaymentMode(String historyPaymentMode) {
		this.historyPaymentMode = historyPaymentMode;
	}

	public double getHistoryBalanceAmount() {
		return historyBalanceAmount;
	}

	public void setHistoryBalanceAmount(double historyBalanceAmount) {
		this.historyBalanceAmount = historyBalanceAmount;
	}

	public Long getPaymentId() {
		return PaymentId;
	}

	public void setPaymentId(Long paymentId) {
		PaymentId = paymentId;
	}

}
