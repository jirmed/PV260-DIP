package pv260.solid.dip.original;

import org.springframework.stereotype.Component;
import pv260.solid.dip.original.model.DarkSkyForecastResponse;
import pv260.solid.dip.original.model.Location;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Component
@Named("DarkSkyForecast")
public class DarkSkyForecastService implements WeatherService {

    @Inject
    private final DarkSkyForecastQueryService darkSkyForecastQueryService;

    @Inject
    private final LocationService locationService;

    @Inject
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
        Location location = locationService.getLocation();
        LocalDate tomorrow = timeService.getTomorrow();
        List<DarkSkyForecastResponse.DailyData> dailyDataList = darkSkyForecastQueryService.query(location, tomorrow)
                .getDaily()
                .getData();
        return dailyDataList
                .stream()
                .filter(dailyData -> timeService.isTomorrow(dailyData.getLocalDateTime()))
                .mapToDouble(value -> (value.getTemperatureMax() + value.getTemperatureMin()) / 2)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("External service did not return record for tomorrow"));
    }

}
