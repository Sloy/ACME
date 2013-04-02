package com.acme.model.certification;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.NumberFormat;

import com.acme.model.IntervalDate;
import com.acme.model.Office;
import com.acme.model.exam.Exam;
import com.acme.model.exam.ExamType;
import com.acme.model.examination.Examination;
import com.acme.model.examination.PreRegister;
import com.acme.model.geography.Language;
import com.acme.model.user.Company;
import com.acme.model.user.Customer;
import com.acme.model.user.Reviewer;
import com.acme.model.user.User;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;


@Entity
public class Certification {
	// -------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------
	private Integer id;
	
	@NotNull(message="certification.name.null")
	@NotBlank(message="certification.name.null")
	private String name;
	
	@NotNull(message="certification.description.null")
	@NotBlank(message="certification.description.null")
	private String description;
	
	@NotNull(message="certification.cost.null")
	@Digits(integer=4,fraction=2,message="certification.cost.number")
	@Min(value = 0,message="certification.cost.negative")
	private Double cost;
	
	@NotNull(message="certification.pricepublic.null")
	@Digits(integer=4,fraction=2,message="certification.pricepublic.number")
	@Min(value = 0,message="certification.pricepublic.negative")
	private Double pricePublic;
	
	@NotNull(message="certification.validez.null")
	@NotBlank(message="certification.validez.null")
	private String validez;
	
	@NotNull(message="certification.company.null")
	private User company;
	
	@NotNull(message="certification.family.null")
	private FamilyProfessional familyProfessional;
	
	@NotNull(message="certification.calification.null")
	@Digits(integer=2,fraction=1,message="certification.calification.number")
	@Min(value = 0,message="certification.calification.negative")
	private Double requirementCalification;
	
	private List<String> requirements;
	
	private Set<Examination> examinations;
	
	private Set<PreRegister> preRegisters;
	
	private Set<Exam> exams;
	
	// -------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------
	public Certification() {
		super();
		this.requirements = Lists.newArrayList();
		this.examinations = Sets.newHashSet();
		this.preRegisters = Sets.newHashSet();
		this.exams = Sets.newHashSet();
	}

	public Certification(String name, String description, Double cost,
			Double pricePublic, String validez, User company,
			FamilyProfessional familyProfessional,
			List<String> requirements,
			Double requirementCalification) {
		super();
		this.name = name;
		this.description = description;
		this.cost = cost;
		this.pricePublic = pricePublic;
		this.validez = validez;
		this.company = company;
		this.familyProfessional = familyProfessional;
		this.requirements = requirements;
		this.exams = Sets.newHashSet();;
		this.preRegisters = Sets.newHashSet();
		this.examinations = Sets.newHashSet();
		this.requirementCalification = requirementCalification;
	}

	// -------------------------------------------------------------
	// Getters & Setters
	// -------------------------------------------------------------
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getValidez() {
		return validez;
	}

	public void setValidez(String validez) {
		this.validez = validez;
	}

	@ManyToOne
	public FamilyProfessional getFamilyProfessional() {
		return familyProfessional;
	}

	public void setFamilyProfessional(FamilyProfessional familyProfessional) {
		this.familyProfessional = familyProfessional;
	}

	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="requirement")
	public List<String> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<String> requirements) {
		this.requirements = requirements;
	}

	@OneToMany(mappedBy="certification",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	public Set<Examination> getExaminations() {
		return examinations;
	}

	public void setExaminations(Set<Examination> examinations) {
		this.examinations = examinations;
	}

	@OneToMany(mappedBy="certification",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	public Set<PreRegister> getPreRegisters() {
		return preRegisters;
	}

	public void setPreRegisters(Set<PreRegister> preRegisters) {
		this.preRegisters = preRegisters;
	}

	@OneToOne
	public User getCompany() {
		return company;
	}

	public void setCompany(User company) {
		this.company = company;
	}

	public Double getRequirementCalification() {
		return requirementCalification;
	}

	public void setRequirementCalification(Double requirementCalification) {
		this.requirementCalification = requirementCalification;
	}

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	// -------------------------------------------------------------
	// Methods
	// -------------------------------------------------------------

	

	public Double getPricePublic() {
		return pricePublic;
	}

	public void setPricePublic(Double pricePublic) {
		this.pricePublic = pricePublic;
	}

	@OneToMany(mappedBy="certification",cascade=CascadeType.ALL,orphanRemoval=true,targetEntity=Exam.class,fetch=FetchType.EAGER)
	public Set<Exam> getExams() {
		return exams;
	}

	public void setExams(Set<Exam> exams) {
		this.exams = exams;
	}

	public void addRequiremnt(String r) {
		this.requirements.add(r);
	}

	public void removeRequirement(String r) {
		this.requirements.remove(r);
	}
	
	public void removeRequirement(Integer r) {
		this.requirements.remove(this.requirements.get(r));
	}

	public void addExamination(Examination e) {
		this.examinations.add(e);
	}

	public void removeExamination(Examination e) {
		this.examinations.remove(e);
	}

	public void addPreRegister(PreRegister p) {
		if (this.preRegisters == null) {
			this.preRegisters = Sets.newHashSet();
		}
		this.preRegisters.add(p);
	}

	public void removePreRegister(PreRegister p) {
		this.preRegisters.remove(p);
	}

	public void addExam(Exam e) {
		if (this.exams == null) {
			this.exams = Sets.newHashSet();;
		}
		this.exams.add(e);
	}

	public void removeExam(Exam e) {
		this.exams.remove(e);
	}

	public Exam addExamToCertification(ExamType typeExam, Language language) {
		Exam temp = new Exam(typeExam, language);
		this.addExam(temp);
		temp.setCertification(this);
		return temp;
	}

	public PreRegister createPreRegisterCustomer(IntervalDate intervaloDeseado,
			Customer customer) {
		PreRegister temp = new PreRegister(intervaloDeseado, customer,this);
		this.addPreRegister(temp);
		customer.addPreRegister(temp);
		return temp;
	}

	@Transient
	public Exam getRandomExam() {
		int size = exams.size();
		Random rand = new Random();
		return (Exam) exams.toArray()[rand.nextInt(size)];
	}

	public Examination createNewExaminationForCertification(
			Date dateRealization, Date dateLimit, Integer maxCustomer,
			Integer minCustomer, Office place, Reviewer reviewer) {
		Examination temp = new Examination(dateRealization, dateLimit,
				maxCustomer, minCustomer, this, place, reviewer);
		this.addExamination(temp);
		return temp;
	}

	public void removePreRegisterCustomer(PreRegister pre) {
		this.removePreRegister(pre);
		pre.getCustomer().removePreRegister(pre);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Certification other = (Certification) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	

}