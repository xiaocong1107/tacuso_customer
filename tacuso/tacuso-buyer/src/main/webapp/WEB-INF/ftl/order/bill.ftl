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
    <link rel="stylesheet" type="text/css" href="/css/bill.css">
</head>
<body>
<section class="bill-page">
    <section class="box-header">
        <h3 class="title">选购你喜欢的商品并评价</h3>
        <div class="transfer">好きな商品と評価を買う</div>
        <div class="box-icon"></div>
        <p class="about">商 品 选 购</p>
    </section>

    <section class="bill-list">
        <!-- 选中添加selected -->
        
        <#list skuList as sku>
        <section class="bill-item">
            <section class="item-info">
                <!-- 点击选中时添加 on -->
                <div class="picker-icon <#if sku.is_buy == 1>on</#if>"></div>
                <div class="num">#<span>${sku.serial_num}</span></div>
                <div class="info-content">
                    <div class="name">${sku.sku_name}</div>
                    <div class="money">￥<span>${sku.sku_price}</span></div>
                    <div class="btn-list">
                        <!-- 点击选中时添加 on -->
                        <div class="btn buy-btn <#if sku.is_buy == 1>on</#if>">购买</div>
                        <div class="btn cancel-btn <#if sku.is_buy == 2>on</#if>">不要</div>
                    </div>
                </div>
                <div class="arrow-icon"></div>
            </section>
            <section class="evaluation-box">
                <p class="title">感谢您对这件商品作出评价</p>
				<#assign json=sku.comment?eval />
                <section class="evaluation-item">
                    <div class="about">款式</div>
                    <!-- 点击选中时添加 on -->
                    <div class="reason-list">
                        <div class="reason-item <#if json.款式 == '喜欢'>on</#if>">喜欢</div>
                        <div class="reason-item <#if json.款式 == '一般'>on</#if>">一般</div>
                        <div class="reason-item <#if json.款式 == '不喜欢'>on</#if>">不喜欢</div>
                    </div>
                </section>

                <section class="evaluation-item">
                    <div class="about">尺码</div>
                    <div class="reason-list">
                        <div class="reason-item <#if json.尺码 == '合适'>on</#if>">合适</div>
                        <div class="reason-item <#if json.尺码 == '偏大'>on</#if>">偏大</div>
                        <div class="reason-item <#if json.尺码 == '偏小'>on</#if>">偏小</div>
                    </div>
                </section>

                <section class="evaluation-item">
                    <div class="about">颜色</div>
                    <div class="reason-list">
                        <div class="reason-item <#if json.颜色 == '喜欢'>on</#if>">喜欢</div>
                        <div class="reason-item <#if json.颜色 == '一般'>on</#if>">一般</div>
                        <div class="reason-item <#if json.颜色 == '不喜欢'>on</#if>">不喜欢</div>
                    </div>
                </section>

                <section class="evaluation-item">
                    <div class="about">面料</div>
                    <div class="reason-list">
                        <div class="reason-item <#if json.面料 == '喜欢'>on</#if>">喜欢</div>
                        <div class="reason-item <#if json.面料 == '一般'>on</#if>">一般</div>
                        <div class="reason-item <#if json.面料 == '不喜欢'>on</#if>">不喜欢</div>
                    </div>
                </section>

                <section class="evaluation-item">
                    <div class="about">价格</div>
                    <div class="reason-list">
                        <div class="reason-item <#if json.价格 == '划算'>on</#if>">划算</div>
                        <div class="reason-item <#if json.价格 == '一般'>on</#if>">一般</div>
                        <div class="reason-item <#if json.价格 == '不划算'>on</#if>">不划算</div>
                    </div>
                </section>
                 <section class="evaluation-content">
             	  <textarea style="width:100%; height:1.4rem; border:.02rem solid #ddd; border-radius:.04rem; padding:.1rem;" placeholder="觉得这件衣服怎么样？欢迎留言 (选填)" id="content${sku.serial_num}" class="content">${sku.content}</textarea>
				</section>
            </section>
        </section>
		</#list>

    </section>

    <section class="pay-info">
        <p>
            <span>商品金额</span>
            <span>￥<span id="total_amount">${total_amount}</span></span>
        </p>
        <p class="youhui">
            <span>会员折扣优惠<i class="about"></i></span>
            <span>-￥<span id="discount_amount">${discount_amount}</span></span>
        </p>
        <p>
            <span>代金券</span>
            <span>-￥<span id="voucher_amount">${voucher_amount}</span></span>
        </p>
    </section>

    <section class="pay-box">
        <div class="picker-all">
            全选
        </div>
        <div class="total">合计：￥<span class="money" id="pay_amount">${pay_amount}</span></div>
        <div class="submit-btn">提交订单</div>
    </section>
</section>

<!-- 商品未做评价内容弹窗 -->
<section class="mask-box mask-evaluation">
    <h3 class="title">还有商品未作出选择与评价哦</h3>
    <p class="description">请确认你要留下来的商品并评价</p>
    <div class="base-btn i-see">知道了</div>
</section>

<!-- 全部商品都不要 -->
<section class="mask-box mask-nothing">
    <h3 class="title">这次没有喜欢的服饰吗？</h3>
    <p class="description">确认后不能修改订单哦～</p>
    <div class="btn-list">
        <div class="base-btn cancel-btn hl">我再想想</div>
        <div class="base-btn ok-btn">确认</div>
    </div>
</section>

<!-- 订单提交确认弹窗 -->
<section class="mask-box mask-confirm">
    <h3 class="title">提交后将为您生成付款订单</h3>
    <p class="description"></p>
    <div class="btn-list">
        <div class="base-btn cancel-btn">取消</div>
        <div class="base-btn ok-btn hl">确认</div>
    </div>
</section>

<section class="mask-box mask-discount">
    <h3 class="title"><span>会员尊享折扣优惠</span></h3>
    <p class="description">购买3件享受9.5折<br>
        全部购买享受9折</p>
    <div class="base-btn i-see">知道了</div>
</section>

<div class="toast"></div>
<div class="mask-bg"></div>

<script>
	var pay_amount = '${pay_amount}';
	var voucher_amount = '${voucher_amount}';
	var discount_amount = '${discount_amount}';
	var total_amount = '${total_amount}';
	var order_no = '${order_no}';
</script>

<script src="/js/zepto.js"></script>
  <script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.3.2.js"></script>
<script src="/js/bill.js"></script>
</body>
</html>