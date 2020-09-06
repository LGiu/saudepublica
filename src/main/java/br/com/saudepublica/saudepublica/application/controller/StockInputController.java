package br.com.saudepublica.saudepublica.application.controller;

import br.com.saudepublica.saudepublica.domain.dto.ItemInputDto;
import br.com.saudepublica.saudepublica.domain.dto.StockInputDto;
import br.com.saudepublica.saudepublica.domain.dto.enumerator.StockStatus;
import br.com.saudepublica.saudepublica.domain.service.EstablishmentService;
import br.com.saudepublica.saudepublica.domain.service.ItemInputService;
import br.com.saudepublica.saudepublica.domain.service.MedicationService;
import br.com.saudepublica.saudepublica.domain.service.StockInputService;
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
@RequestMapping("/stocks-inputs")
public class StockInputController {

    private final StockInputService stockInputService;
    private final ItemInputService itemInputService;
    private final EstablishmentService establishmentService;
    private final MedicationService medicationService;

    @Autowired
    public StockInputController(StockInputService stockInputService,
                                ItemInputService itemInputService,
                                EstablishmentService establishmentService,
                                MedicationService medicationService) {
        this.stockInputService = stockInputService;
        this.itemInputService = itemInputService;
        this.establishmentService = establishmentService;
        this.medicationService = medicationService;
    }

    @RequestMapping
    public ModelAndView getStockInputs() {
        ModelAndView modelAndView = new ModelAndView("stock-input/stocks-inputs-list");
        modelAndView.addObject("stocksInputs", stockInputService.getAll());
        return modelAndView;
    }

    @RequestMapping("/{id}")
    public ModelAndView getStockInputById(@PathVariable("id") Long id, StockInputDto stockInput, Boolean outRequest) throws Exception {
        ModelAndView modelAndView = new ModelAndView("stock-input/stocks-inputs-form");
        modelAndView.addObject("stockInput", outRequest != null && !outRequest ? stockInput : stockInputService.getById(id));
        modelAndView.addObject("itemInput", new ItemInputDto());
        modelAndView.addObject("establishments", establishmentService.getAll());
        modelAndView.addObject("medications", medicationService.getAll());
        return modelAndView;
    }

    @RequestMapping("/new")
    public ModelAndView newStockInput(StockInputDto stockInput) {
        ModelAndView modelAndView = new ModelAndView("stock-input/stocks-inputs-form");
        modelAndView.addObject("stockInput", stockInput != null ? stockInput : new StockInputDto());
        modelAndView.addObject("itemInput", new ItemInputDto());
        modelAndView.addObject("establishments", establishmentService.getAll());
        modelAndView.addObject("medications", medicationService.getAll());
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteStockInput(@PathVariable("id") Long id) {
        stockInputService.deleteById(id);
        return new ModelAndView("redirect:/stocks-inputs");
    }

    @GetMapping("/delete-item/{id}")
    public ModelAndView deleteItemStockInput(@PathVariable("id") Long id) throws Exception {
        ItemInputDto itemInputDto = itemInputService.getById(id);
        itemInputService.deleteById(itemInputDto.getId());
        return new ModelAndView("redirect:/stocks-inputs/" + itemInputDto.getStockInput().getId());
    }

    @PostMapping("/add-item")
    public ModelAndView addItemStockInput(@Valid ItemInputDto itemInput, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (ObjectError objectError : result.getAllErrors()) {
                errors.add("- " + objectError.getDefaultMessage());
            }
            StockInputDto stockInputDto = itemInput.getStockInput();
            stockInputDto.setErrors(errors);
            stockInputDto.setItemInputList(itemInputService.getByStockInputId(stockInputDto.getId()));
            return returnError(stockInputDto);
        } else {
            itemInput.setErrors(null);
        }

        ItemInputDto itemInputSaved = itemInputService.save(itemInput);
        if (itemInputSaved.getErrors() != null && !itemInputSaved.getErrors().isEmpty()) {
            StockInputDto stockInputDto = itemInputSaved.getStockInput();
            stockInputDto.setErrors(itemInputSaved.getErrors());
            stockInputDto.setItemInputList(itemInputService.getByStockInputId(stockInputDto.getId()));
            return returnError(stockInputDto);
        }

        return new ModelAndView("redirect:/stocks-inputs/" + itemInputSaved.getStockInput().getId());
    }


    @PostMapping
    public ModelAndView postStockInput(@Valid StockInputDto stockInput, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (ObjectError objectError : result.getAllErrors()) {
                errors.add("- " + objectError.getDefaultMessage());
            }
            stockInput.setErrors(errors);
            return returnError(stockInput);
        } else {
            stockInput.setErrors(null);
        }

        StockInputDto stockInputSaved = stockInputService.save(stockInput);
        if (stockInputSaved.getErrors() != null && !stockInputSaved.getErrors().isEmpty()) {
            return returnError(stockInput);
        }

        if (stockInput.getStatus().equals(StockStatus.SAVED)) {
            return new ModelAndView("redirect:/stocks-inputs/" + stockInputSaved.getId());
        } else {
            return new ModelAndView("redirect:/stocks-inputs");
        }
    }

    private ModelAndView returnError(StockInputDto stockInput) throws Exception {
        if (stockInput.getId() == null) {
            return newStockInput(stockInput);
        } else {
            return getStockInputById(stockInput.getId(), stockInput, false);
        }
    }

}
