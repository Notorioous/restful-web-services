package am.itspace.restfulwebservices.controller;

import am.itspace.restfulwebservices.model.SomeBean;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Provider;
import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {

    private SimpleBeanPropertyFilter filter;

    private FilterProvider filterProvider;

    @GetMapping("/filtering")
    public MappingJacksonValue retrieveSomeBean(){

        SomeBean someBean = new SomeBean("value1","value2","value3");

        filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1","field2");

        filterProvider = new SimpleFilterProvider().addFilter("SomeBeanFilter",filter);

        return getMapping(filterProvider,someBean);
    }

    @GetMapping("/filtering-list")
    public MappingJacksonValue retrieveSomeBeanList(){
        List<SomeBean> listOfSomeBeans = Arrays.asList(new SomeBean("value1", "value2", "value3"), new SomeBean("value12", "value22", "value32"));

        filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1","field3");

        filterProvider = new SimpleFilterProvider().addFilter("SomeBeanFilter",filter);

        return getMapping(filterProvider,listOfSomeBeans);
    }



    private MappingJacksonValue getMapping(FilterProvider filterProvider, Object objectForFilter){

        MappingJacksonValue mapping = new MappingJacksonValue(objectForFilter);

        mapping.setFilters(filterProvider);

        return mapping;
    }
}
