<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>上传全身照</title>
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="yes" name="apple-touch-fullscreen">
    <meta name="viewport" content="width=device-width,initial-scale=1.0, maximum-scale=1.0, minimum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <script src="/js/rem.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/upload.css">
</head>
<body>
<section class="upload-page">

    <!-- 注册流程显示 -->
    <!--<section class="page-header">
        <div class="progress">已经完成<em>5</em>/6</div>
    </section>-->

    <section class="upload-content">
        <section class="content-top">
            <p class="title">上传你的全身照，推荐更精准</p>
            <p class="sub-title">全身照片能让我们更了解你的身型特征</p>
        </section>
        <!-- on -->
        <section class="content-middle">
            <img class="upload-img" src="" alt="">
            <input type="hidden" value="" class="fullshotUrl">
        </section>
        <section class="content-bottom">
            <p class="title">选择你的近期全身照片，尽量不要上传局部照片</p>
            <div class="leading-box">
                <div class="ld-3"></div>
                <div class="ld-2"></div>
                <div class="ld-1"></div>
            </div>
        </section>
    </section>
    <section class="next-btn">保存</section>
</section>
<div class="toast"></div>
<script src="/js/zepto.js"></script>
<script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.3.2.js"></script>
<script src="/js/upload.js"></script>
</body>
</html>