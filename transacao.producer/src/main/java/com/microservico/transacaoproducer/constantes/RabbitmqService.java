package com.microservico.transacaoproducer.constantes;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitmqService {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    //Método que envia a mensagem , o nome da fila se refere ao nome que colocamos no RabbitMq

    public void enviaMensagem(String nomefila , Object mensagem){
       this.rabbitTemplate.convertAndSend(nomefila,mensagem);
    }




}
