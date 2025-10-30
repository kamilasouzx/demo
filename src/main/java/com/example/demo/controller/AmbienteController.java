package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.AmbienteDTO;
import com.example.demo.service.AmbienteService;

@RestController
@RequestMapping("/ambientes")
public class AmbienteController extends BaseController<AmbienteDTO> {

    protected AmbienteController(AmbienteService service) {
        super(service);
    }
    
}
