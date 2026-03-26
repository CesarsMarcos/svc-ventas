package com.svc.ventas.service.impl;

import com.svc.ventas.models.entity.Usuario;

public interface IUsuarioContext {

  Usuario getPorUserName(String username);

}
