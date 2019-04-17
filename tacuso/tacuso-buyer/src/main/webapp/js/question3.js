var $item = $('.option-item'),
    $nextBtn = $('.next-btn');

$item.on('click', function () {
  if ($(this).hasClass('selected')) {
    $(this).removeClass('selected');
  } else {
    $(this).addClass('selected');
  }
});

$nextBtn.on('click',function(e){

    $.post("/page/save",{},function(data){

        window.location.href="/usercenter/habits";

    });
});



document.body.addEventListener('touchstart', function () {});