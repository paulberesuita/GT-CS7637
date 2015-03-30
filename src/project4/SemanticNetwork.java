package project4;

import java.util.ArrayList;

public class SemanticNetwork {

    FrameA frameA = null;
    FrameB frameB = null;
    FrameC frameC = null;

    ArrayList<Transformation> ABTransformations = null;
    ArrayList<RavensObject> ABRemovals = null;
    ArrayList<RavensObject> ABAdditions = null;
    int MultipleTransformationAB = 0;
    boolean circleOnlyUnclearRotationAB = false;

    ArrayList<Transformation> ACTransformations = null;
    ArrayList<RavensObject> ACRemovals = null;
    ArrayList<RavensObject> ACAdditions = null;
    int MultipleTransformationAC = 0;
    boolean circleOnlyUnclearRotationAC = false;

    public SemanticNetwork(FrameA frameA, FrameB frameB, FrameC frameC) {

        this.frameA = frameA;
        this.frameB = frameB;
        this.frameC = frameC;
        ABTransformations = new ArrayList<Transformation>();
        ABRemovals = new ArrayList<RavensObject>();
        ABAdditions = new ArrayList<RavensObject>();
        ACTransformations = new ArrayList<Transformation>();
        ACRemovals = new ArrayList<RavensObject>();
        ACAdditions = new ArrayList<RavensObject>();

    }
    public void generateTransformations2x1() {

        ArrayList<CorrespondenceIndexAndScore> indexAndScoreArrayAB = new ArrayList<CorrespondenceIndexAndScore>();
        ArrayList<CorrespondenceIndexAndScore> indexAndScoreArrayAC = new ArrayList<CorrespondenceIndexAndScore>();

        for(int i=0; i<frameA.getObjects().size(); i++) {

            //Add corresponding object for frame A object to frame B object
            CorrespondenceIndexAndScore frameBObjectCorrespondence = Utility.bestCorrespondenceIndex(frameA.getObjects().get(i), frameB.getObjects());
            frameBObjectCorrespondence.setFrameAObjectIndex(i);
            indexAndScoreArrayAB.add(frameBObjectCorrespondence);

            //Add corresponding object for frame A object to frame C object
            CorrespondenceIndexAndScore frameCObjectCorrespondence = Utility.bestCorrespondenceIndex(frameA.getObjects().get(i), frameC.getObjects());
            frameCObjectCorrespondence.setFrameAObjectIndex(i);
            indexAndScoreArrayAC.add(frameCObjectCorrespondence);
        }

        if(frameA.getObjects().size() != frameB.getObjects().size()) {

            ArrayList<CorrespondenceIndexAndScore> tempIndexAndScoreArrayAB = new ArrayList<CorrespondenceIndexAndScore>(indexAndScoreArrayAB);

            //Remove duplicate correspondences
            for(int i=0; i<tempIndexAndScoreArrayAB.size(); i++) {

                //Loop again to make sure there not duplicate correspondence; if there are it indicates there is an extra object so remove was performed
                for(int j=0; j<tempIndexAndScoreArrayAB.size(); j++) {

                    if(tempIndexAndScoreArrayAB.get(i).getCorrespondingObjectIndex() == tempIndexAndScoreArrayAB.get(j).getCorrespondingObjectIndex()) {

                        if(tempIndexAndScoreArrayAB.get(i).getScore() >= tempIndexAndScoreArrayAB.get(j).getScore()) {


                        } else {

                            //Remove the extra frame A object
                            ABRemovals.add(frameA.getObjects().get(tempIndexAndScoreArrayAB.get(i).getFrameAObjectIndex()));

                            //remove unnecessary object
                            indexAndScoreArrayAB.remove(i);
                        }

                    }
                }

            }
        }

        //Create Transformations (A to B)
        for(int i=0; i<indexAndScoreArrayAB.size(); i++) {

            //Create Transformation between nodes; assuming correspondence
            Transformation transformation = new Transformation(frameA.getObjects().get(indexAndScoreArrayAB.get(i).getFrameAObjectIndex()), frameB.getObjects().get(indexAndScoreArrayAB.get(i).getCorrespondingObjectIndex()), frameA, frameB, frameC);
            boolean differenceExist = transformation.checkDifferencesBetweenNodes();
            if(differenceExist) {
                ABTransformations.add(transformation);
            }
        }

        System.out.println("removals have been checked");

    }

