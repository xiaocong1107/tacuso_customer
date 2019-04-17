var $questionPage = $(".page");
getQuestionList();

function getQuestionList(){
    $.post("/questionadmin/getUserCenterQuestions",{referer_page:"tacuso_figure_info",uid:uid},renderQuestionList,'json');
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
    $.post("/questionadmin/getUserCenterQuestionsRecord",{referer_page:"tacuso_figure_info",uid:uid},function(data){

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

                            console.log( $(document.querySelector(question_id_filter+answer_key_filter)));

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
        $(".select-value").forEach(function(item){
            if(!$(item).attr("question-id")) return false;
            if(!$(item).attr("tag-value")) return false;

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
            $toast.html('保存成功').show();
            history.pushState({"page":"/usercenteradmin/size"},"",window.location.href);

            setTimeout(function() {
                $toast.hide();
                window.location.href = '/usercenteradmin/editUserInfo';
            }, 500);
        });







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
        $(this).siblings('.select-value').attr("tag-value",$(this).val());
    });

    document.body.addEventListener('touchstart', function () {});


}