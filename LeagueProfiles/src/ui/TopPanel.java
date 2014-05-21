package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TopPanel extends JPanel implements ActionListener {

	private Ui pm;
	
	private JButton button;
	
	private JFileChooser fc;
	
	private JLabel label;
	
	
	public TopPanel(Ui pm)
	{
		this.pm = pm;
		setLayout(new BorderLayout());
		this.setName("League of Legends path");
		
		
		button = new JButton("Browse");
		button.setActionCommand("BROWSE");
		button.addActionListener(this);
		
		
		
		if (getPath().isEmpty())
		{
			label = new JLabel("Select a path please");
		}
		else
		{
			label = new JLabel(getPath());
		}
		label.setEnabled(false);
		
		add(button, BorderLayout.EAST);
		add(label, BorderLayout.CENTER);
		
		
		
		
	}
	
	public String getPath()
	{
		return pm.getPath();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		String command = arg0.getActionCommand( );

        if( "BROWSE".equals( command ) )
        {
            fc = new JFileChooser( "C:/" );
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fc.setDialogTitle( "League of Legends Path, eg: C:/RiotGames/League of Legends" );
            int result = fc.showOpenDialog( this );
            if( result == JFileChooser.APPROVE_OPTION )
            {
                File file = fc.getSelectedFile( );
                String path = file.getAbsolutePath( );
                
                
                File propFile = new File(path, "lol.launcher.exe");

                if (propFile.exists()) {  
                	pm.setPath(path);
                    label.setText(path);
                    label.setToolTipText("LoL game directory.");
                    JOptionPane.showMessageDialog(this, "Path was set.");
                } else {  
                	JOptionPane.showMessageDialog(this, "Path was not set.");  
                }  

            }
        }
    
	}

}
