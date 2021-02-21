# Basic Chess

**Motivation**

I developed this version of chess more than a year after the first one (that can be found in my Simplified-Chess repo). I did it to practice my OOP and Data Structures and Algorithms skills and also to compare my knowledge to what I knew when creating the first version.

**Technologies used**

I used plain Java as it provided all the functionality I needed at this stage.

**Features**

This is an (almost) complete version of the original chess game- it has all of the pieces and they move according to the rules (https://en.wikipedia.org/wiki/Chess#Setup for reference). The visual representation of the chess board is in the console, empty squares are dashes (-), squares which have pieces are denoted with the first letter of the piece (exception made for the knight- his letter is "H", as the king occupies the letter "K" already). Moves are made by inputting a string in the format LetterNumber LetterNumber, which represents two squares on the chess board. The game keeps prompting for valid input, until a legal move is made by the player whose turn it is. The game can currently detect win/loss by checkmate and draw by stalemate. Most subtle logic like blocking a check with a piece, or checking for stalemate after every turn is implemented.

**How to use**

You can inspect the logic of the project in the classes in the src folder. If you want to test the game, you can try downloading the Chess.zip file, extract it somewhere and open it in IntelliJ IDEA (I'm using JDK 15). Better visual representation and more robust way to execute the project coming soon! 

**TODO list**

- Add logic for all of the special moves and rules not implemented yet (en passant, pawn promotion, castling, draw by three-fold repetition, draw by insufficient material, 50 move rule, draw by mutual agreement).
- Create a nicer visual representation in JavaFX.
- Implement a basic bot (using a simple approach like backtracking).

**Credit** 

General idea how to design the classes and their relationships: https://www.geeksforgeeks.org/design-a-chess-game/.

Comprehensive information about all of the rules of chess: https://en.wikipedia.org/wiki/Chess

A class with font colors to make the console visualisation look way nicer: https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
