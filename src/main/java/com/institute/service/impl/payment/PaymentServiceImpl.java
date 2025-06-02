package com.institute.service.impl.payment;

import java.time.LocalDateTime;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.institute.dto.payment.PaymentDto;
import com.institute.entity.payment.PaymentEntity;
import com.institute.repository.payment.PaymentRepository;
import com.institute.repository.user.UserRepository;
import com.institute.service.impl.user.UserServiceImpl;
import com.institute.service.payment.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	PaymentRepository paymentRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public Long savePayment(PaymentDto paymentDto, String username) {

		logger.debug("Service :: createCourse :: Entered");

		PaymentEntity paymentEntity = null;
		PaymentEntity payment = null;
		PaymentEntity savepaymentEntity = null;
		Long id = null;

		try {

			paymentEntity = paymentRepository.getPaymentByCandidateId(paymentDto.getCandidateId());

			if (Objects.nonNull(paymentEntity)) {
				payment = modelMapper.map(paymentDto, PaymentEntity.class);
				payment.setModifiedDate(LocalDateTime.now());
				payment.setModifiedUser(userRepository.getUserId(username));
				payment.setCandidateId(paymentDto.getCandidateId());

				savepaymentEntity = paymentRepository.save(payment);

				id = savepaymentEntity.getId();
			} else {
				payment = modelMapper.map(paymentDto, PaymentEntity.class);
				payment.setCreatedUserName(username);
				payment.setCreatedDate(LocalDateTime.now());
				payment.setModifiedDate(LocalDateTime.now());
				payment.setModifiedUser(userRepository.getUserId(username));

				savepaymentEntity = paymentRepository.save(payment);

				id = savepaymentEntity.getId();
			}

			paymentEntity = null;
			payment = null;
			savepaymentEntity = null;

		} catch (Exception e) {
			logger.error("Service :: createCourse :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: createCourse :: Exited");
		return id;
	}

	@Override
	public PaymentDto getPaymentById(Long candidateId, String username) {
		logger.debug("Service :: getPaymentById :: Entered");

		PaymentEntity paymentEntity = null;
		PaymentDto payment = null;
		try {
			paymentEntity = paymentRepository.getPaymentByCandidateId(candidateId);
			payment = modelMapper.map(paymentEntity, PaymentDto.class);

			paymentEntity = null;

		} catch (Exception e) {
			logger.error("Service :: getPaymentById :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: getPaymentById :: Exited");
		return payment;
	}

}
