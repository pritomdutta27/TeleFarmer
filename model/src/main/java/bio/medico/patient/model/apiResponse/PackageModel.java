package bio.medico.patient.model.apiResponse;


import java.util.ArrayList;
import java.util.List;

import bio.medico.patient.model.NullRemoveUtil;

public class PackageModel {
    private ArrayList<Package> packages;

    public List<Package> getPackages() {
        return NullRemoveUtil.getNotNull(packages);
    }

    public class Item {
        private String title;
        private String duration;
        private int regularPrice;
        private int price;
        private String currency;
        private int discount;
        private boolean isLifeCoverage;
        private int liveCoverageAmount;
        private String liveCoverageCurrency;
        private List<String> description;
        private String url;
        private List<PaymentMethord> paymentMethords;

        public boolean isSeeMoreEnable = false; //for ui


        public String getTitle() {
            return NullRemoveUtil.getNotNull(title);
        }

        public String getDuration() {
            return NullRemoveUtil.getNotNull(duration);
        }

        public int getRegularPrice() {
            return NullRemoveUtil.getNotNull(regularPrice);
        }

        public int getPrice() {
            return NullRemoveUtil.getNotNull(price);
        }

        public String getCurrency() {
            return NullRemoveUtil.getNotNull(currency);
        }

        public int getDiscount() {
            return NullRemoveUtil.getNotNull(discount);
        }

        public boolean isLifeCoverage() {
            return isLifeCoverage;
        }

        public int getLiveCoverageAmount() {
            return NullRemoveUtil.getNotNull(liveCoverageAmount);
        }

        public String getLiveCoverageCurrency() {
            return NullRemoveUtil.getNotNull(liveCoverageCurrency);
        }

        public List<String> getDescription() {
            return NullRemoveUtil.getNotNull(description);
        }

        public String getUrl() {
            return NullRemoveUtil.getNotNull(url);
        }

        public List<PaymentMethord> getPaymentMethords() {
            return NullRemoveUtil.getNotNull(paymentMethords);
        }


    }

    public class Package {
        private String uuid;
        private String title;
        private String subTitle;
        private String description;
        private String imageUrl;
        private List<Item> items;


        public String getUuid() {
            return NullRemoveUtil.getNotNull(uuid);
        }

        public String getTitle() {
            return NullRemoveUtil.getNotNull(title);
        }

        public String getSubTitle() {
            return NullRemoveUtil.getNotNull(subTitle);
        }

        public String getDescription() {
            return NullRemoveUtil.getNotNull(description);
        }

        public String getImageUrl() {
            return NullRemoveUtil.getNotNull(imageUrl);
        }

        public List<Item> getItems() {
            return NullRemoveUtil.getNotNull(items);
        }
    }

    public class PaymentMethord {
        private String title;
        private String imageUrl;
        private boolean isEnable;
        private String billingUrl;
        private boolean isLoad;

        public String getTitle() {
            return NullRemoveUtil.getNotNull(title);
        }

        public String getImageUrl() {
            return NullRemoveUtil.getNotNull(imageUrl);
        }

        public boolean isEnable() {
            return isEnable;
        }

        public String getBillingUrl() {
            return NullRemoveUtil.getNotNull(billingUrl);
        }

        public boolean isLoad() {
            return isLoad;
        }
    }


}