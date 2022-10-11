package com.manuel.store.core.errorhandler;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessage {
	
	private Date timestamp;
	private String message;
}
