package com.backend.as.glady.data.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "walletuser")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8505944918272390412L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer balance;

	private String category;
	
	@Column(name = "created_date")
	private LocalDate createdDate;

	@ManyToOne
	@JoinColumn(name = "id_user", referencedColumnName = "id")
	private User user;

}
