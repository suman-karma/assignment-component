package io.mhe.assignmentcomponent.vo;

public class ExtendedMarathon extends Marathon implements Model {
    private static final long serialVersionUID = 9149011819762160411L;

    private boolean populatedMarathon;


    /**
     * @return populatedMarathon
     */

    public boolean isPopulatedMarathon() {
        return populatedMarathon;
    }

    /**
     * @param populatedMarathon the populatedMarathon to set
     */
    public void setPopulatedMarathon(boolean populatedMarathon) {
        this.populatedMarathon = populatedMarathon;
    }


}