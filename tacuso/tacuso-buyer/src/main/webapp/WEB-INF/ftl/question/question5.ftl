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
        <link rel="stylesheet" type="text/css" href="/css/question5.css">
    </head>
<body>
  <section class="question-page">
    <section class="page-header">
      <div class="progress">已经完成<em>0</em>/5</div>
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
  <script id="questionPage"  type="text/html">
      <section class="page-header">
          <div class="progress">已经完成<em>0</em>/5</div>
      </section>

      {{@allQuestionStr }}

      <section class="next-btn">下一步</section>
  </script>

  <!-- 下拉选择题-->
  <script id="selectChoiceQuestion" type="text/html">
      <section class="question-box" question-id="{{question.question_id}}" question-type="{{question.question_type}}">
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

      </section>

  </script>



  <!-- 多选题模板 -->
  <script id="multiChoiceQuestion" type="text/html">

      <section class="question-box {{question.question_class}}" question-id="{{question.question_id}}"  question-type="{{question.question_type}}">
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
      <section class="question-box {{question.question_class}}" question-id="{{question.question_id}}"  question-type="{{question.question_type}}">
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


  <script src="/js/question5/question5.js"></script>
</body>
</html>