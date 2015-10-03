package fr.unice.polytech.soa1.salesmanagement.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.awt.*;

@XmlType
public class OrderItem {
    private int productId;
    private int qty;
    private String option;

    @XmlElement(name = "product_id", required = true)
    public int getProductId() { return productId; }
    public void setProductId (int productId) { this.productId = productId; }

    @XmlElement(name = "qty", required = true)
    public int getQty() { return qty; }
    public void setQty (int qty) { this.qty = qty; }

    @XmlElement(name = "option", required = false)
    String getOption() { return option; }
    public void setOption (String option) { this.option = option; }

}
