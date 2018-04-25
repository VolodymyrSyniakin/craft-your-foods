package com.gmail.vsyniakin.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

@ControllerAdvice
public class ExceptionHandlerController {


    @ExceptionHandler({NoResultException.class, NullPointerException.class})
    public String noResult(Model model) {
        model.addAttribute("exc", "noResult");
        return "forward:/";
    }

    @ExceptionHandler(PersistenceException.class)
    public String persistenceExc(Model model) {
        model.addAttribute("exc", "server");
        return "forward:/";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String illegalArgumentExc(Model model) {
        model.addAttribute("exc", "illegalArgument");
        return "forward:/";
    }

    @ExceptionHandler(Exception.class)
    public String unhandledExc(Model model) {
        model.addAttribute("exc", "unhandled");
        return "forward:/";
    }

}
