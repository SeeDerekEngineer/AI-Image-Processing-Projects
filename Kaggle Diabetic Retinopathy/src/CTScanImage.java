import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
	
public class CTScanImage {
	   private static Scanner myScanner = new Scanner(System.in);
	   private BufferedImage  image;
	   private int[] histogramValues = new int[256];
	   private static String answer1;
	   private static String answer2;
	   private static String answer3;
	   private static String answer4;
	   private static String answer5;
	   private static String answer6;
	   private static String answer7;
	   private static String answer8;
	   private boolean yesTrue1;
	   private boolean yesTrue2;
	   private boolean yesTrue3;
	   private boolean yesTrue4;
	   private boolean yesTrue5;
	   private boolean yesTrue6;
	   private boolean yesTrue7;
	   private boolean yesTrue8;
	   private double pixelsTotal = 0;
	   private int[] transformHistogram = new int[256];
	   private int[] equalizedHistogram = new int[256];
	   private int[] norEqualizedHistogram = new int[256];
	   private double cdvMin=255;
	   private double cdvMax=0;
	   private double[] cdv = new double[256];
	   File f = null;
	  
	   private static double compassConstant = 0.7071;
	   private int[] differences = new int[8];
	   
	   private BufferedImage img;
	   private BufferedImage img2;
	   private BufferedImage img3;
	   
	   PrintWriter results = new PrintWriter("results.csv"); 
	   
	   private String imgFile = "16_right.jpeg";			/////////////Your Image Here////////////
	   
