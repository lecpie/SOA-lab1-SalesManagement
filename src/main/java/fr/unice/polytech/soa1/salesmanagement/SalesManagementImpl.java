package fr.unice.polytech.soa1.salesmanagement;

import fr.unice.polytech.soa1.salesmanagement.data.*;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@WebService(targetNamespace   = "http://informatique.polytech.unice.fr/soa1/salesmanagement/",
            portName          = "ExternalSalesManagertPort",
            serviceName       = "ExternalSalesManagerService",
            endpointInterface = "fr.unice.polytech.soa1.salesmanagement.SalesManagementService")
public class SalesManagementImpl implements SalesManagementService {

    private Catalog catalog;
    private List<OrderReference> orders;

    public SalesManagementImpl() {

        orders = new ArrayList<OrderReference>();
        catalog = new Catalog();

        Product p1 = new Product();
        p1.setName("Nice chair");
        p1.setPrice(40.0);
        p1.setProductType(ProductType.CHAIR);
        p1.setImageLink("nicedomain.nice/product1.jpg");
        p1.setCollection("Nice collection");
        p1.setDescription("This is a nice chair ... and cheap");
        catalog.addProduct(p1);

        Product p2 = new Product();
        p2.setName("Nice table");
        p2.setPrice(70);
        p2.setProductType(ProductType.TABLE);
        p2.setImageLink("nicedomain.nice/product2.jpg");
        p2.setCollection("Nice collection");
        p2.setDescription("This is a nice table ... and cheap");
        catalog.addProduct(p2);

        Product p3 = new Product();
        p3.setName("Very nice chair");
        p3.setPrice(80);
        p3.setProductType(ProductType.CHAIR);
        p3.setImageLink("nicedomain.nice/product3.jpg");
        p3.setCollection("Very nice collection");
        p3.setDescription("This is a very nice chair !");
        catalog.addProduct(p3);

        Product p4 = new Product();
        p4.setName("Very nice table");
        p4.setPrice(140);
        p4.setProductType(ProductType.TABLE);
        p4.setImageLink("nicedomain.nice/product4.jpg");
        p4.setCollection("Very nice collection");
        p4.setDescription("This is a very nice table !");
        catalog.addProduct(p4);

    }

    public Catalog fetchCatalog() {
        return catalog;
    }

    public OrderReference makeOrder(OrderRequest orderRequest) {
        OrderReference order = estimateOrderRequest(orderRequest);

        order.setOrderRequest(orderRequest);

        synchronized (orders) {
            order.setId(orders.size());
            orders.add(order);
        }

        return order;
    }

    // Mock
    OrderReference estimateOrderRequest(OrderRequest orderRequest) {
        OrderReference order = new OrderReference();

        double price = 0;
        for (OrderItem orderItem : orderRequest.getOrderData()) {
            Product p = catalog.findId(orderItem.getProductId());

            if (p == null) return null;

            price += p.getPrice() * orderItem.getQty();
        }

        Date deliveryDate = new Date();

        deliveryDate.setTime(deliveryDate.getTime() + 30*24*60*60);

        order.setEstimatedDelivery(deliveryDate);
        order.setPrice(price);

        return order;
    }
}
