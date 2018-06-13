package com.tabak.test.task2.aligntest.repository;

import com.tabak.test.task2.aligntest.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoryTest {

    @Autowired
    ProductRepository repository;

    @Before
    public void setUp() {
        Product product = new Product();
        product.setId(1L);
        product.setBrand("brand1");
        product.setName("prod1");
        product.setPrice(333L);
        product.setQuantity(12L);
        repository.save(product);

        product = new Product();
        product.setId(2L);
        product.setBrand("brand1");
        product.setName("prod2");
        product.setPrice(301L);
        product.setQuantity(2L);
        repository.save(product);

        product = new Product();
        product.setId(3L);
        product.setBrand("brand1");
        product.setName("prod3");
        product.setPrice(433L);
        product.setQuantity(4L);
        repository.save(product);

        product = new Product();
        product.setId(4L);
        product.setBrand("brand1");
        product.setName("prod4");
        product.setPrice(100L);
        product.setQuantity(100L);
        repository.save(product);

        product = new Product();
        product.setId(5L);
        product.setBrand("brand1");
        product.setName("prod5");
        product.setPrice(111L);
        product.setQuantity(111L);
        repository.save(product);

        product = new Product();
        product.setId(6L);
        product.setBrand("brand");
        product.setName("prod1");
        product.setPrice(399L);
        product.setQuantity(3L);
        repository.save(product);

        product = new Product();
        product.setId(7L);
        product.setBrand("brand2");
        product.setName("prod2");
        product.setPrice(301L);
        product.setQuantity(3L);
        repository.save(product);

        product = new Product();
        product.setId(8L);
        product.setBrand("brand2");
        product.setName("prod3");
        product.setPrice(433L);
        product.setQuantity(14L);
        repository.save(product);

        product = new Product();
        product.setId(9L);
        product.setBrand("brand2");
        product.setName("prod4");
        product.setPrice(100L);
        product.setQuantity(100L);
        repository.save(product);

        product = new Product();
        product.setId(10L);
        product.setBrand("brand2");
        product.setName("prod5");
        product.setPrice(111L);
        product.setQuantity(1L);
        repository.save(product);
    }

    //@Sql(scripts = "insert.sql")
    @Test
    public void testFindByName() {

        Product product = new Product();
        product.setId(11L);
        product.setBrand("brand3");
        product.setName("name1");
        product.setPrice(11L);
        product.setQuantity(10000L);
        repository.save(product);

        List<Product> name1 = repository.findByName("name1");
        assertEquals(name1.size(), 1);
        assertEquals(name1.get(0).getId(), product.getId());
        assertEquals(name1.get(0).getBrand(), product.getBrand());

        repository.deleteById(11L);
        Optional<Product> byId1 = repository.findById(11L);
        assertFalse(byId1.isPresent());

    }

    @Test
    public void testFindByBrand() {
        List<Product> brand2 = repository.findByBrand("brand2");
        assertEquals(brand2.size(), 4);
    }

    @Test
    public void testFindByNameAndBrand() {
        List<Product> byNameAndBrand = repository.findByNameAndBrand("prod1", "brand1");
        assertEquals(byNameAndBrand.size(), 1);
    }

    @Test
    public void testUpdate() {
        Product product = new Product();
        product.setId(12L);
        product.setBrand("brand3");
        product.setName("name1");
        product.setPrice(11L);
        product.setQuantity(10000L);
        repository.save(product);

        product.setQuantity(111L);
        repository.save(product);
        Optional<Product> byId = repository.findById(12L);
        assertEquals(byId.get().getQuantity(), product.getQuantity());

        repository.deleteById(12L);
        Optional<Product> byId1 = repository.findById(12L);
        assertFalse(byId1.isPresent());
    }

    /**
     * смотрим, что в вернувшихся значениях точно те значения (quantity < 5).  А также проверяем,
     * сколько объектов не попало в ресалт сет ( находим quantity >= 5, сверяем количественно)
     *
     */
    @Test
    public void testQuantity() {
        List<Product> allByQuantityIsLessThan = repository.findAllByQuantityIsLessThan(5L);
        Iterable<Product> products = repository.findAll();

        int countGreater = 0;
        int count = 0;

        for (Iterator<Product> iterator = products.iterator() ; iterator.hasNext(); ) {
            Product el = iterator.next();
            count++;
            if (el.getQuantity() >= 5) {
                countGreater++;
            }
        }

        assertEquals(count, countGreater + allByQuantityIsLessThan.size());

        for (Product anAllByQuantityIsLessThan : allByQuantityIsLessThan) {
            assertFalse(anAllByQuantityIsLessThan.getQuantity() >= 5);
        }

    }
}
