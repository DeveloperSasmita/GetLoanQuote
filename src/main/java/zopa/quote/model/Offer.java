package zopa.quote.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

public @Data
class Offer {

    @CsvBindByPosition(position = 0)
    private String name;

    @CsvBindByPosition(position = 1)
    private double rate;

    @CsvBindByPosition(position = 2)
    private int amount;

    @Override
    public String toString() {
        return " Data pulled from CSV File{" +
                "name=" + name +
                ", amount=" + amount +
                " , rate=" + rate + '\'' +
                '}';
    }
}