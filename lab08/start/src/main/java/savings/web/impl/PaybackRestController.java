package savings.web.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import savings.model.PaybackConfirmation;
import savings.model.Purchase;
import savings.service.PaybackBookKeeper;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

// TODO #1 mark this class as a controller component mapped to '/api/payback' url
@Controller
@RequestMapping("/api/payback")
public class PaybackRestController {

    private final PaybackBookKeeper paybackBookKeeper;

    @Autowired
    public PaybackRestController(PaybackBookKeeper paybackBookKeeper) {
        this.paybackBookKeeper = paybackBookKeeper;
    }

    // TODO #3 make this method respond only to POST request and consume and produce `application/json`
    @RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE ,produces = APPLICATION_JSON_VALUE)
    // TODO #4 make this method return HTTP 201 Created response status
    @ResponseStatus(HttpStatus.CREATED)
    // TODO #5 make this method serialize the returned value into response body
    @ResponseBody
    // TODO #6 make this method deserialize request body into 'purchase' parameter
    public PaybackConfirmation create(@RequestBody Purchase purchase) {
        return paybackBookKeeper.registerPaybackFor(purchase);
    }
}
