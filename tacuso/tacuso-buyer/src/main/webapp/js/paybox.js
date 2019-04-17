document.body.addEventListener('touchstart', function () {});

configWx();

function toPay(){
    pay();
}

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


function pay() {
    $.ajax({
        url: "/wx_pay/paybox",
        type:"post",
        dataType:"json",
        success: function (data) {
            console.log(data);

            if (data.code == "200") {

                wx.ready(function () {
                    wx.chooseWXPay({
                        timestamp: data.data.timeStamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
                        nonceStr: data.data.nonceStr, // 支付签名随机串，不长于 32 位
                        package: data.data.packageValue, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=\*\*\*）
                        signType: data.data.signType, // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
                        paySign: data.data.paySign, // 支付签名
                        success: function () {

                            // 支付成功后的回调函数
                            $.post('/register/member_pay_success', {}, function(result) {
                                if(result.code=="200"){
                                    window.location.href="/page/couponIndex";//支付成功跳转到指定页面
                                }else{
                                    alert(result.data.message);
                                }

                            },"json");

                        },
                        cancel: function (res) {
                            // 取消支付后的回调函数
                        }

                    });

                    wx.onMenuShareAppMessage({
                        title: '1', // 分享标题
                        desc: '1', // 分享描述
                        link: 'http://www.tacuso.com/register/index', // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
                        imgUrl: '1', // 分享图标
                        dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
                        success: function () {
                        }
                    });


                });


            }

        }
    });

};

$(".pay-btn").on("click",function(e){
    toPay();

});


var $ruleTip = $('.rule-tip'),
    $closeBtn = $('.close-btn'),
    $maskBg= $('.mask-bg'),
    $ruleMask = $('.rule-mask');
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

    toast (content) {
        $toast.html(content).show(50);
        setTimeout(function() {
            $toast.hide(100);
        }, 3000);
    },


};
$ruleTip.on('click', function () {
    $register.showMask(true);
    $register.showRuleMask(true);
});


$closeBtn.on('click', function () {
    $register.showRuleMask(false);
    $register.showMask(false);
});






