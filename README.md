# Poker Texas Hold'em (Work In Progress)
Little personal project because I love playing card games with my family and found it a good way to practice java, networking, multithreading and Client-Server structure.

## TO-DOs
### Client
* Add GUI. Images and all that.
* Integrate the PHP files to manage user login with DBB.
* Capture all the exceptions I manage to get and do it as dumb-proof as I can and more.
* Assign a random client # when user joins a game without log-in.

### Server
* Set the port so it's asked by default when starting the server.

### General
* Adjust the timers. Right now there's a 5000 ms delay when ending a round until it starts the next one so the secondary thread has time to get the info several times (just in case).

## Future Planned Improvements
* Make it so a player can only bet the same as the previous player did or more.
* Set a timer for a player's action so it cannot take him longer than i seconds. If he does, retire or bet 0.
* The creator of the game may start it without all the players have joined. Only the creator.
* Add the posibility to set passwords to games.
* Port it to Android(?). Should not be really difficult as almost everything's been done in Java.

## Known Bugs
* (Internal) Web - Script of php executed from Java client. It does return the same number error for user not existing and password erroneous. It should not. The user should not be notified of what's wrong but for debugging purposes I should.

# Documentation
This project differentiates between _Client_ and _Server_ and inside _Client_ it follows _MVC_ architecture. The heavy logic processing of the game is done by the _Server_, the _Client_ just has a serie of checks to know which info it has to currently retrieve.  

About this, the _Client_ launches an information secondary thread when the game is started. This thread takes care of doing a ping to the server every _i_ seconds and gets info such as the current phase or the player's turn. It is also in charge of getting the winner's info and to communicate when to definitely retire a player from the game.

## State Machine
### Information
The _Server_ does implement a state machine which changes the behaviour of the game, depending on which phase it is currently at. The _trigger_ to change between phases are _Bet_ and _Retire_ actions from a client. When the last player has betted, it changes the game to the next phase and calls the method to restart the checks for every player still in this round. When a player retires, it checks if now, after the retirement, there's only one player in this round. If there is, it jumps from phase to phase until _River_ where it assigns the last player standing as winner, gives him the winnings and starts a new round.

Every player may retire from a round whenever he wants; Being only out for the current round and getting back at the start of the next one, as long as he has chips left. The game will not take into account retired players for the current round and jumps them.  

When a player has no longer chips, he has lost, and as so he's fully retired from the game.

The possible phases are:  
* Preflop - Each player gets their 2 private cards.
* Flop - Draws 3 common cards. Sends them to the players.
* Turn - Draws 1 more common card. 4 until now. Sends them to the players.
* River - Draws the last common card. All 5. Sends them to the players.


## Network
### Information
It's fully sinchronized between _Client_ and _Server_ following the order here stated. The _Server_ plays a passive role addressing the incoming connections. The _Client_ it's the one who opens them and closes them as needed; It also has a Network Shutdown Thread which will be executed on exit to close all the possible remaining loose connections.

### Package order
Here are documented all the actions, which do use the network, and as so need to be synchronized between _Client_ and _Server_.
The sending order is specified __from the point of view of a _Client_ to the _Server___. Most of them share a common structure and then do their specific part. Before doing all the sending, there're checks to see if the game the client is trying to get info about, does exist.

#### Mark Structure
* Output / Input. Data type. Definition.

#### Create Game
* O. Int. Menu option.
* O. String. Game reference.
* O. String. Player's ID.
* O. Int. Total number of players.
* I. Bool. Result of the action.

##### Returns
* Bool. Status of the operation

#### Join Game
* O. Int. Menu option.
* O. String. Game reference.
* O. String. Player's ID.
* I. Int. Result of the action.

##### Returns
* Int. Status of the operation

##### Result Options
* 1. Game joined correctly.
* -1. Game with this reference does not exist.
* -2. Game with this reference is already started and cannot be joined.

#### Information Secondary Thread
* O. Int. Menu option.
* O. String. Game reference.
* I. Bool. Does game exist?.
* O. String. Player's ID.
* I. String. Game's phase (State machine).
* I. Bool. Is this player's turn?.

##### Returns
* ArrayList. [0] Str. Current Phase; [1] Bool. Does player speak now?.
###### Error
* Null. IOException.


