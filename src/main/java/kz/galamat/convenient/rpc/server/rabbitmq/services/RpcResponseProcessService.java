package kz.galamat.convenient.rpc.server.rabbitmq.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.galamat.convenient.rpc.server.rabbitmq.exceptions.RpcForwardException;
import kz.galamat.i.convenient.rpc.dtos.RpcErrorResponse;
import kz.galamat.i.convenient.rpc.dtos.RpcRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by Yersin Mukay on 20.10.2022
 */
@AllArgsConstructor
public class RpcResponseProcessService {

    private final Logger logger = LoggerFactory.getLogger(RpcResponseProcessService.class.getName());
    private final RpcForwardService rpcForwardService;

    @SneakyThrows
    public byte[] process(final RpcRequest rpcRequest) {
        try {
            final ByteArrayOutputStream responseOutputStream = new ByteArrayOutputStream();
            rpcForwardService.forward(rpcRequest, responseOutputStream);
            return responseOutputStream.toByteArray();
        } catch (Throwable t) {
            logger.warn(t.getMessage());
            int status = 500;
            if (t instanceof RpcForwardException rpcForwardException) {
                status = rpcForwardException.getStatus() >= 400 ? rpcForwardException.getStatus() : status;
            }
            final RpcErrorResponse errorResponseDto = RpcErrorResponse.builder()
                    .status(status)
                    .error(t.getClass().getName())
                    .message(t.getMessage())
                    .build();
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsBytes(errorResponseDto);
        }
    }
}
