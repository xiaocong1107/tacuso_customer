var $item = $('.option-item '),
    $select = $('.select'),
    $nextBtn = $('.next-btn');

var data = {};

$item.on('click', function () {
  if ($(this).hasClass('selected')) {
    $(this).removeClass('selected');
  } else {
    $(this).addClass('selected');
  }
});

$select.on('change', function () {
  // 更新选择中展示
  $(this).siblings('.select-value').html($(this).val());
});


$nextBtn.on('click',function(e){

    $.post("/page/save",{},function(data){

        window.location.href="/page/index?page_index="+nextIndex;
    });
});



document.body.addEventListener('touchstart', function () {});