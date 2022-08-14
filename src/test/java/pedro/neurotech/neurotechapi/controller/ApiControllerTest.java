package pedro.neurotech.neurotechapi.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pedro.neurotech.neurotechapi.model.CurrencyRate;


@ExtendWith(SpringExtension.class)
class ApiControllerTest {
    @InjectMocks
    private ApiController apiController;

    @Mock
    CurrencyRate currencyRateMock;



}