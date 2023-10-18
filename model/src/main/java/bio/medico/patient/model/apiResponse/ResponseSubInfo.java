package bio.medico.patient.model.apiResponse;

import bio.medico.patient.model.NullRemoveUtil;

import java.util.List;

/**
 * Created by Samiran Kumar on 02,August,2022
 **/

public class ResponseSubInfo {

    private SubInfo subInfo;
    private boolean isSubscribe;
    private FreeCallInfo freeCallinfo;


    public SubInfo getSubInfo() {
        return subInfo;
    }

    public boolean getIsSubscribe() {
        return isSubscribe;
    }

    public FreeCallInfo getFreeCallinfo() {
        return freeCallinfo;
    }

    public static class SubInfo {
        private PackInfo packInfo;
        private String expiryDate;
        private String expiryDateTitle;
        private String uuid;
        private String paymentId;
        private String paymentType;
        private String msisdn;

        public PackInfo getPackInfo() {
            return packInfo;
        }

        public String getExpiryDate() {
            return NullRemoveUtil.getNotNull(expiryDate);
        }

        public String getExpiryDateTitle() {
            return NullRemoveUtil.getNotNull(expiryDateTitle);
        }

        public String getUuid() {
            return NullRemoveUtil.getNotNull(uuid);
        }

        public String getPaymentId() {
            return NullRemoveUtil.getNotNull(paymentId);
        }

        public String getPaymentType() {
            return NullRemoveUtil.getNotNull(paymentType);
        }

        public String getMsisdn() {
            return NullRemoveUtil.getNotNull(msisdn);
        }
    }

    public static class PackInfo {
        private String unsubUrl;
        private String billingGateway;
        private List<String> packDetails;
        private String packDetailsTitle;
        private CorporateInfo corporateInfo;
        private boolean isCorporate;
        private boolean isLifeCoverage;
        private String validitySlug;
        private String validity;
        private String price;
        private String packName;
        private String packType;

        public String getUnsubUrl() {
            return NullRemoveUtil.getNotNull(unsubUrl);
        }

        public String getBillingGateway() {
            return NullRemoveUtil.getNotNull(billingGateway);
        }

        public List<String> getPackDetails() {
            return packDetails;
        }

        public String getPackDetailsTitle() {
            return NullRemoveUtil.getNotNull(packDetailsTitle);
        }

        public CorporateInfo getCorporateInfo() {
            return corporateInfo;
        }

        public boolean getIsCorporate() {
            return isCorporate;
        }

        public boolean getIsLifeCoverage() {
            return isLifeCoverage;
        }

        public String getValiditySlug() {
            return NullRemoveUtil.getNotNull(validitySlug);
        }

        public String getValidity() {
            return NullRemoveUtil.getNotNull(validity);
        }

        public String getPrice() {
            return NullRemoveUtil.getNotNull(price);
        }

        public String getPackName() {
            return NullRemoveUtil.getNotNull(packName);
        }

        public String getPackType() {
            return NullRemoveUtil.getNotNull(packType);
        }
    }

    public static class CorporateInfo {
        private String childCompany;
        private String parentCompany;

        public String getChildCompany() {
            return NullRemoveUtil.getNotNull(childCompany);
        }

        public String getParentCompany() {
            return NullRemoveUtil.getNotNull(parentCompany);
        }
    }


    public static class FreeCallInfo {
        private int callDuration;
        private boolean eligibility;
        private int callLimit;
        private String callLimitType;
        private boolean callLimitStatus;


        public int getCallDuration() {
            return callDuration;

        }

        public boolean isEligibility() {
            return eligibility;
        }

        public int getCallLimit() {
            return callLimit;
        }

        public String getCallLimitType() {
            return NullRemoveUtil.getNotNull(callLimitType);
        }

        public boolean isCallLimitStatus() {
            return callLimitStatus;
        }

        public boolean isCallLimitStatuasExpire() {
            if (!isCallLimitStatus()) {
                return isCallLimitStatus();
            }
            return false;
        }

    }



/*        "freeCallinfo": {
        "callDuration": 30,
                "eligibility": true,
                "callLimit": 2,
                "callLimitType": "everyday",
                "callLimitStatuas": false
    }*/
}
