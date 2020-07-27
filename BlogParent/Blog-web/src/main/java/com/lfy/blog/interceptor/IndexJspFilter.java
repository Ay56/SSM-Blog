package com.lfy.blog.interceptor;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lfy.blog.mapper.UerMapper;
import com.lfy.blog.mapper.UserContentMapper;
import com.lfy.blog.pojo.User;
import com.lfy.blog.pojo.UserContent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 自定义的拦截，实现filter接口
 */
public class IndexJspFilter implements Filter {
        public void init(FilterConfig filterConfig) throws ServletException {

        }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("===========自定义过滤器==========");
        //校验用户是否登录
        //向下转型


        ServletContext context = request.getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
        //直接拿到dao层
        UserContentMapper userContentMapper = ctx.getBean(UserContentMapper.class);
        UerMapper uerMapper=ctx.getBean(UerMapper.class);
        List<User> list1= uerMapper.selectAll(); //查询用户的所用信息
        //使用mybatis分页插件
        int pageNum=0,pageSize=0;
        if(pageNum<1)
        {
            pageNum=1;
        }
        if (pageSize < 1) {
            pageSize =4;
        }
        PageHelper.startPage(pageNum,pageSize);
        List<UserContent> list =  userContentMapper.selectAll(); //查询所有文章信息
        PageInfo<UserContent> pageInfo=new PageInfo<>(list);
        //保存request数据保存在御中
        request.setAttribute("pages",pageInfo);
        request.setAttribute("list",list1);
        //方形
        chain.doFilter(request, response);
    }

    public void destroy() {

        }
    }