<!DOCTYPE HTML>
<html>
    <head>
        <meta charset="UTF-8">
        <title>预约取件</title>
        <meta content="yes" name="apple-mobile-web-app-capable">
        <meta content="yes" name="apple-touch-fullscreen">
        <meta name="viewport" content="width=device-width,initial-scale=1.0, maximum-scale=1.0, minimum-scale=1, user-scalable=no">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <meta name="format-detection" content="telephone=no">
        <script src="/js/rem.js"></script>
        <link rel="stylesheet" type="text/css" href="/css/yuyue.css">
    </head>
<body>
  <section class="yuyue-page">
    <h3 class="title">免邮费，预约上门取件</h3>
    <div class="yuyue-header__box">
      <!-- active 设置点击效果 -->
    </div>
    <div class="yuyue-form">
      <section class="input-item day">
        <div class="base-input time">
          <span class="selected-time"></span>
          <select id="date" class="day-select">
          </select>
        </div>
      </section>
      <section class="input-item timeline">
        <div class="base-input time">
          <span class="selected-time"></span>
          <select id="time" class="timeline-select">
          </select>
        </div>
      </section>
    </div>
    <section class="about-address">
      <h3 class="title">
       	 请确认本次取件地址
        <div class="edit-icon"></div>
      </h3>
      <section class="address-info">

        <div class="user-info">
          <span class="name"  id="name" >${address.name}</span>
          <span class="tel"  id="tel" >${address.phone}</span>
        </div>
        <div class="address" id="address">${address.city + address.detail}</div>
      </section>
    </section> 
	  <section class="yuyue-btn">预约取件</section>
  </section>

  <section class="address-info__box">
    <section class="address-info__header">
      修改收件地址
      <div class="close-btn"></div>
    </section>
    <section class="input-info__list">
      <section class="input-item">
        <div class="label">姓名</div>
        <div class="base-input">
           <input type="text" class="name" value="${address.name}" placeholder="" />
        </div>
      </section>
      <section class="input-item">
        <div class="label">手机号码</div>
        <div class="base-input">
          <input type="tel" class="tel" value="${address.phone}" maxlength="11" placeholder="" />
        </div>
      </section>
      <section class="input-item">
        <div class="label">收件地址</div>
        <div class="base-input city" id="city">
      		    ${address.city}
        </div>
      </section>
      <section class="input-item">
        <div class="label">详细地址</div>
        <div class="base-input">
          <input type="text" class="address" placeholder="" value="${address.detail}"/>
        </div>
      </section>
    </section>
    <section class="address-finish__btn">
      		保存
    </section>
  </section>

  <!-- 提示弹窗 -->
  <section class="mask-box">
    <h3 class="title">确认预约时间和地点无误？</h3>
    <p class="description">确定后不能修改哦～</p>
    <div class="btn-list">
      <div class="btn cancel-btn">我再想想</div>
      <div class="btn ok-btn hl">确认</div>
    </div>
  </section>

  <div class="toast"></div>
  <div class="mask-bg"></div>
  
   <script>
    var orderNo = '${orderNo}';
  </script>
  <script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.3.2.js"></script>
  <script src="/js/zepto.js"></script>
  <script src="/js/picker.min.js"></script>
  <script src="/js/city.js"></script>
  <script src="/js/yuyue.js"></script>
</body>
</html>