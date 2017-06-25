package com.cucumber007.prototypes.activities.giver.objects;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class MyDouble {

    //class to avoid scientific notation in retrofit get requests
    private Double value;
    private static DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
    private static DecimalFormat df;
    static {
        otherSymbols.setDecimalSeparator('.');
        df = new DecimalFormat("#", otherSymbols);
        df.setMaximumFractionDigits(8);
    }

    public MyDouble(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return df.format(value);
    }
}
