package ollama.ChatOllama.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ollama.ChatOllama.Model.ChatSession;
import ollama.ChatOllama.Model.History;

public interface ChatSessionRepository extends JpaRepository<ChatSession,Integer> {


} 
    

