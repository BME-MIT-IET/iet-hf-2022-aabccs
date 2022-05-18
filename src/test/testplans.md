# Planned unit tests


## TestSetNeighbours
    
> Testing that locations within a given distance can be set as neighbours.

## TestSettlerCanMove
    
> Testing that a settler can move from a location to its neighbour.

## TestNoNeighbour
    
> Testing that a settler can not move from a location to an other if they are not neighbours.

## TestTeleportSuccess

> Testing that a settler can use a functioning teleport gate. 
    Checking if the settler's location is the other gate, not 
    the first gate and the two gates are not the same.


## TestTeleportFail
    
> Testing that if a teleport gate does not have a pair, the settler
    stays in the same location.
  
## TestWin
    
> Testing that if two settlers are in the same position with all needed materials the game is won.
  
## TestMineSuccessful
    
> Testing that a settler can mine the material from an asteroid if the layer size is 0.
  
## TestNoSpaceInInventory
    
> Testing that a settler with its inventory full can not mine the material from a 0-layer-sized asteroid.
  
## TestAsteroidIsEmpty
    
> Testing that a settler can not mine from an empty 0-layer-sized asteroid.
  
## TestMineOnTeleportGate
    
> Testing that a settler can not mine on a teleport gate.
  
## TestMineButLayerGreaterThanZero
    
> Testing that a settler can not mine an asteroid if its layer-size is greater than 0.
  
## TestSettlerCanBuildTeleportGate
    
> Testing that a settler with the required materials can create a teleport gate.
  
## TestSettlerCanNotBuildTeleportGate
    
> Testing that a settler without the required materials can not create a teleport gate.
  
## TestSettlerCanBuildRobot
    
> Testing that a settler with the required materials can create a robot and place it on its location.
  
## TestSettlerCanNotBuildRobot
    
> Testing that a settler without the required materials can not create a robot
  
## TestHideSuccess
    
> Testing that a settler can hide in an asteroid if it is empty and its layer-size is 0.
  
## TestHideFailNumberOfLayersGreaterThanZero
    
> Testing that a settler can not hide in an asteroid if its layer-size is greater than 0.
  
## TestHideFailNotEmpty
    
> Testing that a settler can not hide in an asteroid if it is not empty.
  
## TestComingOut
    
> Testing that a settler can come out from the inside of an asteroid.
  
## TestPlaceMaterialSuccess
    
> Testing that a settler can place a material from its inventory into an empty 0-layer-sized asteroid.
  
## TestPlaceMaterialFailNumberOfLayersGreaterThanZero
    
> Testing that a settler can not place a material from its inventory into an asteroid if its layer-size is greater than 0.
  
## TestPlaceMaterialFailNotEmpty
    
> Testing that a settler can not place a material from its inventory into an asteroid if it is not empty.
  
## TestPlaceMaterialFailNotOnAsteroid
    
> Testing that a settler can not place a material from its inventory into a location if it is not an asteroid.
  
## TestPlaceTeleportGate
    
> Testing that a settler can place a teleport gate from its inventory into space.
  
## TestDrillIsPossible
    
> Testing that a settler can drill on an asteroid if its layer-size is greater than 0.
  
## TestDrillButNumberOfLayersEqualsZero
    
> Testing that a settler can not drill on an asteroid if its layer-size is 0.
  
## TestDrillAndMaterialExposed
    
> Testing that if the layer-size of an asteroid becomes 0 through drilling the material inside is exposed.
  
## TestDrillOnTeleportGate
    
> Testing that a settler can not drill on a teleport gate.
  
## TestWaterSublimes
    
> Testing that water in a near-sun asteroid disappears when exposed.
  
## TestUranExplodes
    
> Testing that if an uran material is exposed near sun for the third time,
> it explodes, destroying the containing asteroid as well. 
  
## TestRobotExplodes
    
> Testing that if a robot explodes, it is transferred to a neighbouring asteroid.
  
## TestRobotExplodesNoNeighbour

> Testing that if a robot explodes on an asteroid with no neighbours, it gets destroyed.
  
## TestAsteroidExplodesWithSettler
    
> Testing that if an asteroid explodes, the settler on it dies.
  
## TestSunStorm
    
> Testing that if a sunstorm comes, the settler hidden inside and the robot on surface do not die, 
> but the settler on the surface dies.
  
## TestNotLastSettlerDies
    
> Testing that if there are multiple settlers and one dies, it is removed but the game continues.
  
## TestLose
    
> Testing that if the last settler dies, the game is lost.
  
## TestRobotDies

> Testing that if a robot dies, it is removed from the game.