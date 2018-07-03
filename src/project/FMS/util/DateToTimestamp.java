package project.FMS.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateToTimestamp {
    public static Timestamp parse(Date date){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String str = sf.format(date);
        try {
            date = sf.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        return new Timestamp(date.getTime());
    }
}
