var $item = $('.option-item'),
    $closeBtn =  $('.mask-box__btn, .close-btn');



$item.on('click', function (e) {
  e.stopPropagation();
  //  单选
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

$closeBtn.on('click', function () {
  $('.pay-success__box').hide();
  $('.mask-bg').removeClass('active');
});

document.body.addEventListener('touchstart', function () {});

$('.mask-bg').addClass('active');

$('.next-btn').on('click',function(e){

  $.post("/page/save",{},function(data){

      window.location.href="/page/index?page_id=2";

  });


});

















