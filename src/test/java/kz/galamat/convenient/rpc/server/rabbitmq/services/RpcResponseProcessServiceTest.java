package kz.galamat.convenient.rpc.server.rabbitmq.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kz.galamat.i.convenient.rpc.dtos.RpcRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;

import java.io.ByteArrayOutputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Created by Yersin Mukay on 20.10.2022
 */
@SpringBootTest
class RpcResponseProcessServiceTest {

    @Autowired
    private RpcResponseProcessService rpcResponseProcessService;
    @MockBean
    private RpcForwardService rpcForwardService;

    @Test
    void testProcessForSuccess() throws JsonProcessingException {
        RpcRequest getUserRpcRequest = RpcRequest.builder().path("/users/yersin").method(HttpMethod.GET.name()).build();
        String user = "{\"id\": \"123\", \"name\": \"Yersin\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] expectedBytes = objectMapper.writeValueAsBytes(user);
        doAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            ((ByteArrayOutputStream) args[1]).writeBytes(expectedBytes);
            return null;
        }).when(rpcForwardService).forward(eq(getUserRpcRequest), any());

        byte[] bytes = rpcResponseProcessService.process(getUserRpcRequest);
        assertThat(bytes).isEqualTo(expectedBytes)
                .as("Wrong response from rpcRequestProcessService.process(RpcRequest)");
        verify(rpcForwardService, times(1)).forward(any(), any());
    }
}