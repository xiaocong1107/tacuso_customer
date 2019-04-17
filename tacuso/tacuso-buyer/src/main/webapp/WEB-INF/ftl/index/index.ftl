<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>有衣宅送</title>
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="yes" name="apple-touch-fullscreen">
    <meta name="viewport" content="width=device-width,initial-scale=1.0, maximum-scale=1.0, minimum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <script src="/js/rem.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/home.css">
</head>
<body>
<section class="home-page">
    <section class="page page-1"></section>
    <section class="page page-2"></section>
    <section class="page page-3"></section>
    <section class="page page-4"></section>
    <section class="page page-5"></section>
    <section class="page page-6"></section>
    <section class="page page-7"></section>
    <section class="wechat-qrcode">关注官方公众号></section>
    <section class="blank-170"></section>
  <!--  <a class="btn experience_btn" >立 即 体 验</a>-->
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

<div class="mask-bg"></div>
<script src="/js/zepto.js"></script>
<script>
        var $experience_btn = $('.experience_btn');
        $experience_btn.on('click', function(e) {
            console.log(e);
            window.location.href='/experience';
        });


        var $maskBg = $('.mask-bg'),
                $qrMask = $('.qrcode-mask'),
                $closeBtn = $('.close-btn'),
                $gzh = $('.wechat-qrcode');

        $gzh.on('click', function () {
            $maskBg.addClass('active');
            $qrMask.removeClass('fhide');
        });

        $closeBtn.on('click', function () {
            $qrMask.addClass('fhide');
            $maskBg.removeClass('active');
        });
        window.onload=function(){
            setTimeout(function () {
                $('.page').addClass('on');
            }, 300);
        }
        document.body.addEventListener('touchstart', function () {});

</script>
</body>
</html>