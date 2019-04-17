var $maskBg = $('.mask-bg'),
$qrMask = $('.qrcode-mask'),
$closeBtn = $('.close-btn'),
$maskBox = $('.register-box'),
$start = $('.i-see'),
$gzh = $('.gzh');

var $utils = {
  showMask (show) {
    show === true ? $maskBg.addClass('active') : $maskBg.removeClass('active');
  },

  toast (content) {
    $toast.html(content).show(50);
    setTimeout(function() {
      $toast.hide(100);
    }, 3000);
  },
};

$gzh.on('click', function () {
  $maskBg.addClass('active');
  $qrMask.removeClass('fhide');
});

$closeBtn.on('click', function () {
  $qrMask.addClass('fhide');
  $maskBg.removeClass('active');
});

$.post("/box/next_box_info",function(data){
    if(data.code=="200"){
        $(".check-box").html(data.data + '<i class="check-arrow"></i>');
    }
},"json");

function GetRequest() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for(var i = 0; i < strs.length; i ++) {
            theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
        }
    }
    return theRequest;
}

(function firstLogin(){

    var req = GetRequest();
    

    if(first_login!=undefined && first_login=="1" ){
        $utils.showMask(true);
        $maskBox.show();

        localStorage.setItem("firstLogin",false);
    }

})();

$start.on('click', function () {
  $maskBox.hide();
  $utils.showMask(false);
});

document.body.addEventListener('touchstart', function () {});