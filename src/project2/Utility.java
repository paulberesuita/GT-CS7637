package project2;

import java.util.*;

public class Utility {

    /**
     * Find object in frame A, frame B, or frame C
     * @param objectName
     * @param frameA
     * @param frameB
     * @param frameC
     * @return
     */
    public static RavensObject returnObject(String objectName, FrameA frameA, FrameB frameB, FrameC frameC) {

        RavensObject object = null;

        for(int i=0; i<frameA.getObjects().size(); i++) {

            if(frameA.getObjects().get(i).getName().equals(objectName)) {
                object = frameA.getObjects().get(i);
            }

        }

        for(int i=0; i<frameB.getObjects().size(); i++) {

            if(frameB.getObjects().get(i).getName().equals(objectName)) {
                object = frameB.getObjects().get(i);
            }

        }

        for(int i=0; i<frameC.getObjects().size(); i++) {

            if(frameC.getObjects().get(i).getName().equals(objectName)) {
                object = frameC.getObjects().get(i);
            }

        }

        return object;
    }

    public static RavensObject returnObjectInFrameB(String objectName, FrameB frameB) {

        RavensObject object = null;

        for(int i=0; i<frameB.getObjects().size(); i++) {

            if(frameB.getObjects().get(i).getName().equals(objectName)) {
                object = frameB.getObjects().get(i);
            }

        }

        return object;
    }

    /**
     * Find the object that best corresponds to the object given
     * @param object
     * @param allObjects
     * @return
     */
    public static CorrespondenceIndexAndScore bestCorrespondenceIndex(RavensObject object, ArrayList<RavensObject> allObjects) {

        int count = 0;
        int highestCurrentCount = 0;
        int correspondingObjectIndex = 0;

        for(int i=0; i<allObjects.size(); i++) {

            count = 0;

            for(int m=0; m<allObjects.get(i).getAttributes().size(); m++) {

                for(int n=0; n<object.getAttributes().size(); n++) {

                    if(object.getAttributes().get(n).getName().equals(allObjects.get(i).getAttributes().get(m).getName()) &&
                            object.getAttributes().get(n).getValue().equals(allObjects.get(i).getAttributes().get(m).getValue()) &&
                            (allObjects.get(i).getAttributes().get(m).getName().equals("shape") ||
                                    allObjects.get(i).getAttributes().get(m).getName().equals("size")  ||
                                    allObjects.get(i).getAttributes().get(m).getName().equals("fill")  ||
                                    allObjects.get(i).getAttributes().get(m).getName().equals("angle") ||
                                    allObjects.get(i).getAttributes().get(m).getName().equals("above")   )) {

                        count = count + 5;

                    } else if(object.getAttributes().get(n).getName().equals(allObjects.get(i).getAttributes().get(m).getName()) &&
                            !object.getAttributes().get(n).getValue().equals(allObjects.get(i).getAttributes().get(m).getValue()) &&
                            allObjects.get(i).getAttributes().get(m).getName().equals("overlaps")) {

                        count = count + 4;

                    }else if(object.getAttributes().get(n).getName().equals(allObjects.get(i).getAttributes().get(m).getName()) &&
                            !object.getAttributes().get(n).getValue().equals(allObjects.get(i).getAttributes().get(m).getValue()) &&
                            allObjects.get(i).getAttributes().get(m).getName().equals("angle")) {

                        count = count + 3;

                    } else if(object.getAttributes().get(n).getName().equals(allObjects.get(i).getAttributes().get(m).getName()) &&
                            !object.getAttributes().get(n).getValue().equals(allObjects.get(i).getAttributes().get(m).getValue()) &&
                            allObjects.get(i).getAttributes().get(m).getName().equals("size")) {

                        count = count + 2;

                    } else if(object.getAttributes().get(n).getName().equals(allObjects.get(i).getAttributes().get(m).getName()) &&
                            !object.getAttributes().get(n).getValue().equals(allObjects.get(i).getAttributes().get(m).getValue()) &&
                            allObjects.get(i).getAttributes().get(m).getName().equals("fill")) {

                        count = count + 1;

                    } else if(object.getAttributes().get(n).getName().equals(allObjects.get(i).getAttributes().get(m).getName()) &&
                            !object.getAttributes().get(n).getValue().equals(allObjects.get(i).getAttributes().get(m).getValue()) &&
                            allObjects.get(i).getAttributes().get(m).getName().equals("above")) {

                        count = count + 1;

                    } else {
                        count = count + 0;
                    }

                }
            }

//            //Take out some points for each extra attribute
            if(allObjects.get(i).getAttributes().size() != object.getAttributes().size()) {
                count--;
            }

            if(count > highestCurrentCount) {
                correspondingObjectIndex = i;
                highestCurrentCount = count;
            }
        }

        CorrespondenceIndexAndScore indexAndScore = new CorrespondenceIndexAndScore(correspondingObjectIndex, highestCurrentCount);

        return indexAndScore;

    }

