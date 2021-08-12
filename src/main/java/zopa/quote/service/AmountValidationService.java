package zopa.quote.service;

import zopa.quote.exceptions.InvalidRequestAmount;

public interface AmountValidationService {

    /**
     * Validate amount for a loan maximum, minimum and multiplier
     *
     * @param requestedAmount
     */
    public void validateLimits(int requestedAmount) throws InvalidRequestAmount ;

    public void validateMultiplier(int requestedAmount)  throws InvalidRequestAmount;

    /**
     * Validate amount requested
     *
     * @param requestedAmount
     * @return
     */
    public void isLoanAmountValid(String requestedAmount) throws InvalidRequestAmount;


    public void validateRequestedLoanAmount(String loan_amount) throws InvalidRequestAmount;
}
