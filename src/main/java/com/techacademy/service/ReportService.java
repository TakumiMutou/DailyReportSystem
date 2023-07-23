package com.techacademy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;
import com.techacademy.repository.ReportRepository;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository repository) {
        this.reportRepository = repository;
    }

    public List<Report> getReportList(){
        //全件検索
        return reportRepository.findAll();
    }
    public Report getReport(Integer id) {
        return reportRepository.findById(id).get();
    }
    public List<Report> getReportSelfList(Employee employee){
        return reportRepository.findByEmployee(employee);
    }


    @Transactional
    public Report saveReport(Report report) {

        return reportRepository.save(report);
    }

}
