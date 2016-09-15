package com.jetruby.dribbbyviewer;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.jetruby.dribbbyviewer.activity.GridViewActivity;
import com.jetruby.dribbbyviewer.util.Constant;
import com.jetruby.dribbbyviewer.viewassertion.GridViewItemCountViewAssertion;
import com.jetruby.dribbbyviewer.viewassertion.ListAdapterDataViewAssertion;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class GridViewActivityNextLaunchTest {

    public GridViewActivityNextLaunchTest() {
    }

    @Rule
    public ActivityTestRule<GridViewActivity> activityTestRule = new ActivityTestRule<>(GridViewActivity.class);

    @Test
    public void testBasicsOnNextLaunch() {
        /* Check that GridView has [Constant.SHOTS] items in ListAdapter */
        Espresso
                .onView(ViewMatchers.withId(R.id.grid_view))
                .check(new GridViewItemCountViewAssertion(Constant.SHOTS));

        /* Check that GridView has correct data in ListAdapter */
        Espresso
                .onView(ViewMatchers.withId(R.id.grid_view))
                .check(new ListAdapterDataViewAssertion());

        /* Check that GridView has сompletely displayed ImageView, title and description in TextViews aren't null or empty for all items */
        for (int i = (Constant.SHOTS - 1); i > -1; i--) {
            Espresso
                    .onData(Matchers.anything())
                    .inAdapterView(ViewMatchers.withId(R.id.grid_view))
                    .atPosition(i)
                    .onChildView(ViewMatchers.withId(R.id.imageView))
                    .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()));

            Espresso
                    .onData(Matchers.anything())
                    .inAdapterView(ViewMatchers.withId(R.id.grid_view))
                    .atPosition(i)
                    .onChildView(ViewMatchers.withId(R.id.imageTitle))
                    .check(ViewAssertions.matches(ViewMatchers.withText(Matchers.not(Matchers.isEmptyOrNullString()))));

            Espresso
                    .onData(Matchers.anything())
                    .inAdapterView(ViewMatchers.withId(R.id.grid_view))
                    .atPosition(i)
                    .onChildView(ViewMatchers.withId(R.id.imageDescription))
                    .check(ViewAssertions.matches(ViewMatchers.withText(Matchers.not(Matchers.isEmptyOrNullString()))));
        }

        /* Perform pull down to refresh */
        Espresso
                .onView(ViewMatchers.withId(com.jetruby.dribbbyviewer.R.id.grid_view))
                .perform(ViewActions.swipeDown());

        /* Check that GridView has [Constant.SHOTS] items in ListAdapter */
        Espresso
                .onView(ViewMatchers.withId(R.id.grid_view))
                .check(new GridViewItemCountViewAssertion(Constant.SHOTS));

        /* Check that GridView has correct data in ListAdapter */
        Espresso
                .onView(ViewMatchers.withId(R.id.grid_view))
                .check(new ListAdapterDataViewAssertion());

        /* Check that GridView has сompletely displayed ImageView, title and description in TextViews aren't null or empty for all items */
        for (int i = 0; i < Constant.SHOTS; i++) {
            Espresso
                    .onData(Matchers.anything())
                    .inAdapterView(ViewMatchers.withId(R.id.grid_view))
                    .atPosition(i)
                    .onChildView(ViewMatchers.withId(R.id.imageView))
                    .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()));

            Espresso
                    .onData(Matchers.anything())
                    .inAdapterView(ViewMatchers.withId(R.id.grid_view))
                    .atPosition(i)
                    .onChildView(ViewMatchers.withId(R.id.imageTitle))
                    .check(ViewAssertions.matches(ViewMatchers.withText(Matchers.not(Matchers.isEmptyOrNullString()))));

            Espresso
                    .onData(Matchers.anything())
                    .inAdapterView(ViewMatchers.withId(R.id.grid_view))
                    .atPosition(i)
                    .onChildView(ViewMatchers.withId(R.id.imageDescription))
                    .check(ViewAssertions.matches(ViewMatchers.withText(Matchers.not(Matchers.isEmptyOrNullString()))));
        }
    }
}
