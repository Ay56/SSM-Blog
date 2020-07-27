package com.lfy.blog.service;



import com.github.pagehelper.PageInfo;
import com.lfy.blog.pojo.UserContent;
import com.lfy.blog.utils.PageHelper;
import com.lfy.blog.utils.SolrPager;

import java.util.List;

public interface SolrService {
    /**
     * 根据关键字搜索文章并分页
     * @param keyword
     * @return
     */
    SolrPager<UserContent> findByKeyWords(String keyword, Integer pageNum, Integer pageSize);




    /**
     * 添加文章到solr索引库中
     * @param userContent
     */
    void addUserContent(UserContent userContent);

    /**
     * 修改
     * @param userContent
     */
    void updateUserContent(UserContent userContent);

    /**
     * 根据文章id删除索引库
     * @param
     */
    void deleteById(Long id);
}