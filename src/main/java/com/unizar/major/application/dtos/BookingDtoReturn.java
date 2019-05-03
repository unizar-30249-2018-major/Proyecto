package com.unizar.major.application.dtos;

import com.unizar.major.domain.Period;
import com.unizar.major.domain.Space;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class BookingDtoReturn {
    private long id;

    private boolean isPeriodic;

    private String reason;

    private Collection<Period> period;

    private String state;

    private boolean active;

    private String periodRep;

    private Date finalDate;

    private Collection<Space> spaces;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean getIsPeriodic() {
        return isPeriodic;
    }

    public void setIsPeriodic(boolean isPeriodic) {
        this.isPeriodic = isPeriodic;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Collection<Period> getPeriod() {
        return period;
    }

    public void setPeriod(Collection<Period> period) {
        this.period=period;
    }

    public String getPeriodRep() {
        return periodRep;
    }

    public void setPeriodRep(String periodRep) {
        this.periodRep = periodRep;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Collection<Space> getSpaces() {
        return spaces;
    }

    public void setSpaces(List<Space> spaces) {
        this.spaces = spaces;
    }
}
