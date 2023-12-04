package com.microsservico.consumidortransacao.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.microsservico.consumidortransacao.constantes.RabbitmqConstantes;

import com.microsservico.consumidortransacao.dto.Transacao;
import org.apache.logging.log4j.message.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransacaoConsumer {


  private final RabbitTemplate rabbitTemplate;

  @Autowired
  public TransacaoConsumer(RabbitTemplate rabbitTemplate) {

    this.rabbitTemplate = rabbitTemplate;
  }



    @RabbitListener(queues = RabbitmqConstantes.FILA_TRANSACAO)
    public void consumidor(String mensagem) {
      try {
        Thread.sleep(1000);
        // Faz o parse da string para o objeto Transacao
        Transacao transacao = parseTransacaoFromString(mensagem);

        // Processamento da transação recebida
        System.out.println(mensagem);

        // Verificar se o valor é igual ou superior a R$ 40.000
        if (transacao.getValor() >= 5000) {
          // Notificar a Polícia Federal
          enviarNotificacaoPoliciaFederal(transacao);

          // Notificar a Receita Federal
          enviarNotificacaoReceitaFederal(transacao);
        }
        // Restante do processamento da transação
        // ... seu código aqui

      } catch (Exception e) {

        e.printStackTrace();
        // Tratar exceção aqui (log, notificação, etc.)
      }
    }

    // Método para fazer o parse da string para objeto Transacao
    private Transacao parseTransacaoFromString(String mensagem) {
      try {
        // Remove os colchetes e divide a string nos elementos usando a vírgula como delimitador
        String[] dados = mensagem.substring(mensagem.indexOf("[") + 1, mensagem.indexOf("]")).split(", ");

        // Verifica se a quantidade de elementos é válida
        if (dados.length == 5) {
          // Cria um novo objeto Transacao e atribui os valores
          Transacao transacao = new Transacao();
          transacao.setCodigo(dados[0].split("=")[1]);
          transacao.setCedente(dados[1].split("=")[1]);
          transacao.setPagador(dados[2].split("=")[1]);
          transacao.setValor(Double.parseDouble(dados[3].split("=")[1]));
          transacao.setVencimento(dados[4].split("=")[1]);

          return transacao;
        } else {
          throw new IllegalArgumentException("Formato de mensagem inválido");
        }
      } catch (Exception e) {
        // Trate a exceção (log, notificação, etc.)
        throw new IllegalArgumentException("Erro ao processar a mensagem: " + mensagem);
      }
    }

    private void enviarNotificacaoPoliciaFederal(Transacao transacao) {
      // Lógica para enviar a notificação para a Polícia Federal
      // Utiliza o RabbitTemplate para enviar a mensagem para a exchange desejada
      rabbitTemplate.convertAndSend(
              "transacoes.suspeita", "routing_key_policia_federal", transacao);
      System.out.println("Envia notificacao para Policia Federal");
    }

    private void enviarNotificacaoReceitaFederal(Transacao transacao) {
      // Lógica para enviar a notificação para a Receita Federal
      // Utiliza o RabbitTemplate para enviar a mensagem para a exchange desejada
      rabbitTemplate.convertAndSend("transacoes.suspeita", "routing_key_receita_federal", transacao);
      System.out.println("Envia notificacao para Receita Federal");
    }



}
