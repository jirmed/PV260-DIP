package pv260.solid.dip.original;

import pv260.solid.dip.original.model.Location;

public class LocationServiceImpl implements LocationService {

    @Override
    public Location getLocation() {
        return new Location(getLatitude(), getLongitude());
    }

    @Override
    public double getLatitude() {
        return 49.1973419;
    }

    @Override
    public double getLongitude() {
        return 16.6050103;
    }
}