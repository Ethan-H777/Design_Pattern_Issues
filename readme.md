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

#### Alternative Solution (400 words max)
##### Solution Summary
I override the equals() method of ProductImpl class,
therefore, I can just check if product1.equals(product2) to compare two Product objects.

##### Solution Benefit
This way is effective and simple. Additionally, this solution is also beneficial for future maintenance.
Any changes to the comparing logic of Product class, other class/code will not be affected such as Order class.
This also follows the encapsulation principle where Order class have no knowledge about the detailed comparing logic between products.

I was trying to cast Product objects into ProductImpl objects, then I can compare the flyweights stored in two objects.
Because, identical products shall have the same name, cost and flyweight. However, casting is not considered in the scope test.
The mock does not support casting Product object to ProductImpl object, in order to access getFlyweight() method.
The Product interface is not allowed to modify too, so the best solution is overriding which has less complexity than adapter pattern.
(adapter pattern is not covered in this unit, so I choose Override)

### Slow Order Creation


## Notes About the Submission