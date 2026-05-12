package com.lxy.service;

import com.lxy.pojo.EmpLog;
import com.lxy.pojo.PageResult;
import java.time.LocalDate;

public interface EmpLogService {

    void insertLog(EmpLog empLog);

    PageResult<EmpLog> page(LocalDate begin, LocalDate end, Integer page, Integer pageSize);
}
