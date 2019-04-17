<!DOCTYPE HTML>
<html>
    <head>
        <meta charset="UTF-8">
        <title>评价</title>
        <meta content="yes" name="apple-mobile-web-app-capable">
        <meta content="yes" name="apple-touch-fullscreen">
        <meta name="viewport" content="width=device-width,initial-scale=1.0, maximum-scale=1.0, minimum-scale=1, user-scalable=no">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <meta name="format-detection" content="telephone=no">
        <script src="/js/rem.js"></script>
        <link rel="stylesheet" type="text/css" href="/css/rate.css">
    </head>
<body>
  <section class="rate-page">
    <section class="box-header">
      <h3 class="title">评价本次服务</h3>
    </section>
    <section class="rate-box">
      <section class="rate-item">
        <p class="rate-title">你对本次服务满意度如何? (选填)</p>
        <p class="rate-desc">（物流、客服、系统操作等）</p>
        <div class="start-list service">
          <div class="star" data-score='1'></div>
          <div class="star" data-score='2'></div>
          <div class="star" data-score='3'></div>
          <div class="star" data-score='4'></div>
          <div class="star" data-score='5'></div>
        </div>
      </section>

      <section class="rate-item">
        <p class="rate-title">你对本次搭配满意度如何? (选填)</p>
        <p class="rate-desc">（衣服、款式、搭配建议等）</p>
        <div class="start-list style">
          <div class="star" data-score='1'></div>
          <div class="star" data-score='2'></div>
          <div class="star" data-score='3'></div>
          <div class="star" data-score='4'></div>
          <div class="star" data-score='5'></div>
        </div>
      </section>

      <section class="rate-item">
        <p class="rate-title">请留言你的反馈意见 (选填)</p>
        <p class="rate-desc">我们将为你做得更好</p>
        <div class="area-box">
          <textarea placeholder="请留言你的反馈意见 (选填)" id="content"></textarea>
        </div>
      </section>
      <section class="finish-btn">完成</section>
    </section>
  </section>
<script>
	var orderNo = '${orderNo}';
</script>
  <script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.3.2.js"></script>
  <div class="toast"></div>
  <script src="/js/zepto.js"></script>
  <script src="/js/rate.js"></script>
</body>
</html>