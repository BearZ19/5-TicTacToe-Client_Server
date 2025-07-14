This repository contains a modular implementation of a 5-in-a-row Tic Tac Toe game, built with separation of concerns between the UI, logic, and algorithmic analysis layers.

📁 Project Structure

🔹 1. TicTacToe5-UI (JavaFX Client)
This module implements the graphical user interface using JavaFX.
Users can:

Register, log in, and delete their account

Start a new game or join an existing one

Click on the 10×10 grid to make a move

Receive real-time updates from the server

Components: ClientConnector, ClientGame, ClientRequestSender, GameState, Request, Response

🔹 2. TicTacToe5-Logic (Java Server)
This module handles game and player logic, as well as server-side communication.

It includes:

A multithreaded server with support for multiple players

Controller classes to route requests (e.g., PlayerController, GameController)

PlayerService and GameService for business logic (registering users, managing moves)

Components: Server, HandleRequest, ControllerFactory, GameService, PlayerService, IDao, GameState, Move, Player, Config, GameController, IController, PlayerController, MyDMFilelmpl, Request, Response, ServerDriver



🔹 3. BFS/DFS Algorithm Module (Winner Checker)
A standalone JAR project that includes BFS and DFS algorithms used to:

Scan the board after each move

Check whether a player has connected 5 in a row (horizontally, vertically, or diagonally)

This algorithm is called internally from the GameService class after each valid move.


/////////////////////////////////////////////////////////////////////////////////////////////////////


*The system uses file-based persistence for saving players and game data using the IDao interface and MyDMFileImpl, which store information in .txt files.
Communication between the UI (client) and logic (server) is done using JSON messages via Request and Response classes.

<img width="1919" height="811" alt="Screenshot 2025-07-14 133211" src="https://github.com/user-attachments/assets/92bde407-7264-40ed-a4ef-0a6755e422ec" />
<img width="1864" height="955" alt="Screenshot 2025-07-14 133301" src="https://github.com/user-attachments/assets/1baab69f-7a85-4845-9c5d-2d4a4537bef2" />
