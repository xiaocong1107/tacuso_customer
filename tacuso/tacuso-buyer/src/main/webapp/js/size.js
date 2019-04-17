var $optionItem = $('.option-item'),
    $nav = $('.nav'),
    $select = $('.select'),
    $saveBtn = $('.next-btn'),
    $toast = $('.toast');


$nav.on('click', function() {
  $nav.removeClass('active');
  $(this).addClass('active');
  document.querySelector('.page').scrollTop = document.querySelector('#box' + $(this).attr('data-index')).offsetTop - 40;
});

$optionItem.on('click', function () {
  $(this).siblings().removeClass('selected');
  $(this).addClass('selected');
});

$saveBtn.on('click', function () {
  $toast.html('保存成功').show();
  setTimeout(function() {
    $toast.hide();
    window.location.href = '/usercenter/editUserInfo';
  }, 500);
});


var box1Top = document.querySelector('#box1').offsetTop,
box2Top = document.querySelector('#box2').offsetTop,
box3Top = document.querySelector('#box3').offsetTop;

document.querySelector('.page').addEventListener('scroll', function () {
 var top = document.querySelector('.page').scrollTop;
 $nav.removeClass('active');
 if (top < box2Top - 200) {
  $('.nav-1').addClass('active');
 } else if ((top >= box2Top - 200) && (top <= box3Top - 200)) {
  $('.nav-2').addClass('active');
 } else {
   $('.nav-3').addClass('active');
 }
});

$select.on('change', function () {
  $(this).siblings('.select-value').html($(this).val());
});

document.body.addEventListener('touchstart', function () {});