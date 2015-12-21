package io.sniffy.socket;

import io.sniffy.util.ExceptionUtil;
import sun.misc.Unsafe;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.Socket;
import java.net.SocketImpl;
import java.net.SocketImplFactory;

public class SnifferSocketImplFactory implements SocketImplFactory {

    private final static Constructor<? extends SocketImpl> defaultSocketImplClassConstructor =
            getDefaultSocketImplClassConstructor();

    private final SocketImplFactory previousSocketImplFactory;

    public SnifferSocketImplFactory(SocketImplFactory previousSocketImplFactory) {
        this.previousSocketImplFactory = previousSocketImplFactory;
    }

    public static void install() throws IOException {

        SocketImplFactory previousSocketImplFactory = null;

        try {
            Field factoryField = Socket.class.getDeclaredField("factory");
            factoryField.setAccessible(true);
            previousSocketImplFactory = (SocketImplFactory) factoryField.get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        Socket.setSocketImplFactory(new SnifferSocketImplFactory(previousSocketImplFactory));
    }

    public static void uninstall() {
        try {
            Field factoryField = Socket.class.getDeclaredField("factory");
            factoryField.setAccessible(true);
            factoryField.set(null, null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SocketImpl createSocketImpl() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (null != stackTrace) {
            for (StackTraceElement ste : stackTrace) {
                if (ste.getClassName().startsWith("java.net.ServerSocket")) {
                    return newSocketImpl();
                }
            }
        }

        return new SnifferSocketImpl(newSocketImpl());
    }

    private SocketImpl newSocketImpl() {

        if (null != previousSocketImplFactory) {
            return previousSocketImplFactory.createSocketImpl();
        } else {
            try {
                return null == defaultSocketImplClassConstructor ? null :
                        defaultSocketImplClassConstructor.newInstance();
            } catch (Exception e) {
                ExceptionUtil.throwException(e);
                return null;
            }
        }

    }


    @SuppressWarnings("unchecked")
    private static Class<? extends SocketImpl> getDefaultSocketImplClass() throws ClassNotFoundException {
        return (Class<? extends SocketImpl>) Class.forName("java.net.SocksSocketImpl");
    }

    private static Constructor<? extends SocketImpl> getDefaultSocketImplClassConstructor() {
        Constructor<? extends SocketImpl> constructor;
        try {
            constructor = getDefaultSocketImplClass().getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        }
        constructor.setAccessible(true);
        return constructor;
    }

}