package com.ngo.khawb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class HandelAllExceptions {

  // for any exception occur in our project it will be handel

  @ResponseStatus(value = HttpStatus.FORBIDDEN)
  @ExceptionHandler(value = Exception.class)
  public String anyBadRequestException(Model model) {
    model.addAttribute("msg", "BAD_REQUEST");
    return "error_page";
  }

//  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//  @ExceptionHandler(value = Exception.class)
//  public String anyInternalServerException(Model model) {
//    model.addAttribute("msg", "INTERNAL_SERVER_ERROR");
//    return "error_page";
//  }
//
//  @ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
//  @ExceptionHandler(value = Exception.class)
//  public String anyUnsuportedException(Model model) {
//    model.addAttribute("msg", "UNSUPPORTED_MEDIA_TYPE");
//    return "error_page";
//  }
}
