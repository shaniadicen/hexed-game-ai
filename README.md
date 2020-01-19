# Artificial Intelligence: Hexed! Game Search
A java program that plays a custom standard two-player game called Hexed!

## Requirements
This is an AI program that will be able to play the standard game Hexed!. A standard game is basically an open information, deterministic, two-player, turn-taking and zero-sum game. The game uses a virtual board game where the board consists of multiple combined hexagon with a set of 9 columns and 7 and 6 rows.

Game Download: [Hexed!](http://bit.ly/38muW4X)

To run the game's UI, please download [eazfuscator](https://www.gapotchenko.com/eazfuscator.net) or the game won't execute.

## System Design
This is an implementation of Adversarial Search in Artificial Intelligence.
This implements the minimax search algorithm in game theory to minimize the possible loss for a worst case scenario. It uses a heuristic to find the best possible move. You can change the heuristic of the AI in `computeUtility()` method in the main class.

### Classes
The program is composed of the following classes:
1. HexCell
    - represents a single hex cell which has the attributes row, col, valid cell and color.
    - The row and col is the index of the cell on the board.
    - Valid cell is whether the cell has an index that is valid and should exist on the board.
    - Color is the color of the hex cell, ‘g’ for green, ‘r’ for red and ‘\u0000’ for no color.

2. HexBoard
    - represents a board in the game which has the attribute hex, a 2-dimensional array of HexCell with 9 columns and 7 rows.
    - This class contains the methods for generating and initializing the board and generating possible moves.

3. Index
    - represents a move in the board. It has the attributes row, col and direction.
    - The row and col is for the index of the move and direction is the flanking direction of the move (N,S,NE,NW,SE, and SW).

4. State
    - represents the state of the board.
    - It has the attributes utility, parent, iden, board, depth and move.
    - The utility is for the identification of the utility function of the board.
    - Parent is the container for a node’s parent. The iden is the determinant of whose turn.
    - The board is the copy of the current board. The depth pertains to the ply number and move is the index of the move.

5. Hexed
    - is the main class. Its global variables are hex, firstMove, teamColor, turn, redScore, greenScore and state.
    - The hex is the actual board and the representation of the actual state of the board.
    - The firstMove, teamColor and turn are of type char that determines the color of the first move, color of the team and color of whose turn respectively.
    - The redScore and greenScore are int type and holds the score for red team and green team respectively. The state is a container used for storing the state of the game. This class has the methods start, createBoard, generateScores, playGame, getChosenMove, isHexed, bothHexed, makeMove, generateChildren and computeUtility.
    - The start method receives input from the user such as the initial state of the game and team’s color.
    - The `createBoard` copies the current state of the board passed as a parameter.
    - The `generateScores` sets the correct scores for the two teams at each state of the board.
    - The method `playGame` either makes the best move of the team & updates the state of the board or gets the indexes of the opponent’s move and updates the state of the board.
    - This method is dependent on the global variable turn.
    - `getChosenMove` returns an ArrayList of Index of moves with the same index from the list of possible moves.
    - `isHexed` and `bothHexed` returns true if one of the players has no move or both players have no move (reached a game ending node) respectively.
    - The `makeMove`, `generateChildren` and `computeUtility` are used for the generation of the game tree.

### Move Generation
The possible moves in the board are generated by getting the indexes of the turn’s opponent. The opponent cell is checked on whether it is in between a turn cell which is done by checking each direction around the opponent cell on whether a turn cell exists after the opponent cell, and that the cells leading to the turn cell are all opponent cells and there is no ‘no color’ cell. If the opponent cell is in between a turn cell, then the possible move index would be generated. The move index is generated accordingly with the direction where the opponent index is in between a turn cell. The move index is only possible if the generated index of the move is a ‘no color’ cell. The methods `checkNorth`, `checkSouth`, `checkNorthWest`, etc. are responsible for checking if the opponent cell is in between a turn cell and the method generateMoves is for generating the list of possible moves.

### Cell Flanking
Flanking is the capturing of your opponent's pieces. The flanking of the cells are done by determining the cells in between the move index. The getInBetweenCells method is the method for determining the list of in-between cells from a move. The moves parameter is made to be an array list so that it can handle the situations where more than one direction would be flanked from the move. The method `fixSandwichedColor` is the method which would change the color of the in-between cells.

### Game Tree
For the construction of the game tree, this follows the minimax search strategy. The program is designed such that the game tree will be generated whenever the program has to make its move. Initially, the depth being dived is restricted to 6 plies and will increase by 2 every time it makes its move. If the node being visited at the frontier is a game-ending node or its depth is equal to maximum depth, then the evaluation function will be computed and will be assigned to the utility attribute. The computeUtility method computes the evaluation function of the board which would determine the utility of the terminal state. The evaluation function used is the number of cells that would be flanked if the move of the turn in the board is made.

## Support
For any questions or concerns, please email [shaniaddev@gmail.com](mailto:shaniaddev@gmail.com?subject=[GitHub]%20Hexed%20Game%20AI)
