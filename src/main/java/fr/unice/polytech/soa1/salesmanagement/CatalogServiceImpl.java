package fr.unice.polytech.soa1.salesmanagement;

import fr.unice.polytech.soa1.salesmanagement.data.Catalog;
import fr.unice.polytech.soa1.salesmanagement.data.Product;
import fr.unice.polytech.soa1.salesmanagement.data.ProductType;

import javax.jws.WebService;

@WebService(targetNamespace   = "http://informatique.polytech.unice.fr/soa1/salesmanagement/",
        portName          = "ExternalCatalogServicePort",
        serviceName       = "ExternalCatalogService",
        endpointInterface = "fr.unice.polytech.soa1.salesmanagement.CatalogService")
public class CatalogServiceImpl implements CatalogService {

    private static Catalog catalog;

    public CatalogServiceImpl() {
        if (catalog == null) {
            catalog = new Catalog();
            createSampleData();
        }
    }

    public Catalog fetchCatalog() {
        return catalog;
    }

    public Product findProductById(int productId) {
        return catalog.findId(productId);
    }

    public static void resetData() {
        catalog = new Catalog();
        createSampleData();
    }

    private static void createSampleData() {
        // Create some products

        Product p1 = new Product();
        p1.setName("Nice chair");
        p1.setPrice(40.0);
        p1.setProductType(ProductType.CHAIR);
        p1.setImageLink("nicedomain.nice/product1.jpg");
        p1.setCollection("Nice collection");
        p1.setDescription("This is a nice chair ... and cheap");
        catalog.addProduct(p1);

        Product p2 = new Product();
        p2.setName("Nice table");
        p2.setPrice(70);
        p2.setProductType(ProductType.TABLE);
        p2.setImageLink("nicedomain.nice/product2.jpg");
        p2.setCollection("Nice collection");
        p2.setDescription("This is a nice table ... and cheap");
        catalog.addProduct(p2);

        Product p3 = new Product();
        p3.setName("Very nice chair");
        p3.setPrice(80);
        p3.setProductType(ProductType.CHAIR);
        p3.setImageLink("nicedomain.nice/product3.jpg");
        p3.setCollection("Very nice collection");
        p3.setDescription("This is a very nice chair !");
        catalog.addProduct(p3);

        Product p4 = new Product();
        p4.setName("Very nice table");
        p4.setPrice(140);
        p4.setProductType(ProductType.TABLE);
        p4.setImageLink("nicedomain.nice/product4.jpg");
        p4.setCollection("Very nice collection");
        p4.setDescription("This is a very nice table !");
        catalog.addProduct(p4);
    }

    public int addItemCatalog(Product newProduct) {
        catalog.addProduct(newProduct);

        return newProduct.getId();
    }

    public boolean removeItemCatalog(int productId) {

        if (catalog.findId(productId) != null) {
            catalog.removeProduct(productId);

            return true;
        }

        return false;
    }
}
