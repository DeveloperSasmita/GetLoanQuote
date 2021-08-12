
package zopa.quote;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zopa.quote.exceptions.FundsNotAvailable;
import zopa.quote.exceptions.GenericException;
import zopa.quote.exceptions.InvalidRequestAmount;
import zopa.quote.model.Quote;
import zopa.quote.service.AmountValidationService;
import zopa.quote.service.QuoteProviderService;

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


    @PostMapping(path = "/zopa-rate")
    public ResponseEntity<String> quoteCalculation( @RequestBody String loan_amount) {
        log.info("Loan request received .. Amount : " + loan_amount);

            //Check for Amount validation
            amountValidationService.validateRequestedLoanAmount(loan_amount);

                Optional<Quote> quote = quoteCalculation.getQuote((loan_amount));

                if (quote.isPresent()) {
                    log.info("Sending response with Quote"+quote.get().beautyPrint());
                    return new ResponseEntity<String> (quote.get().beautyPrint(), HttpStatus.OK);
                }
                throw new  GenericException();
    }
}



