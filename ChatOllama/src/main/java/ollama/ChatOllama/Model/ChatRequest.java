package ollama.ChatOllama.Model;

public class ChatRequest {

    Integer id;
    String message;

    

    public ChatRequest(Integer id, String message) {
        this.id = id;
        this.message = message;
    }
    public ChatRequest() {
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }    
}
