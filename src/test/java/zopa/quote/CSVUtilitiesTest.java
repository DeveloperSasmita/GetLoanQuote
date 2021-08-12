package zopa.quote;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import zopa.quote.model.Offer;
import zopa.quote.util.CSVUtility;

import java.io.FileNotFoundException;
import java.io.IOException;


@Slf4j
@SpringBootTest
@TestPropertySource("/application.properties")
public class CSVUtilitiesTest {

    @Value("${loan.payment.months}")
    private String paymentYear;

    @Test
    public void testWithPropertyFile() {
        org.assertj.core.api.Assertions.assertThat(paymentYear).isEqualTo("36");
    }

    @Test
    public void testFileFound() throws IOException {
        CSVUtility.loadCSVFile("LenderData.csv", Offer.class);
    }

    //Random filename

    @Test
    public void testCSVFileNotFound() throws IOException {
        Assertions.assertThrows(IOException.class, () -> {

            CSVUtility.loadCSVFile("LenderData5.CSV", Offer.class);
        });
    }


}
