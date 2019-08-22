package com.ndgndg91.security.authserver.error;


import com.ndgndg91.security.authserver.util.MessageUtils;

public class UnauthorizedException extends ServiceRuntimeException {

    public static final String MESSAGE_KEY = "error.auth";
    public static final String MESSAGE_DETAIL = "error.auth.details";

    public UnauthorizedException(String message) {
        super(MESSAGE_KEY, MESSAGE_DETAIL, new Object[]{message});
    }

    @Override
    public String getMessage() {
        return MessageUtils.getInstance().getMessage(getDetailKey(), getParams());
    }

    @Override
    public String toString() {
        return MessageUtils.getInstance().getMessage(getMessageKey());
    }

}
