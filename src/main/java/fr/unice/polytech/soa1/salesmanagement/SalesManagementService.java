package fr.unice.polytech.soa1.salesmanagement;


import fr.unice.polytech.soa1.salesmanagement.data.*;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name = "SalesManagement")
public interface SalesManagementService {

    @WebResult(name = "catalog")
    Catalog fetchCatalog();

    @WebResult(name = "order_reference")
    OrderReference makeOrder (@WebParam(name = "order_request") OrderRequest orderRequest);

    @WebResult(name = "order_reference")
    OrderReference fetchOrderReference (@WebParam(name = "order_id") int orderId);

    @WebResult(name = "payment_reference")
    String payOrder (@WebParam(name = "order_id") int orderId,
                  @WebParam(name = "payment_info") PaymentInfo paymentInfo);
    

}
