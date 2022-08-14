package pedro.neurotech.neurotechapi.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pedro.neurotech.neurotechapi.model.CurrencyRate;

import java.sql.Date;
import java.util.List;

import static pedro.neurotech.neurotechapi.util.CurrencyRateCreator.*;


@DataJpaTest
@DisplayName("Tests for CurrencyRate Repository")
class CurrencyRateRepositoryTest {
    @Autowired
    private CurrencyRateRepository currencyRateRepository;


    @Test
    @DisplayName("Create and Persists a new currency rate register")
    public void save_PersistData(){
        CurrencyRate currencyRate = createCurrencyRate();
        CurrencyRate currencyRateSaved= this.currencyRateRepository.save(currencyRate);
        Assertions.assertThat(currencyRateSaved).isNotNull();
        Assertions.assertThat(currencyRateSaved.getId()).isNotNull();
        Assertions.assertThat(currencyRateSaved.getBase()).isEqualTo("BRL");
        Assertions.assertThat(currencyRateSaved.getDate()).isEqualTo(Date.valueOf("1996-08-15"));

    }

    @Test
    @DisplayName("Find The Latest Currency")
    public void findTheLatestCurrency(){
        CurrencyRate currencyRate = createCurrencyRate();
        CurrencyRate currencyRateSaved= this.currencyRateRepository.save(currencyRate);

        CurrencyRate currencyRateLatest = createCurrencyRateLatest();
        CurrencyRate currencyRateLatestSaved = this.currencyRateRepository.save(currencyRateLatest);

        CurrencyRate theLatestCurrencyRate = this.currencyRateRepository.findTop1ByOrderByIdDesc();

        Assertions.assertThat(theLatestCurrencyRate).isNotNull();
        Assertions.assertThat(theLatestCurrencyRate.getId()).isNotNull();
        Assertions.assertThat(theLatestCurrencyRate.getId()).isEqualTo(currencyRateLatestSaved.getId());

        Assertions.assertThat(theLatestCurrencyRate.getDate()).isEqualTo(currencyRateLatestSaved.getDate());

    }


    @Test
    @DisplayName("Find The Latest Currency with Empty Repository")
    public void findTheLatestCurrency_withEmptyRepository(){
        CurrencyRate theLatestCurrencyRate = this.currencyRateRepository.findTop1ByOrderByIdDesc();
        Assertions.assertThat(theLatestCurrencyRate).isNull();
    }

    @Test
    @DisplayName("Find Currencies in an interval with Empty Repository")
    public void findCurrenciesInInterval_withEmptyRepository(){
        List<CurrencyRate> currencyRateList = this.currencyRateRepository.findByDateGreaterThanEqualAndDateLessThanEqual(Date.valueOf("1970-08-15"), Date.valueOf("1980-08-15"));
        Assertions.assertThat(currencyRateList.isEmpty()).isTrue();
    }


}