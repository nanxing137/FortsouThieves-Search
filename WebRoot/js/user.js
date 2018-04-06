/*
 * @Author: milkIQ 
 * @Date: 2018-04-04 00:12:20 
 * @Last Modified by: milkIQ
 * @Last Modified time: 2018-04-04 00:12:48
 */

function loginHidden() {
  $('#title').fadeOut('fast', function () {
    $('#title').html('注册&nbsp;<small>海量资源等你来</small>');
    $('#title').fadeIn('fast');
  });
  $('#login').fadeOut('fast', function () {
    $('#register').fadeIn('fast');
  });
}

function registerHidden() {
  $('#title').fadeOut('fast', function () {
    $('#title').html('登录&nbsp;<small>收藏、上传、下载</small>');
    $('#title').fadeIn('fast');
  });
  $('#register').fadeOut('fast', function () {
    $('#login').fadeIn(500);
  });
}

function eye() {
  var icon = $('.glyphicon-eye-open').length > $('.glyphicon-eye-close').length ? $('.glyphicon-eye-open') : $('.glyphicon-eye-close');
  if (icon.hasClass('glyphicon-eye-open')) {
    for (var i = 0; i < icon.length; i++) {
      $(icon[i]).addClass('glyphicon-eye-close');
      $(icon[i]).removeClass('glyphicon-eye-open');
    }
    $('#inputRPassword').attr('type', 'password');
    $('#inputResPassword').attr('type', 'password');
    $('#inputPassword').attr('type', 'password');
  } else if (icon.hasClass('glyphicon-eye-close')) {
    for (var i = 0; i < icon.length; i++) {
      $(icon[i]).addClass('glyphicon-eye-open');
      $(icon[i]).removeClass('glyphicon-eye-close');
    }
    $('#inputRPassword').attr('type', 'text');
    $('#inputResPassword').attr('type', 'text');
    $('#inputPassword').attr('type', 'text');
  }
  console.log('change');
}

function success(data) {
  // data = $.parseJSON(data);
  if (data.status == 0) {
    $('#inputRUsername').attr('data-content', '用户名可用')
    $('#inputRUsername').popover('show');
    setTimeout(function () {
      $('#inputRUsername').popover('hide');
    }, 1000);
    return true;
  } else if (data.status == 1) {
    $('#inputRUsername').attr('data-content', '用户名重复，不能使用');
    $('#inputRUsername').popover('show');
    return false;
  }
}

function findUsername(url, data, success) {
  var status;
  function pipe() {
    status = success();
  }
  $.post({
    url: url,
    data: data,
    success: pipe,
    dataType: 'json'
  });
  return status;
}

function findLittle(str) {
  if (str.search('[a-zA-Z]') < 0)
    return str.length;
  else
    return str.search('[a-zA-Z]');
}

function usernameRegexp() {
  var username = $('#inputRUsername');
  var usernameRule = '[^a-zA-z0-9._\\-]';

  var nameValue = username.val();
  nameValue = nameValue.substring(findLittle(nameValue));
  username.val(nameValue.replace(new RegExp(usernameRule, 'g'), ''));
  if (username.val().length < 6 || username.val() > 11) {
    username.attr('data-content', '用户名位数：6-11');
    username.popover('show');
  } else {
    username.popover('hide');
    return true;
  }
}

function emailRegexp() {
  var email = $('#inputREmail');
  var emailRule = '^[a-z]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$';

  if (!new RegExp(emailRule, 'g').test(email.val())) {
    email.attr('data-content', '邮箱格式不正确');
    email.popover('show');
    return false;
  }
}

function passwordRegexp() {
  var password = $('#inputRPassword');

  if (password.val().length < 6) {
    password.attr('data-content', '密码不要小于6位');
    password.popover('show');
    return false;
  }
  return true;
}

function passwordResRegexp() {
  var password = $('#inputRPassword');
  var passwordRes = $('#inputResPassword');

  if (password.val() != passwordRes.val()) {
    passwordRes.attr('data-content', '两次密码输入不一致');
    passwordRes.popover('show');
    return false;
  }
  return true;
}

function loginCheck() {
  var name = $('#inputUsername');
  var password = $('#inputPassword');
  var form = $('loginForm');
  if (name.val().length == 0) {
    name.attr('data-content', '用户名未填写');
    name.popover('show');
  } else if (password.val().length == 0) {
    password.attr('data-content', '密码未填写');
    password.popover('show');
  } else {
    form.submit();
  }
}

function registerCheck() {
  window.registerStatus.username = usernameRegexp();
  window.registerStatus.email = emailRegexp();
  window.registerStatus.password = passwordRegexp();
  window.registerStatus.passwordRes = passwordResRegexp();
  var username = $('#inputRUsername').val();
  var status = findUsername(
    'http://localhost:3000', {
      name: username
    },
    success
  );
  for (var i in window.registerStatus) {
    if (!window.registerStatus[i]) {
      status = false;
    }
  }
  if (!status) {
    alert('输入有误，请检查后提交');
  } else {
    $('registerForm').submit();
  }
}

$(function () {
  window.registerStatus = {
    username: false,
    email: false,
    password: false,
    passwordRes: false
  };

  window.registerRule = {
    inputRUsername: usernameRegexp,
    inputREmail: emailRegexp,
    inputRPassword: passwordRegexp,
    inputResPassword: passwordResRegexp
  };

  $('[data-toggle="tooltip"]').tooltip();
  $('[data-toggle="popover"]').popover();

  $('#inputRUsername').on('input', usernameRegexp);

  $('#inputRUsername').blur(function () {
    var username = $('#inputRUsername').val();
    findUsername(
      'http://localhost:3000', {
        name: username
      },
      success
    );
  });
  $('.rgForm').blur(function () {
    var name = $(this).attr('id');
    window.registerRule[name]();
  });
  $('input[data-toggle="popover"]').click(function () {
    $(this).popover('hide');
    console.log('hide');
  });
  $('#gotoRegister').click(function () {
    loginHidden();
    $('input[data-toggle="popover"]').popover('hide');
    $('input[data-toggle="popover"]').val('');
  });
  $('#gotoLogin').click(function () {
    registerHidden();
    $('input[data-toggle="popover"]').popover('hide');
    $('input[data-toggle="popover"]').val('');
  });
});