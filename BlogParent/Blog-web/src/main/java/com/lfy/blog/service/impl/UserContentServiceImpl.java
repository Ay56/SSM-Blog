package com.lfy.blog.service.impl;

import com.lfy.blog.mapper.UserContentMapper;
import com.lfy.blog.pojo.Comment;
import com.lfy.blog.pojo.UserContent;
import com.lfy.blog.service.UserContentService;
import com.lfy.blog.utils.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserContentServiceImpl implements UserContentService {

    @Autowired
    private UserContentMapper userContentMapper;

    @Override
    public PageHelper.Page<UserContent> findAll(UserContent content, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public PageHelper.Page<UserContent> findAll(UserContent content, Comment comment, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public PageHelper.Page<UserContent> findAllByUpvote(UserContent content, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public List<UserContent> selectAll() {
        return userContentMapper.selectAll();
    }

    @Override
    public List<UserContent> selectById(Long id) {

        return userContentMapper.selectAllById(id);
    }

    @Override
    public int selectCount(Long id) {
        return userContentMapper.selectCounts(id);
    }

    @Override
    public int selectByIdsum(Long id) {
        return userContentMapper.selectByIdCount(id);
    }

    @Override
    public List<UserContent> selectByIdList(Long id) {

        return userContentMapper.selectAllByIdcontent(id);
    }

    @Override
    public List<UserContent> selectByCatetry() {

        return userContentMapper.selectBycatery();
    }


    @Override
    public void insertcontent(UserContent content) {

        //插入一条记录
        userContentMapper.insert(content);
    }


    @Override
    public UserContent selectOneById(Long id) {
        UserContent userContent = new UserContent();
        userContent.setId(id);
        return userContentMapper.selectOne(userContent);
    }

    @Override
    public void update(UserContent userContent) {
        userContentMapper.updateByPrimaryKeySelective(userContent);
    }

    @Override
    public void deleteById(Long deleteId) {

        UserContent userContent = new UserContent();
        userContent.setId(deleteId);
        userContentMapper.delete(userContent);
    }

    @Override
    public List<UserContent> search(String keywords) {
        return userContentMapper.search(keywords);
    }


}
