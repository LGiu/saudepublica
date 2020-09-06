package br.com.saudepublica.saudepublica.application.controller;

import br.com.saudepublica.saudepublica.domain.dto.EstablishmentDto;
import br.com.saudepublica.saudepublica.domain.service.EstablishmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/establishments")
public class EstablishmentController {

    private final EstablishmentService establishmentService;

    @Autowired
    public EstablishmentController(EstablishmentService establishmentService) {
        this.establishmentService = establishmentService;
    }

    @RequestMapping
    public ModelAndView getEstablishments() {
        ModelAndView modelAndView = new ModelAndView("establishment/establishments-list");
        modelAndView.addObject("establishments", establishmentService.getAll());
        return modelAndView;
    }

    @RequestMapping("/{id}")
    public ModelAndView getEstablishmentById(@PathVariable("id") Long id, EstablishmentDto establishment, Boolean outRequest) throws Exception {
        ModelAndView modelAndView = new ModelAndView("establishment/establishments-form");
        modelAndView.addObject("establishment", outRequest != null && !outRequest ? establishment : establishmentService.getById(id));
        return modelAndView;
    }

    @RequestMapping("/new")
    public ModelAndView newEstablishment(EstablishmentDto establishment) {
        ModelAndView modelAndView = new ModelAndView("establishment/establishments-form");
        modelAndView.addObject("establishment", establishment != null ? establishment : new EstablishmentDto());
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteEstablishment(@PathVariable("id") Long id) {
        establishmentService.deleteById(id);
        return new ModelAndView("redirect:/establishments");
    }

    @PostMapping
    public ModelAndView postEstablishment(@Valid EstablishmentDto establishment, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (ObjectError objectError : result.getAllErrors()) {
                errors.add("- " + objectError.getDefaultMessage());
            }
            establishment.setErrors(errors);
            return returnError(establishment);
        } else {
            establishment.setErrors(null);
        }

        EstablishmentDto establishmentSaved = establishmentService.save(establishment);
        if(establishmentSaved.getErrors() != null && !establishmentSaved.getErrors().isEmpty()){
            return returnError(establishment);
        }

        return new ModelAndView("redirect:/establishments");
    }

    private ModelAndView returnError(EstablishmentDto establishment) throws Exception {
        if (establishment.getId() == null) {
            return newEstablishment(establishment);
        } else {
            return getEstablishmentById(establishment.getId(), establishment, false);
        }
    }
}
