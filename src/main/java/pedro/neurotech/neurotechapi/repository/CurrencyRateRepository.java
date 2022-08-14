package pedro.neurotech.neurotechapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pedro.neurotech.neurotechapi.domain.CurrencyRate;

import java.sql.Date;
import java.util.List;


public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long>{
      CurrencyRate findTop1ByOrderByIdDesc();
      List<CurrencyRate> findByDateGreaterThanEqualAndDateLessThanEqual(Date startDate, Date endDate);


}
