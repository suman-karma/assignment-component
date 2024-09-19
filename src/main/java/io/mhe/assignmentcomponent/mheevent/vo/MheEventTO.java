package io.mhe.assignmentcomponent.mheevent.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.mhe.assignmentcomponent.mheevent.util.MheEventConstants.MheEvent;
import io.mhe.assignmentcomponent.mheevent.util.MheEventConstants.MheEventAction;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MheEventTO {
private MheEvent mheEvent;
    private Long eventSensedByUserId;
    private Date eventSensedDate;
    private List<MheEventDataTO> dataList;
    private TrackbackUrlTO trackbackUrl;
    private MheEventAction mheEventAction;

    public MheEventTO(MheEvent mheEvent, Long eventSensedByUserId, Date eventSensedDate, List<MheEventDataTO> dataList, TrackbackUrlTO trackbackUrl, MheEventAction mheEventAction) {
        this.mheEvent = mheEvent;
        this.eventSensedByUserId = eventSensedByUserId;
        this.eventSensedDate = eventSensedDate;
        this.dataList = dataList;
        this.trackbackUrl = trackbackUrl;
        this.mheEventAction = mheEventAction;
    }

    public MheEventTO() {
    }

    public static MheEventTOBuilder builder() {
        return new MheEventTOBuilder();
    }

    public MheEvent getMheEvent() {
        return this.mheEvent;
    }

    public Long getEventSensedByUserId() {
        return this.eventSensedByUserId;
    }

    public Date getEventSensedDate() {
        return this.eventSensedDate;
    }

    public List<MheEventDataTO> getDataList() {
        return this.dataList;
    }

    public TrackbackUrlTO getTrackbackUrl() {
        return this.trackbackUrl;
    }

    public MheEventAction getMheEventAction() {
        return this.mheEventAction;
    }

    public void setMheEvent(MheEvent mheEvent) {
        this.mheEvent = mheEvent;
    }

    public void setEventSensedByUserId(Long eventSensedByUserId) {
        this.eventSensedByUserId = eventSensedByUserId;
    }

    public void setEventSensedDate(Date eventSensedDate) {
        this.eventSensedDate = eventSensedDate;
    }

    public void setDataList(List<MheEventDataTO> dataList) {
        this.dataList = dataList;
    }

    public void setTrackbackUrl(TrackbackUrlTO trackbackUrl) {
        this.trackbackUrl = trackbackUrl;
    }

    public void setMheEventAction(MheEventAction mheEventAction) {
        this.mheEventAction = mheEventAction;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof MheEventTO other)) return false;
        if (!other.canEqual(this)) return false;
        final Object this$mheEvent = this.getMheEvent();
        final Object other$mheEvent = other.getMheEvent();
        if (!Objects.equals(this$mheEvent, other$mheEvent)) return false;
        final Object this$eventSensedByUserId = this.getEventSensedByUserId();
        final Object other$eventSensedByUserId = other.getEventSensedByUserId();
        if (!Objects.equals(this$eventSensedByUserId, other$eventSensedByUserId))
            return false;
        final Object this$eventSensedDate = this.getEventSensedDate();
        final Object other$eventSensedDate = other.getEventSensedDate();
        if (!Objects.equals(this$eventSensedDate, other$eventSensedDate))
            return false;
        final Object this$dataList = this.getDataList();
        final Object other$dataList = other.getDataList();
        if (!Objects.equals(this$dataList, other$dataList)) return false;
        final Object this$trackbackUrl = this.getTrackbackUrl();
        final Object other$trackbackUrl = other.getTrackbackUrl();
        if (!Objects.equals(this$trackbackUrl, other$trackbackUrl))
            return false;
        final Object this$mheEventAction = this.getMheEventAction();
        final Object other$mheEventAction = other.getMheEventAction();
        return Objects.equals(this$mheEventAction, other$mheEventAction);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof MheEventTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $mheEvent = this.getMheEvent();
        result = result * PRIME + ($mheEvent == null ? 43 : $mheEvent.hashCode());
        final Object $eventSensedByUserId = this.getEventSensedByUserId();
        result = result * PRIME + ($eventSensedByUserId == null ? 43 : $eventSensedByUserId.hashCode());
        final Object $eventSensedDate = this.getEventSensedDate();
        result = result * PRIME + ($eventSensedDate == null ? 43 : $eventSensedDate.hashCode());
        final Object $dataList = this.getDataList();
        result = result * PRIME + ($dataList == null ? 43 : $dataList.hashCode());
        final Object $trackbackUrl = this.getTrackbackUrl();
        result = result * PRIME + ($trackbackUrl == null ? 43 : $trackbackUrl.hashCode());
        final Object $mheEventAction = this.getMheEventAction();
        result = result * PRIME + ($mheEventAction == null ? 43 : $mheEventAction.hashCode());
        return result;
    }

    public String toString() {
        return "MheEventTO(mheEvent=" + this.getMheEvent() + ", eventSensedByUserId=" + this.getEventSensedByUserId() + ", eventSensedDate=" + this.getEventSensedDate() + ", dataList=" + this.getDataList() + ", trackbackUrl=" + this.getTrackbackUrl() + ", mheEventAction=" + this.getMheEventAction() + ")";
    }

    public static class MheEventTOBuilder {
        private MheEvent mheEvent;
        private Long eventSensedByUserId;
        private Date eventSensedDate;
        private List<MheEventDataTO> dataList;
        private TrackbackUrlTO trackbackUrl;
        private MheEventAction mheEventAction;

        MheEventTOBuilder() {
        }

        public MheEventTOBuilder mheEvent(MheEvent mheEvent) {
            this.mheEvent = mheEvent;
            return this;
        }

        public MheEventTOBuilder eventSensedByUserId(Long eventSensedByUserId) {
            this.eventSensedByUserId = eventSensedByUserId;
            return this;
        }

        public MheEventTOBuilder eventSensedDate(Date eventSensedDate) {
            this.eventSensedDate = eventSensedDate;
            return this;
        }

        public MheEventTOBuilder dataList(List<MheEventDataTO> dataList) {
            this.dataList = dataList;
            return this;
        }

        public MheEventTOBuilder trackbackUrl(TrackbackUrlTO trackbackUrl) {
            this.trackbackUrl = trackbackUrl;
            return this;
        }

        public MheEventTOBuilder mheEventAction(MheEventAction mheEventAction) {
            this.mheEventAction = mheEventAction;
            return this;
        }

        public MheEventTO build() {
            return new MheEventTO(mheEvent, eventSensedByUserId, eventSensedDate, dataList, trackbackUrl, mheEventAction);
        }

        public String toString() {
            return "MheEventTO.MheEventTOBuilder(mheEvent=" + this.mheEvent + ", eventSensedByUserId=" + this.eventSensedByUserId + ", eventSensedDate=" + this.eventSensedDate + ", dataList=" + this.dataList + ", trackbackUrl=" + this.trackbackUrl + ", mheEventAction=" + this.mheEventAction + ")";
        }
    }

}
