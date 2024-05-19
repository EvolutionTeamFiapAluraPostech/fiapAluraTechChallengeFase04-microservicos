package br.com.fiap.logistics.infrastructure.repository;

import br.com.fiap.logistics.domain.entity.Logistic;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogisticRepository extends JpaRepository<Logistic, UUID> {

}
