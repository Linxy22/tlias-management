package com.lxy.service.impl;

import com.lxy.mapper.EmpMapper;
import com.lxy.mapper.StudentMapper;
import com.lxy.service.ReportService;
import com.lxy.pojo.JobOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public JobOption getEmpJobData() {
        List<Map<String,Object>> list = empMapper.countEmpJobData();
        List<Object> jobList = list.stream().map(dataMap -> dataMap.get("pos")).toList();
        List<Object> dataList = list.stream().map(dataMap -> dataMap.get("total")).toList();
        return new JobOption(jobList, dataList);
    }

    @Override
    public List<Map> getEmpGenderData() {
        return empMapper.countEmpGenderData();
    }

    @Override
    public List<Map<String, Object>> getEmpDeptData() {
        return empMapper.countEmpDeptData();
    }

    @Override
    public List<Map<String, Object>> getStuClazzData() {
        return studentMapper.countStuClazzData();
    }
}