package project2;

import java.util.ArrayList;

public class Change {

    RavensObject firstObject = null;
    RavensObject secondObject = null;

    ArrayList<String> transformations = null;

    ArrayList<RavensObject> insideObjects = null;
    ArrayList<RavensObject> aboveObjects = null;

    FrameA frameA = null;
    FrameB frameB = null;
    FrameC frameC = null;

    public Change(RavensObject firstObject, RavensObject secondObject, FrameA frameA, FrameB frameB, FrameC frameC) {

        this.firstObject = firstObject;
        this.secondObject = secondObject;

        transformations = new ArrayList<String>();
        insideObjects = new ArrayList<RavensObject>();
        aboveObjects = new ArrayList<RavensObject>();

        this.frameA = frameA;
        this.frameB = frameB;
        this.frameC = frameC;

    }

    /* Set of transformations will include flip, fill, partial fill,
    *  move down, move up, expand, shrink, delete, and
    * rotate 180/135/90/45 degrees.*/
    public boolean checkDifferencesBetweenNodes() {

        boolean differencesExist = false;

        for(int i=0; i< firstObject.getAttributes().size(); i++) {

            for(int j=0; j< secondObject.getAttributes().size(); j++) {

                //TODO - NEED TO ONLY ADD THE DIFFERENCES; ELSE EVERYTHING IS THE SAME

                String difference = getDifference(firstObject.getAttributes().get(i).getName(), firstObject.getAttributes().get(i).getValue(), secondObject.getAttributes().get(j).getName(), secondObject.getAttributes().get(j).getValue());

                //Only add transformation if there is a difference
                if(!difference.equals("no match")) {

                    getTransformations().add(difference);
                    differencesExist = true;
                }

            }

        }

        return differencesExist;

    }

    public String getDifference(String firstNodeName, String firstNodeValue, String secondNodeName, String secondNodeValue) {

        String difference = "no match";

        //Check Fill
        if(firstNodeName.equals("fill") && secondNodeName.equals("fill")) {

            if(firstNodeValue.equals("no") && secondNodeValue.equals("yes")) {
                difference =  "yes fill";
            } else if(firstNodeValue.equals("yes") && secondNodeValue.equals("no")) {
                difference =  "no fill";
            } else if(firstNodeValue.equals("no") && secondNodeValue.equals("left-half")) {
                difference =  "left-half fill";
            } else if(firstNodeValue.equals("no") && secondNodeValue.equals("right-half")) {
                difference =  "right-half fill";
            } else if(firstNodeValue.equals("no") && secondNodeValue.equals("top-left,bottom-left")) {
                difference =  "top-bottom-left fill";
            } else if(firstNodeValue.equals("no") && secondNodeValue.equals("top-right,bottom-right")) {
                difference =  "top-bottom-right fill";
            } else if(firstNodeValue.equals("no") && secondNodeValue.equals("top-left,top-right")) {
                difference =  "top-top-both fill";
            } else if(firstNodeValue.equals("no") && secondNodeValue.equals("bottom-left,bottom-right")) {
                difference =  "bottom-bottom-both fill";
            } else {
            }

        } else if(firstNodeName.equals("shape") && secondNodeName.equals("shape")) {

            if(firstNodeValue.equals("circle") && secondNodeValue.equals("triangle")) {
                difference =  "change to triangle";
            } else if(firstNodeValue.equals("circle") && secondNodeValue.equals("square")) {
                difference =  "change to square";
            }  else if(firstNodeValue.equals("square") && secondNodeValue.equals("circle")) {
                difference =  "change to circle";
            } else if(firstNodeValue.equals("square") && secondNodeValue.equals("triangle")) {
                difference =  "change to triangle";
            }  else if(firstNodeValue.equals("triangle") && secondNodeValue.equals("square")) {
                difference =  "change to square";
            } else if(firstNodeValue.equals("triangle") && secondNodeValue.equals("circle")) {
                difference =  "change to circle";
            } else {
            }

        } else if(firstNodeName.equals("size") && secondNodeName.equals("size")) {

            if(firstNodeValue.equals("small") && secondNodeValue.equals("large")) {
                difference =  "yes large";
            } else if(firstNodeValue.equals("large") && secondNodeValue.equals("small")) {
                difference =  "no large";
            } else {
            }

        } else if(firstNodeName.equals("angle") && secondNodeName.equals("angle")) {

            if(firstNodeValue.equals("0") && secondNodeValue.equals("45")) {
                difference =  "rotate 45";
            } else if(firstNodeValue.equals("0") && secondNodeValue.equals("90")) {
                difference =  "rotate 90";
            } else if(firstNodeValue.equals("0") && secondNodeValue.equals("180")) {
                difference =  "rotate 180";
            } else {
            }

        } else if(firstNodeName.equals("inside") && secondNodeName.equals("inside")) {

            RavensObject object = Utility.returnObject(secondNodeValue, frameA, frameB, frameC);

            getInsideObjects().add(object);

        } else if(firstNodeName.equals("inside") && secondNodeName.equals("above")) {

            RavensObject object = Utility.returnObject(secondNodeValue, frameA, frameB, frameC);

            getAboveObjects().add(object);

        } else {

            System.out.print("NO MATCH!");
        }

        return difference;
    }

    public RavensObject getFirstObject() {
        return firstObject;
    }

    public void setFirstObject(RavensObject firstObject) {
        this.firstObject = firstObject;
    }

    public RavensObject getSecondObject() {
        return secondObject;
    }

    public void setSecondObject(RavensObject secondObject) {
        this.secondObject = secondObject;
    }

    public ArrayList<String> getTransformations() {
        return transformations;
    }

    public void setTransformations(ArrayList<String> transformations) {
        this.transformations = transformations;
    }

    public ArrayList<RavensObject> getInsideObjects() {
        return insideObjects;
    }

    public void setInsideObjects(ArrayList<RavensObject> insideObjects) {
        this.insideObjects = insideObjects;
    }

    public ArrayList<RavensObject> getAboveObjects() {
        return aboveObjects;
    }

    public void setAboveObjects(ArrayList<RavensObject> aboveObjects) {
        this.aboveObjects = aboveObjects;
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

    public FrameC getFrameC() {
        return frameC;
    }

    public void setFrameC(FrameC frameC) {
        this.frameC = frameC;
    }
}
