package com.example.iconscollector.ui.delegates.permissions;

import android.Manifest;

public enum Permission {

    WRITE_STORAGE(Manifest.permission.WRITE_EXTERNAL_STORAGE);

    private final String code;

    Permission(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
