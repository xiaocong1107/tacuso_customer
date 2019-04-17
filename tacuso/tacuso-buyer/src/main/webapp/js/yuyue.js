configWx()
function configWx(){

    $.post("/wx_js/config",{url:window.location.href},function(data){
        if (data.code == "200") {
            wx.config({
                debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                appId: data.data.appId, // 必填，公众号的唯一标识
                timestamp: data.data.timestamp, // 必填，生成签名的时间戳
                nonceStr: data.data.nonceStr, // 必填，生成签名的随机串
                signature: data.data.signature,// 必填，签名
                jsApiList: ['chooseWXPay','onMenuShareTimeline','onMenuShareAppMessage','onMenuShareQQ','onMenuShareQZone'] // 必填，需要使用的JS接口列表
            });
        }
    },"json")
}
var $tab = $('.yuyue-type'),
    $day = $('.day'),
    $timeline = $('.timeline'),
    $maskBg= $('.mask-bg'), // 灰色背景
    $toast = $('.toast'),
    $addressBox = $('.address-info__box'),
    $closeBtn = $('.close-btn'),
    $finishAddress = $('.address-finish__btn'),
    $editAddress = $('.edit-icon');

var active = 'weekday';
$tab.on('click', function() {
  $tab.removeClass('active');
  $(this).addClass('active');
  active = $(this).attr('data-type');
  setFormData();
});

var weekdays = ['今天', '明天', '后天'];


function initDay (dayNum){
  var oDate = new Date();   //获取当前时间
  return new Date(oDate.getFullYear(),oDate.getMonth(), oDate.getDate() + dayNum);
}

function getTimeLines (hour) {
  var list = ['08:00~09:00', '09:00~10:00', '10:00~11:00', '11:00~12:00', '12:00~13:00',
  '13:00~14:00', '14:00~15:00', '15:00~16:00', '16:00~17:00', '17:00~18:00', '18:00~19:00', '19:00~20:00'];
  var pos = hour - 8;
  if (hour < 8) {
    return list;
  } else if (hour >= 8 && hour <= 19) {
    list.splice(0, pos + 1);
    return list;
  } else {
    return [];
  }
}

function setTimeLines (hour) {
  var html = '';
  var list = getTimeLines(hour);
  for (i = 0; i < list.length; i++) {
    html += '<option value="' + list[i] + '">' + list[i] + '</option>'
  }
  $timeline.find('.selected-time').html(list[0] || '已错过当前预约时段');
  $timeline.find('.timeline-select').html(html);
}


function setFormData () {
  var html = '';
  var nowDate = new Date();
  var hour = nowDate.getHours();
  var day, date;
  if (active === 'weekday') {
    for (i = 0; i < 3; i++) {
      date = initDay(i);
      day = [weekdays[i], date.getMonth() + 1, '月', date.getDate(), '日'].join('');
      if (i === 0) {
        $day.find('.selected-time').html(day);
      }
      html += '<option value="' + day + '">' + day + '</option>';
    }
    $day.find('.day-select').html(html);
  }

  if (active === 'weekend') {
    var cDate = new Date().getDay();
    cDate = cDate === 0 ? 7 : cDate;
    var sat = 6 - cDate;
    var sun = 7 - cDate;
    if (sat === 0) {
      date = initDay(0);;
      day = ['今天', date.getMonth() + 1, '月', date.getDate() , '日'].join('');
    } else if (sat > 0) {
      date = initDay(sat);
      day = ['本周六', date.getMonth() + 1, '月', date.getDate(), '日'].join('');
      hour = 0;
    }
    if (day) {
 
     $day.find('.selected-time').html(day);
     html += '<option value="' + day + '">' + day + '</option>';
    }

    if (sun === 0) {
      date = initDay(0);
      day = ['今天', date.getMonth() + 1, '月', date.getDate() , '日'].join('');
      $day.find('.selected-time').html(day);
      html += '<option value="' + day + '">' + day + '</option>';
      $day.find('.day-select').html(html);
    } else if (sun > 0) {
      date = initDay(sun);
      day = ['本周日', date.getMonth() + 1, '月', date.getDate(), '日'].join('');
      html += '<option value="' + day + '">' + day + '</option>';
      $day.find('.day-select').html(html);
    }
  }
  setTimeLines(hour);
}

$('.day-select').on('change', function () {
  var day = $(this).val();
  var reg = new RegExp(/^今天/, 'g');
  if (reg.test(day)) {
    var nowDate = new Date();
    var hour = nowDate.getHours();
    setTimeLines(hour);
  } else {
    setTimeLines(0);
  }
  $day.find('.selected-time').html(day);
});


