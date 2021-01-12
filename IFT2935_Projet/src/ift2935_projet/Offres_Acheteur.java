/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ift2935_projet;

/**
 *
 * @author nahue
 */
public class Offres_Acheteur 
{
    private String objectId;
    private String priceAsk;
    private String status;
    
    public Offres_Acheteur()
    {
        this.objectId = "";
        this.priceAsk = "";
        this.status = "";
    }
    
    public Offres_Acheteur(String objectId, String priceAsk, String status) 
    {
        this.objectId = objectId;
        this.priceAsk = priceAsk;
        this.status = status;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getPriceAsk() {
        return priceAsk;
    }

    public void setPriceAsk(String priceAsk) {
        this.priceAsk = priceAsk;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
