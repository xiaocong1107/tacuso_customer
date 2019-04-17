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
        <link rel="stylesheet" type="text/css" href="/css/new-question-4.css">
    </head>
<body>
<section class="question-page">
  <section class="page-header">
    <div class="progress">已完成<em>4</em>/5</div>
  </section>

</section>
<div class="toast"></div>

<script>
    var pageList = '${pageList}';
    var nextIndex = ${nextIndex};
    var page_id = ${page_id};
</script>
<script src="/js/template-web.js"></script>
<script src="/js/zepto.js"></script>

<!-- 页面 -->
<script id="questionPage"  type="text/html">

    <section class="page-header">
        <div class="progress">已完成<em>4</em>/5</div>
    </section>
	    <section class="page-desc">
	     	 衣服颜色众多，我们会按照你的喜好筛选
	    </section>
    <div class="line"></div>
    {{@allQuestionStr }}


</script>
<!-- 表格选择题 -->
<script id="tableChoiceQuestion" type="text/html">

    <!--  新增固定顶部 -->
    <div class="fixed-title sub-titles fhide">

        {{each question.answerList}}
            {{set $answer_config = JSON.parse($value.answer_config); }}
            <div class="sub-title">{{$answer_config.title}}</div>
            {{each $answer_config.x_axis $config_item $item_index}}
                <div class="sub-title">{{$config_item}}</div>
            {{/each}}
        {{/each}}

    </div>

    <section class="question-box"  question-id="{{question.question_id}}"  question-type="{{question.question_type}}">
      <h3 class="title">{{question.question_title}}</h3>
      <div class="sub-titles inner-titles">

          {{each question.answerList}}

                {{set $answer_config = JSON.parse($value.answer_config); }}
                    <div class="sub-title">{{$answer_config.title}}</div>
                {{each $answer_config.x_axis $config_item $item_index}}
                    <div class="sub-title">{{$config_item}}</div>
                {{/each}}

          {{/each}}
      </div>
      <section class="option-list">


            {{each question.answerList}}

                {{set $answer_config = JSON.parse($value.answer_config); }}

                {{each $answer_config.y_axis $y_config_item $y_item_index}}
                <div class="option-item">

                    <div class="base-box {{$answer_config.y_axis_class[$y_item_index]}}">{{$y_config_item}}</div>

                    {{each $answer_config.x_axis $x_config_item $x_item_index}}
                    <div class="base-box select-box"
                         question-id="{{question.question_id}}"
                         question-type="{{question.question_type}}"
                         tag-type="{{$value.tag_type}}"
                         tag-id="{{$value.answer_tag_id}}"
                         tag-table="{{$value.tag_table}}"
                         tag-key="{{$value.answer_key}}"
                         tag-value="{{$x_config_item}}{{$y_config_item}}">

                    </div>
                    {{/each}}


                </div>
                {{/each}}

            {{/each}}


      </section>
    </section>

</script>

    <section class="next-btn">下一步</section>



  <script src="/js/newquestion/new-question-4.js"></script>
</body>
</html>