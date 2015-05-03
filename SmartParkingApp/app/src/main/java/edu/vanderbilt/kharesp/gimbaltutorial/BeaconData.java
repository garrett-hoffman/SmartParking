package edu.vanderbilt.kharesp.gimbaltutorial;

/**
 * Created on 2015/05/02.
 * <p/>
 * Contributors:
 * Michael A. Walker
 * Shweta P. Khare
 */
public class BeaconData {

    public int signalStrenth;
    public String ID1;

    BeaconData(String ID1, int signalStrenth){
        this.ID1 = ID1;
        this.signalStrenth = signalStrenth;
    }
}
