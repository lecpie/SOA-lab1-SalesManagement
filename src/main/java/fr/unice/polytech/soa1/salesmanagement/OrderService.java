package fr.unice.polytech.soa1.salesmanagement;


import fr.unice.polytech.soa1.salesmanagement.data.*;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.List;

@WebService(name = "OrderService")
public interface OrderService {
    @WebResult(name = "order_reference")
    OrderReference makeOrder (@WebParam(name = "order_request") OrderRequest orderRequest,
                              @WebParam(name = "client")        Client       client);

    @WebResult(name = "order_reference")
    OrderReference fetchOrderReference (@WebParam(name = "order_id") int orderId);

    @WebResult(name = "success")
    boolean changeOrderStatus (@WebParam(name = "order_id") int orderId ,
                               @WebParam(name = "order_status") OrderStatus orderStatus);

    @WebResult(name = "order_request")
    OrderRequest fetchOrderRequest (@WebParam(name = "order_id") int orderId);

    @WebResult(name = "producing_order")
    List <OrderRequest> fetchProducingOrders();

    @WebResult(name = "delivering_order")
    List <OrderRequest> fetchDeliveringOrders();

    @WebResult(name = "success")
    boolean cancelOrder (@WebParam(name = "order_id") int orderId);
}
