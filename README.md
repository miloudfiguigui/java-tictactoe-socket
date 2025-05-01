# Tic-Tac-Toe Multiplayer Game  

This is a two-player Tic-Tac-Toe game built in *Java*, using *Sockets* for networking and *Swing* for the graphical interface. Players connect over the network and take turns making moves in real-time.

## Features  

 * Real-time multiplayer with Java sockets.  
 * Simple Swing-based GUI.  
 * Turn-based logic with win/draw detection.  
 * Automatic board synchronization after each move
 * Clear display of the current player's turn and symbol (X or O)

---

## How to Run the Game  

### Requirements  

- *Java 8 or later*  
- *Terminal or Command Prompt*  

### Steps to Play  

#### 1- *Compile all files:*

 
    javac *.java
   

#### 2- *Start the server:*
   
    javac TicTacToeServer.java
    
#### 3- *Start two clients (in separate terminals):*   
     
     java TicTacToeClient
     
#### 4- *Play the game!*  
- Player 1 will use X.

- Player 2 will use O.

- Each client’s interface updates after every valid move.

- The game ends when there’s a win or a draw.

---
## File Structure  
```
.
├── Game.java              // The shared game state object (Serializable)
├── TicTacToeServer.java   // The server managing connections and state
├── PlayerHandler.java     // Thread class for each connected client
├── TicTacToeClient.java   // The client entry point
├── TicTacToeGUI.java      // The Swing-based game interface
└── README.md
```
---
## Known Issues 

- You must run the server before starting the clients.
- Only two players are supported. 
- Both clients must be run on the same local network or configured for external access. 

---

## Author  

Miloud Figuigui
