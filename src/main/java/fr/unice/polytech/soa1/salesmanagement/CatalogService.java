package fr.unice.polytech.soa1.salesmanagement;


import fr.unice.polytech.soa1.salesmanagement.data.Catalog;
import fr.unice.polytech.soa1.salesmanagement.data.Product;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name = "CatalogService")
public interface CatalogService {

    @WebResult(name = "catalog")
    Catalog fetchCatalog();

    @WebResult(name = "product_id")
    int addItemCatalog(@WebParam(name = "new_product") Product newProduct);
}
