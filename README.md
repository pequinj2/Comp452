# Comp452
 
*Bug Wars* game code used to complete COMP 452 assignments from Athabasca Universtiy. All of the art was done by myself but none of the audio in the game is mine, as well as the title and design of *Bug Wars* is referenced to *Beast Wars: Transformers*. Please refer to the word
docs for each assignment for detailed breakdown of the whole development process. When reviewing, please note that the main point of this class was to learn AI
development for video games, therefore, that was my main focus. All other design features are extras, although not perfect, were for my own intersts and 
education.


# How to Compile:

To start the game, double-click on the jar file called “desktop-1.0.jar”. When the main menu screen opens mouse click to start the game intro, when the intro is done and spacebar is pressed, the game will begin.
To build a new “desktop-1.0.jar”, go into COMP452 folder in the terminal (I do this in Android Studio) and type the command:
	./gradlew desktop:dist
The executable will be in "Comp452\desktop\builds\libs"

# Assignment Overviews:

The following is just brief assignment overviews of what was basically required in each game that was developed.

## Assignment 1:

Objective was to impliment a few steering behaviors with start and end points of a game. Becuase this was the first assignment alot of work was done learning
the LibGDX framework (its similar to the Monogame framework), building assets, game planning and implimentation.

## Assignment 2:

There were two objectives: impliment the A* algorithm and a finite state machine (FSM). 

The A* game required us to have an "Ant" that would traverse the game field in search of a piece of food, but get to it as quickly as possible. 
It would be up to the user what the game field looked like, for instance, they would be able to add swamp terrain - which would cost more for the Ant to walk
through and/or rocks that would completely block the Ant's path to the piece of food. 

The FSM game required multiple Ants that would again traverse the game field in search of food, but when found, would then switch to look for water and finally head home. 

## Assignment 3:

There were two objectives: impliment Connect 4 and impliment a learning technique.

The Connect 4 algorithm used made with NegaMax with ab pruning (with a depth of 4).
The machine learning I did not jive with, so instead I implimented utility theory, specifically a weighted FSM to complete the end boss's move sets.

# Professor Feedback:

My instructor for the class was Dr. Larbie from Athabasca University, the comments below are his feedback from marking my assignments.

## Assignment 1:

Hi Jody,

Your mark for the first assignment is 100%.

Your programs implement both the basic steering behaviors of align(), arrive(), and seek(). The coordinated behavior is also implemented using a Centipede’s Burst Shot. The sprites used for the characters are simple but very good. The documentation of the program is also good and explain the logic of the game, how to play it as well as how to compile and run it. The programs structure in terms of classes and objects is well described in the assignment report, and comments inserted in the source code make it easy to follow the code and understand the logic.   

The few bugs encountered at the programs looks to have been well identified and documented. The interface for the game is simple and works very well, but the Centipede’s speed should have been adjusted to make it a bit slower for the basic level, then create an intermediate and expert levels with increased speed.  

In general, the game is good and implements the required AI behaviors.

Thanks,

Larbi.

## Assignment 2:

Hi Jody,

Your mark for the second assignment is 100%.

The AI concepts are well put in the programs. Both games work very well and most of the AI aspects are implemented. The design and graphics of the game are simple but work well. The documentation of the games is good and explains the code implementation. The comments inserted in the Java code as well as the Finite state machine for the second game helped understand the programs. The report would be much better if it included the structure of the programs. The interaction with the user is very good and gives the user the possibility to change the parameters or define the landscape using the mouse.

In general, the work shows that you understand well the AI concepts and you are able to use the learned techniques for implementing games.

Thanks,

Larbi.

## Assignment 3:

Hi Jody,

Your mark for the Third assignment is 50%.

The first game for Connect4 is well designed with a good try to adda story to the game. The game implements the NegaMax algorithm, but unfortunately it is not working properly. In fact, the human player can easily connect four tokens without any action from the computer trying to stop it, while the NegaMax is supposed to evaluate such situations four levels ahead of the human player move. It looks to me as if the evaluateBoard function is not working properly for selecting the computer move. The program code looks fine even though the logic is not working as it should be. The report is well done and explains both the game design as well as how to run and play the game. The game classes are described and the comments in the code helped me follow the logic.

The second game is fun and well done, but unfortunately it doesn’t implement the required AI learning part. Learning algorithms usually focus on learning a pattern that is logically foreseeable. So, to use the AI learning technique, you should for example have added a repeating attack action that one of the characters can use and have the second character learn such pattern to avoid the attack.

The design and graphics of both games are good and provide a descent interface, but unfortunately none of the AI concepts worked well.

Thanks,

Larbi.

# Thanks!

Thanks for taking the time to read all of this, and if there are any questions please feel free to reach out :)

