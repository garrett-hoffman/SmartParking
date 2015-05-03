package edu.vanderbilt.kharesp.gimbaltutorial;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by mike on 5/2/15.
 */
public class BeaconControlFragment extends Fragment {

    /**
     * The Interface the Activity has to implement to support this fragment.
     */
    BeaconControlActivityInterface mBeaconControlActivityInterface;

    /**
     * EditTexts to pull times from the UI.
     */
    EditText foregroundPeriodEditText;
    EditText foregroundPeriodDelayEditText;
    EditText backgroundPeriodEditText;
    EditText backgroundPeriodDelayEditText;

    /**
     * Buttons to register Clicks with.
     */
    Button startBeaconServiceButton;
    Button stopBeaconServiceButton;
    Button toggleBeaconServiceButton;
    Button setTimingsButton;

    /**
     * Fragment LifeCycle hook method. Called when Fragment is attached to Activity
     *
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mBeaconControlActivityInterface = (BeaconControlActivityInterface) activity;
    }

    /**
     * Fragment LifeCycle hook method. Called when Fragment should create its View.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return View to display
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View returnView = inflater.inflate(R.layout.beacon_control_fragment_layout, container, false);

        /**
         * Associate Buttons with UI's XML Buttons.
         */
        startBeaconServiceButton = (Button) returnView.findViewById(R.id.beacon_control_fragment_start_button);
        stopBeaconServiceButton = (Button) returnView.findViewById(R.id.beacon_control_fragment_stop_button);
        toggleBeaconServiceButton = (Button) returnView.findViewById(R.id.beacon_control_fragment_set_background_service_button);
        setTimingsButton = (Button) returnView.findViewById(R.id.beacon_control_fragment_set_timings_button);

        foregroundPeriodEditText = (EditText) returnView.findViewById(R.id.beacon_control_fragment_foreground_edittext);
        foregroundPeriodDelayEditText = (EditText) returnView.findViewById(R.id.beacon_control_fragment_foreground_delay_edittext);
        backgroundPeriodEditText = (EditText) returnView.findViewById(R.id.beacon_control_fragment_background_edittext);
        backgroundPeriodDelayEditText = (EditText) returnView.findViewById(R.id.beacon_control_fragment_background_delay_edittext);

        return returnView;
    }


}
