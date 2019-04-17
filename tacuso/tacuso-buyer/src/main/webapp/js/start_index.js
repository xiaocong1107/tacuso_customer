document.body.addEventListener('touchstart', function () {});

var $toast = $('.toast');

configWx();

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
checkRegister();

function checkRegister(){

	$.post('/register/register', function(result) {

        if(result.code=="200"){
        	if(result.data.is_question_finish == 1) {
        		$.ajax({
        	        url: "/user/isMember",
        	        type:"post",
        	        dataType:"json",
        	        success: function (res) {
        	            if (res.code == "200") {
        	            	wx.miniProgram.postMessage({ data: {type: 'tab',uno:res.data.uno}})
        	            	wx.miniProgram.switchTab({
        	            	    url:'../order/order'
        	            	})
        	            }
        	        }
        		})
        	}else {
        		$register.toast('个人档案尚未完善！');
        		window.location.href="/page/index";                        		
        	}
        }else{
//        	$register.toast(result.message);
//            window.location.href="/register/index";     
        	wx.miniProgram.reLaunch({
        	    url:'../index/index'
        	})
        }

    },"json");
}

var $register = {
	 toast (content) {
		    $toast.html(content).show(50);
		    setTimeout(function() {
		      $toast.hide(100);
		    }, 3000);
		  }	,
}