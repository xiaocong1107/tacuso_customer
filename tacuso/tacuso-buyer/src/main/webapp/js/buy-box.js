var  $userInfo = $('.user-info'),
    $address = $('.address'),
    $closeBtn = $('.close-btn'),
    $addressBox = $('.address-info__box'),
    $addressBoxTitle = $('.address-info__title'),
    $userInfo = $('.user-info'),
    $address = $('.address'),
    $closeBtn = $('.close-btn'),
    $editBill = $('.edit-bill'), // 点击修改订单按钮
    $tel = $('.tel'),
    $finishAddress = $('.address-finish__btn'),
    $addAddress = $('.add-address'),
    $maskBg= $('.mask-bg'), // 灰色背景
    $toast = $('.toast'),
    $editAddress = $('.edit-icon'),
    $addressList = $('.address-list__box'), // 收件地址列表弹窗
    $submitBtn = $('.confirm-btn'),
    $addressItem = $('.address-item'),
    $arrowBtn = $('.arrow-icon'),
    $pjBtn = $('.pingjia'); // 评价本次盒子并付款按钮
	$needBox = $('.need-box'), // v9.1 我要一个盒子按钮
	$cancelBox = $('.cancel-box'), // v9.1 取消本次盒子
	$cancelBtn = $('.cancel-btn'), // v9.1 取消盒子弹窗取消按钮
	$okBtn = $('.ok-btn'); // v9.1 取消盒子弹窗确认按钮
    $buyer_msg = $('.buyer_msg'),
    $select = $('.select'), // 体重选择
    $pickItem = $('.pick-item'),
    $confrim = $('.confrim'),
    $cancle = $('.cancle'),
    $maskBox= $('.mask-box');

    var $curIndex = null, // 用于编辑当前选择修改收件地址
    $pickAddressIndex = 0; // 设置默认地址
    

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
	  addressList.splice($curIndex, 1, data);
	    console.log(  $addressItem.eq($curIndex).find('span.name'));
	  $addressItem.eq($curIndex).find('span.name').html(data.name);
	  $addressItem.eq($curIndex).find('span.tel').html(data.phone);
	  $addressItem.eq($curIndex).find('div.address').html(data.city.replace(/\//ig, '') + data.address);
  },
  /**
   * 清楚弹窗数据
   */
  clearMaskData () {
      $('input.name').attr('value', '');
      $('input.address').attr('value', '');
      $('input.tel').attr('value', '');
      $('.city').html('广东/广州/越秀区');
  },  
  /**
   * 获取表单数据
   */
  getFormData () {
    var tel = $tel.val();
    return {
      tel,
    };
  },

}

//点击提交修改后订单信息
$submitBtn.on('click', function(){
	$utils.showMask(true);
	$maskBox.css('display','flex')
	console.log('submitBtn')
})

function submitBox () {
	  if (!$('.select-value').html()) {
	    $utils.toast('请选择体重变化情况～');
	    return;
	  }
		

		var answerList='';
		$(".pick-item.active").each(function(){
			answerList=answerList+ $(this).html()+'，';
		});
		
		var len = $('#buyer_msg').val();
		if(len!=''){
			if(len.length>150){
			    $utils.toast('请输入150字以内');
			    return;
			}
		}
	  
	  var params = {
		weight:  $('#weight').val(),
		buyer_msg:  $('#buyer_msg').val(),
		order_no:  order_no,
		add_id:  add_id,
		answerList:answerList,
	  };
	 
	  $.post("/box/update",Object.assign(params),function(data){
	      if(data.code=="200"){
	          // 发起请求成功后，
//	    	  $('#box1').next('.blank-20').hide();
//	    	  $('#box1').hide();
//	    	  $('.status-item').eq(0).removeClass('ready').addClass('prepare');
	    	  $utils.showMask(false);
	    		$maskBox.css('display','none')
	    	  window.location.href = '/box/mybox';
	      }else
	    	  $utils.toast(data.message);
	  },"json");
	  
	}


// 点击更换收件地址
$editAddress.on('click', function () {
	 getAddressList();

});

$confrim.on('click', function () {
	submitBox();
});

$cancle.on('click', function () {
	$utils.showMask(false);
	$maskBox.css('display','none')
});

function getAddressList(){
    $.post("/address/list",{},renderAddressList,'json');
}

function renderAddressList(retdata){
    template.defaults.debug=true
    template.defaults.imports.JSON = JSON;
    addressList=[];
    if(retdata.code=="200"){
        $addressList.html();
        var data = retdata.data;
        data.forEach(function (item) {
        	item.add_id = add_id;
        });

        var addressStr = template("addressList",{addressList:data})
        pageStr = template("addressPage",{addressListStr:addressStr});
        $addressList.html(pageStr);        
        data.forEach(function (item) {
            addressList.push(
                {	
                    address_id:item.address_id,
                    name:item.name,
                    tel:item.phone,
                    city:item.city,
                    address:item.detail
                }
            );

        });
        $utils.showMask(true);
        $addressList.addClass('active');
        
        bindQuestionEvent();
    }
}

// 点击修改订单信息按钮
$editBill.on('click', function () {
  var box1Top = document.querySelector('#box1').offsetTop;
  document.querySelector('.page').scrollTop = box1Top;
});

