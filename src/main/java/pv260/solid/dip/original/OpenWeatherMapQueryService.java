package pv260.solid.dip.original;

import org.springframework.stereotype.Component;
import pv260.solid.dip.original.model.Location;
import pv260.solid.dip.original.model.OpenWeatherMapResponse;

import javax.inject.Inject;
import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

@Component
public class OpenWeatherMapQueryService {
    private static final String SERVICE_URL = "http://api.openweathermap.org/data/2.5/forecast";
    private static final String API_KEY = "7c1e0b17c702be20b2d4f489df669b99";
    private static final int CONNECTION_TIMEOUT = 500;

    private JAXBContext jaxb;

    @Inject
    private ProxyService proxyService;

    public OpenWeatherMapQueryService() {
        try {
            this.jaxb = JAXBContext.newInstance(OpenWeatherMapResponse.class);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void setProxyService(ProxyService proxyService) {
        this.proxyService = proxyService;
    }

    public OpenWeatherMapResponse query(Location location) throws IOException {
        URL remote = new URL(buildUrl(API_KEY, location));
        Proxy proxy = proxyService==null?Proxy.NO_PROXY:proxyService.getProxy();
        HttpURLConnection connection = (HttpURLConnection) remote.openConnection(proxy);
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