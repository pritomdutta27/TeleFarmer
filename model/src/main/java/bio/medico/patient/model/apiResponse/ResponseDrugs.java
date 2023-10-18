package bio.medico.patient.model.apiResponse;

import bio.medico.patient.model.NullRemoveUtil;

public class ResponseDrugs {

    private String _id;
    private String _rev;
    private String uuid;
    private String name;
    private String group;
    private String status;
    private String event;
    private String indication;
    private String preparation;
    private String dosageAndAdministration;
    private String image;
    private String createAt;
    private String updatedAt;
    private String manufacturingBy;

    public String get_id() {
        return _id;
    }

    public String get_rev() {
        return _rev;
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return NullRemoveUtil.getNotNull(name);
    }

    public String getGroup() {
        return group;
    }

    public String getStatus() {
        return status;
    }

    public String getEvent() {
        return event;
    }

    public String getIndication() {
        return indication;
    }

    public String getPreparation() {
        return preparation;
    }

    public String getDosageAndAdministration() {
        return dosageAndAdministration;
    }

    public String getImage() {
        return image;
    }

    public String getCreateAt() {
        return createAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getManufacturingBy() {
        return manufacturingBy;
    }
}
