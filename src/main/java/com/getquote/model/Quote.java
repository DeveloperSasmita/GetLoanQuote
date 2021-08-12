package com.getquote.model;

import lombok.Data;

import java.math.BigDecimal;

public @Data
class Quote {

    /**
     * Requested Amount.
     */
    private int amount;

    /**
     * Rate.
     */
    private double rate;

    /**
     * Monthly Repayment.
     */
    private BigDecimal monthlyRepayment;

    /**
     * Total Repayment.
     */
    private BigDecimal totalRepayment;

    public String beautyPrint() {

        String newline = System.lineSeparator();
        String print = "";
        print += String.format("Requested amount: £%d", getAmount()) + newline;
        print += String.format("Annual Interest Rate: £%.1f%%", getRate()*100) + newline;
        print += String.format("Monthly repayment: £%.2f", getMonthlyRepayment()) + newline;
        print += String.format("Total repayment: £%.2f", getTotalRepayment());

        return print;
    }

    /**
     * Solution constructor.
     *
     * @param amount      Requested/offered amount
     * @param rate  Offered Rate
     * @param monthlyRepayment   Repayment in months
     * @param totalRepayment  Total repayment over 36 months
     */
  /*  public Quote(int amount, double rate, double monthlyRepayment, double totalRepayment) {
        this.amount = amount;
        this.rate = rate;
        this.monthlyRepayment = monthlyRepayment;
        this.totalRepayment = totalRepayment;
    }*/


}
