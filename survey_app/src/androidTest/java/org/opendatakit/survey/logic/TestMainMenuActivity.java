package org.opendatakit.survey.logic;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import android.content.ComponentName;
import android.content.Intent;

import androidx.test.rule.GrantPermissionRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.opendatakit.consts.IntentConsts;
import org.opendatakit.survey.R;
import org.opendatakit.survey.activities.MainMenuActivity;


public class TestMainMenuActivity {

    private final String appName = "default";

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

    @Ignore // till service pipeline is fixed
    @Test
    public void testSyncMenuItem() {

         // Click on the "Sync" menu item
        onView(withId(R.id.action_sync)).perform(click());

        // Verify that the sync activity is launched
        intended(allOf(hasComponent(new ComponentName(IntentConsts.Sync.APPLICATION_NAME,
                IntentConsts.Sync.ACTIVITY_NAME)),
                hasAction(Intent.ACTION_DEFAULT),
                hasExtra(IntentConsts.INTENT_KEY_APP_NAME, appName)));
    }

    @Ignore // till service pipeline is fixed
    @Test
    public void testSettingsMenuItem() {
        // Click on the "Settings" menu item
        onView(withId(R.id.action_settings)).perform(click());

        // Verify that the settings activity is launched
        intended(hasComponent(new ComponentName(IntentConsts.AppProperties.APPLICATION_NAME,
                IntentConsts.AppProperties.ACTIVITY_NAME)));
    }
}
