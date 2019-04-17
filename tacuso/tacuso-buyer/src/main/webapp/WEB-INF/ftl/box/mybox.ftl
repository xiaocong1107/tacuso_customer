<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>我的有衣盒子</title>
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="yes" name="apple-touch-fullscreen">
    <meta name="viewport" content="width=device-width,initial-scale=1.0, maximum-scale=1.0, minimum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <script src="/js/rem.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/mybox.css">
</head>
<body>
<section class="page mybox-page">
    <section class="box-header">
        <h3 class="title">盒子订单已经开启</h3>
        <div class="transfer">私の有衣箱を管理する</div>
        <div class="box-icon"></div>
        <p class="about">订单编号 / ${order_no}</p>
    </section>
    <section class="blank-20"></section>
    <section class="box-status">
         <!-- status-item 准备状态：prepare 当前状态添加 ready, 已经完成状态添加： finish -->
	      <section class="status-item on">
	        <div class="left-box box-icon">
	        </div>
	        <div class="right-box">
	          <p class="about">
	   			请确认本次盒子订单需求，随后将为你准备盒子<!--预计送达时间为：${time} -->
	          </p>
	          <div class="base-status">
	            <!-- <div class="base-btn edit-bill">修改订单信息</div> -->
	            <!-- v9.1 -->
	            
		            <div class="btn-list"  id="div1">
		              <a href="/box/confirmBox"><div class="btn need-box">我要一个盒子</div></a>
		                   <!--  <div class="btn cancel-box">本次不需要</div>-->
		            </div>
	            <div class="finish-box" id="div2">盒子准备中...</div>
	            <div class="finish-box" id="div3">已完成</div>
	          </div>
	        </div>
	      </section>

        <section class="status-item on">
            <div class="left-box delivery-icon">
            </div>
            <div class="right-box">
                <p class="about">
                    已精选4件适合你的衣服，装进有衣盒子寄奔向你，请注意签收哦
                </p>
                <div class="base-status">
                    <!-- 标签设置拨打电话 -->
                    <div class="base-btn"><a href="https://ucmp.sf-express.com/cx/query/waybill.html?reserved=enter__${express_no}">快递单号：${express_no}</a></div>
                    <div class="finish-box">已完成</div>
                </div>
            </div>
        </section>

        <section class="status-item on">
            <div class="left-box arrive-icon">
            </div>
            <div class="right-box">
                <p class="about">
                 		   有衣盒子已送达，你可以在家试穿体验，留下喜欢的并付款
                </p>
                <div class="base-status">
                    <div class="base-btn pingjia">评价本次盒子并付款</div>
                    <div class="finish-box">已完成</div>
                </div>
            </div>
        </section>

        <section class="status-item on">
            <div class="left-box count-icon">
            </div>
            <div class="right-box">
                <p class="about">
                		   不满意的衣服可在3天内免费寄回，预约寄回后，快递员将上门取件
                </p>
                <div class="base-status">
                    <div class="finish-box">已预约取件</div>
                    <div class="base-btn"><a href="/express/yuyue?orderNo=${order_no}">${words}</a></div>
                </div>
            </div>
        </section>
        
    </section>

    <section class="blank-20"></section>
    

    <section class="about-address">
        <h3 class="title">
            请确认本次收件人信息
		<#if order_status lt 3 > <div class="edit-icon"></div> </#if>
        </h3>
        <section class="address-info">
		<#if address?if_exists >
            <!-- 没有收件地址的隐藏 -->
            <div class="user-info">
                <span class="name">${address.name}</span>
                <span class="tel">${address.phone}</span>
            </div>

            <!-- 没有收件地址的文案改为： 请点击编辑添加新地址 -->
            <div class="address">${address.city + address.detail}</div>
        <#else>
        	<div class="address">请点击编辑添加新地址</div>
        </#if>
        </section>
    </section>

    <section class="blank-20"></section>

    <a href="/usercenter/index" class="go-center">返回个人中心</a>

    <section class="blank-20"></section>
    <section class="blank-20"></section>
    <section class="blank-20"></section>
    <section class="blank-20"></section>
    <section class="blank-20"></section>
    <section class="blank-20"></section>
    <section class="blank-20"></section>
    <section class="blank-20"></section>
</section>

<!-- 收件地址列表弹窗 -->
<section class="address-list__box">
    <section class="address-list__header">
        选择收件地址
        <div class="save-btn">保存</div>
    </section>
    <section class="address-list">

    </section>

    <section class="add-address">添加收件地址</section>
</section>

<!-- 编辑添加收件地址 -->
<section class="address-info__box">
    <section class="address-info__header">
        <span class="address-info__title">添加收件地址</span>
        <div class="close-btn"></div>
    </section>
    <section class="input-info__list">
        <section class="input-item">
            <div class="label">姓名</div>
            <div class="base-input">
                <input type="text" class="name" placeholder="" />
            </div>
        </section>
        <section class="input-item">
            <div class="label">手机号码</div>
            <div class="base-input">
                <input type="tel" class="tel" maxlength="11" placeholder="" />
            </div>
        </section>
        <section class="input-item address-picker">
            <div class="label">收件地址</div>
            <div class="base-input city" id="city" >
            </div>
        </section>
        <section class="input-item">
            <div class="label">详细地址</div>
            <div class="base-input">
                <input type="text" class="address" placeholder="" />
            </div>
        </section>
    </section>
    <section class="address-finish__btn">
        完成
    </section>
