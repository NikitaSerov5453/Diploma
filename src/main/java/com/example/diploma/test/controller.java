package com.example.diploma.test;

import com.example.diploma.entity.Report;
import com.example.diploma.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class controller {

    private final ReportRepository  repository;

    private final Test test;

    @GetMapping
    public ModelAndView test() {
        ModelAndView modelAndView = new ModelAndView("test");
        modelAndView.addObject("reports", repository.findAll());
        return modelAndView;
    }
}
