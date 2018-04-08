<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="zh-CN">

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <title>Login</title>

  <!-- Bootstrap -->
  <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" />

  <!--[if lt IE 9]>
      <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

  <style>
    #register {
      display: none;
    }

    .top-ma {
      margin-top: 80px;
    }

    .top-mi {
      margin-top: 20px;
    }

    .top-mii {
      margin-top: 10px;
    }

    .bottom {
      margin-bottom: 10px;
    }

    .left {
      padding-left: 20px;
    }

    .onit {
      pointer-events: auto;
      cursor: pointer;
    }
  </style>
</head>

<body>

  <div class="container-fluid">

    <div class="row">

      <div class=" col-md-8 col-md-offset-2">
        <h1 id="title">登录&nbsp;
          <small>收藏、上传、下载</small>
        </h1>
      </div>

      <div class="col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3 panel panel-default top-ma" id="login">
        <form method="post" class="form-horizontal col-md-10 col-md-offset-1 top-mi" id="loginForm" autocomplete="off" action="/fts/users/logIn.action">
          <div class="form-group form-group-lg">
            <label class="control-label" for="inputUsername">用户名</label>
            <input type="text" class="form-control" id="inputUsername" name="ftsUsers.userName" data-toggle="popover" data-container="body" data-placement="right">
          </div>
          <div class="form-group form-group-lg has-feedback">
            <label class="control-label" for="inputPassword">密码</label>
            <input type="password" class="form-control" id="inputPassword" name="ftsUsers.password" data-toggle="popover" data-container="body" data-placement="right">
            <span class="onit top-mii left glyphicon glyphicon-eye-close form-control-feedback" onclick="eye();" aria-hidden="true"></span>
          </div>
          <div class="row bottom">
            <button type="button" class="btn btn-primary col-md-offset-7 col-sm-offset-5 col-md-2 col-sm-3 col-xs-12 bottom" id="gotoRegister">去注册</button>
            <input type="button" class="btn btn-success col-md-offset-1 col-sm-offset-1 col-md-2 col-sm-3 col-xs-12" id="loginsubmit"
              value="登录" onclick="loginCheck();">
          </div>
        </form>
      </div>

      <div class="col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3 panel panel-default top-ma" id="register">
        <form method="post" class="form-horizontal col-md-10 col-md-offset-1 top-mi" id="registerForm" autocomplete="off" action="/fts/users/submitUsers.action">
          <div class="form-group form-group-lg">
            <label class="control-label" for="inputRUsername">用户名</label>
            <input type="text" class="form-control rgForm" id="inputRUsername" name="ftsUsers.userName" data-toggle="popover" data-container="body" data-placement="right">
          </div>
          <div class="form-group form-group-lg">
            <label class="control-label" for="inputREmail">邮箱</label>
            <input type="email" class="form-control rgForm" id="inputREmail" name="ftsUsers.email" data-toggle="popover" data-container="body" data-placement="right">
          </div>
          <div class="form-group form-group-lg has-feedback">
            <label class="control-label" for="inputRPassword">密码</label>
            <input type="password" class="form-control rgForm" id="inputRPassword" name="ftsUsers.password" data-toggle="popover" data-container="body" data-placement="right">
            <span class="onit top-mii left glyphicon glyphicon-eye-close form-control-feedback" onclick="eye('R');" aria-hidden="true"></span>
          </div>
          <div class="form-group form-group-lg has-feedback">
            <label class="control-label" for="inputResPassword">重复输入密码</label>
            <input type="password" class="form-control rgForm" id="inputResPassword" data-toggle="popover" data-container="body" data-placement="right">
            <span class="onit top-mii left glyphicon glyphicon-eye-close form-control-feedback" onclick="eye('Res');" aria-hidden="true"></span>
          </div>
          <div class="row bottom">
            <button type="button" class="btn btn-primary col-md-offset-7 col-sm-offset-5 col-md-2 col-sm-3 col-xs-12 bottom" id="gotoLogin">去登录</button>
            <input type="button" class="btn btn-success col-md-offset-1 col-sm-offset-1 col-md-2 col-sm-3 col-xs-12" id="registersubmit"
              value="注册" onclick="registerCheck();">
          </div>
        </form>
      </div>

    </div>

  </div>

  <!-- bootstrap -->
  <!-- <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script> -->
  <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

  <script src="${pageContext.request.contextPath}/js/user.js"></script>
</body>

</html>