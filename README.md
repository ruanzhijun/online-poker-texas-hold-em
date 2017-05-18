# Poker Texas Hold'em (Work In Progress)

## TO-DOs

### Client
* Add GUI. Images and all that.
* Set the close of all opened connections in a Shutdown Hook when closed the whole client.

### Server
* Set the port so it's asked by default when starting the server.

## Future Planned Improvements
* Set a timer for a player's action.
* Adjust the timers. Right now there's a 5000 ms delay when ending a round until it starts the next one.

## Network packages order
Here are documented all the actions, which do use the network, and as so need to be synchronized between _Client_ and _Server_.
The sending order is specified __from the point of view of a _Client_ to the _Server___. Most of them share a common structure and then do their specific part. Before doing all the sending, there're checks to see if the game the client is trying to get info about, does exist.

### Mark Structure
* Output / Input. Data type. Comment about what it is.

## Actions

### Create Game
* O. Int. Menu option.
* O. String. Game reference.
* O. String. Player's ID.
* O. Int. Total number of players.
* I. Bool. Result of the action.

#### Returns
* Bool. Status of the operation

### Join Game
* O. Int. Menu option.
* O. String. Game reference.
* O. String. Player's ID.
* I. Bool. Result of the action.

#### Returns
* Bool. Status of the operation

### Information Secondary Thread
* O. Int. Menu option.
* O. String. Game reference.
* I. Bool. Does game exist?.
* O. String. Player's ID.
* I. String. Game's phase (State machine).
* I. Bool. Is this player's turn?.

#### Returns
* ArrayList. [0] Str. Current Phase; [1] Bool. Does player speak now?.
##### Error
* Null. IOException.


### Get private player cards
* O. Int. Menu option.
* O. String. Game reference.
* I. Bool. Does game exist?.
* O. String. Player's ID.
* I. Int. Number of cards to be received. (They will always be 2, but I do re-use code from the way I do get the table cards and didn't feel like to hardcode it).
* I. Card. Private player cards to be added.

#### Returns
* ArrayList. [0] Card #1; [1] Card #2.
##### Error
* Null. IOException.

### Get common table cards
* O. Int. Menu option.
* O. String. Game reference.
* I. Bool. Does game exist?.
* I. Int. Number of cards to be received.
* I. Card. Private player cards to be added.

#### Returns
* ArrayList. [n] Card #n.
##### Error
* Null. IOException.

### Bet
* O. Int. Menu option.
* O. String. Game reference.
* O. String. Player's ID.
* I. Bool. Does game exist?.
* I. Bool. Is this player still in game? (Did not retire).
* I. Bool. May the player bet now?.
* O. Int. Number of chips to bet.
* I. Int. Number of chips the winner gets. (Common pool).

#### Returns
* Int. Number of chips at the moment in common pool.
##### Error
* -1. IOException.
* -2. This game does not exist. Should never be reachable, but better check for it anyway.
* -3. Player may not bet right now. It's not his turn.
* -4. Player did already retire and cannot bet until new round.
* -5. All the players but this did retire. The game is setting itself to the last phase and assigning a winner. Cannot bet now.
* -6. Negative bet amount. Bets may only be positive.
### Check Winner
* O. Int. Menu option.
* O. String. Game reference.
* O. String. Game ID.
* I. Bool. Does game exist?.
* I. Bool. Does the game already have a winner?.
* I. String. Winner's ID.
* I. String. Play the winner has won with (Name of it to display).
* I. Int. Number of chips won. (Only added to the winner).
* O. Bool. Can this player still play? (Has chips left).
* I. Bool. If not. Was this player retired correctly?.

#### Returns
* ArrayList. [0] Str. ID of the Winner; [1] Str. Name of the winning play; [2] Int. Ammount of chips won by the winner.
##### Error
* Null. IOException or game does not exist.


## Versions

* #### 0.2.2 Added checks and a way so the game gets stopped and deleted automatically when there's only 1 player left in game.
* #### 0.2.1 Phases fully implemented, now the game flows smooth from the first, to the last one.  
* #### 0.2   Created and implemented State Machine Pattern. Structure of the several Phases in poker. Changes behaviour of actions.
* #### 0.1.1 Designing the basic structure of the network. Designing communications and package order. 
* #### 0.1   Added the code representation of a cards and a deck. Also methods to manipulate them.
* #### 0.0.1 Initial status. Made a repostitory for the project. Looking which code of old projects can I re-use.



