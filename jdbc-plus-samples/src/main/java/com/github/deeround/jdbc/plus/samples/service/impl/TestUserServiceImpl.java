package com.github.deeround.jdbc.plus.samples.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.deeround.jdbc.plus.samples.domain.TestUser;
import com.github.deeround.jdbc.plus.samples.mapper.TestUserMapper;
import com.github.deeround.jdbc.plus.samples.service.TestUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class TestUserServiceImpl extends ServiceImpl<TestUserMapper, TestUser>
        implements TestUserService {

    @Autowired
    private TestUserMapper testUserMapper;

    @Override
    public List<TestUser> getListByCondition(String name) {
        return this.testUserMapper.getListByCondition(name);
    }
}