    public void generateTransformations2x2() {

        ArrayList<CorrespondenceIndexAndScore> indexAndScoreArrayAB = new ArrayList<CorrespondenceIndexAndScore>();
        ArrayList<CorrespondenceIndexAndScore> indexAndScoreArrayAC = new ArrayList<CorrespondenceIndexAndScore>();

        for(int i=0; i<frameA.getObjects().size(); i++) {

            //Add corresponding object for frame A object to frame B object
            CorrespondenceIndexAndScore frameBObjectCorrespondence = Utility.bestCorrespondenceIndex(frameA.getObjects().get(i), frameB.getObjects());
            frameBObjectCorrespondence.setFrameAObjectIndex(i);
            indexAndScoreArrayAB.add(frameBObjectCorrespondence);

            //Add corresponding object for frame A object to frame C object
            CorrespondenceIndexAndScore frameCObjectCorrespondence = Utility.bestCorrespondenceIndex(frameA.getObjects().get(i), frameC.getObjects());
            frameCObjectCorrespondence.setFrameAObjectIndex(i);
            indexAndScoreArrayAC.add(frameCObjectCorrespondence);
        }


        //A TO B - Possible items need to be removed
        if(frameA.getObjects().size() > frameB.getObjects().size()) {

            ArrayList<CorrespondenceIndexAndScore> tempIndexAndScoreArrayAB = new ArrayList<CorrespondenceIndexAndScore>(indexAndScoreArrayAB);

            //Remove duplicate correspondences
            for(int i=0; i<tempIndexAndScoreArrayAB.size(); i++) {

                //Loop again to make sure there not duplicate correspondence; if there are it indicates there is an extra object so remove was performed
                for(int j=0; j<tempIndexAndScoreArrayAB.size(); j++) {

                    if(tempIndexAndScoreArrayAB.get(i).getCorrespondingObjectIndex() == tempIndexAndScoreArrayAB.get(j).getCorrespondingObjectIndex()) {

                        if(tempIndexAndScoreArrayAB.get(i).getScore() >= tempIndexAndScoreArrayAB.get(j).getScore()) {


                        } else {

                            //Remove the extra frame A object
                            ABRemovals.add(frameA.getObjects().get(tempIndexAndScoreArrayAB.get(i).getFrameAObjectIndex()));

                            //remove unnecessary object
                            indexAndScoreArrayAB.remove(i);
                        }

                    }
                }

            }
        }

        //A TO B - Possible items need to be added
        if(frameA.getObjects().size() < frameB.getObjects().size()) {

            ArrayList<CorrespondenceIndexAndScore> tempIndexAndScoreArrayAB = new ArrayList<CorrespondenceIndexAndScore>(indexAndScoreArrayAB);

            boolean isMultiplicity = true;

            //Check if its actually adding or multiplicity; Assuming 1 object to multiply
            for(int i=0; i<frameB.getObjects().size(); i++) {

                //Careful with initial empty objects
                if(frameA.getObjects().size() > 0) {

                    String shapeOfFrameObject = frameA.getObjects().get(0).getAttributes().get(0).getValue();


                    if (frameB.getObjects().get(i).getAttributes().get(0).getValue().equals(shapeOfFrameObject)) {
                        MultipleTransformationAB++;
                    } else {
                        isMultiplicity = false;
                    }
                } else {
                    isMultiplicity = false;
                }
            }

            if(!isMultiplicity){
                for(int i=0; i<frameB.getObjects().size(); i++) {

                    //Check if it exists as a corresponding object, else then it must be new
                    boolean isThere = false;
                    for(int p=0; p<tempIndexAndScoreArrayAB.size(); p++) {

                        if(tempIndexAndScoreArrayAB.get(p).getCorrespondingObjectIndex() == i) {
                            isThere = true;
                        }

                    }

                    if (!isThere) {
                        ABAdditions.add(frameB.getObjects().get(i));
                    }
                }
            }

        }

        //Special Case - Basic Problem 12 - Only Circle objects with no clear rotation
        if(frameA.getObjects().size() == 1 && frameA.getObjects().size() == 1) {

            if(frameA.getObjects().get(0).getAttributes().get(0).getValue().equals("circle") &&
                frameB.getObjects().get(0).getAttributes().get(0).getValue().equals("circle")) {
                circleOnlyUnclearRotationAB = true;
            }
        }

        //Create Transformations (A to B)
        for(int i=0; i<indexAndScoreArrayAB.size(); i++) {

            //Create Transformation between nodes; assuming correspondence
            Transformation transformation = new Transformation(frameA.getObjects().get(indexAndScoreArrayAB.get(i).getFrameAObjectIndex()), frameB.getObjects().get(indexAndScoreArrayAB.get(i).getCorrespondingObjectIndex()), frameA, frameB, frameC);
            boolean differenceExist = transformation.checkDifferencesBetweenNodes();
            if(differenceExist) {
                ABTransformations.add(transformation);
            }
        }

        //A TO C - Possible items to be removed
        if(frameA.getObjects().size() != frameC.getObjects().size()) {

            ArrayList<CorrespondenceIndexAndScore> tempIndexAndScoreArrayAC = new ArrayList<CorrespondenceIndexAndScore>(indexAndScoreArrayAC);

            //Remove duplicate correspondences
            for(int i=0; i<tempIndexAndScoreArrayAC.size(); i++) {

                //Loop again to make sure there not duplicate correspondence; if there are it indicates there is an extra object so remove was performed
                for(int j=0; j<tempIndexAndScoreArrayAC.size(); j++) {

                    if(tempIndexAndScoreArrayAC.get(i).getCorrespondingObjectIndex() == tempIndexAndScoreArrayAC.get(j).getCorrespondingObjectIndex()) {

                        if(tempIndexAndScoreArrayAC.get(i).getScore() >= tempIndexAndScoreArrayAC.get(j).getScore()) {


                        } else {

                            //Remove the extra frame A object
                            ACRemovals.add(frameA.getObjects().get(tempIndexAndScoreArrayAC.get(i).getFrameAObjectIndex()));

                            //remove unnecessary object
                            indexAndScoreArrayAC.remove(i);
                        }

                    }
                }

            }
        }

        //A TO C - Possible items need to be added
        if(frameA.getObjects().size() < frameC.getObjects().size()) {

            ArrayList<CorrespondenceIndexAndScore> tempIndexAndScoreArrayAC = new ArrayList<CorrespondenceIndexAndScore>(indexAndScoreArrayAB);

            boolean isMultiplicity = true;

            //Check if its actually adding or multiplicity; Assuming 1 object to multiply
            for(int i=0; i<frameC.getObjects().size(); i++) {

                //Careful with initial empty objects
                if(frameA.getObjects().size() > 0) {

                    String shapeOfFrameObject = frameA.getObjects().get(0).getAttributes().get(0).getValue();

                    if(frameC.getObjects().get(i).getAttributes().get(0).getValue().equals(shapeOfFrameObject)){
                        MultipleTransformationAC++;
                    } else {
                        isMultiplicity = false;
                    }

                } else {
                    isMultiplicity = false;
                }

            }

            if(!isMultiplicity) {
                for(int i=0; i<frameC.getObjects().size(); i++) {

                    //Check if it exists as a corresponding object, else then it must be new
                    boolean isThere = false;
                    for(int p=0; p<tempIndexAndScoreArrayAC.size(); p++) {

                        if(tempIndexAndScoreArrayAC.get(p).getCorrespondingObjectIndex() == i) {
                            isThere = true;
                        }

                    }

                    if (!isThere) {
                        ACAdditions.add(frameC.getObjects().get(i));
                    }
                }
            }

        }

        //Special Case - Basic Problem 12 - Only Circle objects with no clear rotation
        if(frameA.getObjects().size() == 1 && frameC.getObjects().size() == 1) {

            if(frameA.getObjects().get(0).getAttributes().get(0).getValue().equals("circle") &&
                    frameC.getObjects().get(0).getAttributes().get(0).getValue().equals("circle")) {
                circleOnlyUnclearRotationAC = true;
            }
        }

        //Create Transformations (A to C)
        for(int i=0; i<indexAndScoreArrayAC.size(); i++) {

            //Create Transformation between nodes; assuming correspondence
            Transformation transformation = new Transformation(frameA.getObjects().get(indexAndScoreArrayAC.get(i).getFrameAObjectIndex()), frameC.getObjects().get(indexAndScoreArrayAC.get(i).getCorrespondingObjectIndex()), frameA, frameB, frameC);
            boolean differenceExist = transformation.checkDifferencesBetweenNodes();
            if(differenceExist) {
                ACTransformations.add(transformation);
            }
        }

        System.out.println("removals have been checked");

    }

