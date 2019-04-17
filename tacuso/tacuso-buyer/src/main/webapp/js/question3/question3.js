var $questionPage = $(".vouchers-page"),
$maskBg= $('.mask-bg'), // V9.1
$maskBox = $('.mask-box'),// V9.1
$start = $('.start'),// V9.1
$toast = $('.toast');// V9.1

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
            console.log(item);
            switch (item.question_type){
                case 1:
                    quetionStr =  template("ChoiceQuestion",{question:item});
                    break;
                case 2:
                    quetionStr =  template("multiChoiceQuestion",{question:item});
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
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

    var $item = $('.option-item'),
        $nextBtn = $('.next-btn'),
        $toast = $(".toast");

    $item.on('click', function () {
        if ($(this).hasClass('selected')) {
            $(this).removeClass('selected');
        } else {
            $(this).addClass('selected');
        }
    });
    $nextBtn.on('click',function(e){

        var selectVerify = true;

        $(".style-box").forEach(function(item){

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
        //扩展字段
        toast("已保存");

        $.post("/page/save",{"answerList":JSON.stringify(answer_select_list)},function(data){
            history.pushState({"page":"/page/index?page_index="+page_id},"",window.location.href);
//            window.location.href="/usercenter/habits";
            window.location.href="/page/vouchers2";
        });
    });

    function toast (content) {
        $toast.html(content).show(50);
        setTimeout(function() {
            $toast.hide(100);
        }, 3000);
    };


    

    var $utils = {
    showMask (show) {
      show === true ? $maskBg.addClass('active') : $maskBg.removeClass('active');
    },

    toast (content) {
      $toast.html(content).show(50);
      setTimeout(function() {
        $toast.hide(100);
      }, 3000);
    },
  };

  // v9.1普通类型选择


  $start.on('click', function () {
    $maskBox.hide();
    $utils.showMask(false);
  });

  $utils.showMask(true);
  $maskBox.show();

  document.body.addEventListener('touchstart', function () {});


}