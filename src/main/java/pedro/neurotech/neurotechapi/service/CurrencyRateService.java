package pedro.neurotech.neurotechapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pedro.neurotech.neurotechapi.model.CurrencyRate;
import pedro.neurotech.neurotechapi.repository.CurrencyRateRepository;

import java.sql.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyRateService {
    private final CurrencyRateRepository currencyRateRepository;

    //Business Rules
    public CurrencyRate addNewRegistry(CurrencyRate currency) {
        return currencyRateRepository.save(currency);
    }

    public boolean isEmpty() {
        return currencyRateRepository.count() > 0 ? true : false;
    }

    public void deleteAll() {
        System.out.println(currencyRateRepository.count());
        currencyRateRepository.deleteAll();
        System.out.println(currencyRateRepository.count());
    }

    public CurrencyRate getLatest() {
        return currencyRateRepository.findTop1ByOrderByIdDesc();
    }

    public List<CurrencyRate> getRatesBetweenDates(String startDate, String endDate) {
        Date _startDate = Date.valueOf(startDate);
        Date _endDate = Date.valueOf(endDate);
        return currencyRateRepository.findByDateGreaterThanEqualAndDateLessThanEqual(_startDate,_endDate);
    }
}




