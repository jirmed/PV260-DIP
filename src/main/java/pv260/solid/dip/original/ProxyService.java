package pv260.solid.dip.original;


import org.springframework.stereotype.Component;


import java.io.InputStream;
import java.net.*;
import java.util.Properties;

@Component
public class ProxyService {
    private final Proxy proxy;

    public ProxyService() {
        this.proxy = getProxyFromPropertiesFile();
    }

    public Proxy getProxy() {
        return proxy;
    }

    private Proxy getProxyFromPropertiesFile() {

        InputStream inputStream;
        Proxy proxy = Proxy.NO_PROXY;

        try {
            Properties prop = new Properties();
            String propFileName = "proxy.properties";
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                return Proxy.NO_PROXY;
            }

            String type = prop.getProperty("proxy_type");
            String host = prop.getProperty("proxy_host");
            String port = prop.getProperty("proxy_port");
            proxy = new Proxy(Proxy.Type.valueOf(type), new InetSocketAddress(host, Integer.parseInt(port)));

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return proxy;
    }

}
