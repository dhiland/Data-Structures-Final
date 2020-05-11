# Data-Structures-Final
Chat Noir game. Final project for Data Structures and Algorithms class at Bethel University?

/** 4-29-2020 Dominic Hiland
    Added 3 view files and Block class for logic
/**


/** 5-4-2020 Dominic Hiland
    - added game monitoring to ChatNoirModel
        - Game displays in console now
    - added basic game logic to model
        - game functions using 2D ArrayList of Block objects
        - Block objects can be used to trace locations of cat and walls using the 
        Block class's built in functionality
        - created functional toString which prints out the current game state
    - changes to GridView class
        - the architecture of GridView class was overhauled to match the
        architecture of the model
        - GridView renamed to ChatNoirView
        - added contingency on game state for View generation. Walls now display as
        GRAY and cat now displays as BLACK
        - View now functions using 2D ArrayList of Rectangles, similarly to ChatNoirModel
    - Changes to Main
        - added try-catch surrounded testing section for present and future use
    - View and Model now both respond to clicks on the View
        - Tested: Cannot place walls on walls and cannot place walls on cat
/**

/** 5-4-2020 Josh Larson

To Do:
- Create method to randomly block squares (Josh)    Complete
- Create alert method (Josh)    Complete
- Create cat movement method (Josh)     Complete
- Game over method (game stops when it should) 
- Add edge variable to block class (Dominic)    Complete
- Add state switcher (so we know who's turn it is) (Dominic)    Complete
- Figure out a loop to replace switch cases (Start with variable for number of blocks equal to 10,
decrement for loop all the way down to 1 to take car of the bottom half of the game board) (Dominic)    Complete





- Djikstra's algorithm (Hopefully Gossett)    Complete
