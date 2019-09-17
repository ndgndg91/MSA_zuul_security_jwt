package com.ndgndg91.security.authserver.model.api.request.user;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class ChangePasswordRequest {
    private String originPassword;
    private String newPassword;
    private String newPasswordCheck;

    public boolean isNotRightNewPw(){
        checkNotNull(newPassword, "newPassword must be provided.");
        checkNotNull(newPasswordCheck, "newPasswordCheck must be provided");
        checkArgument(
                newPassword.length() >= 4 && newPassword.length() <= 15,
                "newPassword length must be between 4 and 15 characters."
        );
        checkArgument(
                newPasswordCheck.length() >= 4 && newPasswordCheck.length() <= 15,
                "newPasswordCheck length must be between 4 and 15 characters."
        );
        return !StringUtils.equals(newPassword, newPasswordCheck);
    }

    public String getOriginPassword() {
        return originPassword;
    }

    public void setOriginPassword(String orignPassword) {
        this.originPassword = orignPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getNewPasswordCheck() {
        return newPasswordCheck;
    }

    public void setNewPasswordCheck(String newPasswordCheck) {
        this.newPasswordCheck = newPasswordCheck;
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .toString();
    }
}
