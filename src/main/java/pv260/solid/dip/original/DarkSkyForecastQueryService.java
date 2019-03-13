package pv260.solid.dip.original;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import pv260.solid.dip.original.model.DarkSkyForecastResponse;
import pv260.solid.dip.original.model.Location;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class DarkSkyForecastQueryService {
    private static final String SERVICE_URL = "https://api.forecast.io/forecast/";
    private static final String API_KEY = "fc2a39c15866166ea203cabadf93a236";
    private static final int CONNECTION_TIMEOUT = 500;

    @Inject
    private  ProxyService proxyService;
    private final Gson jsonParser;


    public DarkSkyForecastQueryService() {
        this.jsonParser = new Gson();
    }

    public DarkSkyForecastResponse query(Location location, LocalDate localDate) throws IOException {
        try (Reader responseReader = getReader(localDate, location)) {
            return parseDarkSkyForecastResponse(responseReader);
        }
    }

    private DarkSkyForecastResponse parseDarkSkyForecastResponse(Reader responseReader) {
        return jsonParser.fromJson(responseReader, DarkSkyForecastResponse.class);
    }

    private Reader getReader(LocalDate localDate, Location location) throws IOException {
        URL remote = new URL(buildUrl(API_KEY,
                location,
                this.getTargetTime(localDate),
                "units=si"));

        Proxy proxy = proxyService.getProxy();
        HttpURLConnection connection = (HttpURLConnection) remote.openConnection(proxy);
        connection.setConnectTimeout(CONNECTION_TIMEOUT);
        InputStream inputStream = connection.getInputStream();
        return new InputStreamReader(inputStream, StandardCharsets.UTF_8);
    }

    private String buildUrl(String apiKey, Location location, String time, String... queryParams) {
        return Arrays
                .stream(queryParams)
                .collect(Collectors.joining("&", SERVICE_URL
                        + apiKey + '/'
                        + location.getLatitude() + ','
                        + location.getLongitude() + ','
                        + time + '?', ""));
    }

    private String getTargetTime(LocalDate localDate) {
        return localDate.plusDays(1)
                .atTime(12, 0)
                .format(DateTimeFormatter.ISO_DATE_TIME);
    }
}