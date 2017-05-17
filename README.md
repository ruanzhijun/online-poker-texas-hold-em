# Poker Texas Hold'em (Work In Progress)

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

### Get private player cards
* O. Int. Menu option.
* O. String. Game reference.
* I. Bool. Does game exist?.
* O. String. Player's ID.
* I. Int. Number of cards to be received. (They will always be 2, but I do re-use code from the way I do get the table cards and didn't feel like to hardcode it).
* I. Card. Private player cards to be added.

#### Returns
* ArrayList<Card>. [0] Card #1; [1] Card #2.

### Get common table cards
* O. Int. Menu option.
* O. String. Game reference.
* I. Bool. Does game exist?.
* I. Int. Number of cards to be received.
* I. Card. Private player cards to be added.


### Bet
* O. Int. Menu option.
* O. String. Game reference.
* O. String. Player's ID.
* I. Bool. Does game exist?.
* I. Bool. May the player bet now?.
* O. Int. Number of chips to bet.
* I. Int. Number of chips the winner gets. (Common pool).

#### Error Return Options
* -1. IOException. Follow the error.
* -2. This game does not exist. Should never be reachable, but better check for it anyway.
* -3. Player may not bet right now. It's not his turn.
* -4. Player did already retire and cannot bet until new round.

### Check Winner
* O. Int. Menu option.
* O. String. Game reference.
* I. Bool. Does game exist?.
* I. Bool. Does the game already have a winner?.
* I. String. Winner's ID.
* I. String. Play the winner has won with (Name of it to display).
* I. Int. Number of chips won. (Only added to the winner).
