package project1;

import java.util.ArrayList;
import java.util.HashMap;

public class FrameGenerated {
	
	ArrayList<RavensObject> objects = null;
	SemanticNetworkAB previousSemanticNetwork = null;
	FrameC frameC = null;
	RavensFigure generatedFrame = null;
	
	public FrameGenerated(SemanticNetworkAB semanticNetworkAB, FrameC frameC) {
		this.previousSemanticNetwork = semanticNetworkAB;
		this.frameC = frameC;
	}
	
	public void createFrame() {
		
		System.out.println("CREATING FRAME");
		generatedFrame = new RavensFigure("D");
		
		System.out.println("Frame C number of objects: " + frameC.getObjects().size());
		for(int i=0; i<frameC.getObjects().size(); i++) {
			
			boolean deleteDetected = false;
			RavensObject object = new RavensObject("Z");
			
			//Index for correct transformation to be given to current i object in Frame C
			int transformationIndex = findBestCorrespondenceForTransformationsFrameC(frameC.getObjects().get(i), previousSemanticNetwork.getAllABTransformations());
			
			System.out.println("Frame C object number of attributes: " + frameC.getObjects().get(transformationIndex).getAttributes().size());
			for(int m=0; m< frameC.getObjects().get(transformationIndex).getAttributes().size(); m++) {

				//Above; below; left-of; right-of
				//Not part of change so transfer those changes get transfer since it only concerns to position; assuming same labeling (correspondence) between Frame C and solution
				if(frameC.getObjects().get(transformationIndex).getAttributes().get(m).getName().equals("above")) {
					
					RavensAttribute sameAttribute = new RavensAttribute(frameC.getObjects().get(transformationIndex).getAttributes().get(m).getName(), frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue());
					object.getAttributes().add(sameAttribute);
					System.out.println("same attributes to add");
					
				} else if(frameC.getObjects().get(transformationIndex).getAttributes().get(m).getName().equals("left-of")) {
					
					RavensAttribute sameAttribute = new RavensAttribute(frameC.getObjects().get(transformationIndex).getAttributes().get(m).getName(), frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue());
					object.getAttributes().add(sameAttribute);
					System.out.println("same attributes to add");
					
				} else {
					
					//Check if these are only positions; else just applied the transformations for differences 
					
					System.out.println("A transformation number of attributes: " + previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationListAttributeName().size());
					for(int y=0; y < previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationListAttributeName().size(); y++) {
						
						System.out.println("All Tranformations Tranformation: " + previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y));
						System.out.println("All Tranformations Attribute Name: " + previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationListAttributeName().get(y));
			
						//Check if we even have an attribute to match
						if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationListAttributeName().get(y).equals("delete")) {
							
							System.out.println("Don't add anything");
							deleteDetected = true;
							
						} 
						
						if(frameC.getObjects().get(transformationIndex).getAttributes().get(m).getName().equals(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationListAttributeName().get(y))) {
														
							if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("yes fill")) {
								
								RavensAttribute yesFillAttribute = new RavensAttribute("fill", "yes");
								object.getAttributes().add(yesFillAttribute);
								
							} else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("no fill")) {
								
								RavensAttribute noFillAttribute = new RavensAttribute("fill", "no");
								object.getAttributes().add(noFillAttribute);
								
							} else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("left-half fill")) {
								
								RavensAttribute noFillAttribute = new RavensAttribute("fill", "left-half");
								object.getAttributes().add(noFillAttribute);
								
							} else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("right-half fill")) {
								
								RavensAttribute noFillAttribute = new RavensAttribute("fill", "right-half");
								object.getAttributes().add(noFillAttribute);
								
							} else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("top-bottom-left fill")) {
								
								String toAddFirst = "top-left,bottom-left";
								String toAddSecond = "top-left";
								String toAddThird = "bottom-left";

								if(frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue().contains(toAddFirst)) {
									
									//Contains both so don't add anything
									
								} else if(frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue().contains(toAddSecond)) {
									
									//Contains top left so add bottom left
									String bottomLeft = ",bottom-left";
									RavensAttribute noFillAttribute = new RavensAttribute("fill", frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue() + bottomLeft);
									object.getAttributes().add(noFillAttribute);
									
								} else {
									//Contains bottom left so add top left
									String topLeft = ",top-left";
									RavensAttribute noFillAttribute = new RavensAttribute("fill", frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue() + topLeft);
									object.getAttributes().add(noFillAttribute);
								}
								
								
							} else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("top-bottom-right fill")) {
								
								String toAddFirst = "top-right,bottom-right";
								String toAddSecond = "top-right";
								String toAddThird = "bottom-right";

								if(frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue().contains(toAddFirst)) {
									
									//Contains both so don't add anything
									
								} else if(frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue().contains(toAddSecond)) {
									
									//Contains top right so add bottom right
									String bottomLeft = ",bottom-right";
									RavensAttribute noFillAttribute = new RavensAttribute("fill", frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue() + bottomLeft);
									object.getAttributes().add(noFillAttribute);
									
								} else {
									//Contains bottom right so add top right
									String topLeft = ",top-right";
									RavensAttribute noFillAttribute = new RavensAttribute("fill", frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue() + topLeft);
									object.getAttributes().add(noFillAttribute);
								}
								
							} else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("top-top-both fill")) {
								
								String toAddFirst = "top-right,top-left";
								String toAddSecond = "top-right";
								String toAddThird = "top-left";

								if(frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue().contains(toAddFirst)) {
									
									//Contains both so don't add anything
									
								} else if(frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue().contains(toAddSecond)) {
									
									//Contains top right so add top left
									String bottomLeft = ",top-left";
									RavensAttribute noFillAttribute = new RavensAttribute("fill", frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue() + bottomLeft);
									object.getAttributes().add(noFillAttribute);
									
								} else {
									//Contains top left so add top right
									String topLeft = ",top-right";
									RavensAttribute noFillAttribute = new RavensAttribute("fill", frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue() + topLeft);
									object.getAttributes().add(noFillAttribute);
								}
								
							} else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("bottom-bottom-both fill")) {
								
								String toAddFirst = "bottom-right,bottom-left";
								String toAddSecond = "bottom-right";
								String toAddThird = "bottom-left";

								if(frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue().contains(toAddFirst)) {
									
									//Contains both so don't add anything
									
								} else if(frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue().contains(toAddSecond)) {
									
									//Contains bottom right so add bottom left
									String bottomLeft = ",bottom-left";
									RavensAttribute noFillAttribute = new RavensAttribute("fill", frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue() + bottomLeft);
									object.getAttributes().add(noFillAttribute);
									
								} else {
									//Contains bottom left so add bottom right
									String topLeft = ",bottom-right";
									RavensAttribute noFillAttribute = new RavensAttribute("fill", frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue() + topLeft);
									object.getAttributes().add(noFillAttribute);
								}
								
							} else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("bottom-bottom-right fill")) {
								
//								RavensAttribute noFillAttribute = new RavensAttribute("fill", "right-half");
//								object.getAttributes().add(noFillAttribute);
								
							} else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("yes large")) {
								
								RavensAttribute largeSizeAttribute = new RavensAttribute("size", "large");
								object.getAttributes().add(largeSizeAttribute);
								
							} else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("no large")) {
								
								RavensAttribute largeSizeAttribute = new RavensAttribute("size", "small");
								object.getAttributes().add(largeSizeAttribute);
								
							} else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("rotate 45")) {
								
								RavensAttribute largeSizeAttribute = new RavensAttribute("angle", "45");
								object.getAttributes().add(largeSizeAttribute);
								
							} else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("rotate 90")) {
								
								RavensAttribute largeSizeAttribute = new RavensAttribute("angle", "90");
								object.getAttributes().add(largeSizeAttribute);
								
							} else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("rotate 180")) {
								
								RavensAttribute largeSizeAttribute = new RavensAttribute("angle", "180");
								object.getAttributes().add(largeSizeAttribute);
								
							}  else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("move out")) {
								
								RavensAttribute moveOutAttribute = new RavensAttribute("above", frameC.getObjects().get(i-1).getName());
								object.getAttributes().add(moveOutAttribute);
								
							} else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("move out left small")) {
								
//								String left = nodeNameWithSizeAttributeValue("small");
								
								RavensAttribute moveOutAttribute = new RavensAttribute("above", frameC.getObjects().get(i-1).getName());
								object.getAttributes().add(moveOutAttribute);
								
								RavensAttribute leftOfAttribute = new RavensAttribute("left-of", "small");
								object.getAttributes().add(leftOfAttribute);
								
							} else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("circle->triangle")) {
								
								RavensAttribute moveOutAttribute = new RavensAttribute("shape", "triangle");
								object.getAttributes().add(moveOutAttribute);
								
							} else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("square->triangle")) {
								
								RavensAttribute moveOutAttribute = new RavensAttribute("shape", "triangle");
								object.getAttributes().add(moveOutAttribute);
								
							} else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("circle->square")) {
								
								RavensAttribute moveOutAttribute = new RavensAttribute("shape", "square");
								object.getAttributes().add(moveOutAttribute);
								
							} else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("triangle->square")) {
								
								RavensAttribute moveOutAttribute = new RavensAttribute("shape", "square");
								object.getAttributes().add(moveOutAttribute);
								
							} else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("triangle->circle")) {
								
								RavensAttribute moveOutAttribute = new RavensAttribute("shape", "circle");
								object.getAttributes().add(moveOutAttribute);
								
							} else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("square->circle")) {
								
								RavensAttribute moveOutAttribute = new RavensAttribute("shape", "circle");
								object.getAttributes().add(moveOutAttribute);
								
							} else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("nothing")) {
								
								RavensAttribute sameAttribute = new RavensAttribute(frameC.getObjects().get(transformationIndex).getAttributes().get(m).getName(), frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue());
								object.getAttributes().add(sameAttribute);
								System.out.println("same attributes to add");
								
							} else {
								System.out.println("Nothing!");
		
							}							
							
						} 
										
					}			
				
			  }
			
			}
			
			if(deleteDetected == false) {
				generatedFrame.getObjects().add(object);
			}
			
		}
		
	}
	
	public int findBestCorrespondenceForTransformationsFrameC(RavensObject objectA, ArrayList<Transformation> allTransformations) {
		
        int count = 0;
        int previousCount = 0;
		int transformationIndex = 0;
		
		for(int i=0; i<allTransformations.size(); i++) {
			
			count = 0;

			for(int m=0; m<allTransformations.get(i).getFirstNode().getAttributes().size(); m++) {
				
				for(int n=0; n<objectA.getAttributes().size(); n++) {
					
					System.out.println("ObjectA attribute Name: " + objectA.getAttributes().get(n).getName());
					System.out.println("ObjectA attribute Value: " + objectA.getAttributes().get(n).getValue());
					System.out.println("Transformation attribute Name: " + allTransformations.get(i).getFirstNode().getAttributes().get(m).getName());
					System.out.println("Transformation attribute Value: " + allTransformations.get(i).getFirstNode().getAttributes().get(m).getValue());

					if( objectA.getAttributes().get(n).getName().equals(allTransformations.get(i).getFirstNode().getAttributes().get(m).getName()) && 
						objectA.getAttributes().get(n).getValue().equals(allTransformations.get(i).getFirstNode().getAttributes().get(m).getValue()) &&
						(allTransformations.get(i).getFirstNode().getAttributes().get(m).getName().equals("shape") || 
						 allTransformations.get(i).getFirstNode().getAttributes().get(m).getName().equals("size")  ||
						 allTransformations.get(i).getFirstNode().getAttributes().get(m).getName().equals("fill")  ||
						 allTransformations.get(i).getFirstNode().getAttributes().get(m).getName().equals("angle") ||
						 allTransformations.get(i).getFirstNode().getAttributes().get(m).getName().equals("above") ||
						 allTransformations.get(i).getFirstNode().getAttributes().get(m).getName().equals("inside") )) {
						
						count++;
						
					} else {
						
						System.out.println("Transformation does not correspond!");
						
					}
					
				}
			}
			
	        if(count > previousCount) {
	        	transformationIndex = i;
	        	previousCount = count;
	        }
		}
		
		return transformationIndex;
	}
	
	public String nodeNameWithSizeAttributeValue(String attributeSizeValue) {
		
		String nodeName = "Z";
		//Checking to see which type of object we are moving left to
		for(int y=0; y< frameC.getObjects().size(); y++){
							
			//Finding size attribute
			for(int m=0; m< frameC.getObjects().get(y).getAttributes().size(); m++){
				
				if(frameC.getObjects().get(y).getAttributes().get(m).getName().equals("size") && 
				   frameC.getObjects().get(y).getAttributes().get(m).getValue().equals(attributeSizeValue)) {
					
					String foundNode = frameC.getObjects().get(y).getName();
					nodeName = foundNode;
					
				}
				
			}
			
		}
		
		return nodeName;
	}
	
	public String retrieveSolution(HashMap<String, RavensFigure> possibleSolutions) {
		
		int solution = 0;
        int previousCount = 0;
        
        for(int i=1; i<7; i++) {
        	
            ArrayList<RavensObject> frameSolution = possibleSolutions.get(String.valueOf(i)).getObjects();
            
            int count = 0;
            
			System.out.println("Generated Frame Number Objects: " + generatedFrame.getObjects().size());
            for(int y=0; y<generatedFrame.getObjects().size(); y++) {
            	
            	boolean doesItExist = doesObjectExist(generatedFrame.getObjects().get(y), frameSolution);
            	
            	//Check if the number of generated objects matches the number of objects in the solution
            	if(generatedFrame.getObjects().size() != frameSolution.size()){
            		
            		count = 0;
            		
            	} else {
            	
					System.out.println("Generated Frame Current Object Number of Attributes: " + generatedFrame.getObjects().get(y).getAttributes().size());
	                for(int m=0; m<generatedFrame.getObjects().get(y).getAttributes().size(); m++) {
	                
//	                    for(int t=0; t< frameSolution.size(); t++) {

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
								//Determine for each problem if Z, Y, X is large etc
//								if(generatedFrame.getObjects().get(y).getAttributes().get(m).getName().equals("left-of") && 
//								   generatedFrame.getObjects().get(y).getAttributes().get(m).getValue().equals("small")	) {
//									
//									System.out.println("found left-of: small");
//	
//									if(frameSolution.get(y).getAttributes().get(n).getName().equals("left-of")) {
//										
//										for(int p=0; p<frameSolution.size(); p++) {
//											
//											if(frameSolution.get(p).getName().equals(frameSolution.get(y).getAttributes().get(n).getValue())) {
//												
//												for(int l=0; l<frameSolution.get(p).getAttributes().size(); l++){
//													
//													if(frameSolution.get(p).getAttributes().get(l).getValue().equals("small")){
//														System.out.println("found left-of: small");
//														count++;
//													}
//	
//												}
//												
//											}
//										}
//									}
//								} else {
												                    	
//								}		           	
		                    }
//	                    }
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
	
	public boolean doesObjectExist(RavensObject ravenObjectToTest, ArrayList<RavensObject> listOfRavenObjects) {
		
		boolean exists = false;
		
		for(int i=0; i<listOfRavenObjects.size(); i++) {
			
			int count = 0;

			for(int m=0; m<listOfRavenObjects.get(i).getAttributes().size(); m++) {
								
				for(int n=0; n<ravenObjectToTest.getAttributes().size(); n++) {
				
					if((ravenObjectToTest.getAttributes().get(n).getName().equals(listOfRavenObjects.get(i).getAttributes().get(m).getName()) && 
					   ravenObjectToTest.getAttributes().get(n).getValue().equals(listOfRavenObjects.get(i).getAttributes().get(m).getValue()))) {
						count ++;
					}
				}
	
			}
			
			if(count == listOfRavenObjects.get(i).getAttributes().size()) {
				exists = true;
			}
		}
		
		return exists;
	}
	
}
