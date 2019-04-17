
// 交互相关

var $getCodeBtn = $('.get-code__btn'), // 获取短信验证码按钮
    $registerBtn = $('.register-btn'), // 注册按钮
    $ruleTip = $('.rule-tip'),
    $maskBg= $('.mask-bg'),
    $ruleMask = $('.rule-mask'), // 规则弹窗
    $noticeMask = $('.notice-mask'), // 提示弹窗
    $maskBoxbtn = $('.mask-box__btn'),
    $maskSuccess = $('.success-mask'),
    $toast = $('.toast'),
    $closeBtn = $('.close-btn'),
    $paySuccMask = $('.pay-success__box'),
    $payBox = $('.pay-box'),
    $payBtn = $('.pay-btn');
	$start = $('.start');


// 表单值相关
var $tel = $('.tel'),
    $msgCode = $('.msgCode');
    $province = $('.province');
    $inviteCode = $('.inviteCode');

var lock = lockGetCode = false;
var timer = null;

var $register = {
  /**
   * 处理背景遮罩
   * @param {*} show boolean
   */
  showMask (show) {
    show === true ? $maskBg.addClass('active') : $maskBg.removeClass('active');
  },

  /**
   * 处理注册条款
   * @param {*} show boolean
   */
  showRuleMask (show) {
    show === true ? $ruleMask.removeClass('fhide') : $ruleMask.addClass('fhide');
  },

  /**
   * 处理提示弹窗
   */
  showNoticeMask (show) {
    show === true ? $noticeMask.removeClass('fhide') : $noticeMask.addClass('fhide');
  },

  /**
   * 处理提示弹窗
   */
  showPaySuccessMask (show) {
    show === true ? $paySuccMask.removeClass('fhide') : $paySuccMask.addClass('fhide');
  },
  
  showRegisterSuccessMask (show) {
	  show === true ? $maskSuccess.removeClass('fhide') : $maskSuccess.addClass('fhide');
  },

  toast (content) {
    $toast.html(content).show(50);
    setTimeout(function() {
      $toast.hide(100);
    }, 3000);
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
  },

  checkTel (tel) {
    var isTel = true; 
    if(tel.length !== 11 || !(/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(tel))) {
      isTel = false;
    }
    return isTel;
  },

  checkInviteCode (inviteCode) {
	    var isInviteCode = true; 
	    if(inviteCode.length !== 6) {
	      isInviteCode = false;
	    }
	    return isInviteCode;
	  },
  
  /**
   * 获取表单数据
   */
  getFormData () {
    var bindphone = $tel.val();
    var code = $msgCode.val();
    var inviteCode = $inviteCode.val();
    var area = '广东省';
    return {
        bindphone,
        code,
        inviteCode,
        area
    };
  },
  
  /**
   * 处理数据请求
   */
  postData (data, callback) {
    $.post('xxxxx', data, function(result) {
      callback(result);
    });
  }
};


/**
 * 处理时间绑定操作相关
 */

 // 点击协议操作

 $ruleTip.on('click', function () {
   $register.showMask(true);
   $register.showRuleMask(true);
 });

 // close 处理

 $closeBtn.on('click', function () {
   $register.showRuleMask(false);
   $register.showNoticeMask(false);
   $register.showPaySuccessMask(false);
   $register.showMask(false);
   $register.showRegisterSuccessMask(false)
 });

 $maskBg.on('click', function () {
   $register.showRuleMask(false);
   $register.showNoticeMask(false);
   $register.showPaySuccessMask(false);
   $register.showMask(false);
   $register.showRegisterSuccessMask(false)
 });

 $maskBoxbtn.on('click', function () {
   $register.showRuleMask(false);
   $register.showNoticeMask(false);
   $register.showPaySuccessMask(false);
   $register.showMask(false);
   $register.showRegisterSuccessMask(false)
 });
 
 /**
  * 注册支付会费按钮
  */

  $payBtn.on('click', function() {
    // 处理支付
  });

  $getCodeBtn.on('click', function () {
      var params = {
          "bindphone":$tel.val(),
          "inviteCode":$inviteCode.val(),
          "isDev":0
      };


      if (!params.bindphone) {
          $register.toast('手机号码不能为空～');
          return;
      }

      if ($register.checkTel(params.bindphone) === false) {
          $register.toast('请填写正确的11位手机号码～');
          return;
      }
      

        if (lockGetCode === true) return;
        clearInterval(timer);
        lockGetCode = true;
        $register.countTime();

          if(isGuangdong=="false"){
              $register.showMask(true);
              $noticeMask.removeClass('fhide');
              return ;
          }

        //获取验证码
        $.post('/register/getCode', params, function(result) {

            if(result.code!="200"){
                $register.toast(result.message);
            }

        },"json");

  });


  if(isGuangdong=="false"){
      $register.showMask(true);
      $noticeMask.removeClass('fhide');
  }



  // 处理省份点击提示
  $province.on('click', function () {
    $register.showMask(true);
    $noticeMask.removeClass('fhide');
  });
  
  $start.on('click', function() {
      localStorage.clear();
      window.location.href="/page/index";
  });

 /**
  * 提交注册操作
  */

 $registerBtn.on('click', function() {
    if (lock === true) return;
    if(isGuangdong=="false"){
        $register.showMask(true);
        $noticeMask.removeClass('fhide');
        return ;
    }

    var params = $register.getFormData();
    if (!params.bindphone) {
      $register.toast('手机号码不能为空～');
      return;
    }
    if (!params.code) {
      $register.toast('短信验证码不能为空～');
      return;
    }
    if ($register.checkTel(params.bindphone) === false) {
      $register.toast('请填写正确的11位手机号码～');
      return;
    }

    $.post('/register/register', params, function(result) {

        if(result.code=="200"){
            //清除一次localStorage

            localStorage.clear();
            window.location.href="/page/index";
//        	$register.showMask(true);
//        	$register.showRegisterSuccessMask(true)
        }else{
            $register.toast(result.message);
        }

    },"json");
 });

 document.body.addEventListener('touchstart', function () {});