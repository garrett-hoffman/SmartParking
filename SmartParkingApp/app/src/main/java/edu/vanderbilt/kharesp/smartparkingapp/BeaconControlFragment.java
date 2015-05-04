package edu.vanderbilt.kharesp.smartparkingapp;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import edu.vanderbilt.kharesp.gimbaltutorial.R;

/*
 * Created on 2015/05/02.
 * <p/>
 * Contributors:
 * Michael A. Walker
 * Shweta P. Khare
 */

/**
 * Fragment to allow the control of the Beacon gathering service
 */
public
class BeaconControlFragment extends Fragment {

	 /**
	  * The Interface the Activity has to implement to support this
	  * fragment.
	  */
	 BeaconActivityControlInterface mBeaconActivityControlInterface;

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
	  * Fragment LifeCycle hook method. Called when Fragment is
	  * attached
	  * to Activity
	  *
	  * @param activity
	  */
	 @Override
	 public
	 void onAttach(Activity activity) {
		  super.onAttach(activity);
		  this.mBeaconActivityControlInterface = (BeaconActivityControlInterface) activity;
	 }

	 /**
	  * Fragment LifeCycle hook method. Called when Fragment should
	  * create
	  * its View.
	  *
	  * @param inflater
	  * @param container
	  * @param savedInstanceState
	  * @return View to display
	  */
	 @Override
	 public
	 View onCreateView(LayoutInflater inflater,
							 ViewGroup container,
							 Bundle savedInstanceState) {
		  View returnView =
					 inflater.inflate(R.layout.beacon_control_fragment_layout, container,
											false);

		  /**
			* Associate Buttons with UI's XML Buttons.
			*/
		  startBeaconServiceButton = (Button) returnView
					 .findViewById(R.id.beacon_control_fragment_start_button);
		  stopBeaconServiceButton = (Button) returnView
					 .findViewById(R.id.beacon_control_fragment_stop_button);
		  toggleBeaconServiceButton = (Button) returnView
					 .findViewById(R.id
												  .beacon_control_fragment_set_background_service_button);
		  setTimingsButton = (Button) returnView
					 .findViewById(R.id.beacon_control_fragment_set_timings_button);

		  foregroundPeriodEditText = (EditText) returnView
					 .findViewById(R.id.beacon_control_fragment_foreground_edittext);
		  foregroundPeriodDelayEditText = (EditText) returnView
					 .findViewById(R.id.beacon_control_fragment_foreground_delay_edittext);
		  backgroundPeriodEditText = (EditText) returnView
					 .findViewById(R.id.beacon_control_fragment_background_edittext);
		  backgroundPeriodDelayEditText = (EditText) returnView
					 .findViewById(R.id.beacon_control_fragment_background_delay_edittext);

		  return returnView;
	 }

	 public
	 interface BeaconActivityControlInterface {

		  /**
			* Start or Stop Foreground/Background service for
			*/
		  public
		  void startBeaconSearching();

		  public
		  void stopBeaconSearching();

		  /**
			* Constatnts from org.altbeacon.beacon.BeaconManager
			*/
		  public static final long DEFAULT_BACKGROUND_BETWEEN_SCAN_PERIOD = 300000L;
		  public static final long DEFAULT_BACKGROUND_SCAN_PERIOD = 10000L;
		  public static final long DEFAULT_FOREGROUND_BETWEEN_SCAN_PERIOD = 0L;
		  public static final long DEFAULT_FOREGROUND_SCAN_PERIOD = 1100L;

		  /**
			* Set the period to scan when in Foreground Mode
			*/
		  public
		  void setForegroundScanPeriod(long time) throws RemoteException;

		  public
		  void setForegroundBetweenScanPeriod(long time) throws RemoteException;

		  /*
			* set the service to (or not) act in 'background' mode,
			* which
			* uses less power
			*/
		  public
		  void setBackgroundMode(boolean mode);

		  /*
			* Set the period to scan when in Background Mode
			*/
		  public
		  void setBackgroundScanPeriod(long time) throws RemoteException;

		  public
		  void setBackgroundBetweenScanPeriod(long time) throws RemoteException;

	 }


}
