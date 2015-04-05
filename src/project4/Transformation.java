package project4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Transformation {

    RavensObject firstObject = null;
    RavensObject secondObject = null;

    ArrayList<String> transformations = null;

    ArrayList<RavensObject> insideObjects = null;
    ArrayList<RavensObject> aboveObjects = null;
    ArrayList<RavensObject> leftOfObjects = null;
    ArrayList<RavensObject> rightOfObjects = null;

    Frame frameA = null;
    Frame frameB = null;

    public Transformation(RavensObject firstObject, RavensObject secondObject, Frame frameA, Frame frameB) {

        this.firstObject = firstObject;
        this.secondObject = secondObject;

        transformations = new ArrayList<String>();
        insideObjects = new ArrayList<RavensObject>();
        aboveObjects = new ArrayList<RavensObject>();
        leftOfObjects = new ArrayList<RavensObject>();
        aboveObjects = new ArrayList<RavensObject>();

        this.frameA = frameA;
        this.frameB = frameB;

    }

    /* Set of transformations will include flip, fill, partial fill,
    *  move down, move up, expand, shrink, delete, and
    * rotate 180/135/90/45 degrees.*/
    public boolean checkDifferencesBetweenNodes() {

        boolean differencesExist = false;

        String firstObjectShapeType = Utility.getShapeOfObject(firstObject);
        String secondObjectShapeType = Utility.getShapeOfObject(firstObject);

        for(int i=0; i< firstObject.getAttributes().size(); i++) {

            for(int j=0; j< secondObject.getAttributes().size(); j++) {

                String difference = getDifference(firstObject.getAttributes().get(i).getName(), firstObject.getAttributes().get(i).getValue(), secondObject.getAttributes().get(j).getName(), secondObject.getAttributes().get(j).getValue(), firstObjectShapeType, secondObjectShapeType);

                //Only add transformation if there is a difference
                if(!difference.equals("no match")) {

                    getTransformations().add(difference);
                    differencesExist = true;
                }

            }

        }

        //TODO - need to add another transformation for attributes that are new; example 8 A(Y) B(Y) the left-of:X is added

        for(int i=0; i< secondObject.getAttributes().size(); i++) {

            boolean isThere = false;
            for (int j = 0; j < firstObject.getAttributes().size(); j++) {

                if(secondObject.getAttributes().get(i).getName().equals(firstObject.getAttributes().get(j).getName())){
                    isThere = true;
                }
            }

            if(!isThere){

                if(secondObject.getAttributes().get(i).getName().equals("above")){

                    RavensObject object = Utility.returnObjectInFrameB(secondObject.getAttributes().get(i).getValue(), frameB);
                    getAboveObjects().add(object);
                    differencesExist = true;


                } else if(secondObject.getAttributes().get(i).getName().equals("inside")) {

                    RavensObject object = Utility.returnObjectInFrameB(secondObject.getAttributes().get(i).getValue(), frameB);
                    getInsideObjects().add(object);
                    differencesExist = true;


                } else if(secondObject.getAttributes().get(i).getName().equals("left-of")) {

                    if(secondObject.getAttributes().get(i).getValue().contains(",")){

                        List<String> differentObjects = Arrays.asList(secondObject.getAttributes().get(i).getValue().split(","));

                        for(int n=0; n<differentObjects.size(); n++) {

                            RavensObject object = Utility.returnObjectInFrameB(differentObjects.get(n), frameB);
                            getLeftOfObjects().add(object);
                        }

                    } else {

                        RavensObject object = Utility.returnObjectInFrameB(secondObject.getAttributes().get(i).getValue(), frameB);
                        getLeftOfObjects().add(object);
                    }

                    differencesExist = true;


                } else if(secondObject.getAttributes().get(i).getName().equals("right-of")) {



                    if(secondObject.getAttributes().get(i).getValue().contains(",")){

                        List<String> differentObjects = Arrays.asList(secondObject.getAttributes().get(i).getValue().split(","));

                        for(int n=0; n<differentObjects.size(); n++) {

                            RavensObject object = Utility.returnObject(differentObjects.get(n), frameA, frameB);
                            getLeftOfObjects().add(object);
                        }

                    } else {

                        RavensObject object = Utility.returnObject(secondObject.getAttributes().get(i).getValue(), frameA, frameB);
                        getRightOfObjects().add(object);
                    }

                    differencesExist = true;


                } else {

                }

            }
        }

        return differencesExist;

    }

    public String getDifference(String firstNodeName, String firstNodeValue, String secondNodeName, String secondNodeValue, String firstObjectShapeType, String secondObjectShapeType) {

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

            if(firstObjectShapeType.equals("right-triangle") && secondObjectShapeType.equals("right-triangle")) {
                if(firstNodeValue.equals("0") && secondNodeValue.equals("90")) {
                    difference =  "reflect up";
                } else if(firstNodeValue.equals("0") && secondNodeValue.equals("45")) {
                    difference =  "rotate 45";
                } else if(firstNodeValue.equals("0") && secondNodeValue.equals("315")) {
                    difference =  "rotate -45";
                } else if(firstNodeValue.equals("0") && secondNodeValue.equals("270")) {
                    difference =  "reflect right";
                } else if(firstNodeValue.equals("45") && secondNodeValue.equals("90")) {
                    difference =  "rotate 45";
                } else if(firstNodeValue.equals("45") && secondNodeValue.equals("0")) {
                    difference =  "rotate -45";
                } else if(firstNodeValue.equals("90") && secondNodeValue.equals("0")) {
                    difference =  "reflect down";
                } else if(firstNodeValue.equals("90") && secondNodeValue.equals("135")) {
                    difference =  "rotate 45";
                } else if(firstNodeValue.equals("90") && secondNodeValue.equals("45")) {
                    difference =  "rotate -45";
                } else if(firstNodeValue.equals("135") && secondNodeValue.equals("180")) {
                    difference =  "rotate 45";
                } else if(firstNodeValue.equals("135") && secondNodeValue.equals("90")) {
                    difference =  "rotate -45";
                } else if(firstNodeValue.equals("180") && secondNodeValue.equals("90")) {
                    difference =  "reflect left";
                } else if(firstNodeValue.equals("180") && secondNodeValue.equals("270")) {
                    difference =  "reflect down";
                } else if(firstNodeValue.equals("270") && secondNodeValue.equals("180")) {
                    difference =  "reflect up";
                } else if(firstNodeValue.equals("270") && secondNodeValue.equals("0")) {
                    difference =  "reflect left";
                }  else {
                }
            } else if(firstObjectShapeType.equals("Pac-Man") && secondObjectShapeType.equals("Pac-Man")) {
                if(firstNodeValue.equals("45") && secondNodeValue.equals("135")) {
                    difference =  "reflect left";
                } else if(firstNodeValue.equals("135") && secondNodeValue.equals("45")) {
                    difference =  "reflect right";
                } else if(firstNodeValue.equals("45") && secondNodeValue.equals("315")) {
                    difference =  "reflect up";
                } else if(firstNodeValue.equals("315") && secondNodeValue.equals("45")) {
                    difference =  "reflect down";
                }  else {
                }
            } else if(firstObjectShapeType.equals("arrow") && secondObjectShapeType.equals("arrow")) {
                if(firstNodeValue.equals("0") && secondNodeValue.equals("180")) {
                    difference =  "reflect left";
                } else if(firstNodeValue.equals("180") && secondNodeValue.equals("0")) {
                    difference =  "reflect right";
                } else {
                }
            } else {
                if(firstNodeValue.equals("0") && secondNodeValue.equals("45")) {
                    difference =  "rotate 45";
                } else if(firstNodeValue.equals("0") && secondNodeValue.equals("90")) {
                    difference =  "rotate 90";
                } else if(firstNodeValue.equals("0") && secondNodeValue.equals("180")) {
                    difference =  "rotate 180";
                } else if(firstNodeValue.equals("0") && secondNodeValue.equals("270")) {
                    difference =  "rotate 270";
                } else if(firstNodeValue.equals("45") && secondNodeValue.equals("0")) {
                    difference =  "rotate -45";
                } else if(firstNodeValue.equals("45") && secondNodeValue.equals("135")) {
                    difference =  "rotate 90";
                } else if(firstNodeValue.equals("45") && secondNodeValue.equals("315")) {
                    difference =  "rotate -90";
                } else if(firstNodeValue.equals("180") && secondNodeValue.equals("90")) {
                    difference =  "rotate -90";
                }  else {
                }
            }

        }

//        else if(firstNodeName.equals("inside") && secondNodeName.equals("inside")) {
//
//            RavensObject object = Utility.returnObject(secondNodeValue, frameA, frameB, frameC);
//
//            getInsideObjects().add(object);
//
//            difference =  "inside objects change";
//
//
//        } else if(firstNodeName.equals("inside") && secondNodeName.equals("above")) {
//
//            RavensObject object = Utility.returnObject(secondNodeValue, frameA, frameB, frameC);
//
//            getAboveObjects().add(object);
//
//            difference =  "move out";
//
//
//        }

        else {

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

    public Frame getFrameA() {
        return frameA;
    }

    public void setFrameA(Frame frameA) {
        this.frameA = frameA;
    }

    public Frame getFrameB() {
        return frameB;
    }

    public void setFrameB(Frame frameB) {
        this.frameB = frameB;
    }

    public ArrayList<RavensObject> getLeftOfObjects() {
        return leftOfObjects;
    }

    public void setLeftOfObjects(ArrayList<RavensObject> leftOfObjects) {
        this.leftOfObjects = leftOfObjects;
    }

    public ArrayList<RavensObject> getRightOfObjects() {
        return rightOfObjects;
    }

    public void setRightOfObjects(ArrayList<RavensObject> rightOfObjects) {
        this.rightOfObjects = rightOfObjects;
    }
}
