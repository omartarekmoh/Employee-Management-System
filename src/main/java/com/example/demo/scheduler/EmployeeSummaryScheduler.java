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

// Scheduler service that logs and saves daily employee count summaries for each department.
@Service
@RequiredArgsConstructor
public class EmployeeSummaryScheduler {

    // Logger for logging department summaries.
    private static final Logger logger = Logger.getLogger(EmployeeSummaryScheduler.class.getName());
    // Service for retrieving employee counts by department.
    private final EmployeeService employeeService;
    // Repository for saving daily summary records.
    private final DailySummaryRepository dailySummaryRepository;

    /**
     * Scheduled task that runs every day at 9:00 AM to log and persist the employee count per department.
     */
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
                                .date(LocalDate.now())
                                .build())
                        .toList()
        );

    }
}
