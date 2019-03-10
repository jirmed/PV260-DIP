package pv260.solid.dip.original;

import java.io.IOException;

public interface WeatherService {
    double findTomorrowsTemperatureAroundLunch() throws IOException;
    double findTomorrowsAverageTemperature() throws  IOException;
}
