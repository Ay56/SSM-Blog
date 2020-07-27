package com.lfy.blog.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lfy.blog.pojo.User;
import com.lfy.blog.pojo.UserContent;
import com.lfy.blog.service.SolrService;
import com.lfy.blog.service.UserContentService;
import com.lfy.blog.utils.SolrPager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/*
首页数据刷新
 */
@Controller
public class IndexJspController {


    @Autowired
    private UserContentService userContentService;


    //搜索功能
    @Autowired
    private SolrService solrService;



    /**
     * 分页查询
     *
     */
    @RequestMapping("/index_list")
    public String findAllList(Model model,Integer curr,Integer limit,HttpServletRequest request,String keyword)
    {
        System.out.println("方法来了"+keyword);


        //首先判断一下登录还是退出
        User user= (User) request.getSession().getAttribute("user");
        if(user!=null)
        {
            model.addAttribute("user",user);
        }
        if(keyword!=null&&!"".equals(keyword))
        {
            //solr的搜索功能
            SolrPager<UserContent> byKeyWords = solrService.findByKeyWords(keyword, curr, limit);
            model.addAttribute("keyword",keyword); //将这个值显示到页面上
            model.addAttribute("pages",byKeyWords);
        }
        else {
            //分页的查询
            PageHelper.startPage(curr,limit);
            List<UserContent> list = userContentService.selectAll();
            //将数据封装
            PageInfo<UserContent> contentPageInfo=new PageInfo<>(list);
            //保存request数据保存在御中
            request.setAttribute("pages",contentPageInfo);
        }
        return "../index";
    }


    /**
     * 检查是否登录
     */
    @RequestMapping("/deindex")

    public String pageLogin(Long id,Model model)
    {
        //如果id为空，则进入给出登录提示
        if(id==null)
        {
            //
            model.addAttribute("msg","请先登录");
            return "../index";
        }else {
            //进入查看文章详情
           return "personal/detol";
        }

    }

}
