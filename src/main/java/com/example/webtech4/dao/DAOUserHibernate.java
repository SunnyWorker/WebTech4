package com.example.webtech4.dao;

import com.example.webtech4.pojo.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class DAOUserHibernate implements DAOUser{

    private SessionFactory sessionFactory;
    private ThreadPoolExecutor threadPoolExecutor;

    public DAOUserHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
    }
    
    @Override
    public User findBoookingById(long id) {
        try {
            return threadPoolExecutor.submit(()->{
                Session session = sessionFactory.openSession();
                Transaction transaction = null;
                User user;
                try {
                    transaction = session.beginTransaction();
                    user = session.get(User.class,id);
                    transaction.commit();
                } catch (HibernateException e) {
                    if (transaction!=null) transaction.rollback();
                    return null;
                } finally {
                    session.close();
                }
                return user;
            }).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findBoookingByEmail(String email) {
        try {
            return threadPoolExecutor.submit(()->{
                Session session = sessionFactory.openSession();
                Transaction transaction = null;
                CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                CriteriaQuery<User> cr = criteriaBuilder.createQuery(User.class);
                Root<User> root = cr.from(User.class);
                cr.select(root);
                cr.where(criteriaBuilder.equal(root.get("email"),email));
                List<User> users = null;
                try {
                    transaction = session.beginTransaction();
                    users = session.createQuery(cr).getResultList();
                    transaction.commit();
                } catch (HibernateException e) {
                    if (transaction!=null) transaction.rollback();
                    e.printStackTrace();
                    return null;
                } finally {
                    session.close();
                }
                if(users.size()>0) return users.get(0);
                else return null;
            }).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAllUsers() {
        try {
            return threadPoolExecutor.submit(()->{
                Session session = sessionFactory.openSession();
                Transaction transaction = null;
                CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                CriteriaQuery<User> cr = criteriaBuilder.createQuery(User.class);
                Root<User> root = cr.from(User.class);
                cr.select(root);
                List<User> users = null;
                try {
                    transaction = session.beginTransaction();
                    users = session.createQuery(cr).getResultList();
                    transaction.commit();
                } catch (HibernateException e) {
                    if (transaction!=null) transaction.rollback();
                    e.printStackTrace();
                } finally {
                    session.close();
                }
                return users;
            }).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(User user) {
        threadPoolExecutor.execute(()->{
            Session session = sessionFactory.openSession();
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                User user2 = session.merge(user);
                session.persist(user2);
                transaction.commit();
            } catch (HibernateException e) {
                if (transaction!=null) transaction.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        });
    }

    @Override
    public void deleteUser(User user) {
        threadPoolExecutor.execute(()->{
            Session session = sessionFactory.openSession();
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                User user2 = session.merge(user);
                session.remove(user2);
                transaction.commit();
            } catch (HibernateException e) {
                if (transaction!=null) transaction.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        });
    }
}
