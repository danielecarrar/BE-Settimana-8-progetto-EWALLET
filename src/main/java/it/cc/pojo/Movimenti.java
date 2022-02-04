package it.cc.pojo;

public class Movimenti {
	
	private String ibanMov;
	private double importo;
	private String tipo;
	private double saldo;
	
	//getter e setter
	
	public String getIbanMov() {
		return ibanMov;
	}
	public double getImporto() {
		return importo;
	}
	public String getTipo() {
		return tipo;
	}
	public void setIbanMov(String ibanMov) {
		this.ibanMov = ibanMov;
	}
	public void setImporto(double importo) {
		this.importo = importo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	@Override
	public String toString() {
		return "Movimenti [ibanMov=" + ibanMov + ", importo=" + importo + ", tipo=" + tipo + ", saldo=" + saldo + "]";
	}	
}