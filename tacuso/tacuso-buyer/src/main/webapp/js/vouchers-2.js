var $questionPage = $(".page")

getQuestionList();

function getQuestionList(){
    $.post("/question/getUserCenterQuestions",{referer_page:"tacuso_consume_info"},renderQuestionList,'json');
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
                    quetionStr =  template("selectChoiceQuestion",{question:item});
                    break;
                case 4:
                    quetionStr =  template("tableChoiceQuestion",{question:item});
                    break;
                case 5:
                    quetionStr =  template("extendQuestion",{question:item});
                    break;
                case 6:
                    quetionStr =  template("tagQuestion",{question:item});
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


    $.post("/question/getUserCenterQuestionsRecord",{referer_page:"tacuso_consume_info"},function(data){

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
                            $(document.querySelector(question_id_filter+answer_key_filter))
                                .attr("tag-value",selected_answer_data.answer_value)
                                .html(selected_answer_data.answer_value);
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
        console.log($(this));
        $nav.removeClass('active');

        $(this).addClass('active');
        document.querySelector('.page').scrollTop = document.querySelector('#box' + $(this).attr('data-index')).offsetTop - 40;
    });

    //扩展问题
    $(".ext_brand").on('change',function(){
        $(this).attr("tag-value",$(this).val());
    });

    $saveBtn.on('click', function () {
    	
        var selectVerify = true;

        $(".evaluation-item").forEach(function(item){
            var selected = $(item).children(".range-list").children(".range-item").hasClass("selected");
            console.log(selected)
            if(!selected){
                selectVerify=false;
            }

        });
        
        $(".question-box").forEach(function(item){

            var selected = $(item).children(".option-list").children(".option-item").hasClass("selected");
            console.log(selected)
            if(!selected){
                selectVerify=false;
            }

        });


        if(selectVerify==false){
            toast("请继续完善你的资料哦");
            return;
        }

    	

        var answer_select_list = [];
        $(".range-item.selected").forEach(function(item){
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


        $(".ext_brand").forEach(function(item){
            if(!$(item).attr("question-id")) return false;
            if($(item).attr("tag-value")!=""){
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
            }
        });

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
        $.post("/page/save",{"answerList":JSON.stringify(answer_select_list)},function(data){
            $toast.html('已保存').show();
            history.pushState({"page":"/usercenter/habits"},"",window.location.href);

            setTimeout(function() {
                $toast.hide();
                window.location.href = '/photo/index';
            }, 500);
        });


    });

    var box1Top = document.querySelector('#box1').offsetTop,
        box2Top = document.querySelector('#box2').offsetTop;

    document.querySelector('.page').addEventListener('scroll', function () {
        var top = document.querySelector('.page').scrollTop;
        $nav.removeClass('active');
        if (top < box2Top - 150) {
            $('.nav-1').addClass('active');
        } else {
            $('.nav-2').addClass('active');
        }
    });

    document.body.addEventListener('touchstart', function () {});

    function toast (content) {
        $toast.html(content).show(50);
        setTimeout(function() {
            $toast.hide(100);
        }, 3000);
    };

}