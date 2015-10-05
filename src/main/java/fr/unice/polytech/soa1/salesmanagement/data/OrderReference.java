package fr.unice.polytech.soa1.salesmanagement.data;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@XmlType
public class OrderReference {

    private int id;
    private Date estimatedDelivery;
    private OrderStatus orderStatus;

    @XmlElement(name = "id", required = true)
    public int getId() { return id; }
    public void setId (int id) { this.id = id; }

    @XmlElement(name = "estimated_delivery")
    public Date getEstimatedDelivery() { return estimatedDelivery; }
    public void setEstimatedDelivery (Date estimatedDelivery) { this.estimatedDelivery = estimatedDelivery; }

    @XmlElement(name = "order_status", required = true)
    public OrderStatus getOrderStatus() { return orderStatus; }
    public void setOrderStatus (OrderStatus orderStatus) { this.orderStatus = orderStatus; }

}
