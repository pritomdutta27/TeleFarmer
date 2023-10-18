package bio.medico.patient.model.apiResponse;



import java.util.List;

public class ResponseLabReport {

    private List<ItemLabReport> items = null;

    public List<ItemLabReport> getItems() {
        return items;
    }

    public void setItems(List<ItemLabReport> items) {
        this.items = items;
    }

    public static class ItemLabReport {

        private String _id;

        private String _rev;

        private String uuid;

        private String channel;

        private String fileUrl;

        private String createAt;

        private String updatedAt;

        private String patientUuid;

        public String getId() {
            return _id;
        }

        public void setId(String id) {
            this._id = id;
        }

        public String getRev() {
            return _rev;
        }

        public void setRev(String rev) {
            this._rev = rev;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }

        public String getCreateAt() {
            return createAt;
        }

        public void setCreateAt(String createAt) {
            this.createAt = createAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getPatientUuid() {
            return patientUuid;
        }

        public void setPatientUuid(String patientUuid) {
            this.patientUuid = patientUuid;
        }

    }

}