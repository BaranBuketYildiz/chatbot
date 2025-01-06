package ollama.ChatOllama.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ollama.ChatOllama.Model.ChatSession;
import ollama.ChatOllama.Model.History;

public interface HistoryRepository extends JpaRepository<History,Integer>{

    List<History> findByChatSessionId(Integer chatSessionId);
 List<History> findByChatSession(ChatSession chatSession);
void deleteByChatSessionId(Integer sessionId);


} 
