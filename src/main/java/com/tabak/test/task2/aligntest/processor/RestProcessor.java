package com.tabak.test.task2.aligntest.processor;

import com.tabak.test.task2.aligntest.exception.CustomNotFoundException;
import com.tabak.test.task2.aligntest.model.Product;
import com.tabak.test.task2.aligntest.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class RestProcessor {

    private ProductRepository repository;

    public RestProcessor(@Autowired ProductRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/rest/api/add", method = RequestMethod.POST)
    public Product addProduct(@RequestBody Product product) {
        log.info(" execute method ADD. " + product.toString());
        return repository.save(product);
    }

    @RequestMapping(value = "/rest/api/find", method = RequestMethod.GET)
    public List<Product> findProduct(@RequestParam(required = false) String name, @RequestParam(required = false) String brand) {

        log.info("execute method FIND. params: " + name + " " + brand);
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
                log.warn(" bad parameters of FIND method. ");
                // Здесь можно тоже сделать Exception. Хотя вернуть пустой список (ничего не найдено) тоже логично
                return new ArrayList<>();
            }
        }
    }

    @RequestMapping(value = "/rest/api/delete", method = RequestMethod.DELETE)
    public void deleteProduct(@RequestParam Long id) throws CustomNotFoundException {
        log.info(" DELETE method. ");
        if (id != null) {
            repository.deleteById(id);
        } else {
            log.warn(" ID for DELETE method not found ");
            throw new CustomNotFoundException();
        }
    }

    @RequestMapping(value = "/rest/api/update", method = RequestMethod.PUT)
    public Product updateProduct(@RequestBody Product product) throws CustomNotFoundException{
        log.info(" UPDATE method. ");
        if (product.getId() != null) {
            return repository.save(product);
        } else {
            log.warn(" ID for UPDATE method not found ");
            throw new CustomNotFoundException();
        }
    }

    @RequestMapping(value = "/rest/api/leftovers", method = RequestMethod.GET)
    public List<Product> getLeftovers() {
        log.info(" leftovers method  ");
        return repository.findAllByQuantityIsLessThan(5L);
    }

    /**
     * custom exception. Нет параметра нет действия
     */
    @ResponseStatus(value=HttpStatus.NOT_FOUND, reason = "Parameter not found in the system")
    @ExceptionHandler(CustomNotFoundException.class)
    public void exceptionHandler()
    {

    }

    /**
     * все исколючения по базе (передан некорректный параметр)
     * ловим здесь
     */
    @ResponseStatus(value=HttpStatus.BAD_REQUEST, reason = "Bad Parameter value")
    @ExceptionHandler(DataAccessException.class)
    public void exceptionHandler2()
    {

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
