package pedro.neurotech.neurotechapi.util;

import pedro.neurotech.neurotechapi.model.CurrencyRate;

import java.sql.Date;
import java.util.Map;

public class CurrencyRateCreator {

    public static CurrencyRate createCurrencyRate(){
        return CurrencyRate.builder().date(Date.valueOf("1996-08-15")).base("BRL")
                .rates(Map.ofEntries(Map.entry("USD", 0.1987456))).build();
    }

    public static CurrencyRate createCurrencyRateLatest(){
        return CurrencyRate.builder().date(Date.valueOf("2022-08-15")).base("BRL")
                .rates(Map.ofEntries(Map.entry("USD", 0.1987456))).build();
    }

    public static CurrencyRate createCurrencyRateWithValidID(){
        return CurrencyRate.builder().id(1L).date(Date.valueOf("1997-08-15")).base("BRL")
                .rates(Map.ofEntries(Map.entry("USD", 0.2356479))).build();
    }
    public static CurrencyRate createCurrencyRateWithInvalidID(){
        return CurrencyRate.builder().id(1L).date(Date.valueOf("1998-08-15")).base("BRL")
                .rates(Map.ofEntries(Map.entry("USD", 0.0123456))).build();
    }
}
