package zopa.quote.service;

import zopa.quote.exceptions.FundsNotAvailable;
import zopa.quote.model.Quote;

import java.math.BigDecimal;
import java.util.Optional;

public interface QuoteProviderService {

    public Optional<Quote> getQuote(String loan) throws FundsNotAvailable;

    public double calculateLowestPossibleRate(int loan_amount);

    public BigDecimal calculateMonthlyRepayment(Quote loanQuote);

}
