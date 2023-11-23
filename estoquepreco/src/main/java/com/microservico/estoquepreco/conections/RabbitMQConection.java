package com.microservico.estoquepreco.conections;

import com.microservico.estoquepreco.constantes.RabbitmqConstantes;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;


@Component
public class RabbitMQConection {
      private static final String NOME_EXCHANGE = "amq.direct";
      private AmqpAdmin amqpAdmin;
    /*esse parametro é o conceito da fila*/

    public RabbitMQConection(AmqpAdmin amqpAdmin) {
       this.amqpAdmin = amqpAdmin;
    }

    private Queue fila(String nomeFila){
        return new Queue(nomeFila,true,false,false);
    }

    private DirectExchange trocaDireta(){
        return new DirectExchange(NOME_EXCHANGE);
    }


    private Binding relacionamento(Queue fila, DirectExchange troca){
        return new Binding(fila.getName(),Binding.DestinationType.EXCHANGE, troca.getName(), fila.getName(),null);
    }

    private void adiciona(){
        Queue filaEstoque =  this.fila(RabbitmqConstantes.FILA_ESTOQUE);
        Queue filaPreco =  this.fila(RabbitmqConstantes.FILA_PRECO);

        DirectExchange troca = this.trocaDireta();

        Binding ligacao = this.relacionamento(filaEstoque,troca);

        //Criando as filas no Rabbitmq
        this.amqpAdmin.declareQueue(filaEstoque);
        this.amqpAdmin.declareQueue(filaPreco);
    }

}
