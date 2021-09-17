package guru.springframework.msscbrewery.web.mapper.helper;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class DateMapper {

    public Timestamp toTimestamp(OffsetDateTime offsetDateTime) {

        if (offsetDateTime == null) {
            return null;
        }

        return Timestamp.valueOf(offsetDateTime.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
    }

    public OffsetDateTime toOffsetDateTime(Timestamp ts) {

        if (ts == null) {
            return null;
        }
        return OffsetDateTime.of(ts.toLocalDateTime().getYear(), ts.toLocalDateTime().getMonthValue(),
                                 ts.toLocalDateTime().getDayOfMonth(), ts.toLocalDateTime().getHour(), ts.toLocalDateTime().getMinute(),
                                 ts.toLocalDateTime().getSecond(), ts.toLocalDateTime().getNano(), ZoneOffset.UTC);
    }
}
