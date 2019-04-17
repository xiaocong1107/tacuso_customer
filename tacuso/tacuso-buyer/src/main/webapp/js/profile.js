var $name = $('.name'),
    $job = $('.selected-job'),
    $select = $('.select'),
    $work = $('.work'),
    $birthDay = $('.birthday'),
    $addressInput = $('.address-input'), // 收件地址编辑
    $addressBox = $('.address-info__box'),
    $userInfo = $('.user-info'),
    $address = $('.address'),
    $finishBtn = $('.next-btn'),
    $closeBtn = $('.close-btn'),
    $toast = $('.toast'),
    $finishAddress = $('.address-finish__btn'),
    $maskBg= $('.mask-bg');


/**
 * update new
 * 设置默认生日 $('.birth-desc').addClass('full').html('2011111');
 *
 */
$("#birthday").on("input",function () {
    if($(this).val().length > 0){
        $(this).addClass("full");
        // 新增
        $('.birth-desc').addClass("full").html($(this).val().replace(/\-/ig, '/'));
    } else {
        $(this).removeClass("full");
        // 新增
        $('.birth-desc').removeClass("full").html('选择日期');
    }
});

initAddress();
function initAddress(){

    $.post("/user/getBindPhone",{},function(data){

        if(data.code=="200"){
            //获取绑定手机
            addressInfo.tel = data.data.bindphone;
        }

        initProfileData();

    },"json");



}


function initProfileData(){

    $.post("/profile/getProfile",{},function(data){

        if(data.code=="200"){
            if(data.data.address){
                var address = data.data.address;
                addressInfo.tel = address.phone;
                addressInfo.addressName = address.name;
                addressInfo.city = address.city;
                addressInfo.address = address.detail;

            }

            if(data.data.userInfo){
                var userInfo = data.data.userInfo;
                $('input.name').val(userInfo.nickname);
                $job.html(userInfo.industy);
                $work.val(userInfo.position);

                if(userInfo.birthday!=""){
                    $birthDay.attr('value', userInfo.birthday.replace(/\//ig, '-'));
                    $('.birth-desc').addClass("full").html(userInfo.birthday);
                }else{
                    $('.birth-desc').html("选择日期");
                }

                //alert($birthDay.val())
            }
            $profile.updatePageView();
        }

    },"json");

}


$profile = {
  emptyMessage: {
    tel: '手机号码不能为空～',
    city: '所在省市区不能为空～',
    name: '姓名不能为空～',
    address: '详细地址不能为空～',
  },
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

  updateMaskData () {
    $('input.addname').attr('value', addressInfo.name);
    $('input.address').attr('value', addressInfo.address);
    $('.city').html(addressInfo.city);
    $('input.tel').attr('value', addressInfo.tel);
  },
  updatePageView () {
    $('.name-text').html(addressInfo.addressName);
    $('.tel-text').html(addressInfo.tel);
    $('p.address').html(addressInfo.city.replace(/\//ig, '') + addressInfo.address);
  },

  checkTel (tel) {
    var isTel = true; 
    if(tel.length !== 11 || !(/^1[3|4|5|7|8][0-9]\d{4,8}$/.test(tel))) {
      isTel = false;
    }
    return isTel;
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


function InitName(){
    addressInfo.name =   $('input.name').val();
}

// 点击编辑详细地址
$addressInput.on('click', function () {
  InitName();
  $profile.updateMaskData();
  $profile.showMask(true);
  $addressBox.addClass('active');
});

$closeBtn.on('click', function () {
  $addressBox.removeClass('active');
  $profile.showMask(false);
});

// 处理收件地址逻辑
$finishAddress.on('click', function () {
  var tel = $('input.tel').val(),
      addressName = $('input.addname').val(),
      city = $('.city').html(),
      address = $('input.address').val();

  if (!addressName || !city || !address || !tel) {
    $profile.toast("请填写完整信息");
    return;
  }

  if (!$profile.checkTel(tel)) {
    $profile.toast('请填写正确的11位手机号码～');
    return;
  }

  addressInfo.tel = tel;
  addressInfo.addressName = addressName;
  addressInfo.city = city;
  addressInfo.address = address;

  $profile.updatePageView();
  $addressBox.removeClass('active');
  $profile.showMask(false);
});

// 处理完成个人档案创建按钮
$finishBtn.on('click', function () {

   var name = $('input.name').val(),
       job =  $job.html(),
       work = $work.val(),
       birthday = $birthDay.val();

    if (!addressInfo || !addressInfo.addressName || !addressInfo.city || !addressInfo.address || !addressInfo.tel) {
        $profile.toast("请填写完整信息");
        return;
    }

    if (!$profile.checkTel(addressInfo.tel)) {
        $profile.toast('请填写正确的11位手机号码～');
        return;
    }

    if(!name){
        $profile.toast("姓名不能为空");
        return;
    }

    if(job && job === "选择行业"){
        $profile.toast("所在行业不能为空");
        return;
    }
    if(!work){
        $profile.toast("职位不能为空");
        return;
    }

    if(!birthday){
        $profile.toast("生日不能为空");
        return;
    }
    var profile = {};
    profile.nickname = name;
    profile.industy  = job;
    profile.position = work;
    profile.birthday = birthday.replace(/\-/ig, '/');
    var postAddress  = {};
    postAddress.name= addressInfo.addressName;
    postAddress.phone= addressInfo.tel;
    postAddress.detail= addressInfo.address;
    postAddress.city = addressInfo.city;
    $.post("/profile/saveProfile",Object.assign(profile,postAddress),function(data){
        if(data.code=="200"){
            history.pushState({"page":"/profile/index"},"",window.location.href);
            //第一次
//            window.location.href="/usercenter/index?first_login=1";
            window.location.href="/page/couponIndex";
        }
    },"json");
});



$select.on('change', function() {
  $('.selected-job').html($(this).val());
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

  $('#city').html(text1 + '/' + text2 + '/' + text3);
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


