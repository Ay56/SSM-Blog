package com.lfy.blog.mapper;

import com.lfy.blog.pojo.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 用户类dao接口
 */
@Repository
public interface UerMapper  extends Mapper<User> {


    List<User> selectAll();

}
