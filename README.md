# Quote Generator

## Technology Stack Used

Java : 11 , Junit5 , Springboot:2.5.3

## External Library Used

[OpenCSV](http://opencsv.sourceforge.net)  : 5.3                                        
[Lombok](https://projectlombok.org/) : 1.18

## Assumptions

- Every file under src/main/resources will be loaded into the classpath. The sample CSV file : `resources\LenderData.csv`
- Application will not start if filepath/filename is not valid(if file not found/loaded)
- Caching has been enabled based on CSV file has fixed data as it loads once when application starts.


## Solution Design

1. Validate if the requested amount violets the restrictions 
2. Verify if the Market has sufficient funds to provide the loan\quote
3. Sort the collection by lowest rate and calculate the average rate
5. Calculate monthly and total repayment from :
   https://en.wikipedia.org/wiki/Amortization_calculator#The_formu

## Logging

- `logging.level.zopa=INFO` can be made to `DEBUG` on the application.properties file.

## Run locally

1. CSV File path, PaymentMonths, Maximum,  Minimum , Multiple of  loan amount are configurable and can be changed in application.properties(optional)
2. Sample LenderData.csv file is at src\test\resources\

If run using JAR (Optional port)                                                        
`java -jar target/quote-0.0.1-SNAPSHOT.jar --server.port=8080`                                         
Property can be passed in the command line to override the configuration provided in
the [configuration](src/main/resources/application.properties) 

Application URL :` http://localhost:port/zopa-rate` (POST)                                                                                                                      

One argument input body : 1700                                                                                          
Expected Response -                                                                          
Requested amount: £1700                                                                       
Annual Interest Rate: £7,2%                                                                   
Monthly repayment: £52,64                                                                        
Total repayment: £1895,04                                                                        


## Run Unit Tests

```mvn clean test```

...
