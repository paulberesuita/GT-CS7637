package project2;

import java.util.ArrayList;
import java.util.HashMap;

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

            if(count > highestCurrentCount) {
                correspondingObjectIndex = i;
                highestCurrentCount = count;
            }
        }

        CorrespondenceIndexAndScore indexAndScore = new CorrespondenceIndexAndScore(correspondingObjectIndex, highestCurrentCount);

        return indexAndScore;

    }

    public static String solution(HashMap<String, RavensFigure> possibleSolutions, RavensFigure generatedFrame) {

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

                            System.out.println("Generated Frame Object Name: " + generatedFrame.getObjects().get(y).getAttributes().get(m).getName());
                            System.out.println("Generated Frame Object Value: " + generatedFrame.getObjects().get(y).getAttributes().get(m).getValue());

                            System.out.println("Solution Frame Object Name: " + frameSolution.get(y).getAttributes().get(n).getName());
                            System.out.println("Solution Frame Object Value: " + frameSolution.get(y).getAttributes().get(n).getValue());

                            if(generatedFrame.getObjects().get(y).getAttributes().get(m).getName().equals(frameSolution.get(y).getAttributes().get(n).getName()) &&
                                    generatedFrame.getObjects().get(y).getAttributes().get(m).getValue().equals(frameSolution.get(y).getAttributes().get(n).getValue())) {
                                count ++;
                                System.out.println("Increased Count!");
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

}
