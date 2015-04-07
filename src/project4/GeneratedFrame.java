package project4;

import java.util.ArrayList;

public class GeneratedFrame {

    SemanticNetwork semanticNetwork = null;
    RavensFigure generatedFrameDFromB = null;
    RavensFigure generatedFrameDFromC = null;
    RavensFigure generatedFrameDFromBC = null;

    RavensFigure generatedFrameIFromH = null;

    boolean uncertainRotation = false;

    public GeneratedFrame(SemanticNetwork semanticNetwork) {

        this.semanticNetwork = semanticNetwork;
    }

    /**
     * Going to perform transformation for 2x2 matrices; will perform C to D first and B to D second; a comparisoin will
     * be perform for both to check they are the same
     */
    public void createFrame2x1() {

        System.out.println("CREATING FRAME");
        generatedFrameDFromC = new RavensFigure("D2");

        performTransformationFromC();
        System.out.println("Done Creating Frame D from C");
    }

    public void createFrame2x2() {

        System.out.println("CREATING FRAME");
        generatedFrameDFromB = new RavensFigure("D1");
        generatedFrameDFromC = new RavensFigure("D2");
        generatedFrameDFromBC = new RavensFigure("D3");

        performTransformationFromB();
        System.out.println("Done Creating Frame D from B");

        performTransformationFromC();
        System.out.println("Done Creating Frame D from C");

        performTransformationFromBThenCFirst();
        performTransformationFromBThenCSecond();
    }

    public void createFrame3x3() {

        System.out.println("CREATING FRAME");
        generatedFrameIFromH = new RavensFigure("I");

        performTransformationFromH();
        System.out.println("Done Creating Frame D from C");
    }

