package fr.unice.polytech.soa1.salesmanagement.data;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlType
public class Product {

    private int id;
    private String name;
    private ProductType productType;
    private double price;
    private String description;
    private String collection;
    private String imageLink;

    @XmlElement(name = "id", required = true)
    public int  getId() { return id; }
    public void setId (int id) { this.id = id; }

    @XmlElement(name = "name", required = true)
    public String getName() { return name; }
    public void   setName (String name) { this.name = name; }

    @XmlElement(name = "product_type", required = true)
    public ProductType getProductType() { return productType; }
    public void        setProductType (ProductType productType) { this.productType = productType; }

    @XmlElement(name = "price", required = true)
    public double getPrice() { return price; }
    public void   setPrice (double price) { this.price = price; }

    @XmlElement(name = "description", required = true)
    public String getDescription() { return description; }
    public void   setDescription (String description) { this.description = description; }

    @XmlElement(name = "collection", required = true)
    public String getCollection() { return collection; }
    public void   setCollection (String collection) { this.collection = collection; }

    @XmlElement(name="image_link", required = true)
    public String getImageLink() { return imageLink; }
    public void   setImageLink (String imageLink) { this.imageLink = imageLink; }

}
