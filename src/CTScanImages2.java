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
	   private int rawValue;
	   private int[] histogramValues = new int[256];
	   private static String answer1;
	   private static String answer2;
	   boolean yesTrue1;
	   boolean yesTrue2;
	   
	   public CTScanImages2() {
	      try {
	         File input = new File("Scans" + File.separator
            + "000063.jpg");
	         image = ImageIO.read(input);
	         yesTrue1 = answer1.equals("Yes") || answer1.equals("yes") || answer1.equals("YES") || answer1.equals("Y") || answer1.equals("y");
	         yesTrue2 = answer2.equals("Yes") || answer2.equals("yes") || answer2.equals("YES") || answer2.equals("Y") || answer2.equals("y");
	         for(int i=yStart; i<yStart + 100; i++){
	            for(int j=xStart; j<xStart + 100; j++){
                   Color c = new Color(image.getRGB(j, i));
	               rawValue = (int) Math.round(c.getRed()*0.21 + c.getGreen()*0.72 + c.getBlue()*0.07);
	               for (int k =0; k < 256; k++){
	            	   if(k == rawValue){histogramValues[k] = histogramValues[k] + 1;}
	               }
	               if(yesTrue1){
	               System.out.printf("%3d",rawValue);	               
	               System.out.print("  ");
	               } //End of 'If' Statement
	               }
	            if(yesTrue1){System.out.println("");}
	            
	         }
	         if(yesTrue1){System.out.println("*****************END OF RAW PIXEL VALUES*********************");}
	         
	         for (int m = 0; m < 256; m++){
	        	 if (yesTrue2){
	        		 System.out.println(histogramValues[m]);
	        	 } //End of 'If' Statement
	         } //End of 'for' Statement
	      } catch (Exception e) {}
	   }
	   public static void main(String args[]) throws Exception 
	   {
	      System.out.print("X Start: ");
	      xStart = myScanner.nextInt();
	      System.out.print("Y Start: ");
	      yStart = myScanner.nextInt();
		  System.out.print("Would you like the raw values?: ");
		  answer1 = myScanner.next();
		  System.out.println("Would you like the historgram values?: ");
		  answer2 = myScanner.next();
	      CTScanImages2 obj = new CTScanImages2();
	   }
	}
	

