package br.com.saudepublica.saudepublica.application.controller;

import br.com.saudepublica.saudepublica.domain.dto.MedicationDto;
import br.com.saudepublica.saudepublica.domain.service.MedicationService;
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
@RequestMapping("/medications")
public class MedicationController {

    private final MedicationService medicationService;

    @Autowired
    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @RequestMapping
    public ModelAndView getMedications() {
        ModelAndView modelAndView = new ModelAndView("medication/medications-list");
        modelAndView.addObject("medications", medicationService.getAll());
        return modelAndView;
    }

    @RequestMapping("/{id}")
    public ModelAndView getMedicationById(@PathVariable("id") Long id, MedicationDto medication, Boolean outRequest) throws Exception {
        ModelAndView modelAndView = new ModelAndView("medication/medications-form");
        modelAndView.addObject("medication", outRequest != null && !outRequest ? medication : medicationService.getById(id));
        return modelAndView;
    }

    @RequestMapping("/new")
    public ModelAndView newMedication(MedicationDto medication) {
        ModelAndView modelAndView = new ModelAndView("medication/medications-form");
        modelAndView.addObject("medication", medication != null ? medication : new MedicationDto());
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteMedication(@PathVariable("id") Long id) {
        medicationService.deleteById(id);
        return new ModelAndView("redirect:/medications");
    }

    @PostMapping
    public ModelAndView postMedication(@Valid MedicationDto medication, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (ObjectError objectError : result.getAllErrors()) {
                errors.add("- " + objectError.getDefaultMessage());
            }
            medication.setErrors(errors);
            return returnError(medication);
        } else {
            medication.setErrors(null);
        }

        MedicationDto medicationSaved = medicationService.save(medication);
        if(medicationSaved.getErrors() != null && !medicationSaved.getErrors().isEmpty()){
            return returnError(medication);
        }

        return new ModelAndView("redirect:/medications");
    }

    private ModelAndView returnError(MedicationDto medication) throws Exception {
        if (medication.getId() == null) {
            return newMedication(medication);
        } else {
            return getMedicationById(medication.getId(), medication, false);
        }
    }
}
