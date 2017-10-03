import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Fixtures extends JApplet {
	int teams,count=0;
	 TextArea ta1,ta2;
	  boolean ghost = false;
         String[][] rounds;
	 public void init(){
	  		  			
		add ("North", new JLabel ("WELCOME", JLabel.CENTER)); 
		setLayout(null);       	       	
        	setVisible(true);
        }   
   	
       
        	
       
       public void paint(Graphics g){
       	setBackground(Color.CYAN);
       	setForeground(Color.RED);
       	g.setFont(new Font("TimesRoman",Font.ITALIC,20));
       	g.drawString("WELCOME ",50,30);
       	}	
        
        
        public void start(){
        
        String n1 = JOptionPane.showInputDialog("Enter Number of Teams:");
        teams = Integer.parseInt(n1); 
        // Find out how many teams we want fixtures for.
       
        Team t[] = new Team[teams];
        
        for(int i=0;i<teams;i++){
        	t[i] = new Team();
        	t[i].name= JOptionPane.showInputDialog("Enter Team name");
        }
        // If odd number of teams add a "ghost".
       
        if (teams % 2 == 1) {
            teams++;
            ghost = true;
        }
        
        // Generate the fixtures using the cyclic algorithm.
        int totalRounds = teams - 1;
        int matchesPerRound = teams / 2;
        String[][] rounds = new String[totalRounds][matchesPerRound];
        
        for (int round = 0; round < totalRounds; round++) {
            for (int match = 0; match < matchesPerRound; match++) {
                int home = (round + match) % (teams - 1);
                int away = (teams - 1 - match + round) % (teams - 1);
                // Last team stays in the same place while the others
                // rotate around it.
                if (match == 0) {
                    away = teams - 1;
                }
                // Add one so teams are number 1 to teams not 0 to teams - 1
                // upon display.
                if(!ghost)
                rounds[round][match] = home + " v " + away;
                else
                	if(home==teams-1||away==teams-1){
                		rounds[round][match]="bye";
                	}
                	else
                		rounds[round][match] = home + " v " + away;
            }
        }
        
        // Interleave so that home and away games are fairly evenly dispersed.
        String[][] interleaved = new String[totalRounds][matchesPerRound];
        if(!ghost){
        int evn = 0;
        int odd = (teams / 2);
        for (int i = 0; i < rounds.length; i++) {
            if (i % 2 == 0) {
                interleaved[i] = rounds[evn++];
            } else {
                interleaved[i] = rounds[odd++];
            }
        }
        
        rounds = interleaved;

        // Last team can't be away for every game so flip them
        // to home on odd rounds.
        for (int round = 0; round < rounds.length; round++) {
            if (round % 2 == 1) {
                rounds[round][0] = flip(rounds[round][0]);
            }
        }
        }
        // Display the fixtures        
        
          String[] components={"0"}; 
          String z;  
	 ta1=new TextArea("FIXTURES\n");
    	  ta1.setBackground(Color.GREEN);
    	  ta1.setForeground(Color.RED);
    	  ta1.setBounds(50,50,400,400);
    	  ta1.setFont(new Font("TimesRoman",Font.BOLD,16));
    	  for(String s[]:rounds)
           	for(String q:s){
           	if(!q.equals("bye")){
           		components = q.split(" v ");       
        		z=t[Integer.parseInt(components[0])].name + " v " + t[Integer.parseInt(components[1])].name;
           		ta1.append("Match: " + (++count) + " " + z +"\n");
           	
           	}
           }
           ta1.setEditable(false);
           add(ta1); 
           
           
           //Input results
           
           
            for(String s[]:rounds)
           	for(String q:s){
           	   if(!q.equals("bye")){
           	   	components = q.split(" v ");       
        		z=t[Integer.parseInt(components[0])].name + " v " + t[Integer.parseInt(components[1])].name;           		           
              	        String ppp = "Winner team of Match:" +z+ " ('NR-no results'):";
                        String winner = JOptionPane.showInputDialog(ppp);    
                              
        		update(t[Integer.parseInt(components[0])],t[Integer.parseInt(components[1])],winner);
           	   }
      		}
      		
      		
      		
      		//Display points table
      		
      		int x1,y1;
      		if(ghost){
      			teams--;
      		}
	
		for(int i=0;i<teams;i++)
			t[i].points=(t[i].wins*2 + t[i].drawn*1);
		for(int i=0;i<teams-1;i++)
			for(int j=0;j<teams-1-i;j++){
				
				x1=t[j].points;
				y1=t[j+1].points;
				
				if(x1<y1)
				{
					
					//swap(p[j],p[j+1]);
					Team r;
					r = t[j];
					t[j] =t[j+1];
					t[j+1] = r;
					
				}
		
			}
			
			
			ta2=new TextArea("POINTS TABLE\n");
			ta2.setBounds(600,50,400,400);
			ta2.setBackground(Color.ORANGE);
			ta2.setForeground(Color.RED);
			ta2.setFont(new Font("TimesRoman",Font.BOLD,16));
			add(ta2);
			ta2.append("NAME MATCHES WIN LOSE NR POINTS\n");
			for(int i=0;i<teams;i++){			
			ta2.append(t[i].name + "              " + t[i].matchesplayed + "           " + t[i].wins + "       " + (t[i].matchesplayed-t[i].wins-t[i].drawn) + "      " + t[i].drawn + "       " +t[i].points+"\n");
			}
			ta2.setEditable(false);
			
			
			
			
			
			
    }
        
         
         
       // Update the points after every match  
 public static void update(Team p,Team q,String winner){
    	
    	p.matchesplayed++;
    	q.matchesplayed++;
    	if(winner.equals(p.name))
    		p.wins++;
    	else if(winner.equals(q.name))
    		q.wins++;
    	else
    	{
    		p.drawn++;
    		q.drawn++;
    	}
    }   
    

    private static  String flip(String match) {
        String[] components = match.split(" v ");       
        return components[1] + " v " + components[0];
        	
    }

       

   
}

		
