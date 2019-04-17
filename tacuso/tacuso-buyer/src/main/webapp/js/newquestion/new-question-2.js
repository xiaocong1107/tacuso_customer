var $questionPage = $(".question-page")

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
                    quetionStr =  template("ChoiceQuestion",{question:item});
                    break;
                case 2:
                    quetionStr =  template("multiChoiceQuestion",{question:item});
                    break;
                case 3:
                    quetionStr =  template("selectChoiceQuestion",{question:item});
                    break;
                case 4:
                    quetionStr =  template("ChoiceQuestion",{question:item});
                    break;
                case 5:
                    quetionStr =  template("extendQuestion",{question:item});
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


/**
 * 事件监听
 */
function bindQuestionEvent(){
    var $item = $('.option-item '),
        $select = $('.select'),
        $nextBtn = $('.next-btn'),
        $toast = $(".toast");
    var data = {};

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

    
    $select.on('change', function () {
        // 更新选择中展示
        $(this).siblings('.select-value').html($(this).val());
        $(this).siblings('.select-value').attr("tag-value",$(this).val());

    });

    //扩展问题
    $(".ext_brand").on('change',function(){
        $(this).attr("tag-value",$(this).val());
    });

    $nextBtn.on('click',function(e){

        var selectVerify = true;

        var $ext_brand = $(".ext_brand");
        var ext = false;
        if($ext_brand.val()!=""){
            ext=true;
        }


        $(".question-box").forEach(function(item){
            var questionType = $(item).attr("question-type");
            switch (questionType){
                case "1":
                    if(!ext){
                        var selected = $(item).children(".option-list").children(".option-item").hasClass("selected");

                        if(!selected){
                            selectVerify=false;
                        }
                    }
                    break;
                case "2":
                case "3":
                    var selectItem = $(item).children(".form-box").children(".input-item");
                    selectItem.each(function(index,sitem){
                        var tagValue = $(sitem).find(".select-value").attr("tag-value")
                        if(tagValue==""){
                            selectVerify=false;
                        }
                        return selectVerify;
                    });

                    break;
            }

        });

        if(selectVerify==false){
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



        $.post("/page/save",{"answerList":JSON.stringify(answer_select_list)},function(data){
            history.pushState({"page":"/page/index?page_index="+page_id},"",window.location.href);
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
}