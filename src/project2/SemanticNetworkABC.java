package project2;

import java.util.ArrayList;

public class SemanticNetworkABC {

    FrameA frameA = null;
    FrameB frameB = null;
    FrameC frameC = null;

    ArrayList<Change> ABTransformations = null;
    ArrayList<RavensObject> ABRemovals = null;

    ArrayList<Change> ACTransformations = null;
    ArrayList<RavensObject> ACRemovals = null;

    public SemanticNetworkABC(FrameA frameA, FrameB frameB, FrameC frameC) {

        this.frameA = frameA;
        this.frameB = frameB;
        this.frameC = frameC;
        ABTransformations = new ArrayList<Change>();
        ABRemovals = new ArrayList<RavensObject>();
        ACTransformations = new ArrayList<Change>();
        ACRemovals = new ArrayList<RavensObject>();

    }

    public void generateTransformations() {

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
            Change transformation = new Change(frameA.getObjects().get(indexAndScoreArrayAB.get(i).getFrameAObjectIndex()), frameB.getObjects().get(indexAndScoreArrayAB.get(i).getCorrespondingObjectIndex()), frameA, frameB, frameC);
            boolean differenceExist = transformation.checkDifferencesBetweenNodes();
            if(differenceExist) {
                ABTransformations.add(transformation);
            }
        }

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

        //Create Transformations (A to C)
        for(int i=0; i<indexAndScoreArrayAC.size(); i++) {

            //Create Transformation between nodes; assuming correspondence
            Change transformation = new Change(frameA.getObjects().get(indexAndScoreArrayAC.get(i).getFrameAObjectIndex()), frameC.getObjects().get(indexAndScoreArrayAC.get(i).getCorrespondingObjectIndex()), frameA, frameB, frameC);
            boolean differenceExist = transformation.checkDifferencesBetweenNodes();
            if(differenceExist) {
                ACTransformations.add(transformation);
            }
        }

        System.out.println("removals have been checked");

    }

    public ArrayList<Change> getABTransformations() {
        return ABTransformations;
    }

    public void setABTransformations(ArrayList<Change> ABTransformations) {
        this.ABTransformations = ABTransformations;
    }

    public ArrayList<RavensObject> getABRemovals() {
        return ABRemovals;
    }

    public void setABRemovals(ArrayList<RavensObject> ABRemovals) {
        this.ABRemovals = ABRemovals;
    }

    public ArrayList<Change> getACTransformations() {
        return ACTransformations;
    }

    public void setACTransformations(ArrayList<Change> ACTransformations) {
        this.ACTransformations = ACTransformations;
    }

    public ArrayList<RavensObject> getACRemovals() {
        return ACRemovals;
    }

    public void setACRemovals(ArrayList<RavensObject> ACRemovals) {
        this.ACRemovals = ACRemovals;
    }
}
