import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class ProductsLoader {

    public List<ProductDTO> getProducts() {
        List<ProductDTO> products = new ArrayList<>();

        System.out.println("Reading products.yaml...");
        Yaml yaml = new Yaml(new Constructor(ProductDTO.class));
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

    private void loadProducts(final List<ProductDTO> products, final Yaml yaml, final InputStream inputStream) {
        yaml.loadAll(inputStream).forEach(product -> products
                .add((ProductDTO) product));
    }

}
