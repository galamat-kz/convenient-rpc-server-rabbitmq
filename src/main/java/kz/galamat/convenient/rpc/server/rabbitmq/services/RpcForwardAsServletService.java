package kz.galamat.convenient.rpc.server.rabbitmq.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.galamat.convenient.rpc.server.rabbitmq.exceptions.RpcForwardException;
import kz.galamat.convenient.rpc.server.rabbitmq.servlet.InternalHttpRequest;
import kz.galamat.convenient.rpc.server.rabbitmq.servlet.InternalHttpResponse;
import kz.galamat.i.convenient.rpc.dtos.RpcRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.RequestDispatcher;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 * Created by Yersin Mukay on 20.10.2022
 */
@AllArgsConstructor
public class RpcForwardAsServletService implements RpcForwardService {

    private final WebApplicationContext context;

    @SneakyThrows
    @Override
    public void forward(final RpcRequest rpcRequest, final OutputStream responseOutputStream) {
        final ObjectMapper objectMapper = new ObjectMapper();
        // servlet request
        final ByteArrayInputStream bodyAsInputStream = new ByteArrayInputStream(
                objectMapper.writeValueAsBytes(rpcRequest.getBody()));
        final InternalHttpRequest servletRequest = InternalHttpRequest.builder()
                .path(rpcRequest.getPath())
                .method(rpcRequest.getMethod())
                .headers(rpcRequest.getHeaders())
                .servletInputStream(bodyAsInputStream)
                .bufferedReader(bodyAsInputStream)
                .queryParams(rpcRequest.getQueryParams())
                .build();
        servletRequest.init();
        // servlet response
        final InternalHttpResponse servletResponse = InternalHttpResponse.builder()
                .servletOutputStream(responseOutputStream)
                .printWriter(responseOutputStream)
                .build();
        // do forward to as servlet
        doForward(servletRequest, servletResponse);

        if (servletRequest.hasException()) {
            throw new RpcForwardException(servletRequest.getException(), servletResponse.getStatus());
        }
    }

    @SneakyThrows
    private void doForward(final InternalHttpRequest request, final InternalHttpResponse response) {
        RequestDispatcher requestDispatcher = Objects.requireNonNull(
                context.getServletContext()).getRequestDispatcher(request.getPath());
        requestDispatcher.forward(request, response);
    }

}
