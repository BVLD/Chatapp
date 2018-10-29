package e.geertvanleuven.chatapp;

public class messages {

    private String message, type;
    private Long time;
    private Boolean seen;
    private String from;


    private messages() {
        //EMPTY CONSTRUCTER NEEDED
    }

    public messages(String message, String type, Long time, Boolean seen, String from) {
        this.message = message;
        this.type = type;
        this.time = time;
        this.seen = seen;
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public Long getTime() {
        return time;
    }

    public Boolean getSeen() {
        return seen;
    }

    public String getFrom() {
        return from;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
