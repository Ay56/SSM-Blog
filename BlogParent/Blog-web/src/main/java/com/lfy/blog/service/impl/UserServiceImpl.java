package com.lfy.blog.service.impl;

import com.lfy.blog.mapper.UerMapper;
import com.lfy.blog.pojo.User;
import com.lfy.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户业务层
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {


    @Autowired
    private  UerMapper uerMapper;

    @Override
    public User login(String email, String password) {
        User user=new User();
        user.setEmail(email);
        user.setPassword(password);

        return uerMapper.selectOne(user);
    }

    /**
     * 插入一个用户信息
     * @param user
     * @return
     */
    @Override
    public int regist(User user) {
        return uerMapper.insert(user);
    }

    /**
     * 修改一个用户信息
     * @param user
     */
    @Override
    public void update(User user) {

        uerMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void delete(String email) {
        User user=new User();
        user.setEmail(email);
        uerMapper.delete(user);
    }

    /**
     * 根据邮箱校验
     * @param email
     * @return
     */
    @Override
    public User selectByEmail(String email) {
        User user=new User();
        user.setEmail(email);
        return uerMapper.selectOne(user);
    }

    /**
     * 根据手机号进行校验
     * @param phone
     * @return
     */
    @Override
    public User selectByPhone(String phone) {
        User user=new User();
        user.setPhone(phone);
        return uerMapper.selectOne(user);
    }

    @Override
    public User selectById(Long id) {
        User user = new User();
        user.setId(id);
        return uerMapper.selectOne(user);
    }

    /**
     * 查询所有信息
     * @return
     */
    @Override
    public List<User> selectAll() {
        return uerMapper.select(null);
    }
}
