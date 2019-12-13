package imagecapture;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ContentExtract {
  // Global variables


  public static void main(String[] args) {

    // It loads OpenCv in java.
    nu.pattern.OpenCV.loadShared();

    // Instantiating the Imagecodecs class
    Imgcodecs imageCodecs = new Imgcodecs();
    String file2 = "image.jpeg";
    Mat src2 = Imgcodecs.imread(file2);

    // Creating an empty matrix to store the result
    Mat dst2 = new Mat();

    // Applying GaussianBlur on the Image
    Imgproc.GaussianBlur(src2, dst2, new Size(9, 9), 0);

    // Writing the image
    Imgcodecs.imwrite("Gaussian.jpg", dst2);
    System.out.println("Image Processed");

    Mat edges1 = new Mat();

    // Detecting the edges
    Imgproc.Canny(dst2, edges1, 0, 100);

    // Writing the image
    Imgcodecs.imwrite("canny_output.jpg", edges1);
    System.out.println("Edges Detected");

    Mat dst3 = new Mat();

    // Preparing the kernel matrix object
    Mat kernel1 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(48, 48));

    // Applying dilate on the Image
    Imgproc.dilate(edges1, dst3, kernel1);
    Imgcodecs.imwrite("Dilation.jpg", dst3);

    Mat dst4 = new Mat();
    // finding contour on images
    List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
    Imgproc.findContours(dst3, contours, dst4, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
    System.out.println(contours.size());
    Vector<Mat> rectangles = new Vector<Mat>();

    for (int i = 0; i < contours.size(); i++) {
      Rect rect = Imgproc.boundingRect(contours.get(i));

      Imgproc.rectangle(src2, new Point(rect.x, rect.y),
          new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0), 2);

    }
    Imgcodecs.imwrite("contour_output.jpg", src2);

    Mat dst5 = new Mat();
    String file3 = "contour_output.jpg";
    Mat src3 = Imgcodecs.imread(file3, 0);
    Imgproc.medianBlur(src2, dst5, 5);


    Imgproc.adaptiveThreshold(src3, dst5, 225, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
        Imgproc.THRESH_BINARY, 13, 10);
    Imgcodecs.imwrite("adaptiveThreshold.jpg", dst5);
  }
}
