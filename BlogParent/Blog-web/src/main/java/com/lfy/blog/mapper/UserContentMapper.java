package com.lfy.blog.mapper;

import com.lfy.blog.pojo.UserContent;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;


import java.util.List;

/**
 *  文章的列表
 */
@Repository
public interface UserContentMapper extends Mapper<UserContent> {


    public List<UserContent> selectAll();


    List<UserContent> selectAllById(Long id);

    //查询记录条数
    int selectCounts(Long id);

    //查询访问量
    int selectByIdCount(Long id);

    List<UserContent> selectAllByIdcontent(Long id);

    List<UserContent> selectBycatery();

    void inserts(UserContent content);

    /**
     * 根据id修改数据
     * @param id
     */
    void updatecontent(Long id);

    List<UserContent> search(String keywords);
}
