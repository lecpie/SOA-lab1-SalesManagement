package fr.unice.polytech.soa1.salesmanagement;


import fr.unice.polytech.soa1.salesmanagement.data.*;

import javax.jws.WebService;

@WebService(targetNamespace   = "http://informatique.polytech.unice.fr/soa1/salesmanagement/",
        portName          = "ExternalBillingServicetPort",
        serviceName       = "ExternalBillingService",
        endpointInterface = "fr.unice.polytech.soa1.salesmanagement.BillingService")
public class BillingServiceImpl implements BillingService {

    public PaymentResponse payOrder(OrderRequest orderRequest, PaymentInfo paymentInfo, PaymentPlan paymentPlan) {
        double value = calculatePrice(orderRequest);

        double firstPayment = calculateFirstPayment(value, paymentPlan);

        PaymentResponse paymentResponse = doPayment(firstPayment, paymentInfo);

        return paymentResponse;
    }

    // Mock for payment operation

    private double calculatePrice(OrderRequest orderRequest) {
        double price = 0;
        for (OrderItem orderItem : orderRequest.getOrder()) {
            Product p = orderItem.getProduct();

            price += p.getPrice() * orderItem.getQty();
        }

        return price;
    }

    private double calculateFirstPayment (double totalValue, PaymentPlan paymentPlan) {
        switch (paymentPlan) {
            case SHORT :
                return totalValue / 3;

            case LONG :
                return totalValue / 6;

            case DIRECT:
            default:
                return totalValue;
        }
    }

    private PaymentResponse doPayment(double value, PaymentInfo paymentInfo) {

        // Generate random bank transfer reference
        String paymentReference = Integer.toString((int) (Math.random() * 1000000000));

        PaymentResponse response = new PaymentResponse();
        response.setSuccess(true);
        response.setMessage(paymentReference);

        return response;
    }

}
