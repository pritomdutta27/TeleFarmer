package bio.medico.patient.model.apiResponse;


import java.util.List;

import bio.medico.patient.model.NullRemoveUtil;
import bio.medico.patient.model.apiResponse.FollowUpModel;


public class RequestCallFinnish {
    private String doctorId;
    private String doctorName;
    private String doctorImage;

    private String patientId;
    private String patientName;
    private String patientImage;
    private String patientGender;

    private String patientDateOfBirth;
    private String patientAge;
    private String patientWeight;
    private String patientHeight;
    private String patientPulse;
    private String patientBloodPressureSystolic;
    private String patientBloodPressureDiastolic;
    private String patientTemperature;
    private String patientReasonOfCalling;

    private String previousHistory;
    private String callType;

    private FollowUpModel nextFollowUp;

    private List<ChiefComplain> chiefComplain;
    private String presentIllness;
    private List<ChiefComplain> presentMedicalCondition;
    private List<ChiefComplain> pastMedicalCondition;
    private String allergicCondition;
    private String currentMedication;

    private List<String> assessmentDiagnosis;
    private List<String> advices;
    private List<String> investigation;

    private List<Prescription> prescription;

    private String channel;
    private String callStatus;
    private long callDuration;

    //===================================================================================================================================================
    public RequestCallFinnish(String doctorId, String doctorName, String doctorImage, String patientId, String patientName, String patientImage, String patientGender,
                              String patientDateOfBirth,
                              String patientAge,
                              String patientWeight,
                              String patientHeight,
                              String patientPulse,
                              String patientBloodPressureSystolic,
                              String patientBloodPressureDiastolic,
                              String patientTemperature,
                              String patientReasonOfCalling,
                              String previousHistory, List<ChiefComplain> chiefComplain, String presentIllness, List<ChiefComplain> presentMedicalCondition,
                              List<ChiefComplain> pastMedicalCondition, String allergicCondition,
                              String currentMedication,
                              List<String> assessmentDiagnosis,
                              List<String> advices,
                              List<String> investigation, List<Prescription> prescription,
                              String callStatus,
                              long callDuration,
                              String callType,
                              FollowUpModel nextFollowUp,
                              String channel
    ) {
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.doctorImage = doctorImage;
        this.patientId = patientId;
        this.patientName = patientName;
        this.patientImage = patientImage;
        this.patientGender = patientGender;

        this.patientDateOfBirth = patientDateOfBirth;
        this.patientAge = patientAge;
        this.patientWeight = patientWeight;
        this.patientHeight = patientHeight;
        this.patientPulse = patientPulse;
        this.patientBloodPressureSystolic = patientBloodPressureSystolic;
        this.patientBloodPressureDiastolic = patientBloodPressureDiastolic;
        this.patientTemperature = patientTemperature;
        this.patientReasonOfCalling = patientReasonOfCalling;

        this.previousHistory = previousHistory;
        this.chiefComplain = chiefComplain;
        this.presentIllness = presentIllness;
        this.presentMedicalCondition = presentMedicalCondition;
        this.pastMedicalCondition = pastMedicalCondition;
        this.allergicCondition = allergicCondition;
        this.currentMedication = currentMedication;
        this.assessmentDiagnosis = assessmentDiagnosis;
        this.advices = advices;
        this.investigation = investigation;
        this.prescription = prescription;
        this.callStatus = callStatus;
        this.callDuration = callDuration;
        this.callType = callType;
        this.nextFollowUp = nextFollowUp;
        this.channel = channel;
    }


    public static class ChiefComplain {
        private String symptomName;
        private String times;
        private String symptomDurationUnits;

        public ChiefComplain() {
        }

        public ChiefComplain(String symptomName, String times, String symptomDurationUnits) {
            this.symptomName = symptomName;
            this.times = times;
            this.symptomDurationUnits = symptomDurationUnits;
        }

        public String getSymptomName() {
            return symptomName;
        }

        public String getTimes() {
            return times;
        }

        public String getSymptomDurationUnits() {
            return symptomDurationUnits;
        }
    }

    public static class Prescription {

        private String medicineName;
        private String formulation;
        private String instruction;
        private String times;
        private String duration;
        private String note;
        private int item = 1;

