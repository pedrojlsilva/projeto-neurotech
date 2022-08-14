package pedro.neurotech.neurotechapi.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pedro.neurotech.neurotechapi.model.CurrencyRate;
import pedro.neurotech.neurotechapi.repository.CurrencyRateRepository;

import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import pedro.neurotech.neurotechapi.util.CurrencyRateCreator;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



import static pedro.neurotech.neurotechapi.util.CurrencyRateCreator.createCurrencyRate;

@DataJpaTest
@DisplayName("Tests for CurrencyRate Repository")
@ExtendWith(SpringExtension.class)
class CurrencyRateServiceTest {
    @InjectMocks
    private CurrencyRateService currencyRateService;

    @Mock
    public CurrencyRateRepository currencyRateRepositoryMock;

    @BeforeEach
    void setup() {
        CurrencyRate validCurrency = CurrencyRateCreator.createCurrencyRateWithValidID();

        BDDMockito.when(currencyRateRepositoryMock.save(ArgumentMatchers.any(CurrencyRate.class)))
                .thenReturn(validCurrency);


        BDDMockito.when(currencyRateRepositoryMock.findTop1ByOrderByIdDesc())
                .thenReturn(validCurrency);

        BDDMockito.when(currencyRateRepositoryMock.count())
                .thenReturn(1L);


        List<CurrencyRate> validCurrencyList = new ArrayList<>(List.of(CurrencyRateCreator.createCurrencyRateWithValidID()));

        BDDMockito.when(currencyRateRepositoryMock.findByDateGreaterThanEqualAndDateLessThanEqual(
                        ArgumentMatchers.any(Date.class), ArgumentMatchers.any(Date.class)))
                .thenReturn(validCurrencyList);
    }



    @Test
    @DisplayName("Add new registry to currencyRateService")
    void addNewRegistry() {
        CurrencyRate currencyRate = createCurrencyRate();
        CurrencyRate currencyRateSaved = this.currencyRateService.addNewRegistry(currencyRate);

        Assertions.assertThat(currencyRateSaved).isNotNull();
        Assertions.assertThat(currencyRateSaved.getId()).isNotNull();
        Assertions.assertThat(currencyRateSaved).isEqualTo(currencyRateSaved);

    }


    @Test
    @DisplayName("get the latest registry of service")
    void getLatest() {
        CurrencyRate currencyRate = CurrencyRateCreator.createCurrencyRateWithValidID();

        CurrencyRate currencySaved = currencyRateService.getLatest();


        Assertions.assertThat(currencySaved).isNotNull();
        Assertions.assertThat(currencySaved).isEqualTo(currencyRate);
    }

    @Test
    @DisplayName("get the latest registry of service with empty repo")
    void getLatest_withEmptyRepo() {

        BDDMockito.when(currencyRateRepositoryMock.findTop1ByOrderByIdDesc())
                .thenReturn(null);

        CurrencyRate currencySaved = currencyRateService.getLatest();

        Assertions.assertThat(currencySaved).isNull();

    }

    @Test
    @DisplayName("get the registries between dates with full-filled repository")
    void getRatesBetweenDates() {
        Date now = Date.valueOf(LocalDate.now());
        for(Long i = 30L; i>0L; i--){

            Date dayToConsult = Date.valueOf(now.toLocalDate().minusDays(i));
            CurrencyRate currencyRate = createCurrencyRate();
            currencyRate.setDate(dayToConsult);
            CurrencyRate currencyRateSaved = this.currencyRateService.addNewRegistry(currencyRate);
        }

        List<CurrencyRate> currencyInterval = currencyRateService.getRatesBetweenDates(
                now.toString(),
                now.toLocalDate().minusDays(5).toString());

        Assertions.assertThat(currencyInterval).isNotNull();
        Assertions.assertThat(currencyInterval).isNotEmpty();

    }

    @Test
    @DisplayName("get the registries between dates with empty repository")
    void getRatesBetweenDates_withEmptyRepo() {

        BDDMockito.when(currencyRateRepositoryMock.findByDateGreaterThanEqualAndDateLessThanEqual(
                        ArgumentMatchers.any(Date.class), ArgumentMatchers.any(Date.class)))
                .thenReturn(Collections.emptyList());

        Date now = Date.valueOf(LocalDate.now());


        List<CurrencyRate> currencyInterval = currencyRateService.getRatesBetweenDates(
                now.toString(),
                now.toLocalDate().minusDays(5).toString());

        Assertions.assertThat(currencyInterval).isNotNull();
        Assertions.assertThat(currencyInterval).isEmpty();

    }
}