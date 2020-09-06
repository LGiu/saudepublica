package br.com.saudepublica.saudepublica.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    private static final String path = "/error";

    private final ErrorAttributes errorAttributes;

    public ErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping(value = path)
    public ModelAndView accessdenied(WebRequest webRequest) {
        final Throwable error = errorAttributes.getError(webRequest);
        return new ModelAndView("error");
    }

    @Override
    public String getErrorPath() {
        return path;
    }
}
