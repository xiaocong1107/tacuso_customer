<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>个人资料</title>
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="yes" name="apple-touch-fullscreen">
    <meta name="viewport" content="width=device-width,initial-scale=1.0, maximum-scale=1.0, minimum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <script src="/js/rem.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/editUserInfo.css">
    
    <style>
	    body{
	        min-height: 100%;
	    	overflow-x: hidden;
	    }
	    
    	.go-center {
    		display: block;
    		background: #399F76;
    		color: #FFF;
			font-weight:bold;
			position: fixed;
		    bottom: 0;
		    z-index: 99;
		    left: 0;
		    right: 0;
    	}
    </style>
</head>
<body>
<section class="editUserInfo-page">
    <section class="user-top__box">
        <a  class="check-box">
            <div class="user-info">
                <p class="name">${userinfo.nickname}</p>
                <p class="others"><span class="birthday">${userinfo.birthday}</span><span class="work">${userinfo.position}</span></p>
                <p class="job">${userinfo.industy}</p>
            </div>
            <!-- <div class="left-box">
              <p class="name">金木</p>
              <p class="birthday">1989/04/12</p>
            </div>
            <div class="right-box">
              <p class="job">互联网 / 金融</p>
              <p class="work">攻城湿</p>
            </div> -->
            <div class="icon-arrow"></div>
        </a>
    </section>
    <section class="nav-list">
        <a class="nav-item" href="/usercenter/style">
            <div class="icon icon-shirt"></div>
            <div class="nav-name">着装偏好</div>
            <div class="icon-arrow"></div>
        </a>
        <a class="nav-item" href="/usercenter/size">
            <div class="icon icon-height"></div>
            <div class="nav-name">身材尺码</div>
            <div class="icon-arrow"></div>
        </a>
        <a class="nav-item" href="/usercenter/habits">
            <div class="icon icon-habbit"></div>
            <div class="nav-name">消费习惯</div>
            <div class="icon-arrow"></div>
        </a>
        <a class="nav-item" href="/usercenter/upload">
            <div class="icon icon-photo"></div>
            <div class="nav-name">上传全身照</div>
            <div class="icon-arrow"></div>
        </a>
    </section>

    <section class="blank-20"></section>

    <section class="nav-list">
        <a class="nav-item" href="/usercenter/address">
            <div class="icon icon-address"></div>
            <div class="nav-name">管理收件地址</div>
            <div class="icon-arrow"></div>
        </a>
        <a class="nav-item wallet" href="/usercenter/changePhone">
            <div class="icon icon-phone"></div>
            <div class="nav-name">修改手机号码</div>
            <div class="message">${bindphone}</div>
            <div class="icon-arrow"></div>
        </a>
        <!--
        不需要密码
        <a class="nav-item" href="./change-pwd.html">
            <div class="icon icon-key"></div>
            <div class="nav-name">修改密码</div>
            <div class="icon-arrow"></div>
        </a>
        -->
    </section>

    <section class="blank-20"></section>

    <section class="blank-20"></section>
    <section class="blank-20"></section>
</section>

<section class="edit-mask">
    <section class="edit-info__header">
        编辑个人信息
        <div class="close-btn"></div>
    </section>
    <section class="input-info__list">
        <section class="input-item">
            <div class="label">姓名</div>
            <div class="base-input">
                <input type="text" class="name" value="" placeholder="" />
            </div>
        </section>
        <section class="input-item">
            <div class="label">生日</div>
            <div class="base-input">
                <span class="birth-desc full">选择日期</span>
                <input type="date"  id="birthday" class="birthday" />
            </div>
        </section>
        <section class="input-item">
            <div class="label">所在行业</div>
            <div class="base-select">
                <span class="select-value">选择行业</span>
                <select name="" id="" class="job">
                    <option value="互联网/电子商务/通信/软件">互联网/电子商务/通信/软件</option>
                    <option value="金融/投资/银行/保险/财务/咨询">金融/投资/银行/保险/财务/咨询</option>
                    <option value="商贸/批发零售/消费/制造">商贸/批发零售/消费/制造</option>
                    <option value="医疗/卫生/制药">医疗/卫生/制药</option>
                    <option value="广告/媒体">广告/媒体</option>
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
            <div class="label">职业</div>
            <div class="base-input">
                <input type="text" class="work" placeholder="" />
            </div>
        </section>
    </section>
    <section class="edit-finish__btn">
        保存
    </section>
</section>



<a class="go-center">保存个人资料</a>
<div class="mask-bg"></div>
<div class="toast"></div>

<script>
    var userInfo = {
        name: '${userinfo.nickname}',
        birthday: '${userinfo.birthday}'.replace(/\//ig, '-'),
        job: '${userinfo.industy}',
        work: '${userinfo.position}'
    };
</script>
  <script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.3.2.js"></script>
<script src="/js/zepto.js"></script>
<script src="/js/edit-user-info.js"></script>
</body>
</html>