    public static SolutionAndScore solution2x2(HashMap<String, RavensFigure> possibleSolutions, RavensFigure generatedFrame) {

        int solution = 0;
        int highestCount = 0;

        for(int i=1; i<7; i++) {

            ArrayList<RavensObject> frameSolution = possibleSolutions.get(String.valueOf(i)).getObjects();

            int count = 0;

            System.out.println("Generated Frame Number Objects: " + generatedFrame.getObjects().size());
            for(int y=0; y<generatedFrame.getObjects().size(); y++) {

                //Check if the number of generated objects matches the number of objects in the solution
                if(generatedFrame.getObjects().size() != frameSolution.size()){

                    count = 0;

                } else {

                    //Get generatedFrame and SolutionFrame shape
                    String generatedFrameShape = generatedFrame.getObjects().get(y).getAttributes().get(0).getValue();
                    String solutionFrameShape = frameSolution.get(y).getAttributes().get(0).getValue();

                    System.out.println("Generated Frame Current Object Number of Attributes: " + generatedFrame.getObjects().get(y).getAttributes().size());
                    for(int m=0; m<generatedFrame.getObjects().get(y).getAttributes().size(); m++) {

                        System.out.println("Current Solution Number Of Attributes: " + frameSolution.get(y).getAttributes().size());
                        for(int n=0; n< frameSolution.get(y).getAttributes().size(); n++) {

                            //Special Case to check for specific fill order; sometimes bottom-left, bottom-right, etc are mix in the answers
                            if(generatedFrame.getObjects().get(y).getAttributes().get(m).getName().equals(frameSolution.get(y).getAttributes().get(n).getName()) &&
                                generatedFrame.getObjects().get(y).getAttributes().get(m).getName().equals("fill")) {


                                List<String> generatedFills = Arrays.asList(generatedFrame.getObjects().get(y).getAttributes().get(m).getValue().split(","));
                                List<String> possibleSolutionFills = Arrays.asList(frameSolution.get(y).getAttributes().get(n).getValue().split(","));

                                boolean allthere = false;

                                for(int l=0; l<generatedFills.size(); l++){

                                    boolean oneThere = false;
                                    for(int t=0; t<possibleSolutionFills.size(); t++){

                                        if(generatedFills.get(l).equals(possibleSolutionFills.get(t))){
                                            oneThere = true;
                                        }
                                    }

                                    if (oneThere){
                                        allthere = true;
                                    } else {
                                        allthere = false;
                                        break;
                                    }
                                }

                                if (allthere) {
                                    count ++;
                                }

                            } else {


                                //Case: Angle
//                                if(generatedFrame.getObjects().get(y).getAttributes().get(m).getName().equals("angle") &&
//                                    frameSolution.get(y).getAttributes().get(n).getName().equals("angle")){
//
//                                    //Square to be checked are 0, 45, 90, 135, 180, 225, 270, 315, 360(0)
//                                    if(generatedFrameShape.equals("square") && solutionFrameShape.equals("square")){
//
//                                        if((generatedFrame.getObjects().get(y).getAttributes().get(m).getValue().equals("0") ||
//                                            generatedFrame.getObjects().get(y).getAttributes().get(m).getValue().equals("45") ) &&
//                                            (frameSolution.get(y).getAttributes().get(n).getValue().equals("0") ||
//                                             frameSolution.get(y).getAttributes().get(n).getValue().equals("45")) ){
//                                            count++;
//                                        }
//
//                                    } else {
//                                        if (generatedFrame.getObjects().get(y).getAttributes().get(m).getName().equals(frameSolution.get(y).getAttributes().get(n).getName()) &&
//                                                generatedFrame.getObjects().get(y).getAttributes().get(m).getValue().equals(frameSolution.get(y).getAttributes().get(n).getValue())) {
//                                            count ++;
//                                            System.out.println("Increased Count!");
//                                        }
//                                    }
//
//                                } else {

                                    if (generatedFrame.getObjects().get(y).getAttributes().get(m).getName().equals(frameSolution.get(y).getAttributes().get(n).getName()) &&
                                            generatedFrame.getObjects().get(y).getAttributes().get(m).getValue().equals(frameSolution.get(y).getAttributes().get(n).getValue())) {
                                        count ++;
                                        System.out.println("Increased Count!");
                                    }
//                                }

                            }
                        }
                    }
                }
            }

            if(count > highestCount) {
                solution = i;
                highestCount = count;
            }

        }

        SolutionAndScore solutionAndScore = new SolutionAndScore();
        solutionAndScore.setSolution(solution);
        solutionAndScore.setHighestScore(highestCount);

        return solutionAndScore;


    }

