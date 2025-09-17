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

Describe the purpose of the SRS and its intended audience.

### 1.2 Product Scope
Identify the product whose software requirements are specified in this document, including the revision or release number. Explain what the product that is covered by this SRS will do, particularly if this SRS describes only part of the system or a single subsystem. 
Provide a short description of the software being specified and its purpose, including relevant benefits, objectives, and goals. Relate the software to corporate goals or business strategies. If a separate vision and scope document is available, refer to it rather than duplicating its contents here.


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

List any other documents or Web addresses to which this SRS refers. These may include user interface style guides, contracts, standards, system requirements specifications, use case documents, or a vision and scope document. Provide enough information so that the reader could access a copy of each reference, including title, author, version number, date, and source or location.

### 1.5 Document Overview
Describe what the rest of the document contains and how it is organized.

## 2. Product Overview
This section should describe the general factors that affect the product and its requirements. This section does not state specific requirements. Instead, it provides a background for those requirements, which are defined in detail in Section 3, and makes them easier to understand.

### 2.1 Product Functions
Summarize the major functions the product must perform or must let the user perform. Details will be provided in Section 3, so only a high level summary (such as a bullet list) is needed here. Organize the functions to make them understandable to any reader of the SRS. A picture of the major groups of related requirements and how they relate, such as a top level data flow diagram or object class diagram, is often effective.
main

### 2.2 Product Constraints
This subsection should provide a general description of any other items that will limit the developer’s options. These may include:  

* Interfaces to users, other applications or hardware.  
* Quality of service constraints.  
* Standards compliance.  
* Constraints around design or implementation.
  
### 2.3 User Characteristics
Identify the various user classes that you anticipate will use this product. User classes may be differentiated based on frequency of use, subset of product functions used, technical expertise, security or privilege levels, educational level, or experience. Describe the pertinent characteristics of each user class. Certain requirements may pertain only to certain user classes. Distinguish the most important user classes for this product from those who are less important to satisfy.

### 2.4 Assumptions and Dependencies
List any assumed factors (as opposed to known facts) that could affect the requirements stated in the SRS. These could include third-party or commercial components that you plan to use, issues around the development or operating environment, or constraints. The project could be affected if these assumptions are incorrect, are not shared, or change. Also identify any dependencies the project has on external factors, such as software components that you intend to reuse from another project, unless they are already documented elsewhere (for example, in the vision and scope document or the project plan).

## 3. Requirements

### 3.1 Functional Requirements 
- FR2: The system shall allow customers to browse through a list of games being displayed
   - 	The list of games will have a search and filter 
-	FR3: The system shall allow customers to subscribe to any video game of their choice.
  -  A customer may unsubscribe from a game if they no longer have interest in the game. 
- FR5: The system shall allow customers to rate and review video games based on their personal enjoyment. 
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
