package it.matrix.alicehometv.pssc;

import it.matrix.alicehometv.logger.ActivityLogger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GetTimestamp {
    
    protected static final SimpleDateFormat dateTimeFmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        
    /** Creates a new instance of GetTimestamp */
    public GetTimestamp() {
    }
    
    public String getTimeStamp(){
         //calcolo del TimeStamp
        ActivityLogger.info("enter getTimeStamp");
        String timeStamp;
        Date cal = Calendar.getInstance().getTime();
        timeStamp=dateTimeFmt.format(cal);
        ActivityLogger.debug("timeStamp==="+timeStamp);        
        return(timeStamp);        
    }
}
