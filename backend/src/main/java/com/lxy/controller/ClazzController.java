package com.lxy.controller;

import com.lxy.pojo.Clazz;
import com.lxy.pojo.PageResult;
import com.lxy.pojo.Result;
import com.lxy.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/clazzs")
@RestController
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    @GetMapping
    public Result page(@RequestParam(defaultValue = "") String name,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("分页查询班级, name={}, page={}, pageSize={}", name, page, pageSize);
        PageResult<Clazz> pageResult = clazzService.page(name, page, pageSize);
        return Result.success(pageResult);
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        log.info("查询班级详情, id={}", id);
        Clazz clazz = clazzService.getById(id);
        return Result.success(clazz);
    }

    @PostMapping
    public Result save(@RequestBody Clazz clazz) {
        log.info("新增班级: {}", clazz);
        clazzService.save(clazz);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Clazz clazz) {
        log.info("修改班级: {}", clazz);
        clazzService.update(clazz);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids) {
        log.info("删除班级, ids={}", ids);
        clazzService.deleteByIds(ids);
        return Result.success();
    }
}
