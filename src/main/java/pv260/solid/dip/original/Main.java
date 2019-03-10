
package pv260.solid.dip.original;


public class Main {

    public static void main(String[] args) {
        LocationService locationService = new LocationServiceImpl();
        TimeService timeService = new TimeServiceImpl();
        WeatherService openWeatherMapService = new OpenWeatherMapService(timeService, locationService);
        RecommendedOutfitService outfitService = new RecommendedOutfitService(openWeatherMapService);
        WeatherService darkSkyForecastService = new DarkSkyForecastService(locationService, timeService);
        RecommendedLunchService lunchService = new RecommendedLunchService(darkSkyForecastService);
        System.out.println("o--         Awesome Lifestyle Page               --o");
        System.out.println("Tomorrow, it would be wise to dress like this:");
        System.out.println(outfitService.recommendOutfitForTomorrow());
        System.out.println("For lunch, we recommend that you:");
        System.out.println(lunchService.recommendLunchForTomorrow());
        System.out.println("o--                                              --o");
    }

}
