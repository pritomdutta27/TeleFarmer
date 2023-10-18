package bio.medico.patient.model.apiResponse;

import bio.medico.patient.model.NullRemoveUtil;

import java.util.ArrayList;
import java.util.List;

public class ResponseLabTestList {
    private List<ItemLabTest> items;

    public List<ItemLabTest> getItems() {
        return NullRemoveUtil.getNotNull(items);
    }

    public static class ItemLabTest {
        private String rev;
        private String id;
        private String title;
        private String uuid;
        private String createAt;
        private String updatedAt;

        public String getRev() {
            return rev;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return NullRemoveUtil.getNotNull(title);
        }

        public String getUuid() {
            return uuid;
        }

        public String getCreateAt() {
            return createAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }
    }


    public static List<String> getItem(List<ItemLabTest> itemLabTests) {
        List<String> list = new ArrayList<>();

        for (ItemLabTest itemLabTest : itemLabTests) {
            list.add(itemLabTest.getTitle());
        }

        return list;
    }

}