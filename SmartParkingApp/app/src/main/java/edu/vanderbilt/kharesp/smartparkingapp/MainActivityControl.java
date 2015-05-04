package edu.vanderbilt.kharesp.smartparkingapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import edu.vanderbilt.kharesp.gimbaltutorial.R;
import org.altbeacon.beacon.*;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
 * Created on 2015/05/02.
 * <p/>
 * Contributors:
 * Shweta P. Khare
 * Michael A. Walker
 */

/**
 * Main Activity for Beacon Access/Use & Development/Testing.
 */
public
class MainActivityControl extends ActionBarActivity implements BeaconConsumer,
																					OnMapReadyCallback,
																					BeaconControlFragment
																							  .BeaconActivityControlInterface {

	 /*
	  * Constant(s) for use with Handler associated with this class.
	  */
	 public static int HANDLER_FLAG_REFRESH_BEACON_DATA = 1;
	 public static UIHanlder mHandler;
	 /*
	  * Current Beacon List.
	  */
	 private static List<BeaconData> beaconData;
	 /*
	  * String used in Logging.
	  */
	 public String LOG = this.getClass().getCanonicalName();
	 /**
	  * This currently doesn't work, but leaving in for reference for
	  * now.
	  */
	 MonitorNotifier mMonitorNotifier = new MonitorNotifier() {
		  @Override
		  public
		  void didEnterRegion(Region region) {
				Log.i(LOG, "just saw a beacon for the first time!!");
		  }

		  @Override
		  public
		  void didExitRegion(Region region) {
				Log.i(LOG, "Can't see the beacon anymore!!");
		  }

		  @Override
		  public
		  void didDetermineStateForRegion(int i,
													 Region region) {
				Log.i(LOG, "State of beacon:" + i);
		  }
	 };
	 /**
	  * This RangeNotifier receives the Beacon data and passes it to
	  * the fragment via onLocationUpdate()
	  */
	 RangeNotifier mRangeNotifier = new RangeNotifier() {
		  @Override
		  public
		  void didRangeBeaconsInRegion(Collection<Beacon> beacons,
												 Region region) {
				if (beacons.size() > 0) {
					 List<BeaconData> data = new ArrayList<BeaconData>();
					 for (Beacon b : beacons) {
						  Log.i(LOG, "uuid:" + b.getServiceUuid() + ";id1:" +
										 b.getId1() + ";rssi:" + b.getRssi());
						  data.add(new BeaconData(b.getId1().toUuidString(), b.getRssi()));
					 }
					 setBeaconData(data);
					 Message msg = mHandler.obtainMessage();
					 msg.arg1 = HANDLER_FLAG_REFRESH_BEACON_DATA;
					 mHandler.sendMessage(msg);
				}
		  }
	 };
	 /*
	  * Beacon Manager Service.
	  */
	 private org.altbeacon.beacon.BeaconManager altBeaconManager;
	 private BeaconControlFragment beaconControlFragment;
	 private BeaconLogFragment.onLocationUpdateInterface locationUpdateFragment;

	 private boolean isDualFragmentDisplay = false;
	 private boolean isLocationUpdateFragmentDisplayed = false;
	 private boolean isControlFragmentDisplayed = false;


	 /**
	  * Overwrite the current Beacon Data cache with new values seen.
	  *
	  * @param data
	  */
	 private static
	 void setBeaconData(List<BeaconData> data) {
		  beaconData = new ArrayList<BeaconData>();
		  beaconData.addAll(data);
	 }

	 public
	 void onLocationUpdate(List<BeaconData> beacons) {
		  locationUpdateFragment.onLocationUpdate(beacons);
	 }

	 /**
	  * LifeCycle hook, used to instantiate Views and other objects.
	  *
	  * @param savedInstanceState
	  */
	 @Override
	 protected
	 void onCreate(Bundle savedInstanceState) {
		  Log.i(LOG, "Main Activity's OnCreate Called.");
		  super.onCreate(savedInstanceState);
		  setContentView(R.layout.activity_main);
		  /*
			* Setup Beacon Manager (Service)
         */
		  altBeaconManager =
					 org.altbeacon.beacon.BeaconManager.getInstanceForApplication(this);
		  altBeaconManager.getBeaconParsers().
					 add(new BeaconParser().setBeaconLayout(
								"m:2-3=0215,i:4-19,i:20-21,i:22-23," + "p:24-24"));
		  altBeaconManager.bind(this);
		  altBeaconManager.setForegroundScanPeriod(100L);

        /*
			* Initialize Fragment(s)
         */
		  locationUpdateFragment =
					 (BeaconLogFragment.onLocationUpdateInterface) new BeaconLogFragment();
		  beaconControlFragment = new BeaconControlFragment();

		  setFragmentDefaults();

		  Resources res = getResources();
		  isDualFragmentDisplay = res.getBoolean(R.bool.isDualFragment);

        /*
			* Initialize Handler
         */
		  mHandler = new UIHanlder(this);

	 }

	 /**
	  * Helper method to attach the fragment to the layout.
	  */
	 public
	 void setFragmentDefaults() {
		  boolean isDualFragmentScreen = false;
		  if (isDualFragmentDisplay == false)
		  // Single Fragment Screen
		  {
				FragmentManager fm = getFragmentManager();
				if (fm.findFragmentById(R.id.main_fragment_container) == null) {
					 fm.beginTransaction()
						.add(R.id.main_fragment_container, (Fragment) locationUpdateFragment)
						.commit();
				}
				isLocationUpdateFragmentDisplayed = true;
		  } else
		  // Dual Fragment Screen
		  {
				FragmentManager fm = getFragmentManager();
				if (fm.findFragmentById(R.id.main_fragment_container) == null) {
					 fm.beginTransaction()
						.add(R.id.main_fragment_container_2, (Fragment) beaconControlFragment)
						.commit();
				}
				isControlFragmentDisplayed = true;
		  }
	 }

	 public
	 void swapFragments() {
		  Fragment fragment;
		  if (isLocationUpdateFragmentDisplayed == true) {
				fragment = (Fragment) beaconControlFragment;
				isLocationUpdateFragmentDisplayed = false;
				isControlFragmentDisplayed = true;
		  } else {
				fragment = (Fragment) locationUpdateFragment;
				isLocationUpdateFragmentDisplayed = true;
				isControlFragmentDisplayed = false;
		  }
		  // Insert the fragment by replacing any existing fragment
		  FragmentManager fragmentManager = getFragmentManager();
		  fragmentManager.beginTransaction().replace(R.id.main_fragment_container,
																	fragment)
							  .commit();
	 }

	 /**
	  * Lifecycle call, clean up the BeaconManager
	  */
	 @Override
	 protected
	 void onDestroy() {
		  super.onDestroy();
		  altBeaconManager.unbind(this);
	 }

	 /**
	  * Beacon Service Hook Method when 'connected'
	  */
	 @Override
	 public
	 void onBeaconServiceConnect() {
		  Log.i(LOG, "beacon service connected.");
		  altBeaconManager.setMonitorNotifier(mMonitorNotifier);
		  altBeaconManager.setRangeNotifier(mRangeNotifier);

        /*
			* No Idea what this does, left it in case it is important ;)
         */
		  try {
				//altBeaconManager.startMonitoringBeaconsInRegion(r);
				altBeaconManager.startRangingBeaconsInRegion(
						  new Region("myRangingUniqueId", null, null, null));
		  } catch (android.os.RemoteException e) {
				Log.e(LOG, e.getMessage());
		  }
	 }

	 /**
	  * Create Options Menu.
	  *
	  * @param menu
	  * @return
	  */
	 @Override
	 public
	 boolean onCreateOptionsMenu(Menu menu) {
		  // Inflate the menu; this adds items to the action bar if it
		  // is present.
		  getMenuInflater().inflate(R.menu.menu_main, menu);

        /*
			* Remove 'swap fragments' menu item when both are displayed
         */
		  if (isDualFragmentDisplay == false) {
				menu.removeItem(R.id.swap_fragments);
		  }
		  return true;
	 }

	 /**
	  * Options Menu Item Selection
	  *
	  * @param item
	  * @return
	  */
	 @Override
	 public
	 boolean onOptionsItemSelected(MenuItem item) {
		  /*
			* Handle action bar item clicks here. The action bar will
         * automatically handle clicks on the Home/Up button, so long
         * as you specify a parent activity in AndroidManifest.xml.
         */
		  switch (item.getItemId()) {
				case R.id.action_settings: {
					 return true;
				}
				case R.id.swap_fragments: {
					 swapFragments();
					 return true;
				}
				default: {
					 return super.onOptionsItemSelected(item);
				}
		  }
	 }

	 /**
	  * TODO Not sure 'why', but needed for Google Maps.
	  *
	  * @param googleMap
	  */
	 @Override
	 public
	 void onMapReady(GoogleMap googleMap) {

	 }

	 /**
	  * Start the Beacon Service
	  */
	 @Override
	 public
	 void startBeaconSearching() {
		  //TODO IMPLEMENT
	 }

	 /*
	  * Stop the Beacon Service
	  */
	 @Override
	 public
	 void stopBeaconSearching() {
		  // TODO IMPLEMENT
	 }

	 /**
	  * Set the period to scan when in Foreground Mode.
	  *
	  * @param time
	  * @throws RemoteException
	  */
	 @Override
	 public
	 void setForegroundScanPeriod(long time) throws RemoteException {
		  altBeaconManager.setForegroundScanPeriod(time);
		  altBeaconManager.updateScanPeriods();
	 }

	 /**
	  * Set the period between scans when in Foreground Mode.
	  *
	  * @param time
	  * @throws RemoteException
	  */
	 @Override
	 public
	 void setForegroundBetweenScanPeriod(long time) throws RemoteException {
		  altBeaconManager.setForegroundBetweenScanPeriod(time);
		  altBeaconManager.updateScanPeriods();
	 }

	 /*
	  * Set the mode (Foreground/Background) of the Beacon Service
	  */
	 @Override
	 public
	 void setBackgroundMode(boolean mode) {
		  altBeaconManager.setBackgroundMode(mode);
	 }

	 /**
	  * Set Background Scan Period.
	  *
	  * @param time
	  * @throws RemoteException
	  */
	 @Override
	 public
	 void setBackgroundScanPeriod(long time) throws RemoteException {
		  altBeaconManager.setBackgroundScanPeriod(time);
		  altBeaconManager.updateScanPeriods();
	 }

	 /**
	  * Set Period between Background Scans.
	  *
	  * @param time
	  * @throws RemoteException
	  */
	 @Override
	 public
	 void setBackgroundBetweenScanPeriod(long time) throws RemoteException {
		  altBeaconManager.setBackgroundBetweenScanPeriod(time);
		  altBeaconManager.updateScanPeriods();
	 }

	 /**
	  * Handler Class, that handles Beacon Service being able to
	  * communicate with UI Thread.
	  */
	 public
	 class UIHanlder extends Handler {

		  WeakReference<MainActivityControl> mActivity;

		  /**
			* Constructor, store WeakReference to Activity
			*
			* @param activity
			*/
		  UIHanlder(MainActivityControl activity) {
				this.mActivity = new WeakReference<MainActivityControl>(activity);
		  }

		  @Override
		  public
		  void handleMessage(Message msg) {
				if (msg.arg1 == HANDLER_FLAG_REFRESH_BEACON_DATA) {
					 if (isLocationUpdateFragmentDisplayed) {
						  mActivity.get().onLocationUpdate(beaconData);
					 }
				}
		  }

	 }
}
