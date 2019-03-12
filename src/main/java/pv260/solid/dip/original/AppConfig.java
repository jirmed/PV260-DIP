package pv260.solid.dip.original;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
@ComponentScan(basePackages = "pv260.solid.dip.original")
public class AppConfig {
    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

}
