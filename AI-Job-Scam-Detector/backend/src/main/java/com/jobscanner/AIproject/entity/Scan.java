package com.jobscanner.AIproject.entity;



import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="scans")
public class Scan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    

    @Column(columnDefinition = "TEXT")
    private String inputText;
    
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

//    @Override
//	public String toString() {
//		return "Scan [id=" + id + ", userId=" + userId + ", inputText=" + inputText + ", riskScore=" + riskScore
//				+ ", result=" + result + ", scanDate=" + scanDate + "]";
//	}

	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Scan [id=" + id + ", userId="  + ", inputText=" + inputText + ", user=" + user + ", riskScore="
				+ riskScore + ", result=" + result + ", scanDate=" + scanDate + "]";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	

	public String getInputText() {
		return inputText;
	}

	public void setInputText(String inputText) {
		this.inputText = inputText;
	}

	public Integer getRiskScore() {
		return riskScore;
	}

	public void setRiskScore(Integer riskScore) {
		this.riskScore = riskScore;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public LocalDateTime getScanDate() {
		return scanDate;
	}

	public void setScanDate(LocalDateTime scanDate) {
		this.scanDate = scanDate;
	}

	private Integer riskScore;

    private String result;

    private LocalDateTime scanDate;
}
