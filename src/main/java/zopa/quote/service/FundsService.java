package zopa.quote.service;

import zopa.quote.exceptions.FundsNotAvailable;
import zopa.quote.model.Offer;

import java.util.List;

public interface FundsService {

    public List<Offer> getOffersList();

    public long getMarketAvailableFunds();

    public void checkAvailableFunds(int loanAmount) throws FundsNotAvailable;


}
