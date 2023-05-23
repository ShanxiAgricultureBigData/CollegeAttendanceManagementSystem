import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class Test1 {
    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH");
        String now = localDateTime.format(formatter);//当前小时
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        String week = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault());
        System.out.println(now);
        System.out.println(week);
    }
}
