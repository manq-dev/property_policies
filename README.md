To run the app hit:

`./mvnv spring-boot:run`

In file `sample-request.http` there is all data needed to execute acceptance criteria on working app.

Coefficients can be parametrized in `application.properties`

If you wish to add new RiskType just add new enum value to RiskType.java, specify how should coefficient be calculated and finally add new properties to `application.properties`.