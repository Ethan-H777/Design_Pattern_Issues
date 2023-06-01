# Assignment 2: Design Patterns

## Solutions to the Key Issues


### RAM Issue
#### Flyweight

- Client:             ProductImpl.java
- Concrete Flyweight: ProductData.java
- Flyweight:          ProductDataFlyweight.java
- Flyweight Factory:  ProductFlyweightFactory.java

My device would run out of memory and fail the testings when dealing with the whole product data (5000000 size). I lower it to 1000 size for the purposing of implementing.

ed post link: https://edstem.org/au/courses/11117/discussion/1388745


### Too Many Orders
#### strategy
- context: the four order classes
- strategy: BusinessStrategy.java
- concrete strategy: BusinessImpl.java, PersonalImpl.java, BusinessSubscription.java, PersonalSubscription.java

I have reduced number of Order class by half by introducing business strategy.
(e.g. from 66 * 2 * 2 to 66 * 2, from 2 * 2 * 2 to 2 * 2)



### Bulky Contact Method
#### Chain of Responsibility

- Client:             ContactHandler.java
- Concrete Handler:   SMSHandler.java, MailHandler.java, PhoneCallHandler.java, EmailHandler.java, MerchandiserHandler.java, CarrierPigeonHandler.java
- Handler:            Handler.java



### System Lag
#### Lazy Load: lazy initialization

- Lazy initialization: SPFEAFacade.java

I have implemented lazy load within the Facade class by caching customers. 
The getCustomer() has been modified to adapt lazy initialization. 
This save time when user view the same customer multiple times.
The getting field information messages will be output at first run , then not anymore for the same customer.

ed post: https://edstem.org/au/courses/11117/discussion/1393171


### Hard to Compare Products
#### Value Object

- Value Object: ProductImpl.java

The equals() and hashCode() methods have been override. 



### Slow Order Creation
#### Unit Of Work

- UOW: UnitOfWork.java
- Client: SPFEAFacade.java

## Notes About the Submission

- to run: gradle run
- to test: gradle test