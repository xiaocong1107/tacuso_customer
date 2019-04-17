<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>我的钱包</title>
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="yes" name="apple-touch-fullscreen">
    <meta name="viewport" content="width=device-width,initial-scale=1.0, maximum-scale=1.0, minimum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <script src="/js/rem.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/wallet.css">
</head>
<body>
<section class="wallet-page">
	<!-- 我的可用余额  ${balance} -->
    
    <h3 class="title">我的代金券 (${availableNum}张可用）</h3>
    <section class="welfare-list">
        <#list vouchers as voucher>
    		<#if voucher.status ==0>
		        <!-- 如果代金券不可以 添加 disabled -->
		        <section class="welfare-item">
		            <div class="left-box">
		                <p class="money">${voucher.amount}</p>
		                <p class="desc">付款即抵扣，每次盒子限用一张，本年度会员期限内有效</p>
		            </div>
		            <div class="right-box">代<br/>金<br/>券</div>
		        </section>    	
		    <#elseif voucher.status == 1>
		        <section class="welfare-item disabled">
		            <div class="left-box">
		                <p class="money">${voucher.amount}</p>
		                <p class="desc">付款即抵扣，每次盒子限用一张，本年度会员期限内有效</p>
		            </div>
		            <div class="right-box">已<br/>使<br/>用</div>
		        </section>   		    
		    <#elseif voucher.status ==2>
		        <section class="welfare-item disabled">
		            <div class="left-box">
		                <p class="money">${voucher.amount}</p>
		                <p class="desc">付款即抵扣，每次盒子限用一张，本年度会员期限内有效</p>
		            </div>
		            <div class="right-box">已<br/>过<br/>期</div>
		        </section>   		        	
    		</#if>
    	</#list>
    </section>
</section>
<div class="toast"></div>
<script src="/js/zepto.js"></script>
</body>
</html>