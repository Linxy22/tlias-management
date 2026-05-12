package com.lxy.mapper;

import com.lxy.pojo.Emp;
import com.lxy.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {

    //条件查询员工列表
    List<Emp> list(EmpQueryParam empQueryParam);

    /**
     * 新增员工数据
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time) " +
            "values (#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime})")
    void insert(Emp emp);

    void deleteByIds(List<Integer> ids);

    Emp getById(Integer id);

    void updateById(Emp emp);
    /**
     * 统计各个职位的员工人数
     */
    @MapKey("pos")
    List<Map<String,Object>> countEmpJobData();

    /**
     * 统计员工性别信息
     */
    @MapKey("name")
    List<Map> countEmpGenderData();

    /**
     * 统计各部门员工人数
     */
    List<Map<String, Object>> countEmpDeptData();
    /**
     * 根据用户名查询员工信息（包含密码，仅用于登录验证）
     */
    @Select("select id, username, password, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time from emp where username = #{username}")
    Emp getByUsername(String username);

    /**
     * 根据ID查询员工（包含密码，仅用于修改密码）
     */
    @Select("select id, username, password, name from emp where id = #{id}")
    Emp getByIdWithPassword(Integer id);

    /**
     * 更新员工密码
     */
    @Update("update emp set password = #{password}, update_time = #{updateTime} where id = #{id}")
    void updatePassword(Emp emp);
}
