// 交互相关
var $getCodeBtn = $('.get-code__btn'),
    $nextBtn = $('.next-btn'),
    $saveBtn = $('.save-btn'),
    $pwdItem = $('.pwd-item'),
    $msgItem = $('.msg-item'),
    $toast = $('.toast');

// 表单值相关
var $tel = $('.tel'),
    $pwd = $('.password'),
    $pwd2 = $('.password2'),
    $msgCode = $('.msgCode');

var lock = false,
    lockGetCode = false;

var timer = null;
var type = 1; // 1 表示短信验证  2: 确认修改密码

var $utils = {
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
    var password2 = $pwd2.val();
    return {
      tel,
      msgCode,
      password,
      password2,
    };
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
 * 点击下一步
 */

$nextBtn.on('click', function () {
  if (lock === true) return;
  var params = $utils.getFormData();
  
  if (!params.tel || !$utils.checkTel(Number(params.tel))) {
    $utils.toast('请填写正确11位手机号码～');
    lock = false;
    return;
  }

  if (!params.msgCode) {
    $utils.toast('短信验证码不能为空～');
    lock = false;
    return;
  }

  // $.post('xxxx', params, function(result) {
  // });
  $nextBtn.addClass('fhide');
  $pwdItem.removeClass('fhide');
  $msgItem.addClass('fhide');
  $saveBtn.removeClass('fhide');
});

$saveBtn.on('click', function() {
    if (lock === true) return;
    var params = $utils.getFormData;
    var data = $utils.getFormData();
    if (!data.password || !data.password2 || data.password !== data.password2) {
      $utils.toast('请确认新密码输入一致～');
      return;
    }
    // 发送保存请求
});
 
 $getCodeBtn.on('click', function () {
   if (lockGetCode === true) return;
   if (!$tel.val() || !$utils.checkTel(Number($tel.val()))) {
    $utils.toast('请填写正确11位手机号码～');
    lockGetCode = false;
    return;
  }
   clearInterval(timer);
   lockGetCode = true;
   $utils.countTime();
 });

 document.body.addEventListener('touchstart', function () {});