/**
 * 
 */
package com.spring.batch.example.spring.batch.csv_to_database.config;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;

import com.spring.batch.example.spring.batch.csv_to_database.model.Employee;

/**
 * com.spring.batch.example.spring.batch.csv_to_database.config DelL
 * @author Rajan kumar
 */
@Configuration
public class EmployeeProcessor implements ItemProcessor<Employee, Employee> {

	@Override
	public Employee process(Employee employee) throws Exception {
		String fullName = employee.getFirstName() + " " + employee.getLastName();
        employee.setFullName(fullName);
		return employee;
	}

}
