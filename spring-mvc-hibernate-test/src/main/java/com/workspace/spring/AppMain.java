package com.workspace.spring;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.workspace.spring.configuration.AppConfiguration;
import com.workspace.spring.model.Employee;
import com.workspace.spring.service.EmployeeService;

public class AppMain {

	public static void main(String args[]) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
		EmployeeService service = (EmployeeService) context.getBean("employeeService");

		/*
		 * Create Employee1
		 */
		Employee employee1 = new Employee();
		employee1.setName("Han Yenn");
		employee1.setJoiningDate(new LocalDate(2010, 10, 10));
		employee1.setSalary(new BigDecimal(10000));
		employee1.setSsn("ssn00000001");

		/*
		 * Create Employee2
		 */
		Employee employee2 = new Employee();
		employee2.setName("Dan Thomas");
		employee2.setJoiningDate(new LocalDate(2012, 11, 11));
		employee2.setSalary(new BigDecimal(20000));
		employee2.setSsn("ssn00000002");

		/*
		 * Persist both Employees
		 */
		service.saveEmployee(employee1);
		service.saveEmployee(employee2);

		/*
		 * Get all employees list from database
		 */
		List<Employee> employees = service.findAllEmployees();
		for (Employee emp : employees) {
			System.out.println(emp);
		}

		/*
		 * delete an employee
		 */
		service.deleteEmployeeBySsn("ssn00000002");

		/*
		 * update an employee
		 */

		Employee employee = service.findBySsn("ssn00000001");
		employee.setSalary(new BigDecimal(50000));
		service.updateEmployee(employee);

		/*
		 * Get all employees list from database
		 */
		List<Employee> employeeList = service.findAllEmployees();
		for (Employee emp : employeeList) {
			System.out.println(emp);
		}

		context.close();
		/* Note :
		 * 
		 *  (1)In case you want to drop AppConfig altogether, in above main, you just have to 
		replace :
		(2)AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		with :
		(3)AnnotationConfigApplicationContext  context = new AnnotationConfigApplicationContext();
		context.scan("com.websystique.spring"); 
		context.refresh();
		(4)Rest of code remains same. Run above program, you will see following output:
		Employee [id=1, name=Han Yenn, joiningDate=2010-10-10, salary=10000, ssn=ssn00000001]
		Employee [id=2, name=Dan Thomas, joiningDate=2012-11-11, salary=20000, ssn=ssn00000002]
		Employee [id=1, name=Han Yenn, joiningDate=2010-10-10, salary=50000, ssn=ssn00000001]
		 */
	}
}
