package com.acme.controllers.user;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.acme.model.certification.Certification;
import com.acme.model.geography.City;
import com.acme.model.geography.Country;
import com.acme.model.geography.State;
import com.acme.model.user.Admin;
import com.acme.model.user.Company;
import com.acme.model.user.Customer;
import com.acme.model.user.Reviewer;
import com.acme.model.user.User;
import com.acme.model.user.UserType;
import com.acme.model.user.Worker;
import com.acme.services.CertificationService;
import com.acme.services.GeographyService;
import com.acme.services.UserService;

@Controller
@RequestMapping({ "/customer/user" })
public class CustomerUserController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private UserService serviceuser;

	@Autowired
	private GeographyService servicegeography;

	@RequestMapping(value = "/country/{id}/states", method = RequestMethod.GET)
	public @ResponseBody
	Set<State> getStatesByCountryId(@PathVariable Long id, Model model) {
		return servicegeography.getCountryById(id).getStates();
	}

	@RequestMapping(value = "/state/{id}/cities", method = RequestMethod.GET)
	public @ResponseBody
	Set<City> getCitiesByStateId(@PathVariable Long id, Model model) {
		return servicegeography.getStateById(id).getCities();
	}

	@ModelAttribute("allCountry")
	public List<Country> getAllCountry() {
		return servicegeography.getAllCountry();
	}

	// Devuelve el usuario con id indicado en la URL
	@RequestMapping(value = "/edit/id/{iduser}", method = RequestMethod.GET)
	public String gettUser(@PathVariable Long iduser, Model model,
			RedirectAttributes redirectAttrs, HttpServletRequest response) {
		User u = serviceuser.getUserById(iduser);
		if (u == null) {
			redirectAttrs.addFlashAttribute("error", "usuario.noexist");
			response.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE,
					HttpStatus.NOT_FOUND);
			return "redirect:/acme/user/create";
		}
		model.addAttribute("statesInCountry", u.getCountry().getStates());
		model.addAttribute("citiesByState", u.getState().getCities());
		model.addAttribute("user", u);
		model.addAttribute("isNew", false);
		model.addAttribute("activeMenu", "user");
		return "/user/oneUser";
	}

	// Modifica el usuario con el id dado
		@RequestMapping(value = "/edit/id/{iduser}", method = RequestMethod.POST)
		public String editUser(@PathVariable Long iduser,
				@ModelAttribute("user") @Valid User u, Model model,
				RedirectAttributes redirectAttrs, HttpServletRequest response,
				BindingResult result) {
			// Comprueba si hay errores de validacion
			if (result.hasErrors()) {
				model.addAttribute("isNew", false);
				model.addAttribute("user", u);
				model.addAttribute("activeMenu", "user");
				return "/user/oneUser";
			}
			User user = serviceuser.getUserById(iduser);
			if (u == null) {
				redirectAttrs.addFlashAttribute("error", "usuario.noexist");
				response.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE,
						HttpStatus.NOT_FOUND);
				return "redirect:/acme/user/create";
			}
			user.setValuesFromUser(u);
			user.setCity(servicegeography.getCityById(user.getCity().getId()));
			user.setState(servicegeography.getStateById(user.getState().getId()));
			user.setCountry(servicegeography.getCountryById(user.getCountry()
					.getId()));
			User uMod = serviceuser.updateUser(user);
			model.addAttribute("user", uMod);
			model.addAttribute("isNew", false);
			model.addAttribute("activeMenu", "user");
			model.addAttribute("info", "user.modify");
			return "/user/oneUser";
		}
}
