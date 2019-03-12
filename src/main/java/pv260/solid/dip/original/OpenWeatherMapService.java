package pv260.solid.dip.original;

import java.io.IOException;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import pv260.solid.dip.original.model.OpenWeatherMapResponse;

import javax.inject.Inject;
import javax.inject.Named;

@Component
@Primary
@Named("OpenWeatherMap")
public class OpenWeatherMapService implements WeatherService {

    @Inject
    private final OpenWeatherMapQueryService openWeatherMapQueryService;

    @Inject
    private final TimeService timeService;

    @Inject
    private final LocationService locationService;


    public OpenWeatherMapService(OpenWeatherMapQueryService openWeatherMapQueryService, TimeService timeService, LocationService locationService) {
        this.openWeatherMapQueryService = openWeatherMapQueryService;
        this.timeService = timeService;
        this.locationService = locationService;
    }

    @Override
    public double findTomorrowsTemperatureAroundLunch() throws IOException {
        OpenWeatherMapResponse query = openWeatherMapQueryService.query(locationService.getLocation());
        return query.getTimes().stream()
                .filter(forecastTime -> this.timeService.isTomorrowLunchTime(forecastTime.getFrom(), forecastTime.getTo()))
                .mapToDouble(temperature -> temperature.getTemperature().getValue())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("External service did not return record for tomorrow around lunch"));
    }

    @Override
    public double findTomorrowsAverageTemperature() throws IOException {
        return openWeatherMapQueryService.query(locationService.getLocation()).getTimes().stream()
                .filter(forecastTime -> this.timeService.isTomorrow(forecastTime.getLocalDateTime()))
                .mapToDouble(value -> value.getTemperature().getValue())
                .average()
                .orElseThrow(() -> new IllegalStateException("External service did not return record for tomorrow"));
    }

}
