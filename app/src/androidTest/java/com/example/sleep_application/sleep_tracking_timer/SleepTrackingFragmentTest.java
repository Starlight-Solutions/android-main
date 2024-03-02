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
        scenario = FragmentScenario.launchInContainer(SleepTrackingFragment.class);
    }

    @Test
    public void testButtonOnClick(){

        scenario.onFragment(fragment -> {
            Button buttonTimerStart = fragment.requireView().findViewById(R.id.start_button);
            Button buttonTimerStop = fragment.requireView().findViewById(R.id.stop_button);
            Button buttonTimerReset = fragment.requireView().findViewById(R.id.reset_button);

            // test if the button are clicking or not
            buttonTimerStart.performClick();
            assertTrue(fragment.getRunning());
        });


    }

    @After
    public void tearDown() throws Exception {
    }
}