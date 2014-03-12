/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 *
 * @author aurelien
 */
public class CsvWindow {
    private JFrame mainFrame;
    private JLabel currFile;
    private JProgressBar progress;
    
    public CsvWindow(String title)
    {
        this.mainFrame = new JFrame(title);
        mainFrame.setBounds(100, 100, 800, 70);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        currFile = new JLabel("Aucun fichier");
        mainFrame.getContentPane().add(currFile, BorderLayout.NORTH);
        
        progress = new JProgressBar(0, 100);
        progress.setStringPainted(true);
        mainFrame.getContentPane().add(progress,BorderLayout.CENTER);
        
        mainFrame.setVisible(true);
    }
    
    public void setCurrentFile(String file, int fileLength)
    {
        currFile.setText(file);
        progress.setMaximum(fileLength);
        progress.setValue(0);
    }
    
    public void incrementProgressBar()
    {
        progress.setValue(progress.getValue()+1);
    }
    
    public void fermer()
    {
        mainFrame.setVisible(false);
        mainFrame.dispose();
    }
    
}
