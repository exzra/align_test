package com.tabak.test.task2.aligntest.processor;

import com.tabak.test.task2.aligntest.model.Product;
import com.tabak.test.task2.aligntest.processor.RestProcessor;
import com.tabak.test.task2.aligntest.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcessorTest {

    @InjectMocks
    RestProcessor restProcessor;

    @Mock
    ProductRepository repositoryTestMock;

    @Test
    public void testProc() {

        Product product = new Product();
        restProcessor.addProduct(product);
        restProcessor.deleteProduct(1L);
        List<Product> product1 = restProcessor.findProduct("name", "brand");

        List<Product> leftovers = restProcessor.getLeftovers();
        restProcessor.updateProduct(product);

    }
}
