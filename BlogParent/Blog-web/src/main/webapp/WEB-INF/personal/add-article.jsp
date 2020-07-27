<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>写文章 -管理</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/static2/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static2/css/style.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/static2/css/font-awesome.min.css">
    <link rel="apple-touch-icon-precomposed" href="${ctx}/static2/images/icon/icon.png">
    <link rel="shortcut icon" href="${ctx}/static2/images/icon/favicon.ico">
    <!--引入layui插件-->
    <link rel="stylesheet" href="/layui/css/layui.css">
    <script src="${ctx}/static2/js/jquery-2.1.4.min.js"></script>

    <!--[if gte IE 9]>
    <script src="${ctx}/static2/js/jquery-1.11.1.min.js" type="text/javascript"></script>
    <script src="${ctx}/static2/js/html5shiv.min.js" type="text/javascript"></script>
    <script src="${ctx}/static2/js/respond.min.js" type="text/javascript"></script>
    <script src="${ctx}/static2/js/selectivizr-min.js" type="text/javascript"></script>
    <![endif]-->
    <!--[if lt IE 9]>
    <script>window.location.href='upgrade-browser.html';</script>

    <![endif]-->
</head>

<body class="user-select">
<section class="container-fluid">
    <header>
        <nav class="navbar navbar-default navbar-fixed-top">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false"> <span class="sr-only">切换导航</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
                    <a class="navbar-brand" href="/">YlsatCMS</a> </div>
                <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="">消息 <span class="badge">1</span></a></li>
                        <li class="dropdown"> <a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${user.nickName} <span class="caret"></span></a>
                            <ul class="dropdown-menu dropdown-menu-left">
                                <li><a title="查看或修改个人信息" data-toggle="modal" data-target="#seeUserInfo">个人信息</a></li>
                                <li><a title="查看您的登录记录" data-toggle="modal" data-target="#seeUserLoginlog">登录记录</a></li>
                            </ul>
                        </li>
                        <li><a href="${ctx}/loginout" onClick="if(!confirm('是否确认退出？'))return false;">退出登录</a></li>
                        <li><a href="/index.jsp">去到首页</a></li>
                    </ul>
                    <form action="${ctx}/serach?id=${user.id}" method="post" class="navbar-form navbar-right" role="search">
                        <div class="input-group">
                            <input type="text" class="form-control"  name="keywords" autocomplete="off" placeholder="键入关键字搜索" maxlength="15">
                            <span class="input-group-btn">
              <button class="btn btn-default" type="submit">搜索</button>
              </span> </div>
                    </form>
                </div>
            </div>
        </nav>
    </header>
    <div class="row">
        <aside class="col-sm-3 col-md-2 col-lg-2 sidebar">
            <ul class="nav nav-sidebar">
                <li><a href="${ctx}/personal?id=${user.id}">报告</a></li>
            </ul>
            <ul class="nav nav-sidebar">
                <li class="active"><a href="article.html">文章</a></li>
                <li><a href="notice.html" data-toggle="tooltip" data-placement="top" title="网站暂无留言功能">公告</a></li>
                <li><a href="comment.html"  data-toggle="tooltip" data-placement="top" title="网站暂无留言功能">评论</a></li>
                <li><a data-toggle="tooltip" data-placement="top" title="网站暂无留言功能">留言</a></li>
            </ul>
            <ul class="nav nav-sidebar">
                <li><a href="category.html" data-toggle="tooltip" data-placement="top" title="网站暂无留言功能">栏目</a></li>
                <li><a class="dropdown-toggle" id="otherMenu" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" data-toggle="tooltip" data-placement="top" title="网站暂无留言功能">其他</a>
                    <ul class="dropdown-menu" aria-labelledby="otherMenu">
                        <li><a href="user-group.html">友情链接</a></li>
                        <li><a href="loginlog.html">访问记录</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="nav nav-sidebar">
                <li><a class="dropdown-toggle" id="userMenu" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" data-toggle="tooltip" data-placement="top" title="网站暂无留言功能">用户</a>
                    <ul class="dropdown-menu" aria-labelledby="userMenu">
                        <li><a href="user-group.html">管理用户组</a></li>
                        <li><a href="manage-user.html">管理用户</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="loginlog.html">管理登录日志</a></li>
                    </ul>
                </li>
                <li><a class="dropdown-toggle" id="settingMenu" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" data-toggle="tooltip" data-placement="top" title="网站暂无留言功能">设置</a>
                    <ul class="dropdown-menu" aria-labelledby="settingMenu">
                        <li><a href="setting.html">基本设置</a></li>
                        <li><a href="readset.html">用户设置</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="safety.html">安全配置</a></li>
                        <li role="separator" class="divider"></li>
                        <li class="disabled"><a>扩展菜单</a></li>
                    </ul>
                </li>
            </ul>
        </aside>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-lg-10 col-md-offset-2 main" id="main">
            <div class="row">
                <form action="${ctx}/addArticle?id=${user.id}"  id="blog_form" method="post" class="add-article-form" enctype="multipart/form-data">
                    <div class="col-md-9">
                        <h1 class="page-header">撰写新文章</h1>
                        <div class="form-group">
                            <label for="article-title" class="sr-only">标题</label>
                            <input type="text" id="article-title" name="title" class="form-control" placeholder="在此处输入标题" required autofocus autocomplete="off">
                        </div>
                        <div class="form-group">
                            <label for="article-content" class="sr-only">内容</label>
                            <div id="article-content" name="content"  ></div>
                        </div>
                        <div class="add-article-box">
                            <h2 class="add-article-box-title"><span>关键字</span></h2>
                            <div class="add-article-box-content">
                                <input type="text" class="form-control" placeholder="请输入关键字" name="keywords" autocomplete="off">
                                <span class="prompt-text">多个标签请用英文逗号,隔开。</span>
                            </div>
                        </div>
                        <div class="add-article-box">
                            <h2 class="add-article-box-title"><span>描述</span></h2>
                            <div class="add-article-box-content">
                                <textarea class="form-control" name="describe" autocomplete="off" required=""></textarea>
                                <span class="prompt-text">描述是可选的手工创建的内容总结，并可以在网页描述中使用</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <h1 class="page-header">操作</h1>
                        <div class="add-article-box">
                            <h2 class="add-article-box-title"><span>栏目</span></h2>
                            <div class="add-article-box-content">
                                <ul class="category-list">
                                    <li>
                                        <label>
                                            <input name="category" type="radio" value="武侠梦" checked>
                                            武侠梦 <em class="hidden-md">( 栏目ID: <span>1</span> )</em></label>
                                    </li>
                                    <li>
                                        <label>
                                            <input name="category" type="radio" value="惊悚梦">
                                            惊悚梦 <em class="hidden-md">( 栏目ID: <span>2</span> )</em></label>
                                    </li>
                                    <li>
                                        <label>
                                            <input name="category" type="radio" value="其他梦">
                                            其他梦 <em class="hidden-md">( 栏目ID: <span>3</span> )</em></label>
                                    </li>
                                    <li>
                                        <label>
                                            <input name="category" type="radio" value="爱情梦">
                                            爱情梦 <em class="hidden-md">( 栏目ID: <span>4</span> )</em></label>
                                    </li>
                                    <li>
                                        <label>
                                            <input name="category" type="radio" value="工作梦">
                                            工作梦 <em class="hidden-md">( 栏目ID: <span>5</span> )</em></label>
                                    </li>
                                    <li>
                                        <label>
                                            <input name="category" type="radio" value="美食梦">
                                            美食梦 <em class="hidden-md">( 栏目ID: <span>5</span> )</em></label>
                                    </li>
                                    <li>
                                        <label>
                                            <input name="category" type="radio" value="动物梦">
                                            动物梦 <em class="hidden-md">( 栏目ID: <span>5</span> )</em></label>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div class="add-article-box">
                            <h2 class="add-article-box-title"><span>标签</span></h2>
                            <div class="add-article-box-content">
                                <input type="text" class="form-control" placeholder="输入新标签" name="titleName" required="">
                                <span class="prompt-text">多个标签请用英文逗号,隔开</span> </div>
                        </div>
                        <div class="add-article-box">
                            <h2 class="add-article-box-title"><span>标题图片</span></h2>
                            <div class="add-article-box-content">
                                <img src="" id="imageview" class="item_img" style="display: none;">
                                <input type="file"  name="upload" id="fileupload" >
                            </div>
                        </div>
                        <div class="add-article-box">
                            <h2 class="add-article-box-title"><span>发布</span></h2>
                            <div class="add-article-box-content">
                                <p><label>状态：</label><span class="article-status-display">未发布</span></p>
                                <p><label>公开度：</label><input type="radio" name="personal" value="0" checked/>公开 <input type="radio" name="personal" value="1" />加密</p>
                                <p><label>发布于：</label><span class="article-time-display"><input  type="text" name="rptTime"  id="test1" /></span></p>
                            </div>
                            <div class="add-article-box-footer">
                                <button class="btn btn-primary" type="button"  id="send">发布</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</section>
