package petStore.controller;

/**
 * Created by Alexandru on 2016-11-12.
 */
public class StringResponseWrapper {
    private String response;

    public StringResponseWrapper(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
