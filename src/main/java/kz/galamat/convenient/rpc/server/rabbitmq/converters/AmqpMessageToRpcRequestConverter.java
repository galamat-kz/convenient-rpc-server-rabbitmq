package kz.galamat.convenient.rpc.server.rabbitmq.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.galamat.i.convenient.rpc.dtos.RpcRequest;
import lombok.SneakyThrows;
import org.springframework.amqp.core.Message;

/**
 * Created by Yersin Mukay on 20.10.2022
 */
public class AmqpMessageToRpcRequestConverter implements MessageToRpcRequestConverter<Message> {

    @SneakyThrows
    @Override
    public RpcRequest convert(Message message) {
        // convert message to rpcRequest
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(message.getBody(), RpcRequest.class);
    }
}
