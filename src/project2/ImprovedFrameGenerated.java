package project2;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;

public class ImprovedFrameGenerated {

    ArrayList<RavensObject> objects = null;
    SemanticNetworkABC semanticNetwork = null;
    RavensFigure generatedFrameDFromB = null;
    RavensFigure generatedFrameDFromC = null;
    RavensFigure figureB = null;
    RavensFigure figureC = null;

    public ImprovedFrameGenerated(SemanticNetworkABC semanticNetworkABC, RavensFigure figureB, RavensFigure figureC) {
        this.semanticNetwork = semanticNetworkABC;
        this.figureB = figureB;
        this.figureC = figureC;
    }

    /**
     * Going to perform transformation for 2x2 matrices; will perform C to D first and B to D second; a comparisoin will
     * be perform for both to check they are the same
     */
    public void createFrame2x2() {

        System.out.println("CREATING FRAME");
        generatedFrameDFromB = new RavensFigure("D1");
        generatedFrameDFromC = new RavensFigure("D2");

        //TRANSFORMATION FROM C TO D FIRST
        for(int i=0; i<semanticNetwork.getFrameC().getObjects().size(); i++) {

            RavensObject object = new RavensObject(semanticNetwork.getFrameC().getObjects().get(i).getName());

            for(int j=0; j<semanticNetwork.getFrameC().getObjects().get(i).getAttributes().size(); j++) {

                RavensAttribute attribute = new RavensAttribute(semanticNetwork.getFrameC().getObjects().get(i).getAttributes().get(j).getName(), semanticNetwork.getFrameC().getObjects().get(i).getAttributes().get(j).getValue());

                object.getAttributes().add(attribute);
            }

            generatedFrameDFromC.getObjects().add(object);


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

                    } else {

                    }

                }
            }


        }

        System.out.println("Done Creating Frame D");

    }

    public int findBestCorrespondenceForTransformationsFrameC(FrameC frameC, Change changeTransformation) {

        int count = 0;
        int previousCount = 0;
        int transformationIndex = 0;

        for(int i=0; i<frameC.getObjects().size(); i++) {

            count = 0;

            for(int m=0; m<frameC.getObjects().get(i).getAttributes().size(); m++) {

                    for(int n=0; n<changeTransformation.getFirstObject().getAttributes().size(); n++) {

                        if(changeTransformation.getFirstObject().getAttributes().get(n).getName().equals(frameC.getObjects().get(i).getAttributes().get(m).getName()) &&
                                changeTransformation.getFirstObject().getAttributes().get(n).getValue().equals(frameC.getObjects().get(i).getAttributes().get(m).getValue()) &&
                                (frameC.getObjects().get(i).getAttributes().get(m).getName().equals("shape") ||
                                        frameC.getObjects().get(i).getAttributes().get(m).getName().equals("size")  ||
                                        frameC.getObjects().get(i).getAttributes().get(m).getName().equals("fill")  ||
                                        frameC.getObjects().get(i).getAttributes().get(m).getName().equals("angle") ||
                                        frameC.getObjects().get(i).getAttributes().get(m).getName().equals("above")   )) {

                            count = count + 5;

                        } else if(changeTransformation.getFirstObject().getAttributes().get(n).getName().equals(frameC.getObjects().get(i).getAttributes().get(m).getName()) &&
                                !changeTransformation.getFirstObject().getAttributes().get(n).getValue().equals(frameC.getObjects().get(i).getAttributes().get(m).getValue()) &&
                                frameC.getObjects().get(i).getAttributes().get(m).getName().equals("overlaps")) {

                            count = count + 4;

                        }else if(changeTransformation.getFirstObject().getAttributes().get(n).getName().equals(frameC.getObjects().get(i).getAttributes().get(m).getName()) &&
                                !changeTransformation.getFirstObject().getAttributes().get(n).getValue().equals(frameC.getObjects().get(i).getAttributes().get(m).getValue()) &&
                                frameC.getObjects().get(i).getAttributes().get(m).getName().equals("angle")) {

                            count = count + 3;

                        } else if(changeTransformation.getFirstObject().getAttributes().get(n).getName().equals(frameC.getObjects().get(i).getAttributes().get(m).getName()) &&
                                !changeTransformation.getFirstObject().getAttributes().get(n).getValue().equals(frameC.getObjects().get(i).getAttributes().get(m).getValue()) &&
                                frameC.getObjects().get(i).getAttributes().get(m).getName().equals("size")) {

                            count = count + 2;

                        } else if(changeTransformation.getFirstObject().getAttributes().get(n).getName().equals(frameC.getObjects().get(i).getAttributes().get(m).getName()) &&
                                !changeTransformation.getFirstObject().getAttributes().get(n).getValue().equals(frameC.getObjects().get(i).getAttributes().get(m).getValue()) &&
                                frameC.getObjects().get(i).getAttributes().get(m).getName().equals("fill")) {

                            count = count + 1;

                        } else if(changeTransformation.getFirstObject().getAttributes().get(n).getName().equals(frameC.getObjects().get(i).getAttributes().get(m).getName()) &&
                                !changeTransformation.getFirstObject().getAttributes().get(n).getValue().equals(frameC.getObjects().get(i).getAttributes().get(m).getValue()) &&
                                frameC.getObjects().get(i).getAttributes().get(m).getName().equals("above")) {


                            count = count + 1;

                        } else {
                            count = count + 0;
                        }
                    }

            }

            int differentNumberOfAttributes = Math.abs(frameC.getObjects().get(i).getAttributes().size()-changeTransformation.getFirstObject().getAttributes().size());
            count = count - 10*differentNumberOfAttributes;

            if(count > previousCount) {
                transformationIndex = i;
                previousCount = count;
            }
        }

        return transformationIndex;
    }
}
