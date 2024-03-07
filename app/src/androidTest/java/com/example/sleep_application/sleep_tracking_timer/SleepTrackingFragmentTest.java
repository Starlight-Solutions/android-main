package com.example.sleep_application.sleep_tracking_timer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import android.os.Handler;
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
        // test if the button are clicking or not, and is the timer activated as desired

        scenario.onFragment(fragment -> {
            //refer to ui elements in fragments
            Button buttonTimerStart = fragment.requireView().findViewById(R.id.start_button);
            Button buttonTimerStop = fragment.requireView().findViewById(R.id.stop_button);
            Button buttonTimerReset = fragment.requireView().findViewById(R.id.reset_button);


            buttonTimerStop.performClick();     // false -> false | stop to stop case
            assertFalse(fragment.getRunning());

            buttonTimerStart.performClick();    // false -> true | stop to start case
            assertTrue(fragment.getRunning());

            assertEquals(0,fragment.getSeconds()); // start at 0 seconds

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // check for the value is 2 seconds or not when started running
                    assertEquals(2, fragment.getSeconds());
                }
            }, 2000); // delay 2000 milliseconds (2 seconds)

            buttonTimerStart.performClick();    // true -> true | running timer clicked start again
            assertTrue(fragment.getRunning());

            buttonTimerStop.performClick();     // true -> false | stop timer
            assertFalse(fragment.getRunning());

            buttonTimerReset.performClick();
            assertFalse(fragment.getRunning());                 // false -> false | reset timer
            assertEquals(0, fragment.getSeconds());     // seconds reset to 0

        });
    }

    @After
    public void tearDown() throws Exception {
    }
}