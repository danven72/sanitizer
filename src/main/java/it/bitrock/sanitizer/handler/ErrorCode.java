package it.bitrock.sanitizer.handler;

public interface ErrorCode {
    String BAD_REQUEST_ERROR_CODE = "1000";
    String getErrorCode();
    String getErrorMessage();

    static ErrorCode buildBadRequestErrorCode(String fieldName, String errorMessage) {
        return new ErrorCode() {
            @Override
            public String getErrorCode() {
                return BAD_REQUEST_ERROR_CODE;
            }

            @Override
            public String getErrorMessage() {
                return String.format("Error on input params: %s %s", fieldName, errorMessage);
            }
        };
    }

}
