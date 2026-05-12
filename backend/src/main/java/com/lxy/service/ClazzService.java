package com.lxy.service;

import com.lxy.pojo.Clazz;
import com.lxy.pojo.PageResult;
import java.util.List;

public interface ClazzService {

    PageResult<Clazz> page(String name, Integer page, Integer pageSize);

    Clazz getById(Integer id);

    void save(Clazz clazz);

    void update(Clazz clazz);

    void deleteByIds(List<Integer> ids);
}
