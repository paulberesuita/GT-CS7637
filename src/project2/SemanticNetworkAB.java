package project2;

import project2.RavensObject;

import java.util.ArrayList;

public class SemanticNetworkAB {

	FrameA frameA = null;
	FrameB frameB = null;

	ArrayList<Transformation> allABTransformations = null;

	public SemanticNetworkAB(FrameA frameA, FrameB frameB) {

		this.frameA = frameA;
		this.frameB = frameB;
		allABTransformations = new ArrayList<Transformation>();
	}

	public void generateTransformations() {

		for(int i=0; i<frameA.getObjects().size(); i++) {

			//Check if object from Frame A is deleted
			if(i > frameB.getObjects().size()-1) {

				//Create Transformation between nodes; assuming correspondence
				Transformation transformation = new Transformation(frameA.getObjects().get(i), null, frameA, frameB);
				transformation.checkDifferencesBetweenNodes();
				allABTransformations.add(transformation);

			} else {

				//Determine if these two objects correspond
				int frameBCorrespondence = findBestCorrespondenceForObjectInFrameA(frameA.getObjects().get(i), frameB.getObjects());

				System.out.println("Correspondence in Frame B: " + frameBCorrespondence);
				System.out.println("Frame A object: " 			 + frameA.getObjects().get(i).getName());
				System.out.println("Frame B object: " 			 + frameB.getObjects().get(frameBCorrespondence).getName());

				//Create Transformation between nodes; assuming correspondence
				Transformation transformation = new Transformation(frameA.getObjects().get(i), frameB.getObjects().get(frameBCorrespondence), frameA, frameB);
				transformation.checkDifferencesBetweenNodes();
				allABTransformations.add(transformation);

			}

		}
	}

	public int findBestCorrespondenceForObjectInFrameA(RavensObject objectA, ArrayList<RavensObject> allObjectsB) {

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

	public ArrayList<Transformation> getAllABTransformations() {
		return allABTransformations;
	}

	public void setAllABTransformations(
			ArrayList<Transformation> allABTransformations) {
		this.allABTransformations = allABTransformations;
	}

}
