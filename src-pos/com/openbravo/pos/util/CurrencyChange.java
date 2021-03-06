//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2007-2009 Openbravo, S.L.
//    http://www.openbravo.com/product/pos
//
//    This file is part of Openbravo POS.
//
//    Openbravo POS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    Openbravo POS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Openbravo POS.  If not, see <http://www.gnu.org/licenses/>.
package com.openbravo.pos.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import com.openbravo.format.DoubleUtils;
import com.openbravo.format.Formats;

public final class CurrencyChange {

    public static double DOLAR_CHANGE = 19;//0.046;
    // public static DecimalFormat FORMAT_DOLLAR = (DecimalFormat) DecimalFormat.getCurrencyInstance(Locale.US);
    // public static DecimalFormat FORMAT_LOCALE = (DecimalFormat) DecimalFormat.getCurrencyInstance(Locale.getDefault());

    private CurrencyChange() {
    }
    /*
    public static String formatDollarValue(Object value) {
        if (value == null) {
            return FORMAT_DOLLAR.format(0);
        } else {
            return FORMAT_DOLLAR.format(value);
        }
    }
    
    public static String formatLocaleValue(Object value) {
        if (value == null) {
            return FORMAT_LOCALE.format("0");
        } else {
            return FORMAT_LOCALE.format(value);
        }
    }
*/
    public static double changeDollarToPeso(double dEuros) {
        return Math.rint( (dEuros * DOLAR_CHANGE) / 1 );
        //return RoundUtils.round((dEuros * DOLAR_CHANGE) / 1);
        //return Math.round((dEuros * DOLAR_CHANGE) / 1);
       // return DoubleUtils.fixDecimals((dEuros * DOLAR_CHANGE) / 1);
        
    }

    public static double changePesoToDollar(double dPts) {
        return Math.rint (100.0 * dPts / DOLAR_CHANGE) / 100.0;
        // return RoundUtils.round((dPts * 1) / DOLAR_CHANGE);
        //return Math.round((dPts * 1) / DOLAR_CHANGE);
    	//double value = (dPts * 1) / DOLAR_CHANGE;
    	//return Math.round(Math.rint(value * 1000000.0) / 1000000.0);
    	//return DoubleUtils.fixDecimals(value);
    }

    public static void main(String[] args) {
        double amt = 12.1;
        System.out.println(changeDollarToPeso(changePesoToDollar(amt)));
        System.out.println(changePesoToDollar(amt));
        //System.out.println(changePtsToEuros(changeEurosToPts(amt)));
    }
/*
    public static void setCurrencyDollar() {
        DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance(Locale.US);
        String strange = "'$' #,##0.00";
        Formats.setCurrencyPattern(strange, dfs);
    }

    public static void setCurrencyLocale() {
        Formats.setCurrencyPattern(null);
    }*/
}