    public void performTransformationFromB() {

        //TRANSFORMATION FROM B TO D FIRST
        for(int i=0; i<semanticNetwork.getFrameB().getObjects().size(); i++) {

            RavensObject object = new RavensObject(semanticNetwork.getFrameB().getObjects().get(i).getName());

            for(int j=0; j<semanticNetwork.getFrameB().getObjects().get(i).getAttributes().size(); j++) {

                RavensAttribute attribute = new RavensAttribute(semanticNetwork.getFrameB().getObjects().get(i).getAttributes().get(j).getName(), semanticNetwork.getFrameB().getObjects().get(i).getAttributes().get(j).getValue());

                object.getAttributes().add(attribute);
            }

            generatedFrameDFromB.getObjects().add(object);

        }


        //Removing objects in the removal list
        if(semanticNetwork.getACRemovals().size() > 0) {

            for(int i=0; i<semanticNetwork.getACRemovals().size(); i++) {

                int index = Utility.objectIndexToRemove(semanticNetwork.getACRemovals().get(i), generatedFrameDFromB);
                generatedFrameDFromB.getObjects().remove(index);
            }

        }

        //Adding objects in the adding list
        if(semanticNetwork.getACAdditions().size() > 0) {

            for(int i=0; i<semanticNetwork.getACAdditions().size(); i++) {

                generatedFrameDFromB.getObjects().add(semanticNetwork.getACAdditions().get(i));
            }

        }

        //SPECIAL CASE - ONLY ROTATING - BASIC PROBLEM 15
        boolean onlyRotation90 = false;
        boolean onlyRotation180 = false;
        boolean onlyRotation270 = false;

        for(int i=0; i<semanticNetwork.getACTransformations().size(); i++) {

            for (int y = 0; y < semanticNetwork.getACTransformations().get(i).getTransformations().size(); y++) {

                if(semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("rotate 90")){
                    onlyRotation90 = true;
                    onlyRotation180 = false;
                    onlyRotation270 = false;
                } else if(semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("rotate 180")){
                    onlyRotation90 = false;
                    onlyRotation180 = true;
                    onlyRotation270 = false;
                } else if(semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("rotate 270")){
                    onlyRotation90 = false;
                    onlyRotation180 = false;
                    onlyRotation270 = true;
                }else {
                    onlyRotation90 = false;
                    onlyRotation180 = false;
                    onlyRotation270 = false;
                }
            }
        }

        boolean onlyMultiple = false;
        //Special Case - ONLY MULTIPLE - 2X2 BASIC PROBLEM 13 AND 16
        if(semanticNetwork.MultipleTransformationAC > 0){
            onlyMultiple = true;
        }

        if(onlyRotation90 || onlyRotation180 || onlyRotation270) {

            for(int i=0; i<generatedFrameDFromB.getObjects().size(); i++) {

                for(int j=0; j<generatedFrameDFromB.getObjects().get(i).getAttributes().size(); j++) {

                    if(generatedFrameDFromB.getObjects().get(i).getAttributes().get(j).getName().equals("angle")){

                        if(onlyRotation90) {
                            RavensAttribute attribute = new RavensAttribute("angle","90");
                            generatedFrameDFromB.getObjects().get(i).getAttributes().set(j, attribute);
                        } else if(onlyRotation180) {
                            RavensAttribute attribute = new RavensAttribute("angle","180");
                            generatedFrameDFromB.getObjects().get(i).getAttributes().set(j, attribute);
                        } else if(onlyRotation270) {
                            RavensAttribute attribute = new RavensAttribute("angle","270");
                            generatedFrameDFromB.getObjects().get(i).getAttributes().set(j, attribute);
                        } else {

                        }

                    }

                }

            }

        } else if(onlyMultiple) {

            RavensObject object = generatedFrameDFromB.getObjects().get(0);

            for(int i=1; i<semanticNetwork.MultipleTransformationAC; i++) {
                generatedFrameDFromB.getObjects().add(object);
            }

        } else {

            //Apply transformation to correct object in FrameD (copy of C)
            for(int i=0; i<semanticNetwork.getACTransformations().size(); i++) {

                int transformationIndex = findBestCorrespondenceForTransformationsFrame(semanticNetwork.getFrameB(), semanticNetwork.getACTransformations().get(i));
                String shapeOfFrameDObject = generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(0).getValue();

                for(int j=0; j<generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().size(); j++) {

                    for(int y=0; y<semanticNetwork.getACTransformations().get(i).getTransformations().size(); y++){

                        if(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("angle") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("rotate -45")){

                            if(shapeOfFrameDObject.equals("circle")) {
                                RavensAttribute attribute = new RavensAttribute("angle", generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getValue());
                                generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                            } else {
                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performRotation(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), "-45"));
                                generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                            }

                        } else if(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("angle") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("rotate 45")){

                            if(shapeOfFrameDObject.equals("circle")) {
                                RavensAttribute attribute = new RavensAttribute("angle", generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getValue());
                                generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                            } else {
                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performRotation(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), "45"));
                                generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                            }

                        } else if(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("angle") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("rotate 180")){

                            if(shapeOfFrameDObject.equals("circle")) {
                                RavensAttribute attribute = new RavensAttribute("angle", generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getValue());
                                generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                            } else {
                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performRotation(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), "180"));
                                generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                            }

                        } else if(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("angle") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("rotate 90")){

                            if(shapeOfFrameDObject.equals("circle")) {
                                RavensAttribute attribute = new RavensAttribute("angle", generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getValue());
                                generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                            } else {
                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performRotation(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), "90"));
                                generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                            }

                        } else if(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("angle") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("reflect left")) {

                            if(shapeOfFrameDObject.equals("half-arrow")) {

                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performReflection(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), shapeOfFrameDObject, "left"));
                                generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                                //since it was a reflection we are gonna set the flip to true
                                for(int e=0; e<generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().size(); e++) {

                                    if(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(e).getName().equals("vertical-flip")){
                                        RavensAttribute attributeVerticalFlip = new RavensAttribute("vertical-flip", "yes");
                                        generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().set(e, attributeVerticalFlip);
                                    }
                                }

                            } else {

                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performReflection(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), shapeOfFrameDObject, "left"));
                                generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                            }


                        } else if(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("angle") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("reflect right")) {

                            if(shapeOfFrameDObject.equals("half-arrow")) {

                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performReflection(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), shapeOfFrameDObject, "right"));
                                generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                            } else {

                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performReflection(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), shapeOfFrameDObject, "right"));
                                generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                            }


                        } else if(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("fill") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("yes fill")) {

                            RavensAttribute attribute = new RavensAttribute("fill", "yes");
                            generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        } else if(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("fill") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("no fill")) {

                            RavensAttribute attribute = new RavensAttribute("fill", "no");
                            generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        } else if(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("fill") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("left-half fill")) {

                            RavensAttribute attribute = new RavensAttribute("fill", "left-half");
                            generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        } else if(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("fill") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("right-half fill")) {

                            RavensAttribute attribute = new RavensAttribute("fill", "right-half");
                            generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        }  else if(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("fill") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("top-bottom-left fill")) {

                            String toAddFirst = "top-left,bottom-left";
                            String toAddSecond = "top-left";
                            String toAddThird = "bottom-left";

                            if(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getValue().contains(toAddFirst)) {

                                //Contains both so don't add anything

                            } else if(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getValue().contains(toAddSecond)) {

                                //Contains top left so add bottom left
                                String bottomLeft = ",bottom-left";
                                RavensAttribute attribute = new RavensAttribute("fill", generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getValue() + bottomLeft);
                                generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                            } else {
                                //Contains bottom left so add top left
                                String topLeft = ",top-left";
                                RavensAttribute attribute = new RavensAttribute("fill", generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getValue() + topLeft);
                                generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                            }


                        } else if(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("fill") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("top-bottom-right fill")) {

                            String checkOne = "top-right,bottom-right";
                            String checkTwo = "top-right";
                            String checkThree = "bottom-right";
                            String checkFour = "bottom-left,bottom-right";


                            if(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getValue().equals(checkOne)) {

                                //Contains both so don't add anything

                            } else if(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getValue().equals(checkTwo)) {

                                //Contains top right so add bottom right
                                String bottomRight = ",bottom-right";
                                RavensAttribute attribute = new RavensAttribute("fill", generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getValue() + bottomRight);
                                generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                            } else if(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getValue().equals(checkThree)) {

                                //Contains bottom right so add top right
                                String topRight = "top-right,";
                                RavensAttribute attribute = new RavensAttribute("fill", topRight + generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getValue());
                                generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                            } else if(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getValue().equals(checkFour)) {

                                //Contains bottom right and bottom left so add top right
                                String topRight = "top-right,";
                                RavensAttribute attribute = new RavensAttribute("fill", topRight + generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getValue());
                                generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                            } else {
                            }

                        } else if(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("size") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("no large")) {

                            RavensAttribute attribute = new RavensAttribute("size", "small");
                            generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        } else if(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("size") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("yes large")) {

                            RavensAttribute attribute = new RavensAttribute("size", "large");
                            generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        } else if(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("shape") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("change to triangle")) {

                            RavensAttribute attribute = new RavensAttribute("shape", "triangle");
                            generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        } else if(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("shape") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("change to square")) {

                            RavensAttribute attribute = new RavensAttribute("shape", "square");
                            generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        } else if(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("shape") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("change to circle")) {

                            RavensAttribute attribute = new RavensAttribute("shape", "circle");
                            generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        } else if(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("size") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("added left-of")) {

                            //TODO - HARCODING - NEEDS REVISION
                            RavensAttribute attribute = new RavensAttribute("left-of", "X");
                            generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().add(j, attribute);

                        } else {

                        }

                    }


                    //WE ARE ASSUMING WE ARE SWITCHING TO "ABOVE" TO THE CORRECT "INSIDE" HOWEVER THIS MAY NOT BE THE CASE
                    for(int k=0; k<semanticNetwork.getACTransformations().get(i).getAboveObjects().size(); k++) {

                        if(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("inside")){

                            generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().remove(j);

                            RavensAttribute attribute = new RavensAttribute("above", semanticNetwork.getACTransformations().get(i).getAboveObjects().get(k).getName());
                            generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().add(j, attribute);


                        }
//                    else {
//                        //If we can't find inside it means it must be new so added
//                        RavensAttribute attribute = new RavensAttribute("above", semanticNetwork.getACTransformations().get(i).getAboveObjects().get(k).getName());
//                        generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().add(j, attribute);
//                    }
                    }

                    //WE ARE ASSUMING WE ARE SWITCHING TO "ABOVE" TO THE CORRECT "INSIDE" HOWEVER THIS MAY NOT BE THE CASE
                    for(int k=0; k<semanticNetwork.getACTransformations().get(i).getLeftOfObjects().size(); k++) {

                        boolean isThere = false;
                        //We will only add if not present
                        for(int u=0; u<generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().size(); u++) {

                            if(generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().get(u).getName().equals("left-of")){
                                isThere = true;
                            }
                        }

                        if(!isThere){
                            RavensAttribute attribute = new RavensAttribute("left-of", semanticNetwork.getACTransformations().get(i).getLeftOfObjects().get(k).getName());
                            generatedFrameDFromB.getObjects().get(transformationIndex).getAttributes().add(j, attribute);

                        }

                    }
                }

            }



        }
    }

    public void performTransformationFromC(){

        //TRANSFORMATION FROM C TO D FIRST
        for(int i=0; i<semanticNetwork.getFrameC().getObjects().size(); i++) {

            RavensObject object = new RavensObject(semanticNetwork.getFrameC().getObjects().get(i).getName());

            for(int j=0; j<semanticNetwork.getFrameC().getObjects().get(i).getAttributes().size(); j++) {

                RavensAttribute attribute = new RavensAttribute(semanticNetwork.getFrameC().getObjects().get(i).getAttributes().get(j).getName(), semanticNetwork.getFrameC().getObjects().get(i).getAttributes().get(j).getValue());

                object.getAttributes().add(attribute);
            }

            generatedFrameDFromC.getObjects().add(object);

        }


        //Removing objects in the removal list
        if(semanticNetwork.getABRemovals().size() > 0) {

            for(int i=0; i<semanticNetwork.getABRemovals().size(); i++) {

                int index = Utility.objectIndexToRemove(semanticNetwork.getABRemovals().get(i), generatedFrameDFromC);
                generatedFrameDFromC.getObjects().remove(index);
            }

        }

        //Adding objects in the adding list
        if(semanticNetwork.getABAdditions().size() > 0) {

            for(int i=0; i<semanticNetwork.getABAdditions().size(); i++) {

                generatedFrameDFromC.getObjects().add(semanticNetwork.getABAdditions().get(i));
            }

        }

        //SPECIAL CASE - ONLY ROTATING - BASIC PROBLEM 15
        boolean onlyRotation90 = false;
        boolean onlyRotation180 = false;
        boolean onlyRotation270 = false;

        for(int i=0; i<semanticNetwork.getABTransformations().size(); i++) {

            for (int y = 0; y < semanticNetwork.getABTransformations().get(i).getTransformations().size(); y++) {

                if(semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("rotate 90")){
                    onlyRotation90 = true;
                    onlyRotation180 = false;
                    onlyRotation270 = false;
                } else if(semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("rotate 180")){
                    onlyRotation90 = false;
                    onlyRotation180 = true;
                    onlyRotation270 = false;
                } else if(semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("rotate 270")){
                    onlyRotation90 = false;
                    onlyRotation180 = false;
                    onlyRotation270 = true;
                }else {
                    onlyRotation90 = false;
                    onlyRotation180 = false;
                    onlyRotation270 = false;
                }
            }
        }

        if(onlyRotation90 || onlyRotation180 || onlyRotation270) {

            for(int i=0; i<generatedFrameDFromC.getObjects().size(); i++) {

                for(int j=0; j<generatedFrameDFromC.getObjects().get(i).getAttributes().size(); j++) {

                    if(generatedFrameDFromC.getObjects().get(i).getAttributes().get(j).getName().equals("angle")){

                        if(onlyRotation90) {
                            RavensAttribute attribute = new RavensAttribute("angle","90");
                            generatedFrameDFromC.getObjects().get(i).getAttributes().set(j, attribute);
                        } else if(onlyRotation180) {
                            RavensAttribute attribute = new RavensAttribute("angle","180");
                            generatedFrameDFromC.getObjects().get(i).getAttributes().set(j, attribute);
                        } else if(onlyRotation270) {
                            RavensAttribute attribute = new RavensAttribute("angle","270");
                            generatedFrameDFromC.getObjects().get(i).getAttributes().set(j, attribute);
                        } else {

                        }

                    }

                }

            }

        } else {

            //Apply transformation to correct object in FrameD (copy of C)
            for(int i=0; i<semanticNetwork.getABTransformations().size(); i++) {

                int transformationIndex = findBestCorrespondenceForTransformationsFrame(semanticNetwork.getFrameC(), semanticNetwork.getABTransformations().get(i));
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
                                semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("rotate 90")){

                            if(shapeOfFrameDObject.equals("circle")) {
                                RavensAttribute attribute = new RavensAttribute("angle", generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getValue());
                                generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                            } else {
                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performRotation(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), "90"));
                                generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                            }

                        } else if(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("angle") &&
                                semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("reflect left")) {

                            if(shapeOfFrameDObject.equals("half-arrow")) {

                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performReflection(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), shapeOfFrameDObject, "left"));
                                generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                                //since it was a reflection we are gonna set the flip to true
                                for(int e=0; e<generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().size(); e++) {

                                    if(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(e).getName().equals("vertical-flip")){
                                        RavensAttribute attributeVerticalFlip = new RavensAttribute("vertical-flip", "yes");
                                        generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().set(e, attributeVerticalFlip);
                                    }
                                }

                            } else {

                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performReflection(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), shapeOfFrameDObject, "left"));
                                generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                            }


                        } else if(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("angle") &&
                                semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("reflect right")) {

                            if(shapeOfFrameDObject.equals("half-arrow")) {

                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performReflection(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), shapeOfFrameDObject, "right"));
                                generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                            } else {

                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performReflection(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), shapeOfFrameDObject, "right"));
                                generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                            }


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

                        } else if(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("shape") &&
                                semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("change to triangle")) {

                            RavensAttribute attribute = new RavensAttribute("shape", "triangle");
                            generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        } else if(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("shape") &&
                                semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("change to square")) {

                            RavensAttribute attribute = new RavensAttribute("shape", "square");
                            generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        } else if(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("shape") &&
                                semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("change to circle")) {

                            RavensAttribute attribute = new RavensAttribute("shape", "circle");
                            generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        } else if(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("size") &&
                                semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("added left-of")) {

                            //TODO - HARCODING - NEEDS REVISION
                            RavensAttribute attribute = new RavensAttribute("left-of", "X");
                            generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().add(j, attribute);

                        } else {

                        }

                    }


                    //WE ARE ASSUMING WE ARE SWITCHING TO "ABOVE" TO THE CORRECT "INSIDE" HOWEVER THIS MAY NOT BE THE CASE
                    for(int k=0; k<semanticNetwork.getABTransformations().get(i).getAboveObjects().size(); k++) {

                        if(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("inside")){

                            generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().remove(j);

                            RavensAttribute attribute = new RavensAttribute("above", semanticNetwork.getABTransformations().get(i).getAboveObjects().get(k).getName());
                            generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().add(j, attribute);


                        }
//                    else {
//                        //If we can't find inside it means it must be new so added
//                        RavensAttribute attribute = new RavensAttribute("above", semanticNetwork.getABTransformations().get(i).getAboveObjects().get(k).getName());
//                        generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().add(j, attribute);
//                    }
                    }

                    //WE ARE ASSUMING WE ARE SWITCHING TO "ABOVE" TO THE CORRECT "INSIDE" HOWEVER THIS MAY NOT BE THE CASE
                    for(int k=0; k<semanticNetwork.getABTransformations().get(i).getLeftOfObjects().size(); k++) {

                        boolean isThere = false;
                        //We will only add if not present
                        for(int u=0; u<generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().size(); u++) {

                            if(generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().get(u).getName().equals("left-of")){
                                isThere = true;
                            }
                        }

                        if(!isThere){
                            RavensAttribute attribute = new RavensAttribute("left-of", semanticNetwork.getABTransformations().get(i).getLeftOfObjects().get(k).getName());
                            generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().add(j, attribute);

                        }

                    }
                }


            }
        }
    }

    public void performTransformationFromH(){

        //Checking if there no transformation horizontally which will also mean vertically
        if( semanticNetwork.getABTransformations().size() == 0 &&
            semanticNetwork.getBCTransformations().size() == 0 &&
            semanticNetwork.getDETransformations().size() == 0 &&
            semanticNetwork.getEFTransformations().size() == 0 &&
            semanticNetwork.getGHTransformations().size() == 0){

//            generatedFrameIFromH.getObjects().add(object);
            for(int i=0; i<semanticNetwork.getFrameH().getObjects().size(); i++) {

                RavensObject object = new RavensObject("Z");
                generatedFrameIFromH.getObjects().add(object);

                for(int j=0; j<semanticNetwork.getFrameH().getObjects().get(0).getAttributes().size(); j++) {

                    RavensAttribute attribute = new RavensAttribute(semanticNetwork.getFrameH().getObjects().get(0).getAttributes().get(j).getName(), semanticNetwork.getFrameH().getObjects().get(0).getAttributes().get(j).getValue());
                    generatedFrameIFromH.getObjects().get(0).getAttributes().add(j, attribute);

                }
            }

        } else if(semanticNetwork.getABTransformations().size() > 0 ||
                semanticNetwork.getBCTransformations().size() > 0 ||
                semanticNetwork.getDETransformations().size() > 0 ||
                semanticNetwork.getEFTransformations().size() > 0 ||
                semanticNetwork.getGHTransformations().size() > 0) {

            String shapeOfFrameH = semanticNetwork.getFrameH().getObjects().get(0).getAttributes().get(0).getValue();

            ArrayList<String> typeOfTransformation = new ArrayList<>();

            if(semanticNetwork.getABTransformations().get(0).getTransformations().get(0).equals("change to plus to square") ||
                semanticNetwork.getBCTransformations().get(0).getTransformations().get(0).equals("change to plus to square") ||
                semanticNetwork.getDETransformations().get(0).getTransformations().get(0).equals("change to plus to square") ||
                semanticNetwork.getEFTransformations().get(0).getTransformations().get(0).equals("change to plus to square") ||
                semanticNetwork.getGHTransformations().get(0).getTransformations().get(0).equals("change to plus to square") ) {

                RavensObject object = new RavensObject("Z");
                generatedFrameIFromH.getObjects().add(object);

                RavensAttribute squareAttribute = new RavensAttribute("shape", "square");
                generatedFrameIFromH.getObjects().get(0).getAttributes().add(0, squareAttribute);

                RavensAttribute fillAttribute = new RavensAttribute("fill", "yes");
                generatedFrameIFromH.getObjects().get(0).getAttributes().add(1, fillAttribute);

            } else if(semanticNetwork.getABTransformations().get(0).getTransformations().get(0).equals("change to plus to circle") ||
                    semanticNetwork.getBCTransformations().get(0).getTransformations().get(0).equals("change to plus to circle") ||
                    semanticNetwork.getDETransformations().get(0).getTransformations().get(0).equals("change to plus to circle") ||
                    semanticNetwork.getEFTransformations().get(0).getTransformations().get(0).equals("change to plus to circle") ||
                    semanticNetwork.getGHTransformations().get(0).getTransformations().get(0).equals("change to plus to circle")) {

                RavensObject object = new RavensObject("Z");
                generatedFrameIFromH.getObjects().add(object);

                RavensAttribute squareAttribute = new RavensAttribute("shape", "circle");
                generatedFrameIFromH.getObjects().get(0).getAttributes().add(0, squareAttribute);

                RavensAttribute fillAttribute = new RavensAttribute("fill", "yes");
                generatedFrameIFromH.getObjects().get(0).getAttributes().add(1, fillAttribute);

            } else {

            }


        } else {
            //GENERATE A GENERAL SOLUTION
        }
    }

    /**
     * Performing transformations From B
     */
    public void performTransformationFromBThenCFirst() {

        //TRANSFORMATION FROM B TO D FIRST
        for(int i=0; i<semanticNetwork.getFrameA().getObjects().size(); i++) {

            RavensObject object = new RavensObject(semanticNetwork.getFrameA().getObjects().get(i).getName());

            for(int j=0; j<semanticNetwork.getFrameA().getObjects().get(i).getAttributes().size(); j++) {

                RavensAttribute attribute = new RavensAttribute(semanticNetwork.getFrameA().getObjects().get(i).getAttributes().get(j).getName(), semanticNetwork.getFrameA().getObjects().get(i).getAttributes().get(j).getValue());

                object.getAttributes().add(attribute);
            }

            generatedFrameDFromBC.getObjects().add(object);

        }

        //Removing objects in the removal list
        if(semanticNetwork.getABRemovals().size() > 0) {

            for(int i=0; i<semanticNetwork.getABRemovals().size(); i++) {

                int index = Utility.objectIndexToRemove(semanticNetwork.getABRemovals().get(i), generatedFrameDFromBC);
                generatedFrameDFromBC.getObjects().remove(index);
            }

        }

        //Adding objects in the adding list
        if(semanticNetwork.getABAdditions().size() > 0) {

            for(int i=0; i<semanticNetwork.getABAdditions().size(); i++) {

                generatedFrameDFromBC.getObjects().add(semanticNetwork.getABAdditions().get(i));
            }

        }

        //SPECIAL CASE - ONLY ROTATING - BASIC PROBLEM 15
        boolean onlyRotation90 = false;
        boolean onlyRotation180 = false;
        boolean onlyRotation270 = false;

        for(int i=0; i<semanticNetwork.getABTransformations().size(); i++) {

            for (int y = 0; y < semanticNetwork.getABTransformations().get(i).getTransformations().size(); y++) {

                if(semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("rotate 90")){
                    onlyRotation90 = true;
                    onlyRotation180 = false;
                    onlyRotation270 = false;
                } else if(semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("rotate 180")){
                    onlyRotation90 = false;
                    onlyRotation180 = true;
                    onlyRotation270 = false;
                } else if(semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("rotate 270")){
                    onlyRotation90 = false;
                    onlyRotation180 = false;
                    onlyRotation270 = true;
                }else {
                    onlyRotation90 = false;
                    onlyRotation180 = false;
                    onlyRotation270 = false;
                }
            }
        }

        //Special Case - ONLY MULTIPLE - 2X2 BASIC PROBLEM 13 AND 16
        boolean onlyCircle = false;
        if(semanticNetwork.isCircleOnlyUnclearRotationAB()){
            onlyCircle = true;
        }

        if(onlyRotation90 || onlyRotation180 || onlyRotation270) {

            for(int i=0; i<generatedFrameDFromBC.getObjects().size(); i++) {

                for(int j=0; j<generatedFrameDFromBC.getObjects().get(i).getAttributes().size(); j++) {

                    if(generatedFrameDFromBC.getObjects().get(i).getAttributes().get(j).getName().equals("angle")){

                        if(onlyRotation90) {
                            RavensAttribute attribute = new RavensAttribute("angle","90");
                            generatedFrameDFromBC.getObjects().get(i).getAttributes().set(j, attribute);
                        } else if(onlyRotation180) {
                            RavensAttribute attribute = new RavensAttribute("angle","180");
                            generatedFrameDFromBC.getObjects().get(i).getAttributes().set(j, attribute);
                        } else if(onlyRotation270) {
                            RavensAttribute attribute = new RavensAttribute("angle","270");
                            generatedFrameDFromBC.getObjects().get(i).getAttributes().set(j, attribute);
                        } else {

                        }

                    }

                }

            }

        } else if(onlyCircle) {

//            RavensObject object = generatedFrameDFromBC.getObjects().get(0);
//            RavensObject object2 = generatedFrameDFromBC.getObjects().get(0);
//            RavensObject object3 = generatedFrameDFromBC.getObjects().get(0);
//            RavensObject object4 = generatedFrameDFromBC.getObjects().get(0);
//            RavensObject object5 = generatedFrameDFromBC.getObjects().get(0);
//
//            int angleIndex = 0;
//
//            for(int i=0; i<object.getAttributes().size(); i++) {
//                if(object.getAttributes().get(i).getName().equals("angle")){
//                    angleIndex = i;
//                }
//            }
//
//            RavensAttribute attributeAngle0 = new RavensAttribute("angle", "0");
//            object.getAttributes().set(angleIndex, attributeAngle0);
//            generatedFrameDFromBC.getObjects().add(object);
//
//            RavensAttribute attributeAngle45 = new RavensAttribute("angle", "45");
//            object2.getAttributes().set(angleIndex, attributeAngle45);
//            generatedFrameDFromBC.getObjects().add(object2);
//
//            RavensAttribute attributeAngle90 = new RavensAttribute("angle", "90");
//            object3.getAttributes().set(angleIndex, attributeAngle90);
//            generatedFrameDFromBC.getObjects().add(object3);
//
//            RavensAttribute attributeAngle135 = new RavensAttribute("angle", "135");
//            object4.getAttributes().set(angleIndex, attributeAngle135);
//            generatedFrameDFromBC.getObjects().add(object4);
//
//            RavensAttribute attributeAngle80 = new RavensAttribute("angle", "180");
//            object5.getAttributes().set(angleIndex, attributeAngle80);
//            generatedFrameDFromBC.getObjects().add(object5);

            uncertainRotation = true;

        } else {

            //Apply transformation to correct object in FrameD (copy of C)
            for(int i=0; i<semanticNetwork.getABTransformations().size(); i++) {

                int transformationIndex = findBestCorrespondenceForTransformationsFrame(semanticNetwork.getFrameB(), semanticNetwork.getABTransformations().get(i));
                String shapeOfFrameDObject = generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(0).getValue();

                for(int j=0; j<generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().size(); j++) {

                    for(int y=0; y<semanticNetwork.getABTransformations().get(i).getTransformations().size(); y++){

                        if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("angle") &&
                                semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("rotate -45")){

                            if(shapeOfFrameDObject.equals("circle")) {
                                RavensAttribute attribute = new RavensAttribute("angle", generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue());
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                            } else {
                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performRotation(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), "-45"));
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                            }

                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("angle") &&
                                semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("rotate 45")){

                            if(shapeOfFrameDObject.equals("circle")) {
                                RavensAttribute attribute = new RavensAttribute("angle", generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue());
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                            } else {
                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performRotation(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), "45"));
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                            }

                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("angle") &&
                                semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("rotate 180")){

                            if(shapeOfFrameDObject.equals("circle")) {
                                RavensAttribute attribute = new RavensAttribute("angle", generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue());
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                            } else {
                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performRotation(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), "180"));
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                            }

                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("angle") &&
                                semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("rotate 90")){

                            if(shapeOfFrameDObject.equals("circle")) {
                                RavensAttribute attribute = new RavensAttribute("angle", generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue());
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                            } else {
                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performRotation(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), "90"));
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                            }

                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("angle") &&
                                semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("reflect left")) {

                            if(shapeOfFrameDObject.equals("half-arrow")) {

                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performReflection(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), shapeOfFrameDObject, "left"));
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                                //since it was a reflection we are gonna set the flip to true
                                for(int e=0; e<generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().size(); e++) {

                                    if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(e).getName().equals("vertical-flip")){
                                        RavensAttribute attributeVerticalFlip = new RavensAttribute("vertical-flip", "yes");
                                        generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(e, attributeVerticalFlip);
                                    }
                                }

                            } else {

                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performReflection(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), shapeOfFrameDObject, "left"));
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                            }


                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("angle") &&
                                semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("reflect right")) {

                            if(shapeOfFrameDObject.equals("half-arrow")) {

                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performReflection(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), shapeOfFrameDObject, "right"));
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                            } else {

                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performReflection(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), shapeOfFrameDObject, "right"));
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                            }


                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("angle") &&
                                semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("reflect up")) {

                            if(shapeOfFrameDObject.equals("half-arrow")) {

                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performReflection(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), shapeOfFrameDObject, "up"));
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                            } else {

                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performReflection(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), shapeOfFrameDObject, "up"));
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                            }


                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("angle") &&
                                semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("reflect down")) {

                            if(shapeOfFrameDObject.equals("half-arrow")) {

                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performReflection(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), shapeOfFrameDObject, "down"));
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                            } else {

                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performReflection(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), shapeOfFrameDObject, "down"));
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                            }


                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("fill") &&
                                semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("yes fill")) {

                            RavensAttribute attribute = new RavensAttribute("fill", "yes");
                            generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("fill") &&
                                semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("no fill")) {

                            RavensAttribute attribute = new RavensAttribute("fill", "no");
                            generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("fill") &&
                                semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("left-half fill")) {

                            RavensAttribute attribute = new RavensAttribute("fill", "left-half");
                            generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("fill") &&
                                semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("right-half fill")) {

                            RavensAttribute attribute = new RavensAttribute("fill", "right-half");
                            generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        }  else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("fill") &&
                                semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("top-bottom-left fill")) {

                            String toAddFirst = "top-left,bottom-left";
                            String toAddSecond = "top-left";
                            String toAddThird = "bottom-left";

                            if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue().contains(toAddFirst)) {

                                //Contains both so don't add anything

                            } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue().contains(toAddSecond)) {

                                //Contains top left so add bottom left
                                String bottomLeft = ",bottom-left";
                                RavensAttribute attribute = new RavensAttribute("fill", generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue() + bottomLeft);
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                            } else {
                                //Contains bottom left so add top left
                                String topLeft = ",top-left";
                                RavensAttribute attribute = new RavensAttribute("fill", generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue() + topLeft);
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                            }


                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("fill") &&
                                semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("top-bottom-right fill")) {

                            String checkOne = "top-right,bottom-right";
                            String checkTwo = "top-right";
                            String checkThree = "bottom-right";
                            String checkFour = "bottom-left,bottom-right";


                            if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue().equals(checkOne)) {

                                //Contains both so don't add anything

                            } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue().equals(checkTwo)) {

                                //Contains top right so add bottom right
                                String bottomRight = ",bottom-right";
                                RavensAttribute attribute = new RavensAttribute("fill", generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue() + bottomRight);
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                            } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue().equals(checkThree)) {

                                //Contains bottom right so add top right
                                String topRight = "top-right,";
                                RavensAttribute attribute = new RavensAttribute("fill", topRight + generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue());
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                            } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue().equals(checkFour)) {

                                //Contains bottom right and bottom left so add top right
                                String topRight = "top-right,";
                                RavensAttribute attribute = new RavensAttribute("fill", topRight + generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue());
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                            } else {
                            }

                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("size") &&
                                semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("no large")) {

                            RavensAttribute attribute = new RavensAttribute("size", "small");
                            generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("size") &&
                                semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("yes large")) {

                            RavensAttribute attribute = new RavensAttribute("size", "large");
                            generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("shape") &&
                                semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("change to triangle")) {

                            RavensAttribute attribute = new RavensAttribute("shape", "triangle");
                            generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("shape") &&
                                semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("change to square")) {

                            RavensAttribute attribute = new RavensAttribute("shape", "square");
                            generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("shape") &&
                                semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("change to circle")) {

                            RavensAttribute attribute = new RavensAttribute("shape", "circle");
                            generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("size") &&
                                semanticNetwork.getABTransformations().get(i).getTransformations().get(y).equals("added left-of")) {

                            //TODO - HARCODING - NEEDS REVISION
                            RavensAttribute attribute = new RavensAttribute("left-of", "X");
                            generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().add(j, attribute);

                        } else {

                        }

                    }


                    //WE ARE ASSUMING WE ARE SWITCHING TO "ABOVE" TO THE CORRECT "INSIDE" HOWEVER THIS MAY NOT BE THE CASE
                    for(int k=0; k<semanticNetwork.getABTransformations().get(i).getAboveObjects().size(); k++) {

                        if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("inside")){

                            generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().remove(j);

                            RavensAttribute attribute = new RavensAttribute("above", semanticNetwork.getABTransformations().get(i).getAboveObjects().get(k).getName());
                            generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().add(j, attribute);


                        }