<!--个人信息模态框-->
<div class="modal fade" id="seeUserInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <form action="" method="post">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" >个人信息</h4>
                </div>
                <div class="modal-body">
                    <table class="table" style="margin-bottom:0px;">
                        <thead>
                        <tr> </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td wdith="20%">姓名:</td>
                            <td width="80%"><input type="text" value="王雨" class="form-control" name="truename" maxlength="10" autocomplete="off" /></td>
                        </tr>
                        <tr>
                            <td wdith="20%">用户名:</td>
                            <td width="80%"><input type="text" value="admin" class="form-control" name="username" maxlength="10" autocomplete="off" /></td>
                        </tr>
                        <tr>
                            <td wdith="20%">电话:</td>
                            <td width="80%"><input type="text" value="18538078281" class="form-control" name="usertel" maxlength="13" autocomplete="off" /></td>
                        </tr>
                        <tr>
                            <td wdith="20%">旧密码:</td>
                            <td width="80%"><input type="password" class="form-control" name="old_password" maxlength="18" autocomplete="off" /></td>
                        </tr>
                        <tr>
                            <td wdith="20%">新密码:</td>
                            <td width="80%"><input type="password" class="form-control" name="password" maxlength="18" autocomplete="off" /></td>
                        </tr>
                        <tr>
                            <td wdith="20%">确认密码:</td>
                            <td width="80%"><input type="password" class="form-control" name="new_password" maxlength="18" autocomplete="off" /></td>
                        </tr>
                        </tbody>
                        <tfoot>
                        <tr></tr>
                        </tfoot>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="submit" class="btn btn-primary">提交</button>
                </div>
            </div>
        </form>
    </div>
