package com.soulcode.goserviceapp.controller;

import com.soulcode.goserviceapp.domain.Cliente;
import com.soulcode.goserviceapp.domain.Prestador;
import com.soulcode.goserviceapp.service.ClienteService;
import com.soulcode.goserviceapp.service.PrestadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PrestadorService prestadorService;

    @ModelAttribute("usuario")
    public Object getUsuario(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            // Verifica se o usuário autenticado é um cliente
            if (authentication.getPrincipal() instanceof Cliente) {
                return clienteService.findAuthenticated(authentication);
            }
            // Verifica se o usuário autenticado é um prestador
            else if (authentication.getPrincipal() instanceof Prestador) {
                return prestadorService.findAuthenticated(authentication);
            }
        }
        return null;
    }
}
