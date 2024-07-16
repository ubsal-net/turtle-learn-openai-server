package org.springframework.ai.openai.samples.helloworld.service;

import io.netty.util.internal.ObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.samples.helloworld.controller.data.PromptRequest;
import org.springframework.ai.openai.samples.helloworld.controller.data.PromptResponse;
import org.springframework.ai.openai.samples.helloworld.infrastructure.ChatHistory;
import org.springframework.ai.openai.samples.helloworld.infrastructure.ChatHistoryDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



@Service
@Slf4j
@RequiredArgsConstructor
public class AIService {

    private final ChatClient chatClient;

    private final ChatHistoryDataRepository chatHistoryDataRepository;



    public PromptResponse sendPrompt(PromptRequest request){

        if(ObjectUtils.isEmpty(request.getUuid())){

            PromptResponse assistant = Completion(request);

            return PromptResponse.builder()
                    .uuid(assistant.getUuid())
                    .message(assistant.getMessage())
                    .build();


        }else{

            PromptResponse assistant = Completion(request.getUuid(), request);

            return PromptResponse.builder()
                    .uuid(assistant.getUuid())
                    .message(assistant.getMessage())
                    .build();

        }



    }


    public PromptResponse Completion(PromptRequest request){

        String uuid = UUID.randomUUID().toString();

        ChatResponse assistant = chatClient.prompt()
                .options(OpenAiChatOptions.builder().withModel("gpt-4o").build())
                .user(request.getMessage())
                .call()
                .chatResponse();


        chatHistoryDataRepository.save(ChatHistory.builder()
                .assistant_message(assistant.getResult().getOutput().getContent())
                .user_message(request.getMessage())
                .uuid(uuid)
                .build());

        return new PromptResponse(uuid, assistant.getResult().getOutput().getContent());
    }


    public PromptResponse Completion(String uuid , PromptRequest request){


        ChatHistory history = chatHistoryDataRepository.findByUuid(request.getUuid());

        log.info("usermessage:{}", history.getUser_message());
        log.info("historymessage:{}", history.getAssistant_message());

        ChatResponse assistant = chatClient.prompt()
                .options(OpenAiChatOptions.builder().withModel("gpt-4o").build())
                .user(request.getMessage())
                .messages(
                        new UserMessage(history.getUser_message()),
                        new AssistantMessage(history.getAssistant_message())
                )
                .call()
                .chatResponse();



        chatHistoryDataRepository.save(ChatHistory.builder()
                .assistant_message(assistant.getResult().getOutput().getContent())
                .user_message(request.getMessage())
                .uuid(request.getUuid())
                .build());


        history.update(request.getMessage(), assistant.getResult().getOutput().getContent());


        return new PromptResponse(uuid, assistant.getResult().getOutput().getContent());

    }



    // 세션 종료

    // Transactional 없으니까 발생
    //  No EntityManager with actual transaction available for current thread - cannot reliably process 'remove' call
    @Transactional
    public void exit(String uuid){
        chatHistoryDataRepository.deleteAllByUuid(uuid);
    }


}
