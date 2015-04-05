package project4;

import java.util.ArrayList;

public class SemanticNetwork {

    Frame frameA = null;
    Frame frameB = null;
    Frame frameC = null;
    Frame frameD = null;
    Frame frameE = null;
    Frame frameF = null;
    Frame frameG = null;
    Frame frameH = null;
    Frame frameI = null;

    ArrayList<Transformation> ABTransformations = null;
    ArrayList<Transformation> ACTransformations = null;
    ArrayList<Transformation> BCTransformations = null;
    ArrayList<Transformation> DETransformations = null;
    ArrayList<Transformation> EFTransformations = null;
    ArrayList<Transformation> GHTransformations = null;

    ArrayList<RavensObject> ABRemovals = null;
    ArrayList<RavensObject> ACRemovals = null;
//    ArrayList<RavensObject> BCRemovals = null;
//    ArrayList<RavensObject> DERemovals = null;
//    ArrayList<RavensObject> EFRemovals = null;
//    ArrayList<RavensObject> GHRemovals = null;


    ArrayList<RavensObject> ABAdditions = null;
    ArrayList<RavensObject> ACAdditions = null;
//    ArrayList<RavensObject> BCAdditions = null;
//    ArrayList<RavensObject> DEAdditions = null;
//    ArrayList<RavensObject> EFAdditions = null;
//    ArrayList<RavensObject> GHAdditions = null;

    int MultipleTransformationAB = 0;
    boolean circleOnlyUnclearRotationAB = false;

    int MultipleTransformationAC = 0;
    boolean circleOnlyUnclearRotationAC = false;

    public SemanticNetwork(Frame frameA, Frame frameB, Frame frameC) {

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

    public SemanticNetwork(Frame frameA, Frame frameB, Frame frameC, Frame frameD, Frame frameE, Frame frameF, Frame frameG, Frame frameH) {

        this.frameA = frameA;
        this.frameB = frameB;
        this.frameC = frameC;
        this.frameD = frameD;
        this.frameE = frameE;
        this.frameF = frameF;
        this.frameG = frameG;
        this.frameH = frameH;

        ABTransformations = new ArrayList<Transformation>();
        ACTransformations = new ArrayList<Transformation>();

        BCTransformations = new ArrayList<Transformation>();

        DETransformations = new ArrayList<Transformation>();
        EFTransformations = new ArrayList<Transformation>();

        GHTransformations = new ArrayList<Transformation>();

        ABRemovals = new ArrayList<RavensObject>();
        ACRemovals = new ArrayList<RavensObject>();

        ABAdditions = new ArrayList<RavensObject>();
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
//            CorrespondenceIndexAndScore frameCObjectCorrespondence = Utility.bestCorrespondenceIndex(frameA.getObjects().get(i), frameC.getObjects());
//            frameCObjectCorrespondence.setFrameAObjectIndex(i);
//            indexAndScoreArrayAC.add(frameCObjectCorrespondence);
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
            Transformation transformation = new Transformation(frameA.getObjects().get(indexAndScoreArrayAB.get(i).getFrameAObjectIndex()), frameB.getObjects().get(indexAndScoreArrayAB.get(i).getCorrespondingObjectIndex()), frameA, frameB);
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
            Transformation transformation = new Transformation(frameA.getObjects().get(indexAndScoreArrayAB.get(i).getFrameAObjectIndex()), frameB.getObjects().get(indexAndScoreArrayAB.get(i).getCorrespondingObjectIndex()), frameA, frameB);
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
            Transformation transformation = new Transformation(frameA.getObjects().get(indexAndScoreArrayAC.get(i).getFrameAObjectIndex()), frameC.getObjects().get(indexAndScoreArrayAC.get(i).getCorrespondingObjectIndex()), frameA, frameB);
            boolean differenceExist = transformation.checkDifferencesBetweenNodes();
            if(differenceExist) {
                ACTransformations.add(transformation);
            }
        }

        System.out.println("removals have been checked");

    }

