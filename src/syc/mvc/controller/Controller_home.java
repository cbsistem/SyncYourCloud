package syc.mvc.controller;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import syc.mvc.model.Connexion;
import syc.mvc.model.Model;
import syc.mvc.view.IHM_home;

public class Controller_home implements ActionListener
{
	private Model model_SYC;
    private IHM_home view_home;
    
    public Controller_home(Model aModel_SYC, IHM_home aView_home) 
    {
		this.model_SYC = aModel_SYC;
		this.view_home = aView_home;
		this.ControllerActionListenerForComponent(view_home.getContentPane());
    }
	
    @SuppressWarnings("unchecked")
	public void ControllerActionListenerForComponent(Container cont_temps)
	{
		if(cont_temps instanceof Container)
		{
			int i;
			int j =cont_temps.getComponentCount();
			for(i =0; i< j ;i++)
			{
				Component cmp_temps = cont_temps.getComponents()[i];
				//System.out.println(j);
						
				if(cmp_temps instanceof JButton)
				{	
					((JButton) cmp_temps).addActionListener(this);
				}
				if(cmp_temps instanceof JTextField )
				{
					((JTextField) cmp_temps).addActionListener(this);
				}
				if(cmp_temps instanceof JPasswordField )
				{
					((JPasswordField) cmp_temps).addActionListener(this);
				}
				if(cmp_temps instanceof JComboBox)
				{
					ControllerActionListenerForComponent((JComboBox<String>) cmp_temps);
				}
				if(cmp_temps instanceof Container)
				{
					ControllerActionListenerForComponent((Container) cmp_temps); 
				}
			}
		}	
	}
    
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==this.view_home.getjBt_Exit())
		{
			//Exit application
			this.view_home.dispose();		
		}	
		
		if(e.getSource()==this.view_home.getjBt_Account())
		{
			//go to IHM_account
			model_SYC.init();
			model_SYC.setDisplay_account(true);
			
			//test
			if(view_home.getjLab_Login().getText().equals(model_SYC.gettestText()))
			{
				//model_SYC.settestText("Youpi");
				//view_home.getjLab_Login().setText(model_SYC.gettestText());
			}
			else
			{
				//view_home.getjLab_Login().setText(model_SYC.gettestText());
			}
		}	
		
		if(e.getSource()==this.view_home.getjBt_Connexion())
		{
			if(this.view_home.getTxt_Login().getText().isEmpty() || this.view_home.getTxt_Password().getText().isEmpty()){
				JOptionPane.showMessageDialog (this.view_home,"Renseignez les champs pour vous connecter","SYC message",1);//1:exclam,1:exclamTriangle,3:interro 
				return;
			}
						
			//Connection and go to IHM_drives
			if(Connexion.Exist(this.view_home.getTxt_Login().getText(), this.view_home.getTxt_Password().getText()))//pr linstant
			{
				model_SYC.init();
				model_SYC.setCurrentConfFile(Connexion.fileConf);
				model_SYC.setDisplay_drives(true);  	
			}
			else
				JOptionPane.showMessageDialog (this.view_home,"Identifiant ou mot de passe incorrect.","SYC message",1); 
		}	
	}
}