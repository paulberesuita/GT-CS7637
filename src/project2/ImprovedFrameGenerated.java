package project2;

import java.util.ArrayList;

public class ImprovedFrameGenerated {

    ArrayList<RavensObject> objects = null;
    SemanticNetworkABC previousSemanticNetwork = null;
    FrameB frameB = null;
    FrameC frameC = null;
    RavensFigure generatedFrameFromB = null;
    RavensFigure generatedFrameFromC = null;

    public ImprovedFrameGenerated(SemanticNetworkABC semanticNetworkABC, FrameB frameB, FrameC frameC) {
        this.previousSemanticNetwork = semanticNetworkABC;
        this.frameB = frameB;
        this.frameC = frameC;
    }

    /**
     * Going to perform transformation for 2x2 matrices; will perform C to D first and B to D second; a comparisoin will
     * be perform for both to check they are the same
     */
//    public void createFrame2x2() {
//
//        System.out.println("CREATING FRAME");
//        generatedFrameFromB = new RavensFigure("D1");
//        generatedFrameFromC = new RavensFigure("D1");
//
//        //TRANSFORMATION FROM C TO D FIRST
//        System.out.println("Frame C number of objects: " + frameC.getObjects().size());
//        for(int i=0; i<frameC.getObjects().size(); i++) {
//
//            boolean deleteDetected = false;
//            RavensObject object = new RavensObject("Z");
//
//            //Index for correct transformation to be given to current i object in Frame C
//            int transformationIndex = findBestCorrespondenceForTransformationsFrameC(frameC.getObjects().get(i), previousSemanticNetwork.getAllABTransformations());
//
//            System.out.println("Frame C object number of attributes: " + frameC.getObjects().get(transformationIndex).getAttributes().size());
//            for(int m=0; m< frameC.getObjects().get(transformationIndex).getAttributes().size(); m++) {
//
//                //Above; below; left-of; right-of
//                //Not part of change so transfer those changes get transfer since it only concerns to position; assuming same labeling (correspondence) between Frame C and solution
//                if(frameC.getObjects().get(transformationIndex).getAttributes().get(m).getName().equals("above")) {
//
//                    RavensAttribute sameAttribute = new RavensAttribute(frameC.getObjects().get(transformationIndex).getAttributes().get(m).getName(), frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue());
//                    object.getAttributes().add(sameAttribute);
//                    System.out.println("same attributes to add");
//
//                } else if(frameC.getObjects().get(transformationIndex).getAttributes().get(m).getName().equals("left-of")) {
//
//                    RavensAttribute sameAttribute = new RavensAttribute(frameC.getObjects().get(transformationIndex).getAttributes().get(m).getName(), frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue());
//                    object.getAttributes().add(sameAttribute);
//                    System.out.println("same attributes to add");
//
//                } else {
//
//                    //Check if these are only positions; else just applied the transformations for differences
//
//                    System.out.println("A transformation number of attributes: " + previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationListAttributeName().size());
//                    for(int y=0; y < previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationListAttributeName().size(); y++) {
//
//                        System.out.println("All Tranformations Tranformation: " + previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y));
//                        System.out.println("All Tranformations Attribute Name: " + previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationListAttributeName().get(y));
//
//                        //Check if we even have an attribute to match
//                        if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationListAttributeName().get(y).equals("delete")) {
//
//                            System.out.println("Don't add anything");
//                            deleteDetected = true;
//
//                        }
//
//                        if(frameC.getObjects().get(transformationIndex).getAttributes().get(m).getName().equals(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationListAttributeName().get(y))) {
//
//                            if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("yes fill")) {
//
//                                RavensAttribute yesFillAttribute = new RavensAttribute("fill", "yes");
//                                object.getAttributes().add(yesFillAttribute);
//
//                            } else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("no fill")) {
//
//                                RavensAttribute noFillAttribute = new RavensAttribute("fill", "no");
//                                object.getAttributes().add(noFillAttribute);
//
//                            } else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("left-half fill")) {
//
//                                RavensAttribute noFillAttribute = new RavensAttribute("fill", "left-half");
//                                object.getAttributes().add(noFillAttribute);
//
//                            } else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("right-half fill")) {
//
//                                RavensAttribute noFillAttribute = new RavensAttribute("fill", "right-half");
//                                object.getAttributes().add(noFillAttribute);
//
//                            } else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("top-bottom-left fill")) {
//
//                                String toAddFirst = "top-left,bottom-left";
//                                String toAddSecond = "top-left";
//                                String toAddThird = "bottom-left";
//
//                                if(frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue().contains(toAddFirst)) {
//
//                                    //Contains both so don't add anything
//
//                                } else if(frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue().contains(toAddSecond)) {
//
//                                    //Contains top left so add bottom left
//                                    String bottomLeft = ",bottom-left";
//                                    RavensAttribute noFillAttribute = new RavensAttribute("fill", frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue() + bottomLeft);
//                                    object.getAttributes().add(noFillAttribute);
//
//                                } else {
//                                    //Contains bottom left so add top left
//                                    String topLeft = ",top-left";
//                                    RavensAttribute noFillAttribute = new RavensAttribute("fill", frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue() + topLeft);
//                                    object.getAttributes().add(noFillAttribute);
//                                }
//
//
//                            } else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("top-bottom-right fill")) {
//
//                                String toAddFirst = "top-right,bottom-right";
//                                String toAddSecond = "top-right";
//                                String toAddThird = "bottom-right";
//
//                                if(frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue().contains(toAddFirst)) {
//
//                                    //Contains both so don't add anything
//
//                                } else if(frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue().contains(toAddSecond)) {
//
//                                    //Contains top right so add bottom right
//                                    String bottomLeft = ",bottom-right";
//                                    RavensAttribute noFillAttribute = new RavensAttribute("fill", frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue() + bottomLeft);
//                                    object.getAttributes().add(noFillAttribute);
//
//                                } else {
//                                    //Contains bottom right so add top right
//                                    String topLeft = ",top-right";
//                                    RavensAttribute noFillAttribute = new RavensAttribute("fill", frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue() + topLeft);
//                                    object.getAttributes().add(noFillAttribute);
//                                }
//
//                            } else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("top-top-both fill")) {
//
//                                String toAddFirst = "top-right,top-left";
//                                String toAddSecond = "top-right";
//                                String toAddThird = "top-left";
//
//                                if(frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue().contains(toAddFirst)) {
//
//                                    //Contains both so don't add anything
//
//                                } else if(frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue().contains(toAddSecond)) {
//
//                                    //Contains top right so add top left
//                                    String bottomLeft = ",top-left";
//                                    RavensAttribute noFillAttribute = new RavensAttribute("fill", frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue() + bottomLeft);
//                                    object.getAttributes().add(noFillAttribute);
//
//                                } else {
//                                    //Contains top left so add top right
//                                    String topLeft = ",top-right";
//                                    RavensAttribute noFillAttribute = new RavensAttribute("fill", frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue() + topLeft);
//                                    object.getAttributes().add(noFillAttribute);
//                                }
//
//                            } else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("bottom-bottom-both fill")) {
//
//                                String toAddFirst = "bottom-right,bottom-left";
//                                String toAddSecond = "bottom-right";
//                                String toAddThird = "bottom-left";
//
//                                if(frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue().contains(toAddFirst)) {
//
//                                    //Contains both so don't add anything
//
//                                } else if(frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue().contains(toAddSecond)) {
//
//                                    //Contains bottom right so add bottom left
//                                    String bottomLeft = ",bottom-left";
//                                    RavensAttribute noFillAttribute = new RavensAttribute("fill", frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue() + bottomLeft);
//                                    object.getAttributes().add(noFillAttribute);
//
//                                } else {
//                                    //Contains bottom left so add bottom right
//                                    String topLeft = ",bottom-right";
//                                    RavensAttribute noFillAttribute = new RavensAttribute("fill", frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue() + topLeft);
//                                    object.getAttributes().add(noFillAttribute);
//                                }
//
//                            } else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("bottom-bottom-right fill")) {
//
////								RavensAttribute noFillAttribute = new RavensAttribute("fill", "right-half");
////								object.getAttributes().add(noFillAttribute);
//
//                            } else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("yes large")) {
//
//                                RavensAttribute largeSizeAttribute = new RavensAttribute("size", "large");
//                                object.getAttributes().add(largeSizeAttribute);
//
//                            } else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("no large")) {
//
//                                RavensAttribute largeSizeAttribute = new RavensAttribute("size", "small");
//                                object.getAttributes().add(largeSizeAttribute);
//
//                            } else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("rotate 45")) {
//
//                                RavensAttribute largeSizeAttribute = new RavensAttribute("angle", "45");
//                                object.getAttributes().add(largeSizeAttribute);
//
//                            } else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("rotate 90")) {
//
//                                RavensAttribute largeSizeAttribute = new RavensAttribute("angle", "90");
//                                object.getAttributes().add(largeSizeAttribute);
//
//                            } else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("rotate 180")) {
//
//                                RavensAttribute largeSizeAttribute = new RavensAttribute("angle", "180");
//                                object.getAttributes().add(largeSizeAttribute);
//
//                            }  else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("move out")) {
//
//                                RavensAttribute moveOutAttribute = new RavensAttribute("above", frameC.getObjects().get(i-1).getName());
//                                object.getAttributes().add(moveOutAttribute);
//
//                            } else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("move out left small")) {
//
////								String left = nodeNameWithSizeAttributeValue("small");
//
//                                RavensAttribute moveOutAttribute = new RavensAttribute("above", frameC.getObjects().get(i-1).getName());
//                                object.getAttributes().add(moveOutAttribute);
//
//                                RavensAttribute leftOfAttribute = new RavensAttribute("left-of", "small");
//                                object.getAttributes().add(leftOfAttribute);
//
//                            } else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("circle->triangle")) {
//
//                                RavensAttribute moveOutAttribute = new RavensAttribute("shape", "triangle");
//                                object.getAttributes().add(moveOutAttribute);
//
//                            } else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("square->triangle")) {
//
//                                RavensAttribute moveOutAttribute = new RavensAttribute("shape", "triangle");
//                                object.getAttributes().add(moveOutAttribute);
//
//                            } else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("circle->square")) {
//
//                                RavensAttribute moveOutAttribute = new RavensAttribute("shape", "square");
//                                object.getAttributes().add(moveOutAttribute);
//
//                            } else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("triangle->square")) {
//
//                                RavensAttribute moveOutAttribute = new RavensAttribute("shape", "square");
//                                object.getAttributes().add(moveOutAttribute);
//
//                            } else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("triangle->circle")) {
//
//                                RavensAttribute moveOutAttribute = new RavensAttribute("shape", "circle");
//                                object.getAttributes().add(moveOutAttribute);
//
//                            } else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("square->circle")) {
//
//                                RavensAttribute moveOutAttribute = new RavensAttribute("shape", "circle");
//                                object.getAttributes().add(moveOutAttribute);
//
//                            } else if(previousSemanticNetwork.getAllABTransformations().get(transformationIndex).getTransformationList().get(y).equals("nothing")) {
//
//                                RavensAttribute sameAttribute = new RavensAttribute(frameC.getObjects().get(transformationIndex).getAttributes().get(m).getName(), frameC.getObjects().get(transformationIndex).getAttributes().get(m).getValue());
//                                object.getAttributes().add(sameAttribute);
//                                System.out.println("same attributes to add");
//
//                            } else {
//                                System.out.println("Nothing!");
//
//                            }
//
//                        }
//
//                    }
//
//                }
//
//            }
//
//            if(deleteDetected == false) {
//                generatedFrame.getObjects().add(object);
//            }
//
//        }
//
//    }
}
