/*
Speed V1: 20mph, V2: 26.6mph
Width 1980(99metre)
Height 480(24metre)
*/

package CAV_Env;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.*;
import jason.NoValueException;
import jason.asSyntax.*;
import jason.environment.*;

public class roadEnv extends Environment
{
    public RoadGUI road_GUI;
    
    public roadEnv()
    {
        road_GUI = new RoadGUI();
    }

    public boolean executeAction(String ag, Structure action)
    {
        try
        {
            Thread.sleep(RoadScenarioVar.m_speedVehicle);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
		//System.out.println("Agent " + ag + " is doing " +action);

    if(action.getFunctor().equals("locate"))
    {
        try
        {
            HandleLocate_v(ag,action);
        }
        catch(NoValueException e)
        {
            e.printStackTrace();
        }
    }

    if(action.getFunctor().equals("locatep"))
    {
        try
        {	
            HandleLocate_p(ag,action);
        }
        catch(NoValueException e)
        {
            e.printStackTrace();
        }
    }

    if(action.getFunctor().equals("start_v"))
    {
        try
		{
        HandleStart_v(ag,action);
        }
		catch(NoValueException e)
		{
            e.printStackTrace();
        }
    }

    if(action.getFunctor().equals("start_p"))
    {
        HandleStart_p(ag,action);
		bornP(ag);
    }

    if(action.getFunctor().equals("move_v"))
    {
        try{
            HandleMoveto_v(ag,action);
        }catch(NoValueException e){
            e.printStackTrace();
        }
    }

    if(action.getFunctor().equals("move_p"))
    {
        try{
            HandleMoveto_p(ag,action);
        }catch(NoValueException e){
            e.printStackTrace();
        }
    }
	
    if(action.getFunctor().equals("cross_road")&&RoadScenarioVar.move_mode_ID==3)
    {
        try{
            HandleCross_p(ag,action);
        }catch(NoValueException e){
            e.printStackTrace();
        }
    }

    if(action.getFunctor().equals("stop_v"))
    {
            HandleStop_v(ag,action);
    }
	
	if(action.getFunctor().equals("stop_p"))
    {
            HandleStop_p(ag,action);
    }
			 
    road_GUI.Update();
    return true;
    }

    private void HandleLocate_v(String ag, Structure action) throws NoValueException//Locate vehicle
    {
        //System.out.println("Start HandleLocate_v");
		int var = (int)((NumberTerm)action.getTerm(0)).solve();

        if(ag.equals("vehicle"))
		{
            RoadScenarioVar.g_vehicleMeter = var;
            String str = "vehicle(1,"+Integer.toString(RoadScenarioVar.g_vehicleMeter)+")";

            if(!containsPercept("vehicle",Literal.parseLiteral(str)))
                addPercept("vehicle",Literal.parseLiteral(str));			
			 if(!containsPercept("vehicle2",Literal.parseLiteral(str)))
                addPercept("vehicle2",Literal.parseLiteral(str));
        }
		else if(ag.equals("vehicle2"))
			{
			RoadScenarioVar.g_vehicle2Meter = var;
            String str = "vehicle2(1,"+Integer.toString(RoadScenarioVar.g_vehicle2Meter)+")";

			 if(!containsPercept("vehicle",Literal.parseLiteral(str)))
                addPercept("vehicle",Literal.parseLiteral(str));	
            if(!containsPercept("vehicle2",Literal.parseLiteral(str)))
                addPercept("vehicle2",Literal.parseLiteral(str));
			}
        else
            System.out.println("Error in the Locate Step!");
    }
	
	 private void HandleLocate_p(String ag, Structure action) throws NoValueException
	 {	
		 System.out.println("Locate_p");
	 }

    private void HandleStart_v(String ag, Structure action) throws NoValueException //Start vehicle add moving 
    {   
        if(!containsPercept(ag,Literal.parseLiteral("moving")))
            addPercept(ag,Literal.parseLiteral("moving"));
	   if(!containsPercept(ag,Literal.parseLiteral("safeVP1")))
            addPercept(ag,Literal.parseLiteral("safeVP1"));
	   if(!containsPercept(ag,Literal.parseLiteral("safeVP2")))
            addPercept(ag,Literal.parseLiteral("safeVP2"));

        
        if(ag.equals("vehicle"))
        {
			String str = "vehicle(1,"+Integer.toString(RoadScenarioVar.g_vehicleMeter)+")";// str == "vehicle(1,X)"
			removePercept("vehicle",Literal.parseLiteral(str));
			removePercept("vehicle2",Literal.parseLiteral(str));
        
        if(RoadScenarioVar.g_vehicleMeter+8<=2010){
            RoadScenarioVar.g_vehicleMeter= RoadScenarioVar.g_vehicleMeter+4;
        }
        else{
            RoadScenarioVar.g_vehicleMeter=0;
        }

		str = "vehicle(1,"+Integer.toString(RoadScenarioVar.g_vehicleMeter)+")";
		if(!containsPercept("vehicle",Literal.parseLiteral(str)))
            addPercept("vehicle",Literal.parseLiteral(str));
		if(!containsPercept("vehicle2",Literal.parseLiteral(str)))
            addPercept("vehicle2",Literal.parseLiteral(str));
        }
		else if(ag.equals("vehicle2"))
			{
				String str = "vehicle2(1,"+Integer.toString(RoadScenarioVar.g_vehicleMeter)+")";
				removePercept("vehicle",Literal.parseLiteral(str));
				removePercept("vehicle2",Literal.parseLiteral(str));
        
        if(RoadScenarioVar.g_vehicle2Meter+8<=2010){
            RoadScenarioVar.g_vehicle2Meter= RoadScenarioVar.g_vehicle2Meter+4;
        }
        else{
            RoadScenarioVar.g_vehicle2Meter=0;
        }

        str = "vehicle2(1,"+Integer.toString(RoadScenarioVar.g_vehicle2Meter)+")";
		if(!containsPercept("vehicle",Literal.parseLiteral(str)))
            addPercept("vehicle",Literal.parseLiteral(str));
		if(!containsPercept("vehicle2",Literal.parseLiteral(str)))
            addPercept("vehicle2",Literal.parseLiteral(str));
			}
        else 
        {
			System.out.println("Error in the Start Step!");
        }
    }
	
	private void HandleStart_p(String ag, Structure action) 
	{	
		 if(!containsPercept(ag,Literal.parseLiteral("movingp")))
		 {
			 addPercept(ag,Literal.parseLiteral("movingp"));
		 }
	}

    private void HandleMoveto_v(String ag, Structure action) throws NoValueException
    {
		if(RoadScenarioVar.vehicle_i>=1)// When vehicles arrive the end of the road, Restart.
				restart();
        if(!containsPercept(ag,Literal.parseLiteral("moving")))
            addPercept(ag,Literal.parseLiteral("moving"));
		if(RoadScenarioVar.m_Pause)
			{
						//no need for each pedestrian to control an independent belief safeVPi
						if((RoadScenarioVar.g_vehicle_x-RoadScenarioVar.g_pedestrian1_x)>=-10 &&(RoadScenarioVar.g_vehicle_x-RoadScenarioVar.g_pedestrian1_x)<=100 && (Math.abs(RoadScenarioVar.g_vehicle_y-RoadScenarioVar.g_pedestrian1_y))<=50
							||((-RoadScenarioVar.g_vehicle2_x+RoadScenarioVar.g_pedestrian1_x)>=-10 && (-RoadScenarioVar.g_vehicle2_x+RoadScenarioVar.g_pedestrian1_x)<=100 && (Math.abs(RoadScenarioVar.g_vehicle2_y-RoadScenarioVar.g_pedestrian1_y))<=50))	
						{	RoadScenarioVar.total_Score1=RoadScenarioVar.total_Score1-500;
							removePercept(ag,Literal.parseLiteral("safeVP1"));	
							RoadScenarioVar.vehicle_i++;
						}
						if((RoadScenarioVar.g_vehicle_x-RoadScenarioVar.g_pedestrian2_x)>=-10 &&(RoadScenarioVar.g_vehicle_x-RoadScenarioVar.g_pedestrian2_x)<=100 && (Math.abs(RoadScenarioVar.g_vehicle_y-RoadScenarioVar.g_pedestrian2_y))<=50
							||((-RoadScenarioVar.g_vehicle2_x+RoadScenarioVar.g_pedestrian2_x)>=-10 && (-RoadScenarioVar.g_vehicle2_x+RoadScenarioVar.g_pedestrian2_x)<=100 && (Math.abs(RoadScenarioVar.g_vehicle2_y-RoadScenarioVar.g_pedestrian2_y))<=50))	
						{	RoadScenarioVar.total_Score2=RoadScenarioVar.total_Score2-500;
								removePercept(ag,Literal.parseLiteral("safeVP1"));	
								RoadScenarioVar.vehicle_i++;
						}
						if((RoadScenarioVar.g_vehicle_x-RoadScenarioVar.g_pedestrian3_x)>=-10 &&(RoadScenarioVar.g_vehicle_x-RoadScenarioVar.g_pedestrian3_x)<=100 && (Math.abs(RoadScenarioVar.g_vehicle_y-RoadScenarioVar.g_pedestrian3_y))<=50
							||((-RoadScenarioVar.g_vehicle2_x+RoadScenarioVar.g_pedestrian3_x)>=-10 && (-RoadScenarioVar.g_vehicle2_x+RoadScenarioVar.g_pedestrian3_x)<=100 && (Math.abs(RoadScenarioVar.g_vehicle2_y-RoadScenarioVar.g_pedestrian3_y))<=50))	
						{	RoadScenarioVar.total_Score3=RoadScenarioVar.total_Score3-500;
							removePercept(ag,Literal.parseLiteral("safeVP1"));	
							RoadScenarioVar.vehicle_i++;
						}
						if((RoadScenarioVar.g_vehicle_x-RoadScenarioVar.g_pedestrian4_x)>=-10 &&(RoadScenarioVar.g_vehicle_x-RoadScenarioVar.g_pedestrian4_x)<=100 && (Math.abs(RoadScenarioVar.g_vehicle_y-RoadScenarioVar.g_pedestrian4_y))<=50
							||((-RoadScenarioVar.g_vehicle2_x+RoadScenarioVar.g_pedestrian4_x)>=-10 && (-RoadScenarioVar.g_vehicle2_x+RoadScenarioVar.g_pedestrian4_x)<=100 && (Math.abs(RoadScenarioVar.g_vehicle2_y-RoadScenarioVar.g_pedestrian4_y))<=50))	
						{	RoadScenarioVar.total_Score4=RoadScenarioVar.total_Score4-500;
							removePercept(ag,Literal.parseLiteral("safeVP1"));	
							RoadScenarioVar.vehicle_i++;
						}
						if((RoadScenarioVar.g_vehicle_x-RoadScenarioVar.g_pedestrian5_x)>=-10 &&(RoadScenarioVar.g_vehicle_x-RoadScenarioVar.g_pedestrian5_x)<=100 && (Math.abs(RoadScenarioVar.g_vehicle_y-RoadScenarioVar.g_pedestrian5_y))<=50
							||((-RoadScenarioVar.g_vehicle2_x+RoadScenarioVar.g_pedestrian5_x)>=-10 && (-RoadScenarioVar.g_vehicle2_x+RoadScenarioVar.g_pedestrian5_x)<=100 && (Math.abs(RoadScenarioVar.g_vehicle2_y-RoadScenarioVar.g_pedestrian5_y))<=50))	
						{	RoadScenarioVar.total_Score5=RoadScenarioVar.total_Score5-500;
							removePercept(ag,Literal.parseLiteral("safeVP1"));	
							RoadScenarioVar.vehicle_i++;
						}
						if((RoadScenarioVar.g_vehicle_x-RoadScenarioVar.g_pedestrian6_x)>=-10 &&(RoadScenarioVar.g_vehicle_x-RoadScenarioVar.g_pedestrian6_x)<=150 && (Math.abs(RoadScenarioVar.g_vehicle_y-RoadScenarioVar.g_pedestrian6_y))<=50
							||((-RoadScenarioVar.g_vehicle2_x+RoadScenarioVar.g_pedestrian6_x)>=-10 && (-RoadScenarioVar.g_vehicle2_x+RoadScenarioVar.g_pedestrian6_x)<=100 && (Math.abs(RoadScenarioVar.g_vehicle2_y-RoadScenarioVar.g_pedestrian6_y))<=50))	
						{	RoadScenarioVar.total_Score6=RoadScenarioVar.total_Score6-500;
							removePercept(ag,Literal.parseLiteral("safeVP1"));	
							RoadScenarioVar.vehicle_i++;
						}
    
					if(ag.equals("vehicle"))
					{
						String str = "vehicle(1,"+Integer.toString(RoadScenarioVar.g_vehicleMeter)+")";
						removePercept("vehicle",Literal.parseLiteral(str));
    
						if(RoadScenarioVar.g_vehicleMeter+12<=2010)
							RoadScenarioVar.g_vehicleMeter+=12;
						else
						{
							RoadScenarioVar.g_vehicleMeter=0;
							RoadScenarioVar.vehicle_i++;
						}
		
						str = "vehicle(1,"+Integer.toString(RoadScenarioVar.g_vehicleMeter)+")";
						if(!containsPercept("vehicle",Literal.parseLiteral(str)))
							addPercept("vehicle",Literal.parseLiteral(str));
						if(!containsPercept("vehicle2",Literal.parseLiteral(str)))
							addPercept("vehicle2",Literal.parseLiteral(str));
					}
		
					else if (ag.equals("vehicle2"))
					{	
						String str = "vehicle2(1,"+Integer.toString(RoadScenarioVar.g_vehicleMeter)+")";
						removePercept("vehicle",Literal.parseLiteral(str));
						removePercept("vehicle2",Literal.parseLiteral(str));
        
						if(RoadScenarioVar.g_vehicle2Meter+10<=2010)
							RoadScenarioVar.g_vehicle2Meter= RoadScenarioVar.g_vehicle2Meter+10;
						else
						{	
							RoadScenarioVar.g_vehicle2Meter=0;
							RoadScenarioVar.vehicle_i++;
						}

						str = "vehicle2(1,"+Integer.toString(RoadScenarioVar.g_vehicle2Meter)+")";
						//System.out.println(str);
						if(!containsPercept("vehicle",Literal.parseLiteral(str)))
							addPercept("vehicle",Literal.parseLiteral(str));
						if(!containsPercept("vehicle2",Literal.parseLiteral(str)))
							addPercept("vehicle2",Literal.parseLiteral(str));
					}
					else 
						System.out.println("Error in the MoveTo Step!");	
			}
			
	}
	
	 private void HandleMoveto_p(String ag, Structure action) throws NoValueException
	  {	 
		  addPercept_pedestrianP(ag);
		  minusScore();
		  //the third version
		  if(ag.equals("pedestrian1")&&((((RoadScenarioVar.g_vehicle_x-RoadScenarioVar.g_pedestrian1_x)>=301)
			  ||(RoadScenarioVar.g_vehicle_x-RoadScenarioVar.g_pedestrian1_x)<=0)&&(((-RoadScenarioVar.g_vehicle2_x+RoadScenarioVar.g_pedestrian1_x)>=301)
		  	  ||(RoadScenarioVar.g_vehicle2_x-RoadScenarioVar.g_pedestrian1_x)>=0))&&(RoadScenarioVar.move_mode_ID==3))
				  	addPercept(ag,Literal.parseLiteral("crossDesire"));
		  if(ag.equals("pedestrian2")&&((((RoadScenarioVar.g_vehicle_x-RoadScenarioVar.g_pedestrian2_x)>=301)
			  ||(RoadScenarioVar.g_vehicle_x-RoadScenarioVar.g_pedestrian2_x)<=0)&&(((-RoadScenarioVar.g_vehicle2_x+RoadScenarioVar.g_pedestrian2_x)>=150)
		  	  ||(RoadScenarioVar.g_vehicle2_x-RoadScenarioVar.g_pedestrian2_x)>=0))&&(RoadScenarioVar.move_mode_ID==3))
				  	addPercept(ag,Literal.parseLiteral("crossDesire"));
		  if(ag.equals("pedestrian3")&&((((RoadScenarioVar.g_vehicle_x-RoadScenarioVar.g_pedestrian3_x)>=301)
			  ||(RoadScenarioVar.g_vehicle_x-RoadScenarioVar.g_pedestrian3_x)<=0)&&(((-RoadScenarioVar.g_vehicle2_x+RoadScenarioVar.g_pedestrian3_x)>=301)
		  	  ||(RoadScenarioVar.g_vehicle2_x-RoadScenarioVar.g_pedestrian3_x)>=0))&&(RoadScenarioVar.move_mode_ID==3))
				  	addPercept(ag,Literal.parseLiteral("crossDesire"));
		 if(ag.equals("pedestrian4")&&((((RoadScenarioVar.g_vehicle_x-RoadScenarioVar.g_pedestrian4_x)>=301)
			  ||(RoadScenarioVar.g_vehicle_x-RoadScenarioVar.g_pedestrian4_x)<=0)&&(((-RoadScenarioVar.g_vehicle2_x+RoadScenarioVar.g_pedestrian4_x)>=301)
		  	  ||(RoadScenarioVar.g_vehicle2_x-RoadScenarioVar.g_pedestrian4_x)>=0))&&(RoadScenarioVar.move_mode_ID==3))
				  	addPercept(ag,Literal.parseLiteral("crossDesire"));
		 if(ag.equals("pedestrian5")&&((((RoadScenarioVar.g_vehicle_x-RoadScenarioVar.g_pedestrian5_x)>=301)
			  ||(RoadScenarioVar.g_vehicle_x-RoadScenarioVar.g_pedestrian5_x)<=0)&&(((-RoadScenarioVar.g_vehicle2_x+RoadScenarioVar.g_pedestrian5_x)>=301)
		  	  ||(RoadScenarioVar.g_vehicle2_x-RoadScenarioVar.g_pedestrian5_x)>=0))&&(RoadScenarioVar.move_mode_ID==3))
				  	addPercept(ag,Literal.parseLiteral("crossDesire"));
		 if(ag.equals("pedestrian6")&&((((RoadScenarioVar.g_vehicle_x-RoadScenarioVar.g_pedestrian6_x)>=301)
			  ||(RoadScenarioVar.g_vehicle_x-RoadScenarioVar.g_pedestrian6_x)<=0)&&(((-RoadScenarioVar.g_vehicle2_x+RoadScenarioVar.g_pedestrian6_x)>=301)
		  	  ||(RoadScenarioVar.g_vehicle2_x-RoadScenarioVar.g_pedestrian6_x)>=0))&&(RoadScenarioVar.move_mode_ID==3))
				  	addPercept(ag,Literal.parseLiteral("crossDesire"));	
	  }
	  
private void HandleStop_v(String ag, Structure action)
    {
		System.out.println("Waiting the pedestrian!");
			if( 	((-RoadScenarioVar.g_vehicle_x+RoadScenarioVar.g_pedestrian1_x)<=-10 || (-RoadScenarioVar.g_vehicle_x+RoadScenarioVar.g_pedestrian1_x)>=150 && (Math.abs(RoadScenarioVar.g_vehicle_y-RoadScenarioVar.g_pedestrian1_y))>=40)
				&&((-RoadScenarioVar.g_vehicle_x+RoadScenarioVar.g_pedestrian2_x)<=-10 || (-RoadScenarioVar.g_vehicle_x+RoadScenarioVar.g_pedestrian2_x)>=150 && (Math.abs(RoadScenarioVar.g_vehicle_y-RoadScenarioVar.g_pedestrian2_y))>=40)
				&&((-RoadScenarioVar.g_vehicle_x+RoadScenarioVar.g_pedestrian3_x)<=-10 || (-RoadScenarioVar.g_vehicle_x+RoadScenarioVar.g_pedestrian3_x)>=150 && (Math.abs(RoadScenarioVar.g_vehicle_y-RoadScenarioVar.g_pedestrian3_y))>=40)
				&&((-RoadScenarioVar.g_vehicle_x+RoadScenarioVar.g_pedestrian4_x)<=-10 || (-RoadScenarioVar.g_vehicle_x+RoadScenarioVar.g_pedestrian4_x)>=150 && (Math.abs(RoadScenarioVar.g_vehicle_y-RoadScenarioVar.g_pedestrian4_y))>=40)
				&&((-RoadScenarioVar.g_vehicle_x+RoadScenarioVar.g_pedestrian5_x)<=-10 || (-RoadScenarioVar.g_vehicle_x+RoadScenarioVar.g_pedestrian5_x)<=150 && (Math.abs(RoadScenarioVar.g_vehicle_y-RoadScenarioVar.g_pedestrian5_y))>=40)
				&&((-RoadScenarioVar.g_vehicle_x+RoadScenarioVar.g_pedestrian6_x)<=-10 || (-RoadScenarioVar.g_vehicle_x+RoadScenarioVar.g_pedestrian6_x)>=150 && (Math.abs(RoadScenarioVar.g_vehicle_y-RoadScenarioVar.g_pedestrian6_y))>=40)
				)
				{
				addPercept(ag,Literal.parseLiteral("safeVP1"));				
				}
		
				removePercept(ag,Literal.parseLiteral("moving"));
    }
	
	private void HandleStop_p(String ag, Structure action)
	{
		System.out.println("Pedestrian Arrives the Shop");
		RoadScenarioVar.pedestrian_arrive=true;
	}	

	public static void printfile_stream()
	{
		try {
			File file = new File("C:\\Users\\Harry\\Desktop\\cav\\cav\\score.txt"); 
			//File file = new File("C:\\Users\\gl18254\\OneDrive - University of Bristol\\cav\\cav\\score.txt"); 
			FileOutputStream os = new FileOutputStream(file,true);
			
					//too mess if using many FileOutputStream write sentences. 
					//Writing in one sentence.
					
					//os.write(" v1x: ".getBytes());
					//os.write((Integer.toString(RoadScenarioVar.g_vehicleMeter)).getBytes());
					//os.write(" v2x: ".getBytes());
					//os.write((Integer.toString(RoadScenarioVar.g_vehicle2Meter)).getBytes());
					//os.write("No.".getBytes());
					/*
					os.write((Integer.toString(RoadScenarioVar.number)).getBytes());
					RoadScenarioVar.number++;
					os.write(" pedestrian: 1 ".getBytes());
					os.write(" Time: ".getBytes());
					os.write((Long.toString(RoadScenarioVar.time_se)+"ms").getBytes());
					os.write(" p1x: ".getBytes());
					os.write((Integer.toString(RoadScenarioVar.g_pedestrian1_x)).getBytes());
					os.write(" p1y: ".getBytes());
					os.write((Integer.toString(RoadScenarioVar.g_pedestrian1_y)).getBytes());
					os.write(" Score_p1: ".getBytes());
					os.write((Integer.toString(RoadScenarioVar.total_Score1)).getBytes());
					os.write("\r\n".getBytes());
					
					//os.write("No.".getBytes());
					os.write((Integer.toString(RoadScenarioVar.number)).getBytes());
					RoadScenarioVar.number++;
					os.write(" pedestrian: 2 ".getBytes());
					os.write(" Time: ".getBytes());
					os.write((Long.toString(RoadScenarioVar.time_se)+"ms").getBytes());
					os.write(" p2x: ".getBytes());
					os.write((Integer.toString(RoadScenarioVar.g_pedestrian2_x)).getBytes());
					os.write(" p2y: ".getBytes());
					os.write((Integer.toString(RoadScenarioVar.g_pedestrian2_y)).getBytes());
					os.write(" Score_p2: ".getBytes());
					os.write((Integer.toString(RoadScenarioVar.total_Score2)).getBytes());
					os.write("\r\n".getBytes());
					
					os.write((Integer.toString(RoadScenarioVar.number)).getBytes());
					RoadScenarioVar.number++;
					os.write(" pedestrian: 3 ".getBytes());
					os.write(" Time: ".getBytes());
					os.write((Long.toString(RoadScenarioVar.time_se)+"ms").getBytes());
					os.write(" p3x: ".getBytes());
					os.write((Integer.toString(RoadScenarioVar.g_pedestrian3_x)).getBytes());
					os.write(" p3y: ".getBytes());
					os.write((Integer.toString(RoadScenarioVar.g_pedestrian3_y)).getBytes());
					os.write(" Score_p3: ".getBytes());
					os.write((Integer.toString(RoadScenarioVar.total_Score3)).getBytes());
					os.write("\r\n".getBytes());
					
					os.write((Integer.toString(RoadScenarioVar.number)).getBytes());
					RoadScenarioVar.number++;
					os.write(" pedestrian: 4 ".getBytes());
					os.write(" Time: ".getBytes());
					os.write((Long.toString(RoadScenarioVar.time_se)+"ms").getBytes());
					os.write(" p4x: ".getBytes());
					os.write((Integer.toString(RoadScenarioVar.g_pedestrian4_x)).getBytes());
					os.write(" p4y: ".getBytes());
					os.write((Integer.toString(RoadScenarioVar.g_pedestrian4_y)).getBytes());
					os.write(" Score_p4: ".getBytes());
					os.write((Integer.toString(RoadScenarioVar.total_Score4)).getBytes());
					os.write("\r\n".getBytes());
					
					os.write((Integer.toString(RoadScenarioVar.number)).getBytes());
					RoadScenarioVar.number++;
					os.write(" pedestrian: 5 ".getBytes());
					os.write(" Time: ".getBytes());
					os.write((Long.toString(RoadScenarioVar.time_se)+"ms").getBytes());
					os.write(" p5x: ".getBytes());
					os.write((Integer.toString(RoadScenarioVar.g_pedestrian5_x)).getBytes());
					os.write(" p5y: ".getBytes());
					os.write((Integer.toString(RoadScenarioVar.g_pedestrian5_y)).getBytes());
					os.write(" Score_p5: ".getBytes());
					os.write((Integer.toString(RoadScenarioVar.total_Score5)).getBytes());
					os.write("\r\n".getBytes());

					os.write((Integer.toString(RoadScenarioVar.number)).getBytes());
					RoadScenarioVar.number++;
					os.write(" pedestrian: 6 ".getBytes());
					os.write(" Time: ".getBytes());
					os.write((Long.toString(RoadScenarioVar.time_se)+"ms").getBytes());
					os.write(" p6x: ".getBytes());
					os.write((Integer.toString(RoadScenarioVar.g_pedestrian6_x)).getBytes());
					os.write(" p6y: ".getBytes());
					os.write((Integer.toString(RoadScenarioVar.g_pedestrian6_y)).getBytes());
					os.write(" Score_p6: ".getBytes());
					os.write((Integer.toString(RoadScenarioVar.total_Score6)).getBytes());
					*/
					os.write((RoadScenarioVar.total_Score_number+", "+Integer.toString(RoadScenarioVar.total_Score)+"\r\n").getBytes());
					//os.write("\r\n".getBytes());
					os.close();
		} catch(IOException e) {
					e.printStackTrace();
		}
	}
	
	public void bornP(String ag) //pedestrian birth point. Get action ID in the second version.
	{	
		RoadScenarioVar.startTime=System.currentTimeMillis(); 
		int px,py;
		px = (int)(Math.random()*1850)+50;
		py = (int)(Math.random()*450)+10;
		if(py>=80&&py<=220)
			py=80;
		if(py>220&&py<=345)
			py=345;
		if(px<=200||px>=1600)
			px=200+(int)(Math.random()*100);
		
		if(py<=500&&RoadScenarioVar.move_mode_ID==3)
			{
			py=20+(int)(Math.random()*40);
			}
		
		if(ag.equals("pedestrian1"))
		{
			RoadScenarioVar.g_pedestrian1_x=px;
			RoadScenarioVar.g_pedestrian1_y=py;
		if(px<=400&&py<=300)
			RoadScenarioVar.move_belief1=1;
		if(px>400&&px<=1320&&py<=300)
			RoadScenarioVar.move_belief1=2;
		if(px>1320&&py<=300)
			RoadScenarioVar.move_belief1=3;
		if(px<=400&&py>300)
			RoadScenarioVar.move_belief1=4;
		if(px>400&&px<=1320&&py>300)
			RoadScenarioVar.move_belief1=5;
		if(px>1320&&py>300)
			RoadScenarioVar.move_belief1=6;
		}	
		
		if(ag.equals("pedestrian2"))
		{
			RoadScenarioVar.g_pedestrian2_x=px;
			RoadScenarioVar.g_pedestrian2_y=py;
		if(px<=400&&py<=300)
			RoadScenarioVar.move_belief2=1;
		if(px>400&&px<=1320&&py<=300)
			RoadScenarioVar.move_belief2=2;
		if(px>1320&&py<=300)
			RoadScenarioVar.move_belief2=3;
		if(px<=400&&py>300)
			RoadScenarioVar.move_belief2=4;
		if(px>400&&px<=1320&&py>300)
			RoadScenarioVar.move_belief2=5;
		if(px>1320&&py>300)
			RoadScenarioVar.move_belief2=6;
		}
		
		if(ag.equals("pedestrian3"))
		{
			RoadScenarioVar.g_pedestrian3_x=px;
			RoadScenarioVar.g_pedestrian3_y=py;
		if(px<=400&&py<=300)
			RoadScenarioVar.move_belief3=1;
		if(px>400&&px<=1320&&py<=300)
			RoadScenarioVar.move_belief3=2;
		if(px>1320&&py<=300)
			RoadScenarioVar.move_belief3=3;
		if(px<=400&&py>300)
			RoadScenarioVar.move_belief3=4;
		if(px>400&&px<=1320&&py>300)
			RoadScenarioVar.move_belief3=5;
		if(px>1320&&py>300)
			RoadScenarioVar.move_belief3=6;
		}
		
		if(ag.equals("pedestrian4"))
		{
			RoadScenarioVar.g_pedestrian4_x=px;
			RoadScenarioVar.g_pedestrian4_y=py;
		if(px<=400&&py<=300)
			RoadScenarioVar.move_belief4=1;
		if(px>400&&px<=1320&&py<=300)
			RoadScenarioVar.move_belief4=2;
		if(px>1320&&py<=300)
			RoadScenarioVar.move_belief4=3;
		if(px<=400&&py>300)
			RoadScenarioVar.move_belief4=4;
		if(px>400&&px<=1320&&py>300)
			RoadScenarioVar.move_belief4=5;
		if(px>1320&&py>300)
			RoadScenarioVar.move_belief4=6;
		}
		
		if(ag.equals("pedestrian5"))
		{
			RoadScenarioVar.g_pedestrian5_x=px;
			RoadScenarioVar.g_pedestrian5_y=py;
		if(px<=400&&py<=300)
			RoadScenarioVar.move_belief5=1;
		if(px>400&&px<=1320&&py<=300)
			RoadScenarioVar.move_belief5=2;
		if(px>1320&&py<=300)
			RoadScenarioVar.move_belief5=3;
		if(px<=400&&py>300)
			RoadScenarioVar.move_belief5=4;
		if(px>400&&px<=1320&&py>300)
			RoadScenarioVar.move_belief5=5;
		if(px>1320&&py>300)
			RoadScenarioVar.move_belief5=6;
		}
		
		if(ag.equals("pedestrian6"))
		{
			RoadScenarioVar.g_pedestrian6_x=px;
			RoadScenarioVar.g_pedestrian6_y=py;
		if(px<=400&&py<=300)
			RoadScenarioVar.move_belief6=1;
		if(px>400&&px<=1320&&py<=300)
			RoadScenarioVar.move_belief6=2;
		if(px>1320&&py<=300)
			RoadScenarioVar.move_belief6=3;
		if(px<=400&&py>300)
			RoadScenarioVar.move_belief6=4;
		if(px>400&&px<=1320&&py>300)
			RoadScenarioVar.move_belief6=5;
		if(px>1320&&py>300)
			RoadScenarioVar.move_belief6=6;
		}	
	}
	
	public void restart()
	{			System.out.println("The scenario is rebuild!");
				RoadScenarioVar.endTime = System.currentTimeMillis();
				RoadScenarioVar.time_se   = RoadScenarioVar.endTime - RoadScenarioVar.startTime;
				calculate_score();
				printfile_stream();
				bornP("pedestrian1");		
				bornP("pedestrian2");	
				bornP("pedestrian3");	
				bornP("pedestrian4");	
				bornP("pedestrian5");	
				bornP("pedestrian6");	
				RoadScenarioVar.m_Frame = false;
				RoadScenarioVar.g_vehicleMeter = 0;
				RoadScenarioVar.g_vehicle2Meter = 0;
				RoadScenarioVar.pedestrian1_verticle=0;
				RoadScenarioVar.pedestrian1_parallel =1;
				RoadScenarioVar.pedestrian2_verticle=0;
				RoadScenarioVar.pedestrian2_parallel =1;
				RoadScenarioVar.pedestrian3_verticle=0;
				RoadScenarioVar.pedestrian3_parallel =1;
				RoadScenarioVar.pedestrian4_verticle=0;
				RoadScenarioVar.pedestrian4_parallel =1;
				RoadScenarioVar.pedestrian5_verticle=0;
				RoadScenarioVar.pedestrian5_parallel =1;
				RoadScenarioVar.pedestrian6_verticle=0;
				RoadScenarioVar.pedestrian6_parallel =1;
				RoadScenarioVar.pedestrian_arrive = false; 
				
				RoadScenarioVar.m_Frame = true;
				RoadScenarioVar.vehicle_i=0;
				RoadScenarioVar.total_Score1 = 1000;	
				RoadScenarioVar.total_Score2 = 1000;	
				RoadScenarioVar.total_Score3 = 1000;	
				RoadScenarioVar.total_Score4 = 1000;	
				RoadScenarioVar.total_Score5 = 1000;	
				RoadScenarioVar.total_Score6 = 1000;	
	}
	
	public void calculate_score()
	{
		RoadScenarioVar.total_Score=RoadScenarioVar.total_Score+(RoadScenarioVar.total_Score1+RoadScenarioVar.total_Score2+RoadScenarioVar.total_Score3+RoadScenarioVar.total_Score4+RoadScenarioVar.total_Score5+RoadScenarioVar.total_Score6);
		RoadScenarioVar.total_Score_number++;
	}
	 
	public void addPercept_pedestrianP(String ag)
	{	if (ag.equals("pedestrian1"))
		{String str = "pedestrian1("+Integer.toString(RoadScenarioVar.g_pedestrian1_x)+","+Integer.toString(RoadScenarioVar.g_pedestrian1_y)+")";
		  if(!containsPercept("vehicle",Literal.parseLiteral(str)))
				addPercept("vehicle",Literal.parseLiteral(str));
		  if(!containsPercept("vehicle2",Literal.parseLiteral(str)))
				addPercept("vehicle2",Literal.parseLiteral(str));}
		if(ag.equals("pedestrian2"))
		{String str = "pedestrian2("+Integer.toString(RoadScenarioVar.g_pedestrian2_x)+","+Integer.toString(RoadScenarioVar.g_pedestrian2_y)+")";
		  if(!containsPercept("vehicle",Literal.parseLiteral(str)))
					addPercept("vehicle",Literal.parseLiteral(str));
		  if(!containsPercept("vehicle2",Literal.parseLiteral(str)))
					addPercept("vehicle2",Literal.parseLiteral(str));}
		if(ag.equals("pedestrian3"))
		{ String str = "pedestrian3("+Integer.toString(RoadScenarioVar.g_pedestrian3_x)+","+Integer.toString(RoadScenarioVar.g_pedestrian3_y)+")";
		  if(!containsPercept("vehicle",Literal.parseLiteral(str)))
					addPercept("vehicle",Literal.parseLiteral(str));
		  if(!containsPercept("vehicle2",Literal.parseLiteral(str)))
					addPercept("vehicle2",Literal.parseLiteral(str)); }
		  if(ag.equals("pedestrian4"))
		  {String str = "pedestrian4("+Integer.toString(RoadScenarioVar.g_pedestrian4_x)+","+Integer.toString(RoadScenarioVar.g_pedestrian4_y)+")";
		  if(!containsPercept("vehicle",Literal.parseLiteral(str)))
					addPercept("vehicle",Literal.parseLiteral(str));
		  if(!containsPercept("vehicle2",Literal.parseLiteral(str)))
		  			addPercept("vehicle2",Literal.parseLiteral(str));}
		  if(ag.equals("pedestrian5"))
		  {String str = "pedestrian5("+Integer.toString(RoadScenarioVar.g_pedestrian5_x)+","+Integer.toString(RoadScenarioVar.g_pedestrian5_y)+")";
		  if(!containsPercept("vehicle",Literal.parseLiteral(str)))
					addPercept("vehicle",Literal.parseLiteral(str));
		  if(!containsPercept("vehicle2",Literal.parseLiteral(str)))
		  addPercept("vehicle2",Literal.parseLiteral(str));}
		  if(ag.equals("pedestrian6"))
		  {String str = "pedestrian6("+Integer.toString(RoadScenarioVar.g_pedestrian6_x)+","+Integer.toString(RoadScenarioVar.g_pedestrian6_y)+")";
		  if(!containsPercept("vehicle",Literal.parseLiteral(str)))
					addPercept("vehicle",Literal.parseLiteral(str));
		  if(!containsPercept("vehicle2",Literal.parseLiteral(str)))
		  		addPercept("vehicle2",Literal.parseLiteral(str));}
	}
	
	public void HandleCross_p(String ag, Structure action) throws NoValueException
	{	
		if(ag.equals("pedestrian1"))
			RoadScenarioVar.g_pedestrian1_y=RoadScenarioVar.g_pedestrian1_y+2;
		if(ag.equals("pedestrian2"))
			RoadScenarioVar.g_pedestrian2_y=RoadScenarioVar.g_pedestrian2_y+2;
		if(ag.equals("pedestrian3"))
			RoadScenarioVar.g_pedestrian3_y=RoadScenarioVar.g_pedestrian3_y+2;
		if(ag.equals("pedestrian4"))
			RoadScenarioVar.g_pedestrian4_y=RoadScenarioVar.g_pedestrian4_y+2;
		if(ag.equals("pedestrian5"))
			RoadScenarioVar.g_pedestrian5_y=RoadScenarioVar.g_pedestrian5_y+2;
		if(ag.equals("pedestrian6"))
			RoadScenarioVar.g_pedestrian6_y=RoadScenarioVar.g_pedestrian6_y+2;
	}
	
	public void minusScore()
	{				//Road scale (130, 340)
				if(RoadScenarioVar.g_pedestrian1_y>=130&&RoadScenarioVar.g_pedestrian1_y<=340)
					//RoadScenarioVar.total_Score1--;
					RoadScenarioVar.total_Score1=RoadScenarioVar.total_Score1-5;
				if(RoadScenarioVar.g_pedestrian2_y>=130&&RoadScenarioVar.g_pedestrian2_y<=340)
					//RoadScenarioVar.total_Score2--;
					RoadScenarioVar.total_Score2=RoadScenarioVar.total_Score2-5;
				if(RoadScenarioVar.g_pedestrian3_y>=130&&RoadScenarioVar.g_pedestrian3_y<=340)
					//RoadScenarioVar.total_Score3--;
					RoadScenarioVar.total_Score3=RoadScenarioVar.total_Score3-5;
				if(RoadScenarioVar.g_pedestrian4_y>=130&&RoadScenarioVar.g_pedestrian4_y<=340)
					//RoadScenarioVar.total_Score4--;
					RoadScenarioVar.total_Score4=RoadScenarioVar.total_Score4-5;
				if(RoadScenarioVar.g_pedestrian5_y>=130&&RoadScenarioVar.g_pedestrian5_y<=340)
					//RoadScenarioVar.total_Score5--;
					RoadScenarioVar.total_Score5=RoadScenarioVar.total_Score5-5;
				if(RoadScenarioVar.g_pedestrian6_y>=130&&RoadScenarioVar.g_pedestrian6_y<=340)
					//RoadScenarioVar.total_Score6--;
					RoadScenarioVar.total_Score6=RoadScenarioVar.total_Score6-5;
	}
	
}