// 点击评价本次盒子并付款
$pjBtn.on('click', function () {
  window.location.href = '/order/bill';
});



//事件监听器
function bindQuestionEvent(){

	var $addressBox = $('.address-info__box'),
	    $addressBoxTitle = $('.address-info__title'),
	    $userInfo = $('.user-info'),
	    $address = $('.address'),
	    $closeBtn = $('.close-btn'),
	    $editBill = $('.edit-bill'), // 点击修改订单按钮
	    $tel = $('.tel'),
	    $finishAddress = $('.address-finish__btn'),
	    $addAddress = $('.add-address'),
	    $maskBg= $('.mask-bg'), // 灰色背景
	    $toast = $('.toast'),
	    $editAddress = $('.edit-icon'),
	    $addressList = $('.address-list__box'), // 收件地址列表弹窗
	    $saveBtn = $('.save-btn'),
	    $submitBtn = $('.confirm-btn'),
	    $addressItem = $('.address-item'),
	    $arrowBtn = $('.arrow-icon'),
	    $pjBtn = $('.pingjia'); // 评价本次盒子并付款按钮


	// 点击选择切换收件地址
	$addressItem.on('click', function () {
	  	$pickAddressIndex = Number($(this).children('.arrow-icon').attr('data-index'));
	  	$(this).siblings().removeClass('selected');
	  	$(this).addClass('selected');
	});

	// 点击编辑地址详情
	$arrowBtn.on('click', function () {
	  	$curIndex = Number($(this).attr('data-index'));

	  	console.info('[curIndex]===='+$curIndex);
	  	$utils.updateMaskData(addressList[$curIndex]);
	  	$addressBoxTitle.html('编辑收件地址');
	  	$utils.showMask(true);
	  	$addressBox.addClass('active');
	  	addEditStatus="edit";
	});

	// 点击新增收件地址
	$addAddress.on('click', function () {
		addEditStatus='add';
		$utils.clearMaskData();
		$addressBoxTitle.html('添加收件地址');
		$addressBox.addClass('active');
	})

	$closeBtn.on('click', function () {
		$addressBox.removeClass('active');
	});
	
	// 处理收件地址编辑保存
	$finishAddress.on('click', function () {
		var data = {
			name: $('input.name').val(),
		    phone: $('input.tel').val(),
		    city: $('#city').html(),
		    detail: $('input.address').val(),
		};
	  
		if (!data.name || !data.phone || !data.city || !data.detail) {
			$utils.toast('请填写完整信息～');
			return;
		}
	
		if (!$utils.checkTel(data.phone)) {
			$utils.toast('请填写正确11位手机号码～');
			return;
		}
		
	    var url = "";
	    if(addEditStatus.indexOf("add")>=0){
	        url = "/address/add";
	    }else{
	    	data.address_id = addressList[$curIndex].address_id;
	        url = "/address/edit"
	    }

	    $.post(url,data,function(retdata){
	        // 发起异步接口请求
	        //$addressUtils.updatePageView(data);
	        $addressBox.removeClass('active');
	        $utils.showMask(false);
	        getAddressList();
	    },"json");		
		$utils.updatePageView(data);
		$addressBox.removeClass('active');
	});
	
	// 选择地址保存操作
	$saveBtn.on('click', function () {

		var params = {
			address_id:  $(".address-item.selected").attr("address-id"),
			order_no:  order_no,
		};
//		if (add_id != params.address_id) {			
			$.post('/box/update/address',Object.assign(params),function(data){
				if(data.code=="200"){
					$('.about-address').find('span.name').html(addressList[$pickAddressIndex].name);
					$('.about-address').find('span.tel').html(addressList[$pickAddressIndex].tel);
					$('.about-address').find('div.address').html(addressList[$pickAddressIndex].city.replace(/\//ig, '') + addressList[$pickAddressIndex].address);
					add_id = params.address_id;
				}
			},"json");
//		}
	    
		$addressList.removeClass('active');
		$utils.showMask(false);
	});
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

document.body.addEventListener('touchstart', function () {});

/**
 * v9.1
 * 点击确认要一个盒子
 */
$needBox.on('click', function () {
  // 发起异步请求，更新当前状态
});

/**
 * v9.1
 * 点击取消本次盒子
 */
$cancelBox.on('click', function() {
  $('.mask-box').show();
  $utils.showMask(true);
});


/**
 * v9.1
 * 取消盒子弹窗相关操作
 */
$cancelBtn.on('click', function () {
  $('.mask-box').hide();
  $utils.showMask(false);
});

/**
 * v9.1
 * 确认盒子取消按钮
 */
$okBtn.on('click', function () {
  $('.mask-box').hide();
  $utils.showMask(false);

	var params = {
			order_no: order_no,
		};
			
			$.post('/box/update/cancel',Object.assign(params),function(data){
				if(data.code=="200"){
					  window.location.href = '/box/waitBox';
				}
			},"json");
  
  // 发起异步请求
  //window.location.href = './wait-box.html';
			
});


// 点击选中衣服款式
$pickItem.on('click', function () {
  if ($(this).hasClass('active')) {
    $(this).removeClass('active');
  } else {
    $(this).addClass('active');
  }
});


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

document.body.addEventListener('touchstart', function () {});

//体重选择
$select.on('change', function () {
  $(this).siblings('.select-value').html($(this).val());
});
