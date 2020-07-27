package com.lfy.blog.controller;

import com.lfy.blog.pojo.User;
import com.lfy.blog.pojo.loginLog;
import com.lfy.blog.service.UserService;
import com.lfy.blog.service.loginLogService;
import com.lfy.blog.utils.CodeCaptchaServlet;
import com.lfy.blog.utils.MD5Util;
import com.lfy.blog.utils.SendEmail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 注册校验后台数据
 */
@Controller
public class RegisterController {

    private final static Logger log = Logger.getLogger(RegisterController.class);

    //自动注入redisTiatem
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //调用dao层验证手机号
    @Autowired
    private UserService userService;


    //更新登录日志信息
    @Autowired
    private loginLogService  loginLogService;

    //接收前端的数据手机号
    //验证手机号是否可用
    @RequestMapping("/checkPhone")
    @ResponseBody
    public Map<String,Object> checkPhone(String phone)
    {

        //根据手机号查询数据
        Map<String,Object> map=new HashMap<>();
        User user = userService.selectByPhone(phone);
        if(user==null)
        {
            //如果是空可以注册
            map.put("message","success");
        }else {
            //如果不是空返回失败
            map.put("message","fail");
        }
        return map;
    }


    //验证验证码
    @RequestMapping("/checkCode")
    @ResponseBody
    public Map<String,Object> checkCode(String code, HttpServletRequest request)
    {
        //获取验证码
        System.out.println("验证码是:"+code);
        //校验，首先获得域对象的session对象中的验证码
        String codes= (String) request.getSession().getAttribute(CodeCaptchaServlet.VERCODE_KEY);
        //创建一个容器
        Map<String,Object> map=new HashMap<>();
        //如果相等则返回
        if(code.equals(codes))
        {
            //相同成功
            map.put("message","success");
        }else {
            map.put("message","fail");
        }
        return map;
    }



    //验证邮箱是否注册
    @RequestMapping("/checkEmail")
    @ResponseBody
    public Map<String,Object> checkEmail(String email)
    {
        //调用业务层查看邮箱是否被注册
        Map<String,Object> map=new HashMap<>();
        User user = userService.selectByEmail(email);
        if(user==null)
        {
            //可以使用该邮箱
            map.put("message","success");
        }else {
            map.put("message","fail");
        }
        return map;
    }





    //注册流程
    @RequestMapping("/doRegister")
    public String doRegister(User user, String code, Model model,HttpServletRequest request)
    {



        //再次验证验证码,如果是空
        if(StringUtils.isEmpty(code))
        {
            model.addAttribute("error","验证码不能为空");
            //返回登录页面
            return "../register";
        }


        int b=checkValidateCode(code);
        if(b==0)
        {
            model.addAttribute("error", "验证码不正确,请重新输入!");
            return "../register";
        }

        //根据邮箱查询是否已经注册
        User user1=userService.selectByEmail(user.getEmail());
        if(user1!=null)
        {
            //说明已经被注册了
            model.addAttribute("error","该邮箱已注册");
            return "../register";
        }else {
            //可以注册
            user1=new User();
            user1.setNickName(user.getNickName());
            user1.setEmail(user.getEmail());
            user1.setPhone(user.getPhone());
            user1.setPassword(MD5Util.encodeToHex(user.getPassword()+"aa"));
            user1.setState("0");
            user1.setEnable("0");
            user1.setImgUrl("/images/icon_m.jpg");
            //生成邮件激活码邮箱+密码+加盐
            String voCode=MD5Util.encodeToHex("aa"+user1.getEmail()+user1.getPassword());
            //将激活码存入redis缓存中
            redisTemplate.opsForValue().set(user1.getEmail(),voCode,24, TimeUnit.HOURS);

              //调用业务层保存用户数据存入mysql数据库中
            userService.regist(user1);

            //获取插入自增的主键并返回


                //注册成功
            System.out.println("注册成功");
                //发送邮件
            SendEmail.sendEmailMessage(user1.getEmail(),voCode);
                //将邮件和激活码赋值拼接
            String message=user1.getEmail()+","+voCode;
                //将数据返回到model中
            model.addAttribute("message",message);
                //并跳转注册成功页面
            return "regists/registerSuccess";
        }




    }


    //重新发送邮箱
    @RequestMapping("/sendEmail")
    @ResponseBody
    public Map<String,Object> sendEmail(String email,String  validateCode)
    {
        Map<String,Object> map=new HashMap<>();
        //重新发送邮件
        SendEmail.sendEmailMessage(email,validateCode);
        map.put("success","success");
        return map;
    }


    //邮箱的发送验证激活码
    @RequestMapping("/activecode")
    public String active(String email,String validateCode,Model model)
    {
        //判断邮箱是否激活
        //从reis中获取这个激活码，根据key  email获取
        String code=redisTemplate.opsForValue().get(email);
        //1.根据邮箱查询一下用户信息查询一下状态
        User user=userService.selectByEmail(email);
        //2.如果状态已经是激活状态直接去登录界面
        if(user!=null&&"1".equals(user.getState()))
        {
            //已激活直接去登录
            model.addAttribute("success","您已激活，请直接登录");
            return "../login";
        }

        //3.判断如果过期
        if(code==null)
        {
            //激活码过期
            model.addAttribute( "fail","您的激活码已过期,请重新注册！" );

            //根据邮箱过期删除激活码，该用户信息
            userService.delete(email);
            return "regists/activeFail";
        }


        //4.如果激活码正确,前端的激活码，和redis中的激活做匹配
        if(code!=null&&validateCode.equals(code))
        {


            //获取电脑上的ip
            String ip="";
            try {
                ip= InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

            user.setEnable("1");
            user.setState("1");
            //更新用户信息
            userService.update(user);

            //并把插入数据的值更新到登录日志中去
            loginLog log=new loginLog();
            log.setUId(user.getId());
            log.setIp(ip);
            log.setCreateTime(new Date());
            log.setLoginNum(0L);
            //设置登录的用户信息
            loginLogService.insertTo(log);

            model.addAttribute("email",email);
            //跳转到激活成功页面
            return "regists/activeSuccess";
        }else {
            //激活码错误
            model.addAttribute( "fail","您的激活码错误,请重新激活！" );
            return "regists/activeFail";
        }

    }





    // 匹对验证码的正确性
    public int checkValidateCode(String code) {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Object vercode = attrs.getRequest().getSession().getAttribute(CodeCaptchaServlet.VERCODE_KEY);
        if (null == vercode) {
            return -1;
        }
        if (!code.equalsIgnoreCase(vercode.toString())) {
            return 0;
        }
        return 1;
    }




}
