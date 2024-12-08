package br.com.escola.admin.repositories;

import br.com.escola.admin.models.Diretor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface DiretorRepository extends JpaRepository<Diretor,Long> {

    @Query("SELECT d FROM Diretor d WHERE d.cpf = :cpf")
    Optional<Diretor> findByCpf(String cpf);
}
