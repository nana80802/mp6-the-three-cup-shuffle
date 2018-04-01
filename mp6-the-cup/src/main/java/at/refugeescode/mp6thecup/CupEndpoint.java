package at.refugeescode.mp6thecup;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/coin")
public class CupEndpoint {
    private Boolean coin;

    @DeleteMapping
    void releaseCup(){
        coin = false;
    }

    @PutMapping
    void putCoin(){
        coin = true;
    }

    @GetMapping
    Boolean getCoin(){
        return coin;
    }
}
