package com.example.demo.controller;

import com.example.demo.entity.BudgetResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BudgetTrackerController {
    @GetMapping("/api/track-budget")
    public BudgetResult trackBudget(@RequestParam double monthlySalary, @RequestParam double dailyExpenditure) {
        double totalExpenditure = dailyExpenditure * 30; // Assuming 30 days in a month
        double totalSaved = monthlySalary - totalExpenditure;

        BudgetResult result = new BudgetResult();
        result.setSpent(totalExpenditure);
        result.setSaved(totalSaved);

        return result;
    }

}
