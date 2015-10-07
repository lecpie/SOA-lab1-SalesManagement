package fr.unice.polytech.soa1.salesmanagement;


import fr.unice.polytech.soa1.salesmanagement.data.OrderRequest;
import fr.unice.polytech.soa1.salesmanagement.data.PaymentInfo;
import fr.unice.polytech.soa1.salesmanagement.data.PaymentPlan;
import fr.unice.polytech.soa1.salesmanagement.data.PaymentResponse;
import junit.framework.TestCase;
import junit.framework.TestResult;
import org.junit.Test;

public class TestBillingServiceImpl extends TestCase {
    private BillingService service;

    public static final String cardnumber = "1234123412341234";
    public static final String cardExpire = "1234";
    public static final String csc        = "123";

    protected void setUp() {
        service = new BillingServiceImpl();
    }

    @Test
    public void testPayOrder() {
        OrderRequest orderRequest = TestOrderServiceImpl.createTestOrderRequest();
        orderRequest.setOrderId(0);

        PaymentInfo paymentInfo = createTestPaymentInfo();

        PaymentResponse paymentResponse = service.payOrder(orderRequest, paymentInfo, PaymentPlan.DIRECT);

        assertTrue("Order could not be paid", paymentResponse.isSuccess());
    }

    public static PaymentInfo createTestPaymentInfo() {
        PaymentInfo paymentInfo = new PaymentInfo();

        paymentInfo.setCardNumber(cardnumber);
        paymentInfo.setCardExpire(cardExpire);
        paymentInfo.setCsc(csc);

        return paymentInfo;
    }
}
