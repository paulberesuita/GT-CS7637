package project4;

import java.util.ArrayList;

/**
 * Created by paulberesuita on 4/4/15.
 */
public class FrameF {

    ArrayList<RavensObject> objects = null;

    public FrameF(ArrayList<RavensObject> objects) {

        this.objects = objects;
    }

    public ArrayList<RavensObject> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<RavensObject> objects) {
        this.objects = objects;
    }
}
