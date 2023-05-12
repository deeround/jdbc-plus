package com.github.deeround.jdbc.plus.samples.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.deeround.jdbc.plus.samples.domain.TestUser;

import java.util.List;

/**
 *
 */
public interface TestUserService extends IService<TestUser> {
    List<TestUser> getListByCondition(String name);
}
