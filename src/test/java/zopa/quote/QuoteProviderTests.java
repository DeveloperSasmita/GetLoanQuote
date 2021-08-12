package zopa.quote;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import zopa.quote.exceptions.FundsNotAvailable;
import zopa.quote.exceptions.InvalidRequestAmount;
import zopa.quote.model.Quote;
import zopa.quote.service.QuoteProviderService;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class QuoteProviderTests {

    @Autowired
    private QuoteProviderService loanQuoteCalculation;

    @Test
    public void testCachingWithDebug() {

        Quote quote = new Quote();
        Assertions.assertNotSame(loanQuoteCalculation.getQuote("1700"), quote);

        Optional<Quote> quote2 = loanQuoteCalculation.getQuote(new String("1700"));
        Assertions.assertEquals(String.format("%.1f", quote2.get().getRate() * 100), "7,2");

        String amount = "1700";
        Optional<Quote> quote3 = loanQuoteCalculation.getQuote(amount);
        Assertions.assertEquals(String.format("%.2f", quote3.get().getMonthlyRepayment()), "52,64");


    }

    @Test
    public void testCalculateRateWith1Decimal() {

        Quote quote = new Quote();
        quote.setAmount(1000);
        Assertions.assertEquals(String.format("%.1f", loanQuoteCalculation.calculateLowestPossibleRate(quote.getAmount()) * 100), "7,0");

    }

    @Test
    public void testCalculateMonthlyRepaymentWith2Decimal() {

        Quote quote = new Quote();
        quote.setAmount(1000);
        quote.setRate(loanQuoteCalculation.calculateLowestPossibleRate(quote.getAmount()));
        Assertions.assertEquals(( loanQuoteCalculation.calculateMonthlyRepayment(quote)), new BigDecimal("1.11"));

    }

    @Test
    public void testWithSampleData() throws InvalidRequestAmount, FundsNotAvailable {

        String loan = "1700";
        Optional<Quote> quote = loanQuoteCalculation.getQuote(loan);
        Assertions.assertEquals(String.format("%.1f", quote.get().getRate() * 100), "7,2");
        Assertions.assertEquals(String.format("%.2f", quote.get().getMonthlyRepayment()), "52,64");
        Assertions.assertEquals(String.format("%.2f", quote.get().getTotalRepayment()), "1894,88");
    }


}
