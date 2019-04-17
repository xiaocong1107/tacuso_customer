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
        <link rel="stylesheet" type="text/css" href="/css/vouchers-1.css">
    </head>
<body>
  <section class="page vouchers-page">
    <section class="page-header">
    </section>

  <script>
      var pageList = '${pageList}';
      var nextIndex = ${nextIndex};
      var page_id = ${page_id};
  </script>
  <script src="/js/template-web.js"></script>
  <script src="/js/zepto.js"></script>

  <script id="questionPage"  type="text/html">

      {{@allQuestionStr }}

      <section class="next-btn">下一步</section>
  </script>

  <!-- 多选题模板 -->
  <script id="multiChoiceQuestion" type="text/html">

      <section class="style-box {{question.question_class}}" question-id="{{question.question_id}}"  question-type="{{question.question_type}}">
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
                  {{if $answer_config.title!=""}}
                    <p>{{$answer_config.title}}</p>
                  {{/if}}

              </div>
              {{/each}}
          </section>
      </section>

  </script>

  <!-- 单选题模板 -->
  <script id="ChoiceQuestion" type="text/html">
      <section class="style-box  {{question.question_class}}" question-id="{{question.question_id}}"  question-type="{{question.question_type}}">
          {{if question.title_hidden==0}} <h3 class="title">{{question.question_title}}</h3> {{/if}}
          {{if question.show_label == 1}}
          <div class="sub-title">
              <span> {{question.label}} </span>
          </div>
          {{/if}}
          <section class="option-list" data-type="single">
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
      </section>
  </script>


  <!-- 表格选择题 -->
  <script id="tableChoiceQuestion" type="text/html">

      <section class="style-box "  question-id="{{question.question_id}}"  question-type="{{question.question_type}}">
          <h3 class="title">{{question.question_title}}</h3>
          <div class="sub-titles">

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

  </section>

  <section class="mask-box">
    <section class="pop-logo"></section>
    <h3 class="mask-title">您对衣服有特殊要求吗?</h3>
    <p class="mask-desc">请告诉我们更多信息，完成后立即体验首次服务</p>
    <div class="line"></div>
    <div class="start">
      <div class="start-title">继续</div>
      <div class="start-icon"></div>
    </div>
  </section>
    <div class="toast"></div>
  <div class="mask-bg"></div>
  <script src="/js/question3/question3.js"></script>
  
</body>
</html>