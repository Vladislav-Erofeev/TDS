package com.example.searchservice.configuration;

import com.example.searchservice.messages.ItemMessage;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfiguration {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${kafka.topic.name}")
    private String kafkaTopicName;

    @Bean
    public NewTopic newTopic() {
        return TopicBuilder.name(kafkaTopicName).build();
    }

    @Bean
    public ConsumerFactory<String, ItemMessage> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapAddress);
        props.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class);
        props.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                groupId);
        props.put(
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                "earliest");
        return new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(),
                new JsonDeserializer<>(ItemMessage.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ItemMessage>
    tableMessageConcurrentKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ItemMessage> containerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(consumerFactory());
        return containerFactory;
    }
}
