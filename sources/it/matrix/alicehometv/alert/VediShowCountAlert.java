/*
 * VediShowCountAlert.java
 *
 * Created on 31 gennaio 2008, 16.40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package it.matrix.alicehometv.alert;

import java.util.Date;

/**
 *
 * @author AlessandroBalasini
 */
public class VediShowCountAlert {
    
    /** Creates a new instance of VediShowCountAlert */
    public VediShowCountAlert() {
    }
    
    private String startDate;
    private String endDate;
    private int countSmsSet;
    private int countSms;
    private int countEmailSet;
    private int countEmail;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getCountSmsSet() {
        return countSmsSet;
    }

    public void setCountSmsSet(int countSmsSet) {
        this.countSmsSet = countSmsSet;
    }

    public int getCountSms() {
        return countSms;
    }

    public void setCountSms(int countSms) {
        this.countSms = countSms;
    }

    public int getCountEmailSet() {
        return countEmailSet;
    }

    public void setCountEmailSet(int countEmailSet) {
        this.countEmailSet = countEmailSet;
    }

    public int getCountEmail() {
        return countEmail;
    }

    public void setCountEmail(int countEmail) {
        this.countEmail = countEmail;
    }
}
