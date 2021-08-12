package com.getquote;

import com.getquote.model.Offer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.getquote.exceptions.FundsNotAvailable;
import com.getquote.service.FundsService;


@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class FundsServiceTests {

    @Autowired
    private FundsService fundsService;

    @Test
    public void testMarketInsufficientFunds() throws FundsNotAvailable {
        Assertions.assertThrows(FundsNotAvailable.class,
                () -> {
                    fundsService.checkAvailableFunds(5000);
                });
    }


    @Test
    public void testLendersSortedByRate() {
        double rate = 0;
        for (Offer offer : fundsService.getOffersList()) {
            Assertions.assertTrue(rate <= offer.getRate());
            rate = offer.getRate();
        }
        Assertions.assertTrue(fundsService.getOffersList().get(0).getName().equals("Jane"));

    }


    @Test
    public void testCSVParseRecords() {
        Assertions.assertTrue(fundsService.getOffersList().size() == 7);
    }

    @Test
    public void testAvailableMarketFunds() {
        Assertions.assertTrue(fundsService.getMarketAvailableFunds() == 2330);
    }
}

