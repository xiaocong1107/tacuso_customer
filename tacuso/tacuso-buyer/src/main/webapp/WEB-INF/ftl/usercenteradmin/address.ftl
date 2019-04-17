<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理收件地址</title>
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="yes" name="apple-touch-fullscreen">
    <meta name="viewport" content="width=device-width,initial-scale=1.0, maximum-scale=1.0, minimum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <script src="/js/rem.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/address.css">
</head>
<body>
<section class="address-page">
    <section class="address-list">
        <section class="address-item selected">
            <div class="picker-icon"></div>
            <div class="address-info">
                <div class="user-info">
                    <span class="name">金木</span>
                    <span class="tel">13800138000</span>
                </div>
                <div class="address">广州市天河区黄埔大道平云路161号B塔999楼</div>
            </div>
            <!-- data-index 必须添加，用于标记索引 -->
            <div class="arrow-icon" data-index="0"></div>
        </section>

        <section class="address-item">
            <div class="picker-icon"></div>
            <div class="address-info">
                <div class="user-info">
                    <span class="name">菲世</span>
                    <span class="tel">13800138000</span>
                </div>
                <div class="address">广州市天河区黄埔大道平云路161号B塔999楼</div>
            </div>
            <!-- data-index 必须添加，用于标记索引 -->
            <div class="arrow-icon" data-index="1"></div>
        </section>
    </section>

    <section class="blank-20"></section>
    <section class="add-address"><em>+</em>添加收件地址</section>
    <section class="save-btn">
        保存
    </section>
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
            <div class="base-input city" id="city">
                广东/广州/越秀区
            </div>
        </section>
        <section class="input-item">
            <div class="label">详细地址</div>
            <div class="base-input">
                <input type="text" class="address" placeholder=""/>
            </div>
        </section>
    </section>
    <section class="address-finish__btn">
        完成
    </section>
</section>

<div class="toast"></div>
<div class="mask-bg"></div>

<script>
    var addressList = [];
</script>

<script src="/js/template-web.js"></script>
<script id="addressPage" type="text/html">

    {{@addressListStr }}

    <section class="blank-20"></section>
            <section class="add-address"><em>+</em>添加收件地址</section>
    <section class="save-btn">
            保存
            </section>

</script>
<script id="addressList" type="text/html">

    <section class="address-list">


        {{each addressList $address $index}}
        <section class="address-item {{if $address.is_main_address==1}}selected{{/if}}" address-id="{{$address.address_id}}">
            <div class="picker-icon"></div>
            <div class="address-info">
                <div class="user-info">
                    <span class="name">{{$address.name}}</span>
                    <span class="tel">{{$address.phone}}</span>
                </div>
                <div class="address">{{$address.city}}{{$address.detail}}</div>
            </div>
            <!-- data-index 必须添加，用于标记索引 -->
            <div class="arrow-icon" data-index="{{$index}}"></div>
        </section>
        {{/each}}

    </section>

</script>
<script src="/js/zepto.js"></script>
<script src="/js/picker.min.js"></script>
<script src="/js/city.js"></script>
<script src="/js/address.js"></script>
</body>
</html>