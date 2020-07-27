package com.lfy.blog.service;

import com.lfy.blog.pojo.User;

import java.util.List;

/**
 * 用户类接口的设计
 */
public interface UserService {


    /**
     * 用户的登录
     */
    public User  login(String email,String password);


    /**
     * 用户的注册
     */

    public int  regist(User user);


    /**
     * 修改用户
     */
    public void update(User user);

    /**
     * 根据邮箱账号删除用户
     */

    public void  delete(String  email);

    /**
     * 根据邮箱账号查询用户
     */

    public User  selectByEmail(String  email);

    /**
     * 根据手机号查询用户
     */
    public User  selectByPhone(String phone);

    /**
     * 根据id查询用户
     */

    public  User  selectById(Long id);

    /**
     * 查询所有用户信息
     */
    public List<User> selectAll();
}
