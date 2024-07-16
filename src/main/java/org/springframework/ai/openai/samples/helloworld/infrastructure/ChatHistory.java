package org.springframework.ai.openai.samples.helloworld.infrastructure;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "chat_history")
public class ChatHistory {

    @Id
    @Column(nullable = false, unique = false)
    private String uuid;

    @Column(columnDefinition = "TEXT")
    private String user_message;

    @Column(columnDefinition = "TEXT")
    private String assistant_message;


    public void update(String user_message, String assistant_message){
        this.user_message = user_message;
        this.assistant_message = assistant_message;
    }


}
