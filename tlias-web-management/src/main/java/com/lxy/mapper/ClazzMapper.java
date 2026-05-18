package com.lxy.mapper;

import com.lxy.pojo.Clazz;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ClazzMapper {

    List<Clazz> list(String name);

    Clazz getById(Integer id);

    void insert(Clazz clazz);

    void update(Clazz clazz);

    void deleteByIds(List<Integer> ids);
}
