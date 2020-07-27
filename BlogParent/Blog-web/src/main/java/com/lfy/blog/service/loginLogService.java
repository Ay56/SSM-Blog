package com.lfy.blog.service;

import com.lfy.blog.pojo.loginLog;

public interface loginLogService {



    //插入一条数据
    public void insertTo(loginLog log);


    //更新一条数据
    void update(loginLog log);

    //根据Uid查询查询用户的信息
    public loginLog selectLogByuId(Long uId);
}
