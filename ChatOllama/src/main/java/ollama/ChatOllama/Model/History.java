package ollama.ChatOllama.Model;

import jakarta.persistence.*;

@Entity
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "chat_session_id", nullable = false)
    private ChatSession chatSession;  // ChatSession ile ilişki

    @Column(length = 5000)
    private String prompt;  // Kullanıcıdan gelen mesaj

    @Column(length = 5000)
    private String response;  // Sistemin yanıtı

    // Default constructor
    public History() {
    }

    // Constructor with parameters
    public History(String prompt, String response) {
        this.prompt = prompt;
        this.response = response;
    }

    // Getter and Setter for id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // Getter and Setter for chatSession
    public ChatSession getChatSession() {
        return chatSession;
    }

    public void setChatSession(ChatSession chatSession) {
        this.chatSession = chatSession;
    }

    // Getter and Setter for prompt
    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    // Getter and Setter for response
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    // toString method for custom string output
    @Override
    public String toString() {
        return String.format("""
                        `history_entry`:
                            `prompt`: %s
                            `response`: %s
                        -----------------
                       \n
            """, prompt, response);
    }
}
