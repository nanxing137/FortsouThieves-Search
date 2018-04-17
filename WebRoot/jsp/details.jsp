<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <title>Search</title>

  <!-- Bootstrap -->
  <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" />

  <!--[if lt IE 9]>
          <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
          <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
      <![endif]-->

  <style>
    body {
      padding-top: 100px;
    }

    .pad-mi {
      padding: 10px 0 10px 0;
    }

    .pd-mi {
      padding: 20px;
    }

    .item {
      height: 300px;
    }

    .onit {
        pointer-events: auto;
        cursor: pointer;
    }
  </style>
</head>

<body>

  <nav class="navbar navbar-default navbar-fixed-top" id="searchBar">
    <div class="container-fluid">
      <div class="row pad-mi">

        <div class="visible-lg-block visible-md-block col-lg-1 col-md-1">
          <img src="${pageContext.request.contextPath}/img/search.png" class="img-responsive" />
        </div>
        <div class=" col-lg-7 col-md-7 col-sm-12 col-xs-12 pad-mi">
            <form method="POST" action="/fts/home/list.action" class="form-group form-group-lg has-feedback">
                <input type="text" class="form-control" name="ftsResource.name" id="search" placeholder="资源搜索，例如：闪灵" data-provide="typeahead" autocomplete="off">
                <span class="onit glyphicon glyphicon-search form-control-feedback" aria-hidden="true" onclick="formSubmit();"></span>
            </form>
        </div>

      </div>
    </div>
  </nav>

  <div class="container-fluid">
    <div class="row">
      <div class="panel panel-default col-lg-8 col-md-8 col-sm-12 col-xs-12 pd-mi mg-mi">

        <div class="panel panel-primary">
          <div class="panel-heading">
            <h1>${ftsResource.ftsCategories.name}-${ftsResource.name}}
              <span class="label label-success"><span class="glyphicon glyphicon-download"></span>  ${ftsResource.downloads}</span>
              <span class="label label-info"><span class="glyphicon glyphicon-eye-open"></span>  ${ftsResource.pageViews}</span>
            </h1>
            <button class="btn-warning btn-lg">
              <span class="glyphicon glyphicon-star"></span>
              <span>收藏</span>
            </button>
          </div>
          <div class="panel-content">
            <img src="${ftsResource.imageUrl}" alt="" class="img-responsive center-block">
          </div>
        </div>

        <div class="panel panel-primary">
          <div class="panel-heading pd-mi">
            <h2>影片详情</h2>
          </div>
          <div class="panel-content">
            <p class="h3 mg-mi">${ftsResource.description}</p>
          </div>
        </div>

        <div class="panel panel-primary">
          <div class="panel-heading">
            <h2>资源下载</h2>
          </div>
          <div class="panel-content pd-mi">
            <p class="h3 mg-mi">${ftsResource.resourceUrl}</p>
          </div>
        </div>

      </div>
    </div>
  </div>


  <div class="col-lg-4 col-md-4 col-md-offset-8 navbar-fixed-top visible-lg-block visible-md-block" style="top: 110px;padding-left: 40px;">
    <div class="page-header">
      <h1>
                  热门资源
                  <small> TOP 3 </small>
              </h1>
    </div>
    <div id="myCarousel" class="carousel slide">
      <!-- 轮播（Carousel）指标 -->
      <ol class="carousel-indicators">
        <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
        <li data-target="#myCarousel" data-slide-to="1"></li>
        <li data-target="#myCarousel" data-slide-to="2"></li>
      </ol>
      <!-- 轮播（Carousel）项目 -->
      <div class="carousel-inner">
        <div class="item active" onclick="window.location.href='http://www.baidu.com'">
          <img src="${pageContext.request.contextPath}/img/search.png" alt="First slide">
          <div class="carousel-caption">标题 1</div>
        </div>
        <div class="item" onclick="window.location.href='http://www.baidu.com'">
          <img src="${pageContext.request.contextPath}/img/404.png" alt="Second slide">
          <div class="carousel-caption">标题 2</div>
        </div>
        <div class="item" onclick="window.location.href='http://www.baidu.com'">
          <img src="${pageContext.request.contextPath}/img/404.png" alt="Third slide">
          <div class="carousel-caption">标题 3</div>
        </div>
      </div>
      <!-- 轮播（Carousel）导航 -->
      <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                  <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                  <span class="sr-only">Previous</span>
              </a>
      <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                  <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                  <span class="sr-only">Next</span>
              </a>
    </div>
  </div>

  <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/bootstrap3-typeahead.min.js"></script>
  <script type="text/javascript">
    $(document).ready(function() {
      var dataSource = ['闪灵', '神偷奶爸', '头号玩家', '爆裂无声', '闪电侠', '一个头两个大', '玩具总动员'];
      $.post({
        url: '/fts/json/keywords.action',
        data: {},
        success: function(result) {
          dataSource = result;
          $('#search').typeahead({
            source: dataSource, // 数据源
            items: 5, //最多显示个数
            minlength: 2
          });
        },
        dataType: 'json'
      });
    });
  </script>
</body>

</html>