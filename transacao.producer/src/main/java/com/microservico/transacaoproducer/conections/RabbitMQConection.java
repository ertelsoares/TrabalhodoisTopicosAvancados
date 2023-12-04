package com.microservico.transacaoproducer.conections;

import com.microservico.transacaoproducer.constantes.RabbitmqConstantes;
import com.microservico.transacaoproducer.service.LeitorArquivo;
import com.microservico.transacaoproducer.constantes.RabbitmqService;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.microservico.transacaoproducer.dto.Transacao;
import java.util.List;


@Component
public class RabbitMQConection {


     @Autowired
     private RabbitmqService rabbitmqService;

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
        return new Binding(fila.getName(),Binding.DestinationType.QUEUE, troca.getName(), fila.getName(),null);
    }

    //Assim que a classe for construita levanta a aplicacao
    @PostConstruct
    private void adiciona(){
        Queue filaTransacao =  this.fila(RabbitmqConstantes.FILA_TRANSACAO);

        DirectExchange troca = this.trocaDireta();

        Binding ligacaoTransacao = this.relacionamento(filaTransacao,troca);
        //Criando as filas no Rabbitmq
        this.amqpAdmin.declareQueue(filaTransacao);

        this.amqpAdmin.declareExchange(troca);

        this.amqpAdmin.declareBinding(ligacaoTransacao);


        try {
            List<Transacao> transacoes = new LeitorArquivo().lerArquivo();
            for (Transacao transacao : transacoes) {

                this.rabbitmqService.enviaMensagem(RabbitmqConstantes.FILA_TRANSACAO, transacao.toString());

            }

        }catch(Exception ex)
            {
                Thread.currentThread().interrupt();
            }
    }

}
