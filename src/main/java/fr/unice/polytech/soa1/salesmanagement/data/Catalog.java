package fr.unice.polytech.soa1.salesmanagement.data;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@XmlType
public class Catalog {
    private List<Product> products = new ArrayList<Product>();

    @XmlElement(name = "products", required = true)
    public List<Product> getProducts() { return (List<Product>) products; }

    synchronized
    public void addProduct(Product product) {

        synchronized (products) {
            int id = products.size();
            product.setId(id);

            this.products.add(product);
        }

    }

    synchronized
    public boolean removeProduct(int id) {

        for (Iterator<Product> it = products.iterator(); it.hasNext(); ) {
            Product p = it.next();
            if (p.getId() == id) {
                it.remove();

                return true;
            }
        }

        return false;
    }
}
