<!DOCTYPE HTML>
<html>
    <head>
        <meta charset="UTF-8">
        <title>确认订单信息</title>
        <meta content="yes" name="apple-mobile-web-app-capable">
        <meta content="yes" name="apple-touch-fullscreen">
        <meta name="viewport" content="width=device-width,initial-scale=1.0, maximum-scale=1.0, minimum-scale=1, user-scalable=no">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <meta name="format-detection" content="telephone=no">
        <script src="../js/rem.js"></script>
        <link rel="stylesheet" type="text/css" href="../css/confirm-box.css">
    </head>
    <style rel="stylesheet" type="text/css">
    	.mask-box {
			position: absolute;
			margin: auto;
			top:3.27rem;
			left:0.95rem;
			padding: .5rem;
			background: #fff;
			text-align: center;
			z-index: 123;
			-webkit-border-radius: .04rem;
			border-radius: .04rem;
			width:5.6rem;
			height:3rem;
			display: none;
			  flex-direction: column;
			  align-items: center;
    justify-content: space-around;
		}
		.mask-box>div {
			display: flex;
			  flex-direction: row;
			  align-items: center;
		}
		.confrim-btn {
			width:2.3rem;
			height:0.6rem;
			background:rgba(255,255,255,1);
			border-radius:0.04rem;
			border:0.02rem solid rgba(57,159,118,1);
			font-size:0.28rem;
			font-family:PingFangSC-Regular;
			font-weight:400;
			color:rgba(57,159,118,1);
			line-height:0.6rem;
		}
		.confrim{
			margin-left : 0.3rem
		}
    </style>
<body>
  <section class="confirm-page">
    <section class="confirm-container"> 
      <section class="confirm-title">
        <p class="main-title">本次有特别想要的品类吗? (选填)</p>
        <p class="sub-title">将会参考你的选择给你最佳的搭配推荐</p>
      </section>
      <section class="picker-box">
        <div class="sub-title"><span>上衣</span></div>
        <div class="picker-list">
                <#list clothesList as list>
                	<#assign json=list.answer_config?eval />
                  	     <div class="pick-item"  value="${json.title}" >${json.title}</div>
                </#list>
      
        </div>
      </section>
      <section class="picker-box">
        <div class="sub-title"><span>长裤</span></div>
        <div class="picker-list">
              <#list trousersList as list>
                	<#assign json=list.answer_config?eval />
                  	     <div class="pick-item"  value="${json.title}">${json.title}</div>
                </#list>
        </div>
      </section>
      <section class="other-info" id="box1">
        <section class="bottom-info">
          <h3 class="title">有其他具体的需求吗？(选填)</h3> 
          <textarea placeholder="欢迎留言(限制字数150字)" id="buyer_msg" class="buyer_msg"></textarea>
        </section>
        <section class="top-info">	
          <h3 class="title">最近体重有变化吗？</h3> 
          <p class="tip">更新体重后我们能推荐更合适的尺码</p>
          <section class="input-item">
            <span class="select-value">没有变化</span>
            <select id="weight" class="select">
              <option value="没有变化">没有变化</option>
              <option value="50kg">50kg</option>
              <option value="51kg">52kg</option>
              <option value="53kg">53kg</option>
              <option value="54kg">54kg</option>
              <option value="55kg">55kg</option>
              <option value="56kg">56kg</option>
              <option value="57kg">57kg</option>
              <option value="58kg">58kg</option>
              <option value="59kg">59kg</option>
              <option value="60kg">60kg</option>
              <option value="61kg">61kg</option>
              <option value="62kg">62kg</option>
              <option value="63kg">63kg</option>
              <option value="64kg">64kg</option>
              <option value="65kg">65kg</option>
              <option value="66kg">66kg</option>
              <option value="67kg">67kg</option>
              <option value="68kg">68kg</option>
              <option value="69kg">69kg</option>
              <option value="70kg">70kg</option>
              <option value="71kg">71kg</option>
              <option value="72kg">72kg</option>
              <option value="73kg">73kg</option>
              <option value="74kg">74kg</option>
              <option value="75kg">75kg</option>
              <option value="76kg">76kg</option>
              <option value="77kg">77kg</option>
              <option value="78kg">78kg</option>
              <option value="79kg">79kg</option>
              <option value="80kg">80kg</option>
              <option value="81kg">81kg</option>
              <option value="82kg">82kg</option>
              <option value="83kg">83kg</option>
              <option value="84kg">84kg</option>
              <option value="85kg">85kg</option>
              <option value="86kg">86kg</option>
              <option value="87kg">87kg</option>
              <option value="88kg">88kg</option>
              <option value="89kg">89kg</option>
              <option value="90kg">90kg</option>
              <option value="91kg">91kg</option>
              <option value="92kg">92kg</option>
              <option value="93kg">93kg</option>
              <option value="94kg">94kg</option>
              <option value="95kg">95kg</option>
              <option value="96kg">96kg</option>
              <option value="97kg">97kg</option>
              <option value="98kg">98kg</option>
              <option value="99kg">99kg</option>
              <option value="100kg">100kg</option>
              <option value="101kg">101kg</option>
              <option value="102kg">102kg</option>
              <option value="103kg">103kg</option>
              <option value="104kg">104kg</option>
              <option value="105kg">105kg</option>
              <option value="106kg">106kg</option>
              <option value="107kg">107kg</option>
              <option value="108kg">108kg</option>
              <option value="109kg">109kg</option>
              <option value="110kg">110kg</option>
              <option value="111kg">111kg</option>
              <option value="112kg">112kg</option>
              <option value="113kg">112kg</option>
              <option value="114kg">114kg</option>
              <option value="115kg">115kg</option>
              <option value="116kg">116kg</option>
              <option value="117kg">117kg</option>
              <option value="118kg">118kg</option>
              <option value="119kg">119kg</option>
              <option value="120kg">120kg</option>
              <option value="121kg">121kg</option>
              <option value="122kg">122kg</option>
              <option value="123kg">123kg</option>
              <option value="124kg">124kg</option>
              <option value="125kg">125kg</option>
              <option value="126kg">126kg</option>
              <option value="127kg">127kg</option>
              <option value="128kg">128kg</option>
              <option value="129kg">129kg</option>
              <option value="130kg">130kg</option>
            </select>
          </section>
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
		        <section class="confirm-btn">给我寄盒子吧</section>
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
  
  <section class="mask-box">
    <h3 class="mask-title">确定提交订单？</h3>
    <div>
    	<span class="confrim-btn cancle">取消</span>
    	<span class="confrim-btn confrim">确定</span>
    </div>
    
  </section>

<div class="toast"></div>
<div class="mask-bg"></div>



<script>
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
  <script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.3.2.js"></script>
  <script src="../js/zepto.js"></script>
  <script src="../js/picker.min.js"></script>
  <script src="../js/city.js"></script>
  <script src="../js/confirm-box.js"></script>
</body>
</html>