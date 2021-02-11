package com.kubilaycicek;

import java.time.LocalDate;
import java.time.Month;

public class Main {

    public static void main(String[] args) {
        Customer customer = new Customer("Kubilay",
                "kubilay@gmail.com",
                "+05543454532",
                LocalDate.of(1993, Month.MAY, 13));

        //System.out.println(new CustomerValidatorService().isValid(customer));

        // if valid we can store customer in db

        // Using Combinator pattern

        CustomerRegistrationValidator.ValidationResult result = CustomerRegistrationValidator.isEmailValid()
                .and(CustomerRegistrationValidator.isPhoneNumberValid())
                .and(CustomerRegistrationValidator.isAdult())
                .apply(customer);

        System.out.println(result);

        if (result!= CustomerRegistrationValidator.ValidationResult.SUCCESS){
            throw new IllegalArgumentException(result.name());
        }


    }
}
