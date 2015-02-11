package project2;

import java.util.ArrayList;

public class SemanticNetworkABC {

    FrameA frameA = null;
    FrameB frameB = null;
    FrameC frameC = null;

    ArrayList<Transformation> allABTransformations = null;

    ArrayList<Change> ABTransformations = null;
    ArrayList<Change> ACTransformations = null;

    public SemanticNetworkABC(FrameA frameA, FrameB frameB, FrameC frameC) {

        this.frameA = frameA;
        this.frameB = frameB;
        this.frameC = frameC;
        allABTransformations = new ArrayList<Transformation>();

    }

    public void generateTransformations() {

        //Checking transformations for FrameA to FrameB
        for(int i=0; i<frameA.getObjects().size(); i++) {

            //Determine if these two objects correspond
            int frameBObjectCorrespondence = findBestCorrespondence(frameA.getObjects().get(i), frameB.getObjects());

            System.out.println("Correspondence in Frame B: " + frameBObjectCorrespondence);
            System.out.println("Frame A object: " 			 + frameA.getObjects().get(i).getName());
            System.out.println("Frame B object: " 			 + frameB.getObjects().get(frameBObjectCorrespondence).getName());

            //Create Transformation between nodes; assuming correspondence
            Transformation transformation = new Transformation(frameA.getObjects().get(i), frameB.getObjects().get(frameBObjectCorrespondence), frameA, frameB);
            transformation.checkDifferencesBetweenNodes();
            allABTransformations.add(transformation);


        }

        //Checking transformations for FrameA to FrameC
        for(int i=0; i<frameA.getObjects().size(); i++) {

            //Determine if these two objects correspond
            int frameCObjectCorrespondence = findBestCorrespondence(frameA.getObjects().get(i), frameC.getObjects());

            System.out.println("Correspondence in Frame B: " + frameCObjectCorrespondence);
            System.out.println("Frame A object: " 			 + frameC.getObjects().get(i).getName());
            System.out.println("Frame B object: " 			 + frameC.getObjects().get(frameCObjectCorrespondence).getName());

            //Create Transformation between nodes; assuming correspondence
            Transformation transformation = new Transformation(frameA.getObjects().get(i), frameB.getObjects().get(frameCObjectCorrespondence), frameA, frameB);
            transformation.checkDifferencesBetweenNodes();
            allABTransformations.add(transformation);


        }
    }

    public int findBestCorrespondence(RavensObject objectA, ArrayList<RavensObject> allObjectsB) {

        int count = 0;
        int previousCount = 0;
        int solutionIndex = 0;

        for(int i=0; i<allObjectsB.size(); i++) {

            count = 0;

            for(int m=0; m<allObjectsB.get(i).getAttributes().size(); m++) {

                for(int n=0; n<objectA.getAttributes().size(); n++) {

                    System.out.println("Object A Attribute Name: " + objectA.getAttributes().get(n).getName());
                    System.out.println("Object A Attribute Value: " + objectA.getAttributes().get(n).getValue());
                    System.out.println("Object B Attribute Name: " + allObjectsB.get(i).getAttributes().get(m).getName());
                    System.out.println("Object B Attribute Value: " + allObjectsB.get(i).getAttributes().get(m).getValue());

                    if(objectA.getAttributes().get(n).getName().equals(allObjectsB.get(i).getAttributes().get(m).getName()) &&
                            objectA.getAttributes().get(n).getValue().equals(allObjectsB.get(i).getAttributes().get(m).getValue()) &&
                            (allObjectsB.get(i).getAttributes().get(m).getName().equals("shape") ||
                                    allObjectsB.get(i).getAttributes().get(m).getName().equals("size")  ||
                                    allObjectsB.get(i).getAttributes().get(m).getName().equals("fill")  ||
                                    allObjectsB.get(i).getAttributes().get(m).getName().equals("angle") ||
                                    allObjectsB.get(i).getAttributes().get(m).getName().equals("above")   )) {

                        count = count + 5;

                    } else if(objectA.getAttributes().get(n).getName().equals(allObjectsB.get(i).getAttributes().get(m).getName()) &&
                            !objectA.getAttributes().get(n).getValue().equals(allObjectsB.get(i).getAttributes().get(m).getValue()) &&
                            allObjectsB.get(i).getAttributes().get(m).getName().equals("angle")) {

                        count = count + 3;

                    } else if(objectA.getAttributes().get(n).getName().equals(allObjectsB.get(i).getAttributes().get(m).getName()) &&
                            !objectA.getAttributes().get(n).getValue().equals(allObjectsB.get(i).getAttributes().get(m).getValue()) &&
                            allObjectsB.get(i).getAttributes().get(m).getName().equals("size")) {

                        count = count + 2;

                    } else if(objectA.getAttributes().get(n).getName().equals(allObjectsB.get(i).getAttributes().get(m).getName()) &&
                            !objectA.getAttributes().get(n).getValue().equals(allObjectsB.get(i).getAttributes().get(m).getValue()) &&
                            allObjectsB.get(i).getAttributes().get(m).getName().equals("fill")) {

                        count = count + 1;

                    } else {
                        count = count + 0;
                    }

                }
            }

            if(count > previousCount) {
                solutionIndex = i;
                previousCount = count;
            }
        }

        return solutionIndex;

    }
}
