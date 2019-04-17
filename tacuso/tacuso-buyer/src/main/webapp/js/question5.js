var $item = $('.option-item'),
$select = $('.select'),
$nextBtn = $('.next-btn');

$item.on('click', function () {
  $item.removeClass('selected');
  $(this).addClass('selected');
});

$select.on('change', function () {
  $(this).siblings('.select-value').html($(this).val());
});


$nextBtn.on('click',function(e){

    $.post("/page/save",{},function(data){

        if(nextIndex==99){
            window.location.href="/photo/index";
        }else{
            window.location.href="/page/index?page_index="+nextIndex;
        }


    });
});


document.body.addEventListener('touchstart', function () {});