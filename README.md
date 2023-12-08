# Trabalho com RabbitMQ - Exchanges amq.direct e fanout

Este projeto demonstra o uso de RabbitMQ para troca de mensagens entre produtores e consumidores, explorando os conceitos de diferentes tipos de exchanges: `amq.direct` e `fanout`.

## Visão Geral

O RabbitMQ é um sistema de mensageria que facilita a comunicação entre aplicações distribuídas. Neste trabalho, são utilizados dois tipos principais de exchanges:

- **amq.direct**: Exchange padrão que roteia mensagens baseadas em uma chave de roteamento específica.
- **fanout**: Exchange que envia cópias de mensagens para todas as filas vinculadas a ela, ignorando a chave de roteamento.

## Funcionalidades Principais

### Produtores
Os produtores são responsáveis por enviar mensagens para o RabbitMQ. Eles enviam mensagens para uma exchange específica, podendo ser `amq.direct` (usando uma chave de roteamento) ou `fanout` (enviando para todas as filas associadas à exchange).

### Consumidores
Os consumidores são aplicativos ou componentes que recebem e processam mensagens do RabbitMQ. Eles podem estar vinculados a uma fila específica, configurada para receber mensagens de uma exchange `amq.direct`, onde a mensagem é roteada com base em uma chave, ou a uma exchange `fanout`, recebendo cópias de todas as mensagens enviadas para essa exchange.

### amq.direct vs fanout
- **amq.direct**: Roteia mensagens usando uma chave de roteamento específica para enviar mensagens para filas que tenham a mesma chave de roteamento especificada pelo produtor.
- **fanout**: Envia cópias de mensagens para todas as filas vinculadas a ela, independentemente da chave de roteamento, permitindo broadcast de mensagens para múltiplos consumidores.

## Como Utilizar
- Configure produtores para enviar mensagens para a exchange `amq.direct` com chaves de roteamento específicas.
- Configure consumidores para receber mensagens da exchange `amq.direct` com base na chave de roteamento ou da exchange `fanout` para receber todas as mensagens.

## Contribuição
Contribuições são bem-vindas! Se encontrar algum problema ou quiser melhorar este trabalho, sinta-se à vontade para criar uma issue ou enviar um pull request.

## Autor
Lucas Ertel Soares
