package dev.jeremidigitallab.wimpychatty.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import dev.jeremidigitallab.wimpychatty.entity.Chat;

public interface ChatRepository extends PagingAndSortingRepository<Chat,String> {

	List<Chat> findByConnectedId(String connectedId);
}
