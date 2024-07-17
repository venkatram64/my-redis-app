package com.venkat.springJdbc.repository;

import com.venkat.springJdbc.model.Employee;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends ListCrudRepository<Employee, Integer> {

    public Employee findByEmail(String email);
}
