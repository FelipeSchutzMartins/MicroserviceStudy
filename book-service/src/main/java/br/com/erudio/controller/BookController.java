package br.com.erudio.controller;

import br.com.erudio.cambio.Cambio;
import br.com.erudio.model.Book;
import br.com.erudio.proxy.CambioProxy;
import br.com.erudio.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private Environment environment;

    @Autowired
    private BookRepository repository;

    @Autowired
    private CambioProxy proxy;

    @GetMapping(value = "/{id}/{currency}")
    public Book findBook(
            @PathVariable("id") Long id,
            @PathVariable("currency") String currency
    ) {
        var book = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found!"));
        var cambio = proxy.getCambio(book.getPrice().doubleValue(), "USD", currency);
        book.setEnvironment(environment.getProperty("local.server.port"));
        book.setPrice(cambio.getConvertedValue());
        book.setCurrency(currency);
        return book;
    }

}
