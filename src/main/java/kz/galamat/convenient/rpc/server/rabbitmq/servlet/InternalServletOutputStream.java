package kz.galamat.convenient.rpc.server.rabbitmq.servlet;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Yersin Mukay on 12.10.2022
 */
public class InternalServletOutputStream extends ServletOutputStream {

    private final OutputStream outputStream;

    public InternalServletOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void write(int b) throws IOException {
        outputStream.write(b);
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setWriteListener(WriteListener listener) {
        throw new UnsupportedOperationException();
    }
}
