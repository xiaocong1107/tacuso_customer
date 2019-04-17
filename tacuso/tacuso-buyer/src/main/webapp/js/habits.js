var $rangeItem = $('.range-item'),
    $optionItem = $('.option-item'),
    $nav = $('.nav'),
    $saveBtn = $('.next-btn'),
    $toast = $('.toast');

$rangeItem.on('click', function () {
  $(this).siblings().removeClass('selected');
  if ($(this).hasClass('selected')) {
    $(this).removeClass('selected');
  } else {
    $(this).addClass('selected');
  }
});

$optionItem.on('click', function () {
  // 处理单选
  if ($(this).parent().attr('data-type') === 'single') {
    $(this).siblings().removeClass('selected');
  }
  if ($(this).hasClass('selected')) {
    $(this).removeClass('selected');
  } else {
    $(this).addClass('selected');
  }
});

$nav.on('click', function() {
  $nav.removeClass('active');
  $(this).addClass('active');
  document.querySelector('.page').scrollTop = document.querySelector('#box' + $(this).attr('data-index')).offsetTop - 40;
});

$saveBtn.on('click', function () {
  $toast.html('保存成功').show();
  setTimeout(function() {
    $toast.hide();
    window.location.href = '/usercenter/upload';
  }, 500);
});

var box1Top = document.querySelector('#box1').offsetTop,
box2Top = document.querySelector('#box2').offsetTop;

document.querySelector('.page').addEventListener('scroll', function () {
 var top = document.querySelector('.page').scrollTop;
 $nav.removeClass('active');
 if (top < box2Top - 400) {
  $('.nav-1').addClass('active');
 } else {
   $('.nav-2').addClass('active');
 }
});

document.body.addEventListener('touchstart', function () {});