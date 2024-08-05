package org.opendatakit.survey;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;

import android.content.ComponentName;
import android.content.Intent;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.fragment.app.FragmentManager;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.opendatakit.consts.IntentConsts;
import org.opendatakit.survey.activities.MainMenuActivity;
import org.opendatakit.survey.fragments.FormChooserListFragment;
import org.opendatakit.survey.utilities.FormInfo;
import org.opendatakit.survey.utilities.MockFormData;
import org.opendatakit.survey.utilities.MockFormListLoader;
import org.opendatakit.survey.utilities.TableIdFormIdVersionListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainMenuActivityTest {

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    );
    @Rule
    public ActivityScenarioRule<MainMenuActivity> activityRule = new ActivityScenarioRule<>(MainMenuActivity.class);

    private final String appName = "default";

    ArrayList<FormInfo> Forms;
    @Before
    public void setUp() {
        Intents.init();
        // Use the mock form list loader to load the mock forms
        Forms = MockFormData.generateMockForms();
        MockFormListLoader mockLoader = new MockFormListLoader(
                InstrumentationRegistry.getInstrumentation().getTargetContext(),Forms);

        activityRule.getScenario().onActivity(activity -> {
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            FormChooserListFragment fragment = (FormChooserListFragment) fragmentManager
                    .findFragmentById(R.id.main_content);
            fragment.onLoadFinished(mockLoader, Forms);
        });


    }
    @After
    public void tearDown() {
        Intents.release();
    }


    @Test
    public void testSyncToolbarItem_whenCLickedShouldSendCorrectIntent() {
        onView(withId(R.id.action_sync)).perform(click());

        intended(allOf(
                hasComponent(new ComponentName(
                        IntentConsts.Sync.APPLICATION_NAME,
                        IntentConsts.Sync.ACTIVITY_NAME)),
                hasAction(Intent.ACTION_DEFAULT),
                hasExtra(IntentConsts.INTENT_KEY_APP_NAME, appName)
        ));
    }

    @Test
    public void testSettingsToolbarItem_whenCLickedShouldSendCorrectIntent() {
        onView(withId(R.id.action_settings)).perform(click());

        intended(allOf(
                hasComponent(new ComponentName(
                        IntentConsts.AppProperties.APPLICATION_NAME,
                        IntentConsts.AppProperties.ACTIVITY_NAME)),
                hasAction(Intent.ACTION_DEFAULT),
                hasExtra(IntentConsts.INTENT_KEY_APP_NAME, appName)
        ));
    }

    @Test
    public void testSortByName() {
        // Open the overflow menu
        Espresso.openContextualActionModeOverflowMenu();
        onView(withText(R.string.sort_by_title)).perform(click());
        onView(withText(R.string.name)).perform(click());
        sortFormList(Forms, "sortByName");

        onView(withId(android.R.id.list)).check((view, noViewFoundException) -> {
            ListView listView = (ListView) view;
            ListAdapter adapter = listView.getAdapter();
            listView.invalidateViews();

            // Verify that the number of items in the adapter matches the number of sorted forms
            assertEquals(adapter.getCount(), Forms.size());

            // Verify that each item in the adapter is in the correct sorted order
            for (int i = 0; i < adapter.getCount(); i++) {
                FormInfo item = (FormInfo) adapter.getItem(i);
                assertEquals(item.formDisplayName, Forms.get(i).formDisplayName);
            }
        });
    }


    @Test
    public void testSortByTableId() {
        // Open the overflow menu
        Espresso.openContextualActionModeOverflowMenu();
        onView(withText(R.string.sort_by_title)).perform(click());
        onView(withText(R.string.tableId)).perform(click());

        sortFormList(Forms, "sortByTableID");

        onView(withId(android.R.id.list)).check((view, noViewFoundException) -> {
            ListView listView = (ListView) view;
            ListAdapter adapter = listView.getAdapter();
            listView.invalidateViews();

            // Verify that the number of items in the adapter matches the number of sorted forms
            assertEquals(adapter.getCount(), Forms.size());

            // Verify that each item in the adapter is in the correct sorted order
            for (int i = 0; i < adapter.getCount(); i++) {
                FormInfo item = (FormInfo) adapter.getItem(i);
                assertEquals(item.tableId,Forms.get(i).tableId);
            }
        });

    }

    private void sortFormList(ArrayList<FormInfo> forms, String sortingOrder) {
        if (sortingOrder.equals("sortByName")) {
            Collections.sort(forms, new Comparator<FormInfo>() {
                @Override
                public int compare(FormInfo lhs, FormInfo rhs) {
                    // Compare by formDisplayName first
                    int cmp = lhs.formDisplayName.compareToIgnoreCase(rhs.formDisplayName);
                    if (cmp != 0) {
                        return cmp;
                    }

                    // If formDisplayName is the same, compare by other fields
                    cmp = lhs.tableId.compareToIgnoreCase(rhs.tableId);
                    if (cmp != 0) {
                        return cmp;
                    }

                    cmp = lhs.formId.compareToIgnoreCase(rhs.formId);
                    if (cmp != 0) {
                        return cmp;
                    }

                    cmp = lhs.formVersion.compareToIgnoreCase(rhs.formVersion);
                    if (cmp != 0) {
                        return cmp;
                    }

                    // Compare by formDisplaySubtext if all other fields are equal
                    return lhs.formDisplaySubtext.compareToIgnoreCase(rhs.formDisplaySubtext);
                }
            });
        } else if (sortingOrder.equals("sortByTableID")) {
            Collections.sort(forms, new Comparator<FormInfo>() {
                @Override
                public int compare(FormInfo left, FormInfo right) {
                    // Compare by tableId first
                    int cmp = left.tableId.compareToIgnoreCase(right.tableId);
                    if (cmp != 0) {
                        return cmp;
                    }

                    // If tableId is the same, compare by other fields
                    cmp = left.formDisplayName.compareToIgnoreCase(right.formDisplayName);
                    if (cmp != 0) {
                        return cmp;
                    }

                    cmp = left.formId.compareToIgnoreCase(right.formId);
                    if (cmp != 0) {
                        return cmp;
                    }

                    cmp = left.formVersion.compareToIgnoreCase(right.formVersion);
                    if (cmp != 0) {
                        return cmp;
                    }

                    // Compare by formDisplaySubtext if all other fields are equal
                    return left.formDisplaySubtext.compareToIgnoreCase(right.formDisplaySubtext);
                }
            });
        }
    }


}