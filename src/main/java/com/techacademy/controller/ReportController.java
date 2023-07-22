package com.techacademy.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;
import com.techacademy.service.ReportService;
import com.techacademy.service.UserDetail;

@Controller
@RequestMapping("report")
public class ReportController {
    private final ReportService service;

    public ReportController(ReportService service) {
        this.service = service;
    }
    @GetMapping("/list")
    public String getList(Model model) {

        model.addAttribute("reportlist", service.getReportList());

        return "report/list";
    }

    @GetMapping("/register")
    public String getRegister(@AuthenticationPrincipal UserDetail loginUser, Model model) {
        String employeeName = loginUser.getEmployee().getName();
        Report report = new Report();
        model.addAttribute("employeename",employeeName);
        model.addAttribute("report", report);
        return "report/register";
    }
    @PostMapping("/register")
    public String postRegister(@AuthenticationPrincipal UserDetail loginUser,Report report) {
        Employee employee = loginUser.getEmployee();
        report.setEmployee(employee);
        LocalDateTime date1 = LocalDateTime.now();
        DateTimeFormatter dtformat =DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String date2 = dtformat.format(date1);
        report.setCreatedAt(date2);
        report.setUpdatedAt(date2);
        service.saveReport(report);
        return "redirect:/report/list";
    }
    @GetMapping("/detail/{id}")
    public String getDetail(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("report", service.getReport(id));

        return "report/detail";
    }
    @GetMapping("/edit/{id}")
    public String getEdit(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("report", service.getReport(id));

        return "report/edit";
    }
    @PostMapping("/edit/{id}")
    public String postEdit(@PathVariable("id") Integer id,Report report) {
        Report tempReport = service.getReport(id);
        Employee employee = tempReport.getEmployee();
        report.setEmployee(employee);
        LocalDateTime date1 = LocalDateTime.now();
        DateTimeFormatter dtformat =DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String date2 = dtformat.format(date1);
        report.setCreatedAt(tempReport.getCreatedAt());
        report.setUpdatedAt(date2);
        service.saveReport(report);
        return "redirect:/report/list";
    }


}
