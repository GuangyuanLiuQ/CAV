// Agent pedestrian in project CAV1.mas2j

/* Initial beliefs and rules */
vehicle(1000,130).
vehicle2(0,250).
pedestrian1(0,0).
pedestrian2(0,0).
pedestrian3(0,0).
pedestrian4(0,0).
pedestrian5(0,0).
pedestrian6(0,0).
safeVP.

crossSafe :-  safeVP & crossDesire. //two conditions 
/* Initial goals */
!prepare_start_pedestrian.

/* Plans */
+!prepare_start_pedestrian:true
<- 
	//.print("Pedestrian is going to start!");
	//?pedestrian(X,Y);
	//?pedestrian(1,X,Y);
	locatep(X,Y); 
	//-pedestrian(1,X,Y); 
	//!strat_pedestrian.
	!start_pedestrian.
	
+!start_pedestrian:  safeVP & not movingp
	<- 	
	.print("The pedestrian is starting!");
	//?pedestrian(1,X);
	start_p;
	//?pedestrian(1,Y);
	//.print(pedestrian(1,Y));
	!walk.
	
+!start_pedestrian:  safeVP & movingp
	<- 	
	//.print("The pedestrian has already start!");
	//?pedestrian(1,X);
	start_p;
	//?pedestrian(1,Y);
	!walk.
		
+!walk :  safeVP & movingp
	<-
	//.print("no danger");
	move_p;
	!walk.
	
+!walk : not safeVP & movingp
	<-
	.print("Vehicle is coming!");
	.send(vechicle,tell,danger(vehicle));
	!walk.
	
+!walk: safeVP & not movingp
	<-
	.print("I walk.");
	stop_p;
	!walk.
+!walk:crossSafe 
	<-
	cross_road;
	!walk.

+!arrive: safeVP & not movingp
	<-
	//.print("arrive the shop");
	stop_p;
	!arrive.


