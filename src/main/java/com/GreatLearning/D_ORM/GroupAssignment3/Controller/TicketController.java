package com.GreatLearning.D_ORM.GroupAssignment3.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.GreatLearning.D_ORM.GroupAssignment3.Entity.Ticket;
import com.GreatLearning.D_ORM.GroupAssignment3.Service.TicketingService;

@Controller
public class TicketController {

	@Autowired
	TicketingService ticketService;

	@GetMapping("/homePage")
	public String homePage(Model model) {
		List<Ticket> tickets = ticketService.getAllTickets();
		model.addAttribute("listOfTickets", tickets);
		return "tickets";
	}

	@GetMapping("/ticket/edit/{id}")
	public String editTicket(@PathVariable int id, Model model) {
		Ticket ticketObject = ticketService.getTicketById(id);
		model.addAttribute("ticketId", ticketObject);
		return "edit_ticket";
	}

	@PostMapping("/ticket/{id}")
	public String updateTicket(@PathVariable int id, @ModelAttribute("ticket") Ticket newValues) {
		Ticket ticket = ticketService.getTicketById(id);
		ticket.setTitle(newValues.getTitle());
		ticket.setDescription(newValues.getDescription());
		ticket.setCreateDate(newValues.getCreateDate());
		ticket.setContent(newValues.getContent());
		ticketService.saveTicket(ticket);
		return "redirect:/homePage";
	}

	@GetMapping("/ticket/new")
	public String createTicket(Model model) {
		Ticket ticket = new Ticket();
		model.addAttribute("blankTicket", ticket);
		return "create_ticket";
	}

	@PostMapping("/newTicket")
	public String newTicket(@ModelAttribute("ticket") Ticket newTicket) {
		ticketService.saveTicket(newTicket);
		return "redirect:/homePage";
	}

	@GetMapping("/ticket/delete/{id}")
	public String deleteTicket(@PathVariable int id) {
		ticketService.deleteTicketById(id);
		return "redirect:/homePage";
	}

	@GetMapping("/ticket/view/{id}")
	public String viewTicket(@PathVariable int id, Model model) {
		Ticket ticket = ticketService.getTicketById(id);
		model.addAttribute("ticketDetail", ticket);
		return "viewTicketDetails";
	}

	@PostMapping("/ticket/search")
	public String searchTicket(@RequestParam("query") String query, Model model) {

		System.out.println(query);
		List<Ticket> tickets = ticketService.getAllTickets();
		List<Ticket> searchResults = null;
		System.out.println("List of Tickets : " + tickets);

		for (Ticket t1 : tickets) {

			if (t1.getTitle().trim().equalsIgnoreCase(query)) {
				searchResults = ticketService.getTicketByTitle(query);
				System.out.println("title = " + t1);
				break;
			} else if (t1.getDescription().trim().equalsIgnoreCase(query)) {
				searchResults = ticketService.getTicketByDescription(query);
				System.out.println("description = " + t1);
				break;
			}
		}

		if (query.isEmpty())
			return "redirect:/homePage";

		System.out.println("Search Results: " + searchResults);
		model.addAttribute("searchResults", searchResults);
		return "searchresults";
	}

}
