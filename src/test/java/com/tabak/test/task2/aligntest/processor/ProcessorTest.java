package com.tabak.test.task2.aligntest.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tabak.test.task2.aligntest.model.Product;
import com.tabak.test.task2.aligntest.processor.RestProcessor;
import com.tabak.test.task2.aligntest.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class)
public class ProcessorTest {

    @InjectMocks
    RestProcessor restProcessor;

    @Mock
    ProductRepository repositoryTestMock;

//    @Autowired
//    MockMvc mockMvc;

    //TODO: доделать execute


    @Test
    public void testAdd() {
        Product mockProduct = new Product();
        mockProduct.setId(12L);
        mockProduct.setBrand("brand3");
        mockProduct.setName("name1");
        mockProduct.setPrice(11L);
        mockProduct.setQuantity(10000L);



        Mockito.when(restProcessor.addProduct(mockProduct)).thenReturn(mockProduct);


        String jsonString = "{\n" +
                "  \"id\": 12,\n" +
                "  \"name\": \"name1\",\n" +
                "  \"brand\": \"brand3\",\n" +
                "  \"price\": 11,\n" +
                "  \"quantity\": 10000\n" +
                "}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/rest/api/add")
                .accept(MediaType.APPLICATION_JSON).content(jsonString)
                .contentType(MediaType.APPLICATION_JSON);

       // MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    }

    @Test
    public void testFind() {

        String name = "name";
        String brand = "brand";
        Mockito.when(restProcessor.findProduct(name, brand)).thenReturn(new ArrayList<>());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/rest/api/find").param("name", "name")
                .param("brand", "brand")
                .accept(MediaType.APPLICATION_JSON);

    }

    @Test
    public void leftovers() {
        Mockito.when(restProcessor.getLeftovers()).thenReturn(new ArrayList<>());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/rest/api/leftovers")
                .accept(MediaType.APPLICATION_JSON);
    }

    @Test
    public void testUpdate() {
        Product mockProduct = new Product();
        mockProduct.setId(12L);
        mockProduct.setBrand("brand3");
        mockProduct.setName("name1");
        mockProduct.setPrice(11L);
        mockProduct.setQuantity(10000L);

        Mockito.when(restProcessor.updateProduct(mockProduct)).thenReturn(mockProduct);

        String jsonString = "{\n" +
                "  \"id\": 12,\n" +
                "  \"name\": \"name1\",\n" +
                "  \"brand\": \"brand3\",\n" +
                "  \"price\": 11,\n" +
                "  \"quantity\": 10000\n" +
                "}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/rest/api/update")
                .accept(MediaType.APPLICATION_JSON).content(jsonString)
                .contentType(MediaType.APPLICATION_JSON);
    }
}
