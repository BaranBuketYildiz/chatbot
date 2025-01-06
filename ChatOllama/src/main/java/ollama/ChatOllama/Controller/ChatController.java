package ollama.ChatOllama.Controller;

import java.util.List;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.boot.autoconfigure.jms.JmsProperties.Listener.Session;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ollama.ChatOllama.Model.ChatRequest;
import ollama.ChatOllama.Model.ChatResponse;
import ollama.ChatOllama.Model.ChatSession;
import ollama.ChatOllama.Model.History;
import ollama.ChatOllama.Service.ChatService;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // İzin verilen frontend URL'si
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/sor")
    public ResponseEntity<ChatResponse> prompt(@RequestBody ChatRequest request) {
        System.out.println("Received Message: " + request.getMessage() + " for session ID: " + request.getId());
        ChatResponse response= chatService.generateMessage(request.getMessage(), request.getId());
        if(response==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/sessions")
    public ResponseEntity<List<ChatSession>> getAllSessions(){
        return ResponseEntity.status(HttpStatus.OK).body(chatService.getAllSession());
    }
    @GetMapping("/history/{sessionId}")
    public ResponseEntity<List<History>> getAllHistory(@PathVariable Integer sessionId){
        List<History> histories=chatService.getAllHistory(sessionId);
        return ResponseEntity.ok().body(histories);
    }
    @DeleteMapping("/{sessionId}")
    public ResponseEntity <String> deleteSession(@PathVariable Integer sessionId){
            String result=chatService.delete(sessionId);
            return ResponseEntity.ok().body(result);

    }


}
