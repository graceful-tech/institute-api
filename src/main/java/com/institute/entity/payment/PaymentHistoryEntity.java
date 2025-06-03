package com.institute.entity.payment;

import java.time.LocalDate;

import com.institute.dto.BaseDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "payment_history")
public class PaymentHistoryEntity extends BaseDto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = true, length = 20)
	private double historyCourseFees;

	@Column(nullable = true, length = 20)
	private double historyDiscount;

	@Column(nullable = true)
	private LocalDate historyPaidDate;

	@Column(nullable = true, length = 20)
	private double historyAmountPaid;

	@Column(nullable = true, length = 50)
	private String historyPaymentMode;

	@Column(nullable = true, length = 20)
	private double historyBalanceAmount;

	@Column(nullable = false)
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
