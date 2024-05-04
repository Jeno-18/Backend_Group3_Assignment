package com.GreatLearning.D_ORM.GroupAssignment3.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.GreatLearning.D_ORM.GroupAssignment3.Entity.Ticket;
import com.GreatLearning.D_ORM.GroupAssignment3.Repository.TicketRepository;

@Service
public class TicketingService {

	@Autowired
	TicketRepository ticketRepo;

	public List<Ticket> getAllTickets() {
		return ticketRepo.findAll();
	}

	public Ticket getTicketById(int id) {
		return ticketRepo.findById(id).get();
	}

	public void saveTicket(Ticket newTicket) {
		ticketRepo.save(newTicket);
	}

	public void deleteTicketById(int id) {
		ticketRepo.deleteById(id);
	}

	public List<Ticket> getTicketByTitle(String title) {
		Ticket searchTicket = new Ticket();
		searchTicket.setTitle(title);
		ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withMatcher("title", ExampleMatcher.GenericPropertyMatchers.exact())
				.withIgnorePaths("id", "createDate", "content", "description");
		Example<Ticket> searchResults = Example.of(searchTicket, exampleMatcher);

		return ticketRepo.findAll(searchResults);
	}

	public List<Ticket> getTicketByDescription(String description) {
		Ticket searchTicket = new Ticket();
		searchTicket.setDescription(description);
		ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withMatcher("description", ExampleMatcher.GenericPropertyMatchers.exact())
				.withIgnorePaths("id", "createDate", "content", "title");
		Example<Ticket> searchResults = Example.of(searchTicket, exampleMatcher);

		return ticketRepo.findAll(searchResults);
	}

}
