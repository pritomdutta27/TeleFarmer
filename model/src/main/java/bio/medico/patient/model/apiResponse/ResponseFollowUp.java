package bio.medico.patient.model.apiResponse;

import bio.medico.patient.model.NullRemoveUtil;

import java.util.List;

public class ResponseFollowUp {


    private List<FollowUpItem> items;

    public List<FollowUpItem> getItem() {
        return NullRemoveUtil.getNotNull(items);
    }

    public class FollowUpItem {
        private String updatedAt;
        private String createAt;
        private String uuid;
        private FollowUpModel nextFollowUp;
        private String callStatus;
        private String patientName;
        private String patientImage;
        private String patientId;
        private String doctorName;
        private String doctorImage;
        private String bmdcNumber;
        private String doctorId;

        public FollowUpModel getFollowUp() {
            return nextFollowUp;
        }


        public String getPatientName() {
            return patientName;
        }
    }


}