    public static String finalSolution(SolutionAndScore solutionAndScoreBD, SolutionAndScore solutionAndScoreCD){

        int solution = 1;

        if(solutionAndScoreBD.getHighestScore()>solutionAndScoreCD.getHighestScore()){
            solution = solutionAndScoreBD.getSolution();
        } else {
            solution = solutionAndScoreCD.getSolution();
        }

        if(solution == 1) {
            System.out.println("Return: " + solution);
            return "1";
        } else if(solution == 2) {
            System.out.println("Return: " + solution);
            return "2";
        } else if(solution == 3) {
            System.out.println("Return: " + solution);
            return "3";
        } else if(solution == 4) {
            System.out.println("Return: " + solution);
            return "4";
        } else if(solution == 5) {
            System.out.println("Return: " + solution);
            return "5";
        } else {
            System.out.println("Return: " + solution);
            return "6";
        }
    }

    public static String solution2x1(HashMap<String, RavensFigure> possibleSolutions, RavensFigure generatedFrame) {

        int solution = 0;
        int previousCount = 0;

        for(int i=1; i<7; i++) {

            ArrayList<RavensObject> frameSolution = possibleSolutions.get(String.valueOf(i)).getObjects();

            int count = 0;

            System.out.println("Generated Frame Number Objects: " + generatedFrame.getObjects().size());
            for(int y=0; y<generatedFrame.getObjects().size(); y++) {

                //Check if the number of generated objects matches the number of objects in the solution
                if(generatedFrame.getObjects().size() != frameSolution.size()){

                    count = 0;

                } else {

                    System.out.println("Generated Frame Current Object Number of Attributes: " + generatedFrame.getObjects().get(y).getAttributes().size());
                    for(int m=0; m<generatedFrame.getObjects().get(y).getAttributes().size(); m++) {

                        System.out.println("Current Solution Number Of Attributes: " + frameSolution.get(y).getAttributes().size());
                        for(int n=0; n< frameSolution.get(y).getAttributes().size(); n++) {

                            //Special Case to check for specific fill order; sometimes bottom-left, bottom-right, etc are mix in the answers
                            if(generatedFrame.getObjects().get(y).getAttributes().get(m).getName().equals(frameSolution.get(y).getAttributes().get(n).getName()) &&
                                    generatedFrame.getObjects().get(y).getAttributes().get(m).getName().equals("fill")) {


                                List<String> generatedFills = Arrays.asList(generatedFrame.getObjects().get(y).getAttributes().get(m).getValue().split(","));
                                List<String> possibleSolutionFills = Arrays.asList(frameSolution.get(y).getAttributes().get(n).getValue().split(","));

                                boolean allthere = false;

                                for(int l=0; l<generatedFills.size(); l++){

                                    boolean oneThere = false;
                                    for(int t=0; t<possibleSolutionFills.size(); t++){

                                        if(generatedFills.get(l).equals(possibleSolutionFills.get(t))){
                                            oneThere = true;
                                        }
                                    }

                                    if (oneThere){
                                        allthere = true;
                                    } else {
                                        allthere = false;
                                        break;
                                    }
                                }

                                if (allthere) {
                                    count ++;
                                }

                            } else {

                                if(generatedFrame.getObjects().get(y).getAttributes().get(m).getName().equals(frameSolution.get(y).getAttributes().get(n).getName()) &&
                                        generatedFrame.getObjects().get(y).getAttributes().get(m).getValue().equals(frameSolution.get(y).getAttributes().get(n).getValue())) {
                                    count ++;
                                    System.out.println("Increased Count!");
                                }
                            }
                        }
                    }
                }
            }

            if(count > previousCount) {
                solution = i;
                previousCount = count;
            }

        }


        if(solution == 1) {
            System.out.println("Return: " + solution);
            return "1";
        } else if(solution == 2) {
            System.out.println("Return: " + solution);
            return "2";
        } else if(solution == 3) {
            System.out.println("Return: " + solution);
            return "3";
        } else if(solution == 4) {
            System.out.println("Return: " + solution);
            return "4";
        } else if(solution == 5) {
            System.out.println("Return: " + solution);
            return "5";
        } else {
            System.out.println("Return: " + solution);
            return "6";
        }
    }

