package br.com.escola.admin.repositories;

import br.com.escola.admin.models.CursoAluno;
import br.com.escola.admin.models.CursoAlunoID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoAlunoRepository extends JpaRepository<CursoAluno, CursoAlunoID> {
}
