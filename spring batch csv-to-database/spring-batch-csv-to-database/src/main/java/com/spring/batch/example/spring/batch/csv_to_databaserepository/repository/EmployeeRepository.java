/**
 * 
 */
package com.spring.batch.example.spring.batch.csv_to_databaserepository.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.batch.example.spring.batch.csv_to_database.model.Employee;

/**
 * com.spring.batch.example.spring.batch.csv_to_databaserepository.repository
 *DelL
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
