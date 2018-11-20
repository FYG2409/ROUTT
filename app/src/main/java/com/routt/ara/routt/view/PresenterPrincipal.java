package com.routt.ara.routt.view;


import android.net.Uri;

import com.google.firebase.storage.StorageReference;

public class PresenterPrincipal {
   private String name;

    private Uri mStorageRef;

    public PresenterPrincipal(Uri mStorageRef,String name) {

        this.name=name;
        this.mStorageRef = mStorageRef;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getmStorageRef() {
        return mStorageRef;
    }

    public void setmStorageRef(Uri mStorageRef) {
        this.mStorageRef = mStorageRef;
    }
}
