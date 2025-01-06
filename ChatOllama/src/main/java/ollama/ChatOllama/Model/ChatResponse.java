package ollama.ChatOllama.Model;

public class ChatResponse {
    
    Integer id;
    String response;
    ChatSession session;
    public ChatResponse(Integer id, String response) {
        this.id = id;
        this.response = response;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getResponse() {
        return response;
    }
    public void setResponse(String response) {
        this.response = response;
    }
    public ChatSession getSession() {
        return session;
    }
    public void setSession(ChatSession session) {
        this.session = session;
    }
}
