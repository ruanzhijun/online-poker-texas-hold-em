# Poker Texas Hold'em (Work In Progress)

## Network packages order
The sending order is specified __from the point of view of a _Client_ to the _Server___. Most of them share a common structure and then do their specific part. Before doing all the send there're checks to see if the game the client is tryign to get info from, does exist.

### Mark Structure
* Output / Input. Data type. Comment about what it is.

## Actions

### Create Game
* O. Int. Menu option.
* O. String. Game reference.
* O. String. Player's ID.
* O. Int. Total number of players.
* I. Bool. Result of the action.


### Join Game
* O. Int. Menu option.
* O. String. Game reference.
* O. String. Player's ID.
* I. Bool. Result of the action.


### Information Secondary Thread
* O. Int. Menu option.
* O. String. Game reference.
* I. Bool. Does game exist?.
* O. String. Player's ID.
* I. String. Game phase (State machine).
* I. Bool. Is this player's turn?.


### Get private player cards
* O. Int. Menu option.
* O. String. Game reference.
* I. Bool. Does game exist?.
* O. String. Player's ID.
* I. Int. Number of cards to be received.
* I. Card. Private player cards to be added.


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


### Check Winner
* O. Int. Menu option.
* O. String. Game reference.
* I. Bool. Does game exist?.
* I. Bool. Does the game already have a winner?.
* I. String. Winner's ID.
* I. String. Play the winner has won with.
* I. Int. Number of chips won. (Only added to the winner).
