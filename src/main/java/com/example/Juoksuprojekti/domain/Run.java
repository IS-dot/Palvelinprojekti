package com.example.Juoksuprojekti.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Run {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String type;
	private double distance;

	private double totalDist;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private LocalDate perfDay;

	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	public Run() {
	}

	public Run(String type, double distance, LocalDate perfDay, User user) {
		super();
		this.type = type;
		this.distance = distance;
		this.perfDay = perfDay;
		this.totalDist = 0;
		this.user = user;
	}

	// metodi juoksumatkan laskemista varten
	public void juokse(double totalDist) {
		this.totalDist += totalDist;
	}

	// metodi päivämäärän konvertoimista varten
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public void muutaPaiva(String para) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate newDate = LocalDate.parse(para, formatter);

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getTotalDist() {
		return totalDist;
	}

	public void setTotalDist(double totalDist) {
		this.totalDist = totalDist;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDate getPerfDay() {
		return perfDay;
	}

	public void setPerfDay(LocalDate perfDay) {
		this.perfDay = perfDay;
	}

	@Override
	public String toString() {
		return "Run [id=" + id + ", type=" + type + ", distance=" + distance + ", perfDay=" + perfDay + ", totalDist="
				+ totalDist + ", user=" + user + "]";
	}

}