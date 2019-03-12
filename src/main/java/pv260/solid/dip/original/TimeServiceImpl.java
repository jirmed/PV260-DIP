package pv260.solid.dip.original;

import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class TimeServiceImpl implements TimeService {

    @Inject
    private Clock clock;

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    @Override
    public LocalDate getTomorrow() {
        return LocalDate.now(clock).plusDays(1);
    }

    @Override
    public boolean isTomorrowLunchTime(LocalDateTime from, LocalDateTime to) {
        LocalDateTime tomorrowLunchTime = getTomorrow().atTime(12, 0);
        return isBetweenInclusive(from, to, tomorrowLunchTime);

    }

    @Override
    public boolean isTomorrow(LocalDateTime dateTime) {
        return dateTime.toLocalDate().equals(getTomorrow());
    }

    private boolean isBetweenInclusive(LocalDateTime localFrom, LocalDateTime localTo, LocalDateTime tomorrowLunchTime) {
        return (localFrom.isBefore(tomorrowLunchTime) ||
                localFrom.isEqual(tomorrowLunchTime)) &&
                (localTo.isAfter(tomorrowLunchTime) ||
                        localTo.isEqual(tomorrowLunchTime));
    }
}