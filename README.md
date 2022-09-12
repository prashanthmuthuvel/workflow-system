# Automating an Academic Document workflow system with Applied AI

### About the project

I have built an online web application system where we can create different kinds of workflow which would act as a blueprint for the ticket.

Brief outline of how this approach tackles the problems mentioned above in the current system.
With this online web application, a ticket process could be completed within an hour.
Even if there is an error made by misspelling the course name or student name, the ticket could be easily sent back to the user who is responsible for that and could move forward from there easily.
At any point of time, the user can know the status of the ticket.


### Architecture Diagram

![architecture](https://user-images.githubusercontent.com/113312080/189729308-331bc23e-0f72-46b1-8e47-2ba7173d7499.png)


### Technologies Used

* ReactJs - Graphical User Interface - Frontend
* Spring Boot - JAVA - Backend
* Mysql - Database Management
* Python - Signature Recognition - Artificial Intelligence
* Elasticsearch - Logging Purpose
* Logstash - Logging Purpose
* Kibana - Logging Purpose


### Entity Relationship Diagram

![Entity_Relationship_Diagram](https://user-images.githubusercontent.com/113312080/189730509-d63645d6-3c45-48cc-9e1f-fa7d76c6eebd.png)


### Workflow Creation

I came up with a json schema, to create a generic workflow which comprises of moving the ticket to different users who can fill different forms in the ticketing system
The json schema can be divided into two parts
  * Form
  * Stage

![stagesAndForms](https://user-images.githubusercontent.com/113312080/189730796-b504c8c1-03be-4131-9792-d14563a1a76b.png)

### Form Structure With Example

![form_structure](https://user-images.githubusercontent.com/113312080/189731904-715f73c8-2035-45c6-8f3d-50a4f27d4bd1.PNG)

### Stage Strucute With Example

According to the stage structure, the flow of the ticket will change.

![stageStructure](https://user-images.githubusercontent.com/113312080/189735572-5160ef4c-074e-41cc-a6ce-2c32e22a6332.PNG)

![flowDiagram](https://user-images.githubusercontent.com/113312080/189735763-a61c1a51-ed1e-4915-b797-642033b378d4.PNG)


### Types of Ticket

There are two types of ticket
  * Normal Ticket
    Once the ticket is created, it will move to stage1 and need manual inputs to move to next stage.
  * Hybrid Ticket
     If a pdf form is already filled, we can upload the pdf file so that the details which are already filled would be extracted from it and we can even move jump to next stage if the signature is found in the pdf file.
