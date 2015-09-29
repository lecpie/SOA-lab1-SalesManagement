package fr.unice.polytech.soa1.salesmanagement;


import fr.unice.polytech.soa1.salesmanagement.data.Catalog;
import fr.unice.polytech.soa1.salesmanagement.data.OrderReference;
import fr.unice.polytech.soa1.salesmanagement.data.OrderRequest;
import fr.unice.polytech.soa1.salesmanagement.data.Product;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name = "SalesManagement")
public interface SalesManagementService {

    @WebResult(name = "catalog")
    Catalog fetchCatalog();

    @WebResult(name = "order_reference")
    OrderReference makeOrder(@WebParam(name = "order_request") OrderRequest orderRequest);

}
