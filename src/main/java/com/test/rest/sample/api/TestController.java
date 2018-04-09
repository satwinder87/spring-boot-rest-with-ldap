package com.test.rest.sample.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {

    @RequestMapping(value = "customers/name", method = RequestMethod.GET)
    public List<String> getListOfCustomers(){
        List<String> names = new ArrayList<>();
        names.add("Name_1");
        names.add("Name_2");
        names.add("Name_3");
        names.add("Name_4");
        names.add("Name_5");
        return names;
    }
}
