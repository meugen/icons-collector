package com.example.iconscollector.ui.delegates.permissions;

import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class PermissionsDelegate {

    private final Fragment fragment;

    public PermissionsDelegate(Fragment fragment) {
        this.fragment = fragment;
    }

    public boolean checkPermission(Permission permission) {
        return ContextCompat.checkSelfPermission(
                fragment.requireContext(),
                permission.getCode()
        ) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission(Permission permission, int requestCode) {
        fragment.requestPermissions(new String[] { permission.getCode() }, requestCode);
    }
}
