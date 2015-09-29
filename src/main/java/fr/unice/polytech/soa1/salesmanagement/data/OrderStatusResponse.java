package fr.unice.polytech.soa1.salesmanagement.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@XmlType
public class OrderStatusResponse {
    private int orderId;
    private OrderStatus orderStatus;
    private Date estimatedDelivery;

    @XmlElement(name = "order_id", required = true)
    public int getOrderId() { return orderId; }
    public void setOrderId (int orderId) { this.orderId = orderId; }

    @XmlElement(name = "order_status", required = true)
    public OrderStatus getOrderStatus() { return orderStatus; }
    public void setOrderStatus (OrderStatus orderStatus) { this.orderStatus = orderStatus; }

    @XmlElement(name = "estimated_delivery", required = true)
    public Date getEstimatedDelivery() { return estimatedDelivery; }
    public void setEstimatedDelivery (Date estimatedDelivery) { this.estimatedDelivery = estimatedDelivery; }

}
