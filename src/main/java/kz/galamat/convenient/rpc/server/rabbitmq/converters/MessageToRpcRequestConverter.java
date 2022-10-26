package kz.galamat.convenient.rpc.server.rabbitmq.converters;

import kz.galamat.i.convenient.rpc.dtos.RpcRequest;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by Yersin Mukay on 20.10.2022
 */
public interface MessageToRpcRequestConverter<S> extends Converter<S, RpcRequest> {

}
