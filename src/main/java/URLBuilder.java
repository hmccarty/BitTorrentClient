public class URLBuilder {
    private String URL;

    public URLBuilder() {
        URL = "";
    }

    public URLBuilder(String URL) {
        this.URL = URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public URLBuilder addParameter(String key, String value) {
        URL += (URL.contains("?")) ? "&" : "?";
        URL += String.format("%s=%s", key, value);
        return this;
    }

    public String getURL() {
        return URL;
    }
}
