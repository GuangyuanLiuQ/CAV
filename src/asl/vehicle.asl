/* Initial beliefs and rules */
vehicle(1,0).
vehicle2(1,0).
pedestrian(1,0,0).
pedestrian(2,60).

/* Initial goals */
!prepare_startVehicle.

/* Plans */
+!prepare_startVehicle:true
<-
 //.print("Vehicle is going to start.");
 ?vehicle(1,X);
 //.print(vehicle(1,X));//
 locate(X); 
 -vehicle(1,X); 
!start_vehicle.

+!start_vehicle:  not moving 			 //start_vehicle: add moving
<- 
	//.print("The vehicle is starting1!");
	?vehicle(1,X); 
	//.print("The vehicle is starting2!");
	start_v;
	?vehicle(1,Y);
	!run.
	
+!run :  safeVP1 & safeVP2 & not moving 					 
	<-                                   
	//.print("The AV is moving!");
    !start_vehicle.

+!run :  safeVP1 & safeVP2 &  moving 
	<- 
	?vehicle(1,X);
	move_v(X+5);
	?vehicle(1,Y);
	!run.
	
+!run :  not safeVP1 & moving 					//check distance ->remove moving
	<- 
	?vehicle(1,X);
	stop_v; 
	?vehicle(1,Y);								
	//.print("The AV has to stop1!");
	!run.
	
+!run :  not safeVP1 & not moving 				//check d ->remove moving
	<- 
	?vehicle(1,X);
	stop_v; 
	?vehicle(1,Y);								
	//.print("The AV has to stop1!");
	!run.

+!run :  not safeVP2 & moving 					//check d ->remove moving
	<- 
	?vehicle(1,X);
	stop_v; 
	?vehicle(1,Y);								
	//.print("The AV has to stop2!");
	!run.
	
+!run :  not safeVP2 & not moving 				//check d ->remove moving
	<- 
	?vehicle(1,X);
	stop_v; 
	?vehicle(1,Y);								
	//.print("The AV has to stop2!");
	!run.



