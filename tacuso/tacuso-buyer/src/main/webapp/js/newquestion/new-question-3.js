
var $questionPage = $(".question-page");

getQuestionList();


function getQuestionList(){

    $.post("/question/getQuestions",{page_id:page_id},renderQuestionList,'json');

}

function renderQuestionList(retdata){
    template.defaults.debug=true
    template.defaults.imports.JSON = JSON;
    if(retdata.code=="200"){
        var data = retdata.data;
        var quetionStr="";
        var allQuestionStr="";
        var pageStr="";
        data.forEach(function(item,index){
            switch (item.question_type){
                case 1:
                    break;
                case 2:
                    quetionStr =  template("multiChoiceQuestion",{question:item});
                    break;
                case 3:
                    quetionStr =  template("RecommendQuestion",{question:item});
                    break;
                case 4:
                    break;
            }

            allQuestionStr+=quetionStr;

        });
        pageStr = template("questionPage",{allQuestionStr:allQuestionStr});
        $questionPage.html(pageStr);
        restoreQuestion();
        bindQuestionEvent();

    }


}

function restoreQuestion(){


    $.post("/question/getQuestionsRecord",{page_id:page_id},function(data){

            if(data.code=="200"){
                var answerList = data.data;
                answerList.forEach(function(selected_answer_List_data_str){

                    if(!selected_answer_List_data_str) return false;


                    selected_answer_List_data = JSON.parse(selected_answer_List_data_str);
                    selected_answer_List_data.forEach(function(selected_answer_data){
                        switch(selected_answer_data.question_type){
                            case 3:
                                var question_id_filter = "[question-id='"+selected_answer_data.question_id+"']";
                                var answer_key_filter = "[tag-key='"+selected_answer_data.answer_key+"']";
                                var answer_value_filter = "[tag-value='"+selected_answer_data.answer_value+"']";
                               
                                $(document.querySelector(question_id_filter+answer_key_filter+answer_value_filter))
                                    .removeClass("suggest-item").addClass("suggest-item active");
                                break;
                            case 5:
                                var question_id_filter = "[question-id='"+selected_answer_data.question_id+"']";
                                var answer_key_filter = "[tag-key='"+selected_answer_data.answer_key+"']";
                                $(document.querySelector(question_id_filter+answer_key_filter))
                                    .attr("tag-value",selected_answer_data.answer_value)
                                    .val(selected_answer_data.answer_value);
                            default:
                                var question_id_filter = "[question-id='"+selected_answer_data.question_id+"']";
                                var answer_key_filter = "[tag-key='"+selected_answer_data.answer_key+"']";
                                var answer_value_filter = "[tag-value='"+selected_answer_data.answer_value+"']";
                                $(document.querySelector(question_id_filter+answer_key_filter+answer_value_filter)).addClass("selected");
                                break;
                        }
                    });




                })
            }

    },'json');


}

function bindQuestionEvent(){

    var $item = $('.option-item'),
        $closeBtn =  $('.mask-box__btn, .close-btn'),
        $toast = $(".toast");


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

    document.body.addEventListener('touchstart', function () {});

//    $('.mask-bg').addClass('active');

    $('.next-btn').on('click',function(e){
        var selectVerify = true;

        $(".question-box").forEach(function(item){

            var selected = $(item).children(".option-list").children(".option-item").hasClass("selected");
            if(!selected){
                selectVerify=false;
            }

        });
        

        if(selectVerify==false){
            toast("请继续完善你的资料哦");
            return;
        }
        
        var is_recommend = null;
        $(".suggest-item.active").forEach(function(item){
        	is_recommend =  $(item).attr("is_recommend");//这里获取class属性值     	  
        });

        if(is_recommend==null){
            toast("请继续完善你的资料哦");
            return;
        }

        //获取选择的数据
        var answer_select_list = [];
        $(".option-item.selected").forEach(function(item){
            if(!$(item).attr("question-id")) return false;
            var answer_select_option = {
                    question_id: $(item).attr("question-id"),
                    question_type: $(item).attr("question-type"),
                    tag_type: $(item).attr("tag-type"),
                    answer_tag_id:$(item).attr("tag-id"),
                    tag_table:$(item).attr("tag-table"),
                    answer_key :$(item).attr("tag-key"),
                    answer_value :$(item).attr("tag-value")
            }
            answer_select_list.push(answer_select_option);
        });
        
        $(".suggest-item.active").forEach(function(item){
            if(!$(item).attr("question-id")) return false;
            var answer_select_active = {
                    question_id: $(item).attr("question-id"),
                    question_type: $(item).attr("question-type"),
                    tag_type: $(item).attr("tag-type"),
                    answer_tag_id:$(item).attr("tag-id"),
                    tag_table:$(item).attr("tag-table"),
                    answer_key :$(item).attr("tag-key"),
                    answer_value :$(item).attr("tag-value"),
                    is_recommend :is_recommend
            }
            answer_select_list.push(answer_select_active);
        });

//        $(".suggest-item.active").forEach(function(item){
//        	var is_recommend =  $(item).attr("is_recommend");//这里获取class属性值
//            alert(is_recommend);
//        	  answer_select_list.push(is_recommend);
//        });

        $.post("/page/save",{"answerList":JSON.stringify(answer_select_list)},function(data){
            history.pushState({"page":"/page/index?page_index="+page_id},document.title,window.location.href);
            window.location.href="/page/index?page_index="+nextIndex;

        });


    });

    function toast (content) {
        $toast.html(content).show(50);
        setTimeout(function() {
            $toast.hide(100);
        }, 3000);
    };

    document.body.addEventListener('touchstart', function () {});

    //下面是v9.1
    $('.suggest-item').on('click', function () {
    	$('.suggest-item').removeClass('active');
    	  $(this).addClass('active');
    	});



}