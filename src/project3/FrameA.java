package project3;

import java.util.ArrayList;

public class FrameA {

	ArrayList<RavensObject> objectsA = null;

	public FrameA(ArrayList<RavensObject> objects) {

		this.objectsA = objects;
	}

	public ArrayList<RavensObject> getObjects() {

        return objectsA;
	}

	public void setObjects(ArrayList<RavensObject> objects) {

        this.objectsA = objects;
	}
}
