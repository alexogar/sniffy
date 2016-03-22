package io.sniffy.socket;

import io.sniffy.Sniffer;
import io.sniffy.util.ExceptionUtil;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketImpl;

public class SnifferSocketImpl extends SocketImpl {

    private final SocketImpl delegate;

    private String address;

    private final static String UNKNOWN = "unknown";

    public SnifferSocketImpl(SocketImpl delegate) {
        this.delegate = delegate;
    }

    protected void logSocket(long millis) {
        Sniffer.logSocket(null == address ? UNKNOWN : address, millis);
    }

    protected void logSocket(long millis, int bytesDown, int bytesUp) {
        Sniffer.logSocket(null == address ? UNKNOWN : address, millis, bytesDown, bytesUp);
    }

    // TODO in order to support server sockets we should also copy fields to delegate
    private static Method method(String methodName, Class<?>... argumentTypes) throws NoSuchMethodException {
        Method method = SocketImpl.class.getDeclaredMethod(methodName, argumentTypes);
        method.setAccessible(true);
        return method;
    }

    @Override
    protected void sendUrgentData(int data) throws IOException {
        long start = System.currentTimeMillis();
        try {
            method("sendUrgentData", int.class).invoke(delegate, data);
        } catch (InvocationTargetException e) {
            processInvocationTargetException(e);
        } catch (Exception e) {
            throw new IOException(e);
        } finally {
            logSocket(System.currentTimeMillis() - start);
        }
    }

    private static void processInvocationTargetException(InvocationTargetException e) {
        ExceptionUtil.throwTargetException(e);
    }

    @Override
    protected void shutdownInput() throws IOException {
        long start = System.currentTimeMillis();
        try {
            method("shutdownInput").invoke(delegate);
        } catch (InvocationTargetException e) {
            processInvocationTargetException(e);
        } catch (Exception e) {
            throw new IOException(e);
        } finally {
            logSocket(System.currentTimeMillis() - start);
        }
    }

    @Override
    protected void shutdownOutput() throws IOException {
        long start = System.currentTimeMillis();
        try {
            method("shutdownOutput").invoke(delegate);
        } catch (InvocationTargetException e) {
            processInvocationTargetException(e);
        } catch (Exception e) {
            throw new IOException(e);
        } finally {
            logSocket(System.currentTimeMillis() - start);
        }
    }

