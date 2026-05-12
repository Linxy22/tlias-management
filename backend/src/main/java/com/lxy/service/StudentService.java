package com.lxy.service;

import com.lxy.pojo.PageResult;
import com.lxy.pojo.Student;
import com.lxy.pojo.StudentQueryParam;
import java.util.List;

public interface StudentService {

    PageResult<Student> page(StudentQueryParam param);

    Student getById(Integer id);

    void save(Student student);

    void update(Student student);

    void deleteByIds(List<Integer> ids);
}
