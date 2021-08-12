package com.getquote.serviceImpl;

import com.getquote.exceptions.FundsNotAvailable;
import com.getquote.model.Offer;
import com.getquote.service.FundsService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.getquote.util.CSVUtility;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public @Data

class FundsServiceImpl implements FundsService {

    private CSVUtility cSVUtility;

    private List<Offer> offersList;

    private long marketAvailableFunds;

    @Value("${filename}")
    private String filename;


    /**
     * Process CSV file.
     * Loads once Application is loaded
     *
     * @return list of all available offers
     * @return total available amount
     */
    @PostConstruct
    public void init() throws IOException {

        Path path = Paths.get(filename);
        log.info("Filepath to load CSV File------------------------" + path.toString());

        setOffersList(cSVUtility.loadCSVFile(path.toString(), Offer.class));

        offersList.sort(Comparator.comparing(Offer::getRate));
        setMarketAvailableFunds(offersList.stream().collect(Collectors.summingInt(Offer::getAmount)));
    }


    public void checkAvailableFunds(final int requestedAmount) throws FundsNotAvailable {

        if (Long.valueOf(requestedAmount) > getMarketAvailableFunds()) {
            log.info(String.format("Market does not have enough offer to fulfil the request . Requested Amount : (%s)  , Available Funds : (%s)", requestedAmount, getMarketAvailableFunds()));
            throw new FundsNotAvailable();

        }
    }

}
