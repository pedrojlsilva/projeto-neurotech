package pedro.neurotech.neurotechapi.CurrencyAPIClient;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pedro.neurotech.neurotechapi.model.CurrencyRate;
import pedro.neurotech.neurotechapi.service.CurrencyRateService;
import java.net.URI;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;


@Log4j2
@Component
public class CurrencyAPIClient implements DisposableBean, Runnable {
    public static final String RAPIDAPIKEY = System.getenv("RAPIDAPIKEY");
    public static final String RAPIDAPIHOST = System.getenv("RAPIDAPIHOST");

    @Autowired
    private CurrencyRateService currencyRateService;
    private Thread thread;
    private Boolean isToDestroy = false;

    public CurrencyAPIClient(){
        this.thread = new Thread(this);
        this.thread.start();
        isToDestroy = false;
    }

    private void initializeDB(){
        for(Long i=CurrencyAPIClientConfigs.daysBeforeToInitDB;i>0;i--){
            Date now = Date.valueOf(LocalDate.now());
            Date dayToConsult = Date.valueOf(now.toLocalDate().minusDays(i));


            RestTemplate template = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-RapidAPI-Key", RAPIDAPIKEY);
            headers.set("X-RapidAPI-Host", RAPIDAPIHOST);
            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

            try{
                ResponseEntity<CurrencyRate> response = template.exchange(URI.create("https://fixer-fixer-currency-v1.p.rapidapi.com/"+dayToConsult.toString()+"?base=BRL&symbols=USD"),
                        HttpMethod.GET,
                        entity,
                        CurrencyRate.class);
                log.info(response.getBody());
                currencyRateService.addNewRegistry(response.getBody());
                Thread.sleep(1);
            }catch(InterruptedException e){
                System.out.println(e);
            }


        }
    }

    @Override
    public void run(){

        try{
            log.info("Initializing Currency External API Client");
            Thread.sleep(1000);

        }catch(InterruptedException e){
            System.out.println(e);
        }

        //currencyRateService.deleteAll(); //Usar apenas se você quiser deletar os registros da base sem deletar a base
        if(!currencyRateService.isEmpty()){
            this.initializeDB();
        }
        System.out.println(RAPIDAPIHOST);


        while(!isToDestroy){
            RestTemplate template = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);

            headers.set("X-RapidAPI-Key", RAPIDAPIKEY);
            headers.set("X-RapidAPI-Host", RAPIDAPIHOST);
            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

            try{
                ResponseEntity<CurrencyRate> response = template.exchange(URI.create("https://fixer-fixer-currency-v1.p.rapidapi.com/latest?base=BRL&symbols=USD"),
                        HttpMethod.GET,
                        entity,
                        CurrencyRate.class);
                log.info(response.getBody());
                currencyRateService.addNewRegistry(response.getBody());
                Thread.sleep(CurrencyAPIClientConfigs.frequencyToUpdateDB*24*60*60*1000);
            }catch(InterruptedException e){
                System.out.println(e);
            }
        }


    }

    public void destroy(){
        isToDestroy = true;

    }
}
