package com.getquote.serviceImpl;

import com.getquote.exceptions.InvalidRequestAmount;
import com.getquote.service.AmountValidationService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public @Data
class AmountValidationImpl implements AmountValidationService {

    @Value("${loan.amount.minimum}")
    private int minimumLoanAmount;

    @Value("${loan.amount.maximum}")
    private int maximumLoanAmount;

    @Value("${loan.amount.multiple}")
    private int loanAmtMultiple;


    @Override
    public void validateRequestedLoanAmount(String loanAmount) throws InvalidRequestAmount {

        isLoanAmountValid(loanAmount);

        int requestedAmt = Integer.parseInt(loanAmount);

        validateLimits(requestedAmt);
        validateMultiplier(requestedAmt);

    }

    public void validateMultiplier(int loanAmount) throws  InvalidRequestAmount {
        if (!((loanAmount % getLoanAmtMultiple()) == 0)) {
            log.debug(String.format("Please request a valid loan amount in multiple of:  %s ", getLoanAmtMultiple()));
            throw new InvalidRequestAmount(String.format("Please request a valid loan amount in multiple of:  %s ", getLoanAmtMultiple()));
        }

    }

    public void validateLimits(int loanAmount) throws InvalidRequestAmount {

        if (!(getMinimumLoanAmount() <= loanAmount && (loanAmount <= getMaximumLoanAmount()))) {
           log.debug(String.format("Please request a valid loan amount in between :  %s  and : %s ", getMinimumLoanAmount(), getMaximumLoanAmount()));
            throw new InvalidRequestAmount(String.format("Please request a valid loan amount in between :  %s  and : %s ", getMinimumLoanAmount(), getMaximumLoanAmount()));

        }
    }


    public void isLoanAmountValid(final String reqAmount) throws  InvalidRequestAmount {
        if (!(StringUtils.isNotEmpty(reqAmount) && !reqAmount.contains(".") && NumberUtils.isNumber(reqAmount))) {
            log.debug(String.format("Please request a valid loan amount in number"));
            throw new InvalidRequestAmount(String.format("Please request a valid loan amount in number"));

        }
    }


}