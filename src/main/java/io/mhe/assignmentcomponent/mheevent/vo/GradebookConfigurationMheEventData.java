package io.mhe.assignmentcomponent.mheevent.vo;

import io.mhe.assignmentcomponent.mheevent.util.MheEventConstants.MheEventAction;

public class GradebookConfigurationMheEventData extends MheEventData {

private Object sectionId;

    public GradebookConfigurationMheEventData(Object sectionId, TrackbackUrlTO trackbackUrl, MheEventAction mheEventAction) {
        this.sectionId = sectionId;
        this.trackbackUrl = trackbackUrl;
        this.mheEventAction = mheEventAction;
    }

    public GradebookConfigurationMheEventData() {
    }

    public static GradebookConfigurationMheEventDataBuilder builder() {
        return new GradebookConfigurationMheEventDataBuilder();
    }

    public Object getSectionId() {
        return this.sectionId;
    }

    @Override
    public TrackbackUrlTO getTrackbackUrl() {
        return this.trackbackUrl;
    }

    @Override
    public MheEventAction getMheEventAction() {
        return this.mheEventAction;
    }

    public void setSectionId(Object sectionId) {
        this.sectionId = sectionId;
    }

    @Override
    public void setTrackbackUrl(TrackbackUrlTO trackbackUrl) {
        this.trackbackUrl = trackbackUrl;
    }

    @Override
    public void setMheEventAction(MheEventAction mheEventAction) {
        this.mheEventAction = mheEventAction;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof GradebookConfigurationMheEventData)) return false;
        final GradebookConfigurationMheEventData other = (GradebookConfigurationMheEventData) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$sectionId = this.getSectionId();
        final Object other$sectionId = other.getSectionId();
        if (this$sectionId == null ? other$sectionId != null : !this$sectionId.equals(other$sectionId)) return false;
        final Object this$trackbackUrl = this.getTrackbackUrl();
        final Object other$trackbackUrl = other.getTrackbackUrl();
        if (this$trackbackUrl == null ? other$trackbackUrl != null : !this$trackbackUrl.equals(other$trackbackUrl))
            return false;
        final Object this$mheEventAction = this.getMheEventAction();
        final Object other$mheEventAction = other.getMheEventAction();
        if (this$mheEventAction == null ? other$mheEventAction != null : !this$mheEventAction.equals(other$mheEventAction))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof GradebookConfigurationMheEventData;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $sectionId = this.getSectionId();
        result = result * PRIME + ($sectionId == null ? 43 : $sectionId.hashCode());
        final Object $trackbackUrl = this.getTrackbackUrl();
        result = result * PRIME + ($trackbackUrl == null ? 43 : $trackbackUrl.hashCode());
        final Object $mheEventAction = this.getMheEventAction();
        result = result * PRIME + ($mheEventAction == null ? 43 : $mheEventAction.hashCode());
        return result;
    }

    public String toString() {
        return "GradebookConfigurationMheEventData(sectionId=" + this.getSectionId() + ", trackbackUrl=" + this.getTrackbackUrl() + ", mheEventAction=" + this.getMheEventAction() + ")";
    }

    public static class GradebookConfigurationMheEventDataBuilder {
        private Object sectionId;
        private TrackbackUrlTO trackbackUrl;
        private MheEventAction mheEventAction;

        GradebookConfigurationMheEventDataBuilder() {
        }

        public GradebookConfigurationMheEventDataBuilder sectionId(Object sectionId) {
            this.sectionId = sectionId;
            return this;
        }

        public GradebookConfigurationMheEventDataBuilder trackbackUrl(TrackbackUrlTO trackbackUrl) {
            this.trackbackUrl = trackbackUrl;
            return this;
        }

        public GradebookConfigurationMheEventDataBuilder mheEventAction(MheEventAction mheEventAction) {
            this.mheEventAction = mheEventAction;
            return this;
        }

        public GradebookConfigurationMheEventData build() {
            return new GradebookConfigurationMheEventData(sectionId, trackbackUrl, mheEventAction);
        }

        public String toString() {
            return "GradebookConfigurationMheEventData.GradebookConfigurationMheEventDataBuilder(sectionId=" + this.sectionId + ", trackbackUrl=" + this.trackbackUrl + ", mheEventAction=" + this.mheEventAction + ")";
        }
    }
}