#### Get private player cards
* O. Int. Menu option.
* O. String. Game reference.
* I. Bool. Does game exist?.
* O. String. Player's ID.
* I. Int. Number of cards to be received. (They will always be 2, but I do re-use code from the way I do get the table cards and didn't feel like to hardcode it).
* I. Card. Private player cards to be added.

##### Returns
* ArrayList. [0] Card #1; [1] Card #2.
###### Error
* Null. IOException.

#### Get common table cards
* O. Int. Menu option.
* O. String. Game reference.
* I. Bool. Does game exist?.
* I. Int. Number of cards to be received.
* I. Card. Private player cards to be added.

##### Returns
* ArrayList. [n] Card #n.
###### Error
* Null. IOException.

#### Bet
* O. Int. Menu option.
* O. String. Game reference.
* O. String. Player's ID.
* I. Bool. Does game exist?.
* I. Bool. Is this player still in game? (Did not retire).
* I. Bool. May the player bet now?.
* O. Int. Number of chips to bet.
* I. Int. Number of chips the winner gets. (Common pool).

##### Returns
* Int. Number of chips at the moment in common pool.

###### Result Options
* -1. IOException.
* -2. This game does not exist. Should never be reachable, but better check for it anyway.
* -3. Player may not bet right now. It's not his turn.
* -4. Player did already retire and cannot bet until new round.
* -5. All the players but this did retire. The game is setting itself to the last phase and assigning a winner. Cannot bet now.
* -6. Negative bet amount. Bets may only be positive.

#### Check Winner
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

##### Returns
* ArrayList. [0] Str. ID of the Winner; [1] Str. Name of the winning play; [2] Int. Ammount of chips won by the winner.
###### Error
* Null. IOException or game does not exist.

## Client

### Information
It has 2 windows. The first to create and manage all the possible info about games, user accounts and what the several phases of a game are and how it works. The second  it's the game itself.

All the input from the user which has to be processed and is sensible, it's matched against regex patterns to check it has nothing weird and it's adequated to what the program asks.

### Main Window

#### Create Game
Asks for 2 inputs. Reference of the game and total number of players.
* Reference. As this game has been designed to host multiple games at the same time, they need an unique ID so the rest of the players may enter to the same game you are creating. The ID can only contain: letters, numbers and underscore.
* Number of players. It's the total number of players the game is going to host. The game will start once all of them have joined. Range: 2-9 (inclusive).

##### Internal Result Options
*  1 . Everything went as expected. Game created and ready to be joined.
* 0. Number the result is initialized with. Only if no other result. Should never be achievable.
* -1. Connection troubles. The client cannot reach the server.
* -2. Reference does not match regex.
* -3. Number of players does not match regex.
* -4. This reference is currently in use. Use another one.

#### Join Game
Joins a game. This game must exist, the reference must be valid, match and it may not be already started.

##### Internal Result Options
*  1 . Game joined correctly.
* 0. Number the result is initialized with. Internal general error. Check it.
* -1. Connection troubles. Client cannot reach the server.
* -2. Reference does not match regex.
* -3. A game with this reference does not exist.
* -4. The game with this reference has already started and cannot be joined.

## Versions

* __0.3__ Setting specific error outputs in the client. The user will know what did go wrong.
* __0.2.3__ Added a way to retire players from the current round without them being deleted from the game. They'll be able to get back at the start of the next round while they have chips left. The game just jumps them as if they weren't there.
* __0.2.2__ Added checks and a way so the game gets stopped and deleted automatically when there's only 1 player left in game.
* __0.2.1__ Phases fully implemented, now the game flows smooth from the first, to the last one.  
* __0.2__   Created and implemented State Machine Pattern. It will be the structure of the several Phases in poker. Changes behaviour of actions depending on the phase it's currently at.
* __0.1.2__ Implemented and tested them to be sure they work as intended. Full Card objects sent through the network.
* __0.1.1__ Designing the basic structure of the network. Designing communications and package order.
* __0.1__   Started! Added the code representation of cards and a deck. Also methods to manipulate them.
* __0.0.1__ Initial status. Made a repostitory for the project. Looking which code of old projects can I re-use. Created really basic _Client_ and _Server_ interconnection.

## Footnote
If by any miracle you do stumble across this project and find bugs, have suggestions or any idea in general I'm open to emails or notes here.
