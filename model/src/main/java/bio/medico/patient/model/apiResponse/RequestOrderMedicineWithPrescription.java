package bio.medico.patient.model.apiResponse;

public class RequestOrderMedicineWithPrescription {

    private String Name;
    private String Address;
    private String prescription;
    private String prescription_link;
    private String Phone;
    private String prescriptionType;

    public RequestOrderMedicineWithPrescription(String name, String address, String prescription_link, String phone,String prescriptionType) {
        Name = name;
        Address = address;
        this.prescription_link = prescription_link;
        Phone = phone;
        prescription = "";
        this.prescriptionType = prescriptionType;
    }
}
