package com.venkat.springJdbc.controller;

import com.venkat.springJdbc.model.Employee;
import com.venkat.springJdbc.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emp")
public class EmployeeController {

    private final EmployeeService employeeService;

    //constructor injection
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> findAll(){
        return this.employeeService.findAll();
    }

    /*@GetMapping("/{email}")
    public Employee findByEmail(@PathVariable String email){
        return this.employeeService.findByEmail(email);
    }
*/
    @GetMapping("/{empId}")
    public Employee findById(@PathVariable Integer empId){
        return this.employeeService.findById(empId);
    }

    @PostMapping()
    public Employee create(@RequestBody Employee employee){
        return this.employeeService.save(employee);
    }

    @PutMapping()
    public Employee update(@RequestBody Employee employee){
        return this.employeeService.save(employee);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id){
        return this.employeeService.delete(id);
    }
}
