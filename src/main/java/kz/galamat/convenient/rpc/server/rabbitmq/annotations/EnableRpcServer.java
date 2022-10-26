package kz.galamat.convenient.rpc.server.rabbitmq.annotations;

import kz.galamat.convenient.rpc.server.rabbitmq.configurations.RpcServerMarkerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RpcServerMarkerConfiguration.class)
public @interface EnableRpcServer {

}
