package com.microsservico.consumidortransacao.config;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitmqConfig {

    public static final String EXCHANGE_NAME = "transacoes.suspeita";

    public static final String POLICIA_QUEUE = "policia.federal";
    public static final String RECEITA_QUEUE = "receita.federal";

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue policiaQueue() {
        return new Queue(POLICIA_QUEUE);
    }

    @Bean
    public Queue receitaQueue() {
        return new Queue(RECEITA_QUEUE);
    }

    @Bean
    public Binding policiaBinding(Queue policiaQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(policiaQueue).to(fanoutExchange);
    }

    @Bean
    public Binding receitaBinding(Queue receitaQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(receitaQueue).to(fanoutExchange);
    }
}