    public static String getShapeOfObject(RavensObject object) {

        String shapeValue = "";

        for(int i=0; i< object.getAttributes().size(); i++) {

            if(object.getAttributes().get(i).getName().equals("shape")){

                shapeValue = object.getAttributes().get(i).getValue();
            }

        }

        return shapeValue;
    }

    public static String performReflection(String value, String objectShape, String direction) {

        String result = "";

        int angleValue = Integer.parseInt(value);

        if(359 > angleValue && angleValue > 180){

            if(objectShape.equals("Pac-Man")) {
                angleValue = angleValue - 90;
            } else if(objectShape.equals("right-triangle")) {
                angleValue = angleValue + 90;
            } else if(objectShape.equals("half-arrow")) {
                angleValue = angleValue + 180;
            } else {
            }
        } else {
            if(objectShape.equals("Pac-Man")) {
                angleValue = angleValue + 90;
            } else if(objectShape.equals("right-triangle")) {

                if(angleValue == 90 && direction.equals("right")){
                    angleValue = angleValue + 90;
                } else if(angleValue == 90 && direction.equals("down")){
                    angleValue = angleValue - 90;
                }
                else if(angleValue == 180 && direction.equals("left")) {
                    angleValue = angleValue - 90;
                } else if(angleValue == 180 && direction.equals("down")) {
                    angleValue = angleValue + 90;
                } else {

                }

            } else if(objectShape.equals("half-arrow")) {
                angleValue = angleValue + 180;
            }else {
            }
        }

        if(angleValue >= 360) {
            angleValue = angleValue - 360;
        }
        result = String.valueOf(angleValue);

        return result;
    }

    public static String performRotation(String currentValue, String rotationValue) {

        String result = "";

        int currentValueInt = Integer.parseInt(currentValue);
        int rotationValueInt = Integer.parseInt(rotationValue);

        currentValueInt = currentValueInt + (rotationValueInt);

        result = String.valueOf(currentValueInt);

        return result;
    }

    public static int objectIndexToRemove(RavensObject removal, RavensFigure figure) {

        int indexToRemove = 0;
        int previousCount = 0;

        for(int r=0; r<figure.getObjects().size(); r++) {

            int count = 0;

            for(int t=0; t<figure.getObjects().get(r).getAttributes().size(); t++) {

                    for(int m=0; m< removal.getAttributes().size(); m++) {

                        if(removal.getAttributes().get(m).getName().equals(figure.getObjects().get(r).getAttributes().get(t).getName()) &&
                           removal.getAttributes().get(m).getValue().equals(figure.getObjects().get(r).getAttributes().get(t).getValue())) {
                            count ++;
                            System.out.println("Increased Count!");
                        }
                    }

            }

            if(count > previousCount) {
                indexToRemove = r;
                previousCount = count;
            }

        }

        return indexToRemove;
    }

}
