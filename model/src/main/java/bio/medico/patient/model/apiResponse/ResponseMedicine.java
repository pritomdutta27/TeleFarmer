package bio.medico.patient.model.apiResponse;


import java.util.List;

import bio.medico.patient.model.NullRemoveUtil;

public class ResponseMedicine {
    private boolean has_more;
    private List<MedicineModel> products;
    private List<DeliveryInfo> deliveryInfo;

    public boolean isHas_more() {
        return has_more;
    }

    public List<MedicineModel> getProducts() {
        return NullRemoveUtil.getNotNull(products);
    }

    public List<DeliveryInfo> getDeliveryInfo() {
        return NullRemoveUtil.getNotNull(deliveryInfo);
    }

    public static class MedicineModel {
        private String generic_name;
        private String Description;
        private String location;
        private String Brand;
        private int stock;
        private String unit;
        private double product_price_after_discount;
        private int vendor_discount;
        private int Discount;
        private double product_price;
        private String image;
        private String product_name;
        private int id;
        private int item = 1;

        public int getItem() {
            return item;
        }

        public void setItem(int item) {
            this.item = item;
        }

        public String getGeneric_name() {
            return NullRemoveUtil.getNotNull(generic_name);
        }

        public String getDescription() {
            return Description;
        }

        public String getLocation() {
            return location;
        }

        public String getBrand() {
            return Brand;
        }

        public int getStock() {
            return stock;
        }

        public String getUnit() {
            return NullRemoveUtil.getNotNull(unit);
        }

        public double getProduct_price_after_discount() {
            return product_price_after_discount;
        }

        public int getVendor_discount() {
            return vendor_discount;
        }

        public int getDiscount() {
            return Discount;
        }

        public double getProduct_price() {
            return product_price;
        }

        public String getImage() {
            return NullRemoveUtil.getNotNull(image);
        }

        public String getProduct_name() {
            return NullRemoveUtil.getNotNull(product_name);
        }

        public int getId() {
            return id;
        }
    }

    public static class DeliveryInfo {
        private String type;
        private String charge;
        private double amount;
        private boolean isSelected; // for ui

        public String getType() {
            return NullRemoveUtil.getNotNull(type);
        }

        public String getCharge() {
            return NullRemoveUtil.getNotNull(charge);
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public double getAmount() {
            return amount;
        }
    }
}
