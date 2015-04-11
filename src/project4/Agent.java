package project4;

import java.util.HashMap;
import java.util.ArrayList;

import org.opencv.core.*;

import javax.swing.*;

/**
 * Your Agent for solving Raven's Progressive Matrices. You MUST modify this
 * file.
 * <p/>
 * You may also create and submit new files in addition to modifying this file.
 * <p/>
 * Make sure your file retains methods with the signatures:
 * public Agent()
 * public char Solve(VisualRavensProblem problem)
 * <p/>
 * These methods will be necessary for the project's main method to run.
 */
public class Agent {
    /**
     * The default constructor for your Agent. Make sure to execute any
     * processing necessary before your Agent starts solving problems here.
     * <p/>
     * Do not add any variables to this signature; they will not be used by
     * main().
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
     * <p/>
     * In addition to returning your answer at the end of the method, your Agent
     * may also call problem.checkAnswer(String givenAnswer). The parameter
     * passed to checkAnswer should be your Agent's current guess for the
     * problem; checkAnswer will return the correct answer to the problem. This
     * allows your Agent to check its answer. Note, however, that after your
     * agent has called checkAnswer, it will *not* be able to change its answer.
     * checkAnswer is used to allow your Agent to learn from its incorrect
     * answers; however, your Agent cannot change the answer to a question it
     * has already answered.
     * <p/>
     * If your Agent calls checkAnswer during execution of Solve, the answer it
     * returns will be ignored; otherwise, the answer returned at the end of
     * Solve will be taken as your Agent's answer to this problem.
     *
     * @param problem the VisualRavensProblem your agent should solve
     * @return your Agent's answer to this problem
     */
    public String Solve(VisualRavensProblem problem) {

        String solution = "1";

        CVUtils cvutils = new CVUtils();

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

//        if (problem.getName().equals("3x3 Basic Problem 08")) {

            //Get All figures
            HashMap<String, VisualRavensFigure> figures = problem.getFigures();

            SemanticNetwork semanticNetwork = null;
            SemanticNetwork semanticNetwork3x3 = null;
            RavensFigure allFiguresA = null;
            RavensFigure allFiguresB = null;
            RavensFigure allFiguresC = null;
            RavensFigure allFiguresD = null;
            RavensFigure allFiguresE = null;
            RavensFigure allFiguresF = null;
            RavensFigure allFiguresG = null;
            RavensFigure allFiguresH = null;
            RavensFigure allFiguresI = null;
            RavensFigure allFigures1 = null;
            RavensFigure allFigures2 = null;
            RavensFigure allFigures3 = null;
            RavensFigure allFigures4 = null;
            RavensFigure allFigures5 = null;
            RavensFigure allFigures6 = null;
            HashMap<String, RavensFigure> figuresNoVisual = null;

            if (problem.getProblemType().equals("3x3 (Image)")) {

                String figureAPath = figures.get("A").getPath();
                String figureBPath = figures.get("B").getPath();
                String figureCPath = figures.get("C").getPath();
                String figureDPath = figures.get("D").getPath();
                String figureEPath = figures.get("E").getPath();
                String figureFPath = figures.get("F").getPath();
                String figureGPath = figures.get("G").getPath();
                String figureHPath = figures.get("H").getPath();
                String figure1Path = figures.get("1").getPath();
                String figure2Path = figures.get("2").getPath();
                String figure3Path = figures.get("3").getPath();
                String figure4Path = figures.get("4").getPath();
                String figure5Path = figures.get("5").getPath();
                String figure6Path = figures.get("6").getPath();

                Frame frameA = new Frame(cvutils.processImage(figureAPath, problem.getProblemType()));
                Frame frameB = new Frame(cvutils.processImage(figureBPath, problem.getProblemType()));
                Frame frameC = new Frame(cvutils.processImage(figureCPath, problem.getProblemType()));
                Frame frameD = new Frame(cvutils.processImage(figureDPath, problem.getProblemType()));
                Frame frameE = new Frame(cvutils.processImage(figureEPath, problem.getProblemType()));
                Frame frameF = new Frame(cvutils.processImage(figureFPath, problem.getProblemType()));
                Frame frameG = new Frame(cvutils.processImage(figureGPath, problem.getProblemType()));
                Frame frameH = new Frame(cvutils.processImage(figureHPath, problem.getProblemType()));

                allFiguresA = new RavensFigure("Figure A");
                allFiguresB = new RavensFigure("Figure B");
                allFiguresC = new RavensFigure("Figure C");
                allFiguresD = new RavensFigure("Figure D");
                allFiguresE = new RavensFigure("Figure E");
                allFiguresF = new RavensFigure("Figure F");
                allFiguresG = new RavensFigure("Figure G");
                allFiguresH = new RavensFigure("Figure H");
                allFiguresI = new RavensFigure("Figure I");
                allFigures1 = new RavensFigure("Figure 1");
                allFigures2 = new RavensFigure("Figure 2");
                allFigures3 = new RavensFigure("Figure 3");
                allFigures4 = new RavensFigure("Figure 4");
                allFigures5 = new RavensFigure("Figure 5");
                allFigures6 = new RavensFigure("Figure 6");

                ArrayList<RavensObject> figureAObjects = cvutils.processImage(figureAPath, problem.getProblemType());
                ArrayList<RavensObject> figureBObjects = cvutils.processImage(figureBPath, problem.getProblemType());
                ArrayList<RavensObject> figureCObjects = cvutils.processImage(figureCPath, problem.getProblemType());
                ArrayList<RavensObject> figureDObjects = cvutils.processImage(figureDPath, problem.getProblemType());
                ArrayList<RavensObject> figureEObjects = cvutils.processImage(figureEPath, problem.getProblemType());
                ArrayList<RavensObject> figureFObjects = cvutils.processImage(figureFPath, problem.getProblemType());
                ArrayList<RavensObject> figureGObjects = cvutils.processImage(figureGPath, problem.getProblemType());
                ArrayList<RavensObject> figureHObjects = cvutils.processImage(figureHPath, problem.getProblemType());
                ArrayList<RavensObject> figure1Objects = cvutils.processImage(figure1Path, problem.getProblemType());
                ArrayList<RavensObject> figure2Objects = cvutils.processImage(figure2Path, problem.getProblemType());
                ArrayList<RavensObject> figure3Objects = cvutils.processImage(figure3Path, problem.getProblemType());
                ArrayList<RavensObject> figure4Objects = cvutils.processImage(figure4Path, problem.getProblemType());
                ArrayList<RavensObject> figure5Objects = cvutils.processImage(figure5Path, problem.getProblemType());
                ArrayList<RavensObject> figure6Objects = cvutils.processImage(figure6Path, problem.getProblemType());

                for (int f = 0; f < figureAObjects.size(); f++) {
                    allFiguresA.getObjects().add(figureAObjects.get(f));
                }

                for (int f = 0; f < figureBObjects.size(); f++) {
                    allFiguresB.getObjects().add(figureBObjects.get(f));
                }

                for (int f = 0; f < figureCObjects.size(); f++) {
                    allFiguresC.getObjects().add(figureCObjects.get(f));
                }

                for (int f = 0; f < figureDObjects.size(); f++) {
                    allFiguresD.getObjects().add(figureDObjects.get(f));
                }

                for (int f = 0; f < figureEObjects.size(); f++) {
                    allFiguresE.getObjects().add(figureEObjects.get(f));
                }

                for (int f = 0; f < figureFObjects.size(); f++) {
                    allFiguresF.getObjects().add(figureFObjects.get(f));
                }

                for (int f = 0; f < figureGObjects.size(); f++) {
                    allFiguresG.getObjects().add(figureGObjects.get(f));
                }

                for (int f = 0; f < figureHObjects.size(); f++) {
                    allFiguresH.getObjects().add(figureHObjects.get(f));
                }


                for (int f = 0; f < figure1Objects.size(); f++) {
                    allFigures1.getObjects().add(figure1Objects.get(f));
                }

                for (int f = 0; f < figure2Objects.size(); f++) {
                    allFigures2.getObjects().add(figure2Objects.get(f));
                }

                for (int f = 0; f < figure3Objects.size(); f++) {
                    allFigures3.getObjects().add(figure3Objects.get(f));
                }

                for (int f = 0; f < figure4Objects.size(); f++) {
                    allFigures4.getObjects().add(figure4Objects.get(f));
                }

                for (int f = 0; f < figure5Objects.size(); f++) {
                    allFigures5.getObjects().add(figure5Objects.get(f));
                }

                for (int f = 0; f < figure6Objects.size(); f++) {
                    allFigures6.getObjects().add(figure6Objects.get(f));
                }

                figuresNoVisual = new HashMap<String, RavensFigure>();
                figuresNoVisual.put("1", allFigures1);
                figuresNoVisual.put("2", allFigures2);
                figuresNoVisual.put("3", allFigures3);
                figuresNoVisual.put("4", allFigures4);
                figuresNoVisual.put("5", allFigures5);
                figuresNoVisual.put("6", allFigures6);

                semanticNetwork = new SemanticNetwork(frameA, frameB, frameC, frameD, frameE, frameF, frameG, frameH);

            } else {

                String figureAPath = figures.get("A").getPath();
                String figureBPath = figures.get("B").getPath();
                String figureCPath = figures.get("C").getPath();
                String figure1Path = figures.get("1").getPath();
                String figure2Path = figures.get("2").getPath();
                String figure3Path = figures.get("3").getPath();
                String figure4Path = figures.get("4").getPath();
                String figure5Path = figures.get("5").getPath();
                String figure6Path = figures.get("6").getPath();

                Frame frameA = new Frame(cvutils.processImage(figureAPath, problem.getProblemType()));
                Frame frameB = new Frame(cvutils.processImage(figureBPath, problem.getProblemType()));
                Frame frameC = new Frame(cvutils.processImage(figureCPath, problem.getProblemType()));

                allFiguresA = new RavensFigure("Figure A");
                allFiguresB = new RavensFigure("Figure B");
                allFiguresC = new RavensFigure("Figure C");
                allFiguresD = new RavensFigure("Figure D");
                allFiguresE = new RavensFigure("Figure E");
                allFiguresF = new RavensFigure("Figure F");
                allFiguresG = new RavensFigure("Figure G");
                allFiguresH = new RavensFigure("Figure H");
                allFiguresI = new RavensFigure("Figure I");
                allFigures1 = new RavensFigure("Figure 1");
                allFigures2 = new RavensFigure("Figure 2");
                allFigures3 = new RavensFigure("Figure 3");
                allFigures4 = new RavensFigure("Figure 4");
                allFigures5 = new RavensFigure("Figure 5");
                allFigures6 = new RavensFigure("Figure 6");

                ArrayList<RavensObject> figureAObjects = cvutils.processImage(figureAPath, problem.getProblemType());
                ArrayList<RavensObject> figureBObjects = cvutils.processImage(figureBPath, problem.getProblemType());
                ArrayList<RavensObject> figureCObjects = cvutils.processImage(figureCPath, problem.getProblemType());
                ArrayList<RavensObject> figure1Objects = cvutils.processImage(figure1Path, problem.getProblemType());
                ArrayList<RavensObject> figure2Objects = cvutils.processImage(figure2Path, problem.getProblemType());
                ArrayList<RavensObject> figure3Objects = cvutils.processImage(figure3Path, problem.getProblemType());
                ArrayList<RavensObject> figure4Objects = cvutils.processImage(figure4Path, problem.getProblemType());
                ArrayList<RavensObject> figure5Objects = cvutils.processImage(figure5Path, problem.getProblemType());
                ArrayList<RavensObject> figure6Objects = cvutils.processImage(figure6Path, problem.getProblemType());

                for (int f = 0; f < figureAObjects.size(); f++) {
                    allFiguresA.getObjects().add(figureAObjects.get(f));
                }

                for (int f = 0; f < figureBObjects.size(); f++) {
                    allFiguresB.getObjects().add(figureBObjects.get(f));
                }

                for (int f = 0; f < figureCObjects.size(); f++) {
                    allFiguresC.getObjects().add(figureCObjects.get(f));
                }

                for (int f = 0; f < figure1Objects.size(); f++) {
                    allFigures1.getObjects().add(figure1Objects.get(f));
                }

                for (int f = 0; f < figure2Objects.size(); f++) {
                    allFigures2.getObjects().add(figure2Objects.get(f));
                }

                for (int f = 0; f < figure3Objects.size(); f++) {
                    allFigures3.getObjects().add(figure3Objects.get(f));
                }

                for (int f = 0; f < figure4Objects.size(); f++) {
                    allFigures4.getObjects().add(figure4Objects.get(f));
                }

                for (int f = 0; f < figure5Objects.size(); f++) {
                    allFigures5.getObjects().add(figure5Objects.get(f));
                }

                for (int f = 0; f < figure6Objects.size(); f++) {
                    allFigures6.getObjects().add(figure6Objects.get(f));
                }

                figuresNoVisual = new HashMap<String, RavensFigure>();
                figuresNoVisual.put("1", allFigures1);
                figuresNoVisual.put("2", allFigures2);
                figuresNoVisual.put("3", allFigures3);
                figuresNoVisual.put("4", allFigures4);
                figuresNoVisual.put("5", allFigures5);
                figuresNoVisual.put("6", allFigures6);

                semanticNetwork = new SemanticNetwork(frameA, frameB, frameC);

            }


            //Check which algorithm to use
            if (problem.getProblemType().equals("2x1 (Image)")) {

                semanticNetwork.generateTransformations2x1();
                GeneratedFrame generatedSolution = new GeneratedFrame(semanticNetwork);
                generatedSolution.createFrame2x1();

                String finalSolution = Utility.solution2x1(figuresNoVisual, generatedSolution.generatedFrameDFromC, generatedSolution.isUncertainRotation());
                solution = finalSolution;

                System.out.println("Finished Running 2x1 Problems");


            } else if (problem.getProblemType().equals("2x2 (Image)")) {

                semanticNetwork.generateTransformations2x2();
                GeneratedFrame generatedSolution = new GeneratedFrame(semanticNetwork);
                generatedSolution.createFrame2x2();
                String finalSolution = Utility.solution2x1(figuresNoVisual, generatedSolution.generatedFrameDFromBC, generatedSolution.isUncertainRotation());
                solution = finalSolution;

                System.out.println("Finished Running 2x2 Problems");

            } else {

                semanticNetwork.generateTransformations3x3();
                GeneratedFrame generatedSolution = new GeneratedFrame(semanticNetwork);
                generatedSolution.createFrame3x3();
                String finalSolution = Utility.solution2x1(figuresNoVisual, generatedSolution.generatedFrameIFromH, generatedSolution.isUncertainRotation());
                solution = finalSolution;

                System.out.println("Finished Running 3x3 Problems");

            }

//        } //Specific Problem

        return solution;
    }
}
