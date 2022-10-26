package kz.galamat.convenient.rpc.server.rabbitmq.configurations;

import kz.galamat.convenient.rpc.rabbitmq.settings.RpcProperties;
import kz.galamat.convenient.rpc.server.rabbitmq.converters.AmqpMessageToRpcRequestConverter;
import kz.galamat.convenient.rpc.server.rabbitmq.converters.MessageToRpcRequestConverter;
import kz.galamat.convenient.rpc.server.rabbitmq.listeners.RpcServerListener;
import kz.galamat.convenient.rpc.server.rabbitmq.services.RpcForwardAsServletService;
import kz.galamat.convenient.rpc.server.rabbitmq.services.RpcForwardService;
import kz.galamat.convenient.rpc.server.rabbitmq.services.RpcResponseProcessService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by Yersin Mukay on 14.10.2022
 */
@Configuration
public class SpringAutoConfiguration {

    @Bean
    public RpcForwardService forwardRpcToDispatcherRequestService(WebApplicationContext context) {
        return new RpcForwardAsServletService(context);
    }

    @Bean
    public MessageToRpcRequestConverter<Message> messageToRpcRequestConverter() {
        return new AmqpMessageToRpcRequestConverter();
    }

    @Bean
    public RpcResponseProcessService rpcRequestProcessService(RpcForwardService rpcForwardService) {
        return new RpcResponseProcessService(rpcForwardService);
    }

    @Bean
    @ConditionalOnBean(RpcServerMarkerConfiguration.Marker.class)
    public RpcServerListener rpcServerListener(RabbitTemplate rabbitTemplate,
                                               RpcProperties rpcProperties,
                                               RpcResponseProcessService rpcResponseProcessService,
                                               MessageToRpcRequestConverter<Message> toRpcRequestConverter) {
        return new RpcServerListener(rabbitTemplate, rpcProperties, rpcResponseProcessService, toRpcRequestConverter);
    }

}
