package com.jkdevelopment.astronomy;

import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.jkdevelopment.astronomy.Model.Apod;
import com.jkdevelopment.astronomy.UI.ApodFragment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class MainActivityUITest{

    @Rule
    public ActivityTestRule <MainActivity>activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void apodFragmentHasContent() {
        /*Arrange*/
        Apod apod = new Apod();
        ApodFragment apodFragment = ApodFragment.newInstance(apod);
        activityTestRule.getActivity().fragmentManager.beginTransaction().replace(R.id.root, apodFragment, null).commitAllowingStateLoss();

        /*Act*/
        onView(withText("APOD")).perform(click());

        /*Assert*/
        onView(withId(R.id.apod_title)).check(matches(not(withText(""))));
        onView(withId(R.id.apod_explanation)).check(matches(not(withText(""))));
        onView(withId(R.id.apod_image)).check(matches(not(withText(""))));
    }
}
