package com.lfy.blog.controller;

import com.lfy.blog.mapper.UerMapper;
import com.lfy.blog.pojo.User;
import com.lfy.blog.pojo.UserContent;
import com.lfy.blog.pojo.loginLog;
import com.lfy.blog.service.UserContentService;
import com.lfy.blog.service.UserService;
import com.lfy.blog.service.loginLogService;
import com.lfy.blog.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 登录流程
 */
@Controller
public class loginController {


    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private loginLogService  loginLogService;

    @Autowired
    private UserContentService  userContentService;

    @RequestMapping("/doLogin")
    public String doLogin(String username, String password,String code,String telephone,Model model, HttpServletRequest request)
    {

        //获取电脑上的ip
        String ip="";
        try {
            ip= InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        //登录流程(判断是手机登录还是邮箱登录)
        //1.首先接收参数
        if(telephone!=null&&!"".equals(telephone))
        {

            //使用的手机登录
            //首先获取存入redis中的随机验证码
            String  codes= (String) redisTemplate.opsForValue().get(telephone);

            //然后将验证用户输入的验证码和redis中的验证码对比
            if(codes.equals(code)){

                //根据手机号查询该用户信息
                User user = userService.selectByPhone(telephone);
                //验证码正确
                //更新登录日志
                //将登录日志查询出来返回到前端
                loginLog loginLog = loginLogService.selectLogByuId(user.getId());
                //更新登录日志

                loginLog log=new loginLog();
                log.setCreateTime(new Date());
                log.setUId(user.getId());
                log.setId(loginLog.getId());
                log.setIp(ip);
                log.setStatus(1L);
                log.setLoginNum(loginLog.getLoginNum()+1);
                //更新数据
                loginLogService.update(log);
                //将登录日志查询出来返回到前端
                loginLog loginLog1 = loginLogService.selectLogByuId(user.getId());
                model.addAttribute("log",loginLog1);
                //保存用户信息
                request.getSession().setAttribute("user",user);
                //激活状态,直接跳转重定向到后台初始化界面
                return "personal/personal";
            }else {
                //验证码错误或过期
                model.addAttribute("error","phone_fail");
                return  "../login";
            }

        }else {
            password= MD5Util.encodeToHex(password+"aa");
            //2.盗用业务成查询这个用户
            User user=userService.login(username,password);
            //3.判断是否是空,如果不是空
            if(user!=null)
            {
                //状态没有修改的话
                if("0".equals(user.getState()))
                {
                    //未激活
                    model.addAttribute("email",username);
                    model.addAttribute("error","active");
                    return "../login";
                }

                //将登录日志查询出来返回到前端
                loginLog loginLog = loginLogService.selectLogByuId(user.getId());
                //更新登录日志

                loginLog log=new loginLog();
                log.setCreateTime(new Date());
                log.setUId(user.getId());
                log.setId(loginLog.getId());
                log.setIp(ip);
                log.setStatus(1L);
                log.setLoginNum(loginLog.getLoginNum()+1);
                //更新数据
                loginLogService.update(log);
                //将登录日志查询出来返回到前端
                loginLog loginLog1 = loginLogService.selectLogByuId(user.getId());
                //根据查询文章的信息返回查询条数
                int  contents=userContentService.selectByIdsum(user.getId());

                int count=userContentService.selectCount(user.getId());
                //访问量
                model.addAttribute("up",contents);
                //总记录数
                model.addAttribute("count",count);

                model.addAttribute("log",loginLog1);
                //用户登录成功,保存用户信息
                request.getSession().setAttribute("user",user);


                //跳转首页
                return "personal/personal";
            }else {
                //登录失败
                model.addAttribute("error","fail");
                return "../login";
            }
        }
    }

    /**
     * 登录请求
     * @param request
     * @return
     */
    @RequestMapping("/login")
    public String login(HttpServletRequest request)
    {
        User user = (User) request.getSession().getAttribute("user");
        if(user!=null)
        {
            return "personal/personal";
        }
        return "../login";
    }


    /**
     * 退出登录
     * @param request
     * @return
     */
    @RequestMapping("/loginout")
    public String exit(HttpServletRequest request) {

        //删除session中的数据
        request.getSession().removeAttribute("user");

        request.getSession().invalidate();

        return "../login";
    }




}
