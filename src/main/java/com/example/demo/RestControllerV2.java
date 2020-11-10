package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;


@RestController
@RequestMapping("/employee")
public class RestControllerV2 {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public @ResponseBody
    Collection<?> fetchAllEmployees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody
    Object findById(@PathVariable("id") Integer id) {
        return employeeRepository.findById(id);
    }

    @PostMapping
    public @ResponseBody
    Object saveEmployeeData(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }
}
