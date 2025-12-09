# GamerGate- Software Design 


Table of Contents
=================
* [Revision History](#revision-history)
* 1 [Product Overview](#1-product-overview)
* 2 [Use Cases](#2-use-cases)
  * 2.1 [Use Case Model](#21-use-case-model)
  * 2.2 [Use Case Descriptions](#22-use-case-descriptions)
    * 2.2.1 [Actor: Developer](#221-actor-farmer)
    * 2.2.2 [Actor: Customer](#222-actor-customer) 
* 3 [UML Class Diagram](#3-uml-class-diagram)
* 4 [Database Schema](#4-database-schema)

## Revision History
| Name | Date    | Reason For Changes  | Version   |
| ---- | ------- | ------------------- | --------- |
|  Kyle |10/20     | Initial Design      |    1      |
|      |         |                     |           |
|      |         |                     |           |

## 1. Product Overview
GamerGate is a simple, comprehensive, easy to use web app with the goal of connecting consumers to Video Games. Farmers and customers make use of the centralized platform to meet their needs. 
Developers create and publish ga,e4s, customers subscribe any available games that they are interested in.

## 2. Use Cases
### 2.1 Use Case Model
![Use Case Model](https://github.com/csc340-uncg/f25-team0/blob/main/doc/Object-Oriented-Design/use-case.png)

### 2.2 Use Case Descriptions

#### 2.2.1 Actor: Developer
##### 2.2.1.1 Sign Up
A developer can sign up to create their profile with their name, email, password, and phone number. Emails must be unique.
##### 2.2.1.2 Log In
A developer shall be able to sign in using their registred email and password. After logging in, the developer shall be directed their dashboard where they see an overview of their games and stats.
##### 2.2.1.3 Update Profile
A developer shall be to modify their profile by going to their profile page. They can change their email, password, and games.
##### 2.2.1.4 Create Gamepages
The developer shall be able to create a new game listing. They would provide a game name, description, and price. 
##### 2.2.1.4 View Customer Stats
A developer will be able to view several statistics such as total revenue, total subscribers, and average ratings.

#### 2.2.2 Actor: Customer
##### 2.2.2.1 Sign Up
A customer can sign up to create their profile with their name, email, password, and address. Emails must be unique.
##### 2.2.2.2 Log In
A customer shall be able to sign in using their registred email and password. After logging in, the customer shall be directed their dashboard where they see an overview of their subscriptions.
##### 2.2.2.3 Browse Games
A customer shall be able to view available games. They can do this from the home page or using a search function. They can also filter boxes by name, descriptions, or generes. They will also be able to select one game and view more details.
##### 2.2.1.4 Subscribe to Games
Upon selecting a game, a customer shall be able to subscribe for the box using a one-click action. This box will then appear on their dashboard, and they will be able to ammend the subscription.
##### 2.2.1.5 Review Games
A customer may write a review for a game they subscribed to. They will be able to rate the box based on graphics and gameplay.

## 3. UML Class Diagram
![UML Class Diagram](https://github.com/B1gB0ss21/CSC340-Team4/blob/main/doc/Object%20Oriented%20Design/class-diagram.png)
## 4. Database Schema
![UML Class Diagram](https://github.com/B1gB0ss21/CSC340-Team4/blob/main/doc/Object%20Oriented%20Design/schema.png)

