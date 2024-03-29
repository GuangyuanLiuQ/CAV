package CAV_Env;

import java.util.*;

import jason.NoValueException;
import jason.asSyntax.*;
import jason.environment.*;

import java.applet.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class RoadScenario extends JPanel{
    private BufferedImage png_vehicle_left, png_vehicle_right, png_pedestrian, png_road, png_pavement, png_shop, png_pedestrianleft;
    private AffineTransform m_affine = new AffineTransform();

    public RoadScenario()
    {
        setSize(RoadScenarioVar.j_ScenarioWidth, RoadScenarioVar.j_ScenarioHeight);
		try {
			png_vehicle_left    	= ImageIO.read(new File("res/vehicle_left.png"));
			png_vehicle_right 		= ImageIO.read(new File("res/vehicle_right.png"));
			png_road              		= ImageIO.read(new File("res/road_verticle.png"));
			png_shop 		          	= ImageIO.read(new File("res/shop.png")); 
			png_pedestrian 		= ImageIO.read(new File("res/pedestrian.png"));
			png_pedestrianleft 	= ImageIO.read(new File("res/pedestrian_left.png"));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g)
    {  
        Graphics2D g2d =(Graphics2D)g;
        //road
        m_affine.setToScale((double)1000 / (double)png_road.getWidth(), (double)900 / (double)png_road.getHeight());
        g2d.drawImage(png_road, new AffineTransformOp(m_affine, AffineTransformOp.TYPE_BILINEAR), -60, -200);
		g2d.drawImage(png_road, new AffineTransformOp(m_affine, AffineTransformOp.TYPE_BILINEAR), 830, -200);
		g2d.drawImage(png_road, new AffineTransformOp(m_affine, AffineTransformOp.TYPE_BILINEAR), 1720, -200);
		
		//shop
		/*m_affine.setToScale((double)100/(double)png_shop.getWidth(), (double)100/(double)png_shop.getHeight());
		g2d.drawImage(png_shop, new AffineTransformOp(m_affine, AffineTransformOp.TYPE_BILINEAR),450,400);*/
		
		//pavement
		/*m_affine.setToScale((double)201 / (double)png_pavement.getWidth(), (double)201 / (double)png_pavement.getHeight());
        g2d.drawImage(png_pavement, new AffineTransformOp(m_affine, AffineTransformOp.TYPE_BILINEAR), 200, 200);
        g2d.drawImage(png_road, new AffineTransformOP(m_affine, AffineTransformOp.TYPE_BILINEAR), 500, 250);*/
		
		//vehicle
		DrawVehicle(png_vehicle_left, RoadScenarioVar.g_vehicleMeter, g2d, RoadScenarioVar.g_vehicleSize);
		DrawVehicle(png_vehicle_right, RoadScenarioVar.g_vehicle2Meter, g2d, RoadScenarioVar.g_vehicle2Size);
		
		//pedestrian
		DrawPedestrian("pedestrian1", png_pedestrian, RoadScenarioVar.g_pedestrianMeter, g2d, RoadScenarioVar.g_pedestrianSize);
		DrawPedestrian("pedestrian2", png_pedestrianleft, RoadScenarioVar.g_pedestrianMeter, g2d, 60);
		DrawPedestrian("pedestrian3", png_pedestrian, RoadScenarioVar.g_pedestrianMeter, g2d, RoadScenarioVar.g_pedestrianSize);
		DrawPedestrian("pedestrian4", png_pedestrianleft, RoadScenarioVar.g_pedestrianMeter, g2d, 60);
		DrawPedestrian("pedestrian5", png_pedestrian, RoadScenarioVar.g_pedestrianMeter, g2d, RoadScenarioVar.g_pedestrianSize);
		DrawPedestrian("pedestrian6", png_pedestrianleft, RoadScenarioVar.g_pedestrianMeter, g2d, 60);
		DrawPedestrian("pedestrian7", png_pedestrian, RoadScenarioVar.g_pedestrianMeter, g2d, RoadScenarioVar.g_pedestrianSize);
		DrawPedestrian("pedestrian8", png_pedestrianleft, RoadScenarioVar.g_pedestrianMeter, g2d, 60);
		DrawPedestrian("pedestrian9", png_pedestrian, RoadScenarioVar.g_pedestrianMeter, g2d, RoadScenarioVar.g_pedestrianSize);
		DrawPedestrian("pedestrian10", png_pedestrianleft, RoadScenarioVar.g_pedestrianMeter, g2d, 60);
		
		//black line
		g2d.setColor(Color.black);
		Stroke stroke = new BasicStroke(4.0f);
		g2d.setStroke(stroke);
		g2d.drawLine(0,490,1980,490);
		
		//subheading
		Font f1 = new Font("Arial",Font.PLAIN,18);
		g2d.setFont(f1);
		g2d.drawString("Scenario",10,20);
		g2d.drawString("GUI",10,510);
    }

    public void VehicleMove(int _meter, int _size) //Vehicle meter [0,1980] Straight Line 
    {
        Point pt = new Point();
        if(_meter>=0 && _meter<=2010)
        {
            pt.x=2000 - _size/2 - (int)(_meter); 
            pt.y=212 - _size/2;
        }
		RoadScenarioVar.g_vehicle_x = pt.x;
		RoadScenarioVar.g_vehicle_y = pt.y;
    }
	
	public void Vehicle2Move(int _meter, int _size)
    {
        Point pt = new Point();
        if(_meter>=0 && _meter<=2010)
        {
            pt.x=-_size/2 +(int)(_meter)-100; 
            pt.y=338- _size/2;
        }
		RoadScenarioVar.g_vehicle2_x = pt.x;
		RoadScenarioVar.g_vehicle2_y = pt.y;
    }
	

    public void PedestrianMove(String ag, int px, int py)  //Pedestrian Move: 3 modes
    {
		if(RoadScenarioVar.m_Pause)								//Pause Button
		{	if(RoadScenarioVar.move_mode_ID==1)
				random_move(ag);										 //Move Mode 1: Random Movement
			if(RoadScenarioVar.move_mode_ID==2)			 // walk and cross
				walk_cross(ag);
			if(RoadScenarioVar.move_mode_ID==3)
				move_mode3(ag, px, py);								 //cross the road when you can
		}	
    }

    public void DrawVehicle(BufferedImage _Image, int _Meter, Graphics2D _g2d, int _Size)
    {
		if(_Image.equals(png_vehicle_left))
			VehicleMove(RoadScenarioVar.g_vehicleMeter, RoadScenarioVar.g_vehicleSize);
			Vehicle2Move(RoadScenarioVar.g_vehicle2Meter, RoadScenarioVar.g_vehicleSize);
			m_affine.setToScale((double)_Size / (double)_Image.getHeight(), (double)_Size / (double)_Image.getWidth());
		if(_Image.equals(png_vehicle_left))
			_g2d.drawImage(_Image, new AffineTransformOp(m_affine, AffineTransformOp.TYPE_BILINEAR), RoadScenarioVar.g_vehicle_x, RoadScenarioVar.g_vehicle_y);
		if(_Image.equals(png_vehicle_right))
			_g2d.drawImage(_Image, new AffineTransformOp(m_affine, AffineTransformOp.TYPE_BILINEAR), RoadScenarioVar.g_vehicle2_x, RoadScenarioVar.g_vehicle2_y);
    }	

    public void DrawPedestrian(String ag, BufferedImage _Image, int _meter, Graphics2D _g2d, int _Size)
    {	
		m_affine.setToScale((double)_Size/(double)_Image.getHeight(), (double)_Size/(double)_Image.getWidth());
		if(ag.equals("pedestrian1"))
		{
			PedestrianMove("pedestrian1",RoadScenarioVar.g_pedestrian1_x,RoadScenarioVar.g_pedestrian1_y); 
			_g2d.drawImage(_Image, new AffineTransformOp(m_affine, AffineTransformOp.TYPE_BILINEAR),RoadScenarioVar.g_pedestrian1_x,RoadScenarioVar.g_pedestrian1_y);
		}
		if(ag.equals("pedestrian2"))
		{
			PedestrianMove("pedestrian2",RoadScenarioVar.g_pedestrian2_x,RoadScenarioVar.g_pedestrian2_y); 
			_g2d.drawImage(_Image, new AffineTransformOp(m_affine, AffineTransformOp.TYPE_BILINEAR),RoadScenarioVar.g_pedestrian2_x,RoadScenarioVar.g_pedestrian2_y);
		}
		if(ag.equals("pedestrian3"))
		{
			PedestrianMove("pedestrian3",RoadScenarioVar.g_pedestrian3_x,RoadScenarioVar.g_pedestrian3_y); 
			_g2d.drawImage(_Image, new AffineTransformOp(m_affine, AffineTransformOp.TYPE_BILINEAR),RoadScenarioVar.g_pedestrian3_x,RoadScenarioVar.g_pedestrian3_y);
		}
		if(ag.equals("pedestrian4"))
		{	
			PedestrianMove("pedestrian4",RoadScenarioVar.g_pedestrian4_x,RoadScenarioVar.g_pedestrian4_y); 
			_g2d.drawImage(_Image, new AffineTransformOp(m_affine, AffineTransformOp.TYPE_BILINEAR),RoadScenarioVar.g_pedestrian4_x,RoadScenarioVar.g_pedestrian4_y);
		}
		if(ag.equals("pedestrian5"))
		{
			PedestrianMove("pedestrian5",RoadScenarioVar.g_pedestrian5_x,RoadScenarioVar.g_pedestrian5_y); 
			_g2d.drawImage(_Image, new AffineTransformOp(m_affine, AffineTransformOp.TYPE_BILINEAR),RoadScenarioVar.g_pedestrian5_x,RoadScenarioVar.g_pedestrian5_y);
		}
		if(ag.equals("pedestrian6"))
		{
			PedestrianMove("pedestrian6",RoadScenarioVar.g_pedestrian6_x,RoadScenarioVar.g_pedestrian6_y); 
			_g2d.drawImage(_Image, new AffineTransformOp(m_affine, AffineTransformOp.TYPE_BILINEAR),RoadScenarioVar.g_pedestrian6_x,RoadScenarioVar.g_pedestrian6_y);
		}
		if(ag.equals("pedestrian7"))
		{
			PedestrianMove("pedestrian7",RoadScenarioVar.g_pedestrian7_x,RoadScenarioVar.g_pedestrian7_y); 
			_g2d.drawImage(_Image, new AffineTransformOp(m_affine, AffineTransformOp.TYPE_BILINEAR),RoadScenarioVar.g_pedestrian7_x,RoadScenarioVar.g_pedestrian7_y);
		}
		if(ag.equals("pedestrian8"))
		{
			PedestrianMove("pedestrian8",RoadScenarioVar.g_pedestrian8_x,RoadScenarioVar.g_pedestrian8_y); 
			_g2d.drawImage(_Image, new AffineTransformOp(m_affine, AffineTransformOp.TYPE_BILINEAR),RoadScenarioVar.g_pedestrian8_x,RoadScenarioVar.g_pedestrian8_y);
		}
		if(ag.equals("pedestrian9"))
		{
			PedestrianMove("pedestrian9",RoadScenarioVar.g_pedestrian9_x,RoadScenarioVar.g_pedestrian9_y); 
			_g2d.drawImage(_Image, new AffineTransformOp(m_affine, AffineTransformOp.TYPE_BILINEAR),RoadScenarioVar.g_pedestrian9_x,RoadScenarioVar.g_pedestrian9_y);
		}
		if(ag.equals("pedestrian10"))
		{
			PedestrianMove("pedestrian10",RoadScenarioVar.g_pedestrian10_x,RoadScenarioVar.g_pedestrian10_y); 
			_g2d.drawImage(_Image, new AffineTransformOp(m_affine, AffineTransformOp.TYPE_BILINEAR),RoadScenarioVar.g_pedestrian10_x,RoadScenarioVar.g_pedestrian10_y);
		}
		
	}
	
	public void move1(String ag, int px, int py)   //Six actions in mode 2
	{
		if(ag.equals("pedestrian1"))
		{
			if(px<=1900&&px>=50)
			px=px+1*RoadScenarioVar.pedestrian1_parallel;			
			else
			{
			py++;
			RoadScenarioVar.pedestrian1_verticle++;
			if(RoadScenarioVar.pedestrian1_verticle>=100)
				{
					RoadScenarioVar.pedestrian1_parallel=RoadScenarioVar.pedestrian1_parallel*-1;
					px=1899;
					RoadScenarioVar.pedestrian1_verticle=0;
				}			
			}
			RoadScenarioVar.g_pedestrian1_x=px;
			RoadScenarioVar.g_pedestrian1_y=py;
		}
		
		if(ag.equals("pedestrian2"))
		{	
			if(px<=1900&&px>=50)
			px=px+1*RoadScenarioVar.pedestrian2_parallel;			
			else
			{
			py++;
			RoadScenarioVar.pedestrian2_verticle++;
			if(RoadScenarioVar.pedestrian2_verticle>=100)
				{
					RoadScenarioVar.pedestrian2_parallel=RoadScenarioVar.pedestrian2_parallel*-1;
					px=1899;
					RoadScenarioVar.pedestrian2_verticle=0;
				}			
			}
			RoadScenarioVar.g_pedestrian2_x=px;
			RoadScenarioVar.g_pedestrian2_y=py;
		}
		
		if(ag.equals("pedestrian3"))
		{	
			if(px<=1900&&px>=50)
			px=px+1*RoadScenarioVar.pedestrian3_parallel;			
			else
			{
			py++;
			RoadScenarioVar.pedestrian3_verticle++;
			if(RoadScenarioVar.pedestrian3_verticle>=100)
				{
					RoadScenarioVar.pedestrian3_parallel=RoadScenarioVar.pedestrian3_parallel*-1;
					px=1899;
					RoadScenarioVar.pedestrian3_verticle=0;
				}			
			}
			RoadScenarioVar.g_pedestrian3_x=px;
			RoadScenarioVar.g_pedestrian3_y=py;
		}
		
		if(ag.equals("pedestrian4"))
		{	
			if(px<=1900&&px>=50)
			px=px+1*RoadScenarioVar.pedestrian4_parallel;			
			else
			{
			py++;
			RoadScenarioVar.pedestrian4_verticle++;
			if(RoadScenarioVar.pedestrian4_verticle>=100)
				{
					RoadScenarioVar.pedestrian4_parallel=RoadScenarioVar.pedestrian4_parallel*-1;
					px=1899;
					RoadScenarioVar.pedestrian4_verticle=0;
				}			
			}
			RoadScenarioVar.g_pedestrian4_x=px;
			RoadScenarioVar.g_pedestrian4_y=py;
		}
		
		if(ag.equals("pedestrian5"))
		{	
			if(px<=1900&&px>=50)
			px=px+1*RoadScenarioVar.pedestrian5_parallel;			
			else
			{
			py++;
			RoadScenarioVar.pedestrian5_verticle++;
			if(RoadScenarioVar.pedestrian5_verticle>=100)
				{
					RoadScenarioVar.pedestrian5_parallel=RoadScenarioVar.pedestrian5_parallel*-1;
					px=1899;
					RoadScenarioVar.pedestrian5_verticle=0;
				}			
			}
			RoadScenarioVar.g_pedestrian5_x=px;
			RoadScenarioVar.g_pedestrian5_y=py;
		}
		
		if(ag.equals("pedestrian6"))
		{	
			if(px<=1900&&px>=50)
			px=px+1*RoadScenarioVar.pedestrian6_parallel;			
			else
			{
			py++;
			RoadScenarioVar.pedestrian6_verticle++;
			if(RoadScenarioVar.pedestrian6_verticle>=100)
				{
					RoadScenarioVar.pedestrian6_parallel=RoadScenarioVar.pedestrian6_parallel*-1;
					px=1899;
					RoadScenarioVar.pedestrian6_verticle=0;
				}			
			}
			RoadScenarioVar.g_pedestrian6_x=px;
			RoadScenarioVar.g_pedestrian6_y=py;
		}
	}
	
	public void move2(String ag, int px, int py)
	{			
		if(ag.equals("pedestrian1"))
		{
			if(py<=430&&py>=10)
			py=py+1*RoadScenarioVar.pedestrian1_parallel;			
		else
		{
			px++;
			RoadScenarioVar.pedestrian1_verticle++;
			if(RoadScenarioVar.pedestrian1_verticle>=100)
				{
					RoadScenarioVar.pedestrian1_parallel=RoadScenarioVar.pedestrian1_parallel*-1;
					py=429;
					RoadScenarioVar.pedestrian1_verticle=0;
				}			
		}
			RoadScenarioVar.g_pedestrian1_x=px;
			RoadScenarioVar.g_pedestrian1_y=py;
		}	
		if(ag.equals("pedestrian2"))
		{
		if(py<=430&&py>=10)
			py=py+1*RoadScenarioVar.pedestrian2_parallel;			
		else
		{
			px++;
			RoadScenarioVar.pedestrian2_verticle++;
			if(RoadScenarioVar.pedestrian2_verticle>=100)
				{
					RoadScenarioVar.pedestrian2_parallel=RoadScenarioVar.pedestrian2_parallel*-1;
					py=429;
					RoadScenarioVar.pedestrian2_verticle=0;
				}			
		}
			RoadScenarioVar.g_pedestrian2_x=px;
			RoadScenarioVar.g_pedestrian2_y=py;
		}
		if(ag.equals("pedestrian3"))
		{
		if(py<=430&&py>=10)
			py=py+1*RoadScenarioVar.pedestrian3_parallel;			
		else
		{
			px++;
			RoadScenarioVar.pedestrian3_verticle++;
			if(RoadScenarioVar.pedestrian3_verticle>=100)
				{
					RoadScenarioVar.pedestrian3_parallel=RoadScenarioVar.pedestrian3_parallel*-1;
					py=429;
					RoadScenarioVar.pedestrian3_verticle=0;
				}			
		}
			RoadScenarioVar.g_pedestrian3_x=px;
			RoadScenarioVar.g_pedestrian3_y=py;
		}	
		if(ag.equals("pedestrian4"))
		{
		if(py<=430&&py>=10)
			py=py+1*RoadScenarioVar.pedestrian4_parallel;			
		else
		{
			px++;
			RoadScenarioVar.pedestrian4_verticle++;
			if(RoadScenarioVar.pedestrian4_verticle>=100)
				{
					RoadScenarioVar.pedestrian4_parallel=RoadScenarioVar.pedestrian4_parallel*-1;
					py=429;
					RoadScenarioVar.pedestrian4_verticle=0;
				}			
		}
			RoadScenarioVar.g_pedestrian4_x=px;
			RoadScenarioVar.g_pedestrian4_y=py;
		}
		if(ag.equals("pedestrian5"))
		{
		if(py<=430&&py>=10)
			py=py+1*RoadScenarioVar.pedestrian5_parallel;			
		else
		{
			px++;
			RoadScenarioVar.pedestrian5_verticle++;
			if(RoadScenarioVar.pedestrian5_verticle>=100)
				{
					RoadScenarioVar.pedestrian5_parallel=RoadScenarioVar.pedestrian5_parallel*-1;
					py=429;
					RoadScenarioVar.pedestrian5_verticle=0;
				}			
		}
			RoadScenarioVar.g_pedestrian5_x=px;
			RoadScenarioVar.g_pedestrian5_y=py;
		}	
		if(ag.equals("pedestrian6"))
		{
		if(py<=430&&py>=50)
			py=py+1*RoadScenarioVar.pedestrian6_parallel;			
		else
		{
			px++;
			RoadScenarioVar.pedestrian6_verticle++;
			if(RoadScenarioVar.pedestrian6_verticle>=100)
				{
					RoadScenarioVar.pedestrian6_parallel=RoadScenarioVar.pedestrian6_parallel*-1;
					py=429;
					RoadScenarioVar.pedestrian6_verticle=0;
				}			
		}
			RoadScenarioVar.g_pedestrian6_x=px;
			RoadScenarioVar.g_pedestrian6_y=py;
		}
	}
	
	public void move3(String ag, int px, int py)
	{	
		if(ag.equals("pedestrian1"))
		{			
			if(px<=1900&&px>=50)
			px=px+(-1)*RoadScenarioVar.pedestrian1_parallel;			
		else
		{
			py++;
			RoadScenarioVar.pedestrian1_verticle++;
			if(RoadScenarioVar.pedestrian1_verticle>=100)
				{
					RoadScenarioVar.pedestrian1_parallel=RoadScenarioVar.pedestrian1_parallel*-1;
					px=51;
					RoadScenarioVar.pedestrian1_verticle=0;
				}			
		}
			RoadScenarioVar.g_pedestrian1_x=px;
			RoadScenarioVar.g_pedestrian1_y=py;
		}	
		if(ag.equals("pedestrian2"))
		{
			if(px<=1900&&px>=50)
			px=px+(-1)*RoadScenarioVar.pedestrian2_parallel;			
		else
		{
			py++;
			RoadScenarioVar.pedestrian2_verticle++;
			if(RoadScenarioVar.pedestrian2_verticle>=100)
				{
					RoadScenarioVar.pedestrian2_parallel=RoadScenarioVar.pedestrian2_parallel*-1;
					px=51;
					RoadScenarioVar.pedestrian2_verticle=0;
				}			
		}
			RoadScenarioVar.g_pedestrian2_x=px;
			RoadScenarioVar.g_pedestrian2_y=py;
		}
		if(ag.equals("pedestrian3"))
		{
			if(px<=1900&&px>=50)
			px=px+(-1)*RoadScenarioVar.pedestrian3_parallel;			
		else
		{
			py++;
			RoadScenarioVar.pedestrian3_verticle++;
			if(RoadScenarioVar.pedestrian3_verticle>=100)
				{
					RoadScenarioVar.pedestrian3_parallel=RoadScenarioVar.pedestrian3_parallel*-1;
					px=51;
					RoadScenarioVar.pedestrian3_verticle=0;
				}			
		}
			RoadScenarioVar.g_pedestrian3_x=px;
			RoadScenarioVar.g_pedestrian3_y=py;
		}	
		if(ag.equals("pedestrian4"))
		{
			if(px<=1900&&px>=50)
			px=px+(-1)*RoadScenarioVar.pedestrian4_parallel;			
		else
		{
			py++;
			RoadScenarioVar.pedestrian4_verticle++;
			if(RoadScenarioVar.pedestrian4_verticle>=100)
				{
					RoadScenarioVar.pedestrian4_parallel=RoadScenarioVar.pedestrian4_parallel*-1;
					px=51;
					RoadScenarioVar.pedestrian4_verticle=0;
				}			
		}
			RoadScenarioVar.g_pedestrian4_x=px;
			RoadScenarioVar.g_pedestrian4_y=py;
		}
		if(ag.equals("pedestrian5"))
		{
			if(px<=1900&&px>=50)
			px=px+(-1)*RoadScenarioVar.pedestrian5_parallel;			
		else
		{
			py++;
			RoadScenarioVar.pedestrian5_verticle++;
			if(RoadScenarioVar.pedestrian5_verticle>=100)
				{
					RoadScenarioVar.pedestrian5_parallel=RoadScenarioVar.pedestrian5_parallel*-1;
					px=51;
					RoadScenarioVar.pedestrian5_verticle=0;
				}			
		}
			RoadScenarioVar.g_pedestrian5_x=px;
			RoadScenarioVar.g_pedestrian5_y=py;
		}	
		if(ag.equals("pedestrian6"))
		{
			if(px<=1900&&px>=50)
			px=px+(-1)*RoadScenarioVar.pedestrian6_parallel;			
		else
		{
			py++;
			RoadScenarioVar.pedestrian6_verticle++;
			if(RoadScenarioVar.pedestrian6_verticle>=100)
				{
					RoadScenarioVar.pedestrian6_parallel=RoadScenarioVar.pedestrian6_parallel*-1;
					px=51;
					RoadScenarioVar.pedestrian6_verticle=0;
				}			
		}
			RoadScenarioVar.g_pedestrian6_x=px;
			RoadScenarioVar.g_pedestrian6_y=py;
		}
	}
	
		public void move4(String ag, int px, int py)
	{	
		if(ag.equals("pedestrian1"))
		{
			if(px<=1900&&px>=50)
			px=px+1*RoadScenarioVar.pedestrian1_parallel;			
		else
		{
			py--;
			RoadScenarioVar.pedestrian1_verticle++;
			if(RoadScenarioVar.pedestrian1_verticle>=100)
				{	RoadScenarioVar.pedestrian1_parallel=RoadScenarioVar.pedestrian1_parallel*-1;
					px=1899;
					RoadScenarioVar.pedestrian1_verticle=0;
				}			
		}
			RoadScenarioVar.g_pedestrian1_x=px;
			RoadScenarioVar.g_pedestrian1_y=py;
		}	
		if(ag.equals("pedestrian2"))
		{
			if(px<=1900&&px>=50)
				px=px+1*RoadScenarioVar.pedestrian2_parallel;			
			else
		{
				py--;
				RoadScenarioVar.pedestrian2_verticle++;
			if(RoadScenarioVar.pedestrian2_verticle>=100)
				{
					RoadScenarioVar.pedestrian2_parallel=RoadScenarioVar.pedestrian2_parallel*-1;
					px=1899;
					RoadScenarioVar.pedestrian2_verticle=0;
				}			
		}
			RoadScenarioVar.g_pedestrian2_x=px;
			RoadScenarioVar.g_pedestrian2_y=py;
		}
		if(ag.equals("pedestrian3"))
		{
			if(px<=1900&&px>=50)
			px=px+1*RoadScenarioVar.pedestrian3_parallel;			
		else
		{
			py--;
			RoadScenarioVar.pedestrian3_verticle++;
			if(RoadScenarioVar.pedestrian3_verticle>=100)
				{
					RoadScenarioVar.pedestrian3_parallel=RoadScenarioVar.pedestrian3_parallel*-1;
					px=1899;
					RoadScenarioVar.pedestrian3_verticle=0;
				}			
		}
			RoadScenarioVar.g_pedestrian3_x=px;
			RoadScenarioVar.g_pedestrian3_y=py;
		}	
		if(ag.equals("pedestrian4"))
		{
			if(px<=1900&&px>=50)
			px=px+1*RoadScenarioVar.pedestrian4_parallel;			
		else
		{
			py--;
			RoadScenarioVar.pedestrian4_verticle++;
			if(RoadScenarioVar.pedestrian4_verticle>=100)
				{
					RoadScenarioVar.pedestrian4_parallel=RoadScenarioVar.pedestrian4_parallel*-1;
					px=1899;
					RoadScenarioVar.pedestrian4_verticle=0;
				}			
		}
			RoadScenarioVar.g_pedestrian4_x=px;
			RoadScenarioVar.g_pedestrian4_y=py;
		}
		if(ag.equals("pedestrian5"))
		{
			if(px<=1900&&px>=50)
			px=px+1*RoadScenarioVar.pedestrian5_parallel;			
		else
		{
			py--;
			RoadScenarioVar.pedestrian5_verticle++;
			if(RoadScenarioVar.pedestrian5_verticle>=100)
				{
					RoadScenarioVar.pedestrian5_parallel=RoadScenarioVar.pedestrian5_parallel*-1;
					px=1899;
					RoadScenarioVar.pedestrian5_verticle=0;
				}			
		}
			RoadScenarioVar.g_pedestrian5_x=px;
			RoadScenarioVar.g_pedestrian5_y=py;
		}	
		if(ag.equals("pedestrian6"))
		{
			if(px<=1900&&px>=50)
			px=px+1*RoadScenarioVar.pedestrian6_parallel;			
		else
		{
			py--;
			RoadScenarioVar.pedestrian6_verticle++;
			if(RoadScenarioVar.pedestrian6_verticle>=100)
				{
					RoadScenarioVar.pedestrian6_parallel=RoadScenarioVar.pedestrian6_parallel*-1;
					px=1899;
					RoadScenarioVar.pedestrian6_verticle=0;
				}			
		}
			RoadScenarioVar.g_pedestrian6_x=px;
			RoadScenarioVar.g_pedestrian6_y=py;
		}
	}
	
		public void move5(String ag, int px, int py)
	{	
		if(ag.equals("pedestrian1"))
		{
			if(py<=430&&py>=10)
			py=py+(-1)*RoadScenarioVar.pedestrian1_parallel;			
		else
		{
			px++;
			RoadScenarioVar.pedestrian1_verticle++;
			if(RoadScenarioVar.pedestrian1_verticle>=100)
				{
					RoadScenarioVar.pedestrian1_parallel=RoadScenarioVar.pedestrian1_parallel*-1;
					py=11;
					RoadScenarioVar.pedestrian1_verticle=0;
				}			
		}
			RoadScenarioVar.g_pedestrian1_x=px;
			RoadScenarioVar.g_pedestrian1_y=py;
		}	
		if(ag.equals("pedestrian2"))
		{
			if(py<=430&&py>=10)
			py=py+(-1)*RoadScenarioVar.pedestrian2_parallel;			
		else
		{
			px++;
			RoadScenarioVar.pedestrian2_verticle++;
			if(RoadScenarioVar.pedestrian2_verticle>=100)
				{
					RoadScenarioVar.pedestrian2_parallel=RoadScenarioVar.pedestrian2_parallel*-1;
					py=11;
					RoadScenarioVar.pedestrian2_verticle=0;
				}			
		}
			RoadScenarioVar.g_pedestrian2_x=px;
			RoadScenarioVar.g_pedestrian2_y=py;
		}
		if(ag.equals("pedestrian3"))
		{
			if(py<=430&&py>=10)
			py=py+(-1)*RoadScenarioVar.pedestrian3_parallel;			
		else
		{
			px++;
			RoadScenarioVar.pedestrian3_verticle++;
			if(RoadScenarioVar.pedestrian3_verticle>=100)
				{
					RoadScenarioVar.pedestrian3_parallel=RoadScenarioVar.pedestrian3_parallel*-1;
					py=11;
					RoadScenarioVar.pedestrian3_verticle=0;
				}			
		}
			RoadScenarioVar.g_pedestrian3_x=px;
			RoadScenarioVar.g_pedestrian3_y=py;
		}	
		if(ag.equals("pedestrian4"))
		{
			if(py<=430&&py>=10)
			py=py+(-1)*RoadScenarioVar.pedestrian4_parallel;			
		else
		{
			px++;
			RoadScenarioVar.pedestrian4_verticle++;
			if(RoadScenarioVar.pedestrian4_verticle>=100)
				{
					RoadScenarioVar.pedestrian4_parallel=RoadScenarioVar.pedestrian4_parallel*-1;
					py=11;
					RoadScenarioVar.pedestrian4_verticle=0;
				}			
		}
			RoadScenarioVar.g_pedestrian4_x=px;
			RoadScenarioVar.g_pedestrian4_y=py;
		}
		if(ag.equals("pedestrian5"))
		{
			if(py<=430&&py>=10)
			py=py+(-1)*RoadScenarioVar.pedestrian5_parallel;			
		else
		{
			px++;
			RoadScenarioVar.pedestrian5_verticle++;
			if(RoadScenarioVar.pedestrian5_verticle>=100)
				{
					RoadScenarioVar.pedestrian5_parallel=RoadScenarioVar.pedestrian5_parallel*-1;
					py=11;
					RoadScenarioVar.pedestrian5_verticle=0;
				}			
		}
			RoadScenarioVar.g_pedestrian5_x=px;
			RoadScenarioVar.g_pedestrian5_y=py;
		}	
		if(ag.equals("pedestrian6"))
		{
			if(py<=430&&py>=10)
			py=py+(-1)*RoadScenarioVar.pedestrian6_parallel;			
		else
		{
			px++;
			RoadScenarioVar.pedestrian6_verticle++;
			if(RoadScenarioVar.pedestrian6_verticle>=100)
				{
					RoadScenarioVar.pedestrian6_parallel=RoadScenarioVar.pedestrian6_parallel*-1;
					py=11;
					RoadScenarioVar.pedestrian6_verticle=0;
				}			
		}
			RoadScenarioVar.g_pedestrian6_x=px;
			RoadScenarioVar.g_pedestrian6_y=py;
		}
	}
	
		public void move6(String ag, int px, int py)
	{	
		if(ag.equals("pedestrian1"))
		{
			if(px<=1900&&px>=50)
			px=px+(-1)*RoadScenarioVar.pedestrian1_parallel;			
		else
		{
			py--;
			RoadScenarioVar.pedestrian1_verticle++;
			if(RoadScenarioVar.pedestrian1_verticle>=100)
				{
					RoadScenarioVar.pedestrian1_parallel=RoadScenarioVar.pedestrian1_parallel*-1;
					px=51;
					RoadScenarioVar.pedestrian1_verticle=0;
				}			
		}
			RoadScenarioVar.g_pedestrian1_x=px;
			RoadScenarioVar.g_pedestrian1_y=py;
		}	
		if(ag.equals("pedestrian2"))
		{
			if(px<=1900&&px>=50)
			px=px+(-1)*RoadScenarioVar.pedestrian2_parallel;			
		else
		{
			py--;
			RoadScenarioVar.pedestrian2_verticle++;
			if(RoadScenarioVar.pedestrian2_verticle>=100)
				{
					RoadScenarioVar.pedestrian2_parallel=RoadScenarioVar.pedestrian2_parallel*-1;
					px=51;
					RoadScenarioVar.pedestrian2_verticle=0;
				}			
		}
			RoadScenarioVar.g_pedestrian2_x=px;
			RoadScenarioVar.g_pedestrian2_y=py;
		}
		if(ag.equals("pedestrian3"))
		{
			if(px<=1900&&px>=50)
			px=px+(-1)*RoadScenarioVar.pedestrian3_parallel;			
		else
		{
			py--;
			RoadScenarioVar.pedestrian3_verticle++;
			if(RoadScenarioVar.pedestrian3_verticle>=100)
				{
					RoadScenarioVar.pedestrian3_parallel=RoadScenarioVar.pedestrian3_parallel*-1;
					px=51;
					RoadScenarioVar.pedestrian3_verticle=0;
				}			
		}
			RoadScenarioVar.g_pedestrian3_x=px;
			RoadScenarioVar.g_pedestrian3_y=py;
		}	
		if(ag.equals("pedestrian4"))
		{
			if(px<=1900&&px>=50)
			px=px+(-1)*RoadScenarioVar.pedestrian4_parallel;			
		else
		{
			py--;
			RoadScenarioVar.pedestrian4_verticle++;
			if(RoadScenarioVar.pedestrian4_verticle>=100)
				{
					RoadScenarioVar.pedestrian4_parallel=RoadScenarioVar.pedestrian4_parallel*-1;
					px=51;
					RoadScenarioVar.pedestrian4_verticle=0;
				}			
		}
			RoadScenarioVar.g_pedestrian4_x=px;
			RoadScenarioVar.g_pedestrian4_y=py;
		}
		if(ag.equals("pedestrian5"))
		{
			if(px<=1900&&px>=50)
			px=px+(-1)*RoadScenarioVar.pedestrian5_parallel;			
		else
		{
			py--;
			RoadScenarioVar.pedestrian5_verticle++;
			if(RoadScenarioVar.pedestrian5_verticle>=100)
				{
					RoadScenarioVar.pedestrian5_parallel=RoadScenarioVar.pedestrian5_parallel*-1;
					px=51;
					RoadScenarioVar.pedestrian5_verticle=0;
				}			
		}
			RoadScenarioVar.g_pedestrian5_x=px;
			RoadScenarioVar.g_pedestrian5_y=py;
		}	
		if(ag.equals("pedestrian6"))
		{
			if(px<=1900&&px>=50)
			px=px+(-1)*RoadScenarioVar.pedestrian6_parallel;			
		else
		{
			py--;
			RoadScenarioVar.pedestrian6_verticle++;
			if(RoadScenarioVar.pedestrian6_verticle>=100)
				{
					RoadScenarioVar.pedestrian6_parallel=RoadScenarioVar.pedestrian6_parallel*-1;
					px=51;
					RoadScenarioVar.pedestrian6_verticle=0;
				}			
		}
			RoadScenarioVar.g_pedestrian6_x=px;
			RoadScenarioVar.g_pedestrian6_y=py;
		}
	}
	
	public void random_move(String ag) // Moving in a random direction
	{
		if(ag.equals("pedestrian1"))
		{	int p1x=(int)(Math.random()*10-5);
			int p1y=(int)(Math.random()*10-5);
			RoadScenarioVar.g_pedestrian1_x=p1x+RoadScenarioVar.g_pedestrian1_x;
			RoadScenarioVar.g_pedestrian1_y=p1y+RoadScenarioVar.g_pedestrian1_y;
		}
		if(ag.equals("pedestrian2"))
		{
			int p2x=(int)(10-Math.random()*20);
			int p2y=(int)(10-Math.random()*20);
			RoadScenarioVar.g_pedestrian2_x=p2x+RoadScenarioVar.g_pedestrian2_x;
			RoadScenarioVar.g_pedestrian2_y=p2y+RoadScenarioVar.g_pedestrian2_y;}
		if(ag.equals("pedestrian3"))
		{
			int p3x=(int)(10-Math.random()*20);
			int p3y=(int)(10-Math.random()*20);
			RoadScenarioVar.g_pedestrian3_x=p3x+RoadScenarioVar.g_pedestrian3_x;
			RoadScenarioVar.g_pedestrian3_y=p3y+RoadScenarioVar.g_pedestrian3_y;}
		if(ag.equals("pedestrian4"))
		{
			int p4x=(int)(10-Math.random()*20);
			int p4y=(int)(10-Math.random()*20);
			RoadScenarioVar.g_pedestrian4_x=p4x+RoadScenarioVar.g_pedestrian4_x;
			RoadScenarioVar.g_pedestrian4_y=p4y+RoadScenarioVar.g_pedestrian4_y;}
		if(ag.equals("pedestrian5"))
		{
			int p5x=(int)(10-Math.random()*20);
			int p5y=(int)(10-Math.random()*20);
			RoadScenarioVar.g_pedestrian5_x=p5x+RoadScenarioVar.g_pedestrian5_x;
			RoadScenarioVar.g_pedestrian5_y=p5y+RoadScenarioVar.g_pedestrian5_y;}
		if(ag.equals("pedestrian6"))
		{
			int p6x=(int)(10-Math.random()*20);
			int p6y=(int)(10-Math.random()*20);
			RoadScenarioVar.g_pedestrian6_x=p6x+RoadScenarioVar.g_pedestrian6_x;
			RoadScenarioVar.g_pedestrian6_y=p6y+RoadScenarioVar.g_pedestrian6_y;
		}
		if(RoadScenarioVar.g_pedestrian1_x>=1900)
			RoadScenarioVar.g_pedestrian1_x=1895;
		if(RoadScenarioVar.g_pedestrian2_x>=1900)
			RoadScenarioVar.g_pedestrian2_x=1895;
		if(RoadScenarioVar.g_pedestrian3_x>=1900)
			RoadScenarioVar.g_pedestrian3_x=1895;
		if(RoadScenarioVar.g_pedestrian4_x>=1900)
			RoadScenarioVar.g_pedestrian4_x=1895;
		if(RoadScenarioVar.g_pedestrian5_x>=1900)
			RoadScenarioVar.g_pedestrian5_x=1895;
		if(RoadScenarioVar.g_pedestrian6_x>=1900)
			RoadScenarioVar.g_pedestrian6_x=1895;
		if(RoadScenarioVar.g_pedestrian1_x<=50)
			RoadScenarioVar.g_pedestrian1_x=55;
		if(RoadScenarioVar.g_pedestrian2_x<=50)
			RoadScenarioVar.g_pedestrian2_x=55;
		if(RoadScenarioVar.g_pedestrian3_x<=50)
			RoadScenarioVar.g_pedestrian3_x=55;
		if(RoadScenarioVar.g_pedestrian4_x<=50)
			RoadScenarioVar.g_pedestrian4_x=55;
		if(RoadScenarioVar.g_pedestrian5_x<=50)
			RoadScenarioVar.g_pedestrian5_x=55;
		if(RoadScenarioVar.g_pedestrian6_x<=50)
			RoadScenarioVar.g_pedestrian6_x=55;
		if(RoadScenarioVar.g_pedestrian1_y>=430)
			RoadScenarioVar.g_pedestrian1_y=RoadScenarioVar.g_pedestrian1_y-5;
		if(RoadScenarioVar.g_pedestrian2_y>=430)
			RoadScenarioVar.g_pedestrian2_y=RoadScenarioVar.g_pedestrian2_y-5;
		if(RoadScenarioVar.g_pedestrian3_y>=430)
			RoadScenarioVar.g_pedestrian3_y=RoadScenarioVar.g_pedestrian3_y-5;
		if(RoadScenarioVar.g_pedestrian4_y>=430)
			RoadScenarioVar.g_pedestrian4_y=RoadScenarioVar.g_pedestrian4_y-5;
		if(RoadScenarioVar.g_pedestrian5_y>=430)
			RoadScenarioVar.g_pedestrian5_y=RoadScenarioVar.g_pedestrian5_y-5;
		if(RoadScenarioVar.g_pedestrian6_y>=430)
			RoadScenarioVar.g_pedestrian6_y=RoadScenarioVar.g_pedestrian6_y-5;
		if(RoadScenarioVar.g_pedestrian1_y<=10)
			RoadScenarioVar.g_pedestrian1_y=RoadScenarioVar.g_pedestrian1_y+5;
		if(RoadScenarioVar.g_pedestrian2_y<=10)
			RoadScenarioVar.g_pedestrian2_y=RoadScenarioVar.g_pedestrian2_y+5;
		if(RoadScenarioVar.g_pedestrian3_y<=10)
			RoadScenarioVar.g_pedestrian3_y=RoadScenarioVar.g_pedestrian3_y+5;
		if(RoadScenarioVar.g_pedestrian4_y<=10)
			RoadScenarioVar.g_pedestrian4_y=RoadScenarioVar.g_pedestrian4_y+5;
		if(RoadScenarioVar.g_pedestrian5_y<=10)
			RoadScenarioVar.g_pedestrian5_y=RoadScenarioVar.g_pedestrian5_y+5;
		if(RoadScenarioVar.g_pedestrian6_y<=10)
			RoadScenarioVar.g_pedestrian6_y=RoadScenarioVar.g_pedestrian6_y+5;
	}
	
	public void move_mode3(String ag, int px, int py)
	{	
		int p1=0, p2=0, p3=0, p4=0, p5=0, p6=0;
		if(ag.equals("pedestrian1"))
			{
			if(px>=50&&px<=1900)
					{	
						px=px+RoadScenarioVar.pedestrian1_parallel;
						if(py<=330&&(((((RoadScenarioVar.g_vehicle_x-px)>=201&&((RoadScenarioVar.g_vehicle_x-px)<=401))))||(RoadScenarioVar.g_vehicle_x-px)<=0)&&(((-RoadScenarioVar.g_vehicle2_x+px)>=201)||(RoadScenarioVar.g_vehicle2_x-px)>=0))
								p1=1;
						if(p1==1)
									py=py+2;
					}
			else
					{	
						if(px<=50)
							px=51;
						if(px>=1900)
							px=1899;
						RoadScenarioVar.pedestrian1_parallel=RoadScenarioVar.pedestrian1_parallel*-1;
					}
			RoadScenarioVar.g_pedestrian1_x=px;
			RoadScenarioVar.g_pedestrian1_y=py;
			}
		if(ag.equals("pedestrian2"))
			{
			if(px>=50&&px<=1900)
					{	
						px=px+RoadScenarioVar.pedestrian2_parallel;
						if(py<=330&&(((((RoadScenarioVar.g_vehicle_x-px)>=201&&((RoadScenarioVar.g_vehicle_x-px)<=501))))||(RoadScenarioVar.g_vehicle_x-px)<=0)&&(((-RoadScenarioVar.g_vehicle2_x+px)>=201)||(RoadScenarioVar.g_vehicle2_x-px)>=0))
							{
									p2=1;
							}
						if(p2==1)
								{
									py=py+2;
								}
					}
		else
					{	
						if(px<=50)
							px=51;
						if(px>=1900)
							px=1899;
						RoadScenarioVar.pedestrian2_parallel=RoadScenarioVar.pedestrian2_parallel*-1;
					}
			RoadScenarioVar.g_pedestrian2_x=px;
			RoadScenarioVar.g_pedestrian2_y=py;
			}
		if(ag.equals("pedestrian3"))
			{
			if(px>=50&&px<=1900)
					{	
						px=px+RoadScenarioVar.pedestrian3_parallel;
						if(py<=330&&(((((RoadScenarioVar.g_vehicle_x-px)>=201&&((RoadScenarioVar.g_vehicle_x-px)<=501))))||(RoadScenarioVar.g_vehicle_x-px)<=0)&&(((-RoadScenarioVar.g_vehicle2_x+px)>=201)||(RoadScenarioVar.g_vehicle2_x-px)>=0))
							{
									p3=1;
							}
							if(p3==1)
								{
									py=py+2;
								}
					}
		else
					{	
						if(px<=50)
							px=51;
						if(px>=1900)
							px=1899;
						RoadScenarioVar.pedestrian3_parallel=RoadScenarioVar.pedestrian3_parallel*-1;
					}
			RoadScenarioVar.g_pedestrian3_x=px;
			RoadScenarioVar.g_pedestrian3_y=py;
			}
		if(ag.equals("pedestrian4"))
			{
		if(px>=50&&px<=1900)
					{
					if(py<=330&&(((((RoadScenarioVar.g_vehicle_x-px)>=201&&((RoadScenarioVar.g_vehicle_x-px)<=501))))||(RoadScenarioVar.g_vehicle_x-px)<=0)&&(((-RoadScenarioVar.g_vehicle2_x+px)>=201)||(RoadScenarioVar.g_vehicle2_x-px)>=0))
							{
								p4=1;
							if(p4==1)
									py=py+2;
								}
					else
						px=px+RoadScenarioVar.pedestrian4_parallel;
					}
		else
					{	
						if(px<=50)
							px=51;
						if(px>=1900)
							px=1899;
						RoadScenarioVar.pedestrian4_parallel=RoadScenarioVar.pedestrian4_parallel*-1;
					}
			RoadScenarioVar.g_pedestrian4_x=px;
			RoadScenarioVar.g_pedestrian4_y=py;
			}
		if(ag.equals("pedestrian5"))
			{
						if(px>=50&&px<=1900)
					{	
						if(py<=330&&(((((RoadScenarioVar.g_vehicle_x-px)>=201&&((RoadScenarioVar.g_vehicle_x-px)<=501))))||(RoadScenarioVar.g_vehicle_x-px)<=0)&&(((-RoadScenarioVar.g_vehicle2_x+px)>=201)||(RoadScenarioVar.g_vehicle2_x-px)>=0))
							{
									p5=1;
							if(p5==1)
									py=py+2;
							}
						else
							px=px+RoadScenarioVar.pedestrian5_parallel;
					}
		else
					{	
						if(px<=50)
							px=51;
						if(px>=1900)
							px=1899;
						RoadScenarioVar.pedestrian5_parallel=RoadScenarioVar.pedestrian5_parallel*-1;
					}
			RoadScenarioVar.g_pedestrian5_x=px;
			RoadScenarioVar.g_pedestrian5_y=py;
			}
		if(ag.equals("pedestrian6"))
			{
			if(px>=50&&px<=1900)
					{	
						if(py<=80&&(((((RoadScenarioVar.g_vehicle_x-px)>=201&&((RoadScenarioVar.g_vehicle_x-px)<=501))))||(RoadScenarioVar.g_vehicle_x-px)<=0)&&(((-RoadScenarioVar.g_vehicle2_x+px)>=201)||(RoadScenarioVar.g_vehicle2_x-px)>=0))
							{
									p6=1;
							if(p6==1)
									py=py+2;
								}
						else
							px=px+RoadScenarioVar.pedestrian6_parallel;
					}
		else
					{	
						if(px<=50)
							px=51;
						if(px>=1900)
							px=1899;
						RoadScenarioVar.pedestrian6_parallel=RoadScenarioVar.pedestrian6_parallel*-1;
					}
			RoadScenarioVar.g_pedestrian6_x=px;
			RoadScenarioVar.g_pedestrian6_y=py;
			}
	}
	
	public void walk_cross(String ag)
	{
		 if (ag.equals("pedestrian1"))
				{
				if(RoadScenarioVar.move_belief1==1)
					move1("pedestrian1", RoadScenarioVar.g_pedestrian1_x, RoadScenarioVar.g_pedestrian1_y);
				if(RoadScenarioVar.move_belief1==2)
					move2("pedestrian1", RoadScenarioVar.g_pedestrian1_x, RoadScenarioVar.g_pedestrian1_y);
				if(RoadScenarioVar.move_belief1==3)
					move3("pedestrian1", RoadScenarioVar.g_pedestrian1_x, RoadScenarioVar.g_pedestrian1_y);
				if(RoadScenarioVar.move_belief1==4)
					move4("pedestrian1", RoadScenarioVar.g_pedestrian1_x, RoadScenarioVar.g_pedestrian1_y);
				if(RoadScenarioVar.move_belief1==5)
					move5("pedestrian1", RoadScenarioVar.g_pedestrian1_x, RoadScenarioVar.g_pedestrian1_y);
				if(RoadScenarioVar.move_belief1==6)
					move6("pedestrian1", RoadScenarioVar.g_pedestrian1_x, RoadScenarioVar.g_pedestrian1_y);
				}	
		else if(ag.equals("pedestrian2"))
				{	
					if(RoadScenarioVar.move_belief2==1)
						move1("pedestrian2", RoadScenarioVar.g_pedestrian2_x, RoadScenarioVar.g_pedestrian2_y);
					if(RoadScenarioVar.move_belief2==2)
						move2("pedestrian2", RoadScenarioVar.g_pedestrian2_x, RoadScenarioVar.g_pedestrian2_y);
					if(RoadScenarioVar.move_belief2==3)
						move3("pedestrian2", RoadScenarioVar.g_pedestrian2_x, RoadScenarioVar.g_pedestrian2_y);
					if(RoadScenarioVar.move_belief2==4)
						move4("pedestrian2", RoadScenarioVar.g_pedestrian2_x, RoadScenarioVar.g_pedestrian2_y);
					if(RoadScenarioVar.move_belief2==5)
						move5("pedestrian2", RoadScenarioVar.g_pedestrian2_x, RoadScenarioVar.g_pedestrian2_y);
					if(RoadScenarioVar.move_belief2==6)
						move6("pedestrian2", RoadScenarioVar.g_pedestrian2_x, RoadScenarioVar.g_pedestrian2_y);
				}

			else if (ag.equals("pedestrian3"))
			{
			if(RoadScenarioVar.move_belief3==1)
				move1("pedestrian3", RoadScenarioVar.g_pedestrian3_x, RoadScenarioVar.g_pedestrian3_y);
			if(RoadScenarioVar.move_belief3==2)
				move2("pedestrian3", RoadScenarioVar.g_pedestrian3_x, RoadScenarioVar.g_pedestrian3_y);
			if(RoadScenarioVar.move_belief3==3)
				move3("pedestrian3", RoadScenarioVar.g_pedestrian3_x, RoadScenarioVar.g_pedestrian3_y);
			if(RoadScenarioVar.move_belief3==4)
				move4("pedestrian3", RoadScenarioVar.g_pedestrian3_x, RoadScenarioVar.g_pedestrian3_y);
			if(RoadScenarioVar.move_belief3==5)
				move5("pedestrian3", RoadScenarioVar.g_pedestrian3_x, RoadScenarioVar.g_pedestrian3_y);
			if(RoadScenarioVar.move_belief3==6)
				move6("pedestrian3", RoadScenarioVar.g_pedestrian3_x, RoadScenarioVar.g_pedestrian3_y);
			}
			else if (ag.equals("pedestrian4"))
				{
			if(RoadScenarioVar.move_belief4==1)
				move1("pedestrian4", RoadScenarioVar.g_pedestrian4_x, RoadScenarioVar.g_pedestrian4_y);
			if(RoadScenarioVar.move_belief4==2)
				move2("pedestrian4", RoadScenarioVar.g_pedestrian4_x, RoadScenarioVar.g_pedestrian4_y);
			if(RoadScenarioVar.move_belief4==3)
				move3("pedestrian4", RoadScenarioVar.g_pedestrian4_x, RoadScenarioVar.g_pedestrian4_y);
			if(RoadScenarioVar.move_belief4==4)
				move4("pedestrian4", RoadScenarioVar.g_pedestrian4_x, RoadScenarioVar.g_pedestrian4_y);
			if(RoadScenarioVar.move_belief4==5)
				move5("pedestrian4", RoadScenarioVar.g_pedestrian4_x, RoadScenarioVar.g_pedestrian4_y);
			if(RoadScenarioVar.move_belief4==6)
				move6("pedestrian4", RoadScenarioVar.g_pedestrian4_x, RoadScenarioVar.g_pedestrian4_y);
			}
			else if (ag.equals("pedestrian5"))
				{
			if(RoadScenarioVar.move_belief5==1)
				move1("pedestrian5", RoadScenarioVar.g_pedestrian5_x, RoadScenarioVar.g_pedestrian5_y);
			if(RoadScenarioVar.move_belief5==2)
				move2("pedestrian5", RoadScenarioVar.g_pedestrian5_x, RoadScenarioVar.g_pedestrian5_y);
			if(RoadScenarioVar.move_belief5==3)
				move3("pedestrian5", RoadScenarioVar.g_pedestrian5_x, RoadScenarioVar.g_pedestrian5_y);
			if(RoadScenarioVar.move_belief5==4)
				move4("pedestrian5", RoadScenarioVar.g_pedestrian5_x, RoadScenarioVar.g_pedestrian5_y);
			if(RoadScenarioVar.move_belief5==5)
				move5("pedestrian5", RoadScenarioVar.g_pedestrian5_x, RoadScenarioVar.g_pedestrian5_y);
			if(RoadScenarioVar.move_belief5==6)
				move6("pedestrian5", RoadScenarioVar.g_pedestrian5_x, RoadScenarioVar.g_pedestrian5_y);
			}
			else if (ag.equals("pedestrian6"))
				{
			if(RoadScenarioVar.move_belief6==1)
				move1("pedestrian6", RoadScenarioVar.g_pedestrian6_x, RoadScenarioVar.g_pedestrian6_y);
			if(RoadScenarioVar.move_belief6==2)
				move2("pedestrian6", RoadScenarioVar.g_pedestrian6_x, RoadScenarioVar.g_pedestrian6_y);
			if(RoadScenarioVar.move_belief6==3)
				move3("pedestrian6", RoadScenarioVar.g_pedestrian6_x, RoadScenarioVar.g_pedestrian6_y);
			if(RoadScenarioVar.move_belief6==4)
				move4("pedestrian6", RoadScenarioVar.g_pedestrian6_x, RoadScenarioVar.g_pedestrian6_y);
			if(RoadScenarioVar.move_belief6==5)
				move5("pedestrian6", RoadScenarioVar.g_pedestrian6_x, RoadScenarioVar.g_pedestrian6_y);
			if(RoadScenarioVar.move_belief6==6)
				move6("pedestrian6", RoadScenarioVar.g_pedestrian6_x, RoadScenarioVar.g_pedestrian6_y);
			}
	}
}