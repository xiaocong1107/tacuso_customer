var $optionItem = $('.option-item'),
    $colorItem = $('.select-box'),
    $nav = $('.nav'),
    $saveBtn = $('.next-btn'),
    $toast = $('.toast');

$nav.on('click', function() {
  $nav.removeClass('active');
  $(this).addClass('active');
  document.querySelector('.page').scrollTop = document.querySelector('#box' + $(this).attr('data-index')).offsetTop - 40;
});
    
// 普通类型选择
$optionItem.on('click', function () {
  if ($(this).parent().attr('data-type') === 'single') {
    $(this).siblings().removeClass('selected');
    $(this).addClass('selected');
  } else {
    if ($(this).hasClass('selected')) {
      $(this).removeClass('selected');
    } else {
        $(this).addClass('selected');
    }
  }
});

// 颜色类型选择
$colorItem.on('click', function () {
  $(this).siblings().removeClass('selected');
  if ($(this).hasClass('selected')) {
    $(this).removeClass('selected');
  } else {
    $(this).addClass('selected');
  }
});

var $style = {
		
	/**
	* 获取表单数据
	*/
	getFormData() {
		var work_style_array = new Array();
		var life_style_array = new Array();
		var shirt_style_array = new Array();
		var trousers_style_array = new Array();			
		var color_favor_array = new Array();
		var disgust_style_array = new Array();
		
		
		$(".selected#work_style").each(function() {
			work_style_array.push($(this).attr("name"));			
		});
		$(".selected#life_style").each(function() {
			life_style_array.push($(this).attr("name"));			
		});
		$(".selected#shirt_style").each(function() {
			shirt_style_array.push($(this).attr("name"));			
		});
		$(".selected#trousers_style").each(function() {
			trousers_style_array.push($(this).attr("name"));			
		});
		$(".selected#color_favor").each(function() {
			color_favor_array.push($(this).attr("name"));			
		});
		$(".selected#disgust_style").each(function() {
			disgust_style_array.push($(this).attr("name"));			
		});
		
		
		var work_style = JSON.stringify(work_style_array);
		var life_style = JSON.stringify(life_style_array);
		var shirt_style = JSON.stringify(shirt_style_array);
		var trousers_style = JSON.stringify(trousers_style_array);
		var color_favor = JSON.stringify(color_favor_array);
		var disgust_style = JSON.stringify(disgust_style_array);
		
		return {
			work_style,
			life_style,
			shirt_style,
			trousers_style,
			color_favor,
			disgust_style
		};
	}
		

		
};

$saveBtn.on('click', function () {
	var params = $style.getFormData();
	$.post('/usercenter/style/update', params, function(result) {

		if(result.code == "200"){
			
//			window.location.href = '/usercenter/editUserInfo';
		}
		
		$toast.html('保存成功').show();
		setTimeout(function() {
			$toast.hide();
		}, 500);
		
	},"json");
	
  
});

var box1Top = document.querySelector('#box1').offsetTop,
box2Top = document.querySelector('#box2').offsetTop,
box3Top = document.querySelector('#box3').offsetTop,
box4Top = document.querySelector('#box4').offsetTop;

document.querySelector('.page').addEventListener('scroll', function () {
 var top = document.querySelector('.page').scrollTop;
 $nav.removeClass('active');
 if (top < box2Top - 200) {
  $('.nav-1').addClass('active');
 } else if ((top >= box2Top - 200) && (top <= box3Top - 200)) {
  $('.nav-2').addClass('active');
 } else if ((top >= box3Top - 200) && (top <= box4Top - 200)) {
  $('.nav-3').addClass('active');
 } else {
   $('.nav-4').addClass('active');
 }
});

document.body.addEventListener('touchstart', function () {});