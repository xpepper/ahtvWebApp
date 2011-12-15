/*
 * VediListAvvisami.java
 *
 * Created on 23 gennaio 2008, 10.23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package it.matrix.alicehometv.alert;

/**
 *
 * @author alessandrobalasini
 */
public class VediListAvvisami {
    
    /** Creates a new instance of VediListAvvisami */
    public VediListAvvisami() {
    }
    
    private String tipoServizio;
    private String tipoInvio;
    private int tipoConsenso;

    public String getTipoServizio() {
        return tipoServizio;
    }

    public void setTipoServizio(String tipoServizio) {
        this.tipoServizio = tipoServizio;
    }

    public String getTipoInvio() {
        return tipoInvio;
    }

    public void setTipoInvio(String tipoInvio) {
        this.tipoInvio = tipoInvio;
    }

    public int getTipoConsenso() {
        return tipoConsenso;
    }

    public void setTipoConsenso(int tipoConsenso) {
        this.tipoConsenso = tipoConsenso;
    }
}
