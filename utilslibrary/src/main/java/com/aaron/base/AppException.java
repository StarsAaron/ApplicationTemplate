package com.aaron.base;

import android.text.TextUtils;

import org.apache.http.conn.ConnectTimeoutException;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * 名称：AppException.java
 * 描述：公共异常类.
 * 对异常描述信息进行修饰，输出普通用户可以理解的信息
 */
public class AppException extends Exception {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1;


    /**
     * 异常消息.
     */
    private String msg = null;

    /**
     * 构造异常类.
     *
     * @param e 异常
     */
    public AppException(Exception e) {
        super();

        try {
            if (e instanceof ConnectException) {
                msg = AppConfig.CONNECT_EXCEPTION;
            } else if (e instanceof ConnectTimeoutException) {
                msg = AppConfig.CONNECT_EXCEPTION;
            } else if (e instanceof UnknownHostException) {
                msg = AppConfig.UNKNOWN_HOST_EXCEPTION;
            } else if (e instanceof SocketException) {
                msg = AppConfig.SOCKET_EXCEPTION;
            } else if (e instanceof SocketTimeoutException) {
                msg = AppConfig.SOCKET_TIMEOUT_EXCEPTION;
            } else if (e instanceof NullPointerException) {
                msg = AppConfig.NULL_POINTER_EXCEPTION;
            } else {
                if (e == null || TextUtils.isEmpty(e.getMessage())) {
                    msg = AppConfig.NULL_MESSAGE_EXCEPTION;
                } else {
                    msg = e.getMessage();
                }
            }
        } catch (Exception e1) {
        }

    }

    /**
     * 用一个消息构造异常类.
     *
     * @param message 异常的消息
     */
    public AppException(String message) {
        super(message);
        msg = message;
    }

    /**
     * 描述：获取异常信息.
     *
     * @return the message
     */
    @Override
    public String getMessage() {
        return msg;
    }

}