$('.day-select').on('change', function () {
	  $timeline.find('.selected-day').html($(this).val());
	});


$('.timeline-select').on('change', function () {
	  $timeline.find('.selected-time').html($(this).val());
	});

$utils = {
  /**
   * 处理背景遮罩
   * @param {*} show boolean
   */
  showMask (show) {
    show === true ? $maskBg.addClass('active') : $maskBg.removeClass('active');
  },

  toast (content) {
    $toast.html(content).show(50);
    setTimeout(function() {
      $toast.hide(100);
    }, 3000);
  },

  checkTel (tel) {
    var isTel = true; 
    if(tel.length !== 11 || !(/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(tel))) {
      isTel = false;
    }
    return isTel;
  },
  /**
   * 设置地址弹窗
   */
  updateMaskData (data) {
    $('input.name').attr('value', data.name);
    $('input.address').attr('value', data.address);
    $('.city').html(data.city);
    $('input.tel').attr('value', data.tel);
  },
  /**
   * 更新地址页面数据
   */
  updatePageView (data) {
    $('.address-info').find('span.name').html(data.name);
    $('.address-info').find('span.tel').html(data.tel);
    $('.address-info').find('div.address').html(data.city.replace(/\//ig, '') + data.address);
    // addressList.splice($curIndex, 1, data);
    // $addressItem.eq($curIndex).find('span.name').html(data.name);
    // $addressItem.eq($curIndex).find('span.tel').html(data.tel);
    // $addressItem.eq($curIndex).find('div.address').html(data.city.replace(/\//ig, '') + data.address);
  },
}

// 处理省市区选择
var nameEl = document.getElementById('city');

var first = []; /* 省，直辖市 */
var second = []; /* 市 */
var third = []; /* 镇 */

var selectedIndex = [0, 0, 0]; /* 默认选中的地区 */

var checked = [0, 0, 0]; /* 已选选项 */

function creatList(obj, list){
  obj.forEach(function(item, index, arr){
  var temp = new Object();
  temp.text = item.name;
  temp.value = index;
  list.push(temp);
  })
}

creatList(city, first);

if (city[selectedIndex[0]].hasOwnProperty('sub')) {
  creatList(city[selectedIndex[0]].sub, second);
} else {
  second = [{text: '', value: 0}];
}

if (city[selectedIndex[0]].sub[selectedIndex[1]].hasOwnProperty('sub')) {
  creatList(city[selectedIndex[0]].sub[selectedIndex[1]].sub, third);
} else {
  third = [{text: '', value: 0}];
}

var picker = new Picker({
  data: [first, second, third],
  selectedIndex: selectedIndex,
  title: '地址选择'
});

picker.on('picker.select', function (selectedVal, selectedIndex) {
  var text1 = first[selectedIndex[0]].text;
  var text2 = second[selectedIndex[1]].text;
  var text3 = third[selectedIndex[2]] ? third[selectedIndex[2]].text : '';

  $('#city').html( text1 + '/' + text2 + '/' + text3);
});

picker.on('picker.change', function (index, selectedIndex) {
  if (index === 0){
    firstChange();
  } else if (index === 1) {
    secondChange();
  }

  function firstChange() {
    second = [];
    third = [];
    checked[0] = selectedIndex;
    var firstCity = city[selectedIndex];
    if (firstCity.hasOwnProperty('sub')) {
      creatList(firstCity.sub, second);

      var secondCity = city[selectedIndex].sub[0]
      if (secondCity.hasOwnProperty('sub')) {
        creatList(secondCity.sub, third);
      } else {
        third = [{text: '/', value: 0}];
        checked[2] = 0;
      }
    } else {
      second = [{text: '', value: 0}];
      third = [{text: '', value: 0}];
      checked[1] = 0;
      checked[2] = 0;
    }

    picker.refillColumn(1, second);
    picker.refillColumn(2, third);
    picker.scrollColumn(1, 0)
    picker.scrollColumn(2, 0)
  }

  function secondChange() {
    third = [];
    checked[1] = selectedIndex;
    var first_index = checked[0];
    if (city[first_index].sub[selectedIndex].hasOwnProperty('sub')) {
      var secondCity = city[first_index].sub[selectedIndex];
      creatList(secondCity.sub, third);
      picker.refillColumn(2, third);
      picker.scrollColumn(2, 0)
    } else {
      third = [{text: '', value: 0}];
      checked[2] = 0;
      picker.refillColumn(2, third);
      picker.scrollColumn(2, 0)
    }
  }

});

picker.on('picker.valuechange', function (selectedVal, selectedIndex) {
  console.log(selectedVal);
  console.log(selectedIndex);
});

nameEl.addEventListener('click', function () {
    picker.show();
});



// 点击更换收件地址
$editAddress.on('click', function () {
  $utils.showMask(true);
  $addressBox.addClass('active');
});

$closeBtn.on('click', function () {
  $addressBox.removeClass('active');
  $utils.showMask(false);
});

// 处理收件地址编辑保存
$finishAddress.on('click', function () {
  var data = {
    name: $('input.name').val(),
    tel: $('input.tel').val(),
    address_id: $('input.address_id').val(),
    city: $('#city').html(),
    address: $('input.address').val(),
  };
  
  if (!data.name || !data.tel || !data.city || !data.address) {
    $utils.toast('请填写完整信息～');
    return;
  }

  if (!$utils.checkTel(data.tel)) {
    $utils.toast('请填写正确11位手机号码～');
    return;
  }
  // 发起异步接口请求
  $utils.updatePageView(data);
  $addressBox.removeClass('active');
  $utils.showMask(false);
  
//  $.post("/address/main_address",{address_id:address_id},function(data){
//      $addressUtils.toast('保存成功~');
//      window.location.href = '/express/yuyue';
//  },'json');


  
});

$('.yuyue-btn').on('click', function () {
  $('.mask-box').show();
  $utils.showMask(true);
});

$('.cancel-btn').on('click', function () {
  $('.mask-box').hide();
  $utils.showMask(false);
});
function getUrlParam(name) {
	   var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	   var r = window.location.search.substr(1).match(reg);
	   if (r != null) return unescape(r[2]); return null;
	}

orderNo = getUrlParam('orderNo')

// 处理确认预约
$('.ok-btn').on('click', function () {
	
	  var params = {
			  date:  $('#date').val(),
			  time:  $('#time').val(),
			  name:  document.getElementById("name").innerText,
			  tel:  document.getElementById("tel").innerText,
			  address:  document.getElementById("address").innerText,
			  orderNo:  orderNo,
			  };
	  
	  var data = new Date()
	  var keyword = params.date.slice(0,2)
	  var tempdata = data
	  if (keyword == "今天") {
		  tempdata = initDay(0)
	  } else if (keyword == "明天") {
		  tempdata = initDay(1)
	  } else if (keyword == "后天") {
		  tempdata = initDay(2)
	  } else {
		  keyword = params.date.slice(0,3)
		  var cDate = data.getDay();
		    cDate = cDate === 0 ? 7 : cDate;
		    var sat = 6 - cDate;
		    var sun = 7 - cDate;
		  if (keyword == "本周六") {
			  tempdata = initDay(sat)
		  } else if (keyword == "本周日") {
			  tempdata = initDay(sun)
		  }
	  }
	  
	  tempdata = formatData(tempdata)
	  var starttime = tempdata+params.time.split('~')[0]+":00"
	  var endtime = tempdata+params.time.split('~')[1]+":00"

	  params.starttime = starttime
	  params.endtime = endtime
	  
//		$.post('/express/callReceiver',Object.assign(params),function(retdata){
//				
//		},"json");
	  delete params.date
	  delete params.time
	  console.log(params)
	  $.ajax({
	        url: "/express/callReceiver",
	        type:"post",
	        dataType:"json",
	        data:params,
	        success: function (retdata) {
	        	if(retdata.code == "200"){
					   $utils.toast('预约成功~');
					   $('.mask-box').hide();
					   $utils.showMask(false);
//					   window.location.href = '/express/rate?orderNo='+orderNo;
	            		wx.miniProgram.switchTab({
						    url:'../order/order'
						})
				}
				else{
					   $utils.toast('预约失败:'+retdata.message);
				}
	        }
	  })
		
});

function formatData (now){
	var year = now.getFullYear(),
	month = now.getMonth() + 1,
	date = now.getDate(),
	hour = now.getHours(),
	minute = now.getMinutes(),
	second = now.getSeconds();
	function p(s) {
	return s < 10 ? '0' + s: s;
	}
	var timeFormat = year + "-" + p(month) + "-" + p(date) + " " //+ p(hour) + ":" + p(minute) + ":" + p(second);
	return timeFormat
}


window.onload = function () {
  setFormData();
}


document.body.addEventListener('touchstart', function () {});