//                    else {
//                        //If we can't find inside it means it must be new so added
//                        RavensAttribute attribute = new RavensAttribute("above", semanticNetwork.getABTransformations().get(i).getAboveObjects().get(k).getName());
//                        generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().add(j, attribute);
//                    }
                    }

                    //WE ARE ASSUMING WE ARE SWITCHING TO "ABOVE" TO THE CORRECT "INSIDE" HOWEVER THIS MAY NOT BE THE CASE
                    for(int k=0; k<semanticNetwork.getABTransformations().get(i).getLeftOfObjects().size(); k++) {

                        boolean isThere = false;
                        //We will only add if not present
                        for(int u=0; u<generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().size(); u++) {

                            if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(u).getName().equals("left-of")){
                                isThere = true;
                            }
                        }

                        if(!isThere){
                            RavensAttribute attribute = new RavensAttribute("left-of", semanticNetwork.getABTransformations().get(i).getLeftOfObjects().get(k).getName());
                            generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().add(j, attribute);

                        }

                    }
                }


            }
        }
    }

    /**
     * Performing transformations From C after already doing from B
     */
    public void performTransformationFromBThenCSecond() {

        //Removing objects in the removal list
        if(semanticNetwork.getACRemovals().size() > 0) {

            for(int i=0; i<semanticNetwork.getACRemovals().size(); i++) {

                int index = Utility.objectIndexToRemove(semanticNetwork.getACRemovals().get(i), generatedFrameDFromBC);
                generatedFrameDFromBC.getObjects().remove(index);
            }

        }

        //Adding objects in the adding list
        if(semanticNetwork.getACAdditions().size() > 0) {

            for(int i=0; i<semanticNetwork.getACAdditions().size(); i++) {

                generatedFrameDFromBC.getObjects().add(semanticNetwork.getACAdditions().get(i));
            }

        }

        //SPECIAL CASE - ONLY ROTATING - BASIC PROBLEM 15
        boolean onlyRotation90 = false;
        boolean onlyRotation180 = false;
        boolean onlyRotation270 = false;

        for(int i=0; i<semanticNetwork.getACTransformations().size(); i++) {

            for (int y = 0; y < semanticNetwork.getACTransformations().get(i).getTransformations().size(); y++) {

                if(semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("rotate 90")){
                    onlyRotation90 = true;
                    onlyRotation180 = false;
                    onlyRotation270 = false;
                } else if(semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("rotate 180")){
                    onlyRotation90 = false;
                    onlyRotation180 = true;
                    onlyRotation270 = false;
                } else if(semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("rotate 270")){
                    onlyRotation90 = false;
                    onlyRotation180 = false;
                    onlyRotation270 = true;
                }else {
                    onlyRotation90 = false;
                    onlyRotation180 = false;
                    onlyRotation270 = false;
                }
            }
        }

        //Special Case - ONLY MULTIPLE - 2X2 BASIC PROBLEM 13 AND 16
        boolean onlyMultiple = false;
        if(semanticNetwork.MultipleTransformationAC > 0){
            onlyMultiple = true;
        }

        //Special Case - ONLY MULTIPLE - 2X2 BASIC PROBLEM 12
        boolean onlyCircle = false;
        if(semanticNetwork.isCircleOnlyUnclearRotationAC()){
            onlyCircle = true;
        }


        if(onlyRotation90 || onlyRotation180 || onlyRotation270) {

            for(int i=0; i<generatedFrameDFromBC.getObjects().size(); i++) {

                for(int j=0; j<generatedFrameDFromBC.getObjects().get(i).getAttributes().size(); j++) {

                    if(generatedFrameDFromBC.getObjects().get(i).getAttributes().get(j).getName().equals("angle")){

                        if(onlyRotation90) {
                            RavensAttribute attribute = new RavensAttribute("angle","90");
                            generatedFrameDFromBC.getObjects().get(i).getAttributes().set(j, attribute);
                        } else if(onlyRotation180) {
                            RavensAttribute attribute = new RavensAttribute("angle","180");
                            generatedFrameDFromBC.getObjects().get(i).getAttributes().set(j, attribute);
                        } else if(onlyRotation270) {
                            RavensAttribute attribute = new RavensAttribute("angle","270");
                            generatedFrameDFromBC.getObjects().get(i).getAttributes().set(j, attribute);
                        } else {

                        }

                    }

                }

            }

        } else if(onlyMultiple) {

            RavensObject object = generatedFrameDFromBC.getObjects().get(0);

            for(int i=1; i<semanticNetwork.MultipleTransformationAC; i++) {
                generatedFrameDFromBC.getObjects().add(object);
            }

        } else if(onlyCircle) {

//            RavensObject object = generatedFrameDFromBC.getObjects().get(0);
//            RavensObject object2 = generatedFrameDFromBC.getObjects().get(0);
//            RavensObject object3 = generatedFrameDFromBC.getObjects().get(0);
//            RavensObject object4 = generatedFrameDFromBC.getObjects().get(0);
//            RavensObject object5 = generatedFrameDFromBC.getObjects().get(0);
//
//            int angleIndex = 0;
//
//            for(int i=0; i<object.getAttributes().size(); i++) {
//                if(object.getAttributes().get(i).getName().equals("angle")){
//                    angleIndex = i;
//                }
//            }

//            RavensAttribute attributeAngle0 = new RavensAttribute("angle", "0");
//            object.getAttributes().set(angleIndex, attributeAngle0);
//            generatedFrameDFromBC.getObjects().add(object);
//
//            RavensAttribute attributeAngle45 = new RavensAttribute("angle", "45");
//            object2.getAttributes().set(angleIndex, attributeAngle45);
//            generatedFrameDFromBC.getObjects().add(object2);
//
//            RavensAttribute attributeAngle90 = new RavensAttribute("angle", "90");
//            object3.getAttributes().set(angleIndex, attributeAngle90);
//            generatedFrameDFromBC.getObjects().add(object3);
//
//            RavensAttribute attributeAngle135 = new RavensAttribute("angle", "135");
//            object4.getAttributes().set(angleIndex, attributeAngle135);
//            generatedFrameDFromBC.getObjects().add(object4);
//
//            RavensAttribute attributeAngle80 = new RavensAttribute("angle", "180");
//            object5.getAttributes().set(angleIndex, attributeAngle80);
//            generatedFrameDFromBC.getObjects().add(object5);

            uncertainRotation = true;

        } else {

            //Apply transformation to correct object in FrameD (copy of C)
            for(int i=0; i<semanticNetwork.getACTransformations().size(); i++) {

                int transformationIndex = findBestCorrespondenceForTransformationsFrame(semanticNetwork.getFrameC(), semanticNetwork.getACTransformations().get(i));
                String shapeOfFrameDObject = generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(0).getValue();

                for(int j=0; j<generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().size(); j++) {

                    for(int y=0; y<semanticNetwork.getACTransformations().get(i).getTransformations().size(); y++){

                        if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("angle") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("rotate -45")){

                            if(shapeOfFrameDObject.equals("circle")) {
                                RavensAttribute attribute = new RavensAttribute("angle", generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue());
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                            } else {
                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performRotation(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), "-45"));
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                            }

                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("angle") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("rotate 45")){

                            if(shapeOfFrameDObject.equals("circle")) {
                                RavensAttribute attribute = new RavensAttribute("angle", generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue());
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                            } else {
                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performRotation(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), "45"));
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                            }

                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("angle") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("rotate 180")){

                            if(shapeOfFrameDObject.equals("circle")) {
                                RavensAttribute attribute = new RavensAttribute("angle", generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue());
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                            } else {
                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performRotation(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), "180"));
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                            }

                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("angle") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("rotate 90")){

                            if(shapeOfFrameDObject.equals("circle")) {
                                RavensAttribute attribute = new RavensAttribute("angle", generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue());
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                            } else {
                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performRotation(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), "90"));
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                            }

                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("angle") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("reflect left")) {

                            if(shapeOfFrameDObject.equals("half-arrow")) {

                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performReflection(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), shapeOfFrameDObject, "left"));
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                                //since it was a reflection we are gonna set the flip to true
                                for(int e=0; e<generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().size(); e++) {

                                    if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(e).getName().equals("vertical-flip")){
                                        RavensAttribute attributeVerticalFlip = new RavensAttribute("vertical-flip", "yes");
                                        generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(e, attributeVerticalFlip);
                                    }
                                }

                            } else {

                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performReflection(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), shapeOfFrameDObject, "left"));
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                            }


                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("angle") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("reflect right")) {

                            if(shapeOfFrameDObject.equals("half-arrow")) {

                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performReflection(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), shapeOfFrameDObject, "right"));
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                            } else {

                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performReflection(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), shapeOfFrameDObject, "right"));
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                            }


                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("angle") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("reflect up")) {

                            if(shapeOfFrameDObject.equals("half-arrow")) {

                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performReflection(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), shapeOfFrameDObject, "up"));
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                            } else {

                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performReflection(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), shapeOfFrameDObject, "up"));
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                            }


                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("angle") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("reflect down")) {

                            if(shapeOfFrameDObject.equals("half-arrow")) {

                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performReflection(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), shapeOfFrameDObject, "down"));
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                            } else {

                                RavensAttribute attribute = new RavensAttribute("angle", Utility.performReflection(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue(), shapeOfFrameDObject, "down"));
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                            }


                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("fill") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("yes fill")) {

                            RavensAttribute attribute = new RavensAttribute("fill", "yes");
                            generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("fill") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("no fill")) {

                            RavensAttribute attribute = new RavensAttribute("fill", "no");
                            generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("fill") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("left-half fill")) {

                            RavensAttribute attribute = new RavensAttribute("fill", "left-half");
                            generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("fill") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("right-half fill")) {

                            RavensAttribute attribute = new RavensAttribute("fill", "right-half");
                            generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        }  else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("fill") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("top-bottom-left fill")) {

                            String toAddFirst = "top-left,bottom-left";
                            String toAddSecond = "top-left";
                            String toAddThird = "bottom-left";

                            if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue().contains(toAddFirst)) {

                                //Contains both so don't add anything

                            } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue().contains(toAddSecond)) {

                                //Contains top left so add bottom left
                                String bottomLeft = ",bottom-left";
                                RavensAttribute attribute = new RavensAttribute("fill", generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue() + bottomLeft);
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                            } else {
                                //Contains bottom left so add top left
                                String topLeft = ",top-left";
                                RavensAttribute attribute = new RavensAttribute("fill", generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue() + topLeft);
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);
                            }


                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("fill") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("top-bottom-right fill")) {

                            String checkOne = "top-right,bottom-right";
                            String checkTwo = "top-right";
                            String checkThree = "bottom-right";
                            String checkFour = "bottom-left,bottom-right";


                            if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue().equals(checkOne)) {

                                //Contains both so don't add anything

                            } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue().equals(checkTwo)) {

                                //Contains top right so add bottom right
                                String bottomRight = ",bottom-right";
                                RavensAttribute attribute = new RavensAttribute("fill", generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue() + bottomRight);
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                            } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue().equals(checkThree)) {

                                //Contains bottom right so add top right
                                String topRight = "top-right,";
                                RavensAttribute attribute = new RavensAttribute("fill", topRight + generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue());
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                            } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue().equals(checkFour)) {

                                //Contains bottom right and bottom left so add top right
                                String topRight = "top-right,";
                                RavensAttribute attribute = new RavensAttribute("fill", topRight + generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getValue());
                                generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                            } else {
                            }

                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("size") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("no large")) {

                            RavensAttribute attribute = new RavensAttribute("size", "small");
                            generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("size") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("yes large")) {

                            RavensAttribute attribute = new RavensAttribute("size", "large");
                            generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("shape") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("change to triangle")) {

                            RavensAttribute attribute = new RavensAttribute("shape", "triangle");
                            generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("shape") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("change to square")) {

                            RavensAttribute attribute = new RavensAttribute("shape", "square");
                            generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("shape") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("change to circle")) {

                            RavensAttribute attribute = new RavensAttribute("shape", "circle");
                            generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().set(j, attribute);

                        } else if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("size") &&
                                semanticNetwork.getACTransformations().get(i).getTransformations().get(y).equals("added left-of")) {

                            //TODO - HARCODING - NEEDS REVISION
                            RavensAttribute attribute = new RavensAttribute("left-of", "X");
                            generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().add(j, attribute);

                        } else {

                        }

                    }


                    //WE ARE ASSUMING WE ARE SWITCHING TO "ABOVE" TO THE CORRECT "INSIDE" HOWEVER THIS MAY NOT BE THE CASE
                    for(int k=0; k<semanticNetwork.getACTransformations().get(i).getAboveObjects().size(); k++) {

                        if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(j).getName().equals("inside")){

                            generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().remove(j);

                            RavensAttribute attribute = new RavensAttribute("above", semanticNetwork.getACTransformations().get(i).getAboveObjects().get(k).getName());
                            generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().add(j, attribute);


                        }
