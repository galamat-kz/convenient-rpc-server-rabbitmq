package kz.galamat.convenient.rpc.server.rabbitmq.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Yersin Mukay on 19.10.2022
 */
@Configuration(proxyBeanMethods = false)
public class RpcServerMarkerConfiguration {

    @Bean
    public Marker rpcServerMarkerBean() {
        return new Marker();
    }

    class Marker {

    }

}
