package com.lxy.service;

import com.lxy.pojo.Dept;

import java.util.List;

public interface DeptService {

    // 查询所有部门数据
    List<Dept> findAll();
//根据id删除部门的方法
    void deleteById(Integer id);

    //新增部门
    void add(Dept dept);
/*根据id查询部门*/
    Dept getById(Integer id);

    void update(Dept dept);
}
