package edu.vanderbilt.kharesp.smartparkingapp;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import edu.vanderbilt.kharesp.gimbaltutorial.R;

import java.util.List;

/* Created on 2015/05/02.
 * <p/>
 * Contributors:
 * Michael A. Walker
 * Shweta P. Khare
 */

/**
 * Fragment that will display a log of Bluetooth LE Beacon data as it is captured.
 */
public
class BeaconLogFragment extends Fragment { //implements onLocationUpdateInterface

	 /**
	  * Store Activity incase it is needed. (lots of future reasons)
	  */
	 Activity activity;

	 /*
	  * The TextView which will display the updated info.
	  */
	 TextView dataList;
	 ScrollView scrollView;

	 /*
	  * Fragment LifeCycle hook method, Called when attaching the Fragment to the
	  * Activity.
	  */
	 @Override
	 public
	 void onAttach(Activity activity) {
		  super.onAttach(activity);
		  this.activity = activity;
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
	 public
	 View onCreateView(LayoutInflater inflater,
							 ViewGroup container,
							 Bundle savedInstanceState) {
		  View returnView =
					 inflater.inflate(R.layout.main_fragment_layout, container, false);

		  dataList = (TextView) returnView
					 .findViewById(R.id.beacon_fragment_scroll_data_text_view);
		  scrollView =
					 (ScrollView) returnView.findViewById(R.id.beacon_fragment_scrollView);

		  return returnView;
	 }

	 /**
	  * Hook Method that is called by the attached Activity to notify of new Beacon Data
	  *
	  * @param beacons
	  */
	 public
	 void onLocationUpdate(List<BeaconData> beacons) {

		  /**
			* Prevent Activity from mistakenly trying to change Views when not attached.
			*/
		  if (this.isDetached()) {
				return;
		  }

		  for (int i = 0; i < beacons.size(); i++) {
				addTextToScrollTextView("" + beacons.get(i).ID1 + "  " +
												beacons.get(i).signalStrenth);
		  }
		  addTextToScrollTextView("--------------------");
	 }

	 /*
	  * Helper method to add text to a scrollable TextView
	  */
	 private
	 void addTextToScrollTextView(String msg) {
		  // add new data
		  dataList.append(msg + "\n");
		  // get amount to scroll
		  final int amountToScroll = dataList.getLineCount() * dataList.getLineHeight();
		  // if there is no need to scroll, amountToScroll will be <=0
		  scrollView.post(new Runnable() {
				@Override
				public
				void run() {
					 scrollView.scrollTo(0, amountToScroll);
				}
		  });
	 }


	 public
	 interface onLocationUpdateInterface {
		  public
		  void onLocationUpdate(List<BeaconData> beacons);
	 }


}
