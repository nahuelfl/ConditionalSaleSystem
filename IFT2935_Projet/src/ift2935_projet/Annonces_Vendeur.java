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
public class Annonces_Vendeur 
{
    private String objectId;
    private String objectName;
    private String estVendu;
    private String price;
    private String spec;

    
    public Annonces_Vendeur()
    {
        this.objectId = "";
        this.objectName = "";
        this.estVendu = "";
        this.price = "";
        this.spec = "";
 
    }
    
    public Annonces_Vendeur(String objectId, String objectName, String price, String estVendu,  String spec) 
    {
        this.objectId = objectId;
        this.objectName = objectName;
        this.estVendu = estVendu;
        this.price = price;
        this.spec = spec;
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

    public String getEstVendu() 
    {
        return estVendu;
    }

    public void setEstVendu(String estVendu) 
    {
        this.estVendu = estVendu;
    }

    public String getPrice() 
    {
        return price;
    }

    public void setPrice(String price) 
    {
        this.price = price;
    }
    
    public String getSpec() 
    {
        return spec;
    }

    public void setSpec(String spec) 
    {
        this.spec = spec;
    }
    
}

