package CAV_Env;
//5 pedestrians
public final class RoadScenarioVar
{
    public static int j_ScenarioWidth  = 1980;
    public static int j_ScenarioHeight = 510;
    public static int j_FrameWidth   = 1980;
    public static int j_FrameHeight  = 610;
    public static int m_speedVehicle = 20;
	public static int m_speedVehicle2 = 20;
	
    public static int total_Score1 = 1000;
	public static int total_Score2 = 1000;	
    public static int total_Score3 = 1000;
	public static int total_Score4 = 1000;	
    public static int total_Score5 = 1000;
	public static int total_Score6 = 1000;
	public static int total_Score = 0;
	public static int total_Score_number=0;
    public static int g_vehicleMeter = 0;
	public static int g_vehicle2Meter = 0;
    public static int g_pedestrianMeter = 0;

	public static int g_vehicleSize = 160;
	public static int g_vehicle2Size = 118;
	public static int g_pedestrianSize =70;
	
	public static int g_vehicle_x =100;
	public static int g_vehicle_y =100;
	public static int g_vehicle2_x =100;
	public static int g_vehicle2_y =100;
	public static int g_pedestrian1_x =0;
	public static int g_pedestrian1_y =0;
	public static int g_pedestrian2_x =0;
	public static int g_pedestrian2_y =0;
	public static int g_pedestrian3_x =0;
	public static int g_pedestrian3_y =0;
	public static int g_pedestrian4_x =0;
	public static int g_pedestrian4_y =0;
	public static int g_pedestrian5_x =0;
	public static int g_pedestrian5_y =0;
	public static int g_pedestrian6_x =0;
	public static int g_pedestrian6_y =0;
	
	public static int vehicle_i =0;
	public static int vehicle2_i =0;
	public static int pedestrian_i=0;
	public static int pedestrian1_verticle=0;
	public static int pedestrian2_verticle=0;
	public static int pedestrian3_verticle=0;
	public static int pedestrian4_verticle=0;
	public static int pedestrian5_verticle=0;
	public static int pedestrian6_verticle=0;
	public static int pedestrian1_parallel =1;
	public static int pedestrian2_parallel =1;
	public static int pedestrian3_parallel =1;
	public static int pedestrian4_parallel =1;
	public static int pedestrian5_parallel =1;
	public static int pedestrian6_parallel =1;
    public static boolean m_Pause = true; 
    public static boolean m_Frame = true; 
	
	public static double pedestrian_belief =0.5;
	public static boolean pedestrian_arrive = false; 
	public static boolean button_pause =false; 
	
	public static long startTime=0; 
	public static long endTime=0; 
	public static long time_se =0;
	public static int number =1;
	public static int  move_belief1 = 1;
	public static int  move_belief2 = 1;
	public static int  move_belief3 = 1;
	public static int  move_belief4 = 1;
	public static int  move_belief5 = 1;
	public static int  move_belief6 = 1;
	
	public static int move_mode_ID =2;
}