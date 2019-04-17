var $optionItem = $('.option-item'),
    $baseBox = $('.base-box'),
    $nextBtn = $('.next-btn');

$baseBox.on('click', function () {
  $(this).siblings().removeClass('selected');
  $(this).addClass('selected');
});


$nextBtn.on('click',function(e){

    $.post("/page/save",{},function(data){

        window.location.href="/page/index?page_id=3";

    });
});






document.body.addEventListener('touchstart', function () {});