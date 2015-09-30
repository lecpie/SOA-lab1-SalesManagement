package fr.unice.polytech.soa1.salesmanagement;

import fr.unice.polytech.soa1.salesmanagement.data.*;

import javax.jws.WebService;
import java.util.*;


@WebService(targetNamespace   = "http://informatique.polytech.unice.fr/soa1/salesmanagement/",
            portName          = "ExternalSalesManagertPort",
            serviceName       = "ExternalSalesManagerService",
            endpointInterface = "fr.unice.polytech.soa1.salesmanagement.SalesManagementService")
public class SalesManagementImpl implements SalesManagementService {

    private Catalog catalog;
    private Map <Integer, OrderReference> orders;
    private Map <Integer, OrderRequest> orderRequests;

    static int nextOrderId = 0;

    public SalesManagementImpl() {

        orders = new HashMap<Integer, OrderReference>();
        catalog = new Catalog();
        orderRequests = new HashMap<Integer, OrderRequest>();

        // Create some data for testing

        // Create some products

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

    public int addItemCatalog(Product newProduct) {
        catalog.addProduct(newProduct);

        return newProduct.getId();
    }

    public OrderReference makeOrder(OrderRequest orderRequest) {
        OrderReference order = estimateOrderRequest(orderRequest);

        synchronized (orders) {
            int orderId = nextOrderId++;

            order.setId(orderId);
            order.setOrderStatus(OrderStatus.REGISTERED);

            orders.put(orderId, order);
            orderRequests.put(orderId, orderRequest);
        }

        return order;
    }

    public OrderReference fetchOrderReference(int orderId) {
        if (orders.containsKey(orderId)) {
            return orders.get(orderId);
        }

        return null;
    }

    public PaymentResponse payOrder(int orderId, PaymentInfo paymentInfo) {
        PaymentResponse paymentResponse = new PaymentResponse();
        OrderReference order;

        if (!orders.containsKey(orderId)) {
            paymentResponse.setSuccess(false);
            paymentResponse.setMessage("NO ORDER");
        }
        else if ((order = orders.get(orderId)).getOrderStatus() != OrderStatus.REGISTERED) {
            paymentResponse.setSuccess(false);
            paymentResponse.setMessage("ALREADY PAID");
        }
        else {
            paymentResponse = doPayment(paymentInfo, order);
            order.setOrderStatus(OrderStatus.PRODUCING);
        }

        return paymentResponse;
    }

    public List<OrderRequest> fetchProducingOrders() {
        List<OrderRequest> producingRequests = new ArrayList<OrderRequest>();

        for (Integer orderId : orders.keySet()) {
            OrderReference orderReference = orders.get(orderId);

            if (orderReference.getOrderStatus() == OrderStatus.PRODUCING) {
                producingRequests.add(orderRequests.get(orderId));
            }
        }

        return producingRequests;
    }

    public boolean setOrderDelivering(int orderId) {
        OrderReference orderReference = orders.get(orderId);

        if (orderReference.getOrderStatus() == OrderStatus.PRODUCING) {
            orderReference.setOrderStatus(OrderStatus.DELIVERING);

            return true;
        }

        return false;
    }

    public boolean setOrderDelivered(int orderId) {
        OrderReference orderReference = orders.get(orderId);

        if (orderReference.getOrderStatus() == OrderStatus.DELIVERING) {
            orderReference.setOrderStatus(OrderStatus.DELIVERED);

            return true;
        }

        return false;    }

    // Mock for buisness layer
    OrderReference estimateOrderRequest(OrderRequest orderRequest) {
        OrderReference order = new OrderReference();

        double price = 0;
        for (OrderItem orderItem : orderRequest.getOrder()) {
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

    PaymentResponse doPayment(PaymentInfo paymentInfo, OrderReference orderReference) {
        // payment successful and random  payment reference

        String paymentReference = Integer.toString((int) (Math.random() * 1000000000));

        PaymentResponse response = new PaymentResponse();
        response.setSuccess(true);
        response.setMessage(paymentReference);

        return response;
    }
}
