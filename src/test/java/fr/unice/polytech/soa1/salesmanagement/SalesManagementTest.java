package fr.unice.polytech.soa1.salesmanagement;

import fr.unice.polytech.soa1.salesmanagement.data.*;
import junit.framework.TestCase;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesManagementTest extends TestCase {

    private SalesManagementService service = null;
    private String testAddress;
    private PaymentInfo testPaymentInfo;

    public void setUp() {
        service = new SalesManagementImpl();
        testAddress = "123 Lambda street - 12345 Lambdatown";

        testPaymentInfo = new PaymentInfo();
        testPaymentInfo.setCardNumber("1234123412341234");
        testPaymentInfo.setCardExpire("1217");
        testPaymentInfo.setCsc("123");
    }

    @Test
    public void testInitialCatalog() {
        Catalog catalog = service.fetchCatalog();

        // Test we have at least 4 products
        // We could test we have exactly 4 products but the following test cases
        // only requires that we have at least 4, products.
        // The point is to avoid freezing too much the specification on initial items.

        assertTrue("Invalid initial catalog initialization", catalog.getProducts().size() >= 4);
    }

    private List <OrderItem> buildTestOrder() {

        Catalog catalog = service.fetchCatalog();
        List <OrderItem> orderItems = new ArrayList<OrderItem>();

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setProductId(catalog.getProducts().get(0).getId());
        orderItem1.setQty(10);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setProductId(catalog.getProducts().get(1).getId());
        orderItem2.setQty(20);
        orderItem2.setOption("Black");

        orderItems.add (orderItem1);
        orderItems.add (orderItem2);

        return orderItems;
    }

    private OrderRequest buildTestOrderRequest(List <OrderItem> orderItems) {
        OrderRequest orderRequest = new OrderRequest();

        orderRequest.setOrder(orderItems);
        orderRequest.setAddress(testAddress);

        return orderRequest;
    }

    @Test
    public void testNormalSalesProcess() {

        // get the initial catalog
        Catalog catalog = service.fetchCatalog();

        // Initialize an order with the first 2 items
        List <OrderItem> orderItems = buildTestOrder();

        OrderRequest orderRequest = buildTestOrderRequest (orderItems);

        // Make the order
        OrderReference initialOrderReference = service.makeOrder(orderRequest);
        int orderId = initialOrderReference.getId();

        assertNotNull("Order not completed", initialOrderReference);

        // Minimum test on the price, we assume there is no discount
        // but that there could be some delivery cost

        double orderPrice = initialOrderReference.getPrice();
        double minPrice = 0;

        for (OrderItem orderItem : orderItems) {
            minPrice += catalog.findId(orderItem.getProductId()).getPrice() * orderItem.getQty();
        }

        assertTrue("Price not coherent", orderPrice >= minPrice);

        assertEquals("Invalid order status after order request",
                     OrderStatus.REGISTERED, initialOrderReference.getOrderStatus());

        assertNotNull("Estimation date not returned", initialOrderReference.getEstimatedDelivery());

        // Try to fetch the order reference from the service
        OrderReference fetchedOrderReference = service.fetchOrderReference(initialOrderReference.getId());
        assertEquals("Initial order reference different from fetched order reference",
                initialOrderReference.getId(), fetchedOrderReference.getId());

        // Save producing order size before payment for testing change later
        int producingOrdersSize = service.fetchProducingOrders().size();

        PaymentResponse paymentResponse = service.payOrder(initialOrderReference.getId(),
                testPaymentInfo, PaymentPlan.DIRECT);

        assertTrue("Payment not successful after order", paymentResponse.isSuccess());
        String paymentReference = paymentResponse.getMessage();

        assertNotNull("Payment reference not returned", paymentReference);

        // Try to pay again should fail
        assertFalse("Successfully paid an order twice",
                service.payOrder(initialOrderReference.getId(), testPaymentInfo, PaymentPlan.DIRECT).isSuccess());

        // Order status should have changed, fetch it again to avoid assiming it's the same object
        assertEquals("Invalid order status after payment",
                     OrderStatus.PRODUCING, service.fetchOrderReference(orderId).getOrderStatus());

        // Check our order has been added to the producing list
        assertEquals("Paid order not added to the producing list",
                     producingOrdersSize + 1, service.fetchProducingOrders().size());

        // The producing chief mark the order as produced
        service.setOrderDelivering(orderId);

        // Check our order has been removed from the producing list
        assertEquals("Paid order not added to the producing list",
                producingOrdersSize, service.fetchProducingOrders().size());

        // Check the order status after order paid
        assertEquals("Invalid order status after order produced",
                OrderStatus.DELIVERING, service.fetchOrderReference(orderId).getOrderStatus());

        // Delivery manager marked the order as delivered
        service.setOrderDelivered(orderId);

        // Check the order status after order delivered
        assertEquals("Invalid order status after order produced",
                OrderStatus.DELIVERED, service.fetchOrderReference(orderId).getOrderStatus());
    }
}
