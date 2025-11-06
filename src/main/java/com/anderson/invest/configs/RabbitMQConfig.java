package com.anderson.invest.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchangeName}")
    public String emailExchange;
    @Value("${rabbitmq.queueWelcome}")
    public String queueWelcome;
    @Value("${rabbitmq.queueInvestimentRemoved}")
    public String queueInvestimentRemoved;

    @Bean
    public TopicExchange emailExchangeBean() {
        return new TopicExchange(this.emailExchange);
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationRunner runner(RabbitAdmin rabbitAdmin) {
        return args -> rabbitAdmin.initialize();
    }

    @Bean
    public Queue welcomeQueue() {
        return new Queue(this.queueWelcome, true);
    }

    @Bean
    public Queue investmentRemovedQueue() {
        return new Queue(this.queueInvestimentRemoved, true);
    }

    @Bean
    public Binding bindingWelcome(Queue welcomeQueue, TopicExchange emailExchange) {
        return BindingBuilder.bind(welcomeQueue).to(emailExchange).with("email.welcome");
    }

    @Bean
    public Binding bindingInvestmentRemoved(Queue investmentRemovedQueue, TopicExchange emailExchange) {
        return BindingBuilder.bind(investmentRemovedQueue).to(emailExchange).with("email.investment.removed");
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
