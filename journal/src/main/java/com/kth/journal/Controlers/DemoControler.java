package com.kth.journal.Controlers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoControler {

    @GetMapping("/api/auth/demo")
    public String demo() {
        return "Hello from the demo controller!";
    }
}
