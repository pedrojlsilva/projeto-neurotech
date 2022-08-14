package pedro.neurotech.neurotechapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pedro.neurotech.neurotechapi.model.CurrencyRate;
import pedro.neurotech.neurotechapi.service.CurrencyRateService;

import java.util.List;

@RestController
@RequestMapping("currencyrate")
@RequiredArgsConstructor
@Log4j2
public class ApiController {

    //Controle dos endpoints
    private final CurrencyRateService currencyRateService;

    @CrossOrigin
    @GetMapping(path="latest")
    public ResponseEntity<CurrencyRate> getLatestCurrency(){
        return new ResponseEntity<CurrencyRate>(currencyRateService.getLatest(),HttpStatus.OK);
    }
    @CrossOrigin
    @GetMapping(path="interval")
    public ResponseEntity<List<CurrencyRate>> getCurrencyWithInterval(@RequestParam(defaultValue = "1996-08-15", name = "startDate") String startDate, @RequestParam(defaultValue = "2022-08-15", name="endDate") String endDate){
        return new ResponseEntity<List<CurrencyRate>>(currencyRateService.getRatesBetweenDates(startDate, endDate),HttpStatus.OK);
    }



}
