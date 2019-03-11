
package pv260.solid.dip.original;


public class Main {

    public static void main(String[] args) {
        ProxyService proxyService = new ProxyService();
        LocationService locationService = new LocationServiceImpl();
        TimeService timeService = new TimeServiceImpl();
        OpenWeatherMapQueryService openWeatherMapQueryService = new OpenWeatherMapQueryService();
        openWeatherMapQueryService.setProxyService(proxyService);
        WeatherService openWeatherMapService = new OpenWeatherMapService(openWeatherMapQueryService, timeService, locationService);
        RecommendedOutfitService outfitService = new RecommendedOutfitService(openWeatherMapService);
        DarkSkyForecastQueryService darkSkyForecastQueryService = new DarkSkyForecastQueryService();
        darkSkyForecastQueryService.setProxyService(proxyService);
        WeatherService darkSkyForecastService = new DarkSkyForecastService(darkSkyForecastQueryService, locationService, timeService);
        RecommendedLunchService lunchService = new RecommendedLunchService(darkSkyForecastService);
        System.out.println("o--         Awesome Lifestyle Page               --o");
        System.out.println("Tomorrow, it would be wise to dress like this:");
        System.out.println(outfitService.recommendOutfitForTomorrow());
        System.out.println("For lunch, we recommend that you:");
        System.out.println(lunchService.recommendLunchForTomorrow());
        System.out.println("o--                                              --o");
    }


}
