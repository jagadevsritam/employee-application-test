package org.example.repo;

import org.example.model.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class EmployeeRepo {
    public Map<Integer, Employee> readEmployees(String filePath) throws IOException {
        Map<Integer, Employee> employeeMap = new LinkedHashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] array = line.split(",");
            int id = Integer.parseInt(array[0]);
            String firstName = array[1];
            String lastName = array[2];
            double salary = Double.parseDouble(array[3]);
            Integer managerId = null;
            if(array.length>4) {
                managerId = array[4].isEmpty() ? null : Integer.parseInt(array[4]);
            }

            Employee employee = new Employee();
            employee.setId(id);
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employee.setSalary(salary);
            employee.setManagerId(managerId);
            employeeMap.put(id, employee);
        }
        reader.close();
        setSubOrdinates(employeeMap);
        return employeeMap;
    }

    private void setSubOrdinates(Map<Integer, Employee> employeeMap) {
        for(Employee employee:employeeMap.values()){
            Integer managerId = employee.getManagerId();
            if (managerId != null) {
                Employee manager = employeeMap.get(managerId);
                manager.addSubordinate(employee);
            }
        }
    }
}
