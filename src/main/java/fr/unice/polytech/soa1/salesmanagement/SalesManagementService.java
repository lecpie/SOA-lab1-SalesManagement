package fr.unice.polytech.soa1.salesmanagement;


import fr.unice.polytech.soa1.salesmanagement.data.Catalog;
import fr.unice.polytech.soa1.salesmanagement.data.Product;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name = "SalesManagement")
public interface SalesManagementService {

    @WebResult(name = "catalog")
    Catalog fetchCatalog();

}
