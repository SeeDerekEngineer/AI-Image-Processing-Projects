import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
	
public class CTScanImages2 {
	   private static Scanner myScanner = new Scanner(System.in);
	   private BufferedImage  image;
	   private static int xStart;
	   private static int yStart;
	   private static int xEnd;
	   private static int yEnd;
	   private int rawValue;
	   private int[] histogramValues = new int[256];
	   private static String answer1;
	   private static String answer2;
	   private static String answer3;
	   private static String answer4;
	   private static String answer5;
	   private boolean yesTrue1;
	   private boolean yesTrue2;
	   private boolean yesTrue3;
	   private boolean yesTrue4;
	   private boolean yesTrue5;
	   private double pixelsTotal = 0;
	   private int[] transformHistogram = new int[256];
	   private double cdvMin=255;
	   private double cdvMax=0;
	   private double[] cdv = new double[256];
	   File f = null;
	   BufferedImage img = new BufferedImage(xEnd, yEnd, BufferedImage.TYPE_INT_RGB);   //Determines size of produced image
	   BufferedImage img2 = new BufferedImage(xEnd, yEnd, BufferedImage.TYPE_INT_RGB);  //Determines size of produced image
	   
	   public CTScanImages2() {
	      try {
	         File input = new File("Scans" + File.separator    //Obtain the jpeg file
            + "Malignant 9-63 center.jpg");
	         image = ImageIO.read(input);
	         yesTrue1 = answer1.equals("Yes") || answer1.equals("yes") || answer1.equals("YES") || answer1.equals("Y") || answer1.equals("y");
	         yesTrue2 = answer2.equals("Yes") || answer2.equals("yes") || answer2.equals("YES") || answer2.equals("Y") || answer2.equals("y");
	         yesTrue3 = answer3.equals("Yes") || answer3.equals("yes") || answer3.equals("YES") || answer3.equals("Y") || answer3.equals("y");
	         yesTrue4 = answer4.equals("Yes") || answer4.equals("yes") || answer4.equals("YES") || answer4.equals("Y") || answer4.equals("y");
	         yesTrue5 = answer5.equals("Yes") || answer5.equals("yes") || answer5.equals("YES") || answer5.equals("Y") || answer5.equals("y");
	         for(int i=yStart; i<yEnd; i++){
	            for(int j=xStart; j<xEnd; j++){
                   Color c = new Color(image.getRGB(j, i));
	               int red = (int)(c.getRed() * 0.299);
	               int green = (int)(c.getGreen() * 0.587);
	               int blue = (int)(c.getBlue() *0.114);
	               rawValue = red+green+blue;
	               Color newColor = new Color(rawValue,
	               rawValue,rawValue);
	               img.setRGB(j,i,newColor.getRGB());
	                       
	               
	               
	               for (int k =0; k < 256; k++){     //Establish Histogram values
	            	   if(k == rawValue){
	            		   histogramValues[k] = histogramValues[k] + 1;  
	            		   break;
	            	   	} //End 'if' statement
	               } //End 'for' loop
	               cdv[0] = histogramValues[0];
	               if(yesTrue1){
	               System.out.printf("%3d",rawValue);	               
	               System.out.print("  ");
	               } //End of 'If' Statement
	               //img.setRGB(j, i, rawValue);           //Change this later
	               } //End 'for' loop
	            if(yesTrue1){System.out.println("");}
	            
	         
	         } //End 'for' loop
	         if(yesTrue1){System.out.println("*****************END OF RAW PIXEL VALUES*********************");}
	         
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	         
	         
	         for (int m = 0; m < 256; m++){
	        	 pixelsTotal = pixelsTotal + histogramValues[m];
	        	 if (histogramValues[m] > cdvMax){cdvMax = histogramValues[m];}
	        	 if (histogramValues[m] < cdvMin && histogramValues[m] != 0){cdvMin = histogramValues[m];}   //Establish cdvMin
          	     if (m != 0){cdv[m] = cdv[m-1] + histogramValues[m];}   									 //Establish cdv[]
	        	 if (yesTrue2){System.out.println(histogramValues[m]);} 
	         } //End of 'for' loop
	         if (yesTrue2){System.out.println("****************END OF HISTOGRAM VALUES*********************");}
	         
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	         
	         
	         for (int m = 0; m < 256; m++){
	        	 transformHistogram[m] = (int) Math.round((((cdv[m]-cdvMin)/(pixelsTotal - cdvMin))*255));
	         } //End of 'for' loop
	      
	         for(int i=yStart; i<yEnd; i++){
		            for(int j=xStart; j<xEnd; j++){
	                   Color c = new Color(image.getRGB(j, i));
	                   int red = (int)(c.getRed() * 0.299);
		               int green = (int)(c.getGreen() * 0.587);
		               int blue = (int)(c.getBlue() *0.114);
		               rawValue = red+green+blue;
		               for (int k =0; k < 256; k++){
		            	   if(k == rawValue){
		            		   rawValue = transformHistogram[k];
		            		   break;
		            	   	} //End 'if' statement
		               } //End 'for' loop
		               if(yesTrue3){
		               System.out.printf("%3d",rawValue);	               
		               System.out.print("  ");
		               } //End of 'If' Statement
		               Color newColor2 = new Color(rawValue,
		            		   rawValue, rawValue);
		               img2.setRGB(j, i, newColor2.getRGB());
		               } //End 'for' loop
		            if(yesTrue3){System.out.println("");}  
		         } //End 'for' loop
	      
	      }  //End of 'try' block
	      catch (Exception e) {}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	  
	      
	   //Build the new image
	   if(yesTrue4){
		   f = new File("Scans" + File.separator
            + "focusedMal9G63.png");
		   try {
			ImageIO.write(img, "png", f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
	  
	   if(yesTrue5){
		   f = new File("Scans" + File.separator
            + "focusedMal9Eg63.png");
		   try {
			ImageIO.write(img2, "png", f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
	   }
	   	   
	   
	   public static void main(String args[]) throws Exception 
	   {
	      xStart = 95;
	      yStart = 249;
	      xEnd = 195;
	      yEnd = 349;
	      
		  //System.out.print("Would you like the raw pixel values?: ");
		  answer1 = "No";
				  //myScanner.next();
		  //System.out.println("Would you like the historgram values?: ");
		  answer2 = "No";
				  //myScanner.next();
	      //System.out.println("Would you like the histogram equalized pixel values?: ");
	      answer3 = "No";
	    		  //myScanner.next();
		  //System.out.println("Would you like the grayscale image?: ");
		  answer4 = "Yes";
				  //myScanner.next();
		  //System.out.println("Would you like the histogram equalized grayscale image?: ");
		  answer5 = "Yes";
				  //myScanner.next();
	      CTScanImages2 obj = new CTScanImages2();
	   }
	}
	