//                    else {
//                        //If we can't find inside it means it must be new so added
//                        RavensAttribute attribute = new RavensAttribute("above", semanticNetwork.getACTransformations().get(i).getAboveObjects().get(k).getName());
//                        generatedFrameDFromC.getObjects().get(transformationIndex).getAttributes().add(j, attribute);
//                    }
                    }

                    //WE ARE ASSUMING WE ARE SWITCHING TO "ABOVE" TO THE CORRECT "INSIDE" HOWEVER THIS MAY NOT BE THE CASE
                    for(int k=0; k<semanticNetwork.getACTransformations().get(i).getLeftOfObjects().size(); k++) {

                        boolean isThere = false;
                        //We will only add if not present
                        for(int u=0; u<generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().size(); u++) {

                            if(generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().get(u).getName().equals("left-of")){
                                isThere = true;
                            }
                        }

                        if(!isThere){
                            RavensAttribute attribute = new RavensAttribute("left-of", semanticNetwork.getACTransformations().get(i).getLeftOfObjects().get(k).getName());
                            generatedFrameDFromBC.getObjects().get(transformationIndex).getAttributes().add(j, attribute);

                        }

                    }
                }

            }



        }
    }

    public int findBestCorrespondenceForTransformationsFrame(Frame frame, Transformation transformationTransformation) {

        int count = 0;
        int previousCount = 0;
        int transformationIndex = 0;

        for(int i=0; i<frame.getObjects().size(); i++) {

            count = 0;

            for(int m=0; m<frame.getObjects().get(i).getAttributes().size(); m++) {

                    for(int n=0; n< transformationTransformation.getFirstObject().getAttributes().size(); n++) {

                        if(transformationTransformation.getFirstObject().getAttributes().get(n).getName().equals(frame.getObjects().get(i).getAttributes().get(m).getName()) &&
                                transformationTransformation.getFirstObject().getAttributes().get(n).getValue().equals(frame.getObjects().get(i).getAttributes().get(m).getValue()) &&
                                (frame.getObjects().get(i).getAttributes().get(m).getName().equals("shape") ||
                                        frame.getObjects().get(i).getAttributes().get(m).getName().equals("size")  ||
                                        frame.getObjects().get(i).getAttributes().get(m).getName().equals("fill")  ||
                                        frame.getObjects().get(i).getAttributes().get(m).getName().equals("angle") ||
                                        frame.getObjects().get(i).getAttributes().get(m).getName().equals("above")   )) {

                            count = count + 5;

                        } else if(transformationTransformation.getFirstObject().getAttributes().get(n).getName().equals(frame.getObjects().get(i).getAttributes().get(m).getName()) &&
                                !transformationTransformation.getFirstObject().getAttributes().get(n).getValue().equals(frame.getObjects().get(i).getAttributes().get(m).getValue()) &&
                                frame.getObjects().get(i).getAttributes().get(m).getName().equals("overlaps")) {

                            count = count + 4;

                        }else if(transformationTransformation.getFirstObject().getAttributes().get(n).getName().equals(frame.getObjects().get(i).getAttributes().get(m).getName()) &&
                                !transformationTransformation.getFirstObject().getAttributes().get(n).getValue().equals(frame.getObjects().get(i).getAttributes().get(m).getValue()) &&
                                frame.getObjects().get(i).getAttributes().get(m).getName().equals("angle")) {

                            count = count + 3;

                        } else if(transformationTransformation.getFirstObject().getAttributes().get(n).getName().equals(frame.getObjects().get(i).getAttributes().get(m).getName()) &&
                                !transformationTransformation.getFirstObject().getAttributes().get(n).getValue().equals(frame.getObjects().get(i).getAttributes().get(m).getValue()) &&
                                frame.getObjects().get(i).getAttributes().get(m).getName().equals("size")) {

                            count = count + 2;

                        } else if(transformationTransformation.getFirstObject().getAttributes().get(n).getName().equals(frame.getObjects().get(i).getAttributes().get(m).getName()) &&
                                !transformationTransformation.getFirstObject().getAttributes().get(n).getValue().equals(frame.getObjects().get(i).getAttributes().get(m).getValue()) &&
                                frame.getObjects().get(i).getAttributes().get(m).getName().equals("fill")) {

                            count = count + 1;

                        } else if(transformationTransformation.getFirstObject().getAttributes().get(n).getName().equals(frame.getObjects().get(i).getAttributes().get(m).getName()) &&
                                !transformationTransformation.getFirstObject().getAttributes().get(n).getValue().equals(frame.getObjects().get(i).getAttributes().get(m).getValue()) &&
                                frame.getObjects().get(i).getAttributes().get(m).getName().equals("above")) {


                            count = count + 1;

                        } else {
                            count = count + 0;
                        }
                    }

            }

            int differentNumberOfAttributes = Math.abs(frame.getObjects().get(i).getAttributes().size()- transformationTransformation.getFirstObject().getAttributes().size());
            count = count - 3*differentNumberOfAttributes;

            if(count > previousCount) {
                transformationIndex = i;
                previousCount = count;
            }
        }

        return transformationIndex;
    }

    public SemanticNetwork getSemanticNetwork() {
        return semanticNetwork;
    }

    public void setSemanticNetwork(SemanticNetwork semanticNetwork) {
        this.semanticNetwork = semanticNetwork;
    }

    public RavensFigure getGeneratedFrameDFromB() {
        return generatedFrameDFromB;
    }

    public void setGeneratedFrameDFromB(RavensFigure generatedFrameDFromB) {
        this.generatedFrameDFromB = generatedFrameDFromB;
    }

    public RavensFigure getGeneratedFrameDFromC() {
        return generatedFrameDFromC;
    }

    public void setGeneratedFrameDFromC(RavensFigure generatedFrameDFromC) {
        this.generatedFrameDFromC = generatedFrameDFromC;
    }

    public RavensFigure getGeneratedFrameDFromBC() {
        return generatedFrameDFromBC;
    }

    public void setGeneratedFrameDFromBC(RavensFigure generatedFrameDFromBC) {
        this.generatedFrameDFromBC = generatedFrameDFromBC;
    }

    public boolean isUncertainRotation() {
        return uncertainRotation;
    }

    public void setUncertainRotation(boolean uncertainRotation) {
        this.uncertainRotation = uncertainRotation;
    }

}