</div>
<!--个人登录记录模态框-->
<div class="modal fade" id="seeUserLoginlog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" >登录记录</h4>
            </div>
            <div class="modal-body">
                <table class="table" style="margin-bottom:0px;">
                    <thead>
                    <tr>
                        <th>登录IP</th>
                        <th>登录时间</th>
                        <th>状态</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>::1:55570</td>
                        <td>2016-01-08 15:50:28</td>
                        <td>成功</td>
                    </tr>
                    <tr>
                        <td>::1:64377</td>
                        <td>2016-01-08 10:27:44</td>
                        <td>成功</td>
                    </tr>
                    <tr>
                        <td>::1:64027</td>
                        <td>2016-01-08 10:19:25</td>
                        <td>成功</td>
                    </tr>
                    <tr>
                        <td>::1:57081</td>
                        <td>2016-01-06 10:35:12</td>
                        <td>成功</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">朕已阅</button>
            </div>
        </div>
    </div>
</div>
<!--微信二维码模态框-->
<div class="modal fade user-select" id="WeChat" tabindex="-1" role="dialog" aria-labelledby="WeChatModalLabel">
    <div class="modal-dialog" role="document" style="margin-top:120px;max-width:280px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="WeChatModalLabel" style="cursor:default;">微信扫一扫</h4>
            </div>
            <div class="modal-body" style="text-align:center"> <img src="images/weixin.jpg" alt="" style="cursor:pointer"/> </div>
        </div>
    </div>
</div>
<!--提示模态框-->
<div class="modal fade user-select" id="areDeveloping" tabindex="-1" role="dialog" aria-labelledby="areDevelopingModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="areDevelopingModalLabel" style="cursor:default;">该功能正在日以继夜的开发中…</h4>
            </div>
            <div class="modal-body"> <img src="/static2/images/baoman/baoman_01.gif" alt="深思熟虑" />
                <p style="padding:15px 15px 15px 100px; position:absolute; top:15px; cursor:default;">很抱歉，程序猿正在日以继夜的开发此功能，本程序将会在以后的版本中持续完善！</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" data-dismiss="modal">朕已阅</button>
            </div>
        </div>
    </div>
</div>
<!--右键菜单列表-->
<div id="rightClickMenu">


</div>

<script src="${ctx}/static2/js/bootstrap.min.js"></script>
<script src="${ctx}/static2/js/admin-scripts.js"></script>
<script src="${ctx}/static2/lib/ueditor/ueditor.config.js"></script>
<script src="${ctx}/static2/lib/ueditor/ueditor.all.min.js"> </script>
<script src="${ctx}/static2/lib/ueditor/lang/zh-cn/zh-cn.js"></script>
<!--使用layui的日期时间选择-->
<script src="/layui/layui.js"></script>
<script type="text/javascript">
    var editor = UE.getEditor('article-content');

    $(function () {
        //显示图片
        $("#fileupload").change(function () {
            var $file=$(this);
            var objUrl=$file[0].files[0];
            //获得一个http格式的url路径
            var windowUrl=window.URL || window.webkitURL;

            var dataURL;
            dataURL=windowUrl.createObjectURL(objUrl);
            $("#imageview").attr("src",dataURL);
            if($("#imageview").attr("style") === 'display: none;'){
                $("#imageview").attr("style","inline");
                $("#imageview").width("200px");
                $("#imageview").height("200px");
            }

        });


        //点击发布按钮的点击事件
        $('#send').click(function () {
            //提交表单
            $('#blog_form').submit();
        });


    });

    //使用layui日期插件
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#test1', //指定元素
            type: 'datetime',
            value:'xxxx-xx-xx'
        });
    });
</script>
</body>
</html>

