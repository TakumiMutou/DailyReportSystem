package com.techacademy.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;
import com.techacademy.service.ReportService;
import com.techacademy.service.UserDetail;

@Controller
public class IndexController {

    private final ReportService service;


    public IndexController(ReportService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String getIndex(@AuthenticationPrincipal UserDetail loginUser, Model model) {

        Employee employee = loginUser.getEmployee();
        List<Report> reports = service.getReportSelfList(employee);
        model.addAttribute("reports", reports);


        return "index";
    }

}
