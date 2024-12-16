package br.com.nksolucoes.nkorderms.repository;

import br.com.nksolucoes.nkorderms.domain.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {}
