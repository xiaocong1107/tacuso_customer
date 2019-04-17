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

function getUrlParam(name) {
	   var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	   var r = window.location.search.substr(1).match(reg);
	   if (r != null) return unescape(r[2]); return null;
	}
var type = getUrlParam('type')
var $star = $('.star'),
    $service = $('.service'),
    $style = $('.style'),
    $finish = $('.finish-btn'),
    $toast = $('.toast');


$service.on('click', '.star', function() {
  var score = $(this).attr('data-score');
  $service.find('.star').removeClass('on');
  for (var i = 0; i < score; i++) {
    $service.find('.star').eq(i).addClass('on');
  }
});

$style.on('click', '.star', function() {
  var score = $(this).attr('data-score');
  $style.find('.star').removeClass('on');
  for (var i = 0; i < score; i++) {
    $style.find('.star').eq(i).addClass('on');
  }
});

// 点击完成处理
$finish.on('click', function () {
	var service = $(".start-list.service").children(".star.on").length;
	var style = $(".start-list.style").children(".star.on").length;
	var content = $("#content").val();
	var data = {
			service:service,
			style:style,
			content:content,
			orderNo:orderNo
	}
	$.post('/express/evaluate', data, function(result) {
        if(result.code=="200"){
        	$utils.toast('评价成功');
//        	  window.location.href="/express/evaluate?service="+service+"&style="+style+"&content="+content+"&orderNo="+orderNo;
        	if (type == 1) {
        		wx.miniProgram.switchTab({
            	    url:'../order/order'
            	})
        	} else if (type == 2) {
//        		wx.miniProgram.redirectTo({
//            	    url:'pages/order-detail/order-detail?orderNo='+data.orderNo
//            	})
        		wx.miniProgram.navigateBack({
        			  delta: 1
        			})
        	} 
        	
        }else{
            alert(result.data.message);
        }

    },"json");
});

var $utils = {
	toast (content) {
	    $toast.html(content).show(50);
	    setTimeout(function() {
	      $toast.hide(100);
	    }, 3000);
	  }
}

geEvaluate()

function geEvaluate () {
	$.post('/express/getEvaluate', {orderNo:orderNo}, function(result) {
        if(result.code=="200"){
        	$("#content").val(result.data.commentInfo.content);
        	
        	for (var i = 0; i < result.data.commentInfo.style; i++) {
        	    $style.find('.star').eq(i).addClass('on');
        	 }
        	for (var i = 0; i < result.data.commentInfo.service; i++) {
        		$service.find('.star').eq(i).addClass('on');
        	 }
        }

    },"json");
}