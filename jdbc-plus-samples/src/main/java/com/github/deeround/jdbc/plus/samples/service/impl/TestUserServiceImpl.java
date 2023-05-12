package com.github.deeround.jdbc.plus.samples.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.deeround.jdbc.plus.samples.domain.TestUser;
import com.github.deeround.jdbc.plus.samples.service.TestUserService;
import com.github.deeround.jdbc.plus.samples.mapper.TestUserMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class TestUserServiceImpl extends ServiceImpl<TestUserMapper, TestUser>
implements TestUserService{

}




