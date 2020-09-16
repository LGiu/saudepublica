package br.com.saudepublica.saudepublica.application.controller;

import br.com.saudepublica.saudepublica.application.util.Session;
import br.com.saudepublica.saudepublica.domain.dto.ItemOutputDto;
import br.com.saudepublica.saudepublica.domain.dto.StockOutputDto;
import br.com.saudepublica.saudepublica.domain.dto.enumerator.StockStatus;
import br.com.saudepublica.saudepublica.domain.dto.enumerator.TypeStockOutput;
import br.com.saudepublica.saudepublica.domain.dto.enumerator.TypeUser;
import br.com.saudepublica.saudepublica.domain.service.*;
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
@RequestMapping("/stocks-outputs")
public class StockOutputController {

    private final StockOutputService stockOutputService;
    private final ItemOutputService itemOutputService;
    private final EstablishmentService establishmentService;
    private final MedicationService medicationService;

    @Autowired
    public StockOutputController(StockOutputService stockOutputService,
                                ItemOutputService itemOutputService,
                                EstablishmentService establishmentService,
                                MedicationService medicationService) {
        this.stockOutputService = stockOutputService;
        this.itemOutputService = itemOutputService;
        this.establishmentService = establishmentService;
        this.medicationService = medicationService;
    }

    @RequestMapping
    public ModelAndView getStockOutputs() {
        ModelAndView modelAndView = new ModelAndView("stock-output/stocks-outputs-list");
        modelAndView.addObject("stocksOutputs", stockOutputService.getAll());
        modelAndView.addObject("user", Session.getUser());
        return modelAndView;
    }

    @RequestMapping("/{id}")
    public ModelAndView getStockOutputById(@PathVariable("id") Long id, StockOutputDto stockOutput, Boolean outRequest) throws Exception {
        ModelAndView modelAndView = new ModelAndView("stock-output/stocks-outputs-form");
        modelAndView.addObject("stockOutput", outRequest != null && !outRequest ? stockOutput : stockOutputService.getById(id));
        modelAndView.addObject("itemOutput", new ItemOutputDto());
        modelAndView.addObject("establishments", establishmentService.getAll());
        modelAndView.addObject("medications", medicationService.getAll());
        modelAndView.addObject("typesStockOutput", TypeStockOutput.values());
        modelAndView.addObject("user", Session.getUser());
        return modelAndView;
    }

    @RequestMapping("/new")
    public ModelAndView newStockOutput(StockOutputDto stockOutput) {
        ModelAndView modelAndView = new ModelAndView("stock-output/stocks-outputs-form");
        modelAndView.addObject("stockOutput", stockOutput != null ? stockOutput : new StockOutputDto());
        modelAndView.addObject("itemOutput", new ItemOutputDto());
        modelAndView.addObject("establishments", establishmentService.getAll());
        modelAndView.addObject("medications", medicationService.getAll());
        modelAndView.addObject("typesStockOutput", TypeStockOutput.values());
        modelAndView.addObject("user", Session.getUser());
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteStockOutput(@PathVariable("id") Long id) {
        stockOutputService.deleteById(id);
        return new ModelAndView("redirect:/stocks-outputs");
    }

    @GetMapping("/delete-item/{id}")
    public ModelAndView deleteItemStockOutput(@PathVariable("id") Long id) throws Exception {
        ItemOutputDto itemOutputDto = itemOutputService.getById(id);
        itemOutputService.deleteById(itemOutputDto.getId());
        return new ModelAndView("redirect:/stocks-outputs/" + itemOutputDto.getStockOutput().getId());
    }

    @PostMapping("/add-item")
    public ModelAndView addItemStockOutput(@Valid ItemOutputDto itemOutput, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (ObjectError objectError : result.getAllErrors()) {
                errors.add("- " + objectError.getDefaultMessage());
            }
            StockOutputDto stockOutputDto = itemOutput.getStockOutput();
            stockOutputDto.setErrors(errors);
            stockOutputDto.setItemOutputList(itemOutputService.getByStockOutputId(stockOutputDto.getId()));
            return returnError(stockOutputDto);
        } else {
            itemOutput.setErrors(null);
        }

        ItemOutputDto itemOutputSaved = itemOutputService.save(itemOutput);
        if (itemOutputSaved.getErrors() != null && !itemOutputSaved.getErrors().isEmpty()) {
            StockOutputDto stockOutputDto = itemOutputSaved.getStockOutput();
            stockOutputDto.setErrors(itemOutputSaved.getErrors());
            stockOutputDto.setItemOutputList(itemOutputService.getByStockOutputId(stockOutputDto.getId()));
            return returnError(stockOutputDto);
        }

        return new ModelAndView("redirect:/stocks-outputs/" + itemOutputSaved.getStockOutput().getId());
    }


    @PostMapping
    public ModelAndView postStockOutput(@Valid StockOutputDto stockOutput, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (ObjectError objectError : result.getAllErrors()) {
                errors.add("- " + objectError.getDefaultMessage());
            }
            stockOutput.setErrors(errors);
            return returnError(stockOutput);
        } else {
            stockOutput.setErrors(null);
        }

        StockOutputDto stockOutputSaved = stockOutputService.save(stockOutput);
        if (stockOutputSaved.getErrors() != null && !stockOutputSaved.getErrors().isEmpty()) {
            return returnError(stockOutput);
        }

        if (stockOutput.getStatus().equals(StockStatus.SAVED)) {
            return new ModelAndView("redirect:/stocks-outputs/" + stockOutputSaved.getId());
        } else {
            return new ModelAndView("redirect:/stocks-outputs");
        }
    }

    private ModelAndView returnError(StockOutputDto stockOutput) throws Exception {
        if (stockOutput.getId() == null) {
            return newStockOutput(stockOutput);
        } else {
            return getStockOutputById(stockOutput.getId(), stockOutput, false);
        }
    }
}
