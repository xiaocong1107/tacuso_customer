<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>消费习惯</title>
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="yes" name="apple-touch-fullscreen">
    <meta name="viewport" content="width=device-width,initial-scale=1.0, maximum-scale=1.0, minimum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <script src="/js/rem.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/vouchers-2.css">
</head>
<body>
<section class="page vouchers-page">
	  <section class="page-header">
	
	  </section>

<script src="/js/template-web.js"></script>
<script src="/js/zepto.js"></script>



<script id="questionPage"  type="text/html">

    {{@allQuestionStr }}

</script>


<!-- 多选题模板 -->
<script id="multiChoiceQuestion" type="text/html">

    <section class="question-box multiChoiceQuestion {{question.question_class}}" id="{{question.attr_id_name}}" question-id="{{question.question_id}}"  question-type="{{question.question_type}}">
        <h3 class="title"> {{question.question_title}} </h3>

        <section class="option-list">
            {{each question.answerList}}
            {{set $answer_config = JSON.parse($value.answer_config); }}
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
                {{if $answer_config.title!="" && question.show_label==1}}
                <p>{{$answer_config.title}}</p>
                {{/if}}

            </div>
            {{/each}}
        </section>
    </section>

</script>

<!-- 单选题模板 -->
<script id="ChoiceQuestion" type="text/html">

    </section>

</script>

<!-- 扩展模板-->
<script id="extendQuestion" type="text/html">

    <section class="extend-input extendQuestion">
        {{each question.answerList}}
        <input type="text" class="ext_brand" placeholder="{{question.question_title}}"
               question-id="{{question.question_id}}"
               question-type="{{question.question_type}}"
               tag-type="{{$value.tag_type}}"
               tag-id="{{$value.answer_tag_id}}"
               tag-table="{{$value.tag_table}}"
               tag-key="{{$value.answer_key}}"
               tag-value=""
        >
        {{/each}}
    </section>

</script>



<!-- 标签选择题 -->
<script id="tagQuestion" type="text/html">

    <section class="habit-box" id="{{question.attr_id_name}}"  question-id="{{question.question_id}}" question-type="{{question.question_type}}">
        <h3 class="title"> {{question.question_title}} </h3>
        <section class="evaluation-box">

            {{each question.answerList $answerItem $answerIndex}}
            {{set $answer_config = JSON.parse($answerItem.answer_config); }}
            <section class="evaluation-item">
                <div class="about">{{$answer_config.title}}</div>
                <!-- 点击选中时添加 on -->
                <div class="range-list">
                    {{each $answer_config.select_option $optionItem $optionIndex}}
                        <div class="range-item"
                             question-id="{{question.question_id}}"
                             question-type="{{question.question_type}}"
                             tag-type="{{$answerItem.tag_type}}"
                             tag-id="{{$answerItem.answer_tag_id}}"
                             tag-table="{{$answerItem.tag_table}}"
                             tag-key="{{$answerItem.answer_key}}"
                             tag-value="{{$optionItem}}"
                        >{{$optionItem}}</div>
                    {{/each}}
                </div>
            </section>


            {{/each}}

        </section>
    </section>


</script>
</section>
<div class="toast"></div>
    <section class="next-btn">下一步</section>
<script src="/js/vouchers-2.js"></script>
</body>
</html>