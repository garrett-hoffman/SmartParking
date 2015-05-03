package edu.vanderbilt.kharesp.gimbaltutorial;

import android.os.RemoteException;

/**
 * Created on 2015/05/02.
 * <p/>
 * Contributors:
 * Michael A. Walker
 * Shweta P. Khare
 */
public interface BeaconControlActivityInterface {

    /**
     * Start or Stop Foreground/Background service for
     */
    public void startBeaconSearching();
    public void stopBeaconSearching();

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
    public void setForegroundScanPeriod(long time) throws RemoteException;
    public void setForegroundBetweenScanPeriod(long time) throws RemoteException;

    /*
     * set the service to (or not) act in 'background' mode, which uses less power
     */
    public void setBackgroundMode(boolean mode);
    /*
     * Set the period to scan when in Background Mode
     */
    public void setBackgroundScanPeriod(long time) throws RemoteException;
    public void setBackgroundBetweenScanPeriod(long time) throws RemoteException;

}
