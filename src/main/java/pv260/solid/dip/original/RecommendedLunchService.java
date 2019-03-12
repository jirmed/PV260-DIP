
package pv260.solid.dip.original;

import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Component
public class RecommendedLunchService {
    @Inject
    @Named("DarkSkyForecast")
    private final WeatherService weatherService;

    public RecommendedLunchService(WeatherService weatherService) {

        this.weatherService = weatherService;
    }

    public String recommendLunchForTomorrow() {
         try {
            double temperatureAroundLunch = weatherService.findTomorrowsTemperatureAroundLunch();
            if (temperatureAroundLunch < -15) {
                return "You will need a lot of energy to keep warm, tomorrow you should eat something very nutritious.";
            } else if (temperatureAroundLunch < 15) {
                return "No day like tomorrow for some chicken.";
            } else if (temperatureAroundLunch < 30) {
                return "It will be quite hot tomorrow, be sure to order a cold beer with your lunch.";
            } else {
                return "You probably will not be hungry at all in such a hot weather. Just get an ice cream!";
            }
        } catch (IOException e) {
            return "Error when calculating best lunch recommendation for tomorrow";
        }
    }


}
