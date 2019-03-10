package pv260.solid.dip.original.model;

import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class OpenWeatherMapDao {
    private static final String SERVICE_URL = "http://api.openweathermap.org/data/2.5/forecast";
    private static final String API_KEY = "7c1e0b17c702be20b2d4f489df669b99";
    private static final int CONNECTION_TIMEOUT = 500;
    private JAXBContext jaxb;

    public OpenWeatherMapDao() {
        try {
            this.jaxb = JAXBContext.newInstance(OpenWeatherMapResponse.class);
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }

    public OpenWeatherMapResponse query(Location location) throws IOException {
        URL remote = new URL(buildUrl(API_KEY, location));
        HttpURLConnection connection = (HttpURLConnection) remote.openConnection();
        connection.setConnectTimeout(CONNECTION_TIMEOUT);
        try (InputStream responseStream = connection.getInputStream()) {
            StreamSource responseSource = new StreamSource(responseStream);
            Unmarshaller unmarshaller = this.jaxb.createUnmarshaller();
            JAXBElement<OpenWeatherMapResponse> responseParsed = unmarshaller.unmarshal(responseSource,
                    OpenWeatherMapResponse.class);
            return (OpenWeatherMapResponse) JAXBIntrospector.getValue(responseParsed);
        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }

    private String buildUrl(String apiKey, Location location) {
        return SERVICE_URL +
                "?lat=" + location.getLatitude() +
                "&lon=" + location.getLongitude() +
                "&mode=xml" +
                "&units=metric" +
//                                  .append("&cnt=2")
                "&appid=" + apiKey;
    }
}