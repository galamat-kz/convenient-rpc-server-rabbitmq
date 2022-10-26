package kz.galamat.convenient.rpc.server.rabbitmq.exceptions;

import lombok.Getter;

/**
 * Created by Yersin Mukay on 20.10.2022
 */
public class RpcForwardException extends RuntimeException {

    @Getter
    private int status = 500;

    public RpcForwardException(Throwable cause) {
        super(cause);
    }

    public RpcForwardException(Throwable cause, int status) {
        this(cause);
        this.status = status;
    }
}
