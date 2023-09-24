package com.example.loginproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.loginproject.data.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {
}
