package com.example.demo.scheduler;

import com.example.demo.model.DailySummary;
import com.example.demo.model.Department;
import com.example.demo.repository.DailySummaryRepository;
import com.example.demo.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class EmployeeSummaryScheduler {

    private static final Logger logger = Logger.getLogger(EmployeeSummaryScheduler.class.getName());
    private final EmployeeService employeeService;
    private final DailySummaryRepository dailySummaryRepository;

    @Scheduled(cron = "0 0 9 * * *") // Runs every day at 9:00 AM
    public void logDailyDepartmentSummary() {
        Map<Department, Long> departmentCounts = employeeService.getEmployeeCountByDepartment();

        departmentCounts.forEach((department, count) ->
                logger.info("Department: " + department + " - Total Employees: " + count)
        );

        dailySummaryRepository.saveAll(
                departmentCounts.entrySet().stream()
                        .map(entry -> DailySummary.builder()
                                .department(String.valueOf(entry.getKey()))
                                .employeeCount(entry.getValue())
                                .date(LocalDate.now()) // You must provide the date
                                .build())
                        .toList()
        );

    }
}
