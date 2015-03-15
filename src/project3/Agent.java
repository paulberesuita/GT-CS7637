package project3;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import org.opencv.core.*;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;

import java.io.InputStream;
import java.io.ByteArrayInputStream;

import javax.imageio.ImageIO;

import javax.swing.*;

import java.awt.image.BufferedImage;

/**
 * Your Agent for solving Raven's Progressive Matrices. You MUST modify this
 * file.
 * 
 * You may also create and submit new files in addition to modifying this file.
 * 
 * Make sure your file retains methods with the signatures:
 * public Agent()
 * public char Solve(VisualRavensProblem problem)
 * 
 * These methods will be necessary for the project's main method to run.
 * 
 */
public class Agent extends JFrame {
    /**
     * The default constructor for your Agent. Make sure to execute any
     * processing necessary before your Agent starts solving problems here.
     * 
     * Do not add any variables to this signature; they will not be used by
     * main().
     * 
     */
    public Agent() {
        
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

            shapeContourOne = true;

            boolean inBounds = (i + 1 >= 0) && (i + 1 < hierarchyList.size());

            if(inBounds) {

                if((int)Double.parseDouble(hierarchyList.get(i + 1).get(3)) == i) {

                    shapeContourTwo = true;

                    i++;

                }
            }

            MatOfPoint2f  mop2f = new MatOfPoint2f(contours.get(i).toArray());
            MatOfPoint2f approx = new MatOfPoint2f();
            double epsilon = 0.02*Imgproc.arcLength(mop2f,true);
            Imgproc.approxPolyDP(mop2f, approx, epsilon, true);

//          double area2 = Imgproc.contourArea(approx);
            RotatedRect originalRect = Imgproc.minAreaRect(mop2f);
            boolean isConvex = Imgproc.isContourConvex(contours.get(i));


            RavensObject ravenObject = new RavensObject("Z");

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
                if(shapeContourOne && shapeContourTwo) {
                    RavensAttribute fillSquare = new RavensAttribute("fill" , "no");
                    ravenObject.getAttributes().add(fillSquare);

                } else{
                    RavensAttribute fillSquare = new RavensAttribute("fill" , "yes");
                    ravenObject.getAttributes().add(fillSquare);
                }

                //Check size
                double squareArea = Imgproc.contourArea(approx);

                if(squareArea < 4000){
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

                //Triangle found
                RavensAttribute shapeTriangle = new RavensAttribute("shape" , "triangle");
                ravenObject.getAttributes().add(shapeTriangle);

                //Check fill
                if(shapeContourOne && shapeContourTwo) {
                    RavensAttribute fillSquare = new RavensAttribute("fill" , "no");
                    ravenObject.getAttributes().add(fillSquare);

                } else{
                    RavensAttribute fillSquare = new RavensAttribute("fill" , "yes");
                    ravenObject.getAttributes().add(fillSquare);
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

                //check angle
                if(originalRect.angle == -45.0) {
                    RavensAttribute angleTriangle = new RavensAttribute("angle" , "0");
                    ravenObject.getAttributes().add(angleTriangle);
                } else {
                    RavensAttribute angleTriangle = new RavensAttribute("angle" , "45");
                    ravenObject.getAttributes().add(angleTriangle);
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


        Imgproc.threshold(image, image, 127, Imgproc.THRESH_BINARY_INV,1);

        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();

        Imgproc.findContours(image, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        ravensObjects = retrieveImageObjects(contours, hierarchy);

//        RotatedRect originalRect = Imgproc.minAreaRect(mop2f);

//        for(int i=0; i<contours.size(); i++){
//            MatOfPoint2f rect_points[4] = minRect[i].points( rect_points );
//        }
//        // rotated rectangle
//        for( int j = 0; j < 4; j++ )
//
//            line( drawing, rect_points[j], rect_points[(j+1)%4], color, 1,

//        Imgproc.drawContours(image, contours, -1, new Scalar(255,255,0), 1);
////        double area = Imgproc.contourArea(contours.get(0));
//
//        ImageVisible panel = new ImageVisible(convert(image));
//        add(panel);
//        setVisible(true);
//        setSize(400,400);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);

        return ravensObjects;
    }

    /**
     * The primary method for solving incoming Raven's Progressive Matrices.
     * For each problem, your Agent's Solve() method will be called. At the
     * conclusion of Solve(), your Agent should return a String representing its
     * answer to the question: "1", "2", "3", "4", "5", or "6". These Strings
     * are also the Names of the individual RavensFigures, obtained through
     * RavensFigure.getName().
     * 
     * In addition to returning your answer at the end of the method, your Agent
     * may also call problem.checkAnswer(String givenAnswer). The parameter
     * passed to checkAnswer should be your Agent's current guess for the
     * problem; checkAnswer will return the correct answer to the problem. This
     * allows your Agent to check its answer. Note, however, that after your
     * agent has called checkAnswer, it will *not* be able to change its answer.
     * checkAnswer is used to allow your Agent to learn from its incorrect
     * answers; however, your Agent cannot change the answer to a question it
     * has already answered.
     * 
     * If your Agent calls checkAnswer during execution of Solve, the answer it
     * returns will be ignored; otherwise, the answer returned at the end of
     * Solve will be taken as your Agent's answer to this problem.
     * 
     * @param problem the VisualRavensProblem your agent should solve
     * @return your Agent's answer to this problem
     */
    public String Solve(VisualRavensProblem problem) {

        String solution = "1";

//        if(problem.getName().equals("2x1 Basic Problem 06")) {

            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

            //Get All figures
            HashMap<String, VisualRavensFigure> figures = problem.getFigures();

            String figureAPath = figures.get("A").getPath();
            String figureBPath = figures.get("B").getPath();
            String figureCPath = figures.get("C").getPath();
            String figure1Path = figures.get("1").getPath();
            String figure2Path = figures.get("2").getPath();
            String figure3Path = figures.get("3").getPath();
            String figure4Path = figures.get("4").getPath();
            String figure5Path = figures.get("5").getPath();
            String figure6Path = figures.get("6").getPath();

            FrameA frameA = new FrameA(processImage(figureAPath));
            FrameB frameB = new FrameB(processImage(figureBPath));
            FrameC frameC = new FrameC(processImage(figureCPath));

            RavensFigure allFiguresA = new RavensFigure("Figure A");
            RavensFigure allFiguresB = new RavensFigure("Figure B");
            RavensFigure allFiguresC = new RavensFigure("Figure C");

            RavensFigure allFigures1 = new RavensFigure("Figure 1");
            RavensFigure allFigures2 = new RavensFigure("Figure 2");
            RavensFigure allFigures3 = new RavensFigure("Figure 3");
            RavensFigure allFigures4 = new RavensFigure("Figure 4");
            RavensFigure allFigures5 = new RavensFigure("Figure 5");
            RavensFigure allFigures6 = new RavensFigure("Figure 6");

            ArrayList<RavensObject> figureAObjects = processImage(figureAPath);
            ArrayList<RavensObject> figureBObjects = processImage(figureBPath);
            ArrayList<RavensObject> figureCObjects = processImage(figureCPath);

            ArrayList<RavensObject> figure1Objects = processImage(figure1Path);
            ArrayList<RavensObject> figure2Objects = processImage(figure2Path);
            ArrayList<RavensObject> figure3Objects = processImage(figure3Path);
            ArrayList<RavensObject> figure4Objects = processImage(figure4Path);
            ArrayList<RavensObject> figure5Objects = processImage(figure5Path);
            ArrayList<RavensObject> figure6Objects = processImage(figure6Path);

            for(int f=0; f<figureAObjects.size(); f++) {
                allFiguresA.getObjects().add(figureAObjects.get(f));
            }

            for(int f=0; f<figureBObjects.size(); f++) {
                allFiguresB.getObjects().add(figureBObjects.get(f));
            }

            for(int f=0; f<figureCObjects.size(); f++) {
                allFiguresC.getObjects().add(figureCObjects.get(f));
            }

            for(int f=0; f<figure1Objects.size(); f++) {
                allFigures1.getObjects().add(figure1Objects.get(f));
            }

            for(int f=0; f<figure2Objects.size(); f++) {
                allFigures2.getObjects().add(figure2Objects.get(f));
            }

            for(int f=0; f<figure3Objects.size(); f++) {
                allFigures3.getObjects().add(figure3Objects.get(f));
            }

            for(int f=0; f<figure4Objects.size(); f++) {
                allFigures4.getObjects().add(figure4Objects.get(f));
            }

            for(int f=0; f<figure5Objects.size(); f++) {
                allFigures5.getObjects().add(figure5Objects.get(f));
            }

            for(int f=0; f<figure6Objects.size(); f++) {
                allFigures6.getObjects().add(figure6Objects.get(f));
            }

            SemanticNetwork semanticNetwork = new SemanticNetwork(frameA, frameB, frameC);

            System.out.print("Breakpoint");

            HashMap<String, RavensFigure> figuresNoVisual = new HashMap<String, RavensFigure>();
            figuresNoVisual.put("1", allFigures1);
            figuresNoVisual.put("2", allFigures2);
            figuresNoVisual.put("3", allFigures3);
            figuresNoVisual.put("4", allFigures4);
            figuresNoVisual.put("5", allFigures5);
            figuresNoVisual.put("6", allFigures6);


            //Check which algorithm to use
        if(problem.getProblemType().equals("2x1 (Image)")) {

//           if(problem.getName().equals("2x1 Basic Problem 18")) {
            semanticNetwork.generateTransformations2x1();
            GeneratedFrame generatedSolution = new GeneratedFrame(semanticNetwork, allFiguresB, allFiguresC);
            generatedSolution.createFrame2x1();

            System.out.print("Breakpoint");

            String finalSolution = Utility.solution2x1(figuresNoVisual, generatedSolution.generatedFrameDFromC, generatedSolution.isUncertainRotation());
            solution = finalSolution;

//            }
            System.out.println("Finished Running 2x1 Problems");


        } else if(problem.getProblemType().equals("2x2 (Image)")) {

//            if(problem.getName().equals("2x2 Basic Problem 10")) {
            semanticNetwork.generateTransformations2x2();
            GeneratedFrame generatedSolution = new GeneratedFrame(semanticNetwork, allFiguresB, allFiguresC);
            generatedSolution.createFrame2x2();
            String finalSolution = Utility.solution2x1(figuresNoVisual, generatedSolution.generatedFrameDFromBC, generatedSolution.isUncertainRotation());
            solution = finalSolution;
//            }

            System.out.println("Finished Running 2x2 Problems");

        }
//        } //Specific Problem
        return solution;
    }
}
