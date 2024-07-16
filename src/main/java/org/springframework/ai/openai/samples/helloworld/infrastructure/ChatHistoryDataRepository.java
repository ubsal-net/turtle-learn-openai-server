package org.springframework.ai.openai.samples.helloworld.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatHistoryDataRepository extends JpaRepository<ChatHistory , Long> {

    ChatHistory findByUuid(String uuid);

    void deleteAllByUuid(String uuid);

}
