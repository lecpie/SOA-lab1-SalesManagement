package fr.unice.polytech.soa1.salesmanagement;

import fr.unice.polytech.soa1.salesmanagement.data.*;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SalesManagementTest extends TestCase {

    private SalesManagementService service = null;

    public void setUp() {
        service = new SalesManagementImpl();
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

    @Test
    public void testNormalSalesProcess() {

        // get the initial catalog
        Catalog catalog = service.fetchCatalog();

        // Initialize an order with the first 2 items
        int itemId1 = catalog.getProducts().get(0).getId();
        int itemId2 = catalog.getProducts().get(1).getId();
        int qty1 = 10;
        int qty2 = 20;

        List <OrderItem> orderItems = new ArrayList<OrderItem>();
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setProductId(itemId1);
        orderItem1.setQty(qty1);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setProductId(itemId2);
        orderItem2.setQty(qty2);

        orderItems.add (orderItem1);
        orderItems.add (orderItem2);

        String address = "123 Lambda street - 12345 Lambdatown";

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrder(orderItems);
        orderRequest.setAddress(address);

        // Make the order
        OrderReference initialOrderReference = service.makeOrder(orderRequest);
        int orderId = initialOrderReference.getId();

        assertNotNull("Order not completed", initialOrderReference);

        // Minimum test on the price, we assume there is no discount
        // but that there could be some delivery cost

        double orderPrice = initialOrderReference.getPrice();
        double minPrice = qty1 * catalog.findId(itemId1).getPrice()
                        + qty2 * catalog.findId(itemId2).getPrice();

        assertTrue("Price not coherent", orderPrice >= minPrice);

        assertEquals("Invalid order status after order request",
                     OrderStatus.REGISTERED, initialOrderReference.getOrderStatus());

        assertNotNull("Estimation date not returned", initialOrderReference.getEstimatedDelivery());

        // Try to fetch the order reference from the service
        OrderReference fetchedOrderReference = service.fetchOrderReference(initialOrderReference.getId());
        assertEquals("Initial order reference different from fetched order reference",
                initialOrderReference.getId(), fetchedOrderReference.getId());

        String cardNumber = "1234123412341234";
        String cardExpire = "1217";
        String csc        = "123";

        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setCardNumber(cardNumber);
        paymentInfo.setCardExpire(cardExpire);
        paymentInfo.setCsc(csc);

        // Save producing order size before payment for testing change later
        int producingOrdersSize = service.fetchProducingOrders().size();

        PaymentResponse paymentResponse = service.payOrder(initialOrderReference.getId(), paymentInfo);

        assertTrue("Payment not successful after order", paymentResponse.isSuccess());
        String paymentReference = paymentResponse.getMessage();

        assertNotNull("Payment reference not returned", paymentReference);

        // Try to pay again should fail
        assertFalse("Successfully paid an order twice", service.payOrder(initialOrderReference.getId(), paymentInfo).isSuccess());

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
