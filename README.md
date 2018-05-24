# Simple Game

Turn-based game in which player must survive on 20x20 board. In addition to the player, there are animals and plants that can be a threat. Every animal have two attributes: strength and initiative and plants have only strength.
If someone meets stronger than himself, he dies unless they are the same species. In this case they reproduce. Initiative determines which animal can move first (if they have the same number of initiative, the older have priority). Besides that animals and plants can have special properties, e.g. eating guarana increases strength and turtle have chance of defending itself against stronger opponent. Player have special ability that kills everything around him. This effect lasts for couple of turns but after every use player have wait some time before it will be available again.

To start turn click "next turn". Arrow keys are responsible for movement. To use special power press "c". You can also click empty space on board to spawn organism. Dialog will pop up and then you can choose animal or plant. Additional there is a log at the bottom of the screen, in which you can see every event that occurred during last turn. State of game can be saved to file and then loaded by clicking appropriate buttons on screen.

Organisms and their representation (colored squares):
 - human -> black
 - fox -> blue
 - grass -> green
 - guarana -> cyan
 - sheep -> white
 - sow thistle -> yellow
 - turtle -> red
 - wolf -> gray
 - wolf berries -> light gray