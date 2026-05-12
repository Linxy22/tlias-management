package com.lxy.mapper;

import com.lxy.pojo.EmpLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.List;

@Mapper
public interface EmpLogMapper {

    @Insert("insert into emp_log (operate_time, info) values (#{operateTime}, #{info})")
    void insert(EmpLog empLog);

    List<EmpLog> list(@Param("begin") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                      @Param("end") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end);
}
