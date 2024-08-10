/**
 * 
 */
package com.spring.batch.example.spring.batch.csv_to_database.config;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.separator.DefaultRecordSeparatorPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.batch.example.spring.batch.csv_to_database.model.Employee;

/**
 * com.spring.batch.example.spring.batch.csv_to_database.config DelL
 * @author Rajan kumar
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {
	@Bean
	FlatFileItemReader<Employee> reader() {
		return new FlatFileItemReaderBuilder<Employee>().name("employeeItemReader")
				.resource(new ClassPathResource("employees.csv")).delimited().names("firstName", "lastName", "email")
				.fieldSetMapper(createFieldSetMapper()).recordSeparatorPolicy(new DefaultRecordSeparatorPolicy())
				.build();
	}

	private BeanWrapperFieldSetMapper<Employee> createFieldSetMapper() {
		BeanWrapperFieldSetMapper<Employee> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Employee.class);
		return fieldSetMapper;
	}
	   
		@Bean
		JpaItemWriter<Employee> writer(EntityManagerFactory entityManagerFactory) {
			JpaItemWriter<Employee> writer = new JpaItemWriter<>();
			writer.setEntityManagerFactory(entityManagerFactory);
			return writer;
		}

		@Bean
		Job importEmployeeJob(JobBuilderFactory jobBuilderFactory,Step step1)
		{
			return jobBuilderFactory.get("importEmployeeJob")
					.incrementer(new RunIdIncrementer())
					.flow(step1)
					.end()
					.build();
		}
		
		@Bean
		Step step1(StepBuilderFactory stepBuilderFactory, FlatFileItemReader<Employee> reader,
				ItemProcessor<Employee, Employee> processor, JpaItemWriter<Employee> writer) {
			return stepBuilderFactory.get("step1").<Employee, Employee>chunk(5).reader(reader).processor(processor)
					.writer(writer).build();
		}	
		
}
