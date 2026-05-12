package com.lxy.mapper;

import com.lxy.pojo.Student;
import com.lxy.pojo.StudentQueryParam;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {

    List<Student> list(StudentQueryParam param);

    Student getById(Integer id);

    void insert(Student student);

    void update(Student student);

    void deleteByIds(List<Integer> ids);

    List<Map<String, Object>> countStuClazzData();
}
