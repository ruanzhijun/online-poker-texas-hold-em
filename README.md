# Poker Texas Hold'em (Work In Progress)
Little personal project because I love playing card games with my family and found it a good way to practice java, networking, multithreading and Client-Server structure.

## TO-DOs
### Client
* Integrate the PHP files to manage user login with DBB.
* Capture all the exceptions I manage to get and do it as dumb-proof as I can and more.
* Assign a random client # when user joins a game without log-in.

### Server
* Set the port so it's asked by default when starting the server.

### General
* Move this doc into a Word, leave here a basic explanation.
* Adjust the timers. Right now there's a 5000 ms delay when ending a round until it starts the next one so the secondary thread has time to get the info several times (just in case).

## Future Planned Improvements
* Make it so a player can only bet the same as the previous player did or more.
* Set a timer for a player's action so it cannot take him longer than i seconds. If he does, retire or bet 0.
* The creator of the game may start it without all the players have joined (Only the creator).
* Add the posibility to make password-protected games.
* Update GUI to JavaFX (instead of Swing).
* Update check and auto-update.
* Port it to Android(?). Should not be really difficult as almost everything's been done in Java.

## Known Bugs
* Trouble to end a complete round. The side thread does not end itself when it should.

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
