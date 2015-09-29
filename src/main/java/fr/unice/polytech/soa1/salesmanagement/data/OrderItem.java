package fr.unice.polytech.soa1.salesmanagement.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class OrderItem {
    private int productId;
    private int qty;

    @XmlElement(name = "product_id", required = true)
    public int getProductId() { return productId; }
    public void setProductId (int productId) { this.productId = productId; }

    @XmlElement(name = "qty", required = true)
    public int getQty() { return qty; }
    public void setQty (int qty) { this.qty = qty; }
}
