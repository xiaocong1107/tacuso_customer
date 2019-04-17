var $questionPage = $(".page")

getQuestionList();

function getQuestionList(){
    $.post("/question/getUserCenterQuestions",{referer_page:"tacuso_clothing_info"},renderQuestionList,'json');
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
            console.log(item);
            switch (item.question_type){
                case 1:
                    quetionStr =  template("ChoiceQuestion",{question:item});
                    break;
                case 2:
                    quetionStr =  template("multiChoiceQuestion",{question:item});
                    break;
                case 3:
                    quetionStr =  template("RecommendQuestion",{question:item});
                    break;
                case 4:
                    quetionStr =  template("tableChoiceQuestion",{question:item});
                    break;
                case 5:
                    quetionStr =  template("extendQuestion",{question:item});
                    break;
                case 6:

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


    $.post("/question/getUserCenterQuestionsRecord",{referer_page:"tacuso_clothing_info"},function(data){

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

function bindQuestionEvent() {
    var $optionItem = $('.option-item'),
        $colorItem = $('.select-box'),
        $nav = $('.nav'),
        $saveBtn = $('.next-btn'),
        $toast = $('.toast'),
        $multiChoiceQuestion = $(".RecommendQuestion");

    $nav.on('click', function() {
        $nav.removeClass('active');
        $(this).addClass('active');
        document.querySelector('.page').scrollTop = document.querySelector('#box' + $(this).attr('data-index')).offsetTop - 60;
    });

//    $multiChoiceQuestion.removeClass("square");

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

    $saveBtn.on('click', function () {


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
            };
            console.log(answer_select_option);
            answer_select_list.push(answer_select_option);
        });
        $(".base-box.selected").forEach(function(item){
            if(!$(item).attr("question-id")) return false;
            var answer_select_option = {
                question_id: $(item).attr("question-id"),
                question_type: $(item).attr("question-type"),
                tag_type: $(item).attr("tag-type"),
                answer_tag_id:$(item).attr("tag-id"),
                tag_table:$(item).attr("tag-table"),
                answer_key :$(item).attr("tag-key"),
                answer_value :$(item).attr("tag-value")
            };
            console.log(answer_select_option);
            answer_select_list.push(answer_select_option);
        });
        $(".suggest-item.active").forEach(function(item){
            if(!$(item).attr("question-id")) return false;
            var answer_select_option = {
                    question_id: $(item).attr("question-id"),
                    question_type: $(item).attr("question-type"),
                    tag_type: $(item).attr("tag-type"),
                    answer_tag_id:$(item).attr("tag-id"),
                    tag_table:$(item).attr("tag-table"),
                    answer_key :$(item).attr("tag-key"),
                    answer_value :$(item).attr("tag-value"),
                    is_recommend :is_recommend
            }
            answer_select_list.push(answer_select_option);
        });
        console.log(answer_select_list);

        $.post("/page/save",{"answerList":JSON.stringify(answer_select_list)},function(data){
            $toast.html('保存成功').show();
//            history.pushState({"page":"usercenter/style"},document.title,window.location.href);
            window.history.go(-1);

            setTimeout(function() {
                $toast.hide();
//                window.location.href='/usercenter/editUserInfo';
                
            }, 500);
        });

    });

    var box1Top = document.querySelector('#box1').offsetTop,
        box2Top = document.querySelector('#box21').offsetTop + document.querySelector('#box22').clientHeight + document.querySelector('#box23').clientHeight,
        box3Top = document.querySelector('#box3').offsetTop,
        box4Top = document.querySelector('#box4').offsetTop;

    document.querySelector('.page').addEventListener('scroll', function () {
        var top = document.querySelector('.page').scrollTop;
        $nav.removeClass('active');
        if (top < document.querySelector('#box21').offsetTop - 200) {
            $('.nav-1').addClass('active');
        } else if ((top >= document.querySelector('#box21').offsetTop - 200) && (top <= box3Top - 200)) {
            $('.nav-2').addClass('active');
        } else if ((top >= box3Top - 200) && (top <= box4Top - 200)) {
            $('.nav-3').addClass('active');
        } else {
            $('.nav-4').addClass('active');
        }
    });

    document.body.addEventListener('touchstart', function () {});
    
    //下面是v9.1
    $('.suggest-item').on('click', function () {
    	$('.suggest-item').removeClass('active');
          $(this).addClass('active');
          document.querySelector('.page').scrollTop = document.querySelector('.page').scrollTop + 1;
    	});

}