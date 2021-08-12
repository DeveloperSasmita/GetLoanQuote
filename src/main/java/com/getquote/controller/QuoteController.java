
package com.getquote.controller;

import com.getquote.exceptions.GenericException;
import com.getquote.model.Quote;
import com.getquote.service.QuoteProviderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import com.getquote.service.AmountValidationService;

import java.util.Optional;


@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class QuoteController {

    @Autowired
    private QuoteProviderService quoteCalculation;

    @Autowired
    AmountValidationService amountValidationService;


    @GetMapping(path = "/getquote")
    public ResponseEntity<String> quoteCalculation( @RequestParam @NonNull String loan_amount) {
        log.info("Loan request received .. Amount : " + loan_amount);

            //Check for Amount validation
            amountValidationService.validateRequestedLoanAmount(loan_amount);

                Optional<Quote> quote = quoteCalculation.getQuote((loan_amount));

                if (quote.isPresent()) {
                    log.info("Sending response with Quote"+quote.get().beautyPrint());
                    return new ResponseEntity<String> (quote.get().beautyPrint(), HttpStatus.OK);
                }
                throw new GenericException();
    }
}



