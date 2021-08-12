package com.getquote.service;

import com.getquote.exceptions.FundsNotAvailable;
import com.getquote.model.Offer;

import java.util.List;

public interface FundsService {

    public List<Offer> getOffersList();

    public long getMarketAvailableFunds();

    public void checkAvailableFunds(int loanAmount) throws FundsNotAvailable;


}
