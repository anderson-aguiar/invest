package com.anderson.invest.services;

import com.anderson.invest.dtos.InvestmentRemoveEvent;
import com.anderson.invest.exceptions.RabbitMQException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class InvestmentRemovedListenerService {

    private final Logger log = LoggerFactory.getLogger(InvestmentRemovedListenerService.class);
    private final EmailPublisherService emailPublisherService;

    public InvestmentRemovedListenerService(EmailPublisherService emailPublisherService) {
        this.emailPublisherService = emailPublisherService;
    }

    //se tudo ocorrer bem com a transação com o banco, ai sim é enviado a msg para a fila do rabbit
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleInvestmentRemoveEvent(InvestmentRemoveEvent event) {
        try {
            emailPublisherService.sendInvestmentRemovedMessage(event.dto());
        } catch (RabbitMQException e) {
            log.warn("Falha ao enviar mensagem de venda do investimento: {}", e.getMessage());
        }
    }
}
