<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>创建个人档案</title>
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="yes" name="apple-touch-fullscreen">
    <meta name="viewport" content="width=device-width,initial-scale=1.0, maximum-scale=1.0, minimum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <script src="/js/rem.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/profile.css">
</head>
<body>
<section class="profile-page" id="app">
    <section class="page-header">
        <div class="progress" @click="testf">即将完成</div>
    </section>
    <section class="page-desc">
	        请确保信息真实，让我们更懂你的需求
	    </section>
	    <div class="line"></div>
	    <section class="title-box">
	      <!-- <h3 class="title">购买衣服时，常选的码数是？</h3> -->
	    </section>
	      
    <div class="profile-form">
        <section class="input-item">
            <div class="icon icon-name"></div>
            <div class="label">姓名</div>
            <div class="base-input">
                <input type="text" class="name" placeholder="" />
            </div>
        </section>
        <section class="input-item">
            <div class="icon icon-work"></div>
            <div class="label">所在行业</div>
            <div class="base-input job">
                <span class="selected-job">选择行业</span>
                <select id="job_select" class="select">
                    <option value="选择行业">选择行业</option>
                    <option value="互联网/电子商务/通信/软件">互联网/电子商务/通信/软件</option>
                    <option value="金融/投资/银行/保险/财务/咨询">金融/投资/银行/保险/财务/咨询</option>
                    <option value="商贸/批发零售/消费/制造">商贸/批发零售/消费/制造</option>
                    <option value="医疗/卫生/制药">医疗/卫生/制药</option>
                    <option value="广告/媒体">广告/媒体</option>
                    <option value="地产/建筑/装修">地产/建筑/装修</option>
                    <option value="教育/培训/科研">教育/培训/科研</option>
                    <option value="住宿餐饮/服务业">住宿餐饮/服务业</option>
                    <option value="物流/仓储/运输/航空">物流/仓储/运输/航空</option>
                    <option value="电力/能源/原材料">电力/能源/原材料</option>
                    <option value="政府/事业单位/社会保障">政府/事业单位/社会保障</option>
                    <option value="文化/体育/娱乐业">文化/体育/娱乐业</option>
                    <option value="法律/商务服务">法律/商务服务</option>
                    <option value="农林牧渔/水利/环境/公共设施">农林牧渔/水利/环境/公共设施</option>
                    <option value="学生">学生</option>
                    <option value="其他">其他</option>
                </select>
            </div>
        </section>
        <section class="input-item">
            <div class="icon icon-job"></div>
            <div class="label">职位</div>
            <div class="base-input">
                <input type="text" class="work" placeholder="" />
            </div>
        </section>
        <section class="input-item">
            <div class="icon icon-birth"></div>
            <div class="label">出生日期</div>
            <div class="base-input birth" >
                <span class="birth-desc">选择日期</span>
                <input  type="date" id="birthday" class="birthday" />

            </div>
        </section>
        <section class="input-item">
            <div class="icon icon-address"></div>
            <div class="label">收件信息</div>
            <div class="base-input address-input">
                <div class="infos">
                    <!-- 无收件地址 隐藏 -->
                    <p class="user-info">
                        <span class="name-text"></span>
                        <span class="tel-text"></span>
                    </p>
                    <!-- 无收件地址时候，文案替换为 添加收件地址 -->
                    <p class="address">添加收件地址</p>
                </div>
            </div>
        </section>
        <!--<section class="like-about">
          <textarea class="like"></textarea>
        </section>-->
        <!--  <section class="blank-180"></section>  -->
    </div>
</section>
<section class="next-btn">下一步</section>
<section class="address-info__box">
    <section class="address-info__header">
        添加收件地址
        <div class="close-btn"></div>
    </section>
    <section class="input-info__list">
        <section class="input-item">
            <div class="label">姓名</div>
            <div class="base-input">
                <input type="text" class="addname" value="" placeholder="" />
            </div>
        </section>
        <section class="input-item">
            <div class="label">手机号码</div>
            <div class="base-input">
                <input type="tel" class="tel" value="" maxlength="11" placeholder="" />
            </div>
        </section>
        <section class="input-item">
            <div class="label">收件地址</div>
            <div class="base-input city" id="city">
                广东省
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
<div class="toast"></div>
<div class="mask-bg"></div>

<!-- 用户初始化及信息变更处理 -->
<script>
    var addressInfo = {
        name: '',
        tel: '',
        city: '广东/广州/天河',
        address: '',
    };
</script>
<script src="https://cdn.jsdelivr.net/npm/vue@2.5.17/dist/vue.js"></script>
<script src="/js/zepto.js"></script>
<script src="/js/picker.min.js"></script>
<script src="/js/city.js"></script>
<script src="/js/profile.js"></script>
</body>
</html>