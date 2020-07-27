package com.lfy.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lfy.blog.pojo.User;
import com.lfy.blog.pojo.UserContent;
import com.lfy.blog.pojo.loginLog;
import com.lfy.blog.service.SolrService;
import com.lfy.blog.service.UserContentService;
import com.lfy.blog.service.UserService;
import com.lfy.blog.service.loginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class ArticleController {


    @Autowired
    private loginLogService  logService;

    @Autowired
    private UserContentService contentService;

    @Autowired
    private UserService userService;


    @Autowired
    private SolrService solrService;

    /**
     * 所有文章列表
     * @return
     */
    @RequestMapping("/article")
    public String article(Long id,Model model)
    {
        //进入之前首先查询所有文章的信息
        int pageNum=0,pageSize=0;
        if(pageNum<1)
        {
            pageNum=1;
        }
        if (pageSize < 1) {
            pageSize =7;
        }
        PageHelper.startPage(pageNum,pageSize);
        //查询所有当前用户下所有文章
        List<UserContent> list=contentService.selectByIdList(id);
        PageInfo<UserContent> pageInfo=new PageInfo<>(list);
        model.addAttribute("page",pageInfo);
        return "personal/article";
    }

    /**
     * 分页查询
     */

    @RequestMapping("/artcle_list")
    public String artcle_list(Long id, Integer curr, Integer limit,Model model)
    {
        //分页查询
        PageHelper.startPage(curr,limit);
        List<UserContent> list=contentService.selectByIdList(id);
        PageInfo<UserContent> pageInfo=new PageInfo<>(list);
        model.addAttribute("page",pageInfo);
        return "personal/article";
    }

    /**
     * 报告数据初始化
     * @return
     */
    @RequestMapping("/personal")
    public String  personal(Long id, Model model)
    {
        //进入到这个先查询数据库查询登录日志
        System.out.println("我的Id是:"+id);
        //根据id查询查询报告日志
        //访问量
        int count=contentService.selectByIdsum(id);
        model.addAttribute("up",count);
        //文章数
        int size=contentService.selectCount(id);
        model.addAttribute("count",size);
        //查询登录信息
        loginLog loginLog = logService.selectLogByuId(id);
        model.addAttribute("log",loginLog);
        return "personal/personal";
    }

    /**
     * 进入文章页面初始化
     *
     */

    @RequestMapping("/add")
    public String addArticle(Model model){


        return "personal/add-article";
    }


    /**
     * 发布文章
     */

    @RequestMapping("/addArticle")
    public String addArces(MultipartFile upload,HttpServletRequest request,UserContent content,Long id) throws IOException {
        //将文件上传的位置
        String realPath = request.getSession().getServletContext().getRealPath("/uploads");
        //创建文件夹
        File file = new File(realPath);
        if(!file.exists())
        {
            //创建文件夹目录
            file.mkdirs();
        }
        //获取到上传文件的名称
        String filename = upload.getOriginalFilename();
        //将文件名称修改一成唯一的值
        String s = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        filename=s+"_"+filename;
        //上传文件  第一参数：文件夹  第二个参数文件名
        upload.transferTo(new File(file,filename));
        //上传文件的具体路径图片

        //根据uid查询当前用户信息
        User user = userService.selectById(id);


        //将数据保存到数据库中
        UserContent userContent=new UserContent();
        userContent.setUId(user.getId());
        userContent.setNickName(user.getNickName());
        userContent.setCategory(content.getCategory());
        userContent.setContent(content.getContent());
        userContent.setRptTime(content.getRptTime());
        userContent.setImgUrl(user.getImgUrl());
        userContent.setPersonal("1");
        //标题图片
        userContent.setTitlePic(filename);
        userContent.setTitle(content.getTitle());
        userContent.setTitleName(content.getTitleName());
        //更新数据库数据
        contentService.insertcontent(userContent);
        //同时更新solr数据
        solrService.addUserContent(userContent);
        System.out.println("发布成功！！");
        //返回文章的标题的首页,转发到article请求
        return "forward:/article";
    }


    /**
     * 先查询修改的数据并返回到页面
     */

    @RequestMapping("/selectupdate")
    public String update(Long id,Model model)
    {

        //首页我要查询出修改的页面值并且返回到页面上去
        //根据文章id查询数据
       UserContent userContent= contentService.selectOneById(id);
       //数据返回并且渲染到页面
        model.addAttribute("update",userContent);


        return "personal/update-article";
    }


    /**
     * 修改数据
     */

    @RequestMapping("/update")
    public String update(Long updateId,Long id,String titlePic,HttpServletRequest request,MultipartFile upload,UserContent content) throws IOException {

        //将文件上传的位置
        String realPath = request.getSession().getServletContext().getRealPath("/uploads");
       UserContent userContent = new UserContent();
       String  str=upload.getOriginalFilename();
       if(!str.equals("")&&str!=null)
       {
           //判断有没有上传新的图片
           if(upload!=null&&!"".equals(upload))
           {

               //删除原来的文件图片
               if(titlePic!=null&&!"".equals(titlePic))
               {
                   File file = new File(realPath + titlePic);
                   //删除
                   file.delete();
               }
               //创建文件夹
               File file = new File(realPath);
               if(!file.exists())
               {
                   //创建文件夹目录
                   file.mkdirs();
               }
               //获取到上传文件的名称
               String filename = upload.getOriginalFilename();
               //将文件名称修改一成唯一的值
               String s = UUID.randomUUID().toString().replace("-", "").toUpperCase();
               filename=s+"_"+filename;
               //上传文件  第一参数：文件夹  第二个参数文件名
               upload.transferTo(new File(file,filename));
               userContent.setTitlePic(filename);
           }
       }else {
           userContent.setTitlePic(titlePic);
       }

        //更新数据
        //根据id去更新数据
        userContent.setId(updateId);
        userContent.setTitle(content.getTitle());
        userContent.setContent(content.getContent());
        userContent.setCategory(content.getCategory());
        userContent.setTitleName(content.getTitleName());
        userContent.setRptTime(content.getRptTime());
        //更新文章
        contentService.update(userContent);
        //更新修改数据的solr
        solrService.updateUserContent(userContent);
        //更新之后跳转到文章页面
        return "forward:/article";
    }



    @RequestMapping("/delete")
    public String delete(Long id,Long deleteId)
    {

        //根据id删除该文章
        contentService.deleteById(deleteId);
        solrService.deleteById(deleteId);
        return "forward:/article";
    }

    //文章的搜索功能
    @RequestMapping("/serach")
    public String  serach(String keywords,Long id)
    {

        List<UserContent> list=contentService.search(keywords);

        return  "forward:/article";
    }
}
