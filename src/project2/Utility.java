package project2;

import java.util.ArrayList;

public class Utility {

    public Utility() {

    }

    public static int add(int i, int j) {
        return i+j;
    }

    public static RavensObject returnObject(String objectName, FrameA frameA, FrameB frameB, FrameC frameC) {

        RavensObject object = null;

        for(int i=0; i<frameA.getObjects().size(); i++) {

            if(frameA.getObjects().get(i).getName().equals(objectName)) {
                object = frameA.getObjects().get(i);
            }

        }

        for(int i=0; i<frameB.getObjects().size(); i++) {

            if(frameB.getObjects().get(i).getName().equals(objectName)) {
                object = frameB.getObjects().get(i);
            }

        }

        for(int i=0; i<frameC.getObjects().size(); i++) {

            if(frameC.getObjects().get(i).getName().equals(objectName)) {
                object = frameC.getObjects().get(i);
            }

        }

        return object;
    }

    public static CorrespondenceIndexAndScore bestCorrespondenceIndex(RavensObject object, ArrayList<RavensObject> allObjects) {

        int count = 0;
        int highestCurrentCount = 0;
        int correspondingObjectIndex = 0;

        for(int i=0; i<allObjects.size(); i++) {

            count = 0;

            for(int m=0; m<allObjects.get(i).getAttributes().size(); m++) {

                for(int n=0; n<object.getAttributes().size(); n++) {

                    if(object.getAttributes().get(n).getName().equals(allObjects.get(i).getAttributes().get(m).getName()) &&
                            object.getAttributes().get(n).getValue().equals(allObjects.get(i).getAttributes().get(m).getValue()) &&
                            (allObjects.get(i).getAttributes().get(m).getName().equals("shape") ||
                                    allObjects.get(i).getAttributes().get(m).getName().equals("size")  ||
                                    allObjects.get(i).getAttributes().get(m).getName().equals("fill")  ||
                                    allObjects.get(i).getAttributes().get(m).getName().equals("angle") ||
                                    allObjects.get(i).getAttributes().get(m).getName().equals("above")   )) {

                        count = count + 5;

                    } else if(object.getAttributes().get(n).getName().equals(allObjects.get(i).getAttributes().get(m).getName()) &&
                            !object.getAttributes().get(n).getValue().equals(allObjects.get(i).getAttributes().get(m).getValue()) &&
                            allObjects.get(i).getAttributes().get(m).getName().equals("angle")) {

                        count = count + 3;

                    } else if(object.getAttributes().get(n).getName().equals(allObjects.get(i).getAttributes().get(m).getName()) &&
                            !object.getAttributes().get(n).getValue().equals(allObjects.get(i).getAttributes().get(m).getValue()) &&
                            allObjects.get(i).getAttributes().get(m).getName().equals("size")) {

                        count = count + 2;

                    } else if(object.getAttributes().get(n).getName().equals(allObjects.get(i).getAttributes().get(m).getName()) &&
                            !object.getAttributes().get(n).getValue().equals(allObjects.get(i).getAttributes().get(m).getValue()) &&
                            allObjects.get(i).getAttributes().get(m).getName().equals("fill")) {

                        count = count + 1;

                    } else {
                        count = count + 0;
                    }

                }
            }

            if(count > highestCurrentCount) {
                correspondingObjectIndex = i;
                highestCurrentCount = count;
            }
        }

        CorrespondenceIndexAndScore indexAndScore = new CorrespondenceIndexAndScore(correspondingObjectIndex, highestCurrentCount);

        return indexAndScore;

    }

}
