# CSE 110 Java Honors Project
This is my honors project for first-semester CSE 110 Principles of Programming with Java.

It's a console-based Java program that prompts users with their resource usage (e.g. how long they showered) so that it can then be compared to average usage and their historical usage.

## Why Resource Conservation idea
I'm learning computer science so that I can leverage technology for the social good. So, I wanted this first project to reflect that. Creating a game wasn't good enough.

I care a lot about sustainability and created this app as a thought experiment for how technology can be used to encourage resource conservation.

## Object-oriented approach
This CSE 110 class is the first time I was exposed to *object oriented programming*.

I entirely redesigned my project around OOP after learning this concept, and tried to create the program so that it can be used for *any resource conservation*. Right now it's for water conservation, but can just as easily be used for looking at miles driven by someone.

## Issues
Please note this was a project for my first semester ever of programming, so I know a lot of this has issues! This was before I read *Code Complete* and before programming really started to click.

#### Poor abstraction and class contracts
I did this project before reading *Code Complete*, so didn't know about ideas like *class contracts*, *encapsulation*, and *abstraction*.

Consequently, even after refactoring, the project's design could still be improved.

#### No GUI
This was before I had learned how to build GUIs, so the program just uses console dialogs.

## Refactoring
I've refactored this project in two main stages:
1. Cleanup of contracts, code style, abstraction, etc (Sept 2016). Particularly focused on DRY, One Responsibility principle, and abstraction/encapsulation.
2. Addition of Unit Tests + Java 7/8 (Nov 2016). Unit tests led to fixing remaining logic errors and improved ease of general refactoring. Added Path API and Lambda/Stream to better leverage API. 

## What I learned
Above all, this project taught me how to take a programming project from initial conception to release.
* Object oriented programming
* Class relations and inheritance
* UML diagrams
* IO file reading and writing
* Unit testing (refactor)
