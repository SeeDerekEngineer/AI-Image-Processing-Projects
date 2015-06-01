

//Unused Class for practice and learning purposes only



import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
	 
	public class CTScanImage extends Component {
	 
	  public static void main(String[] args) {
	    CTScanImage newImage = new CTScanImage();
	    newImage.JavaWalkBufferedImageTest1();
	  }
	 
	  public void printPixelARGB(int pixel) {
	    int alpha = (pixel >> 24) & 0xff;
	    int red = (pixel >> 16) & 0xff;
	    int green = (pixel >> 8) & 0xff;
	    int blue = (pixel) & 0xff;
	    System.out.println("rgb:" red + ", " + green + ", " + blue);
	  }
	 
	  private void marchThroughImage(BufferedImage image) {
	    int w = image.getWidth();
	    int h = image.getHeight();
	    System.out.println("width, height: " + w + ", " + h);
	 
	    for (int i = 0; i < h; i++) {
	      for (int j = 0; j < w; j++) {
	        System.out.println("x,y: " + j + ", " + i);
	        int pixel = image.getRGB(j, i);
	        printPixelARGB(pixel);
	        System.out.println("");
	      }
	    }
	  }
	 
	  public  void JavaWalkBufferedImageTest1() {
	    try {
	      // get the BufferedImage, using the ImageIO class
	      BufferedImage image = 
	        ImageIO.read(new File("Scans" + File.separator
            + "Auggie with goggles.jpg"));
	      marchThroughImage(image);
	    } catch (IOException e) {
	      System.err.println(e.getMessage());
	    }
	  }
	
	}


