package org.opendatakit.survey.logic;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import android.content.ComponentName;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opendatakit.consts.IntentConsts;
import org.opendatakit.survey.R;
import org.opendatakit.survey.activities.MainMenuActivity;


@RunWith(AndroidJUnit4.class)
public class TestMainMenuActivity {

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    );

    @Rule
    public ActivityScenarioRule<MainMenuActivity> mainMenuActivityActivity =
            new ActivityScenarioRule<>(MainMenuActivity.class);

    @Before
    public void setUp() {
        // Initialize Espresso-Intents
        Intents.init();
    }

    @After
    public void tearDown() {
        // Release Espresso-Intents
        Intents.release();
    }

    @Test
    public void testToolbarLogoNavigation() {
        // Click on the toolbar logo
        onView(withId(R.id.imgAppBarLogo)).perform(click());

        // Verify that the main activity is displayed
        onView(withId(R.id.toolbarMainActivity)).check(matches(isDisplayed()));
    }

    @Test
    public void testSyncMenuItem() {
        // Click on the "Sync" menu item
        onView(withId(R.id.action_sync)).perform(click());

        // Verify that the sync activity is launched
        intended(hasComponent(new ComponentName(IntentConsts.Sync.APPLICATION_NAME,
                IntentConsts.Sync.ACTIVITY_NAME)));
    }

    @Test
    public void testSettingsMenuItem() {
        // Click on the "Settings" menu item
        onView(withId(R.id.action_settings)).perform(click());

        // Verify that the settings activity is launched
        intended(hasComponent(new ComponentName(IntentConsts.AppProperties.APPLICATION_NAME,
                IntentConsts.AppProperties.ACTIVITY_NAME)));
    }
}
