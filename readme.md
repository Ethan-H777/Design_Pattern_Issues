# Assignment 2: Design Patterns

## Solutions to the Key Issues

### RAM Issue

#### Flyweight

- Client:             ProductImpl.java
- Concrete Flyweight: ProductData.java
- Flyweight:          ProductDataFlyweight.java
- Flyweight Factory:  ProductFlyweightFactory.java

My device would run out of memory and fail the testings when product data is 5000000 size. I was using 1000 size for the purposing of implementing.
ed post link: https://edstem.org/au/courses/11117/discussion/1388745

### Too Many Orders

#### strategy
- context: the four order classes
- strategy: BusinessStrategy.java
- concrete strategy: BusinessImpl.java, PersonalImpl.java, BusinessSubscription.java, PersonalSubscription.java

#### Alternative Solution (400 words max)

##### Solution Summary

The solution summary goes here, you should describe what changes you have made to the codebase, be specific to the classes and methods you changed and how.

##### Solution Benefit

How did you solution solve the problem, be brief.

### Bulky Contact Method
#### Chain of Responsibility

- Client:             ContactHandler.java
- Concrete Handler:   SMSHandler.java, MailHandler.java, PhoneCallHandler.java, EmailHandler.java, MerchandiserHandler.java, CarrierPigeonHandler.java
- Handler:            Handler.java

### System Lag


### Hard to Compare Products


### Slow Order Creation


## Notes About the Submission