package am.itspace.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    //Version with different endpoints
    @GetMapping("/v1/person")
    public PersonV1 personV1(){
        return new PersonV1("Bob Marley");
    }

    @GetMapping("/v2/person")
    public PersonV2 personV2(){
        return new PersonV2(new Name("Bob","Marley"));
    }

    //Version with params
    @GetMapping(value = "/person/param", params = "version=1")
    public PersonV1 paramV1(){
        return new PersonV1("Bob Marley");
    }

    @GetMapping(value = "/person/param", params = "version=2")
    public PersonV2 paramV2(){
        return new PersonV2(new Name("Bob","Marley"));
    }

    //With header
    @GetMapping(value = "/person/header", headers = "X-API-VERSION=1")
    public PersonV1 headerParamV1(){
        return new PersonV1("Bob Marley");
    }

    @GetMapping(value = "/person/header", headers = "X-API-VERSION=2")
    public PersonV2 headerParamV2(){
        return new PersonV2(new Name("Bob","Marley"));
    }



}
