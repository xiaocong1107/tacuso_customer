<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>我的历史订单</title>
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="yes" name="apple-touch-fullscreen">
    <meta name="viewport" content="width=device-width,initial-scale=1.0, maximum-scale=1.0, minimum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <script src="/js/rem.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/history.css">
</head>
<body>

<#if orderList?? && (orderList?size > 0)>

<section class="history-page">
<#list orderList as order>
    <section class="history-item">
        <section class="item-header">
            <p class="bill-num">订单编号：${order.order_no}</p>
            <p class="time">创建时间：${order.pay_time}</p>
        </section>
        
        <section class="pay-info bill-item">
        <#list order.detailList as sku>
            <p>
                <span>${sku.sku_name}</span>
                <span>￥${sku.sku_price}</span>
            </p>
        </#list>
        </section>

        <section class="pay-info about-money">
            <p>
                <span>商品金额</span>
                <span>￥${order.total_amount}</span>
            </p>
            <p class="youhui">
                <span>会员折扣优惠</span>
                <span>-￥${order.discount_amount}</span>
            </p>
            <p>
                <span>代金券</span>
                <span>-￥${order.voucher_amount}</span>
            </p>
        </section>

        <section class="total">
            <span>合计</span>
            <span>￥${order.pay_amount}</span>
        </section>

    </section>
</#list>
</section>

<#else>

	<!--  历史记录不为空显示  empty-page -->
	<section class="empty-page">
	    <div class="empty-icon"></div>
	    <p>还 没 有 历 史 订 单</p>
	</section>

</#if>


<div class="toast"></div>
<div class="mask-bg"></div>
<script src="/js/zepto.js"></script>
</body>
</html>