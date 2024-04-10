package classes;

import java.util.Date;

import util.Util;

public abstract class Pessoa {
	
	private String nome;
	private String cpf;
	private Date data_nascimento;
	
	public Pessoa(String nome, String cpf, Date data_nascimento) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.data_nascimento = data_nascimento;
		
	}
	
	public Pessoa() {
		super();
	}

	public String getNome() {
		return nome;
	}
	public String getCpf() {
		return cpf;
	}
	public Date getData_nascimento() {
		return data_nascimento;
	}
	
	@Override
	public String toString() {
		return "Nome: " + nome + " \nCPF: " + Util.formatarCPF(cpf) + " \nData de nascimento: " + Util.novaDT(data_nascimento);
	}
}
