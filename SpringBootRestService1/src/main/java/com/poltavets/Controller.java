package com.poltavets;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/controller")
public class Controller {

    @GetMapping
    public Collection<String> showStatus() {
        return new ResponseJson().getJsons();
    }
}
