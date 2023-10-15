package com.ienh.cpi.cpi.Objects;

import com.ienh.cpi.cpi.Models.User;

public class TokenInfo {
    private boolean valid;
    private User user;

    public TokenInfo(boolean valid, User user) {
        this.valid = valid;
        this.user = user;
    }

    public boolean isValid() {
        return valid;
    }

    public User getValue() {
        return user;
    }
}
