package com.anderson.invest.services;

import com.anderson.invest.dtos.UserMinDTO;
import com.anderson.invest.exceptions.RabbitMQException;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailPublisherService {

    @Value("${rabbitmq.exchangeName}")
    private String emailExchange;


    private final RabbitTemplate rabbitTemplate;

    public EmailPublisherService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendWelcomeMessage(UserMinDTO message) {
        try{
            rabbitTemplate.convertAndSend(emailExchange, "email.welcome", message);

        }catch (AmqpException e){
            throw new RabbitMQException("Falha ao enviar a mensagem para a fila ");

        }
    }
}
