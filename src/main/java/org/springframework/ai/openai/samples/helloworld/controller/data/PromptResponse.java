package org.springframework.ai.openai.samples.helloworld.controller.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PromptResponse {

    private String uuid;
    private String message;

}
