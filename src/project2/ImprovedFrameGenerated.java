package project2;

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

            for(int j=0; j<generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().size(); j++) {

                for(int y=0; y<semanticNetwork.getABTransformations().get(i).getTransformations().size(); y++){

                    if(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("angle") &&
                       semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("rotate -45")){

                        RavensAttribute attribute = new RavensAttribute("angle", "0");
                        generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                    } else if(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("angle") &&
                            semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("reflect left")) {

                        int angleValue = Integer.parseInt(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getValue());
                        if(359 > angleValue && angleValue > 180){
                            angleValue = angleValue + 90;
                        } else {
                            angleValue = angleValue - 90;
                        }

                        RavensAttribute attribute = new RavensAttribute("angle", String.valueOf(angleValue));
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

                //Will check if we have too many attributes for frame C object
                if(m <= changeTransformation.getFirstObject().getAttributes().size() ) {
                           count = count - 10;
                } else {
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
            }

            if(count > previousCount) {
                transformationIndex = i;
                previousCount = count;
            }
        }

        return transformationIndex;
    }
}
