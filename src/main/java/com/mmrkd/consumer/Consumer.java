package com.mmrkd.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class Consumer {

    public static void main(String[] args) throws Exception {

        //创建一个拉取消息的消费者对象
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("wolfcode-consumer");
        //设置名字地址
        consumer.setNamesrvAddr("127.0.0.1:9876");
        //绑定消息的主题
        consumer.subscribe("01-hello", "*");
        //消费者监听处理消息方法
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    System.out.println("消费者线程1:"+Thread.currentThread().getName()+",消息ID:"+msg.getMsgId()+",消息内容:"+new String(msg.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //启动消费者
        consumer.start();

    }
}
