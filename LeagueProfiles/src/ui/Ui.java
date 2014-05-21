package ui;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import proc.ProfileManager;

public class Ui extends JFrame {
	
	private ProfileManager profileManager;
	
	private TopPanel topPanel;
	
	private MidPanel midPanel;
	
	public Ui( ProfileManager pm )
    {
		setTitle("LeagueProfiles");
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		
       profileManager = pm;
       topPanel = new TopPanel(this);
       midPanel = new MidPanel(this);
       JPanel signature = new JPanel(new BorderLayout());
       JLabel label = new JLabel("LeagueProfiles-2014");
       label.setEnabled(false);
       add(midPanel, BorderLayout.CENTER);
       add(topPanel, BorderLayout.NORTH);
       add(signature, BorderLayout.SOUTH);
       
       signature.add(label, BorderLayout.CENTER);
       //To pack or not to pack
       setSize(400, 200);
       //pack();
       
    }
	
	public void dispose()
	{
		try
        {
            profileManager.saveData( );
            super.dispose( );
        }
        catch( Exception e )
        {
            setVisible( true );
            int respuesta = JOptionPane.showConfirmDialog( this, "Problems saving:\n" + e.getMessage( ) + "\n¿Still want to close?", "Error", JOptionPane.YES_NO_OPTION );
            if( respuesta == JOptionPane.YES_OPTION )
            {
                super.dispose( );
            }
        }
	}
	
	public void setPath(String path)
	{
		profileManager.setGamePath(path);
	}
	
	public ArrayList<String> getProfiles() throws Exception
	{
		return profileManager.getProfiles();
	}
	
	public void deleteProfile(String profileName) throws Exception
	{
		profileManager.deleteProfile(profileName);
	}
	
	public void createProfile(String profileName) throws Exception
	{
		profileManager.createNewProfile(profileName);
	}
	
	public void useProfile(String profileName) throws Exception
	{
		profileManager.useProfile(profileName);
	}
	
	public String getPath()
	{
		return profileManager.getPath();
	}
	
	public static void main( String[] args )
    {
        ProfileManager pms = null;
        try
        {
            pms = new ProfileManager( );
        }
        catch(Exception e )
        {
            e.printStackTrace( );
            System.exit( 1 );
        }
        Ui id = new Ui( pms );
        id.setVisible( true );
    }

}
