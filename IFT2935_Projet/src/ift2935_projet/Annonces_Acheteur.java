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
public class Annonces_Acheteur 
{
    private String objectId;
    private String objectName;
    private String specs;
    private String priceAsked;
    private String location;
    
    public Annonces_Acheteur()
    {
        this.objectId = "";
        this.objectName = "";
        this.priceAsked = "";
        this.specs = "";
        this.location = "";
    }
    
    public Annonces_Acheteur(String objectId, String objectName, String specs, String priceAsked, String location) 
    {
        this.objectId = objectId;
        this.objectName = objectName;
        this.priceAsked = priceAsked;
        this.specs = specs;
        this.location = location;
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

    public String getPriceAsked() 
    {
        return priceAsked;
    }

    public void setPriceAsked(String priceAsked) 
    {
        this.priceAsked = priceAsked;
    }

    public String getSpecs() 
    {
        return specs;
    }

    public void setSpecs(String specs) 
    {
        this.specs = specs;
    }

    public String getLocation() 
    {
        return location;
    }

    public void setLocation(String location) 
    {
        this.location = location;
    }
    
    
}