        public int getItem() {
            return item;
        }

        public void setItem(int item) {
            this.item = item;
        }

        public Prescription() {
        }

        public Prescription(String medicineName, String formulation, String instruction, String times, String duration, String note) {
            this.medicineName = medicineName;
            this.formulation = formulation;
            this.instruction = instruction;
            this.times = times;
            this.duration = duration;
            this.note = note;
        }

        public String getMedicineName() {
            return NullRemoveUtil.getNotNull(medicineName);
        }

        public String getFormulation() {
            return NullRemoveUtil.getNotNull(formulation);
        }

        public String getInstruction() {
            return NullRemoveUtil.getNotNull(instruction);
        }

        public String getTimes() {
            return NullRemoveUtil.getNotNull(times);
        }

        public String getDuration() {
            return NullRemoveUtil.getNotNull(duration);
        }

        public String getNote() {
            return NullRemoveUtil.getNotNull(note);
        }


        public void setMedicineName(String medicineName) {
            this.medicineName = medicineName;
        }

        public void setFormulation(String formulation) {
            this.formulation = formulation;
        }

        public void setInstruction(String instruction) {
            this.instruction = instruction;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public void setNote(String note) {
            this.note = note;
        }
    }

    //=========================================================================================================
    public String getDoctorId() {
        return doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDoctorImage() {
        return doctorImage;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getPatientImage() {
        return patientImage;
    }

    public String getPatientGender() {
        return NullRemoveUtil.getNotNull(patientGender);
    }

    public String getPatientDateOfBirth() {
        return patientDateOfBirth;
    }

    public String getPatientAge() {
        return NullRemoveUtil.getNotNull(patientAge);
    }

    public String getPatientWeight() {
        return patientWeight;
    }

    public String getPatientHeight() {
        return patientHeight;
    }

    public String getPatientPulse() {
        return NullRemoveUtil.getNotNull(patientPulse);
    }

    public String getPatientBloodPressureSystolic() {
        return NullRemoveUtil.getNotNull(patientBloodPressureSystolic);
    }

    public String getPatientBloodPressureDiastolic() {
        return NullRemoveUtil.getNotNull(patientBloodPressureDiastolic);
    }

    public String getCallType() {
        return NullRemoveUtil.getNotNull(callType);
    }

    public String getPatientTemperature() {
        return NullRemoveUtil.getNotNull(patientTemperature);
    }

    public String getPatientReasonOfCalling() {
        return NullRemoveUtil.getNotNull(patientReasonOfCalling);
    }

    public String getPreviousHistory() {
        return NullRemoveUtil.getNotNull(previousHistory);
    }

    public List<ChiefComplain> getChiefComplain() {
        return NullRemoveUtil.getNotNull(chiefComplain);
    }

    public String getPresentIllness() {
        return NullRemoveUtil.getNotNull(presentIllness);
    }

    public List<ChiefComplain> getPresentMedicalCondition() {
        return NullRemoveUtil.getNotNull(presentMedicalCondition);
    }

    public List<ChiefComplain> getPastMedicalCondition() {
        return NullRemoveUtil.getNotNull(pastMedicalCondition);
    }

    public String getAllergicCondition() {
        return NullRemoveUtil.getNotNull(allergicCondition);
    }

    public String getCurrentMedication() {
        return NullRemoveUtil.getNotNull(currentMedication);
    }

    public List<String> getAssessmentDiagnosis() {
        return NullRemoveUtil.getNotNull(assessmentDiagnosis);
    }

    public List<String> getAdvices() {
        return NullRemoveUtil.getNotNull(advices);
    }

    public List<String> getInvestigation() {
        return NullRemoveUtil.getNotNull(investigation);
    }

    public List<Prescription> getPrescription() {
        return NullRemoveUtil.getNotNull(prescription);
    }

    public String getCallStatus() {
        return NullRemoveUtil.getNotNull(callStatus);
    }

    public long getCallDuration() {
        return callDuration;
    }

    public FollowUpModel getNextFollowUp() {
        return nextFollowUp;
    }

    public String getChannel() {
        return NullRemoveUtil.getNotNull(channel);
    }
}
