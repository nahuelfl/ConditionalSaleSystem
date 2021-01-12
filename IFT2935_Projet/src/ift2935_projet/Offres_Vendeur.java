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
public class Offres_Vendeur 
{
    private String objectId;
    private String objectName;
    private String priceAsk;
    private String userId;
    private String offer;
    
    public Offres_Vendeur()
    {
        this.objectId = "";
        this.objectName = "";
        this.priceAsk = "";
        this.userId = "";
        this.offer = "";

    }
    
    public Offres_Vendeur(String objectId, String objectName, String priceAsk, String userId, String offer) 
    {
        this.objectId = objectId;
        this.objectName = objectName;
        this.priceAsk = priceAsk;
        this.userId = userId;
        this.offer = offer;
    }

    public String getObjectId() 
    {
        return objectId;
    }

    public void setObjectId(String objectId) 
    {
        this.objectId = objectId;
    }

    public String getObjectName() 
    {
        return objectName;
    }

    public void setObjectName(String objectName) 
    {
        this.objectName = objectName;
    }

    public String getPriceAsk() 
    {
        return priceAsk;
    }

    public void setPriceAsk(String priceAsk) 
    {
        this.priceAsk = priceAsk;
    }

    public String getUserId() 
    {
        return userId;
    }

    public void setUserId(String userId) 
    {
        this.userId = userId;
    }
    
     public String getOffer() 
    {
        return offer;
    }

    public void setOffer(String offer) 
    {
        this.offer = offer;
    }
    
    
}

