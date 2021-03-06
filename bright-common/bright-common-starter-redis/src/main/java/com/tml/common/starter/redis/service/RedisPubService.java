package com.tml.common.starter.redis.service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.tml.common.starter.redis.entity.RedisSimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author JacksonTu
 * @version 1.0
 * @description redis消息发布服务
 * @since 2020/8/15 10:51
 */
public class RedisPubService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 推送消息
     *
     * @param topicName 主题
     * @param publisher 发布者
     * @param content   内容
     * @param status    状态
     */
    public void publish(String topicName, String publisher, String content, int status) {
        RedisSimpleMessage pushMsg = new RedisSimpleMessage();
        pushMsg.setPublisher(publisher);
        pushMsg.setContent(content);
        pushMsg.setStatus(status);
        pushMsg.setCreateTime(new Date());
        ChannelTopic topic = new ChannelTopic(topicName);

        redisTemplate.convertAndSend(topic.getTopic(),toJson(pushMsg));
    }

    private <T> String toJson(T t) {
        ObjectMapper objectMapper = new ObjectMapper();
        //序列化时，日期的统一格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        //去掉默认的时间戳格式
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        //序列化处理
        objectMapper.enable(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature());
        objectMapper.enable(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER.mappedFeature());
        //忽略无法转换的对象
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        //反序列化时，属性不存在的兼容处理
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //允许单引号来包住属性名称和字符串值
        objectMapper.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
        //忽略为空的字段
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        SimpleModule simpleModule = new SimpleModule();
        // Long类型序列化成字符串，避免Long精度丢失
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        simpleModule.addSerializer(long.class, ToStringSerializer.instance);

        objectMapper.registerModule(simpleModule);

        if (null == t) {
            return null;
        }

        try {
            return t instanceof String ? (String) t : objectMapper.writeValueAsString(t);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
