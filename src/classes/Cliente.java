package classes;

import java.util.Date;

public class Cliente extends Pessoa {

	private int idCliente;
	private String endereco;
	private String telefone;

	public Cliente(int idCliente, String nome, String cpf, Date data_nascimento, String endereco, String telefone) {
		super(nome, cpf, data_nascimento);
		this.idCliente = idCliente;
		this.endereco = endereco;
		this.telefone = telefone;
	}

	public Cliente() {
		super();
	}

	@Override
	public String toString() {
		return "\nCódigo do Cliente:\t" + idCliente + "\n" + super.toString() + "\n\nEndereco:" + endereco + " \nContato:" + telefone;
	}
	


	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public int getIdCliente() {
		return idCliente;
	}

}
