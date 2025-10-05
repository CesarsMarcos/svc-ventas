package com.svc.ventas.message.response;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response implements Serializable {

	private String mensaje;

	/**
	 * 
	 */
	private static final long serialVersionUID = -3412524148354430541L;
	
}
