package fr.unice.polytech.soa1.salesmanagement.data;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@XmlType
public class OrderReference {

    private int id;
    private Date estimatedDelivery;
    private double price;
    private OrderRequest orderRequest;

    @XmlElement(name = "id", required = true)
    public int getId() { return id; }
    public void setId (int id) { this.id = id; }

    @XmlElement(name = "estimated_delivery")
    public Date getEstimatedDelivery() { return estimatedDelivery; }
    public void setEstimatedDelivery (Date estimatedDelivery) { this.estimatedDelivery = estimatedDelivery; }

    @XmlElement(name = "price")
    public double getPrice() { return price; }
    public void   setPrice (double price) { this.price = price; }


    public OrderRequest getOrderRequest() { return orderRequest; }
    public void         setOrderRequest (OrderRequest orderRequest) { this.orderRequest = orderRequest; }

}
