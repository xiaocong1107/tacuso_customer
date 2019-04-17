var $uploadImg = $('.upload-img'),
    $uploadBtn = $('.upload'),
    $nextBtn = $('.next-btn'),
    $contentMiddle = $(".content-middle"),
    $fullshotUrl  =$(".fullshotUrl");
/**
$uploadBtn.on('change', function (e) {
    console.info('[start upload]', e);
    var target = e.target;

    if (!target.files) {
        // toast 处理
        return;
    }

    var data = new FormData();
    data.append('file', target.files[0]);
    $.ajax({
        type: 'POST',
        url: '/projects',
        // post payload:
        data: data,
        'Content-Type': 'multipart/form-data',
    });
});
**/
document.body.addEventListener('touchstart', function () {});

var isShowDialog = 0
function parseUrlPath() {
	console.log(window.location.pathname)
	if (window.location.pathname.indexOf("/photo/index") >= 0) {
		isShowDialog = 1
	} else {
		isShowDialog = 0
	}
}

parseUrlPath()



configWx();

function configWx(){

    $.post("/wx_js/config",{url:window.location.href},function(data){
        if (data.code == "200") {
            wx.config({
                debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                appId: data.data.appId, // 必填，公众号的唯一标识
                timestamp: data.data.timestamp, // 必填，生成签名的时间戳
                nonceStr: data.data.nonceStr, // 必填，生成签名的随机串
                signature: data.data.signature,// 必填，签名
                jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage','onMenuShareQQ','onMenuShareQZone',
                    'chooseImage', 'previewImage', 'uploadImage', 'downloadImage'] // 必填，需要使用的JS接口列表
            });
        }
    },"json")


}



wx.ready(function() {
    /**
     * [weixinUpload 调用微信接口实现上传]
     * @param  {[function]} choose [选择图片后的回调]
     * @param  {[function]} upload [上传后的回调]
     */
    function weixinUpload(choose, upload) {
        wx.chooseImage({
            count: 1,
            sizeType: ['original', 'compressed'],
            sourceType: ['album', 'camera'],
            success: function(res) {  console.log(res);
                var localIds = res.localIds;
                choose && choose(localIds); //选择图片后回调
                // 上传照片
                wx.uploadImage({
                    localId: '' + localIds,
                    isShowProgressTips: 1, //开启上传进度
                    success: function(res) {
                        serverId = res.serverId;
                        upload && upload(serverId); //上传图片后回调
                    }
                });
            }
        });
    }

    /**
     * [uploadImage 上传图片到本地服务器]
     * @param  {[type]}   mediaId  [图片serverID]
     * @param  {Function} callback [回调]
     */
    function uploadImage(mediaId, callback) {
        $.ajax({
            type: "POST",
            url: "/uploadFile/wechatUpLoad",
            data: {
                media_id: serverId
            },
            dataType: "json",
            success: function(result) {
                if (result.code == 200) {
                    callback(result.data);
                } else {
                    console.log("上传失败！");
                }
            },
            error: function() {
                console.log("系统异常，请稍后重试");
            }
        });
    }




    //点击上传按钮
    $contentMiddle.on('click', function() {
        weixinUpload(
            function(localIds) {
                //$uploadImg.attr('src', localIds); //微信本地图片地址,可以用来做上传前预览
                $contentMiddle.addClass("on")
            },
            function(serverId) {

                //TODO 上传中

                uploadImage(serverId, function(data) {
                    $uploadImg.attr('src', data); //返回真实的图片地址
                    $contentMiddle.addClass("on");
                    $fullshotUrl.val(data);

                    $toast("上传成功");
                    //TODO 上传完成
                    checkRegister();
                });
            }
        );
    });

    $toast = $('.toast');
    function toast (content) {
        $toast.html(content).show();
        setTimeout(function() {
            $toast.hide();
        }, 3000);
    }


    //点击下一步
    $nextBtn.on("click",function(){
        var fullShortUrl = $fullshotUrl.val();
        console.log(fullShortUrl)
        if($fullshotUrl.val() == ""){
            toast('请上传你的全身照');
            return false;
        }

        $.post("/photo/fullBody",{"fullshot":fullShortUrl},function(data){
            //alert(JSON.stringify(data));
            if(data.code=="200"){

                if(window.location.href.indexOf("usercenter/upload")>0){
//                    history.pushState({"page":"/usercenter/upload"},"",window.location.href);
//                    window.location.href="/usercenter/editUserInfo";
                    window.history.back();
                }else{
                    history.pushState({"page":"/photo/index"},"",window.location.href);
//                    window.location.href="/profile/index";
                    window.location.href="/usercenter/saveCoupon";
                   
                }


            }else{
                toast('上传失败，请联系客服');
            }
        },"json");
    })



});

getFullBodyShot();

function getFullBodyShot(){

    $.post("/photo/getFullBody",{},function(data){

        if(data.code=="200"){

            $uploadImg.attr('src', data.data.fullbody); //返回真实的图片地址
            $contentMiddle.addClass("on");
            $fullshotUrl.val(data.data.fullbody);



        }else{

        }
    },"json");

}

function checkRegister(){

	$.post('/register/register', function(result) {

        if(result.code=="200"){
        	if(result.data.is_question_finish == 1) {
        		$.ajax({
        	        url: "/user/isMember",
        	        type:"post",
        	        dataType:"json",
        	        success: function (res) {
        	            if (res.code == "200") {
        	            	wx.miniProgram.postMessage({ data: {type: 'tab',uno:res.data.uno,isShowDialog:isShowDialog}})
        	            	wx.miniProgram.switchTab({
        	            	    url:'../order/order'
        	            	})
        	            }
        	        }
        		})
        	}else {
        		$register.toast('个人档案尚未完善！');
        		window.location.href="/page/index";                        		
        	}
        }else{
        	$register.toast(result.message);
            window.location.href="/register/index";         
        }

    },"json");
}