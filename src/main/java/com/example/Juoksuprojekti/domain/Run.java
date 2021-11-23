package com.example.Juoksuprojekti.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Run {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String type;
	private double distance;
	private String starttime;
	private String endtime;
	private double totalDist;

	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	public Run() {
	}

	public Run(String type, double distance, String starttime, String endtime, User user) {
		super();
		this.type = type;
		this.distance = distance;
		this.starttime = starttime;
		this.endtime = endtime;
		this.totalDist = 0;
		this.user = user;
	}

	// metodi juoksumatkan laskemista varten
	public void juokse(double totalDist) {
		this.totalDist += totalDist;
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

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
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

	@Override
	public String toString() {
		return "Run [id=" + id + ", type=" + type + ", distance=" + distance + ", starttime=" + starttime + ", endtime="
				+ endtime + ", totalDist=" + totalDist + ", user=" + user + "]";
	}

}