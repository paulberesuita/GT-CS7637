package project4;

import java.util.ArrayList;

/**
 * Created by paulberesuita on 4/4/15.
 */
public class FrameG {

    ArrayList<RavensObject> objects = null;

    public FrameG(ArrayList<RavensObject> objects) {

        this.objects = objects;
    }

    public ArrayList<RavensObject> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<RavensObject> objects) {
        this.objects = objects;
    }
}
