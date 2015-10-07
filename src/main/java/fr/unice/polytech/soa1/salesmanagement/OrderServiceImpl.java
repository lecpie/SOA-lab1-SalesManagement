package fr.unice.polytech.soa1.salesmanagement;

import fr.unice.polytech.soa1.salesmanagement.data.*;

import javax.jws.WebService;
import java.util.*;

@WebService(targetNamespace   = "http://informatique.polytech.unice.fr/soa1/salesmanagement/",
        portName          = "ExternalOrderServicetPort",
        serviceName       = "ExternalOrderService",
        endpointInterface = "fr.unice.polytech.soa1.salesmanagement.OrderService")
public class OrderServiceImpl implements OrderService {

    private static Map <Integer, OrderReference> orders;
    private static Map <Integer, OrderRequest> orderRequests;

    private static int nextOrderId = 0;

    public OrderServiceImpl() {
        if (orders == null || orderRequests == null) {
            resetData();
        }
    }

    public static void resetData() {
        orders        = new HashMap<Integer, OrderReference>();
        orderRequests = new HashMap<Integer, OrderRequest>();
        nextOrderId = 0;
    }

    synchronized private static int getnewId()  {
        return nextOrderId++;
    }

    public OrderReference makeOrder(OrderRequest orderRequest, Client client) {
        // Refuse missing informations or empty product request list
        if (orderRequest == null || orderRequest.getOrder() == null || orderRequest.getOrder().size() == 0)
            return null;

        // Refuse an order with a product item with 0 qty
        for (OrderItem orderItem : orderRequest.getOrder()) {
            if (orderItem.getQty() == 0)
                return null;
        }

        OrderReference order = estimateOrderRequest(orderRequest);

        int orderId = getnewId();

        order.setId(orderId);
        orderRequest.setOrderId(orderId);
        order.setOrderStatus(OrderStatus.REGISTERED);

        orders.put(orderId, order);
        orderRequests.put(orderId, orderRequest);

        return order;
    }

    public OrderReference fetchOrderReference(int orderId) {
        if (orders.containsKey(orderId)) {
            return orders.get(orderId);
        }

        return null;
    }

    public boolean changeOrderStatus(int orderId, OrderStatus orderStatus) {
        if (orders.containsKey(orderId)) {
            orders.get(orderId).setOrderStatus(orderStatus);
            return true;
        }

        return false;
    }

    public OrderRequest fetchOrderRequest(int orderId) {
        return orderRequests.get(orderId);
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

    public List<OrderRequest> fetchDeliveringOrders() {
        List<OrderRequest> deliveringRequests = new ArrayList<OrderRequest>();

        for (Integer orderId : orders.keySet()) {
            OrderReference orderReference = orders.get(orderId);

            if (orderReference.getOrderStatus() == OrderStatus.DELIVERING) {
                deliveringRequests.add(orderRequests.get(orderId));
            }
        }

        return deliveringRequests;
    }


    public boolean cancelOrder (int orderId) {
        if (!orders.containsKey(orderId) || orders.get(orderId).getOrderStatus() != OrderStatus.REGISTERED) {
            return false;
        }

        orders.remove(orderId);
        orderRequests.remove(orderId);

        return true;
    }

    // Mocks for buisness layer

    OrderReference estimateOrderRequest(OrderRequest orderRequest) {
        OrderReference order = new OrderReference();

        Date deliveryDate = new Date();

        // Each product type takes one week
        deliveryDate.setTime(deliveryDate.getTime() + orderRequest.getOrder().size() * 7*24*60*60);

        order.setEstimatedDelivery(deliveryDate);

        return order;
    }

}
