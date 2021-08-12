package com.getquote;

import com.getquote.exceptions.InvalidRequestAmount;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.getquote.service.AmountValidationService;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AmountValidationTests {

    @Autowired
    private AmountValidationService amountValidationService;


    @Test
    public void testInvalidAmountNumber() throws InvalidRequestAmount {
        Assertions.assertThrows(InvalidRequestAmount.class,
                () -> {
                    amountValidationService.isLoanAmountValid("110A");
                });
    }


    @Test
    public void testMinimumInvalidAmount() throws InvalidRequestAmount {
        Assertions.assertThrows(InvalidRequestAmount.class,
                () -> {
                    amountValidationService.validateLimits(900);
                });
    }


    @Test
    public void testMaximumInvalidAmount() throws InvalidRequestAmount {
        Assertions.assertThrows(InvalidRequestAmount.class,
                () -> {
                    amountValidationService.validateLimits(16000);
                });

    }

    @Test
    public void testInvalidMultiplierAmount() throws InvalidRequestAmount {
        Assertions.assertThrows(InvalidRequestAmount.class, () -> {
            amountValidationService.validateMultiplier(220);
        });
    }


}
