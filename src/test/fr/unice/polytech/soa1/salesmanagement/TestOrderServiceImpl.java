package fr.unice.polytech.soa1.salesmanagement;

import fr.unice.polytech.soa1.salesmanagement.data.*;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestOrderServiceImpl extends TestCase {

    private OrderService service;

    protected void setUp() {
        OrderServiceImpl.resetData();
        service = new OrderServiceImpl();
    }

    @Test
    public void testOrderProcess() {
        OrderRequest orderRequest = createTestOrderRequest();
        Client client = TestClientServiceImpl.createTestClient();
        client.setId(0);

        OrderReference orderReference = service.makeOrder(orderRequest, client);

        assertNotNull("No order reference after order", orderReference);

        int orderId = orderReference.getId();

        assertEquals("Initial order status is not REGISTERED", OrderStatus.REGISTERED, orderReference.getOrderStatus());
        assertNotNull("Estimated delivery should not be null", orderReference.getEstimatedDelivery());

        OrderReference fetchedOrderRefernce = service.fetchOrderReference(orderId);
        assertEquals("Fetched order reference has unexpected id", orderId, fetchedOrderRefernce.getId());

        OrderRequest fetchedOrderRequest = service.fetchOrderRequest(orderId);
        assertEquals("fetched order request has unexpected id", orderId, fetchedOrderRequest.getOrderId());
        assertEquals("fetched order request has an unexpected number of order item", 1, fetchedOrderRequest.getOrder().size());

        int initProducingorderSize = service.fetchProducingOrders().size();

        service.changeOrderStatus(orderId, OrderStatus.PRODUCING);

        int afterChangeProducingOrderSize = service.fetchProducingOrders().size();

        assertEquals("producing order size not changed", initProducingorderSize + 1, afterChangeProducingOrderSize);

        int initDeliveringOrderSize = service.fetchDeliveringOrders().size();

        service.changeOrderStatus(orderId, OrderStatus.DELIVERING);

        assertEquals("order not removed from producing order list", initProducingorderSize, service.fetchProducingOrders().size());

        int afterChangeDeliveringOrdersSize = service.fetchDeliveringOrders().size();

        assertEquals("delivering order list not changed", initDeliveringOrderSize + 1, afterChangeDeliveringOrdersSize);

        service.changeOrderStatus(orderId, OrderStatus.DELIVERED);

        assertEquals("order not removed from delivering order list", initDeliveringOrderSize, service.fetchDeliveringOrders().size());
    }

    @Test
    public void testCancelOrder() {
        OrderRequest orderRequest = createTestOrderRequest();
        Client client = TestClientServiceImpl.createTestClient();
        client.setId(0);

        OrderReference orderReference = service.makeOrder(orderRequest, client);

        int orderId = orderReference.getId();

        assertTrue("Order cancellation reported failed", service.cancelOrder(orderId));

        assertFalse("Could cancell order again", service.cancelOrder(orderId));

    }

    public static OrderRequest createTestOrderRequest() {
        OrderRequest orderRequest = new OrderRequest();

        List<OrderItem> orderItems = new ArrayList<OrderItem>();

        OrderItem orderItem = new OrderItem();
        Product p = TestCatalogServiceImpl.createTestProduct();
        p.setId(0);

        orderItems.add(orderItem);

        orderItem.setProduct(p);
        orderItem.setQty(10);

        orderRequest.setOrder(orderItems);

        return orderRequest;
    }
}
