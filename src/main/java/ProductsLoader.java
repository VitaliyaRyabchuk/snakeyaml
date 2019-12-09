import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class ProductsLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsLoader.class);

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();

        LOGGER.info("Reading products.yaml...");
        Yaml yaml = new Yaml(new Constructor(Product.class));
        try (InputStream inputStream = this.getClass().getClassLoader()
                .getResourceAsStream("products.yaml")) {
            if (isNull(inputStream)) {
                throw new IllegalStateException("products.yaml not found");
            }
            loadProducts(products, yaml, inputStream);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load products.yaml file", e);
        }
        return products;
    }

    private void loadProducts(final List<Product> products, final Yaml yaml, final InputStream inputStream) {
        yaml.loadAll(inputStream).forEach(product -> products
                .add((Product) product));
    }

}
