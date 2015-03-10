package project3;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import org.opencv.core.*;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import java.awt.image.BufferedImage;
import java.awt.Panel;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.awt.image.DataBufferByte;
import javax.imageio.ImageIO;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.util.Vector;

import org.opencv.highgui.VideoCapture;

import static org.opencv.highgui.Highgui.imread;
import static org.opencv.imgproc.Imgproc.Canny;
import static org.opencv.imgproc.Imgproc.blur;
import static org.opencv.imgproc.Imgproc.cvtColor;

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
    public static Mat convert(BufferedImage i){
        BufferedImage image = i;
        byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        Mat mat = new Mat(image.getHeight(),image.getWidth(), CvType.CV_8UC3);
        mat.put(0, 0, data);
        return mat;
    }
    public static void show(BufferedImage i){
        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(new JLabel(new ImageIcon(i)));
        frame.pack();
        frame.setVisible(true);
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

        if(problem.getName().equals("2x1 Basic Problem 01")) {

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

            Mat figureAImage = Highgui.imread(figure3Path, 0);

            Imgproc.threshold(figureAImage,figureAImage,127,255,1);

            List<MatOfPoint> contours = new ArrayList<>();
            Mat hierarchy = new Mat();

            ArrayList<MatOfPoint2f> result = new ArrayList<MatOfPoint2f>();

            Imgproc.findContours(figureAImage, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
            Imgproc.drawContours(figureAImage, contours, 0, new Scalar(255,255,0), 1);

            ImageVisible panel = new ImageVisible(convert(figureAImage));
            add(panel);
            setVisible(true);
            setSize(400,400);
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            for (MatOfPoint contour : contours) {
                MatOfPoint2f  mop2f = new MatOfPoint2f(contour.toArray());
                MatOfPoint2f approx = new MatOfPoint2f();
                double epsilon = 0.01*Imgproc.arcLength(mop2f,true);
                Imgproc.approxPolyDP(mop2f, approx, epsilon, true);
                System.out.println("approx: " + approx.size());
                result.add(approx);
            }

            System.out.print("Visible");


//        //Populate frame A figures to array list
//        ArrayList<RavensObject> frameAObjects = figures.get("A").getObjects();
//        FrameA frameA = new FrameA(frameAObjects);
//
//        //Populate frame A figures to array list
//        ArrayList<RavensObject> frameBObjects = figures.get("B").getObjects();
//        FrameB frameB = new FrameB(frameBObjects);
//
//        //Populate frame A figures to array list
//        ArrayList<RavensObject> frameCObjects = figures.get("C").getObjects();
//        FrameC frameC = new FrameC(frameCObjects);
//
//        String solution = "1";
//
//        SemanticNetwork semanticNetwork = new SemanticNetwork(frameA, frameB, frameC);
//
//        //Check which algorithm to use
//        if(problem.getProblemType().equals("2x1")) {
//
////           if(problem.getName().equals("2x1 Basic Problem 18")) {
//            semanticNetwork.generateTransformations2x1();
//            GeneratedFrame generatedSolution = new GeneratedFrame(semanticNetwork, figures.get("B"), figures.get("C"));
//            generatedSolution.createFrame2x1();
//            String finalSolution = Utility.solution2x1(figures, generatedSolution.generatedFrameDFromC, generatedSolution.isUncertainRotation());
//            solution = finalSolution;
//
////            }
//            System.out.println("Finished Running 2x1 Problems");
//
//
//        } else if(problem.getProblemType().equals("2x2")) {
//
////            if(problem.getName().equals("2x2 Basic Problem 10")) {
//            semanticNetwork.generateTransformations2x2();
//            GeneratedFrame generatedSolution = new GeneratedFrame(semanticNetwork, figures.get("B"), figures.get("C"));
//            generatedSolution.createFrame2x2();
//            String finalSolution = Utility.solution2x1(figures, generatedSolution.generatedFrameDFromBC, generatedSolution.isUncertainRotation());
////                SolutionAndScore finalSolutionFromB = Utility.solution2x2(figures, generatedSolution.generatedFrameDFromB);
////                SolutionAndScore finalSolutionFromC = Utility.solution2x2(figures, generatedSolution.generatedFrameDFromC);
////                String finalSolution = Utility.finalSolution(finalSolutionFromB, finalSolutionFromC);
//            solution = finalSolution;
////            }
//
//            System.out.println("Finished Running 2x2 Problems");
//
//        }

        }

        return "1";
    }
}
