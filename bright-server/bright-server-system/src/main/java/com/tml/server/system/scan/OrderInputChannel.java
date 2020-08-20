package com.tml.server.system.scan;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
* @author JacksonTu
 * @version 1.0
 * @description
 * @since 2020/8/20 18:15
 */
public interface OrderInputChannel {
    //定义通道的名称
    String saveOder = "saveOder";
    //定义为输入通道
    @Input(saveOder)
    SubscribableChannel saveOrder();
}
