package com.github.deeround.jdbc.plus.samples.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.deeround.jdbc.plus.samples.domain.TestLog;
import com.github.deeround.jdbc.plus.samples.mapper.TestLogMapper;
import com.github.deeround.jdbc.plus.samples.service.TestLogService;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class TestLogServiceImpl extends ServiceImpl<TestLogMapper, TestLog>
        implements TestLogService {

}




