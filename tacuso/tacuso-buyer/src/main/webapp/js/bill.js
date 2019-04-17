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
var $billItem = $('.bill-item'), 
	$picker = $('.picker-icon'), 
	$btn = $('.btn'), 
	$arrow = $('.arrow-icon'), 
	$reasonBtn = $('.reason-item'), 
	$discount = $('.youhui'), 
	$pickerAll = $('.picker-all'), 
	$money = $('.money'), 
	$subBtn = $('.submit-btn'), 
	$cancelBtn = $('.cancel-btn'), 
	$iSeeBtn = $('.i-see'), 
	$okBtn = $('.ok-btn'), 
	$maskBg = $('.mask-bg'), 
	$mEv = $('.mask-evaluation'), // 评价弹窗
	$mNothing = $('.mask-nothing'), 
	$mConfirm = $('.mask-confirm'), 
	$mDiscount = $('.mask-discount');

// 更新选中数量提示
function updatePickAll() {
	var num = $('.picker-icon.on').length;
	$pickerAll.addClass('selected').html('已选' + num);

	if (num === 0) {
		$pickerAll.removeClass('selected').html('全选');
	}
}

function setAmount() {
//	3件 9.5折，全部9折
	total_amount = 0;
	$('.buy-btn.on').each(function() {
		total_amount = total_amount + Number($(this).parent().parent().children(".money").children("span").text());
	});
	var buy_num = $('.buy-btn.on').size();
	var sku_num = $('.buy-btn').size();
	var discount = 1;
	if (buy_num == 4) {
		discount = 0.9;
	} 
	else if (buy_num == 3) {
		discount = 0.95;
	}else{
		discount = 1
	}
	discount_amount = (total_amount * (1-discount)).toFixed(2);
	var pay_amount = total_amount - discount_amount - voucher_amount;
	if (pay_amount < 0) {
		pay_amount = 0;
	}
	$("#pay_amount").html(pay_amount);
	$("#discount_amount").html(discount_amount);
	$("#total_amount").html(total_amount);
}

// 商品评价点击购买，或者不要按钮
$btn.on('click', function() {

	$(this).siblings('.btn').removeClass('on');
	$parent = $(this).parents('.bill-item');
	$infoContent = $(this).parents('.info-content');
	if (!$(this).hasClass('on')) {
		$(this).addClass('on');
		$parent.addClass('selected');
		
		// 处理点击购买按钮
		if ($(this).hasClass('buy-btn')) {
			$infoContent.siblings('.picker-icon').addClass('on');
		} else {
			$infoContent.siblings('.picker-icon').removeClass('on');
		}
		setAmount();
	}
	updatePickAll();
});

$arrow.on('click', function() {
	$parent = $(this).parents('.bill-item');
	if ($parent.hasClass('selected')) {
		$parent.removeClass('selected');
	} else {
		$parent.addClass('selected');
	}
});

// 点击评价选项
$reasonBtn.on('click', function() {
	$(this).siblings().removeClass('on');
	if ($(this).hasClass('on')) {
		$(this).removeClass('on');
	} else {
		$(this).addClass('on');
	}
});

// 选中所有
$pickerAll.on('click', function() {
	// 判断是否取消所有
	if ($(this).hasClass('selected')
			&& $('.picker-icon.on').length === $billItem.length) {
		$(this).removeClass('selected');
		$picker.removeClass('on');
		$billItem.removeClass('selected');
		$('.buy-btn').removeClass('on');
	} else {
		$(this).addClass('selected');
		$picker.addClass('on');
		$billItem.addClass('selected');
		$('.buy-btn').addClass('on');
		$('.cancel-btn').removeClass('on');
	}
	setAmount();
	updatePickAll();
});

// 点击优惠查看提示
$discount.on('click', function() {
	$maskBg.addClass('active');
	$mDiscount.show();
});

$subBtn.on('click', function() {
	
	// 背景
	$maskBg.addClass('active');
	// 商品未评介逻辑
	if($('.reason-item.on').size() != $('.evaluation-item').size()) {
		$mEv.show();
		return;
	} 

	// 全部商品不要
	if($('.buy-btn.on').size() == 0) {
		$mNothing.show();
		return;
	}
	
	// 订单提交确认
	$mConfirm.show();
});

$cancelBtn.on('click', function() {
	// 处理订单弹窗提交取消按钮
	if ($(this).hasClass('base-btn')) {
		$('.mask-box').hide();
		$maskBg.removeClass('active');
	}
});

$okBtn.on('click', function() {
	// 处理订单提交

	var skuList = [];
	$('.bill-item').each(function() {
		$itemInfo = $(this).children('.item-info');
		var serial_num = $itemInfo.children('.num').children("span").text();
		var is_buy = 2;
		if ($itemInfo.children('.info-content').children('.btn-list').children('.buy-btn').hasClass('on')) {
			is_buy = 1;
		}
		
		var comment = [];
		$evaInfo = $(this).children('.evaluation-box');
		$evaInfo.children('.evaluation-item').each(function() {
			var comment_item = {};
			comment_item.about = $(this).children('.about').html();
			var reason = $(this).children('.reason-list').children('.reason-item.on').html();
			comment_item.reason = (reason == null) ? '':reason;
			comment.push(comment_item);
		});
		var   $textArea = $('#content'+serial_num);
		console.log($textArea.val());
		var content = $textArea.val();

		var sku = {	
				serial_num : serial_num,	
				is_buy : is_buy,
				comment : JSON.stringify(comment),
				content :content,
		};
		skuList.push(sku);
	});
	
	var data = {
		skus : JSON.stringify(skuList),
		order_no : order_no,
	};
	
    $.post('/box/pay',data,function(retdata){
    	if (retdata.code == "200") {    
    		if (retdata.data.buy_num == 0) {
    			
    			$.post('/box/pay_success', {order_no:retdata.data.pay_order_no}, function(result) {
                    if(result.code=="200"){
                        window.location.href="/express/yuyue?orderNo="+retdata.data.pay_order_no;
                    }

                },"json");
    			
    		} else {
    			window.location.href = "/order/pay?pay_order_no=" + retdata.data.pay_order_no;
    		}
    	}
    },"json");

});

$iSeeBtn.on('click', function() {
	$('.mask-box').hide();
	$maskBg.removeClass('active');
});

document.body.addEventListener('touchstart', function() {
});

$(function() { 
	setAmount();
});
