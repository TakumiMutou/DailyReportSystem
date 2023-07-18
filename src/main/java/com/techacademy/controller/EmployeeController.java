package com.techacademy.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.entity.Authentication;
import com.techacademy.entity.Employee;
import com.techacademy.service.EmployeeService;

@Controller
@RequestMapping("employee")
public class EmployeeController {
    private final EmployeeService service;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    /** 従業員一覧画面を表示 */
    @GetMapping("/list")
    public String getList(Model model) {

        model.addAttribute("employeelist", service.getEmployeeList());
        return "employee/list";
    }

    @GetMapping("/detail/{id}")
    public String getEmployee(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("employee", service.getEmployee(id));

        return "employee/detail";
    }


    @GetMapping("/register")
    public String getRegister(@ModelAttribute Employee employee) {
        // 登録画面に遷移
        return "employee/register";
    }

    @PostMapping("/register")
    public String postRegister(Employee employee) {
        // 登録
        Authentication authentication = employee.getAuthentication();
        authentication.setEmployee(employee);
        String password = employee.getAuthentication().getPassword();
        authentication.setPassword(passwordEncoder.encode(password));
        employee.setAuthentication(authentication);
        employee.setUpdatedAt(LocalDateTime.now());
        employee.setDeleteFlag(0);
        employee.setCreatedAt(LocalDateTime.now());
        service.saveEmployee(employee);
        return "redirect:/employee/list";
    }

    @GetMapping("/edit/{id}")
    public String getEdit(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("employee", service.getEmployee(id));

        return "employee/edit";
    }

    @PostMapping("/edit/{id}")
    public String postEdit(@PathVariable("id") Integer id, Employee employee) {
        Authentication authentication = employee.getAuthentication();
        authentication.setEmployee(employee);
        String password =employee.getAuthentication().getPassword();
        if (password.equals("")) {
            password = service.getEmployee(id).getAuthentication().getPassword();
        }
        authentication.setPassword(passwordEncoder.encode(password));
        employee.setAuthentication(authentication);
        employee.setId(id);
        employee.setUpdatedAt(LocalDateTime.now());
        employee.setDeleteFlag(0);
        employee.setCreatedAt(service.getEmployee(id).getCreatedAt());
        service.saveEmployee(employee);
        return "redirect:/employee/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteEdit(@PathVariable("id") Integer id) {
        Employee employee = service.getEmployee(id);
        employee.setDeleteFlag(1);
        service.saveEmployee(employee);
        return "redirect:/employee/list";
    }

}
