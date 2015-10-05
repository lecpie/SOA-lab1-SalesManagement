package fr.unice.polytech.soa1.salesmanagement;


import fr.unice.polytech.soa1.salesmanagement.data.*;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name = "BillingService")
public interface BillingService {

    PaymentResponse payOrder (@WebParam(name = "order_request") OrderRequest orderRequest,
                              @WebParam(name = "payment_info")  PaymentInfo paymentInfo,
                              @WebParam(name = "payment_plan")  PaymentPlan paymentPlan);

}
