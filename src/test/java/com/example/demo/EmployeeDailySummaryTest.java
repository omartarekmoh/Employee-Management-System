package com.example.demo;

import com.example.demo.scheduler.EmployeeSummaryScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmployeeDailySummaryTest {

    @Autowired
    private EmployeeSummaryScheduler employeeSummaryScheduler;

    @Test
    void runNow() {
        employeeSummaryScheduler.logDailyDepartmentSummary(); // force-run it
    }
}
