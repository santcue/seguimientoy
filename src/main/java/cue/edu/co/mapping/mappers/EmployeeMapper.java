package cue.edu.co.mapping.mappers;

import cue.edu.co.mapping.dtos.EmployeeDto;
import cue.edu.co.model.Employee;

public class EmployeeMapper {


    public static EmployeeDto mapFromModel(Employee employee) {
        return new EmployeeDto(employee.getId(), employee.getName(), employee.getAddress(), employee.getPhone(), employee.getEmail(),
                employee.getPost(), employee.getSalary(), employee.getEmployment_history(), employee.getPassword(), employee.getBirthdate());
    }

    public static Employee mapFromModel(EmployeeDto employee) {
        return Employee.builder()
                .name(employee.name())
                .phone(employee.phone())
                .email(employee.email())
                .address(employee.address())
                .post(employee.post())
                .salary(employee.salary())
                .employment_history(employee.employment_history())
                .password(employee.password())
                .birthdate(employee.birthdate())
                .build();
    }

}
