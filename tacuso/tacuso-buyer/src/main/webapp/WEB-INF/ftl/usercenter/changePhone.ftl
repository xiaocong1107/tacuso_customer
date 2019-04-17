<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>修改手机号码</title>
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="yes" name="apple-touch-fullscreen">
    <meta name="viewport" content="width=device-width,initial-scale=1.0, maximum-scale=1.0, minimum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <script src="/js/rem.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/change-phone.css">
</head>
<body>
<div class="phone-page">
    <div class="phone-form">
        <section class="input-item">
            <div class="label">手机号码</div>
            <div class="base-input">
                <input type="text" class="tel" maxlength="11" placeholder="" />
            </div>
        </section>
        <section class="input-item msg-item">
            <div class="label">短信验证</div>
            <div class="base-input">
                <input type="text" class="msgCode" placeholder="" maxlength="6"/>
            </div>
            <div class="get-code__btn">获取验证码</div>
        </section>
        <section class="save-btn">保存</section>
    </div>
</div>
<div class="toast"></div>
<script src="/js/zepto.js"></script>
<script src="/js/change-phone.js"></script>
</body>
</html>