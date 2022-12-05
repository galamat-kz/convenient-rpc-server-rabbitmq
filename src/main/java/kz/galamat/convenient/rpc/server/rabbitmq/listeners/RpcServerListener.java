package kz.galamat.convenient.rpc.server.rabbitmq.listeners;

import kz.galamat.convenient.rpc.rabbitmq.settings.RpcProperties;
import kz.galamat.convenient.rpc.server.rabbitmq.converters.MessageToRpcRequestConverter;
import kz.galamat.convenient.rpc.server.rabbitmq.services.RpcResponseProcessService;
import kz.galamat.i.convenient.rpc.dtos.RpcRequest;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * Created by Yersin Mukay on 12.10.2022
 */
@AllArgsConstructor
public class RpcServerListener {

    private final Logger logger = LoggerFactory.getLogger(RpcServerListener.class.getName());
    private final RabbitTemplate rabbitTemplate;
    private final RpcProperties rpcProperties;
    private final RpcResponseProcessService rpcResponseProcessService;
    private final MessageToRpcRequestConverter<Message> toRpcRequestConverter;

    @RabbitListener(queues = "${spring.convenient.rpc.rabbitmq.queue}", returnExceptions = "true")
    public void process(Message message) {
        CorrelationData correlationData = new CorrelationData(message.getMessageProperties().getCorrelationId());
        try {
            // convert message to rpcRequest
            final RpcRequest rpcRequest = toRpcRequestConverter.convert(message);
            // process the incoming request and respond to it
            final byte[] reply = rpcResponseProcessService.process(rpcRequest);
            // replyMessage and correlationData
            Message replyMessage = MessageBuilder
                    .withBody(reply)
                    .setCorrelationId(message.getMessageProperties().getCorrelationId())
                    .build();
            // send the response
            rabbitTemplate.send(rpcProperties.getExchange(),
                    message.getMessageProperties().getReplyTo(), replyMessage, correlationData);
        } catch (Throwable t) {
            logger.error(t.getMessage());
        }
    }
}
