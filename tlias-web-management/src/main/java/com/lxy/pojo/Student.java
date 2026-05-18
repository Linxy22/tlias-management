package com.lxy.pojo;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Student {
    private Integer id;
    private String name;
    private Integer gender;
    private String phone;
    private String degree;
    private Integer clazzId;
    private String clazzName;
    private LocalDate entryDate;
    private LocalDate graduationDate;
    private String idCard;
    private String image;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
