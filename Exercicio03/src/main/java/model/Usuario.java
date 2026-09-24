package model;

public class Usuario {

    // Dados que representam um usuário
    private int id;
    private String nome;
    private String usuario;
    private String email;
    private String senha;

    // Construtor vazio
    public Usuario() {
        id = -1;
        nome = "";
        usuario = "";
        email = "";
        senha = "";
    }

    // Construtor usado quando o usurio já possui um id
    public Usuario(int id, String nome, String usuario, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.usuario = usuario;
        this.email = email;
        this.senha = senha;
    }

    // Construtor usado para cadastrar um novo usuário
    public Usuario(String nome, String usuario, String email, String senha) {
        this.nome = nome;
        this.usuario = usuario;
        this.email = email;
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}