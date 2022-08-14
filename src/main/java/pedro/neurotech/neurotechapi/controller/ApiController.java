package pedro.neurotech.neurotechapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pedro.neurotech.neurotechapi.domain.CurrencyRate;
import pedro.neurotech.neurotechapi.service.CurrencyRateService;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("currencyrate")
@RequiredArgsConstructor
@Log4j2
public class ApiController {

    //Controle dos endpoints
    private final CurrencyRateService currencyRateService;

    @GetMapping(path="latest")
    public ResponseEntity<CurrencyRate> getLatestCurrency(){
        return new ResponseEntity<CurrencyRate>(currencyRateService.getLatest(),HttpStatus.OK);
    }

    @GetMapping(path="interval")
    public ResponseEntity<List<CurrencyRate>> getCurrencyWithInterval(@RequestParam(defaultValue = "1996-08-15", name = "startDate") String startDate, @RequestParam(defaultValue = "2022-08-15", name="endDate") String endDate){
        return new ResponseEntity<List<CurrencyRate>>(currencyRateService.getRatesBetweenDates(startDate, endDate),HttpStatus.OK);
    }



}
