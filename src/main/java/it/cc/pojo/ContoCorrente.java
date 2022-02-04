package it.cc.pojo;

import java.util.Objects;

public class ContoCorrente {
	
	private String iban;	//sarà la chiave primaria
	private String data;
	private double saldo;
	private String intestatario;
	
	//getter e setter
	public String getIban() {
		return iban;
	}
	public String getData() {
		return data;
	}
	public double getSaldo() {
		return saldo;
	}
	public String getIntestatario() {
		return intestatario;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public void setData(String data) {
		this.data = data;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	public void setIntestatario(String intestatario) {
		this.intestatario = intestatario;
	}
	@Override
	public int hashCode() {
		return Objects.hash(iban);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContoCorrente other = (ContoCorrente) obj;
		return Objects.equals(iban, other.iban);
	}
}