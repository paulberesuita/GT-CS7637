package project1;

import java.util.ArrayList;

public class Transformation {

	RavensObject firstNode = null;
	RavensObject secondNode = null;
	ArrayList<String> transformationList = null;
	ArrayList<String> transformationListAttributeName = null;
	FrameA frameA = null;
	FrameB frameB = null;

	public Transformation(RavensObject firstNode, RavensObject secondNode, FrameA frameA, FrameB frameB) {
		this.firstNode = firstNode;
		this.secondNode = secondNode;
		this.frameA = frameA;
		this.frameB = frameB;
		transformationList = new ArrayList<String>();
		transformationListAttributeName = new ArrayList<String>();
	}
	
	/* Set of transformations will include flip, fill, partial fill, 
	*  move down, move up, expand, shrink, delete, and 
	* rotate 180/135/90/45 degrees.*/
	public void checkDifferencesBetweenNodes() {
	
		//Check if Object got deleted
		if(secondNode == null) {
			
			addTransformation("delete");
			addTransformationAttributeName("delete");
		
		} else {
		
			for(int i=0; i< firstNode.getAttributes().size(); i++) {
				
				for(int y=0; y< secondNode.getAttributes().size(); y++) {
					
//					if(!firstNode.getAttributes().get(i).getValue().equals(secondNode.getAttributes().get(y).getValue())) {
						
						System.out.println("First Node Name: " + firstNode.getAttributes().get(i).getName());
						System.out.println("First Node Value: " +firstNode.getAttributes().get(i).getValue());
						System.out.println("Second Node Name: " + secondNode.getAttributes().get(y).getName());
						System.out.println("Second Node Value: " + secondNode.getAttributes().get(y).getValue());
						System.out.println("Difference Found so store it to transformation list");
						
						String difference = whichDifference(firstNode.getAttributes().get(i).getName(), firstNode.getAttributes().get(i).getValue(), secondNode.getAttributes().get(y).getName(), secondNode.getAttributes().get(y).getValue());
						
						//Only add transformation if there is a difference
						if(!difference.equals("")) {
							addTransformation(difference);
							addTransformationAttributeName(firstNode.getAttributes().get(i).getName());
						}

				}
				
			}
			
		}
		
	}
	
	public String whichDifference(String firstNodeName, String firstNodeValue, String secondNodeName, String secondNodeValue) {
		
		//Check Fill
		if(firstNodeName.equals("fill") && secondNodeName.equals("fill")) {
			
			if(firstNodeValue.equals("no") && secondNodeValue.equals("yes")) {
				return "yes fill";
			} else if(firstNodeValue.equals("yes") && secondNodeValue.equals("no")) {
				return "no fill";
			} else if(firstNodeValue.equals("no") && secondNodeValue.equals("left-half")) {
				return "left-half fill";
			} else if(firstNodeValue.equals("no") && secondNodeValue.equals("right-half")) {
				return "right-half fill";
			} else if(firstNodeValue.equals("no") && secondNodeValue.equals("top-left,bottom-left")) {
				return "top-bottom-left fill";
			} else if(firstNodeValue.equals("no") && secondNodeValue.equals("top-right,bottom-right")) {
				return "top-bottom-right fill";
			} else if(firstNodeValue.equals("no") && secondNodeValue.equals("top-left,top-right")) {
				return "top-top-both fill";
			} else if(firstNodeValue.equals("no") && secondNodeValue.equals("bottom-left,bottom-right")) {
				return "bottom-bottom-both fill";
			} else {
				return "nothing";
			}
			
		} else if(firstNodeName.equals("shape") && secondNodeName.equals("shape")) {
				
				if(firstNodeValue.equals("circle") && secondNodeValue.equals("triangle")) {
					return "circle->triangle";
				} else if(firstNodeValue.equals("square") && secondNodeValue.equals("triangle")) {
					return "square->triangle";
				} else if(firstNodeValue.equals("circle") && secondNodeValue.equals("square")) {
					return "circle->square";
				} else if(firstNodeValue.equals("triangle") && secondNodeValue.equals("square")) {
					return "triangle->square";
				} else if(firstNodeValue.equals("triangle") && secondNodeValue.equals("circle")) {
					return "triangle->circle";
				} else if(firstNodeValue.equals("square") && secondNodeValue.equals("circle")) {
					return "square->circle";
				} else {
					return "nothing";
				}
				
		} else if(firstNodeName.equals("size") && secondNodeName.equals("size")) {
			
			if(firstNodeValue.equals("small") && secondNodeValue.equals("large")) {
				return "yes large";
			} else if(firstNodeValue.equals("large") && secondNodeValue.equals("small")) {
				return "no large";
			} else {
				return "nothing";
			}
			
		} else if(firstNodeName.equals("angle") && secondNodeName.equals("angle")) {
			
			if(firstNodeValue.equals("0") && secondNodeValue.equals("45")) {
				return "rotate 45";
			} else if(firstNodeValue.equals("0") && secondNodeValue.equals("90")) {
				return "rotate 90";
			} else if(firstNodeValue.equals("0") && secondNodeValue.equals("180")) {
				return "rotate 180";
			} else {
				return "nothing";
			}
			
		} else if(firstNodeName.equals("inside") && secondNodeName.equals("above")) {
			
			String toMove = "move out";
			String position = positionCurrentNodesObject();	
			
			toMove = toMove + position;
			
			System.out.println("Movement with position: " +  toMove);
			
			if(firstNodeValue.equals("Z") && secondNodeValue.equals("Z")) {
				return toMove;
			} else if(firstNodeValue.equals("Z,Y") && secondNodeValue.equals("Z")) {
				return toMove;
			} else if(firstNodeValue.equals("Y") && secondNodeValue.equals("Y")) {
				return toMove;
			} else {
				return "nothing";
			}
			
		} else {
			
			System.out.print("Could not find which difference");
		}	
		
		return "";
	}
	
