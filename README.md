# Amazons AI - COSC 322
##### Created by
- **Rick Feng** - @Rick-Feng-u
- **Kathryn Lecha** - @kzlecha
- **Elias Pinno** - @EliasPinno
- **Sean Roarty** - @sroarty
- **Kanishka Verma** - @kanishkaverma

# Description
This project is the implementation of a rational agent that can play the Game of the Amazons. It was written in Java using the provided Game Client API. This rational agent is capable of making legal moves in the game controlled by a game-tree search and heuristic. Our agent came in 6th place during the in-class AI tournament. In the qualifiers, it won against every team in Lab 02. **This bot sends moves to a custom game server and will not work out of the box.**
![An overview of the Game Client interface](https://i.imgur.com/y3t9iUg.png)

# Game Tree Search
### Minimax
We used the minimax algorithm for our game-tree search method. This algorithm assumes that the opponent player will always make a move that maximizes their own probability of winning, measured by a heuristic, minimizing the player’s probability of winning. This game tree search then finds a move that maximizes the player’s success given the opponent makes the best move they can. This is a straightforward way to parse a game tree and uses O(bd) computation at depth d.
Minimax was a simple yet effective algorithm for our game tree search. Early explorations showed Monte-Carlo was very computationally intensive, despite performing better. While our minimax algorithm was not able to beat a Monte-Carlo tree search, it constantly won against other minimax algorithms.
### Alpha Beta Pruning
We used alpha-beta pruning to improve the performance of the tree search and search deeper in the tree. By removing nodes that would not influence the final decision in a minimax tree, the expected complexity becomes (bdlogd). By removing unused nodes, pruning decreased computational complexity. This allowed us to search further down the tree. However, pruning only started at a depth of 3, which was not encountered until halfway through a game.
### Iterative Deepening
Iterative deepening proved to be very effective in searching the game tree considering hardware restrictions. Since Game of the Amazons has a decreasing number of possible moves as the game progresses, the late game can search much deeper in the tree than the early game. Iterative deepening allowed the search to increase depth dynamically without going over the 30 second limit to make a move. Despite this, iterative deepening still often timed out at depth 2 during the first half of the game. It wasn’t until the late game that iterative deepening travelled further down the tree.
### Heuristic
The heuristic we used was a number of moves heuristic. It calculates the number of moves any queen can make. Initially this heuristic considered the number of different arrow possibilities. This heuristic was unstable and also computationally expensive. The final heuristic only considers the number of moves the four queens can make at any given game state, ignoring distinct arrow placements.. This was less computationally expensive and found to be more stable (vastly improved the depth we could look into at all stages of the game). However, the second heuristic played less aggressively and lost to the heuristic that considered arrow moves in testing. By not considering arrow moves, the Bot was less likely to accurately assess the heuristic value of certain board states, and made it less likely to move into places which had more future moves.

We also attach a depth discount rate to these terminal values: a winning board state that is 1 move away is more valuable than a winning state it sees 5 or 6 moves away, and a loss 1 move away is more negative then a loss more moves away, so the bot will pick terminal states accurately based on how far away they are in the future.
