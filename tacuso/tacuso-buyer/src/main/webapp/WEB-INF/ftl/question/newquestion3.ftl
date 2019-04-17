<!DOCTYPE HTML>
<html>
    <head>
        <meta charset="UTF-8">
        <title>创建个人档案</title>
        <meta content="yes" name="apple-mobile-web-app-capable">
        <meta content="yes" name="apple-touch-fullscreen">
        <meta name="viewport" content="width=device-width,initial-scale=1.0, maximum-scale=1.0, minimum-scale=1, user-scalable=no">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <meta name="format-detection" content="telephone=no">
        <script src="/js/rem.js"></script>
        <link rel="stylesheet" type="text/css" href="/css/new-question-3.css">
    </head>
<body>

<section class="supplement-page">
	  <section class="question-page">
	    <section class="page-header">

	    </section>
	  </section>
	
	  <script>
	      var pageList = '${pageList}';
	      var nextIndex = ${nextIndex};
	      var page_id = ${page_id};
	  </script>
  <script src="/js/template-web.js"></script>
  <script src="/js/zepto.js"></script>


  <script id="questionPage"  type="text/html">
      <section class="page-header">
              <div class="progress">已完成<em>3</em>/5</div>
      </section>
      
       <section class="page-desc">
	     根据你的穿衣风格,可搭配出最适合你的款式
	    </section>
	   <div class="line"></div>
		    
      {{@allQuestionStr }}

  </script>

  <!-- 多选题模板 -->
  <script id="multiChoiceQuestion" type="text/html">

      <section class="question-box {{question.question_class}}" question-id="{{question.question_id}}"  question-type="{{question.question_type}}">
          <h3 class="title"> {{question.question_title}} </h3>

          <section class="option-list">
              {{each question.answerList}}
                  {{set $answer_config = JSON.parse($value.answer_config); }}
                  <p class="option-title">{{$answer_config.title}}</p>
                  <div class="option-item"
                       question-id="{{question.question_id}}"
                       question-type="{{question.question_type}}"
                       tag-type="{{$value.tag_type}}"
                       tag-id="{{$value.answer_tag_id}}"
                       tag-table="{{$value.tag_table}}"
                       tag-key="{{$value.answer_key}}"
                       tag-value="{{$answer_config.title}}">

                      {{if $answer_config.pic_url!="" && $answer_config.pic_url && $answer_config.pic_url!=undefined }}
                            <img src="{{$answer_config.pic_url}}" alt="" data-index="{{$index}}" class="tag-choice-item">
                      {{/if}}

                  </div>
              {{/each}}
          </section>
      </section>

  </script>

  
  
   <!-- 单选题模板 -->
  <script id="RecommendQuestion" type="text/html">

    	<section class="suggest-box">
          <h3 class="title"> {{question.question_title}} </h3>

          <section class="suggest-list">
              {{each question.answerList}}
                  {{set $answer_config = JSON.parse($value.answer_config); }}
                  
                  
                         <div  class="suggest-item" 
		                       question-id="{{question.question_id}}"
		                       question-type="{{question.question_type}}"
		                       tag-type="{{$value.tag_type}}"
		                       tag-id="{{$value.answer_tag_id}}"
		                       tag-table="{{$value.tag_table}}"
		                       tag-key="{{$value.answer_key}}"
		                       is_recommend="{{$answer_config.title}}"
		                       tag-value="{{$answer_config.title}}"
		                       >
		                       
					          <div class="picker-icon"></div>
					          <div class="about">{{$answer_config.title}}</div>
					    </div>
                  
              {{/each}}
          </section>
      </section>


  </script>
  
  </section>
  	  <div class="toast"></div>   
        <section class="next-btn">下一步</section>

  <script src="/js/newquestion/new-question-3.js"></script>




</body>
</html>