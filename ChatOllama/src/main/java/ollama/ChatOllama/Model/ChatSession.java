package ollama.ChatOllama.Model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
public class ChatSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 25)
    private String firstMessage;  // İlk mesajın 25 karakteri

    @OneToMany(mappedBy = "chatSession")
    @JsonIgnore
    private List<History> historyList;  // History ile ilişki

    public ChatSession(Integer id, String firstMessage, List<History> historyList) {
        this.id = id;
        this.firstMessage = firstMessage;
        this.historyList = historyList;
    }

    public ChatSession() {
    }

    // Constructor with parameters
    public ChatSession(String firstMessage) {
        this.firstMessage = firstMessage;
    }

    // Getter and Setter for id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // Getter and Setter for firstMessage
    public String getFirstMessage() {
        return firstMessage;
    }

    public void setFirstMessage(String firstMessage) {
        this.firstMessage = firstMessage;
    }

    // Getter and Setter for historyList
    public List<History> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<History> historyList) {
        this.historyList = historyList;
    }
}
