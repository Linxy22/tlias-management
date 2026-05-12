package com.lxy.controller;

import com.lxy.pojo.Result;
import com.lxy.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequestMapping("/updatePwd")
@RestController
public class UpdatePwdController {

    @Autowired
    private EmpService empService;

    @PatchMapping
    public Result updatePwd(@RequestBody Map<String, String> params,
                             @RequestHeader("token") String token) {
        String oldPwd = params.get("oldPwd");
        String newPwd = params.get("newPwd");
        log.info("修改密码, token={}", token);
        empService.updatePassword(token, oldPwd, newPwd);
        return Result.success();
    }
}
