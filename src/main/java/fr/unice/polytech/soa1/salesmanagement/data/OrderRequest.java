package fr.unice.polytech.soa1.salesmanagement.data;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlType
public class OrderRequest {

    private List <OrderItem> order;
    private String address;

    @XmlElement(name = "order_item", required = true)
    public List<OrderItem> getOrder() { return order; }
    public void setOrder (List <OrderItem> order) { this.order = order; }

    @XmlElement(name = "address", required = true)
    public String getAddress() { return address; }
    public void setAddress (String address) { this.address = address; }
}
