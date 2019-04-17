<!DOCTYPE HTML>
<html>
    <head>
        <meta charset="UTF-8">
        <title>物流跟踪</title>
        <meta content="yes" name="apple-mobile-web-app-capable">
        <meta content="yes" name="apple-touch-fullscreen">
        <meta name="viewport" content="width=device-width,initial-scale=1.0, maximum-scale=1.0, minimum-scale=1, user-scalable=no">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <meta name="format-detection" content="telephone=no">
        <script src="../js/rem.js"></script>
        <link rel="stylesheet" type="text/css" href="../css/delivery.css">
    </head>
<body>
  <section class="delivery-page">
    <section class="page-header">
      <p>
        <span>发货时间：</span><span>2018-07-14 22:30:02</span>
      </p>
      <p>
        <span>物流公司：</span><span>2018-07-14 22:30:02</span>
      </p>
      <p>
        <span>联系电话：</span><span class="phone">2018-07-14 22:30:02</span>
      </p>
      <p class="last">
        <span>物流单号：</span><span>2018-07-14 22:30:02</span>
      </p>
      <!-- 物流单号替换 -->
      <div class="copy" data-clipboard-text="此处填写物流单号">复制</div>
    </section>
    <section class="blank-20"></section>
    <section class="delivery-info">
      <section class="info-header">
        <div>物流跟踪</div>
        <div><a href="">刷新物流轨迹</a></div>
      </section>
      <section class="info-list">
        <!-- class: 添加 active  -->
        <section class="item active">
          <p>广州市【广州番禺区八部】，已送达.有问题打派件电话.已签收有问题打派件电话</p>
          <p>2018-07-14 22:30:02</p>
        </section>
        <section class="item">
          <p>广州市【广州番禺区八部】，已送达.有问题打派件电话.已签收有问题打派件电话</p>
          <p>2018-07-14 22:30:02</p>
        </section>
      </section>
    </section>
  </section>
  <div class="toast"></div>
  <script src="../js/zepto.js"></script>
  <script src="../js/clipboard.min.js"></script>
  <script>
    var clipboard = new ClipboardJS('.copy');
    clipboard.on('success', function() {
      $('.toast').html('复制成功～').show();
      setTimeout(function() {
        $('.toast').hide();
      }, 2000);
    })
  </script>
</body>
</html>