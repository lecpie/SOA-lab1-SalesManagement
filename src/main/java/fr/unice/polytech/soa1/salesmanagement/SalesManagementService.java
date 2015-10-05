package fr.unice.polytech.soa1.salesmanagement;


import fr.unice.polytech.soa1.salesmanagement.data.*;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;

@WebService(name = "SalesManagement")
public interface SalesManagementService {

    @WebResult(name = "catalog")
    Catalog fetchCatalog();

    @WebResult(name = "product_id")
    int addItemCatalog(@WebParam(name = "new_product") Product newProduct);

    @WebResult(name = "order_reference")
    OrderReference makeOrder (@WebParam(name = "order_request") OrderRequest orderRequest);

    @WebResult(name = "order_reference")
    OrderReference fetchOrderReference (@WebParam(name = "order_id") int orderId);

    @WebResult(name = "payment_reference")
    PaymentResponse payOrder (@WebParam(name = "order_id") int orderId,
                              @WebParam(name = "payment_info") PaymentInfo paymentInfo,
                              @WebParam(name = "payment_plan") PaymentPlan paymentPlan);

    @WebResult(name = "producing_order")
    List<OrderRequest> fetchProducingOrders();

    @WebResult(name = "status")
    boolean setOrderDelivering (@WebParam(name = "order_id") int orderId);

    @WebResult(name = "status")
    boolean setOrderDelivered (@WebParam(name = "order_id") int orderId);

    @WebResult(name = "status")
    boolean cancelOrder (@WebParam(name = "order_id") int orderId);
}
