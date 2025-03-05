package org.example;

import org.example.model.Employee;
import org.example.repo.EmployeeRepo;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Map;

public class EmployeeReportServiceTest {

    private EmployeeReportService employeeReportService;
    private EmployeeRepo employeeRepo;
    private Map<Integer, Employee> employeeMap;
    @BeforeEach
    public void setup() throws IOException {
        employeeReportService = new EmployeeReportService();
        employeeRepo = new EmployeeRepo();
        URL resourceUrl = getClass().getClassLoader().getResource("test.csv");
        if (resourceUrl != null) {
            String filePath = Paths.get(resourceUrl.getPath()).toString();
            employeeMap = employeeRepo.readEmployees(filePath);
            assertNotNull(employeeMap);
            employeeMap.entrySet().forEach(e -> System.out.println(e.getKey() + " " + e.getValue()));
        } else {
            System.out.println("File not found!");
        }
    }

    @Test
    public void testGenerateReport() {
        String report = employeeReportService.generateReport(employeeMap);
        assertNotNull(report);
        assertNotEquals(0,report.length());
        System.out.println(report);
    }
}