package org.springframework.ai.openai.samples.helloworld.controller.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RandomQuestionResponse {


    private String question;

    private List<String> selections;

    private String answer;



}
