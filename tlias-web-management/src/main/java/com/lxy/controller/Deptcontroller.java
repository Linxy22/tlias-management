package com.lxy.controller;

import com.lxy.pojo.Dept;
import com.lxy.pojo.Result;
import com.lxy.service.DeptService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
public class Deptcontroller {

    @Autowired
    private DeptService deptService;

//    @RequestMapping(value = "/depts",method= RequestMethod.GET)//method:指定请求方式
   @GetMapping("/depts")
    public Result list(){
//        System.out.println("查询全部部门数据");
        log.info("查询全部部门数据");
        List<Dept> deptList= deptService.findAll();
        return Result.success(deptList);
    }

    /**
     * 根据ID删除部门 - 简单参数接收: 方式一 (HttpServletRequest)
     */
//    @DeleteMapping("/depts")
//    public Result delete(HttpServletRequest request){
//        String idStr = request.getParameter("id");
//        int id = Integer.parseInt(idStr);
//
//        System.out.println("根据ID删除部门: " + id);
//        return Result.success();
//    }

//    @DeleteMapping("/depts")
//    public Result delete(@RequestParam("id") Integer deptId){
//        System.out.println("根据ID删除部门: " + deptId);
//        return Result.success();
//    }


    //方式三：省略@RequestParam（前端请求的参数名与服务器端方法形参名一致）
    @DeleteMapping("/depts")
    public Result delete(Integer id){
//        System.out.println("根据ID删除部门: " + id);
        log.info("根据ID删除部门: {}" , id);
        deptService.deleteById(id);
        return Result.success();
    }

    //新增部门
    @PostMapping("/depts")
    public Result add(@RequestBody Dept dept){
//        System.out.println("新增部门: " + dept);
        log.info("新增部门:{} " , dept);
        deptService.add(dept);
        return Result.success();

    }
    //根据id查询部门
   /* @GetMapping("/depts/{id}")
    public Result getInfo(@PathVariable("id") Integer deptId){
        System.out.println("查询部门ID: " + deptId);
        return Result.success();
    }*/
    @GetMapping("/depts/{id}")
    public Result getInfo(@PathVariable Integer id){
//        System.out.println("查询部门ID: " + id);
        log.info("查询部门ID:{} " , id);
        Dept dept=deptService.getById(id);
        return Result.success(dept);
    }
    //根据id修改部门
    @PutMapping("/depts")
    public Result update(@RequestBody Dept dept){
//        System.out.println("修改部门: " + dept);
        log.info("修改部门: {}" ,dept);
        deptService.update(dept);
        return Result.success();
    }
}
