package com.example.sleep_application.sleep_tracking_timer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import android.widget.Button;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.sleep_application.R;
import com.example.sleep_application.ui.sleep_tracking_timer.SleepTrackingFragment;

import org.junit.After;
import org.junit.Before;

@RunWith(AndroidJUnit4.class)
public class SleepTrackingFragmentTest {

    private FragmentScenario<SleepTrackingFragment> scenario;

    @Before
    public void setUp() throws Exception {
        // open fragment in testing container
        scenario = FragmentScenario.launchInContainer(SleepTrackingFragment.class);
    }

    @Test
    public void testTimerButtonOnClickStatesChanges(){

        scenario.onFragment(fragment -> {
            //refer to ui elements in fragments
            Button buttonTimerStart = fragment.requireView().findViewById(R.id.start_button);
            Button buttonTimerStop = fragment.requireView().findViewById(R.id.stop_button);
            Button buttonTimerReset = fragment.requireView().findViewById(R.id.reset_button);

            // test if the button are clicking or not

            buttonTimerStop.performClick();     // false -> false
            assertFalse(fragment.getRunning());

            buttonTimerStart.performClick();    // false -> true
            assertTrue(fragment.getRunning());

            buttonTimerStart.performClick();    // true -> true
            assertTrue(fragment.getRunning());

            buttonTimerStop.performClick();     // true -> false
            assertFalse(fragment.getRunning());

            buttonTimerReset.performClick();
            assertFalse(fragment.getRunning());                 // false -> false
            assertEquals(0, fragment.getSeconds());     // seconds reset to 0

        });

    }

    @After
    public void tearDown() throws Exception {
    }
}