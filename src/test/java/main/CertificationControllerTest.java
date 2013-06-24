package main;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.acme.model.certification.Certification;
import com.acme.model.certification.FamilyProfessional;
import com.acme.model.geography.City;
import com.acme.model.geography.Country;
import com.acme.model.geography.State;
import com.acme.model.user.Admin;
import com.acme.model.user.Company;
import com.acme.model.user.Customer;
import com.acme.model.user.User;
import com.acme.model.user.UserType;
import com.acme.repository.CertificationRepository;
import com.acme.repository.CountryRepository;
import com.acme.repository.ExaminationRepository;
import com.acme.repository.UserRepository;
import com.acme.services.CertificationService;
import com.acme.services.UserService;
import com.google.common.collect.Lists;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/acme-servlet.xml")
public class CertificationControllerTest {

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private CertificationService certrep;

	@Autowired
	private UserService serviceuser;

	@Autowired
	private CountryRepository countryrep;

	private MockMvc mockMvc;

	private static FamilyProfessional fam1;

	private static User mCompany;

	private static Certification cert1;

	private static boolean first = true;

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
				.alwaysExpect(status().isOk()).build();
		if (first) {
			first = false;
			fam1 = new FamilyProfessional("Idiomas");
			fam1 = certrep.createFamilyProfessional(fam1);

			List<String> requisitos = Lists
					.newArrayList("El aspirante debera de superar cada apartado del examen");

			// Creamos una serie de paises
			Country c1 = new Country();
			c1.setName("Italia");
			State c = new State();
			c.setName("Lazio");
			c.createCity("Roma");
			City bomarzo = new City();
			bomarzo.setName("Bomarzo");
			c.getCities().add(bomarzo);
			c1.getStates().add(c);
			countryrep.save(c1);

			// Creamos un usuario con rol de company, customer y admin
			Company comp = new Company();
			Customer cust = new Customer();
			Admin admin = new Admin();
			mCompany = new User();
			mCompany.setUsername("Microsoft");
			mCompany.setEmail("peprde@asdmsn.es");
			mCompany.setMobilephone("66666666");
			mCompany.setName("Biasdfll");
			mCompany.setSurname("Aloasdfnso");
			mCompany.setPassword("123456");
			mCompany.setPhone("34234234");
			mCompany.addRoleToUser(comp, UserType.ROLE_COMPANY);
			mCompany.setCity(bomarzo);
			mCompany.setState(c);
			mCompany.setCountry(c1);
			mCompany = serviceuser.createUser(mCompany);

			cert1 = new Certification(
					"Ingles C2",
					"Certificado de nivel B1 de idiomas para Inlges expedido por el Instituto de Idiomas de la Universidad de Sevilla",
					(Double) 15.0, (Double) 30.0, "No caduca", mCompany, fam1,
					requisitos, 5.0);
			cert1 = certrep.createCertification(cert1);
		}
	}

	@Test
	public void allCertification() throws Exception {
		String view = this.mockMvc.perform(get("/certification").accept(MediaType.TEXT_HTML))
				.andDo(print()).andExpect(status().isOk()).andReturn().getModelAndView().getViewName();
		assertEquals("El controlador redirige a la vista /certification/listCertification",view,"/certification/listCertification");
	}

	@Test
	public void oneCertification() throws Exception {
		String view = this.mockMvc
				.perform(
						get("/admin/certification/edit/id/" + cert1.getId()).accept(
								MediaType.TEXT_HTML)).andDo(print())
				.andExpect(status().isOk()).andReturn().getModelAndView().getViewName();
		assertEquals("El controlador redirige a la vista /certification/oneCertification",view,"/certification/oneCertification");
	}
	
	@Test
	public void allFamily() throws Exception {
		String view = this.mockMvc
				.perform(
						get("/admin/certification/family").accept(
								MediaType.TEXT_HTML)).andDo(print())
				.andExpect(status().isOk()).andReturn().getModelAndView().getViewName();
		assertEquals("El controlador redirige a la vista /certification/familyprofessional",view,"/certification/familyprofessional");
	}
}
