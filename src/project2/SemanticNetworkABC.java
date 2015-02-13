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


        //Create Transformations (A to B)
        for(int i=0; i<indexAndScoreArrayAB.size(); i++) {

            boolean toAdd = false;

            //Loop again to make sure there not duplicate correspondence; if there are it indicates there is an extra object so remove was performed
            for(int j=0; j<indexAndScoreArrayAB.size(); j++) {

                if(indexAndScoreArrayAB.get(i).getCorrespondingObjectIndex() == indexAndScoreArrayAB.get(j).getCorrespondingObjectIndex()) {

                    if(indexAndScoreArrayAB.get(i).getScore() >= indexAndScoreArrayAB.get(j).getScore()) {

                        toAdd = true;

                    } else {

                        //Remove the extra frame A object
                        ABRemovals.add(frameA.getObjects().get(indexAndScoreArrayAB.get(i).getFrameAObjectIndex()));
                        toAdd = false;

                        //remove unnecessary object
                        indexAndScoreArrayAB.remove(i);

                    }

                } else {
                    toAdd = true;
                }

            }

            if(toAdd) {
                //Create Transformation between nodes; assuming correspondence
                Change transformation = new Change(frameA.getObjects().get(indexAndScoreArrayAB.get(i).getFrameAObjectIndex()), frameB.getObjects().get(indexAndScoreArrayAB.get(i).getCorrespondingObjectIndex()), frameA, frameB, frameC);
                boolean differenceExist = transformation.checkDifferencesBetweenNodes();
                if(differenceExist) {
                    ABTransformations.add(transformation);
                }
            }
        }

        //Create Transformations (A to C)
        for(int i=0; i<indexAndScoreArrayAC.size(); i++) {

            boolean toAdd = false;

            //Loop again to make sure there not duplicate correspondence; if there are it indicates there is an extra object so remove was performed
            for(int j=0; j<indexAndScoreArrayAC.size(); j++) {

                if(indexAndScoreArrayAC.get(i).getCorrespondingObjectIndex() == indexAndScoreArrayAC.get(j).getCorrespondingObjectIndex()) {

                    if(indexAndScoreArrayAC.get(i).getScore() >= indexAndScoreArrayAC.get(j).getScore()) {

                        toAdd = true;

                    } else {

                        //Remove the extra frame A object
                        ACRemovals.add(frameA.getObjects().get(indexAndScoreArrayAC.get(i).getFrameAObjectIndex()));
                        toAdd = false;

                        //remove unnecessary object
                        indexAndScoreArrayAC.remove(i);

                    }

                } else {
                    toAdd = true;
                }

            }

            if(toAdd) {
                //Create Transformation between nodes; assuming correspondence
                Change transformation = new Change(frameA.getObjects().get(indexAndScoreArrayAC.get(i).getFrameAObjectIndex()), frameC.getObjects().get(indexAndScoreArrayAC.get(i).getCorrespondingObjectIndex()), frameA, frameB, frameC);
                boolean differenceExist = transformation.checkDifferencesBetweenNodes();
                if(differenceExist) {
                    ACTransformations.add(transformation);
                }
            }
        }

        System.out.println("removals have been checked");

    }

}
