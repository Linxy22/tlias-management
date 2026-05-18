package com.lxy.mapper;

import com.lxy.pojo.Emp;
import com.lxy.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {
//查询总记录数
    //原始分页查询
   /* @Select("select count(*) from emp e left join dept d on e.dept_id=d.id")
    public Long count();
    @Select("select e.*, d.name deptName from emp as e left join dept as d on e.dept_id = d.id limit #{start}, #{pageSize}")
    public List<Emp> list(Integer start,Integer pageSize);*/

//    @Select("select e.*, d.name deptName from emp as e left join dept as d on e.dept_id = d.id order by e.update_time desc")
//    public List<Emp> list(String  name, Integer gender, LocalDate begin, LocalDate end);

    //条件查询员工列表
    public List<Emp> list(EmpQueryParam empQueryParam);

    /**
     * 新增员工数据
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, role, create_time, update_time) " +
            "values (#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{role},#{createTime},#{updateTime})")
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
     * 根据用户名和密码查询员工信息（SQL 定义在 EmpMapper.xml）
     */
    Emp getUsernameAndPassword(Emp emp);
}
