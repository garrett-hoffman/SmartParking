package edu.vanderbilt.kharesp.gimbaltutorial;

/**
 * Created by mike on 5/2/15.
 */
public class BeaconData {

    public int signalStrenth;
    public String UUID;

    BeaconData(String UUID, int signalStrenth){
        this.UUID = UUID;
        this.signalStrenth = signalStrenth;
    }
}
