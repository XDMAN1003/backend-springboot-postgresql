package com.amigo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@SpringBootApplication
@RestController
@RequestMapping("customers")
public class Main {
    private final CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args){
        SpringApplication.run(Main.class, args);
    }

//    @GetMapping("/")
//    public GreetResponse greet(){
//        return new GreetResponse("Hello", List.of("Python","Java"), new Person("amigo", 28, 30_000));
//    }
//
//    record Person(String name, int age, double saving){}
//    record GreetResponse(String greet, List<String> favProgLang, Person person){}
    @GetMapping
    public List<Customer> getCustomers(){
        return customerRepository.findAll();
    }

    record NewCustomerRequest(String name, String email, Integer age){}
    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request){
        Customer customer = new Customer();
        customer.setAge(request.age());
        customer.setEmail(request.email());
        customer.setName(request.name());
        customerRepository.save(customer);
        //System.out.println(request.age());
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id){
        customerRepository.deleteById(id);
    }

    @PutMapping("{customerId}")
    public void updateCustomer(@PathVariable("customerId") Integer id, @RequestBody NewCustomerRequest request){
        Customer customer = customerRepository.findById(id).get();
        if(Objects.nonNull(request.age())){ customer.setAge(request.age());}
        if(Objects.nonNull(request.email())){ customer.setEmail(request.email());}
        if(Objects.nonNull(request.name())){ customer.setName(request.name());}
        customerRepository.save(customer);
    }
}
