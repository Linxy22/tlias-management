package com.lxy.controller;

import com.lxy.pojo.PageResult;
import com.lxy.pojo.Result;
import com.lxy.pojo.EmpLog;
import com.lxy.service.EmpLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RequestMapping("/logs")
@RestController
public class LogController {

    @Autowired
    private EmpLogService empLogService;

    @GetMapping
    public Result page(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                       @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("查询日志, begin={}, end={}, page={}, pageSize={}", begin, end, page, pageSize);
        PageResult<EmpLog> pageResult = empLogService.page(begin, end, page, pageSize);
        return Result.success(pageResult);
    }
}
