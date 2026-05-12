package com.lxy.service;

import com.lxy.pojo.Emp;
import com.lxy.pojo.EmpQueryParam;
import com.lxy.pojo.LoginInfo;
import com.lxy.pojo.PageResult;
import java.util.List;

public interface EmpService {
    /*分页查询*/
//     PageResult<Emp> page(Integer page, Integer pageSize,String  name, Integer gender,
//                           LocalDate begin,
//                          LocalDate end);
    PageResult<Emp> page(EmpQueryParam empQueryParam);

    /**
     * 添加员工
     * @param emp
     */
    void save(Emp emp);
//批量删除员工
    void deleteByIds(List<Integer> ids);

    Emp getInfo(Integer id);

    void update(Emp emp);

    LoginInfo login(Emp emp);

    void updatePassword(String token, String oldPwd, String newPwd);
}
