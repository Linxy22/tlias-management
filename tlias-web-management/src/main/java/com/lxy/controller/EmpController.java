package com.lxy.controller;

import com.lxy.pojo.Emp;
import com.lxy.pojo.EmpQueryParam;
import com.lxy.pojo.PageResult;
import com.lxy.pojo.Result;
import com.lxy.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

//员工管理
@Slf4j
@RequestMapping("/emps")
@RestController
public class EmpController {

    @Autowired
    private EmpService empService;
//
//@GetMapping
//public Result page(@RequestParam(defaultValue = "1")Integer page,
//                   @RequestParam(defaultValue = "10")Integer pageSize, String  name, Integer gender,
//                   @DateTimeFormat (pattern = "yyyy-MM-dd") LocalDate begin,
//                   @DateTimeFormat (pattern = "yyyy-MM-dd") LocalDate end) {
//    log.info("分页查询，{}，{},{},{},{},{}", page, pageSize, name, gender, begin, end);
//    PageResult<Emp>pageResult = empService.page(page, pageSize, name, gender, begin, end);
//    return Result.success(pageResult);
//}
@GetMapping
public Result page(EmpQueryParam empQueryParam) {
    log.info("查询请求参数： {}", empQueryParam);
    PageResult pageResult = empService.page(empQueryParam);
    return Result.success(pageResult);
}

@PostMapping
    public Result save(@RequestBody Emp emp){
    log.info("请求参数：{}",emp);
    empService.save(emp);
    return Result.success();
}
@DeleteMapping
    public Result delete(@RequestParam List< Integer> ids){
    log.info("批量删除部门: ids={}",ids);
    empService.deleteByIds(ids);
    return Result.success();
}
/*查询回显*/
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        log.info("根据id查询员工的详细信息");
        Emp emp = empService.getInfo(id);
        return Result.success(emp);
    }
/*更新员工信息*/
    @PutMapping
    public Result update(@RequestBody Emp emp){
        log.info("修改员工信息：{}",emp);
        empService.update(emp);
        return Result.success();
    }
}
