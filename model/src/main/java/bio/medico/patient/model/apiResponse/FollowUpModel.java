package bio.medico.patient.model.apiResponse;


import bio.medico.patient.model.NullRemoveUtil;

public class FollowUpModel {
    private int value;
    private String units;
    private String followUpTime;

    public FollowUpModel(int value, String units, String followUpTime) {
        this.value = value;
        this.units = units;
        this.followUpTime = followUpTime;
    }

    public int getValue() {
        return NullRemoveUtil.getNotNull(value);
    }

    public String getUnits() {
        return NullRemoveUtil.getNotNull( units);
    }

    public String getFollowUpTime() {
        return NullRemoveUtil.getNotNull(followUpTime);
    }



}
