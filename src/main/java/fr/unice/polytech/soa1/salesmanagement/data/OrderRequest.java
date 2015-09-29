package fr.unice.polytech.soa1.salesmanagement.data;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlType
public class OrderRequest {

    private List<OrderItem> order = new ArrayList<OrderItem>();

    @XmlElement(name = "order_item", required = true)
    public List<OrderItem> getOrderData() {

        return order;
    }
}
