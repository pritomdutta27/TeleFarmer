package bio.medico.patient.model.apiResponse;

import java.util.List;

import bio.medico.patient.model.apiResponse.ResponseVendorInfoItem;

public class ResponseCommonVendor {
	private List<ResponseVendorInfoItem> vendorInfo;

	public List<ResponseVendorInfoItem> getVendorInfo(){
		return vendorInfo;
	}
}