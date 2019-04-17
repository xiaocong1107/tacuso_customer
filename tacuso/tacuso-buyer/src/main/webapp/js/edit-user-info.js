configWx()
function configWx(){

    $.post("/wx_js/config",{url:window.location.href},function(data){
        if (data.code == "200") {
            wx.config({
                debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                appId: data.data.appId, // 必填，公众号的唯一标识
                timestamp: data.data.timestamp, // 必填，生成签名的时间戳
                nonceStr: data.data.nonceStr, // 必填，生成签名的随机串
                signature: data.data.signature,// 必填，签名
                jsApiList: ['chooseWXPay','onMenuShareTimeline','onMenuShareAppMessage','onMenuShareQQ','onMenuShareQZone'] // 必填，需要使用的JS接口列表
            });
        }
    },"json")
}
var $select = $('select'),
    $maskBg= $('.mask-bg'),
    $toast = $('.toast'),
    $closeBtn = $('.close-btn'),
    $maskBox = $('.edit-mask'),
    $editBtn = $('.check-box'),
    $saveBtn = $('.edit-finish__btn'),
	$goCenter = $('.go-center');


/**
 * update new
 * 设置默认生日 $('.birth-desc').addClass('full').html('2011111');
 *
 */
$("#birthday").on("input",function () {
    if($(this).val().length > 0){
        $(this).addClass("full");
        // 新增
        $('.birth-desc').addClass("full").html($(this).val().replace(/\-/ig, '/'));
    } else {
        $(this).removeClass("full");
        // 新增
        $('.birth-desc').removeClass("full").html('选择日期');
    }
});



$select.on('change', function() {
  $(this).siblings('.select-value').html($(this).val());
});

$closeBtn.on('click', function () {
  $maskBox.removeClass('active');
  $maskBg.removeClass('active');
});

$goCenter.on('click', function () {
	wx.miniProgram.switchTab({
	    url:'../me/me'
	})
});

// 点击编辑展示弹窗
$editBtn.on('click', function () {
  $('input.name').attr('value', userInfo.name);
  $('input.work').attr('value', userInfo.work);

  $('input.birthday').attr('value', userInfo.birthday.replace(/\//ig, '-'));
  $('.birth-desc').html(userInfo.birthday.replace(/\-/ig, '/'));

  $('select.job').attr('value', userInfo.job);
  $('.select-value').html(userInfo.job);
  $maskBox.addClass('active');
  $maskBg.addClass('active');
});


// 编辑保存
$saveBtn.on('click', function () {
  var params = {
      nickname:  $('input.name').val(),
      birthday:  $('input#birthday').val(),
      industy:  $('.select-value').html(),
      position:  $('input.work').val(),
  };
  if (!params.nickname) {
    toast('姓名不能为空～');
    return;
  }

  if (!params.birthday) {
    toast('请选择您的出生日期～');
    return;
  }

  if (!params.industy) {
    toast('行业信息不能为空～');
    return;
  }

  if (!params.position) {
    toast('职业信息不能为空～');
    return;
  }

    params.birthday = params.birthday.replace(/\-/ig, '/');

  // 接口请求保存成功后操作
    $.post("/profile/saveProfile",Object.assign(params),function(data){
        console.log(data);
        if(data.code=="200"){

            toast('保存成功～');
            updateInfo (params);
            $maskBox.removeClass('active');
            $maskBg.removeClass('active');

        }
    },"json");


});

function updateInfo (data) {
  userInfo = {
      name: data.nickname,
      birthday: data.birthday,
      job: data.industy,
      work: data.position
  };
  $('p.name').html(data.nickname);

  $('span.birthday').html(data.birthday.replace(/\-/ig, '/'));
  $('input.birthday').attr('value', userInfo.birthday.replace(/\//ig, '-'));
  $('.birth-desc').addClass("full").html(data.birthday.replace(/\-/ig, '/'));

  $('p.job').html(data.industy);
  $('span.work').html(data.position);
}

function toast (message) {
  $toast.html(message).show();
  setTimeout(function () {
    $toast.hide();
  }, 2000);
}

document.body.addEventListener('touchstart', function () {});