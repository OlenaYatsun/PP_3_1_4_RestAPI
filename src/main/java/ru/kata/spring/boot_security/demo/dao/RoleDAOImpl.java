package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.hibernate.FetchMode.JOIN;
import static org.hibernate.hql.internal.antlr.HqlTokenTypes.FETCH;

@Repository
public class RoleDAOImpl implements RoleDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role getRoleById(int id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public Role getRoleByRoleName(String name) {
        return entityManager.find(Role.class, name);
    }

    @Override
    public void save(Role role) {
        entityManager.persist(role);
    }

    @Override
    public void update( Role updatedRole) {
        entityManager.merge(updatedRole);
    }

    @Override
    public void delete(int id) {
        entityManager.createQuery("delete from Role role where role.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public List<Role> getDemandedRoles() {
        return entityManager.createQuery("select r from Role r",  Role.class).getResultList();
    }
}
