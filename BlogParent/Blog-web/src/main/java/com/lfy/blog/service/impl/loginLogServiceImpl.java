package com.lfy.blog.service.impl;

import com.lfy.blog.mapper.loginLogMapper;
import com.lfy.blog.pojo.loginLog;
import com.lfy.blog.service.loginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 登录日志业务层
 */
@Service
public class loginLogServiceImpl  implements loginLogService {


    @Autowired
    private loginLogMapper logMapper;

    @Override
    public void insertTo(loginLog log) {
        //增加一条数据
        logMapper.insert(log);


    }

    @Override
    public void update(loginLog log) {
        //更新一条数据
        logMapper.updateByPrimaryKeySelective(log);
    }

    @Override
    public loginLog selectLogByuId(Long uId) {
        //根据uid查询一条用户当前登录的状态
        loginLog loginLog = new loginLog();
        loginLog.setUId(uId);
        return logMapper.selectOne(loginLog);
    }
}
