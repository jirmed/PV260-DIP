package pv260.solid.dip.original;

import java.io.IOException;

import pv260.solid.dip.original.model.OpenWeatherMapDao;
import pv260.solid.dip.original.model.OpenWeatherMapResponse;

public class OpenWeatherMapService implements WeatherService {

    private final OpenWeatherMapDao openWeatherMapDao = new OpenWeatherMapDao();
    private final TimeService timeService;
    private final LocationService locationService;

    public OpenWeatherMapService(TimeService timeService, LocationService locationService) {
        this.timeService = timeService;
        this.locationService = locationService;
    }

    @Override
    public double findTomorrowsTemperatureAroundLunch() throws IOException {
        OpenWeatherMapResponse query = openWeatherMapDao.query(locationService.getLocation());
        return query.getTimes().stream()
                .filter(forecastTime -> this.timeService.isTomorrowLunchTime(forecastTime.getFrom(), forecastTime.getTo()))
                .mapToDouble(temperature -> temperature.getTemperature().getValue())
                .findFirst()
                .orElseThrow(IOException::new);
    }

    @Override
    public double findTomorrowsAverageTemperature() throws IOException {
        return openWeatherMapDao.query(locationService.getLocation()).getTimes().stream()
                .filter(forecastTime -> this.timeService.isTomorrow(forecastTime.getLocalDateTime()))
                .mapToDouble(value -> value.getTemperature().getValue())
                .average()
                .orElseThrow(IOException::new);

    }

}