    public ArrayList<Transformation> getABTransformations() {
        return ABTransformations;
    }

    public void setABTransformations(ArrayList<Transformation> ABTransformations) {
        this.ABTransformations = ABTransformations;
    }

    public ArrayList<RavensObject> getABRemovals() {
        return ABRemovals;
    }

    public void setABRemovals(ArrayList<RavensObject> ABRemovals) {
        this.ABRemovals = ABRemovals;
    }

    public ArrayList<Transformation> getACTransformations() {
        return ACTransformations;
    }

    public void setACTransformations(ArrayList<Transformation> ACTransformations) {
        this.ACTransformations = ACTransformations;
    }

    public ArrayList<RavensObject> getACRemovals() {
        return ACRemovals;
    }

    public void setACRemovals(ArrayList<RavensObject> ACRemovals) {
        this.ACRemovals = ACRemovals;
    }

    public FrameC getFrameC() {
        return frameC;
    }

    public void setFrameC(FrameC frameC) {
        this.frameC = frameC;
    }

    public FrameA getFrameA() {
        return frameA;
    }

    public void setFrameA(FrameA frameA) {
        this.frameA = frameA;
    }

    public FrameB getFrameB() {
        return frameB;
    }

    public void setFrameB(FrameB frameB) {
        this.frameB = frameB;
    }

