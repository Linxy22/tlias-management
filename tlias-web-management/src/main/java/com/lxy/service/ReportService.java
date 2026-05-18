package com.lxy.service;

import com.lxy.pojo.JobOption;

import java.util.List;
import java.util.Map;

public interface ReportService {
    /**
     * 统计各个职位的员工人数
     */
    JobOption getEmpJobData();

    /**
     * 统计员工性别信息
     */
    List<Map> getEmpGenderData();

    /**
     * 统计各部门员工人数
     */
    List<Map<String, Object>> getEmpDeptData();

    /**
     * 统计各班级学员人数
     */
    List<Map<String, Object>> getStuClazzData();
}