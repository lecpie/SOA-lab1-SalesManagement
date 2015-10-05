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
            orders        = new HashMap<Integer, OrderReference>();
            orderRequests = new HashMap<Integer, OrderRequest>();
            nextOrderId = 0;
        }
    }

    public OrderReference makeOrder(OrderRequest orderRequest, Client client) {
        OrderReference order = estimateOrderRequest(orderRequest);

        synchronized (orders) {
            int orderId = nextOrderId++;

            order.setId(orderId);
            orderRequest.setOrderId(orderId);
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

    public PaymentResponse payOrder(int orderId, PaymentInfo paymentInfo, PaymentPlan paymentPlan) {
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
        else if ((paymentResponse = doPayment(paymentInfo, order, paymentPlan)).isSuccess()) {
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

    public boolean cancelOrder(int orderId) {
        if (! orders.containsKey(orderId)) {
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

        deliveryDate.setTime(deliveryDate.getTime() + 30*24*60*60);

        order.setEstimatedDelivery(deliveryDate);

        return order;
    }

    PaymentResponse doPayment(PaymentInfo paymentInfo, OrderReference orderReference, PaymentPlan paymentPlan) {
        // payment successful and random  payment reference

        String paymentReference = Integer.toString((int) (Math.random() * 1000000000));

        PaymentResponse response = new PaymentResponse();
        response.setSuccess(true);
        response.setMessage(paymentReference);

        return response;
    }
}
