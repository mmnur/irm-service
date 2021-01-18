# irm-service

## How to build?
gradle clean build

## How to run?
java -jar build\libs\irm-service-1.0.jar

## How to use?
### Creating user

POST /users/register HTTP/1.1  
Host: localhost:9090  
Content-Type: application/json  
Cache-Control: no-cache  
  
{  
  "displayName":"Chris Matthews",  
	"email":"chris1@example.com",  
	"username":"chris1",  
	"password":"chris123"  
}  
  
### Creating organization

POST /orgs/register HTTP/1.1  
Host: localhost:9090  
Content-Type: application/json  
Cache-Control: no-cache  
  
{  
	"displayName":"Swedish Hospital",  
	"email":"admin@swedish.com",  
	"username":"adminsd",  
	"password":"adminsd123"  
}  
  
### Registering device

#### Init request
POST /devices/initreg HTTP/1.1  
Host: localhost:9090  
Content-Type: application/json  
Cache-Control: no-cache  

{  
    "displayName":"device1",  
    "ownerEID":"usr-c46a3676-efae-46fc-83ea-ee37bbdb0a18",  
    "publicKey":"MIIBIzANBgkqhkiG9w0BAQEFAAOCARAAMIIBCwKCAQIA21DhF2rEiz6EJk7bir67qbnzLHQBWODQBpM2Rc/eWgMirSEjPwYV4g+PXv1On8dEgaCXhl0JrXnMhPknJ6WJVnFOzYwr0vEoVPitsG9zrh/Xp2Bt9qauMy05wn1kcXNSiqHrNoGZ507v9R8WuwSRIjpEqYT/QjJRilcFAPr+5d7NjKzE6GPw+l6sUQvA1yJVyLoDV61Ow4jvlemmNTEtM2at9fvOa7IxduLPonQFhkeR3pUC1p3EWJfZIITDQk7XDlK/o0RtIcXQ8zta37f5Jwn2hnkGWhdiRZzulFvmi/HpMN8VZKQ7qF1fpSXpX5kpQnwUqxr9r1obx6NJ0MimVbcCAwEAAQ=="  
}  
  
#### Finish request

POST /devices/finishreg HTTP/1.1  
Host: localhost:9090  
Content-Type: application/json  
Cache-Control: no-cache  
Postman-Token: 708f2365-cc3b-6e4d-9196-22a5d0d90247  
  
{  
    "challenge":"chlng-d73719f2-ee48-46f9-8bb1-c52d6b76b7111610984472683",  
    "challengeResponse":"NTgsLCUv3poJiMy5KGTXm0JnTOXQU/87GcJsXKeSUtY0pMHi7RAKsLucUHWxYBtst5SElv8g1wef8fyUwZhxX6iL9951jEbsEvJaDlfo4E0XL1tpTBinHWKjJT1zxVK5VQISg6OHSAMs89b+neAh8/fKj8kt+IRBjDGQaVxwV0VZ5vXRLv8CX/D9o8vDj1fJbA0NzuSzYGKvhqAR1NEzY3N1JRzbe2yrTAebHLe+SRhFXRooASi8uX4wKQfHb+WB8TCjWjqjSa33waXvnEpZ8nqmSf4jTUghJJmqAPuB/TIr5MXxD8rSedA5MV0Z5VFt/5ez8bfA87g98qTSh7Tctl0="  
}  

### Registering relatinoship
  
POST /relationships/register HTTP/1.1  
Host: localhost:9090  
Content-Type: application/json  
Cache-Control: no-cache  
  
{  
	"entitySrc":"usr-acccb752-fcba-48a0-80aa-ae9802c321db",  
	"entityTgt":"usr-c46a3676-efae-46fc-83ea-ee37bbdb0a18",  
	"relationTypeS2T":"Doctor",  
	"relationTypeT2S":"Patient"  
}  

### Finding relationship

GET /relationships/find?entityIdSrc=dev-1c276a28-3d82-4729-8467-f51f7ad77259&amp;entityIdTgt=usr-0d3b19ba-302c-4eb6-a3bd-a4b3c633b061&amp;maxDegreeOfRelationship=2 HTTP/1.1  
Host: localhost:9090  
Content-Type: application/json  
Cache-Control: no-cache  
