package bio.medico.patient.model.apiResponse;

import bio.medico.patient.model.NullRemoveUtil;

import java.util.List;

/**
 * Created by Samiran Kumar on 29,November,2022
 **/
public class ResponsePackList {

    private List<PackageModel.Item> items;

    public List<PackageModel.Item> getPackagesList() {
        return NullRemoveUtil.getNotNull(items);
    }


}


