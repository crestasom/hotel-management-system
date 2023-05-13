package com.cretasom.hotelservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cretasom.hms.entity.ResponseBean;
import com.cretasom.hms.exception.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@RestControllerAdvice
@AllArgsConstructor
public class RestExceptionHandler {

	private static Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

	@ExceptionHandler({ ResourceNotFoundException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	ResponseBean handleBadRequestException(RuntimeException ex) {
		logger.error("Exception occured!![{}]", ex.getMessage(), ex);
		ResponseBean bean = new ResponseBean();
		bean.setRespCode(HttpStatus.BAD_REQUEST.value());
		bean.setRespDesc(ex.getMessage());
		return bean;
	}

}