package com.cretasom.hotel_security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthResp {

	private int respCode;
	private String respDesc;
	private String token;
}