    @Override
    protected FileDescriptor getFileDescriptor() {
        long start = System.currentTimeMillis();
        try {
            return (FileDescriptor) method("getFileDescriptor").invoke(delegate);
        } catch (InvocationTargetException e) {
            processInvocationTargetException(e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            logSocket(System.currentTimeMillis() - start);
        }
    }

    @Override
    protected InetAddress getInetAddress() {
        long start = System.currentTimeMillis();
        try {
            return (InetAddress) method("getInetAddress").invoke(delegate);
        } catch (InvocationTargetException e) {
            processInvocationTargetException(e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            logSocket(System.currentTimeMillis() - start);
        }
    }

    @Override
    protected int getPort() {
        long start = System.currentTimeMillis();
        try {
            return (Integer) method("getPort").invoke(delegate);
        } catch (InvocationTargetException e) {
            processInvocationTargetException(e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            logSocket(System.currentTimeMillis() - start);
        }
    }

    @Override
    protected boolean supportsUrgentData() {
        long start = System.currentTimeMillis();
        try {
            return (Boolean) method("supportsUrgentData").invoke(delegate);
        } catch (InvocationTargetException e) {
            processInvocationTargetException(e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            logSocket(System.currentTimeMillis() - start);
        }
    }

    @Override
    protected int getLocalPort() {
        long start = System.currentTimeMillis();
        try {
            return (Integer) method("getLocalPort").invoke(delegate);
        } catch (InvocationTargetException e) {
            processInvocationTargetException(e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            logSocket(System.currentTimeMillis() - start);
        }
    }

    @Override
    public String toString() {
        long start = System.currentTimeMillis();
        try {
            return delegate.toString();
        } finally {
            logSocket(System.currentTimeMillis() - start);
        }
    }

    @Override
    protected void setPerformancePreferences(int connectionTime, int latency, int bandwidth) {
        long start = System.currentTimeMillis();
        try {
            method("setPerformancePreferences", int.class, int.class, int.class).invoke(delegate, connectionTime, latency, bandwidth);
        } catch (InvocationTargetException e) {
            processInvocationTargetException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            logSocket(System.currentTimeMillis() - start);
        }
    }

    @Override
    protected void create(boolean stream) throws IOException {
        long start = System.currentTimeMillis();
        try {
            method("create", boolean.class).invoke(delegate, stream);
        } catch (InvocationTargetException e) {
            processInvocationTargetException(e);
        } catch (Exception e) {
            throw new IOException(e);
        } finally {
            logSocket(System.currentTimeMillis() - start);
        }
    }

    @Override
    protected void connect(String host, int port) throws IOException {
        long start = System.currentTimeMillis();
        try {
            method("connect", String.class, int.class).invoke(delegate, host, port);
            this.address = host + ":" + port;
        } catch (InvocationTargetException e) {
            processInvocationTargetException(e);
        } catch (Exception e) {
            throw new IOException(e);
        } finally {
            logSocket(System.currentTimeMillis() - start);
        }
    }

    @Override
    protected void connect(InetAddress address, int port) throws IOException {
        long start = System.currentTimeMillis();
        try {
            method("connect", InetAddress.class, int.class).invoke(delegate, address, port);
            this.address = address.toString() + ":" + port;
        } catch (InvocationTargetException e) {
            processInvocationTargetException(e);
        } catch (Exception e) {
            throw new IOException(e);
        } finally {
            logSocket(System.currentTimeMillis() - start);
        }
    }

    @Override
    protected void connect(SocketAddress address, int timeout) throws IOException {
        long start = System.currentTimeMillis();
        try {
            method("connect", SocketAddress.class, int.class).invoke(delegate, address, timeout);
            this.address = address.toString();
        } catch (InvocationTargetException e) {
            processInvocationTargetException(e);
        } catch (Exception e) {
            throw new IOException(e);
        } finally {
            logSocket(System.currentTimeMillis() - start);
        }
    }

    @Override
    protected void bind(InetAddress host, int port) throws IOException {
        long start = System.currentTimeMillis();
        try {
            method("bind", InetAddress.class, int.class).invoke(delegate, host, port);
        } catch (InvocationTargetException e) {
            processInvocationTargetException(e);
        } catch (Exception e) {
            throw new IOException(e);
        } finally {
            logSocket(System.currentTimeMillis() - start);
        }
    }

    @Override
    protected void listen(int backlog) throws IOException {
        long start = System.currentTimeMillis();
        try {
            method("listen", int.class).invoke(delegate, backlog);
        } catch (InvocationTargetException e) {
            processInvocationTargetException(e);
        } catch (Exception e) {
            throw new IOException(e);
        } finally {
            logSocket(System.currentTimeMillis() - start);
        }
    }

    @Override
    protected void accept(SocketImpl s) throws IOException {
        long start = System.currentTimeMillis();
        try {
            method("accept", SocketImpl.class).invoke(delegate, s);
        } catch (InvocationTargetException e) {
            processInvocationTargetException(e);
        } catch (Exception e) {
            throw new IOException(e);
        } finally {
            logSocket(System.currentTimeMillis() - start);
        }
    }

    @Override
    protected InputStream getInputStream() throws IOException {
        long start = System.currentTimeMillis();
        try {
            return new SnifferInputStream(this, (InputStream) method("getInputStream").invoke(delegate));
        } catch (InvocationTargetException e) {
            processInvocationTargetException(e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new IOException(e);
        } finally {
            logSocket(System.currentTimeMillis() - start);
        }
    }

    @Override
    protected OutputStream getOutputStream() throws IOException {
        long start = System.currentTimeMillis();
        try {
            return new SnifferOutputStream(this, (OutputStream) method("getOutputStream").invoke(delegate));
        } catch (InvocationTargetException e) {
            processInvocationTargetException(e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new IOException(e);
        } finally {
            logSocket(System.currentTimeMillis() - start);
        }
    }

    @Override
    protected int available() throws IOException {
        long start = System.currentTimeMillis();
        try {
            return (Integer) method("available").invoke(delegate);
        } catch (InvocationTargetException e) {
            processInvocationTargetException(e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new IOException(e);
        } finally {
            logSocket(System.currentTimeMillis() - start);
        }
    }

    @Override
    protected void close() throws IOException {
        long start = System.currentTimeMillis();
        try {
            method("close").invoke(delegate);
        } catch (InvocationTargetException e) {
            processInvocationTargetException(e);
        } catch (Exception e) {
            throw new IOException(e);
        } finally {
            logSocket(System.currentTimeMillis() - start);
        }
    }

    // interface

    @Override
    public void setOption(int optID, Object value) throws SocketException {
        long start = System.currentTimeMillis();
        try {
            delegate.setOption(optID, value);
        } finally {
            logSocket(System.currentTimeMillis() - start);
        }
    }

    @Override
    public Object getOption(int optID) throws SocketException {
        long start = System.currentTimeMillis();
        try {
            return delegate.getOption(optID);
        } finally {
            logSocket(System.currentTimeMillis() - start);
        }
    }
}