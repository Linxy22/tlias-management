package com.lxy.controller;

import com.lxy.service.ReportService;
import com.lxy.pojo.JobOption;
import com.lxy.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/report")
@RestController
public class ReportController {

    @Autowired
    private ReportService reportService;

    /**
     * 统计各个职位的员工人数
     */
    @GetMapping("/empJobData")
    public Result getEmpJobData(){
        log.info("统计各个职位的员工人数");
        JobOption jobOption = reportService.getEmpJobData();
        return Result.success(jobOption);
    }
    /**
     * 统计员工性别信息
     */
    @GetMapping("/empGenderData")
    public Result getEmpGenderData(){
        log.info("统计员工性别信息");
        List<Map> genderList = reportService.getEmpGenderData();
        return Result.success(genderList);
    }

    /**
     * 统计各部门员工人数
     */
    @GetMapping("/emp")
    public Result getEmpDeptData() {
        log.info("统计各部门员工人数");
        return Result.success(reportService.getEmpDeptData());
    }

    /**
     * 统计各班级学员人数
     */
    @GetMapping("/stu")
    public Result getStuClazzData() {
        log.info("统计各班级学员人数");
        return Result.success(reportService.getStuClazzData());
    }
}