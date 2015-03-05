package project3;

import java.util.ArrayList;

public class FrameD {

    ArrayList<RavensObject> objects = null;

    public FrameD(ArrayList<RavensObject> objects) {

        this.objects = objects;
    }

    public ArrayList<RavensObject> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<RavensObject> objects) {
        this.objects = objects;
    }
}
