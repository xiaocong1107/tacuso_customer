package com.tacuso.buyer.service.impl;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.tacuso.buyer.constants.RedisKey;
import com.tacuso.buyer.dao.QuestionMapper;
import com.tacuso.buyer.entity.Answer;
import com.tacuso.buyer.entity.ClothingInfo;
import com.tacuso.buyer.entity.ConsumeInfo;
import com.tacuso.buyer.entity.FigureInfo;
import com.tacuso.buyer.entity.Question;
import com.tacuso.buyer.service.ClothingInfoService;
import com.tacuso.buyer.service.ConsumeInfoService;
import com.tacuso.buyer.service.FigureInfoService;
import com.tacuso.buyer.service.QuestionService;
import com.tacuso.buyer.vo.AnswerTagVo;
import com.tacuso.buyer.vo.QuestionVo;
import com.xiaoleilu.hutool.util.ClassUtil;

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper,Question> implements QuestionService {

    public static final Logger logger =  LoggerFactory.getLogger(QuestionService.class);

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private FigureInfoService figureInfoService;

    @Autowired
    private ConsumeInfoService consumeInfoService;

    @Autowired
    private ClothingInfoService clothingInfoService;

    @SuppressWarnings("rawtypes")
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<QuestionVo> getQuestionByPageId(Integer pageId) {

        return questionMapper.getQuestionByPageId(pageId);
    }

    @Override
    public List<Question> getAllQuestionByPageId(Integer pageId) {

        return questionMapper.getSimpleQuestionByPageId(pageId);

    }

    @Override
    public Integer saveAnswerTag(Integer uid, Integer wx_uid , List<AnswerTagVo> answerTagVoList) {

        //先进行表格的转换
        Multimap<String,AnswerTagVo> answerTagVoMultimap = ArrayListMultimap.create();
        Multimap<Integer,AnswerTagVo> questionid_answerTagMultimap =  ArrayListMultimap.create();
        
        System.out.println("answerTagVoList==="+answerTagVoList);
        for (Iterator<AnswerTagVo> iterator = answerTagVoList.iterator(); iterator.hasNext();) {
            AnswerTagVo answerTagVo = iterator.next();

            logger.info("answerTagVo : {}" , answerTagVo.toString());


            if(answerTagVo.getTag_type() == 1){

                answerTagVoMultimap.put(answerTagVo.getTag_table(),answerTagVo);

                questionid_answerTagMultimap.put(answerTagVo.getQuestion_id(),answerTagVo);

            }
        }

        logger.info("answerTagVoMultimap: {}" , JSON.toJSONString(answerTagVoMultimap));
        //遍历multiMap
        for(Map.Entry<String, Collection<AnswerTagVo>> answerTagVoEntry : answerTagVoMultimap.asMap().entrySet()){

            String tableName =  answerTagVoEntry.getKey();
            Collection<AnswerTagVo> answerTagVoCollection = answerTagVoEntry.getValue();

            //同一个表
            //遍历表字段
            Multimap<String,String> columnMultimap = HashMultimap.create();

            for(AnswerTagVo answerTagVo: answerTagVoCollection){
                columnMultimap.put(answerTagVo.getAnswer_key(),answerTagVo.getAnswer_value());
            }

            //获取该表的对应的数据
            Set<String> columnKeySet =   columnMultimap.keySet();

            for(String columnName : columnKeySet){

                Collection<String> columnValueSet = columnMultimap.get(columnName);

                logger.info("Collection : {}" , JSON.toJSONString(columnValueSet));

                //开始写数据
                switch(tableName){
                    case "tacuso_figure_info":

                        try {
                            FigureInfo figureInfo = new FigureInfo();
                            figureInfo.setUid(uid);
                            figureInfo.setWx_uid(wx_uid);
                            logger.info("MethodName : {}" , "set"+ StringUtils.capitalize(columnName));
                            Method m = ClassUtil.getPublicMethod(FigureInfo.class,"set"+ StringUtils.capitalize(columnName),String.class);
                            m.invoke(figureInfo, JSON.toJSONString(columnValueSet));
                            figureInfo = figureInfoService.createOrUpdateFigureInfo(figureInfo);

                            logger.info("FigureInfo : {}" , JSON.toJSONString(figureInfo));
                        } catch (Exception e) {
                                e.printStackTrace();
                        }

                        break;
                    case "tacuso_consume_info":

                        try {
                            ConsumeInfo consumeInfo = new ConsumeInfo();
                            consumeInfo.setUid(uid);
                            consumeInfo.setWx_uid(wx_uid);
                            logger.info("MethodName : {}" , "set"+ StringUtils.capitalize(columnName));
                            Method m = ClassUtil.getPublicMethod(ConsumeInfo.class,"set"+ StringUtils.capitalize(columnName),String.class);
                            m.invoke(consumeInfo, JSON.toJSONString(columnValueSet));
                            consumeInfo = consumeInfoService.createOrUpdateConsumeinfo(consumeInfo);

                            logger.info("ConsumeInfo : {}" , JSON.toJSONString(consumeInfo));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;
                    case "tacuso_clothing_info":

                        try {
                            ClothingInfo clothingInfo = new ClothingInfo();
                            clothingInfo.setUid(uid);
                            clothingInfo.setWx_uid(wx_uid);
                            logger.info("MethodName : {}" , "set"+ StringUtils.capitalize(columnName));
                            Method m = ClassUtil.getPublicMethod(ClothingInfo.class,"set"+ StringUtils.capitalize(columnName),String.class);
                            m.invoke(clothingInfo, JSON.toJSONString(columnValueSet));
                            clothingInfo = clothingInfoService.createOrUpdateClothingInfo(clothingInfo);

                            logger.info("ClothingInfo : {}" , JSON.toJSONString(clothingInfo));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;
                    default:
                        logger.info("找不到方法 : {}" , "");
                        break;

                }

            }
        }


        logger.info("questionid_answerTagMultimap: {}" , JSON.toJSONString(questionid_answerTagMultimap));

        //记录保存到Redis
        for(Map.Entry<Integer, Collection<AnswerTagVo>> answerTagVoEntry : questionid_answerTagMultimap.asMap().entrySet()){

            Integer question_id =  answerTagVoEntry.getKey();
            Collection<AnswerTagVo> answerTagVoCollection = answerTagVoEntry.getValue();
System.out.println("answerTagVoCollection======"+answerTagVoCollection);
            saveRecordToRedis(uid,question_id,JSON.toJSONString(answerTagVoCollection));

        }



        return 0;
    }


    private void saveRecordToRedis(Integer uid ,Integer question_id , String question_record){

        String redisKey = RedisKey.QUESTION_RECORD+"_"+uid;
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        hashOperations.put(redisKey,String.valueOf(question_id),question_record);
    }

    @Override
    public List getRecordFromRedis(Integer uid ,Collection<Object> questionIdList){
        String redisKey = RedisKey.QUESTION_RECORD+"_"+uid;
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        return  hashOperations.multiGet(redisKey,questionIdList);
    }

    @Override
    public List<QuestionVo> getQuestionByReferer(String referer) {
        return questionMapper.getQuestionByReferer(referer);
    }
    
    @Override
    public List<QuestionVo> getQuestionByRefererAtStyle(String referer) {
        return questionMapper.getQuestionByRefererAtStyle(referer);
    }

    @Override
    public List<Question> getAllQuestionByReferer(String referer) {
        return questionMapper.getSimpleQuestionByReferer(referer);
    }
    
    @Override
    public List<Answer> getAllAnswerByQuestionId(Integer questionId) {
        return questionMapper.getAllAnswerByQuestionId(questionId);
    }
}
