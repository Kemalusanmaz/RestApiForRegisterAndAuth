package com.tai.gky.demoKemal.Repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
@NoRepositoryBean
public interface IBaseRepository<T, ID> extends ListCrudRepository<T, ID>, Repository<T, ID> {
}
