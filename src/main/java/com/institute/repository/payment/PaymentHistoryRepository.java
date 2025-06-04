package com.institute.repository.payment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.institute.entity.payment.PaymentHistoryEntity;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistoryEntity, Long> {

	@Query(value = "select * from payment_history where payment_id  = :paymentId ", nativeQuery = true)
	List<PaymentHistoryEntity> getPaymentHistoryByPaymentId(@Param("paymentId") Long paymentId);

	@Query(value = "select sum(history_amount_paid) from  payment_history where payment_id = :paymentId ", nativeQuery = true)
	double getOverallPaidAmountCount(@Param("paymentId") Long paymentId);

}
