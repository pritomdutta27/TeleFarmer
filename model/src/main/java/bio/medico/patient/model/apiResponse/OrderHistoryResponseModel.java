package bio.medico.patient.model.apiResponse;

import java.util.List;

public class OrderHistoryResponseModel {
    private List<OrderHistory> history;

    public List<OrderHistory> getHistory() {
        return history;
    }

    public static class OrderHistory {
        private List<Ordered_product> ordered_product;
        private String Created_At;
        private String Order_Status;
        private String Total_Price;
        private String Order_Type;
        private String Payment_Method;
        private String Address;
        private int id;

        public List<Ordered_product> getOrdered_product() {
            return ordered_product;
        }

        public String getCreated_At() {
            return Created_At;
            //return AppKey.getTime1(Created_At, AppKey.DATE_TIME_FORmMATE_6, AppKey.DATE_TIME_FORmMATE_2);
        }

        public String getOrder_Status() {
            return Order_Status;
        }

        public String getTotal_Price() {
            return Total_Price;
        }

        public String getOrder_Type() {
            return Order_Type;
        }

        public String getPayment_Method() {
            return Payment_Method;
        }

        public String getAddress() {
            return Address;
        }

        public int getId() {
            return id;
        }
    }

    public static class Ordered_product {
        private int Quantity;
        private int Product_id;

        public int getQuantity() {
            return Quantity;
        }

        public int getProduct_id() {
            return Product_id;
        }
    }
}
