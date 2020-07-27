package com.lfy.blog.utils;

import lombok.Data;

import java.util.List;
@Data
public class SolrPager<T> {

    //当前页
    private int  pageNum;
    //每页的大小
    private int  pageSize;
    //总页数
    private int pageCount;

    //总记录数
    private Long recordCount;

    //存放当前页的数据
    private List<T> list;
}
