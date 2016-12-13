# CSE 110 Java Honors Project
This is my honors project for first-semester CSE 110 Principles of Programming with Java.

It's a console-based Java program that prompts users with their resource usage (e.g. how long they showered) so that it can then be compared to average usage and their historical usage.

## Why Resource Conservation idea
I'm learning computer science so that I can leverage technology for the social good. So, I wanted this first project to reflect that. Creating a game wasn't good enough.

I care a lot about sustainability and created this app as a thought experiment for how technology can be used to encourage resource conservation.

## Object-oriented approach
This CSE 110 class is the first time I was exposed to *object oriented programming*.

I entirely redesigned my project around OOP after learning this concept, and 
in particular refactored the program so that it can be used for *any 
resource conservation*. The user just has to instantiate a new usage like 
"car", and the app will adapt the program from there.

## Initial Issues
Since I did this project my very first semester of programming (and before 
reading *Code Complete*), it had a lot of issues.

I've tried to fix most through significant refactoring.

#### Very poor original abstraction and class contracts
I did this project before reading *Code Complete*, so the original project 
had very poor abstraction and encapsulation. Every class knew about each 
other, every class outputted to the console instead of just one controller, 
and side effects abounded.

#### No GUI
This was before I had learned how to build GUIs, so the program just uses 
console dialogs. 

## Refactoring

#### Abstraction, One Responsibility, DRY, Encapsulation
Significantly reworked class design and methods to follow best practices; 
limiting access to unnecessary information and classes, ensuring each method 
and class only does one thing; controlling the level of abstraction; using 
helper functions and classes to fight duplication.

#### More readable code
Significantly improved names, layout, and comments for readability. 

#### Add unit tests
This sparked fixing logical errors and other refactorings, and enabled such 
drastic refactoring without fear.

#### Leverage Java APIs
Added support for Java 7/8, e.g. Lambdas/Stream, Path API, and Collections API.

#### Implement Model View Controller design
Extracted all console interface into new module, and decoupled object 
information from presentation.

Allows for easier extension from console app to JavaFx app.

## What I learned
Above all, this project taught me how to take a programming project from initial conception to release.
* Object oriented programming
* Class relations and inheritance
* UML diagrams
* IO file reading and writing
* Clean code (refactor)
* Unit testing (refactor)
* Error handling (refactor)
* Model View Controller (refactor)

