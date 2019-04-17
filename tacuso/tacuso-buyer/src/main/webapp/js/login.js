// 交互相关
var $getCodeBtn = $('.get-code__btn'),
    $loginType = $('.login-type'),
    $msgItem = $('.msg-item'),
    $pwdItem = $('.pwd-item'),
    $registerBtn = $('.register-btn'),
    $toast = $('.toast'),
    $loginBtn = $('.login-btn');

// 表单值相关
var $tel = $('.tel'),
    $msgCode = $('.msgCode');
    $pwd = $('.password');

var lock = false,
    lockGetCode = false;

// 登录类型限制 0: 短信验证码 1: 密码登录

var type = 0;
var timer = null;

var $login = {
  toast (content) {
    $toast.html(content).show(50);
    setTimeout(function() {
      $toast.hide(100);
    }, 3000);
  },

  checkTel (tel) {
    var isTel = true; 
    if(tel.length !== 11 || !(/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(tel))) {
      isTel = false;
    }
    return isTel;
  },

  /**
   * 获取表单数据
   */
  getFormData () {
    var tel = $tel.val();
    var msgCode = $msgCode.val();
    var password = $pwd.val();
    if (type === 0) {
      return {
        tel,
        msgCode,
        type
      };
    } else {
      return {
        tel,
        password,
        type
      };
    }
  },

  countTime () {
    var count = 60;
    timer = setInterval(function () {
      count--;
      $getCodeBtn.html(count + 's');
      if (count <= 0) {
        $getCodeBtn.html('重新发送');
        lockGetCode = false;
        clearInterval(timer);
      }
    }, 1000);
  }
};

/**
 * 点击登录类型交互
 */
$loginType.on('click', function () {
  $loginType.removeClass('active');
  $(this).addClass('active');
  if ($(this).hasClass('use-phone')) {
    type = 0;
    $pwdItem.addClass('fhide');
    $msgItem.removeClass('fhide');
  } else {
    $msgItem.addClass('fhide');
    $pwdItem.removeClass('fhide');
    type = 1;
  }
});

/**
 * 点击登录
 */

$loginBtn.on('click', function () {
  if (lock === true) return;
  var params = $login.getFormData();
  if (!params.tel) {
    $login.toast('请输入手机号码~');
    lock = false;
    return;
  }
  if (!$login.checkTel(params.tel)) {
    $login.toast('请填写正确11位手机号码～');
    lock = false;
    return;
  }

  if (type === 0 || !params.msgCode) {
    $login.toast('请输入短信验证码~');
    lock = false;
    return;
  }
  if (type === 1 || !params.password) {
    $login.toast('密码不能为空~');
    lock = false;
    return;
  }
  $.post('/login/login', params, function(result) {
    // 登录结果处理
  });
});

/**
 * 点击去注册
 */

 $registerBtn.on('click', function () {
   window.location.href = '/register/index';
 });

 
 $getCodeBtn.on('click', function () {
	 if (lock === true) return;
	 var params = $login.getFormData();
	 
	 if (!params.tel) {
		 $login.toast('手机号码不能为空～');
		 lock = false;
		 return;
	 }
	 if (!$login.checkTel(params.tel)) {
		 $login.toast('请填写正确11位手机号码～');
		 lock = false;
		 return;
	 }
	      
	 if (lockGetCode === true) return;
	 clearInterval(timer);
	 lockGetCode = true;
	 $login.countTime();
   
	   //获取验证码
     $.post('/login/getCode', params, function(result) {

         if(result.code!="200"){
        	 $login.toast(result.message);
         }

     },"json");
 });

 document.body.addEventListener('touchstart', function () {});
