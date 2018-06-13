package com.tabak.test.task2.aligntest.processor;

import com.tabak.test.task2.aligntest.model.Product;
import com.tabak.test.task2.aligntest.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public Product addProduct(@RequestBody Product product) {
        return repository.save(product);
    }

    @RequestMapping(value = "/rest/api/find", method = RequestMethod.GET)
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

    @RequestMapping(value = "/rest/api/delete", method = RequestMethod.DELETE)
    public void deleteProduct(@RequestParam Long id) {
        repository.deleteById(id);
    }

    @RequestMapping(value = "/rest/api/update", method = RequestMethod.PUT)
    public Product updateProduct(@RequestBody Product product) {
        if (product.getId() != null) {
            return repository.save(product);
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/rest/api/leftovers", method = RequestMethod.GET)
    public List<Product> getLeftovers() {
        return repository.findAllByQuantityIsLessThan(5L);
    }

    @ExceptionHandler(Exception.class)
    public HttpServletResponse handleException(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return response;
    }


    @RequestMapping(value="/rest/api/logout", method = RequestMethod.POST)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }
}
