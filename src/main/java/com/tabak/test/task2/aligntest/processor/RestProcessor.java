package com.tabak.test.task2.aligntest.processor;

import com.tabak.test.task2.aligntest.model.Product;
import com.tabak.test.task2.aligntest.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RestProcessor {

    private ProductRepository repository;

    public RestProcessor(@Autowired ProductRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/rest/api/add", method = RequestMethod.POST)
    public void addProduct(@RequestBody Product product) {
        repository.save(product);
    }

    @RequestMapping(value = "rest/api/find", method = RequestMethod.GET)
    public List<Product> findProduct(@RequestParam(required = false) String name, @RequestParam(required = false) String brand) {

        if (name != null) {
            if (brand != null) {
                return repository.findByNameAndBrand(name, brand);
            } else {
                return repository.findByName(name);
            }
        } else {
            if (brand != null) {
                return repository.findByBrand(brand);
            } else {
                return new ArrayList<>();
            }
        }
    }

    @RequestMapping(value = "rest/api/delete", method = RequestMethod.DELETE)
    public void deleteProduct(@RequestParam Long id) {
        repository.deleteById(id);
    }

    @RequestMapping(value = "rest/api/update", method = RequestMethod.PUT)
    public void updateProduct(@RequestBody Product product) {
        if (product.getId() != null) {
            repository.save(product);
        } else {
            return;
        }
    }

    @RequestMapping(value = "rest/api/leftovers", method = RequestMethod.GET)
    public List<Product> getLeftovers() {
        return repository.findAllByQuantityIsLessThan(5L);
    }

    @ExceptionHandler(Exception.class)
    public HttpServletResponse handleException(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return response;
    }
}
