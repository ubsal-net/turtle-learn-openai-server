package org.springframework.ai.openai.samples.helloworld.controller.data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PromptRequest {

    private String uuid;

    @NotNull @NotEmpty
    private String message;


}
