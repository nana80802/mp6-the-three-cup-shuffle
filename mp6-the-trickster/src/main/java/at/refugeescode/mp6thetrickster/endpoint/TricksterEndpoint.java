package at.refugeescode.mp6thetrickster.endpoint;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@RestController
public class TricksterEndpoint {

    private RestTemplate restTemplate;

    @Value("#{'${cups.ports}'.split(',')}")
    private List<String> cupsPorts;

    public TricksterEndpoint(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/play")
    void getPlay() {
        removeCoinFromCups();
        hideRandomlyCoinInCup();

    }

    @GetMapping("/choose/{number}")
    Boolean chooseCup(@PathVariable Integer number){
        String port = cupsPorts.get(number - 1);
        String url = "http://localhost:" + port + "/coin";
        ResponseEntity<Boolean> result = restTemplate.getForEntity(url, Boolean.class);
        return result.getBody();

    }

    private void removeCoinFromCups() {
        cupsPorts.stream()
                .map(port -> "http://localhost:"+port+"/coin")
                .forEach(url -> restTemplate.delete(url));
    }

    private void hideRandomlyCoinInCup() {
        Collections.shuffle(cupsPorts);
        String randomPort = cupsPorts.get(0);
        String url = "http://localhost:" + randomPort + "/coin";
        restTemplate.put(url, null);

    }



}