</section>

  <!-- 提示弹窗 -->
  <section class="mask-box">
    <h3 class="title">确认取消本次盒子吗？</h3>
    <p class="description">将顺延至下个月寄送～</p>
    <div class="btn-list">
      <div class="btn cancel-btn">取消</div>
      <div class="btn ok-btn hl">确认</div>
    </div>
  </section>


<div class="toast"></div>
<div class="mask-bg"></div>

<script src="/js/zepto.js"></script>

<script>
	<#if  order_null==1 >
		document.getElementById("div2").style.display="none";
		document.getElementById("div3").style.display="none";
		 $('.status-item').eq(0).removeClass('on').addClass('ready');
	<#elseif order_status == 0 >
			document.getElementById("div1").style.display="none";
			document.getElementById("div3").style.display="none";
		 $('.status-item').eq(0).removeClass('on').addClass('finish');
	<#elseif order_status == 2||order_status == 1>
			document.getElementById("div1").style.display="none";
			document.getElementById("div3").style.display="none";
		 $('.status-item').eq(0).removeClass('on').addClass('finish');
	<#elseif order_status == 21>
			document.getElementById("div1").style.display="none";
			document.getElementById("div2").style.display="none";
		 $('.status-item').eq(0).removeClass('on').addClass('finish');	 
	<#elseif order_status == 3>
		 	document.getElementById("div1").style.display="none";
		 	document.getElementById("div2").style.display="none";
		 $('.status-item').eq(0).removeClass('on').addClass('finish');
		 $('.status-item').eq(1).removeClass('on').addClass('ready');	 
	<#elseif order_status == 4>
			 	document.getElementById("div1").style.display="none";
		 		document.getElementById("div2").style.display="none";
		 $('.status-item').eq(0).removeClass('on').addClass('finish');
		 $('.status-item').eq(1).removeClass('on').addClass('finish');
		 $('.status-item').eq(2).removeClass('on').addClass('ready');
	<#elseif order_status == 5>
			 	document.getElementById("div1").style.display="none";
		 		document.getElementById("div2").style.display="none";
		 $('.status-item').eq(0).removeClass('on').addClass('finish');
		 $('.status-item').eq(1).removeClass('on').addClass('finish');
		 $('.status-item').eq(2).removeClass('on').addClass('finish');
		 $('.status-item').eq(3).removeClass('on').addClass('ready');
	<#elseif order_status == 6>
			document.getElementById("div1").style.display="none";
		 	document.getElementById("div2").style.display="none";
		 $('.status-item').eq(0).removeClass('on').addClass('finish');
		 $('.status-item').eq(1).removeClass('on').addClass('finish');
		 $('.status-item').eq(2).removeClass('on').addClass('finish');
		 $('.status-item').eq(3).removeClass('on').addClass('finish');
		 $('.status-item').eq(3).removeClass('on').addClass('ready');
	<#else>
			document.getElementById("div1").style.display="none";
		 	document.getElementById("div2").style.display="none";
		 $('.status-item').eq(0).removeClass('on').addClass('finish');
		 $('.status-item').eq(1).removeClass('on').addClass('finish');
		 $('.status-item').eq(2).removeClass('on').addClass('finish');
		 $('.status-item').eq(3).removeClass('on').addClass('finish');
	</#if>
	
    var addressList = [];
    var order_no = '${order_no}';
    var add_id = '${address.address_id}';
</script>

<script src="/js/template-web.js"></script>

<script id="addressPage" type="text/html">

	<section class="address-list__header">
		选择收件地址
		<div class="save-btn">保存</div>
	</section>
		
    {{@addressListStr }}

	<section class="add-address">添加收件地址</section>

</script>

<script id="addressList" type="text/html">
	<section class="address-list" style="height:200px;overflow-y:auto">	
		{{each addressList $address $index}}
			<!-- 循环用户收件地址 -->
		<section class="address-item {{if $address.address_id == $address.add_id }}selected{{/if}}" address-id="{{$address.address_id}}">
				<div class="picker-icon"></div>
				<div class="address-info">
					<div class="user-info">
						<span class="name">{{$address.name}}</span>
						<span class="tel">{{$address.phone}}</span>
					</div>
					<div class="address">{{$address.city}}{{$address.detail}}</div>
				</div>
				<div class="arrow-icon" data-index="{{$index}}"></div>
		</section>
		{{/each}}
		
	</section>
</script>

<script src="/js/picker.min.js"></script>
<script src="/js/city.js"></script>
<script src="/js/mybox.js"></script>
</body>
</html>