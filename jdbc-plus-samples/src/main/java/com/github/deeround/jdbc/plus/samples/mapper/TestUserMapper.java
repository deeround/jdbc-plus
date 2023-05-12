package com.github.deeround.jdbc.plus.samples.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.deeround.jdbc.plus.samples.domain.TestUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Entity com.github.deeround.jdbc.plus.samples.domain.TestUser
 */
@Repository
public interface TestUserMapper extends BaseMapper<TestUser> {

    List<TestUser> getListByCondition(@Param("name") String name);

}




