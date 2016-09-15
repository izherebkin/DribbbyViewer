package com.jetruby.dribbbyviewer.viewassertion;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.view.View;
import android.widget.GridView;
import android.widget.ListAdapter;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

public class GridViewItemCountViewAssertion implements ViewAssertion {
    private final int expected;

    public GridViewItemCountViewAssertion(int expected) {
        this.expected = expected;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if (noViewFoundException != null) {
            throw noViewFoundException;
        }
        GridView gridView = (GridView) view;
        ListAdapter listAdapter = gridView.getAdapter();
        MatcherAssert.assertThat("GridView item count is incorrect", listAdapter.getCount(), Matchers.is(expected));
    }
}