	public String positionCurrentNodesObject() {
		
		String position = "";
		
		//Need to check if moving above left or above right
		for(int i=0; i< secondNode.getAttributes().size(); i++) {
			
			if(secondNode.getAttributes().get(i).getName().equals("left-of") && secondNode.getAttributes().get(i).getValue().equals("X")){
				
				//Checking to see which type of object we are moving left to
				for(int y=0; y< frameB.getObjects().size(); y++){
					
					if(frameB.getObjects().get(y).getName().equals("X")){
						
						//Finding size attribute
						for(int m=0; m< frameB.getObjects().get(y).getAttributes().size(); m++){
							
							if(frameB.getObjects().get(y).getAttributes().get(m).getName().equals("size")) {
								
								if(frameB.getObjects().get(y).getAttributes().get(m).getValue().equals("small")) {
									
									position = " left small";
									
								} else if(frameB.getObjects().get(y).getAttributes().get(m).getValue().equals("medium")) {
									
									position = " left medium";
									
								} else if(frameB.getObjects().get(y).getAttributes().get(m).getValue().equals("large")) {
									
									position = " left large";
									
								} else {
									
								}
							}
							
						}

					}
					
				}
				
			} else if(secondNode.getAttributes().get(i).getName().equals("left-of") && secondNode.getAttributes().get(i).getValue().equals("Y")) {
				
				//Checking to see which type of object we are moving left to
				for(int y=0; y< frameB.getObjects().size(); y++){
					
					if(frameB.getObjects().get(y).getName().equals("Y")){
						
						//Finding size attribute
						for(int m=0; m< frameB.getObjects().get(y).getAttributes().size(); m++){
							
							if(frameB.getObjects().get(y).getAttributes().get(m).getName().equals("size")) {
								
								if(frameB.getObjects().get(y).getAttributes().get(m).getValue().equals("small")) {
									
									position = " left small";
									
								} else if(frameB.getObjects().get(y).getAttributes().get(m).getValue().equals("medium")) {
									
									position = " left medium";
									
								} else if(frameB.getObjects().get(y).getAttributes().get(m).getValue().equals("large")) {
									
									position = " left large";
									
								} else {
									
								}
							}
							
						}

					}
					
				}
				
			} else if(secondNode.getAttributes().get(i).getName().equals("left-of") && secondNode.getAttributes().get(i).getValue().equals("Z")){
				
				//Checking to see which type of object we are moving left to
				for(int y=0; y< frameB.getObjects().size(); y++){
					
					if(frameB.getObjects().get(y).getName().equals("Z")){
						
						//Finding size attribute
						for(int m=0; m< frameB.getObjects().get(y).getAttributes().size(); m++){
							
							if(frameB.getObjects().get(y).getAttributes().get(m).getName().equals("size")) {
								
								if(frameB.getObjects().get(y).getAttributes().get(m).getValue().equals("small")) {
									
									position = " left small";
									
								} else if(frameB.getObjects().get(y).getAttributes().get(m).getValue().equals("medium")) {
									
									position = " left medium";
									
								} else if(frameB.getObjects().get(y).getAttributes().get(m).getValue().equals("large")) {
									
									position = " left large";
									
								} else {
									
								}
							}
							
						}

					}
					
				}
			} else {
				
			}
		}
		
		return position;
	}
	
	
	public void addTransformation(String transformation) {
		transformationList.add(transformation);
	}
	
	public void addTransformationAttributeName(String attributeName) {
		transformationListAttributeName.add(attributeName);
	}
	
	public RavensObject getFirstNode() {
		return firstNode;
	}

	public void setFirstNode(RavensObject firstNode) {
		this.firstNode = firstNode;
	}

	public RavensObject getSecondNode() {
		return secondNode;
	}

	public void setSecondNode(RavensObject secondNode) {
		this.secondNode = secondNode;
	}

	public ArrayList<String> getTransformationList() {
		return transformationList;
	}

	public void setTransformationList(ArrayList<String> transformationList) {
		this.transformationList = transformationList;
	}	
	
	public ArrayList<String> getTransformationListAttributeName() {
		return transformationListAttributeName;
	}

	public void setTransformationListAttributeName(
			ArrayList<String> transformationListAttributeName) {
		this.transformationListAttributeName = transformationListAttributeName;
	}
	
}
