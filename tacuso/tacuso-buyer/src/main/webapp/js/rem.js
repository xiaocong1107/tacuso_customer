
/**
 * 使用说明:
 *     1、在 html 标签中加上 data-dw 属性, 设置设计稿的实际宽度,默认为 750
 *     2、css的尺寸改为rem, 即 100px 的设计稿实际尺寸, 应该改为 1rem, 可以利用sass中封装的 px2rem() 处理
 *     3、利用 px2rem() 在css中指定 body 的 width 值
 *     4、在 css 中设置 html 标签的默认 font-size, 建议设置为50px
 *
 *  ps: 在uc浏览器上有个bug, 字体会被放大, 需通过增加html5标签 header、nav、section 中的任一个来避免
 */

~function(window) {
    var uAgent = window.navigator.userAgent;
    var isIOS = uAgent.match(/iphone/i);
    var isYIXIN = uAgent.match(/yixin/i);
    var is2345 = uAgent.match(/Mb2345/i);
    var ishaosou = uAgent.match(/mso_app/i);
    var isSogou = uAgent.match(/sogoumobilebrowser/ig);
    var isLiebao = uAgent.match(/liebaofast/i);
    var isGnbr = uAgent.match(/GNBR/i);
  
    var dataDv = document.documentElement.dataset.dw || 750;
    var minFontSize = 42;
    var maxFontSize = 100;
  
    function resizeRoot() {
      var wWidth = (screen.width > 0) ? (window.innerWidth >= screen.width || window.innerWidth == 0) ? screen.width : window.innerWidth : window.innerWidth;
      if (isIOS) {
        wWidth = screen.width;
      }
      var wFsize = wWidth > dataDv ? maxFontSize : wWidth / (dataDv / 100);
      wFsize = wFsize > minFontSize ? wFsize : minFontSize;
      document.documentElement.style.fontSize = wFsize + 'px';
    }
  
    var tid;
    window.addEventListener('resize', function() {
      clearTimeout(tid);
      tid = setTimeout(resizeRoot, 300);
    }, false);
    window.addEventListener('pageshow', function(e) {
      if (e.persisted) {
        clearTimeout(tid);
        tid = setTimeout(resizeRoot, 300);
      }
    }, false);
  
    resizeRoot();
    if (isYIXIN || is2345 || ishaosou || isSogou || isLiebao || isGnbr) {
      /*YIXIN 和 2345 这里有个刚调用系统浏览器时候的bug，需要一点延迟来获取*/
      setTimeout(function () {
        resizeRoot();
      }, 500);
    }
  }(window);     