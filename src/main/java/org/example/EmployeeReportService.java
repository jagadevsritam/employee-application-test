package org.example;

import org.example.model.Employee;

import java.util.*;

public class EmployeeReportService {

    public String generateReport(Map<Integer, Employee> employeeMap) {
        Map<Integer,Integer> managerPathMap = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        for (Employee employee : employeeMap.values()) {
            if (employee.getManagerId() != null) {
                double avgSalary = getAvgSubordinatesSalary(employee);
                double managerSalary = employee.getSalary();
                double twentyPercentSalary = avgSalary + avgSalary * 0.2;
                double fiftyPercentSalary = avgSalary + avgSalary * 0.5;
                if (managerSalary < twentyPercentSalary) {
                    sb.append(employee.getFirstName() + " " + employee.getLastName() + " is earning less by $" + (twentyPercentSalary - managerSalary));
                    sb.append("\n");
                } else if (managerSalary > fiftyPercentSalary) {
                    sb.append(employee.getFirstName() + " " + employee.getLastName() + " is earning more by $" + (managerSalary - fiftyPercentSalary));
                    sb.append("\n");
                }
            }
            managerPathMap.put(employee.getId(),getReportingLine(employeeMap,employee));
        }
        Map.Entry<Integer,Integer> empEntry = managerPathMap.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).stream().findFirst().get();
        Employee emp = employeeMap.get(empEntry.getKey());
        sb.append(emp.getFirstName() + " " + emp.getLastName() + " has reporting line " + (empEntry.getValue()));
        sb.append("\n");
        return sb.toString();
    }


    private double getAvgSubordinatesSalary(Employee employee) {
        List<Employee> subordinates = employee.getSubordinates();
        if (subordinates.isEmpty()) return 0;
        double totalSalary = 0;
        for (Employee subordinate : subordinates) {
            totalSalary += subordinate.getSalary();
        }
        return totalSalary / subordinates.size();
    }

    private int getReportingLine(Map<Integer, Employee> employeeMap, Employee employee) {
        int length = 0;
        while (employee.getManagerId() != null) {
            employee = employeeMap.get(employee.getManagerId());
            length++;
        }
        return length;
    }
}