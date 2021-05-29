package com.example.taskmaster;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void testUserNameTitle() {
        onView(withId(R.id.welcome_msg)).check(matches(isDisplayed()));
    }

    @Test
    public void testAddTaskBtnView() {
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.add_task_title)).check(matches(isDisplayed()));
        onView(withId(R.id.add_task_body)).check(matches(isDisplayed()));

        onView(withId(R.id.add_task_title)).perform(clearText(),typeText("Test Topic"));
        onView(withId(R.id.add_task_body)).perform(clearText(),typeText("Test Description"));
        onView(withId(R.id.save_task)).perform(click());

    }

    @Test
    public void testMyTaskWelcomingTitle() {
        onView(withId(R.id.textView9)).check(matches(isDisplayed()));
    }
    @Test
    public void testRecyclerView() {
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));

    }

    @Test
    public void testAllTaskBtnView() {
        onView(withId(R.id.alltasksbutton)).perform(click());
    }
    @Test
    public void testSettingBtnView() {
        onView(withId(R.id.settingButton)).perform(click());
        onView(withId(R.id.username)).check(matches(isDisplayed()));
        onView(withId(R.id.username)).perform(clearText(),typeText("test user"));
        onView(withId(R.id.Save_username)).perform(click());
        onView(withId(R.id.welcome_msg)).check(matches(withText("Test User's tasks")));

    }
}