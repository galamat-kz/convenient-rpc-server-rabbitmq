package kz.galamat.convenient.rpc.server.rabbitmq.services;

import kz.galamat.i.convenient.rpc.dtos.RpcRequest;

import java.io.OutputStream;

/**
 * Created by Yersin Mukay on 20.10.2022
 */
public interface RpcForwardService {

    void forward(RpcRequest rpcRequest, OutputStream responseOutputStream);

}
