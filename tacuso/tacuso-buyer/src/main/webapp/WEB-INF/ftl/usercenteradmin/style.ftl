<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <title>着装偏好</title>
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="yes" name="apple-touch-fullscreen">
    <meta name="viewport" content="width=device-width,initial-scale=1.0, maximum-scale=1.0, minimum-scale=1, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <script src="/js/rem.js"></script>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
<section class="page style-page">
    <section class="page-header">
        <a class="nav nav-1 active" data-index='1'>风格偏好<br><em>スタイルの設定</em></a>/
        <a class="nav nav-2" data-index='2'>版型偏好<br><em>スタイルの設定</em></a>/
        <a class="nav nav-3" data-index='3'>颜色偏好<br><em>スタイルの設定</em></a>/
        <a class="nav nav-4" data-index='4'>款式偏好<br><em>スタイルの設定</em></a>
    </section>




 
</section>
<div class="toast"></div>
<script src="/js/template-web.js"></script>
<script src="/js/zepto.js"></script>



<script id="questionPage"  type="text/html">
    <section class="page-header">
        <a class="nav nav-1 active" data-index='1'>风格偏好<br><em>スタイルの設定</em></a>/
        <a class="nav nav-2" data-index='2'>版型偏好<br><em>スタイルの設定</em></a>/
        <a class="nav nav-3" data-index='3'>颜色偏好<br><em>スタイルの設定</em></a>/
        <a class="nav nav-4" data-index='4'>款式偏好<br><em>スタイルの設定</em></a>
    </section>

    {{@allQuestionStr }}

</script>

<!-- 表格选择题 -->
<script id="tableChoiceQuestion" type="text/html">


    <a class="color-box tableChoiceQuestion" id="{{question.attr_id_name}}"  question-id="{{question.question_id}}"  question-type="{{question.question_type}}">
        <h3 class="title">{{question.question_title}}</h3>
        <div class="sub-titles ">

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
    </a>

</script>

<!-- 多选题模板 -->
<script id="multiChoiceQuestion" type="text/html">

    <a class="style-box multiChoiceQuestion {{question.question_class}}" id="{{question.attr_id_name}}" question-id="{{question.question_id}}"  question-type="{{question.question_type}}">
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
    </a>

</script>

<!-- 单选题模板 -->
<script id="ChoiceQuestion" type="text/html">

    <a class="style-box ChoiceQuestion square {{question.question_class}}" id="{{question.attr_id_name}}" question-id="{{question.question_id}}"  question-type="{{question.question_type}}">
        {{if question.title_hidden==0}} <h3 class="title">{{question.question_title}}</h3> {{/if}}
        {{if question.show_label == 1}}
        <div class="sub-title">
            <span> {{question.label}} </span>
        </div>
        {{/if}}
        <section class="option-list square" data-type="single">
            {{each question.answerList}}
            {{set $answer_config = JSON.parse($value.answer_config); }}
            <div class="option-item "
                 question-id="{{question.question_id}}"
                 question-type="{{question.question_type}}"
                 tag-type="{{$value.tag_type}}"
                 tag-id="{{$value.answer_tag_id}}"
                 tag-table="{{$value.tag_table}}"
                 tag-key="{{$value.answer_key}}"
                 tag-value="{{$answer_config.title}}">

                <img src="{{$answer_config.pic_url}}" alt="" data-index="{{$index}}">
                <p>{{$answer_config.title}}</p>
            </div>
            {{/each}}

        </section>
    </a>

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

<!-- 下拉选择题-->
<script id="selectChoiceQuestion" type="text/html">
    <a class="style-box selectChoiceQuestion" id="{{question.attr_id_name}}"  question-id="{{question.question_id}}" question-type="{{question.question_type}}">
        <h3 class="title"> {{question.question_title}} </h3>

        <section class="form-box">
            {{each question.answerList $answerItem $answerIndex}}
            {{set $answer_config = JSON.parse($answerItem.answer_config); }}
            <section class="input-item">
                <div class="icon {{$answer_config.icon}}"></div>
                <div class="label">{{$answer_config.title}}</div>
                <div class="base-input ">
                          <span class="select-value"
                                question-id="{{question.question_id}}"
                                question-type="{{question.question_type}}"
                                tag-type="{{$answerItem.tag_type}}"
                                tag-id="{{$answerItem.answer_tag_id}}"
                                tag-table="{{$answerItem.tag_table}}"
                                tag-key="{{$answerItem.answer_key}}"
                                tag-value=""
                          ></span>
                    <select id="" class="select">
                        <option value=""></option>
                        {{each $answer_config.select_option $optionItem $optionIndex}}
                        <option value="{{$optionItem}}">{{$optionItem}}</option>
                        {{/each}}
                    </select>
                </div>
            </section>
            {{/each}}

        </section>

    </a>

</script>

<!-- 标签选择题 -->


<script>
var uid=${userId};
</script>



<script src="/js/admin/style/style.js"></script>
</body>
</html>