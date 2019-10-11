package CAV_Env;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RoadGUI extends JFrame{
    private RoadScenario scenario = new RoadScenario();
    private JTextField m_speedTextField = new JTextField(Integer.toString(RoadScenarioVar.m_speedVehicle));
    private JSlider   m_speedSlider = new JSlider(1,10);
	private JButton m_btn1  = new JButton("Mode 1");
	private JButton m_btn2  = new JButton("Mode 2");
	private JButton m_btn3  = new JButton("Mode 3");
    private JButton m_btnVehicle  = new JButton("Pause");
    private JButton m_btnFrame    = new JButton("ReStart");
    //private JTextArea m_score = new JTextArea(50,50); //display the total score

    public RoadGUI()
    {
        super("CAV");
        setLayout(null);
        setSize(RoadScenarioVar.j_FrameWidth, RoadScenarioVar.j_FrameHeight);

        scenario.setLayout(null);
        add(scenario);
		
		m_btnVehicle.setFont(m_btnVehicle.getFont().deriveFont(18.0f));
		m_btnFrame.setFont(m_btnVehicle.getFont().deriveFont(18.0f));
		m_speedTextField.setFont(m_btnVehicle.getFont().deriveFont(18.0f));
		
        AddButtons();
        setVisible(true);
    }

    public void Update()
    {
        //m_score.setText(RoadScenarioVar.total_Score);  //total score
		
        if(RoadScenarioVar.m_Pause)
            m_btnVehicle.setText("Pause");
        else
            m_btnVehicle.setText("Continue");
		
        m_btnFrame.setText("ReStart");
		
        repaint();
    }

    public void AddButtons()
    {
        m_btn1.setBounds(1000,510,100,30);		   		 //Mode 1
        m_btn1.setLocation(1000,510);
        m_btn1.addActionListener(new Btn1());
        add(m_btn1);
		
		m_btn2.setBounds(1150,510,100,30);		    //Mode 2
        m_btn2.setLocation(1150,510);
        m_btn2.addActionListener(new Btn2());
        add(m_btn2);
		
		m_btn3.setBounds(1300,510,100,30);		    //Mode 3
        m_btn3.setLocation(1300,510);
        m_btn3.addActionListener(new Btn3());
        add(m_btn3);
		
		m_btnVehicle.setBounds(400,510,100,30);         //Pause Button
        m_btnVehicle.setLocation(400,510);
        m_btnVehicle.addActionListener(new Btn1Handler());
        add(m_btnVehicle);
		
		m_btnFrame.setBounds(700,510,100,30);		    //Restart Button
        m_btnFrame.setLocation(700,510);
        m_btnFrame.addActionListener(new Btn2Handler());
        add(m_btnFrame);
		
		m_speedTextField.setBounds(100,510,50,30);    //Text[0,1000]
		m_speedTextField.setLocation(100,510);
		
        m_speedSlider.setBounds(150,510,100,30);       //Slider
        m_speedSlider.setLocation(150,510);
        m_speedSlider.setValue(RoadScenarioVar.m_speedVehicle/20);
		
        m_speedSlider.addChangeListener(new SpeedHandler());
        add(m_speedTextField);
        add(m_speedSlider);
    }
    
private class SpeedHandler implements ChangeListener
{
    public void stateChanged(ChangeEvent e)
    {
        JSlider source = (JSlider)e.getSource();
        RoadScenarioVar.m_speedVehicle = source.getValue()*20;
        m_speedTextField.setText(Integer.toString(RoadScenarioVar.m_speedVehicle));
    }
}

private class Btn1Handler implements ActionListener
{
    public void actionPerformed(ActionEvent e)
    {   
        if(RoadScenarioVar.m_Pause)
			System.out.println("Switch to Pause!");
        else
			System.out.println("Switch to Continue!");
        RoadScenarioVar.m_Pause = !RoadScenarioVar.m_Pause;
		//public int speed1=RoadScenarioVa.m_speedVehicle;
		/*if(RoadScenarioVar.m_Pause)
		{
			RoadScenarioVar.m_speedVehicle=20;
		}
		else
		{
			RoadScenarioVar.m_speedVehicle=10000;
		}*/
        Update();
    }
}

public class Btn2Handler implements ActionListener
{
    public void actionPerformed(ActionEvent e)
    {	
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
				System.out.println("The scenario is rebuild!");
				RoadScenarioVar.m_Frame = true;
				RoadScenarioVar.vehicle_i=0;
				RoadScenarioVar.total_Score1 = 2000;	
				RoadScenarioVar.total_Score2 = 2000;	
				RoadScenarioVar.total_Score3 = 2000;	
				RoadScenarioVar.total_Score4 = 2000;	
				RoadScenarioVar.total_Score5 = 2000;	
				RoadScenarioVar.total_Score6 = 2000;	
        Update();
    }
}

public class Btn1 implements ActionListener
{
    public void actionPerformed(ActionEvent e)
    {	
		System.out.println("Switch to Mode 1");
		RoadScenarioVar.move_mode_ID=1;
        Update();
    }
}

public class Btn2 implements ActionListener
{
    public void actionPerformed(ActionEvent e)
    {	
		System.out.println("Switch to Mode 2");
		RoadScenarioVar.move_mode_ID=2;
        Update();
    }
}

public class Btn3 implements ActionListener
{
    public void actionPerformed(ActionEvent e)
    {	
		System.out.println("Switch to Mode 3");
		RoadScenarioVar.move_mode_ID=3;
        Update();
    }
}


}