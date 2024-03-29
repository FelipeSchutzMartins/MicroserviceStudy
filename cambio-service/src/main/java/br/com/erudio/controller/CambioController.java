package br.com.erudio.controller;

import br.com.erudio.model.Cambio;
import br.com.erudio.repository.CambioRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("cambio-service")
public class CambioController {

    @Autowired
    private Environment environment;

    @Autowired
    private CambioRepository repository;

    @GetMapping(value = "/{amount}/{from}/{to}")
    public Cambio getCambio(
            @Context HttpServletRequest request,
            @PathVariable("amount") BigDecimal amount,
            @PathVariable("from") String from,
            @PathVariable("to") String to
    ) {
        Cambio cambio = repository.findByFromAndTo(from, to);
        if (cambio == null) throw new RuntimeException("Not found!");

        var port = environment.getProperty("local.server.port");
        BigDecimal convertedValue = cambio.getConversionFactor().multiply(amount);
        cambio.setEnvironment(port);
        cambio.setConvertedValue(convertedValue);
        return cambio;
    }

}
