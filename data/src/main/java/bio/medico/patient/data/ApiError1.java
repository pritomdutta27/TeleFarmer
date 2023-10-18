package bio.medico.patient.data;

import com.skh.hkhr.util.NullRemoveUtil;

import java.util.List;

public class ApiError1 {

    private List<ApiError11> errors;

    public List<ApiError11> getError() {
        return NullRemoveUtil.getNotNull(errors);
    }

    public class ApiError11 {

        private Context1 context;
        private String type;
        private List<String> path;
        private String message;

        public Context1 getContext() {
            return context;
        }

        public String getType() {
            return type;
        }

        public List<String> getPath() {
            return path;
        }

        public String getMessage() {
            return NullRemoveUtil.getNotNull(message);
        }
    }

    public static class Context1 {
        private String key;
        private String value;
        private String label;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }
    }

}
