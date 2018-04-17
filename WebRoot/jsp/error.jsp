<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
  <title>404 Error</title>
  <!-- Bootstrap -->
  <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
  <!-- HTML5 shim 和 Respond.js 是为了让 IE8 支持 HTML5 元素和媒体查询（media queries）功能 -->
  <!-- 警告：通过 file:// 协议（就是直接将 html 页面拖拽到浏览器中）访问页面时 Respond.js 不起作用 -->
  <!--[if lt IE 9]>
      <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>
  <div class="container">
    <div class="row">
      <div class="col-md-8 col-md-offset-2">
        <img src="${pageContext.request.contextPath}/img/404.png" class="img-responsive center-block">
      </div>
      <div class="col-md-8 col-md-offset-2">
        <br/>
        <p class="h1">
          <strong>404 Not Found</strong>
        </p>
        <p class="h2">你想要的页面找不到了哦~</p>
        <p class="h2">去首页搜搜看吧，有很多超棒的资源呐！</p>
        <p>
          <br/>
          <button class="btn btn-primary btn-lg" href="/" role="button">返回首页</button>
        </p>
      </div>
    </div>
  </div>
  <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
  <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
  <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
  <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>

</html>