# Combinator Design Pattern

Before Combinator Design Pattern

    public class CustomerValidatorService {
     private boolean isEmailValid(String email) {
         return email.contains("@");
     }

     private boolean isPhoneNumberValid(String phoneNumber) {
         return phoneNumber.startsWith("+0");
     }

     private boolean isAdult(LocalDate dob) {
         return Period.between(dob, LocalDate.now()).getYears() > 18;
     }

     public boolean isValid(Customer customer) {
          return isEmailValid(customer.getEmail()) &&
                 isPhoneNumberValid(customer.getPhoneNumber()) &&
                 isAdult(customer.getDob());
     }
    }

After Combinator Design Pattern

     public interface CustomerRegistrationValidator
         extends Function<Customer, CustomerRegistrationValidator.ValidationResult> {

     static CustomerRegistrationValidator isEmailValid() {
         return customer -> customer.getEmail().contains("@") ?
                 ValidationResult.SUCCESS : ValidationResult.EMAIL_NOT_VALID;
     }

     static CustomerRegistrationValidator isPhoneNumberValid() {
         return customer -> customer.getPhoneNumber().startsWith("+0") ?
                 ValidationResult.SUCCESS : ValidationResult.PHONE_NUMBER_NOT_VALID;
     }

     static CustomerRegistrationValidator isAdult() {
         return customer -> Period.between(customer.getDob(), LocalDate.now()).getYears() > 18 ?
                 ValidationResult.SUCCESS : ValidationResult.IS_NOT_AND_ADULT;
     }

     default CustomerRegistrationValidator and(CustomerRegistrationValidator other) {
         return customer -> {
             ValidationResult result = this.apply(customer);
             return result.equals(ValidationResult.SUCCESS) ? other.apply(customer) : result;
         };
     }

     enum ValidationResult {
         SUCCESS,
         PHONE_NUMBER_NOT_VALID,
         EMAIL_NOT_VALID,
         IS_NOT_AND_ADULT
     }
    }
