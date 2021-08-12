package zopa.quote.serviceImpl;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import zopa.quote.exceptions.FundsNotAvailable;
import zopa.quote.model.Offer;
import zopa.quote.model.Quote;
import zopa.quote.service.FundsService;
import zopa.quote.service.QuoteProviderService;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Component
public @Data
class QuoteProviderServiceImpl implements QuoteProviderService {

    @Autowired
    private FundsService fundsService;


    @Value("${loan.payment.months}")
    private int totalPaymentsMonths;


    @Cacheable("quote")
    public Optional <Quote> getQuote(String loan_amount) throws FundsNotAvailable {

        log.info("Processing to  getQuote with requested amount....." + loan_amount);

        int requestedAmt = Integer.parseInt(loan_amount);

        //To check if requested amount of funds available in market
        fundsService.checkAvailableFunds(requestedAmt);

        return (generateQuote(requestedAmt));
    }

    public Optional<Quote> generateQuote(final int requestedAmt) {

        Quote quote = new Quote();
        quote.setAmount(requestedAmt);
        quote.setRate(calculateLowestPossibleRate(requestedAmt));
        quote.setMonthlyRepayment(calculateMonthlyRepayment(quote).setScale(2, BigDecimal.ROUND_UP));
        quote.setTotalRepayment(quote.getMonthlyRepayment().multiply(new BigDecimal(getTotalPaymentsMonths())).setScale(2, BigDecimal.ROUND_UP) );

        if (quote == null) {
            log.error("An error occurred while generating the quote");
            return Optional.empty();
        }
        return Optional.of(quote);
    }

    /**
     * @param , requestedAmount , MarketLenders Data
     * @return rate
     */
    public double calculateLowestPossibleRate(final int requestedAmt) {

        int currentAmt = 0;
        double rate = 0;

        for (Offer offer : getFundsService().getOffersList()) {
            if (currentAmt + offer.getAmount() > requestedAmt) {
                double delta = requestedAmt - currentAmt;
                rate += (delta / requestedAmt) * offer.getRate();
                break;
            } else {
                currentAmt += offer.getAmount();
                rate += ((double) offer.getAmount() / requestedAmt) * offer.getRate();
            }
        }
        return rate;


    }

    /**
     * @param , rate, amount ,totalPaymentsMonths
     *          Formula from the website https://en.wikipedia.org/wiki/Amortization_calculator#The_formu
     * @return Monthly Repayment
     */

    public BigDecimal calculateMonthlyRepayment(Quote quote) {

        return new BigDecimal(quote.getRate() * quote.getAmount() / 12 / (1 - Math.pow((quote.getRate() / 12 + 1), (-getTotalPaymentsMonths()))));
      //  return (new BigDecimal(quote.getRate()).multiply(new BigDecimal(quote.getAmount())) .divide(new BigDecimal(12)).divide((BigDecimal.ONE.subtract (new BigDecimal(quote.getRate()).divide(new BigDecimal(12)).add(BigDecimal.ONE) ).pow (getTotalPaymentsMonths()))));

    }


}