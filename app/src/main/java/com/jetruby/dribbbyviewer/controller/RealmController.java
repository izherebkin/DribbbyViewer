package com.jetruby.dribbbyviewer.controller;

import android.content.Context;

import com.jetruby.dribbbyviewer.model.Shot;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class RealmController {
    private static RealmController instance;
    private final Realm realm;

    private RealmController(Context context) {
        Realm.init(context);
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder().build());
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Context context) {
        if (instance == null) {
            instance = new RealmController(context);
        }
        return instance;
    }

    public boolean hasAnyObjects() {
        return !realm.isEmpty();
    }

    public List<Shot> copyToEmptyShotRealm(List<Shot> shots) {
        realm.beginTransaction();
        realm.delete(Shot.class);
        List<Shot> copy = realm.copyToRealm(shots);
        realm.commitTransaction();
        return copy;
    }

    public RealmResults<Shot> getShots() {
        return realm.where(Shot.class).findAll();
    }
}
