package io.javabrains.springbootstarter;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {

    @RequestMapping("/hi")
    String sayHello() {
        return "Nilay";
    }

}
