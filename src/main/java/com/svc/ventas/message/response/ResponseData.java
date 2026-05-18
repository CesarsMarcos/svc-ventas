package com.svc.ventas.message.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseData<T> {

	private T data;

	private String mensaje;

}
