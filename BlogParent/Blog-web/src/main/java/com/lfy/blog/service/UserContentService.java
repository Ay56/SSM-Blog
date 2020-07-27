package com.lfy.blog.service;

import com.lfy.blog.pojo.Comment;
import com.lfy.blog.pojo.UserContent;
import com.lfy.blog.utils.PageHelper;

import java.util.List;

/**
 * 文章表的接口设计里面包含了我我们自定义的分页接口
 */
public interface UserContentService {
    /**
     * 查询所有Content并分页
     * @param content
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageHelper.Page<UserContent> findAll(UserContent content, Integer pageNum, Integer pageSize);
    PageHelper.Page<UserContent> findAll(UserContent content, Comment comment, Integer pageNum, Integer pageSize);
    PageHelper.Page<UserContent> findAllByUpvote(UserContent content, Integer pageNum, Integer pageSize);


    public List<UserContent> selectAll();


    List<UserContent> selectById(Long id);

    int selectCount(Long id);

    int selectByIdsum(Long id);

    List<UserContent> selectByIdList(Long id);

    List<UserContent> selectByCatetry();

    void insertcontent(UserContent content);

    //根据id查询一条文章数据
    UserContent selectOneById(Long id);


    void update(UserContent userContent);

    void deleteById(Long deleteId);

    //文章搜索功能
    List<UserContent> search(String keywords);
}
