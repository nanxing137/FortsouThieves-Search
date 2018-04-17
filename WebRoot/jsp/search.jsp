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
    .top-ma {
      margin-top: 80px;
    }

    .top-mi {
      margin-top: 20px;
    }

    .top-mii {
      margin-top: 10px;
    }

    .pad-mi {
      padding: 20px;
    }

    .bottom-pad-ma {
      padding-bottom: 40px;
    }

    .search-tip {
      /* width: auto; */
      position: absolute;

    }

    .onit {
      pointer-events: auto;
      cursor: pointer;
    }
  </style>

</head>

<body>

  <div class="container col-lg-6 col-md-6 col-sm-8 col-xs-12 col-lg-offset-3 col-md-offset-3 col-sm-offset-2">
    <div class="row top-ma">
      <div class="panel pad-mi">
        <!-- <div class=""> -->
        <img src="${pageContext.request.contextPath}/img/search.png" class="img-responsive center-block pad-mi bottom-pad-ma" />
        <!-- </div> -->
        <form class="form-group form-group-lg has-feedback top-mi" method="POST" action="/home/list.action">
          <input type="text" class="form-control" name="ftsResource.name" id="search" placeholder="资源搜索，例如：闪灵" data-provide="typeahead"
            autocomplete="off">
          <span class="onit glyphicon glyphicon-search form-control-feedback" aria-hidden="true" onclick="formSubmit();"></span>
        </form>
      </div>
    </div>
  </div>

  <!-- bootstrap -->
  <!-- <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script> -->
  <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/bootstrap3-typeahead.min.js"></script>
  <script type="text/javascript">
    var dataSource = ['闪灵', '神偷奶爸', '头号玩家', '爆裂无声', '闪电侠', '一个头两个大', '玩具总动员'];
    $(document).ready(function () {
      $.post({
        url: '/json/keywords.action',
        data: {},
        success: function (result) {
          dataSource = $.parseJSON(result);

          $('#search').typeahead({
            source: dataSource, // 数据源
            items: 5,            //最多显示个数
            minlength: 2
          });
        },
        dataType: 'json'
      });
    });
    function formSubmit() {
      $('.form-group').submit();
    }
  </script>
</body>

</html>