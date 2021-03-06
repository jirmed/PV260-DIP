package pv260.solid.dip.original;

import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Component
public class RecommendedOutfitService {

    @Inject
    @Named("OpenWeatherMap")
    private final WeatherService weatherService;

    public RecommendedOutfitService(WeatherService weatherService) {
        this.weatherService = weatherService;
    }


    public String recommendOutfitForTomorrow() {
        try {
            double averageTemperature = weatherService.findTomorrowsAverageTemperature();
            if (averageTemperature < -10) {
                return "It will be super cold, weak a jacket or two!";
            } else if (averageTemperature < 0) {
                return "It will be rather chilly, better wear a coat.";
            } else if (averageTemperature < 15) {
                return "Weather will be very pleasant, weak a light jacket and jeans.";
            } else if (averageTemperature < 25) {
                return "Tomorrow will be a beautiful day, shirt and short should be fine.";
            } else {
                return "It will be really hot, better grab a swimsuit and run to the beach!";
            }
        } catch (IOException e) {
            return "Error when calculating best outfit for tomorrow";
        }
    }


}
