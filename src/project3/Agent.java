package project3;

import java.util.HashMap;
import java.util.ArrayList;

import org.opencv.core.*;

import javax.swing.*;

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

//        if(problem.getName().equals("2x1 Basic Problem 15")) {

            CVUtils cvutils = new CVUtils();

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

            FrameA frameA = new FrameA(cvutils.processImage(figureAPath));
            FrameB frameB = new FrameB(cvutils.processImage(figureBPath));
            FrameC frameC = new FrameC(cvutils.processImage(figureCPath));

            RavensFigure allFiguresA = new RavensFigure("Figure A");
            RavensFigure allFiguresB = new RavensFigure("Figure B");
            RavensFigure allFiguresC = new RavensFigure("Figure C");
            RavensFigure allFigures1 = new RavensFigure("Figure 1");
            RavensFigure allFigures2 = new RavensFigure("Figure 2");
            RavensFigure allFigures3 = new RavensFigure("Figure 3");
            RavensFigure allFigures4 = new RavensFigure("Figure 4");
            RavensFigure allFigures5 = new RavensFigure("Figure 5");
            RavensFigure allFigures6 = new RavensFigure("Figure 6");

            ArrayList<RavensObject> figureAObjects = cvutils.processImage(figureAPath);
            ArrayList<RavensObject> figureBObjects = cvutils.processImage(figureBPath);
            ArrayList<RavensObject> figureCObjects = cvutils.processImage(figureCPath);
            ArrayList<RavensObject> figure1Objects = cvutils.processImage(figure1Path);
            ArrayList<RavensObject> figure2Objects = cvutils.processImage(figure2Path);
            ArrayList<RavensObject> figure3Objects = cvutils.processImage(figure3Path);
            ArrayList<RavensObject> figure4Objects = cvutils.processImage(figure4Path);
            ArrayList<RavensObject> figure5Objects = cvutils.processImage(figure5Path);
            ArrayList<RavensObject> figure6Objects = cvutils.processImage(figure6Path);

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

            HashMap<String, RavensFigure> figuresNoVisual = new HashMap<String, RavensFigure>();
            figuresNoVisual.put("1", allFigures1);
            figuresNoVisual.put("2", allFigures2);
            figuresNoVisual.put("3", allFigures3);
            figuresNoVisual.put("4", allFigures4);
            figuresNoVisual.put("5", allFigures5);
            figuresNoVisual.put("6", allFigures6);

        SemanticNetwork semanticNetwork = new SemanticNetwork(frameA, frameB, frameC);

            //Check which algorithm to use
        if(problem.getProblemType().equals("2x1 (Image)")) {

            semanticNetwork.generateTransformations2x1();
            GeneratedFrame generatedSolution = new GeneratedFrame(semanticNetwork, allFiguresB, allFiguresC);
            generatedSolution.createFrame2x1();

            String finalSolution = Utility.solution2x1(figuresNoVisual, generatedSolution.generatedFrameDFromC, generatedSolution.isUncertainRotation());
            solution = finalSolution;

            System.out.println("Finished Running 2x1 Problems");


        } else if(problem.getProblemType().equals("2x2 (Image)")) {

            semanticNetwork.generateTransformations2x2();
            GeneratedFrame generatedSolution = new GeneratedFrame(semanticNetwork, allFiguresB, allFiguresC);
            generatedSolution.createFrame2x2();
            String finalSolution = Utility.solution2x1(figuresNoVisual, generatedSolution.generatedFrameDFromBC, generatedSolution.isUncertainRotation());
            solution = finalSolution;

            System.out.println("Finished Running 2x2 Problems");

        }
//        } //Specific Problem
        return solution;
    }
}
