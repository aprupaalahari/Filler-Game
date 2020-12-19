=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: 21557860
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Arrays
  		I used 2D arrays to hold my tiles in the Filler class for the logic of the game. 
  		I also used 2D arrays to hold the buttons in the GameBoard class to be interacted with. 
  		I felt that it was appropriate to use this because my game relies heavily on the grid format
  		and required iteration through the buttons/tiles.

  2. Collections
  		I used collections to store my Leaderboard, as well as the tiles which belonged to each player.
  		Using a collection was best because I could keep track of, remove, and add elements to it accordingly,
  		as well as iterate through them and perform actions (ex. coloring) on elements. 

  3. Recursion
  		I used recursion as a way to ensure that my game was fair. The game is told to always start in such
  		a coloring that no neighboring tiles are of the same color (this would result in a "clump" which could
  		serve as an advantage to the player it is closest to at the start of the game. Thus, I used recursion 
  		to make sure no vertically or horizontally adjacent tiles were the same color to make the game fair. 

  4. File I/O
  		I used File I/O to keep track of and display the Leaderboard for the game. This made it easy to also ensure
  		that scores were ordered correctly, which was helpful to ensuring the leaderboard actually showed high scores
  		as wanted!

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  		Game: This class serves as the GUI for my game. It creates a GameBoard to play on, and is runnable so that
  		players can use it.
  		GameBoard: This served as the interactive component for my logic from Filler to be placed on. It creates a 
  		Filler so that players can actually play the game. 
  		Filler: This class contains the logic of the game, including an option to reset. 


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  		One block I was having was the graphics of the buttons. I ended up coloring them through the Tiles themselves 
  		(which were treated as buttons) rather than with the grid layout in GameBoard. After solving this, I was able
  		to see the visuals of the game, but it took a while. Additionally, making sure the game would be fair was difficult 
  		as I had to ensure that no player had any advantage. 


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
  		I believe that I was able to preserve functionality. For each method, I made sure to give a description so that
  		I could ensure it was in the right place. Private state is encapsulated pretty well; I used Getters and Setters on
  		private instance variables to make sure of this. 

========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
