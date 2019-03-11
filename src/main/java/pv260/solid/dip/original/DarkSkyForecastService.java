package pv260.solid.dip.original;

import java.io.IOException;

public class DarkSkyForecastService implements WeatherService {

    private final DarkSkyForecastQueryService darkSkyForecastQueryService;

    private final LocationService locationService;
    private final TimeService timeService;

    public DarkSkyForecastService(DarkSkyForecastQueryService darkSkyForecastQueryService, LocationService locationService, TimeService timeService) {
        this.darkSkyForecastQueryService = darkSkyForecastQueryService;
        this.locationService = locationService;
        this.timeService = timeService;
    }

    @Override
    public double findTomorrowsTemperatureAroundLunch() throws IOException {
        return findTomorrowsAverageTemperature();
    }

    @Override
    public double findTomorrowsAverageTemperature() throws IOException {
        return darkSkyForecastQueryService.query(locationService.getLocation(), timeService.getTomorrow()).getDaily().getData()
                .stream().filter(dailyData -> timeService.isTomorrow(dailyData.getLocalDateTime()))
                .mapToDouble(value -> (value.getTemperatureMax() + value.getTemperatureMin()) / 2)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("External service did not return record for tomorrow"));
    }

}
