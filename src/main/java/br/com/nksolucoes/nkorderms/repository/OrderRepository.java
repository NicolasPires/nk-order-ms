package br.com.nksolucoes.nkorderms.repository;

import br.com.nksolucoes.nkorderms.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {}
