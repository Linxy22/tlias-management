package com.lxy.mapper;

import com.lxy.pojo.EmpExpr;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface EmpExprMapper {

    /**
     * 批量插入员工工作经历信息 - 使用注解方式避免XML配置问题
     */
//    @Insert({
//            "<script>",
//            "INSERT INTO emp_expr (emp_id, begin, end, company, job) VALUES ",
//            "<foreach collection='exprList' item='expr' separator=','>",
//            "(#{expr.empId}, #{expr.begin}, #{expr.end}, #{expr.company}, #{expr.job})",
//            "</foreach>",
//            "</script>"
//    })
//    @Options(useGeneratedKeys = false)
    int insertBatch(@Param("exprList") List<EmpExpr> exprList);
    /**
     * 根据员工的ID批量删除工作经历信息
     */
    void deleteByEmpIds(List<Integer> empIds);
}

