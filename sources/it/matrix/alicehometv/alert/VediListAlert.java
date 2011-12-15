/*
 * VediListAlert.java
 *
 * Created on 10 gennaio 2008, 14.47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package it.matrix.alicehometv.alert;

/**
 *
 * @author alessandrobalasini
 */
public class VediListAlert {
    
    /** Creates a new instance of VediListAlert */
    public VediListAlert() {
    }
    
    private Integer assetId;
    private String title;
    private String channelName;
    private String startDate;
    private String startHour;
    private String idTipoInvio;
    private String status;
    private int idChannel;

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getIdTipoInvio() {
        return idTipoInvio;
    }

    public void setIdTipoInvio(String idTipoInvio) {
        this.idTipoInvio = idTipoInvio;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIdChannel() {
        return idChannel;
    }

    public void setIdChannel(int idChannel) {
        this.idChannel = idChannel;
    }

      
}
