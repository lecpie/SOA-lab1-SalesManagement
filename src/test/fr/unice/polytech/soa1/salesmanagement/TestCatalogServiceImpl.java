package fr.unice.polytech.soa1.salesmanagement;

import fr.unice.polytech.soa1.salesmanagement.data.Catalog;
import fr.unice.polytech.soa1.salesmanagement.data.Client;
import fr.unice.polytech.soa1.salesmanagement.data.Product;
import fr.unice.polytech.soa1.salesmanagement.data.ProductType;
import junit.framework.TestCase;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

public class TestCatalogServiceImpl extends TestCase {
    private CatalogService service;
    private final int minCatalogSize = 4;

    // Info when creating a new product for test purpose
    public static final String testCollection = "Newest products";
    public static final String testDescription = "This is the newest chair !";
    public static final String testImgLink = "newWebSite.new/newChair.jpg";
    public static final double testPrice = 150.0;
    public static final ProductType testProductType = ProductType.CHAIR;
    public static final String testName = "New chair";

    protected void setUp() {
        service = new CatalogServiceImpl();
        CatalogServiceImpl.resetData();
    }

    @Test
    public void testInitCatalog() {
        Catalog catalog = service.fetchCatalog();

        // Assert catalog test products are here
        assertTrue("Init catalog must have at least 4 items on setup", catalog.getProducts().size() >= minCatalogSize);

        // Check each product content
        List<Integer> idList = new ArrayList<Integer>();
        for (int i = 0; i < minCatalogSize; ++i) {
            Product p = catalog.getProducts().get(i);

            assertNotNull("A product has a null id", p.getId());
            assertNotNull("A product has a null collection", p.getCollection());
            assertNotNull("A product has a null description", p.getDescription());
            assertNotNull("A product has a null imagelink", p.getImageLink());
            assertNotNull("A product has a null name", p.getName());
            assertTrue("A product has no price", p.getPrice() != 0.0);
            assertNotNull("A product has a null product type", p.getProductType());

            assertFalse("A product id is not unique", idList.contains(p.getId()));
        }
    }

    @Test
    public void testAddRemoveCatalog() {
        Catalog catalog = service.fetchCatalog();
        int initSize = catalog.getProducts().size();

        Product p = createTestProduct();

        int productId = service.addItemCatalog(p);

        assertTrue("Product creation reported failed", productId != -1);

        catalog = service.fetchCatalog();

        int afterInsertSize = catalog.getProducts().size();

        // Test we have one more product
        assertEquals("Unexpected catalog size after product insert", initSize + 1, afterInsertSize);

        // Check we find the right product by the returned id
        assertEquals("found product id not same as requested id", productId, service.findProductById(productId).getId());

        int idCount = 0;

        // Check this id is unique in the catalog
        for (Product catalogProduct : catalog.getProducts()) {
            if (catalogProduct.getId() == productId)
                ++idCount;
        }

        assertEquals("Added product id not unique", 1, idCount);

        // Try to remove this product

        assertTrue("Product could not be removed", service.removeItemCatalog(productId));

        catalog = service.fetchCatalog();

        assertEquals("Catalog size is not back to the state before the product was added", initSize, catalog.getProducts().size());

        // Check this id is not in the catalog anymore
        idCount = 0;
        for (Product catalogProduct : catalog.getProducts()) {
            if (catalogProduct.getId() == productId)
                ++idCount;
        }

        assertEquals("Product id is still in the catalog", 0, idCount);

        assertNull("The service can find the removed product", service.findProductById(productId));

        assertFalse("Product can be removed again", service.removeItemCatalog(productId));
    }

    public static Product createTestProduct() {
        Product p = new Product();

        p.setName(testName);
        p.setDescription(testDescription);
        p.setImageLink(testImgLink);
        p.setPrice(testPrice);
        p.setProductType(testProductType);
        p.setCollection(testCollection);

        return p;
    }
}
