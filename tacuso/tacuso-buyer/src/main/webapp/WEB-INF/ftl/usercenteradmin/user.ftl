<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>个人中心</title>
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="yes" name="apple-touch-fullscreen">
    <meta name="viewport" content="width=device-width,initial-scale=1.0, maximum-scale=1.0, minimum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <script src="/js/rem.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/user.css">
</head>
<body>
<section class="user-page">
    <section class="user-top__box">
        <!-- 如果暂无订单盒子 需要跳转到 wait-box.html -->
        <a href="/box/mybox" class="check-box">
        </a>
    </section>
    <section class="nav-list">
        <a class="nav-item" href="/usercenter/editUserInfo">
            <div class="icon icon-profile"></div>
            <div class="nav-name">修改个人资料</div>
            <div class="icon-arrow"></div>
        </a>
        <a class="nav-item wallet" href="/usercenter/wallet">
            <div class="icon icon-wallet"></div>
            <div class="nav-name">我的钱包</div>
            <div class="message">${wallet_msg}</div>
            <div class="icon-arrow"></div>
        </a>
        <a class="nav-item" href="/order/history">
            <div class="icon icon-history"></div>
            <div class="nav-name">历史订单查询</div>
            <div class="icon-arrow"></div>
        </a>
        <a class="nav-item" href="/qa/index">
            <div class="icon icon-help"></div>
            <div class="nav-name">帮助中心</div>
            <div class="icon-arrow"></div>
        </a>
        <div class="nav-item gzh">
            <div class="icon icon-gzh"></div>
            <div class="nav-name">关注公众号</div>
            <div class="icon-arrow"></div>
        </div>
    </section>
</section>

<!-- 公众号弹窗 -->
<section class="qrcode-mask fhide">
    <div class="mask-box">
        <div class="mask-box__header">
            <div class="close-btn"></div>
        </div>
        <div class="mask-box__content">
            <p>欢迎关注有衣宅送公众号！</p>
            <p>更方便查询服务细则、了解订单进度，完善个人信息</p>
            <div class="gzh-qrcode">
                <img src="/images/tacuso_QRcode.png" alt="">
            </div>
            <div class="about">
                <p>1. 在微信中搜索有衣宅送，即可关注我们</p>
                <p>2. 长按以上二维码并识别，即可关注我们</p>
            </div>
        </div>
    </div>
</section>

<!-- 首次注册进入用户中心弹窗 -->
<#if isFirst>
<section class="register-mask">
    <div class="mask-box">
        <div class="mask-box__header">
            <!-- <div class="close-btn"></div> -->
        </div>
        <div class="mask-box__content">
            <p>预计盒子送达时间</p>
            <p class="time">${time}</p>
            <p>看看我们第一次推荐是否可以符合你的期望，期待一下～</p>
            <div class="mask-box__btn">知道了</div>
        </div>
    </div>
</section>
<div class="mask-bg active"></div>
<#else>
<div class="mask-bg"></div>
</#if>
<script src="/js/zepto.js"></script>
<script src="/js/user.js"></script>
</body>
</html>