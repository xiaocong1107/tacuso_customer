<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>商品支付</title>
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="yes" name="apple-touch-fullscreen">
    <meta name="viewport" content="width=device-width,initial-scale=1.0, maximum-scale=1.0, minimum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <script src="/js/rem.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/pay.css">
</head>
<body>
<section class="pay-page">
    <section class="header-box">
        <p class="pay-desc">应付金额</p>
        <p class="total"><span>￥</span>${order.pay_amount}</p>
    </section>

    <section class="bill-box">
        <section class="pay-info bill-item">
        <#list detailList as detail>
            <p>
                <span><em>#${detail.serial_num}</em> <a class="product-buyer-name">${detail.sku_name}</a></span>
                <span>￥${detail.sku_price}</span>
            </p>
        </#list>
        </section>
        <section class="pay-info about-money">
            <p>
                <span>商品金额</span>
                <span>￥${order.total_amount}</span>
            </p>
            <p class="youhui">
                <span>会员折扣优惠<i class="about"></i></span>
                <span>-￥${order.discount_amount}</span>
            </p>
            <p>
                <span>服务费抵扣</span>
                <span>-￥${order.service_amount}</span>
            </p>
        </section>

        <section class="pay-btn">确认支付￥${order.pay_amount}</section>
    </section>

</section>

<section class="mask-box mask-discount">
    <h3 class="title"><span>会员尊享折扣优惠</span></h3>
    <p class="description">购买3件享受9.5折<br>
        全部购买享受9折</p>
    <div class="base-btn i-see">知道了</div>
</section>


<div class="toast"></div>
<div class="mask-bg"></div>

<script>
	var order_no = '${order.pay_order_no}';
</script>

<script src="/js/zepto.js"></script>
<script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.3.2.js"></script>
<script src="/js/billpay.js"></script>
</body>
</html>