package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

public class MidPanel extends JPanel implements ActionListener {
	
	private Ui ui;
	
	private JList list;
	
	private JButton addButton;
	
	private JButton delButton;
	
	private JButton useButton;
	
	private JScrollPane listScroller;
	
	public MidPanel(Ui ui)
	{

		this.ui = ui;
		setLayout(new BorderLayout());
		
		//
		list = new JList(); //data has type Object[]
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL_WRAP);
		//list.setVisibleRowCount(-1);
		
		
		listScroller = new JScrollPane();
		listScroller.setViewportView(list);

		
		
		addButton = new JButton("Add New Profile");
		addButton.setActionCommand("ADD");
		addButton.addActionListener(this);
		
		delButton = new JButton("Delete Selected Profile");
		delButton.setActionCommand("DEL");
		delButton.addActionListener(this);
		
		useButton = new JButton("Use Selected Profile");
		useButton.setActionCommand("USE");
		useButton.addActionListener(this);
		
		add(listScroller, BorderLayout.CENTER);
		updateProfiles();
		GridLayout g = new GridLayout(3, 1);
		JPanel p =new JPanel(g);
	
		p.add(addButton);
		p.add(useButton);
		p.add(delButton);
		add(p, BorderLayout.EAST);
		
				
		
	}
	
	public void updateProfiles()
	{
		try{
		 ArrayList<String> al = ui.getProfiles();
		 String[] arra = new String[al.size()];
		 al.toArray(arra); // fill the array
		 list.setListData(arra);
		}
		catch(Exception e)
		{
			 ArrayList<String> a=  new ArrayList<String>();
			 a.add("No Profiles Found");
			 String[] array = new String[a.size()];
			 a.toArray(array); // fill the array
			 list.setListData(array);
			 JOptionPane.showMessageDialog(this, "There are no profiles yet.");
			 
		}
	}
	

	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		String command = arg0.getActionCommand( );
		
		if( "ADD".equals(command) )
		{
			String name = JOptionPane.showInputDialog(this,"Enter a name for the new profile", "Add a Profile");
			if (name != null)
			{
				try
				{
					ui.createProfile(name);
					JOptionPane.showMessageDialog(this, "Profile "+ name +" was created.");
					updateProfiles();
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(this, e.getMessage());
				}
			}
		}
		else if ("DEL".equals(command))
		{
			String selectedProfile =(String) list.getSelectedValue();
			try{
			ui.deleteProfile(selectedProfile);
			updateProfiles();
			JOptionPane.showMessageDialog(this, "Profile "+selectedProfile+" was deleted.");
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(this, e.getMessage());
			}
			
		}
		else 
		{
			String selectedProfile =(String) list.getSelectedValue();
			try{
			ui.useProfile(selectedProfile);
			JOptionPane.showMessageDialog(this, "Profile "+selectedProfile+" is now active.");
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(this, e.getMessage());
			}
		}
			
	}
	


}
