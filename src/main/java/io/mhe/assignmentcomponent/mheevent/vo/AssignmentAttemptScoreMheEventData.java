package io.mhe.assignmentcomponent.mheevent.vo;

import io.mhe.assignmentcomponent.mheevent.util.MheEventConstants.MheEventAction;

import java.util.List;
import java.util.Objects;

public class AssignmentAttemptScoreMheEventData extends MheEventData {

private List<MheEventDataTO> MheEventDataTOList;
    private MheEventOperation operation;

    public AssignmentAttemptScoreMheEventData(List<MheEventDataTO> MheEventDataTOList, MheEventOperation operation, TrackbackUrlTO trackbackUrl, MheEventAction mheEventAction) {
        this.MheEventDataTOList = MheEventDataTOList;
        this.operation = operation;
        this.trackbackUrl = trackbackUrl;
        this.mheEventAction = mheEventAction;
    }

    public AssignmentAttemptScoreMheEventData() {
    }

    public static AssignmentAttemptScoreMheEventDataBuilder builder() {
        return new AssignmentAttemptScoreMheEventDataBuilder();
    }

    public List<MheEventDataTO> getMheEventDataTOList() {
        return this.MheEventDataTOList;
    }

    public MheEventOperation getOperation() {
        return this.operation;
    }

    @Override
    public TrackbackUrlTO getTrackbackUrl() {
        return this.trackbackUrl;
    }

    @Override
    public MheEventAction getMheEventAction() {
        return this.mheEventAction;
    }

    public void setMheEventDataTOList(List<MheEventDataTO> MheEventDataTOList) {
        this.MheEventDataTOList = MheEventDataTOList;
    }

    public void setOperation(MheEventOperation operation) {
        this.operation = operation;
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
        if (!(o instanceof AssignmentAttemptScoreMheEventData other)) return false;
        if (!other.canEqual(this)) return false;
        final Object this$MheEventDataTOList = this.getMheEventDataTOList();
        final Object other$MheEventDataTOList = other.getMheEventDataTOList();
        if (!Objects.equals(this$MheEventDataTOList, other$MheEventDataTOList))
            return false;
        final Object this$operation = this.getOperation();
        final Object other$operation = other.getOperation();
        if (!Objects.equals(this$operation, other$operation)) return false;
        final Object this$trackbackUrl = this.getTrackbackUrl();
        final Object other$trackbackUrl = other.getTrackbackUrl();
        if (!Objects.equals(this$trackbackUrl, other$trackbackUrl))
            return false;
        final Object this$mheEventAction = this.getMheEventAction();
        final Object other$mheEventAction = other.getMheEventAction();
        return Objects.equals(this$mheEventAction, other$mheEventAction);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AssignmentAttemptScoreMheEventData;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $MheEventDataTOList = this.getMheEventDataTOList();
        result = result * PRIME + ($MheEventDataTOList == null ? 43 : $MheEventDataTOList.hashCode());
        final Object $operation = this.getOperation();
        result = result * PRIME + ($operation == null ? 43 : $operation.hashCode());
        final Object $trackbackUrl = this.getTrackbackUrl();
        result = result * PRIME + ($trackbackUrl == null ? 43 : $trackbackUrl.hashCode());
        final Object $mheEventAction = this.getMheEventAction();
        result = result * PRIME + ($mheEventAction == null ? 43 : $mheEventAction.hashCode());
        return result;
    }

public String toString() {
		StringBuilder returnStr = new StringBuilder("AssignmentAttemptScoreMheEventData(MheEventDataTOList=");
		if (this.MheEventDataTOList != null && this.MheEventDataTOList.size() > 0) {
			this.MheEventDataTOList.forEach(returnStr::append);
		}
		returnStr.append(", operation=" + this.operation + ", trackbackUrl=" + this.trackbackUrl + ", mheEventAction="
				+ this.mheEventAction + ")");
		return returnStr.toString();
}

    public static class AssignmentAttemptScoreMheEventDataBuilder {
        private List<MheEventDataTO> mheEventDataTOList;
        private MheEventOperation operation;
        private TrackbackUrlTO trackbackUrl;
        private MheEventAction mheEventAction;

        AssignmentAttemptScoreMheEventDataBuilder() {
        }

        public AssignmentAttemptScoreMheEventDataBuilder mheEventDataTOList(List<MheEventDataTO> MheEventDataTOList) {
            this.mheEventDataTOList = MheEventDataTOList;
            return this;
        }
        
        public AssignmentAttemptScoreMheEventDataBuilder addMheEventDataTO(MheEventDataTO MheEventDataTO) {
            this.mheEventDataTOList = List.of(MheEventDataTO);
            return this;
        }

        public AssignmentAttemptScoreMheEventDataBuilder operation(MheEventOperation operation) {
            this.operation = operation;
            return this;
        }

        public AssignmentAttemptScoreMheEventDataBuilder trackbackUrl(TrackbackUrlTO trackbackUrl) {
            this.trackbackUrl = trackbackUrl;
            return this;
        }

        public AssignmentAttemptScoreMheEventDataBuilder mheEventAction(MheEventAction mheEventAction) {
            this.mheEventAction = mheEventAction;
            return this;
        }

        public AssignmentAttemptScoreMheEventData build() {
            return new AssignmentAttemptScoreMheEventData(mheEventDataTOList, operation, trackbackUrl, mheEventAction);
        }

        public String toString() {
            return "AssignmentAttemptScoreMheEventData.AssignmentAttemptScoreMheEventDataBuilder(MheEventDataTOList=" + this.mheEventDataTOList + ", operation=" + this.operation + ", trackbackUrl=" + this.trackbackUrl + ", mheEventAction=" + this.mheEventAction + ")";
        }
    }
}
