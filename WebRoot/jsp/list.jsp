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
      padding-top: 110px;
    }

    .carousel-inner div {
      cursor: pointer;
    }

    .pad-mi {
      padding: 10px 0 10px 0;
    }

    .ma-top {
      top: 110px;
    }

    .item {
      height: 300px;
    }

    .onit {
      pointer-events: auto;
      cursor: pointer;
    }

    .pointer {
      cursor: pointer;
    }

    .capiton-over {
      overflow: hidden;
    }
  </style>

</head>

<body>

  <nav class="navbar navbar-default navbar-fixed-top" id="searchBar">
    <div class="container-fluid">
      <div class="row pad-mi">

        <div class="visible-lg-block visible-md-block col-lg-1 col-md-1 pointer" onclick="window.location.href='/fts/home/search.action'">
          <img src="${pageContext.request.contextPath}/img/search.png" class="img-responsive" alt="四十大盗搜索" />
        </div>
        <div class=" col-lg-7 col-md-7 col-sm-12 col-xs-12 pad-mi">
          <form method="POST" action="/fts/home/list.action" class="form-group form-group-lg has-feedback">
            <input type="text" class="form-control" name="ftsResource.name" id="search" placeholder="资源搜索，例如：闪灵" data-provide="typeahead"
              autocomplete="off">
            <span class="onit glyphicon glyphicon-search form-control-feedback" aria-hidden="true" onclick="formSubmit();"></span>
          </form>
        </div>

      </div>
    </div>
  </nav>

  <div class="container-fluid">

    <div class="row">

      <div class="col-lg-8 col-md-8 col-sm-12 col-xs-12">
        <div class="row grid">

          <!-- insert data here -->

        </div>
      </div>

    </div>


  </div>


  <div class="col-lg-4 col-md-4 col-md-offset-8 navbar-fixed-top visible-lg-block visible-md-block" style="top: 110px;">
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


  <!-- bootstrap -->
  <!-- <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script> -->
  <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/bootstrap3-typeahead.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/masonry.pkgd.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/imagesloaded.pkgd.min.js"></script>
  <script type="text/javascript">
    var searchName = "${ftsResource.name}";
    var pageNumber = 1;
    var elementsNow;

    String.prototype.times = function (n) {
      return Array.prototype.join.call({ length: n + 1 }, this);
    };

    $(document).ready(function () {
      $('#search').val(searchName);

      var dataSource = ['闪灵', '神偷奶爸', '头号玩家', '爆裂无声', '闪电侠', '一个头两个大', '玩具总动员'];
      $.post({
        url: '/fts/json/keywords.action',
        data: {},
        success: function (result) {
          dataSource = result;

          $('#search').typeahead({
            source: dataSource, // 数据源
            items: 5,            //最多显示个数
            minlength: 2
          });
        },
        dataType: 'json'
      });

      var $grid = $('.grid').masonry({
        columnWidth: '.grid-item',
        itemSelector: '.grid-item',
        gutter: 0
      });
      // layout Masonry after each image loads
      $grid.imagesLoaded().progress(function () {
        $grid.masonry('layout');
      });

      getValue();

      $(window).on('scroll', function () {
        if ($(document).scrollTop() >= $(document).height() - $(window).height()) {
          getValue();
        }
      });

    });

    function getValue() {
      $.post({
        url: '/fts/json/searchJson.action',
        data: {
          "ftsResource.name": searchName,
          "pageIndex": pageNumber
        },
        success: function (result) {
          var elements = $.parseJSON(result);
          getItems(elements);
          pageNumber++;
        },
        dataType: 'json'
      });
    }

    function getItems(ele) {
      var elements = ele;
      var allHTML = [];
      for (let i in elements) {
        allHTML[i] = '<div class="col-lg-3 col-md-3 col-sm-12 col-xs-12 grid-item pointer" onclick="window.location.href=' +
          "'/fts/home/details.action?ftsResource.id=" + elements[i].id + "'" +
          '"><div class="thumbnail"><img src="' +
          elements[i].imageUrl +
          '" class="visible-lg-block visible-md-block" alt="test"><div class="caption"><h4>' +
          elements[i].categoriesName + ' - ' + elements[i].name +
          '</h4><p>' +
          elements[i].description +
          '</p></div></div></div>';
      }
      loadData(allHTML);
    }

    function loadData(data) {
      $('.grid').append(data)
        // add and lay out newly appended items
        .masonry('appended', data);
    }

    function formSubmit() {
      $('.form-group').submit();
    }
  </script>
</body>

</html>