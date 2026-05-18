package com.lxy.service.impl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lxy.mapper.EmpExprMapper;
import com.lxy.mapper.EmpMapper;
import com.lxy.pojo.*;
import com.lxy.service.EmpLogService;
import com.lxy.service.EmpService;
import com.lxy.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class EmpServiceImpl implements EmpService{

    @Autowired
    private EmpMapper empMapper;


    @Autowired
    private EmpExprMapper empExprMapper;

    @Autowired
    private EmpLogService empLogService;
//    @Override
//    public PageResult<Emp> page(Integer page, Integer pageSize) {
//
//        if (page == null || page <= 0) {
//            page = 1; // 默认第一页
//        }
//        if (pageSize == null || pageSize <= 0) {
//            pageSize = 10; // 默认每页10条记录
//        }
//        //调用mapper接口查询总记录数
//        Long total = empMapper.count();
//        //调用mapper接口，查询结果列表
//        Integer start = (page-1)*pageSize;
//        List<Emp> rows=empMapper.list(start,pageSize);
//        //封装结果PageResult
//        return new PageResult<Emp>(total,rows);
//    }

    //pagehelper分页查询
//    @Override
//    public PageResult<Emp> page(Integer page, Integer pageSize,String  name, Integer gender, LocalDate begin, LocalDate end) {
//        //1设置分页参数
//        PageHelper.startPage(page,pageSize);
//        //2执行查询
//        List<Emp> empList = empMapper.list(name,gender,begin,end);
//        //3解析查询结果
//        Page<Emp> p=(Page<Emp>) empList;
//
//        return new PageResult<Emp>(p.getTotal(), p.getResult());
//    }
    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {
        //1设置分页参数
        PageHelper.startPage(empQueryParam.getPage(),empQueryParam.getPageSize());
        //2执行查询
        List<Emp> empList = empMapper.list(empQueryParam);
        //3解析查询结果
        Page<Emp> p=(Page<Emp>) empList;

        return new PageResult<Emp>(p.getTotal(), p.getResult());
    }
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void save(Emp emp) {
        try {
            //1.补全基础属性
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());

            //2.保存员工基本信息
            empMapper.insert(emp);



            //3. 保存员工的工作经历信息 - 批量
            Integer empId = emp.getId();
            List<EmpExpr> exprList = emp.getExprList();
            if(!CollectionUtils.isEmpty(exprList)){
                exprList.forEach(empExpr -> empExpr.setEmpId(empId));
                empExprMapper.insertBatch(exprList);
            }
        } finally {
            //记录操作日志
            EmpLog empLog = new EmpLog(null, LocalDateTime.now(), emp.toString());
            empLogService.insertLog(empLog);
        }

    }
    @Transactional
    @Override
    public void deleteByIds(List< Integer> ids){
        empMapper.deleteByIds(ids);
        empExprMapper.deleteByEmpIds(ids);
    }
    @Override
    public Emp getInfo(Integer id){
        return empMapper.getById(id);
    }
    @Transactional
    @Override
    public void update(Emp emp) {
        //1. 根据ID更新员工基本信息
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);

        //2. 根据员工ID删除员工的工作经历信息 【删除老的】
        empExprMapper.deleteByEmpIds(Arrays.asList(emp.getId()));

        //3. 新增员工的工作经历数据 【新增新的】
        Integer empId = emp.getId();
        List<EmpExpr> exprList = emp.getExprList();
        if(!CollectionUtils.isEmpty(exprList)){
            exprList.forEach(empExpr -> empExpr.setEmpId(empId));
            empExprMapper.insertBatch(exprList);
        }
    }
    @Override
    public LoginInfo login(Emp emp) {
        Emp empLogin = empMapper.getUsernameAndPassword(emp);
        if(empLogin != null){
            //1. 生成JWT令牌
            Map<String,Object> dataMap = new HashMap<>();
            dataMap.put("id", empLogin.getId());
            dataMap.put("username", empLogin.getUsername());
            dataMap.put("role", empLogin.getRole());

            String jwt = JwtUtils.generateJwt(dataMap);
            LoginInfo loginInfo = new LoginInfo(empLogin.getId(), empLogin.getUsername(), empLogin.getName(), jwt, empLogin.getRole());
            return loginInfo;
        }
        return null;
    }
    }



