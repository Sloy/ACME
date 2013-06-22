package com.acme.model.examination;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.acme.model.AbstractPersistable;
import com.acme.model.exam.Question;


@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="ANSWER_TYPE")
public abstract class AnswerExam extends AbstractPersistable<Long>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3432554486856221113L;
	// -------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------
	private Double puntuation;
	@ManyToOne
	@JoinColumn(name="QUESTION_ID",referencedColumnName="id")
	protected Question question;
	private String text;
	@OneToOne
	private Register register;

	// -------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------
	public AnswerExam() {

	}

	public AnswerExam(Double puntuation, Question question) {
		super();
		this.puntuation = puntuation;
		this.question = question;
	}

	public AnswerExam(Question question) {
		super();
		this.question = question;
	}

	// -------------------------------------------------------------
	// Getters & Setters
	// -------------------------------------------------------------
	
	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public void setPuntuation(Double puntuation) {
		this.puntuation = puntuation;
	}

	public Double getPuntuation() {
		return puntuation;
	}

	// -------------------------------------------------------------
	// Methods
	// -------------------------------------------------------------
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}


	public Register getRegister() {
		return register;
	}

	public void setRegister(Register register) {
		this.register = register;
	}

}
