package pv260.solid.dip.original.model;

import java.time.LocalDateTime;
import java.util.Map;

public class Weather {
    private final Map<LocalDateTime, Double> temperatureMap;

    public Weather(Map<LocalDateTime, Double> temperatureMap) {
        this.temperatureMap = temperatureMap;
    }

    public Map<LocalDateTime, Double> getTemperatureMap() {
        return temperatureMap;
    }
}