    public ArrayList<RavensObject> getABAdditions() {
        return ABAdditions;
    }

    public void setABAdditions(ArrayList<RavensObject> ABAdditions) {
        this.ABAdditions = ABAdditions;
    }

    public ArrayList<RavensObject> getACAdditions() {
        return ACAdditions;
    }

    public void setACAdditions(ArrayList<RavensObject> ACAdditions) {
        this.ACAdditions = ACAdditions;
    }

    public int getMultipleTransformationAB() {
        return MultipleTransformationAB;
    }

    public void setMultipleTransformationAB(int multipleTransformationAB) {
        MultipleTransformationAB = multipleTransformationAB;
    }

    public boolean isCircleOnlyUnclearRotationAB() {
        return circleOnlyUnclearRotationAB;
    }

    public void setCircleOnlyUnclearRotationAB(boolean circleOnlyUnclearRotationAB) {
        this.circleOnlyUnclearRotationAB = circleOnlyUnclearRotationAB;
    }

    public int getMultipleTransformationAC() {
        return MultipleTransformationAC;
    }

    public void setMultipleTransformationAC(int multipleTransformationAC) {
        MultipleTransformationAC = multipleTransformationAC;
    }

    public boolean isCircleOnlyUnclearRotationAC() {
        return circleOnlyUnclearRotationAC;
    }

    public void setCircleOnlyUnclearRotationAC(boolean circleOnlyUnclearRotationAC) {
        this.circleOnlyUnclearRotationAC = circleOnlyUnclearRotationAC;
    }
}
