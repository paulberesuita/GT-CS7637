package project1;

import java.util.ArrayList;

public class FrameB {

	ArrayList<RavensObject> objectsB = null;
	
	public FrameB(ArrayList<RavensObject> objects) {
		
		this.objectsB = objects;
	}
	
	public ArrayList<RavensObject> getObjects() {
		return objectsB;
	}

	public void setObjects(ArrayList<RavensObject> objects) {
		this.objectsB = objects;
	}
}
