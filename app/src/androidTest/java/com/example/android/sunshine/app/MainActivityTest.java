package com.example.android.sunshine.app;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        ViewInteraction textView = onView(
                allOf(withId(R.id.title), withText("Settings"), isDisplayed()));
        textView.perform(click());

        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(android.R.id.list),
                                withParent(withClassName(is("android.widget.LinearLayout")))),
                        0),
                        isDisplayed()));
        linearLayout.perform(click());

        ViewInteraction editText = onView(
                allOf(withId(android.R.id.edit), withText("94043"),
                        withParent(withClassName(is("android.widget.LinearLayout")))));
        editText.perform(scrollTo(), click());

        ViewInteraction editText2 = onView(
                allOf(withId(android.R.id.edit), withText("94043"),
                        withParent(withClassName(is("android.widget.LinearLayout")))));
        editText2.perform(scrollTo(), replaceText("Arnhem,nl"), closeSoftKeyboard());

        ViewInteraction button = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        withParent(allOf(withClassName(is("com.android.internal.widget.ButtonBarLayout")),
                                withParent(withClassName(is("android.widget.LinearLayout"))))),
                        isDisplayed()));
        button.perform(click());

        ViewInteraction imageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        withParent(allOf(withClassName(is("android.widget.Toolbar")),
                                withParent(withClassName(is("com.android.internal.widget.ActionBarContainer"))))),
                        isDisplayed()));
        imageButton.perform(click());

        ViewInteraction linearLayout2 = onView(
                allOf(childAtPosition(
                        withId(R.id.listview_forecast),
                        3),
                        isDisplayed()));
        linearLayout2.perform(click());

        ViewInteraction imageButton2 = onView(
                allOf(withContentDescription("Navigate up"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        imageButton2.perform(click());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