	   public CTScanImage() throws FileNotFoundException {
	   
		Scanner scan = new Scanner(new File("ProblemSetList.txt"));   
		   
		while(scan.hasNext()) {                                            // Load each image in order.
           String fileName = scan.nextLine();
		   
		   try {
	    	  
		     
		   
		  //System.out.print("Would you like the raw pixel values?: ");
		  answer1 = "No";
				  //myScanner.next();
		  //System.out.println("Would you like the histogram values?: ");
		  answer2 = "no";
	      //System.out.println("Would you like the histogram equalized pixel values?: ");
	      answer3 = "No";
	    		  //myScanner.next();
		  //System.out.println("Would you like the grayscale image?: ");
		  answer4 = "No";
		  //System.out.println("Would you like the histogram equalized grayscale image?: ");
		  answer5 = "No";
				  //myScanner.next();
		  //System.out.println("Would you like the equalized histogram values?: ");
		  answer6 = "No";
		  //System.out.println("Would you like the Compass Image?");
		  answer7 = "No";
		  //System.out.println("Would you like the normalized histogram values?");
		  answer8 = "No";
	    	  
	    	  
	    	  File input = new File("Scans" + File.separator    //Obtain the jpeg file
            + fileName);
	         image = ImageIO.read(input);
	         yesTrue1 = answer1.equals("Yes") || answer1.equals("yes") || answer1.equals("YES") || answer1.equals("Y") || answer1.equals("y");
	         yesTrue2 = answer2.equals("Yes") || answer2.equals("yes") || answer2.equals("YES") || answer2.equals("Y") || answer2.equals("y");
	         yesTrue3 = answer3.equals("Yes") || answer3.equals("yes") || answer3.equals("YES") || answer3.equals("Y") || answer3.equals("y");
	         yesTrue4 = answer4.equals("Yes") || answer4.equals("yes") || answer4.equals("YES") || answer4.equals("Y") || answer4.equals("y");
	         yesTrue5 = answer5.equals("Yes") || answer5.equals("yes") || answer5.equals("YES") || answer5.equals("Y") || answer5.equals("y");
	         yesTrue6 = answer6.equals("Yes") || answer6.equals("yes") || answer6.equals("YES") || answer6.equals("Y") || answer6.equals("y");
	         yesTrue7 = answer7.equals("Yes") || answer7.equals("yes") || answer7.equals("YES") || answer7.equals("Y") || answer7.equals("y");
	         yesTrue8 = answer8.equals("Yes") || answer8.equals("yes") || answer8.equals("YES") || answer8.equals("Y") || answer8.equals("y");
	        
	         BufferedImage bimg = ImageIO.read(input);
	         int width          = bimg.getWidth();
	         int height         = bimg.getHeight();

	         int xStart = 0;							
	  	     int xEnd = width;							
	  	     int yStart = 0;							
	  	     int yEnd = height;
	  	     int boxXStart = 0;							///////// Your Parameters Here ////////
		     int boxXEnd = width;						
		     int boxXStart2 = 0;
		     int boxXEnd2 = width;
		     int boxYStart = 0;
		     int boxYEnd = height;
		     int increment = 20;						//////////////////////////////////////
	  	     
	  	     
	  	     img = new BufferedImage(xEnd, yEnd, BufferedImage.TYPE_INT_RGB);   //Determines size of produced image
		     img2 = new BufferedImage(xEnd, yEnd, BufferedImage.TYPE_INT_RGB);  //Determines size of produced image
		     img3 = new BufferedImage(xEnd, yEnd, BufferedImage.TYPE_INT_RGB);  //Determines size of produced image
		   
		     int[][] rawValue = new int[xEnd][yEnd];
		     int[][] lhnewValue = new int[xEnd][yEnd];
		     int[][] lhAverage = new int[xEnd][yEnd];
		     int[][] count = new int[xEnd][yEnd]; 
	         
	         
		     
		     for(int i=yStart; i<yEnd ; i++){
	            for(int j=xStart; j<xEnd ; j++){				//Establish pixel values
                   Color c = new Color(image.getRGB(j, i));
	               int red = (int)(c.getRed() * 0.299);
	               int green = (int)(c.getGreen() * 0.587);
	               int blue = (int)(c.getBlue() *0.114);
	               rawValue[j][i] = red+green+blue;			
	               Color newColor = new Color(rawValue[j][i],
	               rawValue[j][i],rawValue[j][i]);
	               img.setRGB(j,i,newColor.getRGB());   //Establish grayscale image
	            }
	         }
	        
lhLoop:	     for (int zz = 0; zz < 1000; zz++){						//Start of local histogram equalization loop y
	 		 for (int z = 0; z < 1000; z++){                         //Start of local histogram equalization loop x
outerloop:	    for(int i=boxYStart; i<boxYEnd ; i++){					//Set 'boxYEnd' and 'boxXEnd' to 'yEnd' and 'xEnd' for non lh
		            for(int j=boxXStart; j<boxXEnd ; j++){      
	
		         if(yesTrue7){ 										//DeLeo Comapss Gradient
                   if(j > 0 && i > 0){								
	               differences[0] = (int) Math.abs(rawValue[j][i]-(compassConstant*(rawValue[j-1][i-1] - rawValue[j][i])));
	               }	else {differences[0] = 0;}
	               if(i > 0){
	               differences[1] = Math.abs(rawValue[j][i]-rawValue[j][i-1]);
	               }	else {differences[1] = 0;}
	               if(j < xEnd-1 && i > 0){
	               differences[2] = (int) Math.abs(rawValue[j][i]-(compassConstant*(rawValue[j+1][i-1] - rawValue[j][i])));
	               }	else {differences[2] = 0;}
	               if(j > 0){
	               differences[3] = Math.abs(rawValue[j][i]-rawValue[j-1][i]);
	               }	else {differences[3] = 0;}
	               if(j < xEnd - 1){
	               differences[4] = Math.abs(rawValue[j][i]-rawValue[j+1][i]);
	               }	else {differences[4] = 0;}
	               if(j > 0 && i < yEnd - 1){
	               differences[5] = (int) Math.abs(rawValue[j][i]-(compassConstant*(rawValue[j-1][i+1] - rawValue[j][i])));
	               }	else {differences[5] = 0;}
	               if(i < yEnd - 1){
	               differences[6] = Math.abs(rawValue[j][i]-rawValue[j][i+1]);
	               }	else {differences[6] = 0;}
	               if(j < xEnd - 1 && i < yEnd - 1){
	               differences[7] = (int) Math.abs(rawValue[j][i]-(compassConstant*(rawValue[j+1][i+1] - rawValue[j][i])));	               
	               }	else {differences[7] = 0;}
	              
	               rawValue[j][i] = differences[0];
	               for(int dd = 1; dd < 8; dd++){
	            	   if(differences[dd] > rawValue[j][i]){rawValue[j][i] = differences[dd];} 
	               }
	             }//End if statement  
		           
		            	 if(j >= xEnd){
		     	        	break;
		     	        }//End if statement
		     		               
		     	        if(i >= yEnd){
		     	        	break outerloop;
		     	        }  
	             
		            	
		            	
		          for (int k =0; k < 256; k++){     //Establish Histogram values
	            	   if(k == rawValue[j][i]){
	            		   histogramValues[k] = histogramValues[k] + 1;  	   
	            		   break;
	            	   	} //End 'if' statement
	            	  
	    		            		 
		           } //End 'for' loop
	               cdv[0] = histogramValues[0];
	               
	               if(yesTrue1){
	               System.out.printf("%3d",rawValue[j][i]);	               
	               System.out.print("  ");
	               } //End of 'If' Statement
	               } //End 'for' loop j iteration
	            if(yesTrue1){System.out.println("");}
	            
	         
	         } //End 'for' loop i iteration
	         if(yesTrue1){System.out.println("*****************END OF RAW PIXEL VALUES*********************");}
	         
	         
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	         
	         //Establish cdv variable used for histogram equalization of pixel values
	         //Print histogram values
	         
	         for (int m = 0; m < 256; m++){
	        	 pixelsTotal = pixelsTotal + histogramValues[m];
	        	 if (histogramValues[m] > cdvMax){cdvMax = histogramValues[m];}
	        	 if (histogramValues[m] < cdvMin && histogramValues[m] != 0){cdvMin = histogramValues[m];}   //Establish cdvMin
          	     if (m != 0){cdv[m] = cdv[m-1] + histogramValues[m];}   									 //Establish cdv[]
          	     
          	     if (yesTrue2){System.out.println(histogramValues[m]);} 
	    
	         } //End of 'for' loop
	         if (yesTrue2){System.out.println("****************END OF HISTOGRAM VALUES*********************");}
	         
	         for (int m = 0; m < 256; m++){
	        	 norEqualizedHistogram[m] = (int) ((histogramValues[m]*1000/pixelsTotal));
	        	 if (yesTrue8){System.out.println(norEqualizedHistogram[m]);}
	         }
	         if(yesTrue8){System.out.println("END OF NORMALIZED EQUALIZED HISTOGRAM VALUES");}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	         
	         //Establish histogram equalized pixel values
	         
	         for (int m = 0; m < 256; m++){
	        	 transformHistogram[m] = (int) Math.round((((cdv[m]-cdvMin)/(pixelsTotal - cdvMin))*255));
	         } //End of 'for' loop
	         
outloop:	     for(int i=boxYStart; i<boxYEnd ; i++){
		            for(int j=boxXStart; j<boxXEnd ; j++){
	  
	        if(j >= xEnd){
	        	break;
	        }//End if statement
		               
	        if(i >= yEnd){
	        	break outloop;
	        }
		    
	        
		               for (int k =0; k < 256; k++){               //Conversion of pixel values to histogram equalized pixel values
		            	   if(k == rawValue[j][i]){
		            		//   rawValue[j][i] = transformHistogram[k];		//Block out if using lh
		            		   
		            //		   lhAverage[j][i] = transformHistogram[k]; 		//Block out if not using lh
		            		   
		            		   count[j][i]++;								//Block out if not using lh with averaging
		            		   lhnewValue[j][i] = (lhnewValue[j][i] + transformHistogram[k]);
		            		   lhAverage[j][i] = lhnewValue[j][i]/count[j][i];
		            		   break;
		            	   	} //End 'if' statement
		               } //End 'for' loop
		               
		               
		               for (int k = 0; k< 256; k++){
		            	   if(k == rawValue[j][i]){
		            		   equalizedHistogram[k] = equalizedHistogram[k] + 1;
		            		   break;
		            	   } //End 'if' statement
		               } //End 'for' loop
		               if(yesTrue3){
		               System.out.println(lhAverage[j][i]);	               
		              // System.out.print("  ");
		               } //End of 'If' Statement
		               
		               if(lhAverage[j][i] >= 255){lhAverage[j][i] = 255;}
		 //              if(j >= 492 && j < 592 && i >= 572 && i < 672){
		               Color newColor2 = new Color(lhAverage[j][i],
		            		   lhAverage[j][i], lhAverage[j][i]);
		               img2.setRGB(j, i, newColor2.getRGB());
		 //              }
		               
		              if(yesTrue7){
  	              if(lhAverage[j][i] > 0) {							
		            	   lhAverage[j][i] = 255;
		               }	
		              else {lhAverage[j][i] = 0;}
		               Color newColor3 = new Color(lhAverage[j][i],
		            		   lhAverage[j][i], lhAverage[j][i]);
	               		img3.setRGB(j, i, newColor3.getRGB());
	      				}
		               
		            
		            } //End 'for' loop
		           // if(yesTrue3){System.out.println("");}  
		         } //End 'for' loop
			
	
		
	if(boxXStart == xStart && boxXEnd == xEnd && boxYStart == yStart && boxYEnd == yEnd ){	//To prevent lh equalization
		break lhLoop;
	}
		
												//Block out this section when not using lh
	
	
		for(int k = 0; k < 256; k++){				//for loop to reset histogram algorithms
			histogramValues[k] = 0;					//This is for local histogram equalization.  
			cdv[k] = 0;
			cdvMin = 255;
			cdvMax = 0;
			transformHistogram[k] = 0;
			pixelsTotal = 0;
		}//End for loop
	
		boxXStart = boxXStart + increment;			//The increment is used for local histogram
		boxXEnd = boxXEnd + increment;
	
	}//End local histogram equalization for loop x
	 	
	 	boxXStart = boxXStart2;
	 	boxXEnd = boxXEnd2;
	 	boxYStart = boxYStart + increment;			//The increment is used for local histogram
		boxYEnd = boxYEnd + increment;
		
		if(zz < Math.round(yEnd/increment)){System.out.println("Row " + (zz+1) + " assembled out of " + 
		Math.round(yEnd/increment));
		} //End if
		
	}//End local histogram equalization for loop y
	        
	    System.out.println("Image fully assembled.");
	 
	      }  //End of 'try' block
	      catch (Exception e) {System.out.println("Did you put an image, because I got nothing?");}
	      
	      // Print Equalized Histogram Values
	      for (int m = 0; m < 256; m++){
	    	  if(yesTrue6){System.out.println(equalizedHistogram[m]);}
	         } //End of 'for' loop
	      	if(yesTrue6){System.out.println("END OF EQUALIZED HISTOGRAM VALUES");}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	  
	   //Build the new image
	      
	   if(yesTrue4){
		   f = new File("NewScans" + File.separator
            + "GrayScan.png");
		   try {
			ImageIO.write(img, "png", f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
	  
	   if(yesTrue5){
		   f = new File("NewScans" + File.separator
            + "EqualizedScan.png");
		   try {
			ImageIO.write(img2, "png", f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   }
	
	   if(yesTrue7){
		   f = new File("NewScans" + File.separator
		            + "CompassScan.png");
				   try {
					ImageIO.write(img3, "png", f);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	   }//End if
	   
	   results.println(fileName);
	   for(int m = 0; m < 256; m++){
	   results.println(histogramValues[m]);
	   }
	   
	   
		}//End while loop
		results.close();
	   }//End CTScanImage Class
	   	   
	   
	   public static void main(String args[]) throws Exception 
	   {
	     		  
		  CTScanImage obj = new CTScanImage();
	   }
	}