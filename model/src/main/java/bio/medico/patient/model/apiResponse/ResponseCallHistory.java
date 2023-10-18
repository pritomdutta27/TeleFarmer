package bio.medico.patient.model.apiResponse;

import bio.medico.patient.model.NullRemoveUtil;

import java.util.List;


public class ResponseCallHistory extends RequestCallFinnish {
    private String uuid; //prescription Id
    private String createAt;
    private String updatedAt;

    public ResponseCallHistory(String doctorId, String doctorName, String doctorImage, String patientId, String patientName, String patientImage, String patientGender, String patientDateOfBirth, String patientAge, String patientWeight, String patientHeight, String patientPulse, String patientBloodPressureSystolic, String patientBloodPressureDiastolic, String patientTemperature, String patientReasonOfCalling, String previousHistory, List<ChiefComplain> chiefComplain, String presentIllness, List<ChiefComplain> presentMedicalCondition, List<ChiefComplain> pastMedicalCondition, String allergicCondition, String currentMedication, List<String> assessmentDiagnosis, List<String> advices, List<String> investigation, List<Prescription> prescription, String callStatus, long callDuration, String callType, FollowUpModel followUp, String channel) {
        super(doctorId, doctorName, doctorImage, patientId, patientName, patientImage, patientGender, patientDateOfBirth, patientAge, patientWeight, patientHeight, patientPulse, patientBloodPressureSystolic, patientBloodPressureDiastolic, patientTemperature, patientReasonOfCalling, previousHistory, chiefComplain, presentIllness, presentMedicalCondition, pastMedicalCondition, allergicCondition, currentMedication, assessmentDiagnosis, advices, investigation, prescription, callStatus, callDuration, callType, followUp, channel);
    }


    public String getUuid() {
        return NullRemoveUtil.getNotNull(uuid);
    }

    public String getCreateAt() {
        return NullRemoveUtil.getNotNull(createAt);
    }

    public String getUpdatedAt() {
        return NullRemoveUtil.getNotNull(updatedAt);
    }


}

