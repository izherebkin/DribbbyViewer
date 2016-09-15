package com.jetruby.dribbbyviewer.util;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.jetruby.dribbbyviewer.model.Shot;

import java.util.ArrayList;
import java.util.List;

public class Helper {

    private Helper() {
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public static List<Shot> filter(List<Shot> shots) {
        List<Shot> filtered = new ArrayList<>();
        for (Shot shot : shots) {
            boolean isAnimated = shot.getAnimated() == null ? true : shot.getAnimated();
            boolean hasTitleAndDescription = (shot.getTitle() != null && shot.getDescription() != null);
            boolean hasImage = (shot.getImages().getHidpi() != null || shot.getImages().getNormal() != null || shot.getImages().getTeaser() != null);
            if (!isAnimated && hasTitleAndDescription && hasImage) {
                filtered.add(shot);
                if (filtered.size() == Constant.SHOTS) {
                    break;
                }
            }
        }
        return filtered;
    }
}
