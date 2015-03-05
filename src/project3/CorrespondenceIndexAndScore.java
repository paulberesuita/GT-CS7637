package project3;

public class CorrespondenceIndexAndScore {

    public int frameAObjectIndex;
    public int correspondingObjectIndex;
    public int score;

    public CorrespondenceIndexAndScore(int correspondingObjectIndex, int score) {

        this.frameAObjectIndex = 0;
        this.correspondingObjectIndex = correspondingObjectIndex;
        this.score = score;
    }

    public int getFrameAObjectIndex() {
        return frameAObjectIndex;
    }

    public void setFrameAObjectIndex(int frameAObjectIndex) {
        this.frameAObjectIndex = frameAObjectIndex;
    }

    public int getCorrespondingObjectIndex() {
        return correspondingObjectIndex;
    }

    public void setCorrespondingObjectIndex(int correspondingObjectIndex) {
        this.correspondingObjectIndex = correspondingObjectIndex;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


}
