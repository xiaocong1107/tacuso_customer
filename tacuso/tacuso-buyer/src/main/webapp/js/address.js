

var $addressPage = $('.address-page');
//数据获取
getAddressList();
function getAddressList(){
    $addressPage.html();
    $.post("/address/list",{},renderAddressList,'json');
}




function renderAddressList(retdata){
    template.defaults.debug=true
    template.defaults.imports.JSON = JSON;
    addressList=[];
    if(retdata.code=="200"){
        var data = retdata.data;
        var addressStr = template("addressList",{addressList:data})
        pageStr = template("addressPage",{addressListStr:addressStr});
        $addressPage.html(pageStr);
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

        bindQuestionEvent();
    }
}




var addEditStatus = "add";
var $curIndex = null; // 用于标记当前编辑地址项
$addressUtils = {
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
        $('input.name').val(data.name);
        $('input.address').val(data.address);
        $('.city').html(data.city);
        $('input.tel').val( data.tel);
    },
    /**
     * 更新地址页面数据
     */
    updatePageView (data) {
        $addressItem.eq($curIndex).find('span.name').html(data.name);
        $addressItem.eq($curIndex).find('span.tel').html(data.tel);
        $addressItem.eq($curIndex).find('div.address').html(data.city.replace(/\//ig, '') + data.address);
    },
    /**
     * 清楚弹窗数据
     */
    clearMaskData () {
        $addressBox.find('input').val('');
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


var $finishAddress = $('.address-finish__btn'); // 编辑完成地址
// 处理收件地址逻辑
$finishAddress.on('click', function () {
    var data = {
        name: $('input.name').val(),
        tel: $('input.tel').val(),
        city: $('#city').html(),
        address: $('input.address').val(),
    };
    if (!data.name || !data.tel || !data.city || !data.address) {
        $addressUtils.toast('请填写完整信息～');
        return;
    }

    if (!$addressUtils.checkTel(data.tel)) {
        $addressUtils.toast('请填写正确11位手机号码～');
        return;
    }

    var postData = {
        name: data.name,
        phone: data.tel,
        city:data.city,
        detail:data.address
    };

    var url = "";
    if(addEditStatus.indexOf("add")>=0){
        url = "/address/add";
    }else{
        postData.address_id = addressList[$curIndex].address_id;
        url = "/address/edit"
    }

    $.post(url,postData,function(retdata){
        // 发起异步接口请求

        //$addressUtils.updatePageView(data);
        $addressBox.removeClass('active');
        $addressUtils.showMask(false);
        getAddressList();
    },"json");


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



//事件监听器
function bindQuestionEvent(){



    var $name = $('.name'),
        $work = $('.work'),
        $addressPage = $('.address-page');
        $addressBox = $('.address-info__box'), // 地址添加编辑弹窗
        $addressBoxTitle = $('.address-info__title'), // 弹窗标题
        $userInfo = $('.user-info'),
        $address = $('.address'),
        $closeBtn = $('.close-btn'), // 关闭弹窗按钮
        $tel = $('.tel'),
        $finishAddress = $('.address-finish__btn'), // 编辑完成地址
        $addAddress = $('.add-address'), // 添加地址
        $maskBg = $('.mask-bg'),
        $toast = $('.toast'),
        $addressItem = $('.address-item'),
        $arrowBtn = $('.arrow-icon'),
        $saveBtn = $('.save-btn');




// 点击选择切换收件地址
    $addressItem.on('click', function () {
        $(this).siblings().removeClass('selected');
        $(this).addClass('selected');
    });

// 点击编辑地址详情
    $arrowBtn.on('click', function (e) {
        e.stopPropagation();
        addEditStatus = "edit";
        $curIndex = Number($(this).attr('data-index'));
        console.log($curIndex)
        console.log(addressList[$curIndex]);
        //alert($curIndex)
        //alert(JSON.stringify(addressList[$curIndex]));
        $addressUtils.updateMaskData(addressList[$curIndex]);
        $addressBoxTitle.html('编辑收件地址');
        $addressUtils.showMask(true);
        $addressBox.addClass('active');
    });

// 点击新增收件地址
// 保存成功后刷新页面
    $addAddress.on('click', function () {
        addEditStatus='add';
        $addressUtils.clearMaskData();
        $addressBoxTitle.html('添加收件地址');
        $addressUtils.showMask(true);
        $addressBox.addClass('active');
    })

    $closeBtn.on('click', function () {
        $addressUtils.showMask(false);
        $addressBox.removeClass('active');
    });


// 保存默认地址按钮
    $saveBtn.on('click', function () {

        var address_id = $(".address-item.selected").attr("address-id");

        $.post("/address/main_address",{address_id:address_id},function(data){
            $addressUtils.toast('保存成功~');
//            window.location.href = '/usercenter/editUserInfo';
            window.history.back();
        },'json');


    });


}


