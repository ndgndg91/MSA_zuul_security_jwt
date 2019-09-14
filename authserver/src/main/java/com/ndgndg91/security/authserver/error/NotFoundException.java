package com.ndgndg91.security.authserver.error;

import com.ndgndg91.security.authserver.util.MessageUtils;
import org.apache.commons.lang3.StringUtils;

public class NotFoundException extends ServiceRuntimeException {

    private static final String MESSAGE_KEY = "error.notfound";
    private static final String MESSAGE_DETAILS = "error.notfound.details";

    public NotFoundException(Class cls, Object... values) {
        this(cls.getSimpleName(), values);
    }

    private NotFoundException(String targetName, Object... values) {
        super(MESSAGE_KEY, MESSAGE_DETAILS, new String[]{targetName, (values != null && values.length > 0) ? StringUtils.join(values, ",") : ""});
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
