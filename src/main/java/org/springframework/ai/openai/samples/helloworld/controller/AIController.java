package org.springframework.ai.openai.samples.helloworld.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.samples.helloworld.controller.data.PromptRequest;
import org.springframework.ai.openai.samples.helloworld.controller.data.PromptResponse;
import org.springframework.ai.openai.samples.helloworld.service.AIService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AIController {

	private final AIService aiService;


	// chatGPT api key 변경필요
	@GetMapping("/ai")
	@Operation(summary = "프롬프트 요청", description = "프롬프트 요청입니다. message : 문자열로 사용자의 요청을 담고, uuid : 사용자의 세션번호입니다. 첫 대화시, uuid를 비우고 요청하세요")
	PromptResponse completion(@RequestParam(value = "message", defaultValue = "인사한번해드려") String message, @RequestParam(value = "uuid") String uuid) {

		return aiService.sendPrompt(new PromptRequest(uuid, message));

	}

	@GetMapping("/exit")
	public String exitChatSession(@RequestParam(value = "uuid") String uuid){
		aiService.exit(uuid);
		return "청소성공";
	}



}
