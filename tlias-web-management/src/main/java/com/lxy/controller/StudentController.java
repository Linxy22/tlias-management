package com.lxy.controller;

import com.lxy.pojo.PageResult;
import com.lxy.pojo.Result;
import com.lxy.pojo.Student;
import com.lxy.pojo.StudentQueryParam;
import com.lxy.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/students")
@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public Result page(StudentQueryParam param) {
        log.info("分页查询学员, {}", param);
        PageResult<Student> pageResult = studentService.page(param);
        return Result.success(pageResult);
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        log.info("查询学员详情, id={}", id);
        Student student = studentService.getById(id);
        return Result.success(student);
    }

    @PostMapping
    public Result save(@RequestBody Student student) {
        log.info("新增学员: {}", student);
        studentService.save(student);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Student student) {
        log.info("修改学员: {}", student);
        studentService.update(student);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids) {
        log.info("删除学员, ids={}", ids);
        studentService.deleteByIds(ids);
        return Result.success();
    }
}
