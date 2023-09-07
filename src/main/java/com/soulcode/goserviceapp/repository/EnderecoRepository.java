package com.soulcode.goserviceapp.repository;


import com.soulcode.goserviceapp.domain.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    @Modifying
    @Query("UPDATE Endereco e SET e.logradouro = :#{#novoEndereco.logradouro}, e.numero = :#{#novoEndereco.numero}, e.cidade = :#{#novoEndereco.cidade}, e.uf = :#{#novoEndereco.uf} WHERE (e.id = :id OR e.usuario.email = :email)")
    void alterarEnderecoPorIdOuEmail(
            @Param("id") Long id,
            @Param("email") String email,
            @Param("novoEndereco") Endereco novoEndereco
    );
}
