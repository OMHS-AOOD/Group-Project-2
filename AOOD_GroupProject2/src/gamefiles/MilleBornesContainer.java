package gamefiles;

import java.awt.Color;
import java.awt.Window;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class MilleBornesContainer implements Serializable {
	private MilleBornesGame mbg;
	private File desktop;
	public MilleBornesContainer() {
		mbg = new MilleBornesGame(this);
		FileSystemView filesys = FileSystemView.getFileSystemView();

		File[] roots = filesys.getRoots();

		desktop = filesys.getHomeDirectory();
	}

	public void save(){
		UIManager.put("OptionPane.messageForeground", Color.green);
		UIManager.put("Button.foreground", Color.GREEN);
		JFileChooser jfc = new JFileChooser(desktop);
		jfc.setDialogTitle("Select a location to save");
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int check = jfc.showSaveDialog(new JFrame());
		
		if(check == JFileChooser.APPROVE_OPTION) {
			
			File f = jfc.getSelectedFile();
			String path = f.getAbsolutePath();
			
			try {
				FileOutputStream fos;
				if(path.endsWith("\\")){
			    	fos = new FileOutputStream(new File(path + "MilleBornesGame" + ".mbg"));

				}
				else{
			    	fos = new FileOutputStream(new File(path + ".mbg"));
				}
			    ObjectOutputStream oos = new ObjectOutputStream(fos);
			    oos.writeObject(new GameData(mbg));
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error when writing to file", "Error" , JOptionPane.INFORMATION_MESSAGE);
			}
			
		}
	}

	public boolean load() {
		JFileChooser jfc = new JFileChooser(desktop);
		jfc.setDialogTitle("Select a Mille Bornes game");
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Mille Bornes Games(.mbg)", "mbg");
		jfc.setFileFilter(filter);
		int check = jfc.showOpenDialog(new JFrame());

		if(check == JFileChooser.APPROVE_OPTION) {
			File f = jfc.getSelectedFile();
			try {
		    	FileInputStream fis = new FileInputStream(f);
			    ObjectInputStream ois = new ObjectInputStream(fis);
			    mbg.dispose();
			    mbg = null;		
			 
			    GameData data = (GameData) ois.readObject();
			    MilleBornesGame mbg2 = new MilleBornesGame(data, this);

		        ois.close();
			    return true;
			}
		    catch (IOException | ClassNotFoundException e) {
		    	e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error when trying to read game file", "Error" , JOptionPane.INFORMATION_MESSAGE);
				return false;
		    }
			
		}
		return false;
	}

	public void newGame() {
		mbg.dispose();
		mbg = null;
		mbg = new MilleBornesGame(this);
		
	}

}
