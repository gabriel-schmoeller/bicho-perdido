package bichoperdido.business.user.domain;

/**
 * @author Gabriel.
 */
public class UsuarioInput {

    private String nome;
    private String telefone;
    private String email;
    private String senha;
    private String dummy;

    public UsuarioInput() {
    }

    public UsuarioInput(String nome, String telefone, String email, String senha) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getDummy() {
        return dummy;
    }
}
