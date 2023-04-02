package br.com.escola.admin.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_curso")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_curso")
    private Long id;
    @Column(name = "nm_curso")
    private String nome;
    @Column(name = "ds_descricao")
    private String descricao;
    @Column(name = "ds_imgURL")
    private String imgUrl;
    @ManyToOne
    @JoinColumn(name = "cd_professor")
    private Professor professor;

    public Curso(String nome, String descricao, String imgUrl, Professor professor) {
        this.nome = nome;
        this.descricao = descricao;
        this.imgUrl = imgUrl;
        this.professor = professor;
    }

    public Curso() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

}
