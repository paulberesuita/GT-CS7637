package project2;

import project2.RavensObject;

import java.util.ArrayList;

public class FrameC {
	
	ArrayList<RavensObject> objects = null;

	public FrameC(ArrayList<RavensObject> objects) {

		this.objects = objects;
	}

	public ArrayList<RavensObject> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<RavensObject> objects) {
		this.objects = objects;
	}

}