    public void generateTransformations3x3() {

        ArrayList<CorrespondenceIndexAndScore> indexAndScoreArrayAB = new ArrayList<CorrespondenceIndexAndScore>();
        ArrayList<CorrespondenceIndexAndScore> indexAndScoreArrayBC = new ArrayList<CorrespondenceIndexAndScore>();
        ArrayList<CorrespondenceIndexAndScore> indexAndScoreArrayDE = new ArrayList<CorrespondenceIndexAndScore>();
        ArrayList<CorrespondenceIndexAndScore> indexAndScoreArrayEF = new ArrayList<CorrespondenceIndexAndScore>();
        ArrayList<CorrespondenceIndexAndScore> indexAndScoreArrayGH = new ArrayList<CorrespondenceIndexAndScore>();

        //Frame A to B
        for(int i=0; i<frameA.getObjects().size(); i++) {

            //Add corresponding object for frame A object to frame B object
            CorrespondenceIndexAndScore frameBObjectCorrespondence = Utility.bestCorrespondenceIndex(frameA.getObjects().get(i), frameB.getObjects());
            frameBObjectCorrespondence.setFrameAObjectIndex(i);
            indexAndScoreArrayAB.add(frameBObjectCorrespondence);
        }

        //Frame B to C
        for(int i=0; i<frameB.getObjects().size(); i++) {

            //Add corresponding object for frame B object to frame C object
            CorrespondenceIndexAndScore frameCObjectCorrespondence = Utility.bestCorrespondenceIndex(frameB.getObjects().get(i), frameC.getObjects());
            frameCObjectCorrespondence.setFrameAObjectIndex(i);
            indexAndScoreArrayBC.add(frameCObjectCorrespondence);
        }

        //Frame D to E
        for(int i=0; i<frameD.getObjects().size(); i++) {

            //Add corresponding object for frame D object to frame E object
            CorrespondenceIndexAndScore frameEObjectCorrespondence = Utility.bestCorrespondenceIndex(frameD.getObjects().get(i), frameE.getObjects());
            frameEObjectCorrespondence.setFrameAObjectIndex(i);
            indexAndScoreArrayDE.add(frameEObjectCorrespondence);
        }

        //Frame E to F
        for(int i=0; i<frameE.getObjects().size(); i++) {

            //Add corresponding object for frame E object to frame F object
            CorrespondenceIndexAndScore frameFObjectCorrespondence = Utility.bestCorrespondenceIndex(frameE.getObjects().get(i), frameF.getObjects());
            frameFObjectCorrespondence.setFrameAObjectIndex(i);
            indexAndScoreArrayDE.add(frameFObjectCorrespondence);
        }

        //Frame G to H
        for(int i=0; i<frameG.getObjects().size(); i++) {

            //Add corresponding object for frame G object to frame H object
            CorrespondenceIndexAndScore frameHObjectCorrespondence = Utility.bestCorrespondenceIndex(frameG.getObjects().get(i), frameH.getObjects());
            frameHObjectCorrespondence.setFrameAObjectIndex(i);
            indexAndScoreArrayDE.add(frameHObjectCorrespondence);
        }


        //Create Transformations (A to B)
        for(int i=0; i<indexAndScoreArrayAB.size(); i++) {

            //Create Transformation between nodes; assuming correspondence
            Transformation transformation = new Transformation(frameA.getObjects().get(indexAndScoreArrayAB.get(i).getFrameAObjectIndex()), frameB.getObjects().get(indexAndScoreArrayAB.get(i).getCorrespondingObjectIndex()), frameA, frameB);
            boolean differenceExist = transformation.checkDifferencesBetweenNodes();
            if(differenceExist) {
                ABTransformations.add(transformation);
            }
        }

        //Create Transformations (B to C)
        for(int i=0; i<indexAndScoreArrayBC.size(); i++) {

            //Create Transformation between nodes; assuming correspondence
            Transformation transformation = new Transformation(frameB.getObjects().get(indexAndScoreArrayBC.get(i).getFrameAObjectIndex()), frameC.getObjects().get(indexAndScoreArrayBC.get(i).getCorrespondingObjectIndex()), frameB, frameC);
            boolean differenceExist = transformation.checkDifferencesBetweenNodes();
            if(differenceExist) {
                BCTransformations.add(transformation);
            }
        }

        //Create Transformations (D to E)
        for(int i=0; i<indexAndScoreArrayDE.size(); i++) {

            //Create Transformation between nodes; assuming correspondence
            Transformation transformation = new Transformation(frameD.getObjects().get(indexAndScoreArrayDE.get(i).getFrameAObjectIndex()), frameE.getObjects().get(indexAndScoreArrayDE.get(i).getCorrespondingObjectIndex()), frameD, frameE);
            boolean differenceExist = transformation.checkDifferencesBetweenNodes();
            if(differenceExist) {
                DETransformations.add(transformation);
            }
        }

        //Create Transformations (E to F)
        for(int i=0; i<indexAndScoreArrayEF.size(); i++) {

            //Create Transformation between nodes; assuming correspondence
            Transformation transformation = new Transformation(frameE.getObjects().get(indexAndScoreArrayEF.get(i).getFrameAObjectIndex()), frameF.getObjects().get(indexAndScoreArrayEF.get(i).getCorrespondingObjectIndex()), frameE, frameF);
            boolean differenceExist = transformation.checkDifferencesBetweenNodes();
            if(differenceExist) {
                EFTransformations.add(transformation);
            }
        }

        //Create Transformations (G to H)
        for(int i=0; i<indexAndScoreArrayGH.size(); i++) {

            //Create Transformation between nodes; assuming correspondence
            Transformation transformation = new Transformation(frameG.getObjects().get(indexAndScoreArrayGH.get(i).getFrameAObjectIndex()), frameH.getObjects().get(indexAndScoreArrayGH.get(i).getCorrespondingObjectIndex()), frameG, frameH);
            boolean differenceExist = transformation.checkDifferencesBetweenNodes();
            if(differenceExist) {
                GHTransformations.add(transformation);
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

    public Frame getFrameC() {
        return frameC;
    }

    public void setFrameC(Frame frameC) {
        this.frameC = frameC;
    }

    public Frame getFrameA() {
        return frameA;
    }

    public void setFrameA(Frame frameA) {
        this.frameA = frameA;
    }

    public Frame getFrameB() {
        return frameB;
    }

    public void setFrameB(Frame frameB) {
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
