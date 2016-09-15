package com.jetruby.dribbbyviewer.viewassertion;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.view.View;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.jetruby.dribbbyviewer.model.Shot;

import org.hamcrest.MatcherAssert;

public class ListAdapterDataViewAssertion implements ViewAssertion {

    public ListAdapterDataViewAssertion() {
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }
        GridView gridView = (GridView) view;
        ListAdapter listAdapter = gridView.getAdapter();
        for (int i = 0; i < listAdapter.getCount(); i++) {
            Shot item = (Shot) listAdapter.getItem(i);
            boolean isCorrect = (!item.getAnimated() && item.getTitle() != null && item.getDescription() != null
                    && (item.getImages().getHidpi() != null || item.getImages().getNormal() != null || item.getImages().getTeaser() != null));
            MatcherAssert.assertThat("ListAdapter data is incorrect", isCorrect);
        }
    }
}
