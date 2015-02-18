package project2;

import java.util.ArrayList;

public class GeneratedFrame {

    ArrayList<RavensObject> objects = null;
    SemanticNetwork semanticNetwork = null;
    RavensFigure generatedFrameDFromB = null;
    RavensFigure generatedFrameDFromC = null;
    RavensFigure figureB = null;
    RavensFigure figureC = null;

    public GeneratedFrame(SemanticNetwork semanticNetwork, RavensFigure figureB, RavensFigure figureC) {
        this.semanticNetwork = semanticNetwork;
        this.figureB = figureB;
        this.figureC = figureC;
    }

    /**
     * Going to perform transformation for 2x2 matrices; will perform C to D first and B to D second; a comparisoin will
     * be perform for both to check they are the same
     */
    public void createFrame() {

        System.out.println("CREATING FRAME");
        generatedFrameDFromB = new RavensFigure("D1");
        generatedFrameDFromC = new RavensFigure("D2");

        boolean toAdd = true;
        //TRANSFORMATION FROM C TO D FIRST
        for(int i=0; i<semanticNetwork.getFrameC().getObjects().size(); i++) {

            RavensObject object = new RavensObject(semanticNetwork.getFrameC().getObjects().get(i).getName());

            for(int j=0; j<semanticNetwork.getFrameC().getObjects().get(i).getAttributes().size(); j++) {

                RavensAttribute attribute = new RavensAttribute(semanticNetwork.getFrameC().getObjects().get(i).getAttributes().get(j).getName(), semanticNetwork.getFrameC().getObjects().get(i).getAttributes().get(j).getValue());

                object.getAttributes().add(attribute);
            }

            //Checking if the object to be added is in the removal list; if it is then it won't be added
            if(semanticNetwork.getABRemovals().size() > 0) {
                for(int r=0; r<semanticNetwork.getABRemovals().size(); r++) {

                    for(int t=0; t<semanticNetwork.getABRemovals().get(r).getAttributes().size(); t++) {

                        for(int m=0; m<object.getAttributes().size(); m++) {

                            //Criteria is shape size
                            if (object.getAttributes().get(m).getName().equals(semanticNetwork.getABRemovals().get(r).getAttributes().get(t).getName())
                                    && object.getAttributes().get(m).getValue().equals(semanticNetwork.getABRemovals().get(r).getAttributes().get(t).getValue())) {
                                toAdd = false;
                            }
                        }
                    }

                }
            }

            if(toAdd) {
                generatedFrameDFromC.getObjects().add(object);
            }

        }


        //Apply transformation to correct object in FrameD (copy of C)
        for(int i=0; i<semanticNetwork.getABTransformations().size(); i++) {

            int transformationIndex = findBestCorrespondenceForTransformationsFrameC(semanticNetwork.getFrameC(), semanticNetwork.getABTransformations().get(i));
            String shapeOfFrameDObject = generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(0).getValue();

            for(int j=0; j<generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().size(); j++) {

                for(int y=0; y<semanticNetwork.getABTransformations().get(i).getTransformations().size(); y++){

                    if(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("angle") &&
                       semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("rotate -45")){

                        if(shapeOfFrameDObject.equals("circle")) {
                            RavensAttribute attribute = new RavensAttribute("angle", generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getValue());
                            generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                        } else {
                            RavensAttribute attribute = new RavensAttribute("angle", Utility.performRotation(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), "-45"));
                            generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                        }

                    } else if(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("angle") &&
                            semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("rotate 45")){

                        if(shapeOfFrameDObject.equals("circle")) {
                            RavensAttribute attribute = new RavensAttribute("angle", generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getValue());
                            generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                        } else {
                            RavensAttribute attribute = new RavensAttribute("angle", Utility.performRotation(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), "45"));
                            generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                        }

                    } else if(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("angle") &&
                            semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("rotate 180")){

                        if(shapeOfFrameDObject.equals("circle")) {
                            RavensAttribute attribute = new RavensAttribute("angle", generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getValue());
                            generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                        } else {
                            RavensAttribute attribute = new RavensAttribute("angle", Utility.performRotation(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), "180"));
                            generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                        }

                    } else if(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("angle") &&
                            semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("reflect left")) {

                        RavensAttribute attribute = new RavensAttribute("angle", Utility.performReflection(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), shapeOfFrameDObject));
                        generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                    } else if(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("angle") &&
                            semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("reflect right")) {

                        RavensAttribute attribute = new RavensAttribute("angle", Utility.performReflection(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), shapeOfFrameDObject));
                        generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                    } else if(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("fill") &&
                            semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("yes fill")) {

                        RavensAttribute attribute = new RavensAttribute("fill", "yes");
                        generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                    } else if(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("fill") &&
                            semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("no fill")) {

                        RavensAttribute attribute = new RavensAttribute("fill", "no");
                        generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                    } else if(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("fill") &&
                            semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("left-half fill")) {

                        RavensAttribute attribute = new RavensAttribute("fill", "left-half");
                        generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                    } else if(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("fill") &&
                            semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("right-half fill")) {

                        RavensAttribute attribute = new RavensAttribute("fill", "right-half");
                        generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                    }  else if(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("fill") &&
                            semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("top-bottom-left fill")) {

                        String toAddFirst = "top-left,bottom-left";
                        String toAddSecond = "top-left";
                        String toAddThird = "bottom-left";

                        if(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getValue().contains(toAddFirst)) {

                            //Contains both so don't add anything

                        } else if(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getValue().contains(toAddSecond)) {

                            //Contains top left so add bottom left
                            String bottomLeft = ",bottom-left";
                            RavensAttribute attribute = new RavensAttribute("fill", generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getValue() + bottomLeft);
                            generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        } else {
                            //Contains bottom left so add top left
                            String topLeft = ",top-left";
                            RavensAttribute attribute = new RavensAttribute("fill", generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getValue() + topLeft);
                            generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                        }


                    } else if(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("fill") &&
                            semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("top-bottom-right fill")) {

                        String checkOne = "top-right,bottom-right";
                        String checkTwo = "top-right";
                        String checkThree = "bottom-right";
                        String checkFour = "bottom-left,bottom-right";


                        if(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getValue().equals(checkOne)) {

                            //Contains both so don't add anything

                        } else if(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getValue().equals(checkTwo)) {

                            //Contains top right so add bottom right
                            String bottomRight = ",bottom-right";
                            RavensAttribute attribute = new RavensAttribute("fill", generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getValue() + bottomRight);
                            generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        } else if(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getValue().equals(checkThree)) {

                            //Contains bottom right so add top right
                            String topRight = "top-right,";
                            RavensAttribute attribute = new RavensAttribute("fill", topRight + generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getValue());
                            generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        } else if(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getValue().equals(checkFour)) {

                            //Contains bottom right and bottom left so add top right
                            String topRight = "top-right,";
                            RavensAttribute attribute = new RavensAttribute("fill", topRight + generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getValue());
                            generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        } else {
                        }

                    } else if(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("size") &&
                            semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("no large")) {

                        RavensAttribute attribute = new RavensAttribute("size", "small");
                        generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                    } else if(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("size") &&
                            semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("yes large")) {

                        RavensAttribute attribute = new RavensAttribute("size", "large");
                        generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                    } else {

                    }

                }
            }


        }

        System.out.println("Done Creating Frame D");

    }

    public int findBestCorrespondenceForTransformationsFrameC(FrameC frameC, Transformation transformationTransformation) {

        int count = 0;
        int previousCount = 0;
        int transformationIndex = 0;

        for(int i=0; i<frameC.getObjects().size(); i++) {

            count = 0;

            for(int m=0; m<frameC.getObjects().get(i).getAttributes().size(); m++) {

                    for(int n=0; n< transformationTransformation.getFirstObject().getAttributes().size(); n++) {

                        if(transformationTransformation.getFirstObject().getAttributes().get(n).getName().equals(frameC.getObjects().get(i).getAttributes().get(m).getName()) &&
                                transformationTransformation.getFirstObject().getAttributes().get(n).getValue().equals(frameC.getObjects().get(i).getAttributes().get(m).getValue()) &&
                                (frameC.getObjects().get(i).getAttributes().get(m).getName().equals("shape") ||
                                        frameC.getObjects().get(i).getAttributes().get(m).getName().equals("size")  ||
                                        frameC.getObjects().get(i).getAttributes().get(m).getName().equals("fill")  ||
                                        frameC.getObjects().get(i).getAttributes().get(m).getName().equals("angle") ||
                                        frameC.getObjects().get(i).getAttributes().get(m).getName().equals("above")   )) {

                            count = count + 5;

                        } else if(transformationTransformation.getFirstObject().getAttributes().get(n).getName().equals(frameC.getObjects().get(i).getAttributes().get(m).getName()) &&
                                !transformationTransformation.getFirstObject().getAttributes().get(n).getValue().equals(frameC.getObjects().get(i).getAttributes().get(m).getValue()) &&
                                frameC.getObjects().get(i).getAttributes().get(m).getName().equals("overlaps")) {

                            count = count + 4;

                        }else if(transformationTransformation.getFirstObject().getAttributes().get(n).getName().equals(frameC.getObjects().get(i).getAttributes().get(m).getName()) &&
                                !transformationTransformation.getFirstObject().getAttributes().get(n).getValue().equals(frameC.getObjects().get(i).getAttributes().get(m).getValue()) &&
                                frameC.getObjects().get(i).getAttributes().get(m).getName().equals("angle")) {

                            count = count + 3;

                        } else if(transformationTransformation.getFirstObject().getAttributes().get(n).getName().equals(frameC.getObjects().get(i).getAttributes().get(m).getName()) &&
                                !transformationTransformation.getFirstObject().getAttributes().get(n).getValue().equals(frameC.getObjects().get(i).getAttributes().get(m).getValue()) &&
                                frameC.getObjects().get(i).getAttributes().get(m).getName().equals("size")) {

                            count = count + 2;

                        } else if(transformationTransformation.getFirstObject().getAttributes().get(n).getName().equals(frameC.getObjects().get(i).getAttributes().get(m).getName()) &&
                                !transformationTransformation.getFirstObject().getAttributes().get(n).getValue().equals(frameC.getObjects().get(i).getAttributes().get(m).getValue()) &&
                                frameC.getObjects().get(i).getAttributes().get(m).getName().equals("fill")) {

                            count = count + 1;

                        } else if(transformationTransformation.getFirstObject().getAttributes().get(n).getName().equals(frameC.getObjects().get(i).getAttributes().get(m).getName()) &&
                                !transformationTransformation.getFirstObject().getAttributes().get(n).getValue().equals(frameC.getObjects().get(i).getAttributes().get(m).getValue()) &&
                                frameC.getObjects().get(i).getAttributes().get(m).getName().equals("above")) {


                            count = count + 1;

                        } else {
                            count = count + 0;
                        }
                    }

            }

            int differentNumberOfAttributes = Math.abs(frameC.getObjects().get(i).getAttributes().size()- transformationTransformation.getFirstObject().getAttributes().size());
            count = count - 3*differentNumberOfAttributes;

            if(count > previousCount) {
                transformationIndex = i;
                previousCount = count;
            }
        }

        return transformationIndex;
    }
}
