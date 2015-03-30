package project4;

import org.opencv.core.*;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CVUtils extends JFrame {


    public CVUtils() {

    }

    public static BufferedImage convert(Mat m){
        Mat image_tmp = m;

        MatOfByte matOfByte = new MatOfByte();

        Highgui.imencode(".png", image_tmp, matOfByte);

        byte[] byteArray = matOfByte.toArray();
        BufferedImage bufImage = null;

        try {

            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            return bufImage;
        }
    }

    public static ArrayList<RavensObject> retrieveImageObjects(List<MatOfPoint> contours, Mat hierarchy){

        //Current contour hierarchy
        List<List<String>> hierarchyList = new ArrayList<List<String>>((int)hierarchy.total());

        for(int m=0; m<hierarchy.total(); m++) {

            ArrayList<String> hierarchyArray = new ArrayList<>();

            for (int n=0; n<hierarchy.get(0,m).length; n++){
                hierarchyArray.add(n, Double.toString(hierarchy.get(0,m)[n]));
            }

            hierarchyList.add(m, hierarchyArray);

        }

        ArrayList<RavensObject> ravensObjects = new ArrayList<RavensObject>();

        boolean shapeContourOne = false;
        boolean shapeContourTwo = false;

        for(int i=0; i<contours.size(); i++) {

            MatOfPoint2f mop2f = new MatOfPoint2f(contours.get(i).toArray());
            MatOfPoint2f approx = new MatOfPoint2f();
            double epsilon = 0.02*Imgproc.arcLength(mop2f,true);
            Imgproc.approxPolyDP(mop2f, approx, epsilon, true);

//          double area2 = Imgproc.contourArea(approx);
            RotatedRect originalRect = Imgproc.minAreaRect(mop2f);
            double x = originalRect.center.x;
            double y = originalRect.center.y;
            boolean xInCenter = (x >= 89) && ( x < 93);
            boolean yInCenter = (y >= 89) && ( y < 93);

            boolean isConvex = Imgproc.isContourConvex(contours.get(i));

            shapeContourOne = true;

            boolean inBounds = (i + 1 >= 0) && (i + 1 < hierarchyList.size());

            if(inBounds) {

                if((int)Double.parseDouble(hierarchyList.get(i + 1).get(3)) == i) {

                    shapeContourTwo = true;

                    i++;

                }
            }

            RavensObject ravenObject = new RavensObject("Z");

            double currentObjectArea = Imgproc.contourArea(approx);

            double circleAreaCal = 3.1415 * originalRect.size.width/2 * originalRect.size.width/2;
//            double triangleAreaCal = (originalRect.size.width * originalRect.size.height) / 2;
//            double squareAreaCal = originalRect.size.width * originalRect.size.height;

            double circleDifference = Math.abs(currentObjectArea - circleAreaCal);

            boolean circleLikely = false;

            if(circleDifference < 700) {
                circleLikely = true;
            }

            //Determine Shape
            if(!isConvex && approx.total() == 12) {

                //Plus found
                RavensAttribute shapePlus = new RavensAttribute("shape" , "plus");
                ravenObject.getAttributes().add(shapePlus);

                //Check fill
                if(shapeContourOne && shapeContourTwo) {
                    RavensAttribute fillPlus = new RavensAttribute("fill" , "no");
                    ravenObject.getAttributes().add(fillPlus);

                } else{
                    RavensAttribute fillPlus = new RavensAttribute("fill" , "yes");
                    ravenObject.getAttributes().add(fillPlus);
                }

                //Check size
                double plusAres = Imgproc.contourArea(approx);

                if(plusAres < 4000){
                    RavensAttribute sizePlus = new RavensAttribute("size" , "small");
                    ravenObject.getAttributes().add(sizePlus);
                } else {
                    RavensAttribute sizePlus = new RavensAttribute("size" , "large");
                    ravenObject.getAttributes().add(sizePlus);
                }

                //check if child
                if(shapeContourOne && shapeContourTwo) {
                    if ((int) Double.parseDouble(hierarchyList.get(i - 1).get(3)) != -1) {
                        RavensAttribute insidePlus = new RavensAttribute("inside", "Z");
                        ravenObject.getAttributes().add(insidePlus);
                    }
                }

                //check angle

                if(originalRect.angle == -0.0) {
                    RavensAttribute anglePlus = new RavensAttribute("angle" , "45");
                    ravenObject.getAttributes().add(anglePlus);
                } else {
                    RavensAttribute anglePlus = new RavensAttribute("angle" , "0");
                    ravenObject.getAttributes().add(anglePlus);
                }

                //check height
                //There is more than 1 object to check if an object is above or not
                if(contours.size() > 2 && i > 1) {

                    MatOfPoint2f  nextObject = new MatOfPoint2f(contours.get(i-2).toArray());
                    RotatedRect originalRect2 = Imgproc.minAreaRect(nextObject);

                    if(originalRect.center.y < originalRect2.center.y) {
                        RavensAttribute anglePlus = new RavensAttribute("above" , "Z");
                        ravenObject.getAttributes().add(anglePlus);
                    }

                }


            }
            else if(!isConvex && approx.total() == 8 && !xInCenter && !yInCenter && !circleLikely) {

                RavensAttribute shapePacman = new RavensAttribute("shape" , "Pac-Man");
                ravenObject.getAttributes().add(shapePacman);

                //Will use x, y to determine angle

                if(originalRect.center.x < 95 && originalRect.center.y < 95) {

                    RavensAttribute anglePlus = new RavensAttribute("angle" , "45");
                    ravenObject.getAttributes().add(anglePlus);

                } else if(originalRect.center.x > 95 && originalRect.center.y < 95) {

                    RavensAttribute anglePlus = new RavensAttribute("angle" , "135");
                    ravenObject.getAttributes().add(anglePlus);

                } else if(originalRect.center.x < 95 && originalRect.center.y > 95) {

                    RavensAttribute anglePlus = new RavensAttribute("angle" , "315");
                    ravenObject.getAttributes().add(anglePlus);

                } else if(originalRect.center.x > 95 && originalRect.center.y > 95) {

                    RavensAttribute anglePlus = new RavensAttribute("angle" , "225");
                    ravenObject.getAttributes().add(anglePlus);

                } else {
                    RavensAttribute anglePlus = new RavensAttribute("angle" , "0");
                    ravenObject.getAttributes().add(anglePlus);
                }

            }
            else if(approx.total() == 4) {

                //Square or Diamond found
//                if(originalRect.angle == -45.0) {
//                    RavensAttribute shapeSquare = new RavensAttribute("shape" , "diamond");
//                    ravenObject.getAttributes().add(shapeSquare);
//                } else {
                RavensAttribute shapeSquare = new RavensAttribute("shape" , "square");
                ravenObject.getAttributes().add(shapeSquare);
//                }

                //Check fill
                boolean isPartial = false;

                //we should use i + 1; but it already has been increased previously
                MatOfPoint2f mop2fCheckFill = new MatOfPoint2f(contours.get(i).toArray());
                MatOfPoint2f approxCheckFill = new MatOfPoint2f();
                double epsilonCheckFill = 0.02*Imgproc.arcLength(mop2fCheckFill,true);
                Imgproc.approxPolyDP(mop2fCheckFill, approxCheckFill, epsilonCheckFill, true);

                double area1 = Imgproc.contourArea(approx);
                double area2 = Imgproc.contourArea(approxCheckFill);
                double difference = area1 - area2;

                //The found contour is a square
                if(approxCheckFill.total() == 4 && difference > 5000) {
                    isPartial = true;
                }

                if(shapeContourOne && shapeContourTwo && !isPartial) {
                    RavensAttribute fillSquare = new RavensAttribute("fill" , "no");
                    ravenObject.getAttributes().add(fillSquare);

                } else if(isPartial) {

                    RotatedRect originalRectCheckFill = Imgproc.minAreaRect(mop2fCheckFill);

                    if(originalRectCheckFill.center.x > 100) {
                        RavensAttribute fillSquare = new RavensAttribute("fill" , "left-half");
                        ravenObject.getAttributes().add(fillSquare);
                    } else if(originalRectCheckFill.center.y > 100) {
                        RavensAttribute fillSquare = new RavensAttribute("fill" , "top-half");
                        ravenObject.getAttributes().add(fillSquare);
                    } else {
                        RavensAttribute fillSquare = new RavensAttribute("fill" , "right-half");
                        ravenObject.getAttributes().add(fillSquare);
                    }

                } else{
                    RavensAttribute fillSquare = new RavensAttribute("fill" , "yes");
                    ravenObject.getAttributes().add(fillSquare);
                }

                //Check size
                double squareArea = Imgproc.contourArea(approx);

                if(squareArea < 4400){
                    RavensAttribute sizeSquare = new RavensAttribute("size" , "small");
                    ravenObject.getAttributes().add(sizeSquare);
                } else {
                    RavensAttribute sizeSquare = new RavensAttribute("size" , "large");
                    ravenObject.getAttributes().add(sizeSquare);
                }

                //check if child
                if(shapeContourOne && shapeContourTwo) {
                    if ((int) Double.parseDouble(hierarchyList.get(i - 1).get(3)) != -1) {
                        RavensAttribute insideSquare = new RavensAttribute("inside", "Z");
                        ravenObject.getAttributes().add(insideSquare);
                    }
                }

                //check angle
                if(originalRect.angle == -45.0) {
                    RavensAttribute angleSquare = new RavensAttribute("angle" , "45");
                    ravenObject.getAttributes().add(angleSquare);
                } else {
                    RavensAttribute angleSquare = new RavensAttribute("angle" , "0");
                    ravenObject.getAttributes().add(angleSquare);
                }

                //check height
                //There is more than 1 object to check if an object is above or not
                if(contours.size() > 2 && i > 1) {

                    MatOfPoint2f  nextObject = new MatOfPoint2f(contours.get(i-2).toArray());
                    RotatedRect originalRect2 = Imgproc.minAreaRect(nextObject);

                    if(originalRect.center.y < originalRect2.center.y) {
                        RavensAttribute anglePlus = new RavensAttribute("above" , "Z");
                        ravenObject.getAttributes().add(anglePlus);
                    }

                }

            } else if(approx.total() == 3) {

                //Finding greatets flat for triangle
                String pointing = "";
                double greatestX = 0;
                double greatestY = 0;
                int xCount = 0;
                int yCount = 0;
                double alotOfXCordinate = 0;
                double alotOfYCordinate = 0;

                double tempX = 0;
                double tempY = 0;

                Point[] pointsArray = contours.get(i).toArray();

                boolean continueX = false;
                boolean continueY = false;

                for(int n=0; n<pointsArray.length; n++) {

                    if(pointsArray[n].x == tempX) {

                        if(continueX) {
                            xCount ++;
                            alotOfXCordinate = pointsArray[n].x;
                        }
                        continueX = true;
                    } else {
                        continueX = false;

                    }

                    if(pointsArray[n].y == tempY) {

                        if(continueY) {
                            yCount ++;
                            alotOfYCordinate = pointsArray[n].y;
                        }
                        continueY = true;
                    } else {
                        continueY = false;
                    }

                    tempX = pointsArray[n].x;
                    tempY = pointsArray[n].y;

                    if(pointsArray[n].x > greatestX) {
                        greatestX = pointsArray[n].x;
                    }
                    if(pointsArray[n].y > greatestY) {
                        greatestY = pointsArray[n].y;
                    }
                }
                double checkTriangleOnEdge = Math.abs(xCount - yCount);

                //For problems such as 2x2 Problme 4
                if(checkTriangleOnEdge < 5 && (( alotOfXCordinate < 10 || alotOfYCordinate < 10) || (alotOfXCordinate > 170 || alotOfYCordinate > 170)) ) {

                    //Triangle found
                    RavensAttribute shapeTriangle = new RavensAttribute("shape" , "right-triangle");
                    ravenObject.getAttributes().add(shapeTriangle);

                    if(alotOfXCordinate > 100 && alotOfYCordinate < 10) {
                        RavensAttribute angleTriangle = new RavensAttribute("angle" , "180");
                        ravenObject.getAttributes().add(angleTriangle);
                    } else if(alotOfXCordinate < 100 && alotOfYCordinate < 10) {
                        RavensAttribute angleTriangle = new RavensAttribute("angle" , "90");
                        ravenObject.getAttributes().add(angleTriangle);
                    } else if(alotOfXCordinate > 100 && alotOfYCordinate > 100) {
                        RavensAttribute angleTriangle = new RavensAttribute("angle" , "270");
                        ravenObject.getAttributes().add(angleTriangle);
                    } else {
                        RavensAttribute angleTriangle = new RavensAttribute("angle" , "0");
                        ravenObject.getAttributes().add(angleTriangle);
                    }

                } else {

                    //Triangle found
                    RavensAttribute shapeTriangle = new RavensAttribute("shape" , "triangle");
                    ravenObject.getAttributes().add(shapeTriangle);

                    boolean isPartial = false;

                    //we should use i + 1; but it already has been increased previously
                    MatOfPoint2f mop2fCheckFill = new MatOfPoint2f(contours.get(i).toArray());
                    MatOfPoint2f approxCheckFill = new MatOfPoint2f();
                    double epsilonCheckFill = 0.02*Imgproc.arcLength(mop2fCheckFill,true);
                    Imgproc.approxPolyDP(mop2fCheckFill, approxCheckFill, epsilonCheckFill, true);

                    double area1 = Imgproc.contourArea(approx);
                    double area2 = Imgproc.contourArea(approxCheckFill);
                    double difference = area1 - area2;

                    //The found contour is a square
                    if(approxCheckFill.total() == 3 && difference > 5000) {
                        isPartial = true;
                    }

                    //Check fill
                    if(shapeContourOne && shapeContourTwo && !isPartial) {
                        RavensAttribute fillTriangle = new RavensAttribute("fill" , "no");
                        ravenObject.getAttributes().add(fillTriangle);

                    } else if(isPartial) {

                        RotatedRect originalRectCheckFill = Imgproc.minAreaRect(mop2fCheckFill);

                        if(originalRectCheckFill.center.x > 100) {
                            RavensAttribute fillTriangle = new RavensAttribute("fill" , "left-half");
                            ravenObject.getAttributes().add(fillTriangle);
                        } else if(originalRectCheckFill.center.y > 100) {
                            RavensAttribute fillTriangle = new RavensAttribute("fill" , "top-half");
                            ravenObject.getAttributes().add(fillTriangle);
                        } else {
                            RavensAttribute fillTriangle = new RavensAttribute("fill" , "right-half");
                            ravenObject.getAttributes().add(fillTriangle);
                        }

                    } else{
                        RavensAttribute fillTriangle = new RavensAttribute("fill" , "yes");
                        ravenObject.getAttributes().add(fillTriangle);
                    }

                    //Check size
                    double triangleArea = Imgproc.contourArea(approx);

                    if(triangleArea < 4000){
                        RavensAttribute sizeSquare = new RavensAttribute("size" , "small");
                        ravenObject.getAttributes().add(sizeSquare);
                    } else {
                        RavensAttribute sizeSquare = new RavensAttribute("size" , "large");
                        ravenObject.getAttributes().add(sizeSquare);
                    }

                    //check if child
                    if(shapeContourOne && shapeContourTwo) {
                        if ((int) Double.parseDouble(hierarchyList.get(i - 1).get(3)) != -1) {
                            RavensAttribute insideTriangle = new RavensAttribute("inside", "Z");
                            ravenObject.getAttributes().add(insideTriangle);
                        }
                    }


                        if(yCount > 50 && alotOfYCordinate > originalRect.center.y) {
                            pointing = "up";
                            RavensAttribute angleTriangle = new RavensAttribute("angle" , "0");
                            ravenObject.getAttributes().add(angleTriangle);
                        } else if(yCount > 50 && alotOfYCordinate < originalRect.center.y) {
                            pointing = "down";
                            RavensAttribute angleTriangle = new RavensAttribute("angle" , "180");
                            ravenObject.getAttributes().add(angleTriangle);
                        } else if(xCount > 50 && alotOfXCordinate > originalRect.center.x) {
                            pointing = "left";
                            RavensAttribute angleTriangle = new RavensAttribute("angle" , "270");
                            ravenObject.getAttributes().add(angleTriangle);
                        } else if(xCount > 50 && alotOfXCordinate < originalRect.center.x) {
                            pointing = "right";
                            RavensAttribute angleTriangle = new RavensAttribute("angle" , "90");
                            ravenObject.getAttributes().add(angleTriangle);
                        }

                    //check height
                    //There is more than 1 object to check if an object is above or not
                    if(contours.size() > 2 && i > 1) {

                        MatOfPoint2f  nextObject = new MatOfPoint2f(contours.get(i-2).toArray());
                        RotatedRect originalRect2 = Imgproc.minAreaRect(nextObject);

                        if(originalRect.center.y < originalRect2.center.y && originalRect.center.x == originalRect2.center.x && contours.size() < 8) {
                            RavensAttribute anglePlus = new RavensAttribute("above" , "Z");
                            ravenObject.getAttributes().add(anglePlus);
                        }

                    }
                }


            } else {

                //Cicle probably found
                RavensAttribute shapeCircle = new RavensAttribute("shape"  , "circle");
                ravenObject.getAttributes().add(shapeCircle);

                //Check fill
                if(shapeContourOne && shapeContourTwo) {
                    RavensAttribute fillCircle = new RavensAttribute("fill" , "no");
                    ravenObject.getAttributes().add(fillCircle);

                } else{
                    RavensAttribute fillCircle = new RavensAttribute("fill" , "yes");
                    ravenObject.getAttributes().add(fillCircle);
                }

                //Check size
                double circleArea = Imgproc.contourArea(approx);

                if(circleArea < 6000){
                    RavensAttribute sizeCircle = new RavensAttribute("size" , "small");
                    ravenObject.getAttributes().add(sizeCircle);
                } else {
                    RavensAttribute sizeCircle = new RavensAttribute("size" , "large");
                    ravenObject.getAttributes().add(sizeCircle);
                }

                //check if child
                if(shapeContourOne && shapeContourTwo) {
                    if ((int) Double.parseDouble(hierarchyList.get(i - 1).get(3)) >= 0) {
                        RavensAttribute insideCircle = new RavensAttribute("inside", "Z");
                        ravenObject.getAttributes().add(insideCircle);
                    }
                }

                //check angle
                if(originalRect.angle == -45.0) {
                    RavensAttribute angleCircle = new RavensAttribute("angle" , "0");
                    ravenObject.getAttributes().add(angleCircle);
                } else if(originalRect.angle == -0.0) {
                    RavensAttribute angleCircle = new RavensAttribute("angle" , "0");
                    ravenObject.getAttributes().add(angleCircle);
                } else {

                }

                //check height
                //There is more than 1 object to check if an object is above or not
                if(contours.size() > 2 && i > 1) {

                    MatOfPoint2f  nextObject = new MatOfPoint2f(contours.get(i-2).toArray());
                    RotatedRect originalRect2 = Imgproc.minAreaRect(nextObject);

                    if(originalRect.center.y < originalRect2.center.y) {
                        RavensAttribute anglePlus = new RavensAttribute("above" , "Z");
                        ravenObject.getAttributes().add(anglePlus);
                    }

                }

            }

            //Add all of the objects with its properties
            ravensObjects.add(ravenObject);

            shapeContourOne = false;
            shapeContourTwo = false;
        }

        return ravensObjects;
    }

    public ArrayList<RavensObject> processImage(String imagePath){

        ArrayList<RavensObject> ravensObjects = null;

        Mat image = Highgui.imread(imagePath, 0);


        Imgproc.threshold(image, image, 127, Imgproc.THRESH_BINARY_INV, 1);

        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();

        Imgproc.findContours(image, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_NONE);

        ravensObjects = retrieveImageObjects(contours, hierarchy);

//        RotatedRect originalRect = Imgproc.minAreaRect(mop2f);

//        for(int i=0; i<contours.size(); i++){
//            MatOfPoint2f rect_points[4] = minRect[i].points( rect_points );
//        }
//        // rotated rectangle
//        for( int j = 0; j < 4; j++ )
//
//            line( drawing, rect_points[j], rect_points[(j+1)%4], color, 1,

        Imgproc.drawContours(image, contours, -1, new Scalar(255,255,0), 1);
//        double area = Imgproc.contourArea(contours.get(0));

//        ImageVisible panel = new ImageVisible(convert(image));
//        add(panel);
//        setVisible(true);
//        setSize(400,400);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);

        return ravensObjects;
    }

}
