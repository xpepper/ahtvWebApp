/*
 * InfoContent.java
 *
 * Created on 19 dicembre 2007, 15.22
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package it.matrix.alicehometv.pssc;

import it.matrix.alicehometv.logger.ActivityLogger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author alessandrobalasini
 */
public class InfoContent implements Comparable{
    
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
    /** Creates a new instance of InfoContent */
    public InfoContent() {
    }
    
    private String nome;
    private String dateFrom;
    private String dateTo;
    private String trasCode;
    private String price;
    private String rating;
    private String state;
    private String pay;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getTrasCode() {
        return trasCode;
    }

    public void setTrasCode(String trasCode) {
        this.trasCode = trasCode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

     public int compareTo(Object iContent) throws ClassCastException {
        try{
         if (!(iContent instanceof InfoContent))
          throw new ClassCastException("A iContent object expected.");
        //int iContentDateTo = sdf.parse((InfoContent) iContent).getDateTo());  
        String dataToNew = ((InfoContent) iContent).getDateTo();
//        ActivityLogger.debug("dataToNew==="+dataToNew);
        Date data = sdf.parse(dataToNew);
//        ActivityLogger.debug("this.dateTo==="+this.dateTo);
//        ActivityLogger.debug("this.nome==="+this.nome);
        if (sdf.parse(this.dateTo).getTime() - data.getTime() > 0){
//            ActivityLogger.debug("RETURN===-1");
            return -1;
        } else if (sdf.parse(this.dateTo).getTime() - data.getTime() < 0){
//            ActivityLogger.debug("RETURN===1");
            return 1;
        } else {
//            ActivityLogger.debug("RETURN===0");
            return 0;
        }
        
      } catch(ParseException ex){
          ActivityLogger.error("Eccezione compareTo - InfoContent==="+ex);
          return 0;
      }
     }

   
}
