package com.example.nasapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.nasapp.Model.Apod;
import com.example.nasapp.UI.ApodFragment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
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
