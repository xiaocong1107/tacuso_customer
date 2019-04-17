var $questionPage = $(".question-page"),
	$maskBg= $('.mask-bg'), // v9.1灰色背景
	$toast = $('.toast'),   // v9.1
	$maskBox = $('.mask-box'),  // v9.1
	$start = $('.start'),  // v9.1
	$item = $('.option-item'),
	$select = $('.select'),
	$nextBtn = $('.next-btn');

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
        $select = $('.select'),
        $nextBtn = $('.next-btn'),
        $toast = $(".toast");

    $item.on('click', function () {
        $item.removeClass('selected');
        $(this).addClass('selected');
    });

    $select.on('change', function () {
        $(this).siblings('.select-value').html($(this).val());
        $(this).siblings('.select-value').attr("tag-value",$(this).val());
    });


    $nextBtn.on('click',function(e){

        var selectVerify = true;

        $(".question-box").forEach(function(item){
            var questionType = $(item).attr("question-type");
            switch (questionType){
                case "1":
                case "2":

                    var selected = $(item).children(".option-list").children(".option-item").hasClass("selected");
                    if(!selected){
                        selectVerify=false;
                    }

                    break;
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
        	
            if(nextIndex==99){
                history.pushState({"page":"/page/index?page_index="+page_id},"",window.location.href);
                window.location.href="/photo/index";
            }else{
                history.pushState({"page":"/page/index?page_index="+page_id},"",window.location.href);
                window.location.href="/page/index?page_index="+nextIndex;
            }


        });
    });

    function toast (content) {
        $toast.html(content).show(50);
        setTimeout(function() {
            $toast.hide(100);
        }, 3000);
    };

    document.body.addEventListener('touchstart', function () {});

    
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

    	//下面方法是V9.1
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

    			$item.on('click', function () {
    			  $item.removeClass('selected');
    			  $(this).addClass('selected');
    			});

    			$select.on('change', function () {
    			  $(this).siblings('.select-value').html($(this).val());
    			});


    			$start.on('click', function () {
    			  $maskBox.hide();
    			  $utils.showMask(false);
    			});

    			$utils.showMask(true);
    			$maskBox.show();

    			document.body.addEventListener('touchstart', function () {});
    	

}