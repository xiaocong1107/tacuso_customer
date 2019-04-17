// 交互相关
var $getCodeBtn = $('.get-code__btn'),
    $saveBtn = $('.save-btn'),
    $toast = $('.toast');

// 表单值相关
var $tel = $('.tel'),
    $msgCode = $('.msgCode');

var lock = false,
    lockGetCode = false;

var timer = null;

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
    return {
      tel,
      msgCode
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
 * 点击保存
 */

$saveBtn.on('click', function () {
  if (lock === true) return;
  var params = $utils.getFormData();

  if (!params.tel || !$utils.checkTel(params.tel)) {
    $utils.toast('请填写正确11位手机号码～');
    lock = false;
    return;
  }

  if (!params.msgCode) {
    $utils.toast('短信验证码不能为空～');
    lock = false;
    return;
  }

  var postData = {
      bindphone:params.tel,
      code:params.msgCode
  };

    $.post('/user/changePhone', postData, function(result) {

        if(result.code=="200"){
//            window.location.href="/usercenter/editUserInfo";
        	window.history.back();
        }else{
            $utils.toast(result.message);
        }

    },"json");
});


 
 $getCodeBtn.on('click', function () {
   if (lockGetCode === true) return;
   if (!$tel.val() || !$utils.checkTel($tel.val())) {
    $utils.toast('请填写正确11位手机号码～');
    lockGetCode = false;
    return;
  }
   clearInterval(timer);
   lockGetCode = true;
   $utils.countTime();

     //获取验证码
     $.post('/register/getCode', {bindphone:$tel.val() }, function(result) {

         if(result.code!="200"){
             $utils.toast(result.message);
         }

     },"json");

 });

 document.body.addEventListener('touchstart', function () {});

