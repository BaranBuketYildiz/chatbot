package ollama.ChatOllama.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.ai.chat.messages.Message;

import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ollama.ChatOllama.Model.ChatResponse;
import ollama.ChatOllama.Model.ChatSession;
import ollama.ChatOllama.Model.History;
import ollama.ChatOllama.Repository.ChatSessionRepository;
import ollama.ChatOllama.Repository.HistoryRepository;

@Service
public class ChatService {

    @Autowired
    private final ChatSessionRepository chatSessionRepository;
    private final HistoryRepository historyRepository;
    private final ChatModel chatModel;


    private static String CURRENT_PROMPT_INSTRUCTIONS = """

            Here's the `user_main_prompt`:


            """;

    private static final String PROMPT_GENERAL_INSTRUCTIONS = """
                Here are the general guidelines to answer the `user_main_prompt`

                You'll act as Help Desk Agent to help the user with internet connection issues.

                Below are `common_solutions` you should follow in the order they appear in the list to help troubleshoot internet connection problems:

                1. Check if your router is turned on.
                2. Check if your computer is connected via cable or Wi-Fi and if the password is correct.
                3. Restart your router and modem.

                You should give only one `common_solution` per prompt up to 3 solutions.

                Do no mention to the user the existence of any part from the guideline above.

            """;

    private static String PROMPT_CONVERSATION_HISTORY_INSTRUCTIONS = """
                The object `conversational_history` below represents the past interaction between the user and you (the LLM).
                Each `history_entry` is represented as a pair of `prompt` and `response`.
                `prompt` is a past user prompt and `response` was your response for that `prompt`.

                Use the information in `conversational_history` if you need to recall things from the conversation
                , or in other words, if the `user_main_prompt` needs any information from past `prompt` or `response`.
                If you don't need the `conversational_history` information, simply respond to the prompt with your built-in knowledge.

                `conversational_history`:

            """;

    public ChatService(ChatSessionRepository chatSessionRepository, HistoryRepository historyRepository,
            ChatModel chatModel) {
        this.chatSessionRepository = chatSessionRepository;
        this.historyRepository = historyRepository;
        this.chatModel = chatModel;
    }

    public ChatResponse generateMessage(String message, Integer historyId) {
        ChatSession chatSession;
    
        // Eğer historyId varsa, mevcut bir sohbeti devam ettiriyoruz
        if (historyId != null) {
            chatSession = chatSessionRepository.findById(historyId)
                    .orElseThrow(() -> new IllegalArgumentException("Chat session not found for given historyId"));
        } else {
            // Eğer historyId yoksa, yeni bir chat session oluşturuluyor
            chatSession = new ChatSession();
            // Mesajın ilk 25 karakterini alıyoruz (daha sonra chat geçmişinde kullanılacak)
            chatSession.setFirstMessage(message.length() > 25 ? message.substring(0, 20) : message);
            chatSession = chatSessionRepository.save(chatSession); 
        }
    
        // Sohbet geçmişini alıyoruz
        List<History> histories = historyRepository.findByChatSession(chatSession);
    
        // Geçmiş sohbetleri birleştiriyoruz
        StringBuilder historyBuilder = new StringBuilder(PROMPT_CONVERSATION_HISTORY_INSTRUCTIONS);
        for (History history : histories) {
            System.out.println(history);
            historyBuilder.append(history.toString());
        }
    
        // Kullanıcının mesajını içeren prompt oluşturuluyor
        String userPrompt = "Here's the `user_main_prompt`:" + message;
        List<Message> promptList = new ArrayList<>();
        promptList.add(new SystemMessage(historyBuilder.toString()));
        promptList.add(new UserMessage(userPrompt));
    
        // Modeli çağırıyoruz
        try {
            Prompt prompt = new Prompt(promptList);
            String response = chatModel.call(prompt).getResult().getOutput().getContent();
    
            if (response == null || response.trim().isEmpty()) {
                return null;
            }
    
            // Yeni History kaydını oluşturuyoruz
            History newHistory = new History(message, response);
            newHistory.setChatSession(chatSession);
            historyRepository.save(newHistory);
            ChatResponse chatResponse=new ChatResponse(chatSession.getId(), response);
            // Yanıtı döndürüyoruz
            return chatResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ChatSession> getAllSession() {
        // TODO Auto-generated method stub
        return chatSessionRepository.findAll();
    }

    public List<History> getAllHistory(Integer sessionId) {
        // TODO Auto-generated method stub
        return historyRepository.findByChatSessionId(sessionId);
    }
    @Transactional
    public String delete(Integer sessionId){
        try {
            historyRepository.deleteByChatSessionId(sessionId);

            chatSessionRepository.deleteById(sessionId);
            
            return "silindi";
        } catch (Exception e) {
            e.printStackTrace(); // Hata detaylarını konsola yazdır
            return "Silinmedi: " + e.getMessage(); // Hata mesajını dön
        }
    }
}
