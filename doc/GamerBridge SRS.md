# Software Requirements Specification
## For GamersGate

Version 0.1  

Prepared by Kyle McDuffie & Elizabeth Spratt
CSC340
09/17/25 



Table of Contents
=================
* [Revision History](#revision-history)
* 1 [Introduction](#1-introduction)
  * 1.1 [Document Purpose](#11-document-purpose)
  * 1.2 [Product Scope](#12-product-scope)
  * 1.3 [Definitions, Acronyms and Abbreviations](#13-definitions-acronyms-and-abbreviations)
  * 1.4 [References](#14-references)
  * 1.5 [Document Overview](#15-document-overview)
* 2 [Product Overview](#2-product-overview)
  * 2.1 [Product Functions](#21-product-functions)
  * 2.2 [Product Constraints](#22-product-constraints)
  * 2.3 [User Characteristics](#23-user-characteristics)
  * 2.4 [Assumptions and Dependencies](#24-assumptions-and-dependencies)
* 3 [Requirements](#3-requirements)
  * 3.1 [Functional Requirements](#31-functional-requirements)
    * 3.1.1 [User Interfaces](#311-user-interfaces)
    * 3.1.2 [Hardware Interfaces](#312-hardware-interfaces)
    * 3.1.3 [Software Interfaces](#313-software-interfaces)
  * 3.2 [Non-Functional Requirements](#32-non-functional-requirements)
    * 3.2.1 [Performance](#321-performance)
    * 3.2.2 [Security](#322-security)
    * 3.2.3 [Reliability](#323-reliability)
    * 3.2.4 [Availability](#324-availability)
    * 3.2.5 [Compliance](#325-compliance)
    * 3.2.6 [Cost](#326-cost)
    * 3.2.7 [Deadline](#327-deadline)

## Revision History
| Name | Date    | Reason For Changes  | Version   |
| ---- | ------- | ------------------- | --------- |
| Kyle | 09/17   | Inital SRS          | 1.0       |
|      |         |                     |           |
|      |         |                     |           |

## 1. Introduction

### 1.1 Document Purpose

The purpose of this Software Requirements Document (SRD) is to describe the client and developer view requirements for the  GamerBridge application.  Client-oriented requirements describe the system from the client’s perspective. These requirements include a description of the different types of users served by the system.  
Developer-oriented requirements describe the system from a software developer’s perspective. These requirements include a detailed description of functional, data, performance, and other important requirements. 

### 1.2 Product Scope
“GamerBridge” is a web-based platform designed to help video game developers showcase their games and receive feedback from customers. The system is a web-based application that creates an environment for producers and consumers of video games to connect. Video game developers are able to connect with a larger population of people and receive more immediate feedback of their products. Over anything, we want to create a comfortable user experience as well as providing a healthy environment.  




### 1.3 Definitions, Acronyms and Abbreviations                                                                                                                                              

| Reference | Defintion |
| --------- | -------------------------------------------------------------------------------------------------------------------------------------------|
| Java       | A programming language originally developed by James Gosling at Sun Microsystems. We will be using this language to build the backend     | service for LocalHarvest Hub                                                                                                                             |
| Postgresql | Open-source relational database management                                                                                                |system.                                                                                                                                                  |
| SpringBoot | An open-source Java-based framework used to create a micro Service. This will be used to create and run our  application.                                                                                                                                             |
| Spring MVC | Model-View-Controller. This is the architectural pattern that will be used to implement our system.                                                                                                                                                  |
| Spring Web | Will be used to build our web application by using Spring MVC. This is one of the dependencies of our system.                                                                                                                                                  |
| API        | Application Programming Interface. This will be used to interface the backend and the fronted of our application.                                                                                                                                             |
| HTML       | Hypertext Markup Language. This is the code that will be used to structure and design the web application and its content.                                                                                                                                                 |
| CSS        | Cascading Style Sheets. Will be used to add styles and appearance to the web app.                                                                                                                                                     |
| JavaScript | An object-oriented computer programming language commonly used to create interactive effects within web browsers.Will be used in conjuction conjuction with HTML and CSS to make the web app                                                                                                                                                      |
| VS Code    | An integrated development environment (IDE) for Java. This is where our system will be         created.                                                                                                                                                 |
|                                                                                                                                                        |                                                                                                                                                                               
                            


### 1.3 Definitions, Acronyms and Abbreviations                                                                                                                                                                          |


### 1.4 References

https://spring.io/guides
### 1.5 Document Overview
Section 1 is a general introduction to the document, intended for any readers. Section 2 is focused on the product and its features. This section is for customers and business stakeholders. Section 3 specifies the requirements and constraints for the product and development process. This section is intended for all stakeholders, especially the development team. 

## 2. Product Overview
GamerBridge is a web-based platform designed to help video game developers showcase their games and receive feedback from customers. Customers can browse different profiles of video game developers, view the status of games, subscribe to their favorite developers, and leave reviews on the games based on their personal experience with the game. Video game developers can manage released games that they display, update game information, and track player engagement.  


### 2.1 Product Functions
GamerBridge allows developers to create and customize video games that they offer.  They are able to display games, update the status of the games, and manage their profiles. Customers are able to subscribe to their preferred developers, rate video games, and manage their profiles. 

### 2.2 Product Constraints
At this point, the program will only run on a computer with Java jdk 21 installed. The full scope of the project is hopefully realized; however, the team has a deadline of about 10 weeks, which could lead to feature cuts. The program would have a challenge scaling, as the current plan is to use a free version of a Postgresql database to store the information.

### 2.3 User Characteristics
Our website does not require that the users have any prior knowledge of the application or computers besides how to operate a web browser. Users interested in rating and following the process of video game development should be able to master the application quickly. 

### 2.4 Assumptions and Dependencies
We will be using Java, with our program being dependent on Spring and SpringBoot, and RestAPI to connect to external APIs and developed with VS Code. The application will also use an external API that will help enrich customers with information on video games. (IGDB).  

## 3. Requirements

### 3.1 Functional Requirements 
-	FR0:  The system shall allow users to create accounts as either a customer or a video game developer
  - Each account will have a unique identifier assigned at the time of creation
-	FR1: The system shall allow developers to display a new video game and provide details including title, description, status, and price
-	FR2: The system shall allow customers to browse through a list of games being displayed
  -	The list of games will have a search and filter 
-	FR3: The system shall allow customers to subscribe to any video game of their choice.
  -	 A customer may unsubscribe from a game if they no longer have interest in the game. 
-	FR4: Users are able to modify their profiles at any time 
-	FR5: The system shall allow customers to rate and review video games based on their personal enjoyment. 
-	FR6: The system shall allow developers to respond to a review 



#### 3.1.1 User interfaces
Web pages using HTML, CSS, and JavaScript.

#### 3.1.2 Hardware interfaces
Devices that have web browser capabilities.

#### 3.1.3 Software interfaces
- Java jdk 21
-	PostgreSQL 17
-	SpringBoot 3.4.5


### 3.2 Non Functional Requirements 

#### 3.2.1 Performance
- NFR0: The novice user will be able to add and manage game subscriptions in less than 5 minutes.
- NFR1: The expert user will be able to add and manage game subscriptions in less than 1 minute.
- NFR2: The GamerBridge system will consume less than 500 MB of memory

#### 3.2.2 Security
- NFR3: The system is going to be available to authorized users, using their username and password. 

#### 3.2.3 Reliability


#### 3.2.4 Availability
- NFR4: GamerBridge will be available 24/7. Scheduled Maintenance should be initialized during scheduled low activity hours such as midnight to minimize conflict with users using the app.

#### 3.2.5 Compliance


#### 3.2.6 Cost
- NFR6: We expect to spend zero dollars on this project.

#### 3.2.7 Deadline
- NFR7: The final product must be delivered by December 2025.
