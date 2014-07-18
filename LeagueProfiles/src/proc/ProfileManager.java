package proc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class ProfileManager {
	

	
	// Paths n stuff
	private static String LOL_PATH = "";
	//this one may vary a lot from patch to patch
	private static final String VIDEO_CONFIG_PATH ="/RADS/solutions/lol_game_client_sln/releases/0.0.1.48/deploy/DATA/menu/hud";
	
	private static final String MOUSE_KEYBOARD_PATH="/Config/input.ini";
	
	private static final String GAME_SETTINGS_PATH = "/Config/game.cfg";
	
	
	//Atributes
	
	private ArrayList<String> profiles;
	
	
	public ProfileManager()
	{
		File archive = new File( "profiles.txt" );
		File archiveP = new File( "path.txt" );
        if( archive.exists( ) && archiveP.exists() )
        {
            try
            {
            	//Profiles
            	FileInputStream fis = new FileInputStream("profiles.txt");
            	ObjectInputStream ois = new ObjectInputStream(fis);
            	profiles = (ArrayList<String>) ois.readObject();
            	ois.close();
            	//Path
            	FileInputStream fisa = new FileInputStream("path.txt");
            	ObjectInputStream oisa = new ObjectInputStream(fisa);
            	LOL_PATH = (String) oisa.readObject();
            	oisa.close();
            }
            catch( Exception e )
            {
               e.printStackTrace();
            }
        }
        else
        {
            profiles = new ArrayList<String>( );
            LOL_PATH = "";
        }
	}
	
	
	
	/**
	 * Returns the list of profile names.
	 * @return
	 */
	public ArrayList<String> getProfiles() throws Exception
	{
		if (profiles.isEmpty())
		{
			throw new Exception("There are no profiles registered.");
		}
		else
		{
			return profiles;
		}
		
	}
	
	/**
	 * Creates a new profile based on a new name.
	 * NOTE: The profile is created with the settings that are actually active
	 * for the League of Legends installation.
	 * @param profileName
	 */
	public void createNewProfile(String profileName) throws Exception
	{
		//First part: Namecheck
		boolean found = false;
		for(int i = 0; i<profiles.size() && !found; i++)
		{
			String verified = profiles.get(i);
			if(verified.equals(profileName))
			{
				found = true;
				new Exception("A profile with the name "+profileName+" already exists.");
			}

		}
	
		try
		{
			profiles.add(profileName);
			//Second part: Folder creation.
			File dir = new File(profileName);
			dir.mkdir();
			//Third part copy the stuff.
			//Copy Mouse and keyboard settings.
			copy(new File(LOL_PATH+MOUSE_KEYBOARD_PATH), new File(profileName+"/config.ini"));
			//Copy general settings
			copy(new File(LOL_PATH+GAME_SETTINGS_PATH), new File(profileName+"/game.cfg"));
			//Copy video settings.
			copy(new File(LOL_PATH+VIDEO_CONFIG_PATH), new File(profileName+"/hud") );

			
			
		}
		catch(Exception e)
		{
			new Exception("Error creating profile.");
		}
		
	}
	
	/**
	 * Deletes a profile based on a name.
	 * @param profileName
	 */
	public void deleteProfile(String profileName) throws Exception
	{
		boolean found = false;
		for(int i = 0; i<profiles.size() && !found; i++)
		{
			String verified =profiles.get(i);
			if(verified.equals(profileName))
			{
				
				File directory = new File(profileName);
		    	if(!directory.exists()){
		 
		           System.out.println("Directory does not exist.");
		           System.exit(0);
		 
		        }
		    	else
		    	{
		            try
		            {
		            	delete(directory);
		            }
		            catch(Exception e)
		            {
		               new Exception("Error removing profile");
		            }
		         }
		    	profiles.remove(i);
				found = true;
				
			}

		}
	}
	
	/**
	 * Sets the current profile to the one that matches in name.
	 * @param profileName
	 */
	public void useProfile(String profileName)
	{
		try
		{
			//Copy Mouse and keyboard settings.
			copy(new File(profileName+"/config.ini"), new File(LOL_PATH+MOUSE_KEYBOARD_PATH));
			//Copy video settings.
			copy(new File(profileName+"/hud"), new File(LOL_PATH+VIDEO_CONFIG_PATH));
			//Copy general settings
			copy(new File(profileName+"/game.cfg"), new File(LOL_PATH+GAME_SETTINGS_PATH));

		}
		catch(Exception e)
		{
			new Exception("Error updating profile.");
		}
	}
	
	//Utils
	
	public void setGamePath(String path)
	{
		LOL_PATH = path;
	}
	
	public String getPath()
	{
		return LOL_PATH;
	}
	
	public void copy(File sourceLocation , File targetLocation) throws IOException {
	    if (sourceLocation.isDirectory()) {
	        if (!targetLocation.exists()) {
	            targetLocation.mkdir();
	        }

	        String[] children = sourceLocation.list();
	        for (int i=0; i<children.length; i++) {
	            copy(new File(sourceLocation, children[i]),
	                    new File(targetLocation, children[i]));
	        }
	    } else {

	        InputStream in = new FileInputStream(sourceLocation);
	        OutputStream out = new FileOutputStream(targetLocation);
	        
	        byte[] buf = new byte[1024];
	        int len;
	        while ((len = in.read(buf)) > 0) {
	            out.write(buf, 0, len);
	        }
	        in.close();
	        out.close();
	    }
	}
	
	public static void delete(File file)
	    	throws IOException{
	 
	    	if(file.isDirectory()){
	 
	    		//directory is empty, then delete it
	    		if(file.list().length==0){
	 
	    		   file.delete();
	    		   System.out.println("Directory is deleted : " 
	                                                 + file.getAbsolutePath());
	 
	    		}else{
	 
	    		   //list all the directory contents
	        	   String files[] = file.list();
	 
	        	   for (String temp : files) {
	        	      //construct the file structure
	        	      File fileDelete = new File(file, temp);
	 
	        	      //recursive delete
	        	     delete(fileDelete);
	        	   }
	 
	        	   //check the directory again, if empty then delete it
	        	   if(file.list().length==0){
	           	     file.delete();
	        	     System.out.println("Directory is deleted : " 
	                                                  + file.getAbsolutePath());
	        	   }
	    		}
	 
	    	}else{
	    		//if file, then delete it
	    		file.delete();
	    		System.out.println("File is deleted : " + file.getAbsolutePath());
	    	}
	    }
	
	public void saveData() throws Exception
	{
			if(profiles.isEmpty())
			{
				FileOutputStream foso = new FileOutputStream("path.txt");
				ObjectOutputStream ooso = new ObjectOutputStream(foso);
				ooso.writeObject(LOL_PATH);
				ooso.close();
			}
			else
			{
				FileOutputStream fos = new FileOutputStream("profiles.txt");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(profiles);
				oos.close();
				
				FileOutputStream fosa = new FileOutputStream("path.txt");
				ObjectOutputStream oosa = new ObjectOutputStream(fosa);
				oosa.writeObject(LOL_PATH);
				oosa.close();
			}
	}
	

	
//	public static void main(String[] args)
//	{
//		try{
//		ProfileManager pm = new ProfileManager();
//		pm.setGamePath("C:/League of Legends");
//		pm.createNewProfile("Satanasa");
//		pm.createNewProfile("Abejitas");
//		pm.deleteProfile("Satanasa");
//		
//		File f = new File("Satanas");
//		System.out.println(f.exists());
//		pm.saveData();
//
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//	}

}
