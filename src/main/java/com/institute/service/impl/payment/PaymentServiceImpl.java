package com.institute.service.impl.payment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.institute.dto.WrapperDto;
import com.institute.dto.payment.PaymentHistoryDto;
import com.institute.dto.payment.PaymentDto;
import com.institute.dto.payment.SearchPaymentDto;
import com.institute.entity.payment.PaymentEntity;
import com.institute.entity.payment.PaymentHistoryEntity;
import com.institute.repository.payment.PaymentCustomRepository;
import com.institute.repository.payment.PaymentHistoryRepository;
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

	@Autowired
	PaymentCustomRepository paymentCustomRepository;

	@Autowired
	PaymentHistoryRepository paymentHistoryRepository;

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
				payment.setCreatedUserName(paymentEntity.getCreatedUserName());
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

			savePaymenthistory(paymentDto, username, id);

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

	@Override
	public WrapperDto<SearchPaymentDto> searchPayment(SearchPaymentDto searchPaymentDto, String username) {
		logger.debug("Service :: searchPayment :: Entered");

		WrapperDto<SearchPaymentDto> paymentWrapperDto = null;
		try {

			searchPaymentDto.setLoggedUserName(username);
			searchPaymentDto.setLoggedUserId(userRepository.getUserId(username));

			Pageable pageable = PageRequest.of(searchPaymentDto.getPage(), searchPaymentDto.getLimit());
			paymentWrapperDto = paymentCustomRepository.searchPayments(searchPaymentDto, pageable);
		} catch (Exception e) {
			logger.error("Service :: searchPayment :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: searchPayment :: Exited");
		return paymentWrapperDto;
	}

	private void savePaymenthistory(PaymentDto paymentDto, String username, Long paymentId) {
		logger.debug("Service :: savePaymenthistory :: Entered");

		PaymentHistoryDto historyPaymentDto = new PaymentHistoryDto();
		PaymentHistoryEntity paymentHistoryEntity = null;

		try {
			historyPaymentDto.setPaymentId(paymentId);
			historyPaymentDto.setHistoryCourseFees(paymentDto.getCourseFees());
			historyPaymentDto.setHistoryAmountPaid(paymentDto.getAmountPaid());
			historyPaymentDto.setHistoryBalanceAmount(paymentDto.getAmountPaid());
			historyPaymentDto.setHistoryDiscount(paymentDto.getDiscount());

			if (Objects.nonNull(paymentDto.getPaymentMode()) && !paymentDto.getPaymentMode().isEmpty()) {
				historyPaymentDto.setHistoryPaymentMode(paymentDto.getPaymentMode());
			}

			if (Objects.nonNull(paymentDto.getPaidDate())) {
				historyPaymentDto.setHistoryPaidDate(paymentDto.getPaidDate());
			}

			historyPaymentDto.setCreatedDate(LocalDateTime.now());
			historyPaymentDto.setModifiedDate(LocalDateTime.now());
			historyPaymentDto.setCreatedUserName(username);

			paymentHistoryEntity = modelMapper.map(historyPaymentDto, PaymentHistoryEntity.class);

			paymentHistoryRepository.save(paymentHistoryEntity);

			paymentHistoryEntity = null;
			historyPaymentDto = null;

		} catch (Exception e) {
			logger.error("Service :: savePaymenthistory :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: savePaymenthistory :: Exited");

	}

	@Override
	public List<PaymentHistoryDto> getPaymentHistoryBypaymentId(Long id, String username) {
		logger.debug("Service :: getPaymentHistoryBypaymentId :: Entered");

		List<PaymentHistoryEntity> paymentHistoryByPaymentId = null;
		List<PaymentHistoryDto> paymentHistoryDtoList = new ArrayList<>();

		try {
			paymentHistoryByPaymentId = paymentHistoryRepository.getPaymentHistoryByPaymentId(id);

			paymentHistoryByPaymentId.forEach(history -> {
				PaymentHistoryDto paymentHistory = new PaymentHistoryDto();

				paymentHistory = modelMapper.map(history, PaymentHistoryDto.class);
				paymentHistoryDtoList.add(paymentHistory);
				paymentHistory = null;

			});

			paymentHistoryByPaymentId = null;

		} catch (Exception e) {
			logger.error("Service :: getPaymentHistoryBypaymentId :: Exception :: " + e.getMessage());
		}
		logger.debug("Service :: getPaymentHistoryBypaymentId :: Exited");
		return paymentHistoryDtoList;

	}

}
